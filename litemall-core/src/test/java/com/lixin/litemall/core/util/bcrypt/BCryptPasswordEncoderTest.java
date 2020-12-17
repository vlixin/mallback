package com.lixin.litemall.core.util.bcrypt;

import junit.framework.TestCase;

public class BCryptPasswordEncoderTest extends TestCase {

    public void testEncode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String lixin = bCryptPasswordEncoder.encode("lixin");
        System.out.println(lixin);

        boolean lixin1 = bCryptPasswordEncoder.matches("lixin", lixin);
        System.out.println(lixin1);
    }
}
