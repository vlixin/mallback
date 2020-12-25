package com.lixin.litemall.wx.vo.goods;

import lombok.Data;

import java.util.List;

/**
 * spu 属性
 */
@Data
public class SpuAttr {
   String spuAttrName; // 辣度
   List<String> attrValues; // 不辣 麻辣 香辣
}
