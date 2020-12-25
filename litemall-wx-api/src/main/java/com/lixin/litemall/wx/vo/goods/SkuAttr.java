package com.lixin.litemall.wx.vo.goods;

import lombok.Data;

import java.math.BigDecimal;

/**
 * recode specs and price
 */
@Data
public class SkuAttr {
    String spec;
    BigDecimal price;
    String skuId;
}


