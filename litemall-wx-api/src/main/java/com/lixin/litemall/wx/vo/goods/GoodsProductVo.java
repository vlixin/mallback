package com.lixin.litemall.wx.vo.goods;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsProductVo {


    private String goodsSn;

    private String name;
    // 分类ID
    private Integer categoryId;

    // 商品介绍图片
    private String[] gallery;
    // 关键字
    private String keywords;
    // 商品简介
    private String brief;

    private Boolean isOnSale;
    // 排序
    private Short sortOrder;
    // 商品页面介绍图片
    private String picUrl;
    // 分享海报
    private String shareUrl;

    private String unit;
    // 价格
    private BigDecimal counterPrice;
    // 零售价格
    private BigDecimal retailPrice;
    // 上面是 goods自己的属性


}
