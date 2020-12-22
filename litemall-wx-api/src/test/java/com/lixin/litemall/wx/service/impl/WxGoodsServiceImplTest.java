package com.lixin.litemall.wx.service.impl;

import com.lixin.litemall.common.common.CommonSymbol;
import com.lixin.litemall.wx.vo.goods.GoodsProductVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WxGoodsServiceImplTest {

    @Autowired
    WxGoodsServiceImpl wxGoodsService;

    @Test
    public void testSplit() {
        String[] split = "hello".split(CommonSymbol.colon);
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void testGetAllGoods() {
        List<GoodsProductVo> a0000A = wxGoodsService.getAllOrderGoodsAndCateGory("A0000A");
        System.out.println(a0000A);
    }


}
