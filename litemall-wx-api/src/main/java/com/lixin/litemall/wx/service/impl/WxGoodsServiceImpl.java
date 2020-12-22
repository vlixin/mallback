package com.lixin.litemall.wx.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lixin.litemall.core.util.JacksonUtil;
import com.lixin.litemall.db.dao.LitemallAdminMapper;
import com.lixin.litemall.db.dao.LitemallCategoryMapper;
import com.lixin.litemall.db.dao.LitemallGoodsMapper;
import com.lixin.litemall.db.dao.LitemallGoodsProductMapper;
import com.lixin.litemall.db.domain.*;
import com.lixin.litemall.wx.service.WxGoodsService;
import com.lixin.litemall.wx.vo.StoreInfoVo;
import com.lixin.litemall.wx.vo.goods.CategoryInfoVo;
import com.lixin.litemall.wx.vo.goods.GoodsProductVo;
import com.lixin.litemall.wx.vo.goods.GoodsSpecificationVo;
import net.sf.cglib.beans.BeanCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    Logger logger = LoggerFactory.getLogger(WxGoodsServiceImpl.class);

    @Override
    public List<GoodsProductVo> getAllOrderGoodsAndCateGory(Integer cateGoryId) {

        LitemallGoodsExample litemallGoodsExample = new LitemallGoodsExample();
        litemallGoodsExample.createCriteria()
                .andCategoryIdEqualTo(cateGoryId);
        ArrayList<GoodsProductVo> goodsVos = new ArrayList<>();

        List<LitemallGoods> litemallGoods = goodsMapper.selectByExample(litemallGoodsExample);

        for (LitemallGoods litemallGood : litemallGoods) {
            // 商品的VO
            GoodsProductVo goodsProductVo = new GoodsProductVo();
            beanCopier.copy(litemallGood, goodsProductVo, null);

            // 对应用的规格进行填充
            LitemallGoodsProductExample productExample = new LitemallGoodsProductExample();
            productExample.createCriteria()
                    .andGoodsIdEqualTo(litemallGood.getId());

            ArrayList<GoodsSpecificationVo> goodsSpecificationVos = new ArrayList<>();
            List<LitemallGoodsProduct> goodsSpecs = productMapper.selectByExample(productExample);

            for (LitemallGoodsProduct goodsSpec : goodsSpecs) {
                GoodsSpecificationVo goodsSpecificationVo = new GoodsSpecificationVo();
                try {
                    goodsSpecificationVo.setSpecs(JacksonUtil.mapper.readValue(goodsSpec.getSpecsJson(),
                            new TypeReference<HashMap<String, String>>() {
                            }));
                } catch (IOException e) {
                    logger.error("parse spec json error , specs id {}", goodsSpec.getId());
                    continue;
                }
                goodsSpecificationVo.setPrice(goodsSpec.getPrice());

                goodsSpecificationVos.add(goodsSpecificationVo);
            }
            goodsProductVo.setSpecifications(goodsSpecificationVos);
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

        ArrayList<CategoryInfoVo> categoryInfoVos = new ArrayList<>();
        List<LitemallCategory> allOrderGoodsCategory = getAllOrderGoodsCategory(shopId);
        for (LitemallCategory category : allOrderGoodsCategory) {
            CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
            categoryInfoVo.setCategoryId(category.getId());
            categoryInfoVo.setCategoryName(category.getName());
            categoryInfoVo.setFoodCount(0);

            // 获取当前分类下的商品
            List<GoodsProductVo> allOrderGoodsAndCateGory = getAllOrderGoodsAndCateGory(category.getId());
            categoryInfoVo.setItems(allOrderGoodsAndCateGory);
            categoryInfoVos.add(categoryInfoVo);
        }
        store.setFood(categoryInfoVos);
        return store;

    }
}
