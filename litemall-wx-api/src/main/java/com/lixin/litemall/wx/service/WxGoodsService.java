package com.lixin.litemall.wx.service;

import com.lixin.litemall.db.domain.LitemallCategory;
import com.lixin.litemall.wx.vo.StoreInfoVo;
import com.lixin.litemall.wx.vo.goods.GoodsProductVo;

import java.io.IOException;
import java.util.List;

/**
 * 商品服务定义接口
 */
public interface WxGoodsService {

    /**
     * 获取当前所有的商品按照类型和商品的排序
     *
     * @return
     */
    List<GoodsProductVo> getAllOrderGoodsAndCateGory(Integer shopId) throws IOException;

    /**
     * 获取当前店铺所有的类型分类
     */
    List<LitemallCategory> getAllOrderGoodsCategory(String shopId);

    /**
     * 获取店铺首次加载信息
     *
     * @param shopId
     * @return
     */
    StoreInfoVo getAllCategoryAndGoods(String shopId);
}
