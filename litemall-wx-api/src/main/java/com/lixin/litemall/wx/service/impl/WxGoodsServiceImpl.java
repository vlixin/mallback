package com.lixin.litemall.wx.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lixin.litemall.core.util.JacksonUtil;
import com.lixin.litemall.db.dao.LitemallGoodsMapper;
import com.lixin.litemall.db.dao.LitemallGoodsProductMapper;
import com.lixin.litemall.db.domain.*;
import com.lixin.litemall.wx.service.WxGoodsService;
import com.lixin.litemall.wx.vo.goods.GoodsProductVo;
import com.lixin.litemall.wx.vo.goods.GoodsSpecificationVo;
import net.sf.cglib.beans.BeanCopier;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

public class WxGoodsServiceImpl implements WxGoodsService {

    //https://www.cnblogs.com/winner-0715/p/10117282.html
    final BeanCopier beanCopier = BeanCopier.create(LitemallGoods.class, GoodsProductVo.class, false);

    @Resource
    LitemallGoodsMapper goodsMapper;
    @Resource
    LitemallGoodsProductMapper productMapper;


    @Override
    public List<LitemallGoods> getAllOrderGoodsAndCateGory(String shopId) {

        LitemallGoodsExample litemallGoodsExample = new LitemallGoodsExample();
        litemallGoodsExample.createCriteria()
                .andShopIdEqualTo(shopId);

        List<LitemallGoods> litemallGoods = goodsMapper.selectByExample(litemallGoodsExample);
        for (LitemallGoods litemallGood : litemallGoods) {
            // 商品的VO
            GoodsProductVo goodsProductVo = new GoodsProductVo();
            beanCopier.copy(litemallGood, goodsProductVo, null);

            // 对应用的规格进行填充
            LitemallGoodsProductExample productExample = new LitemallGoodsProductExample();
            productExample.createCriteria()
                    .andGoodsIdEqualTo(litemallGood.getId());

            List<LitemallGoodsProduct> goodsSpecs = productMapper.selectByExample(productExample);
            for (LitemallGoodsProduct goodsSpec : goodsSpecs) {
                GoodsSpecificationVo goodsSpecificationVo = new GoodsSpecificationVo();

                JacksonUtil.mapper.readValue(goodsSpec.getSpecifications(), new TypeReference<HashMap<String, String>>() {
                });
            }


        }

        return null;
    }

    @Override
    public List<LitemallCategory> getAllOrderGoodsCategory() {
        return null;
    }
}
