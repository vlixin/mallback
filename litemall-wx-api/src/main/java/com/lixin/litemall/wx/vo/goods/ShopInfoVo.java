package com.lixin.litemall.wx.vo.goods;

import lombok.Data;

import java.util.List;

/**
 * 当前店内关键信息
 */
@Data
public class ShopInfoVo {
    // 分类ID
    int categoryId;
    // 分类名称
    String categoryName;
    // 当前购买数量
    int foodCount;
    // 该分类下商品信息
    List<GoodsProductVo> items;
}
