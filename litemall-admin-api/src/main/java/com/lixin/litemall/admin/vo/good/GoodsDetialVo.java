package com.lixin.litemall.admin.vo.good;

import com.lixin.litemall.db.domain.LitemallGoods;
import com.lixin.litemall.db.domain.LitemallGoodsAttribute;
import com.lixin.litemall.db.domain.LitemallGoodsProduct;
import com.lixin.litemall.db.domain.LitemallGoodsSpecification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class GoodsDetialVo {
    LitemallGoods goods;
    List<LitemallGoodsProduct> products;
    List<LitemallGoodsSpecification> specifications;
    List<LitemallGoodsAttribute> attributes;
}
