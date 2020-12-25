package com.lixin.litemall.wx.service.impl;

import com.lixin.litemall.common.common.CommonSymbol;
import com.lixin.litemall.db.dao.*;
import com.lixin.litemall.db.domain.*;
import com.lixin.litemall.wx.service.WxGoodsService;
import com.lixin.litemall.wx.vo.StoreInfoVo;
import com.lixin.litemall.wx.vo.goods.CategoryInfoVo;
import com.lixin.litemall.wx.vo.goods.GoodsProductVo;
import com.lixin.litemall.wx.vo.goods.SkuAttr;
import com.lixin.litemall.wx.vo.goods.SpuAttr;
import net.sf.cglib.beans.BeanCopier;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WxGoodsServiceImpl implements WxGoodsService {

    //https://www.cnblogs.com/winner-0715/p/10117282.html
    final BeanCopier beanCopier = BeanCopier.create(LitemallGoods.class, GoodsProductVo.class, false);

    @Resource
    LitemallGoodsMapper goodsMapper;
    @Resource
    LitemallGoodsProductMapper productMapper;
    @Resource
    LitemallCategoryMapper categoryMapper;
    @Resource
    LitemallAdminMapper adminMapper;
    @Resource
    LitemallGoodsAttributeMapper attributeMapper;

    Logger logger = LoggerFactory.getLogger(WxGoodsServiceImpl.class);

    /**
     * 获取某个分类下所有的商品
     *
     * @param cateGoryId
     * @return
     */
    @Override
    public List<GoodsProductVo> getAllOrderGoodsAndCateGory(Integer cateGoryId) {

        LitemallGoodsExample litemallGoodsExample = new LitemallGoodsExample();

        litemallGoodsExample.createCriteria()
                .andCategoryIdEqualTo(cateGoryId);
        ArrayList<GoodsProductVo> goodsVos = new ArrayList<>();

        List<LitemallGoods> litemallGoods = goodsMapper.selectByExample(litemallGoodsExample);

        // 对商品属性进行填充
        for (LitemallGoods litemallGood : litemallGoods) {
            // 商品的VO
            GoodsProductVo goodsProductVo = new GoodsProductVo();

            litemallGood.setName(litemallGood.getName().substring(0, 3));
            litemallGood.setBrief(litemallGood.getName().substring(0, 3));

            beanCopier.copy(litemallGood, goodsProductVo, null);

            // 对应用的规格进行填充
            LitemallGoodsProductExample productExample = new LitemallGoodsProductExample();
            ArrayList<SkuAttr> skuses = new ArrayList<>();

            productExample.createCriteria()
                    .andGoodsIdEqualTo(litemallGood.getId());


            // 获取某个商品的规格(sku)属性
            List<LitemallGoodsProduct> goodsSpecs = productMapper.selectByExample(productExample);
            for (LitemallGoodsProduct goodsSpec : goodsSpecs) {
                SkuAttr skuAttr = new SkuAttr();
                skuAttr.setSpec(Strings.join(Arrays.asList(goodsSpec.getSpecifications()), '-'));
                skuAttr.setPrice(goodsSpec.getPrice());
                skuses.add(skuAttr);
            }
            goodsProductVo.setSkuAttrList(skuses);


            // 获取商品的可选的spu 属性
            LitemallGoodsAttributeExample attributeExample = new LitemallGoodsAttributeExample();
            attributeExample.createCriteria()
                    .andGoodsIdEqualTo(litemallGood.getId())
                    .andCanSelectEqualTo(CommonSymbol.CanSelect);

            List<LitemallGoodsAttribute> attributes = attributeMapper.selectByExample(attributeExample);
            ArrayList<SpuAttr> spuAttrs = new ArrayList<>();
            for (LitemallGoodsAttribute attribute : attributes) {
                SpuAttr spuAttr = new SpuAttr();
                spuAttr.setSpuAttrName(attribute.getAttribute());
                spuAttr.setAttrValues(Arrays.asList(attribute.getValue().split(CommonSymbol.attrSplit)));
                spuAttrs.add(spuAttr);
            }

            goodsProductVo.setSpuAttrList(spuAttrs);

            goodsVos.add(goodsProductVo);
        }

        return goodsVos;
    }


    @Override
    public List<LitemallCategory> getAllOrderGoodsCategory(String shopId) {

        LitemallCategoryExample categoryExample = new LitemallCategoryExample();
        categoryExample.createCriteria()
                .andShopIdEqualTo(shopId)
                .andLevelEqualTo("L2");

        return categoryMapper.selectByExample(categoryExample);
    }

    /**
     * 获取所有带分类的商品
     *
     * @return
     */
    @Override
    public StoreInfoVo getAllCategoryAndGoods(String shopId) {

        LitemallAdminExample adminExample = new LitemallAdminExample();
        adminExample.createCriteria()
                .andShopIdEqualTo(shopId);
        LitemallAdmin admin = adminMapper.selectOneByExample(adminExample);

        StoreInfoVo store = new StoreInfoVo();
        store.setDelPrice(admin.getDelPrice());
        store.setMinDelPrice(admin.getMinDelPrice());
        store.setStoreName(admin.getShopName());

        ArrayList<CategoryInfoVo> categoryInfoVos = new ArrayList<>();
        List<LitemallCategory> allOrderGoodsCategory = getAllOrderGoodsCategory(shopId);

        for (LitemallCategory category : allOrderGoodsCategory) {

            CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
            categoryInfoVo.setCategoryId(category.getId());
            categoryInfoVo.setCategoryName(category.getName());
            categoryInfoVo.setFoodCount(0);

            // 获取当前分类下的商品
            List<GoodsProductVo> allOrderGoodsAndCateGory = getAllOrderGoodsAndCateGory(category.getId());
            if (allOrderGoodsAndCateGory.size() <= 3) {
                continue;
            }
            categoryInfoVo.setItems(allOrderGoodsAndCateGory);
            categoryInfoVos.add(categoryInfoVo);
        }
        store.setFood(categoryInfoVos);
        return store;

    }
}
