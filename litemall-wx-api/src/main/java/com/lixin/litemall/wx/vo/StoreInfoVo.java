package com.lixin.litemall.wx.vo;

import com.lixin.litemall.wx.vo.goods.CategoryInfoVo;
import lombok.Data;

import java.util.List;

/**
 * 商店菜单展示类
 */
@Data
public class StoreInfoVo {
    Integer minDelPrice;
    Integer delPrice;
    List<CategoryInfoVo> food;
}
