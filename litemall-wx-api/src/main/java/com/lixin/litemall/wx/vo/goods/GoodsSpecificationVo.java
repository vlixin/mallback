package com.lixin.litemall.wx.vo.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * recode specs and price
 */
@Data
public class GoodsSpecificationVo {
    HashMap<String, String> specs;
    BigDecimal price;
}

