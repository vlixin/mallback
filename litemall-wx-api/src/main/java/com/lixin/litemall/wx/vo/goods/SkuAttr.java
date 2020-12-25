package com.lixin.litemall.wx.vo.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * recode specs and price
 */
@Data
public class SkuAttr {
    List<String> spec;
    BigDecimal price;
    String skuId;
}


