package com.lixin.litemall.wx.service;

import com.lixin.litemall.db.domain.LitemallCategory;
import com.lixin.litemall.db.domain.LitemallGoods;

import java.util.List;

/**
 * 商品服务定义接口
 */
public interface WxGoodsService {

    /**
     * 获取当前所有的商品按照类型和商品的排序
     */
    List<LitemallGoods> getAllOrderGoodsAndCateGory();

    /**
     * 获取当前店铺所有的类型分类
     */
    List<LitemallCategory> getAllOrderGoodsCategory();
}
