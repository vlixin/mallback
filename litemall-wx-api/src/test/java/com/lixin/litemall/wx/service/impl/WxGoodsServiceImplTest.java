package com.lixin.litemall.wx.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lixin.litemall.common.common.CommonSymbol;
import com.lixin.litemall.core.util.JacksonUtil;
import com.lixin.litemall.wx.vo.StoreInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
        StoreInfoVo a0000A = wxGoodsService.getAllCategoryAndGoods("A0000A");
        try {
            System.out.println(JacksonUtil.mapper.writeValueAsString(a0000A));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
