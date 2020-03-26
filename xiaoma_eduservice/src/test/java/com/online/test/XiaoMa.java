package com.online.test;

import org.junit.Test;

import java.util.Optional;

/**
 * @Author: changyong
 * @Date: create in 14:53 2020/3/25
 * @Description:
 */
public class XiaoMa {

    @Test
    public void testOptional() {
        String str1 = null;
        String str2 = "466";
        String str3 = Optional.ofNullable(str1).orElseGet(() -> str2);
        System.out.println(str3);
    }
}
