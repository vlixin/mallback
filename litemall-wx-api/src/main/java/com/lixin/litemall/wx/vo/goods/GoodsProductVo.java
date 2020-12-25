package com.lixin.litemall.wx.vo.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsProductVo {

    private String goodsSn;

    private String name;
    // 分类ID
    private Integer categoryId;

    // 商品介绍图片
    //private String[] gallery;
    // 关键字
    private String keywords;

    // 商品简介
    private String brief;

    // 排序
    private Short sortOrder;

    // 商品页面介绍图片
    private String picUrl;

    private String unit;

    // 价格
    private BigDecimal counterPrice;

    // 零售价格
    private BigDecimal retailPrice;

    // 商品数量
    private int count;

    // 上面是 goods自己的属性


    // 下面是 商品的规格信息 stock keep 商品库存属性 Stock Keeping Unit
    List<SkuAttr> skuAttrList;

    // 商品的可选属性  商品标准单元  Standard Product Unit
    List<SpuAttr> spuAttrList;


}
