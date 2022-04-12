package com.sunrise.stoneage.dao;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author huangzihua
 * @date 2021-11-08
 */
public class MD5Test {
    public static void main(String[] args) {
        String appid = "dygas";
        String key = "232b2e8e-402b-11ec-92d2-fa163ee36d30";
        String account = "test001";
        String str = appid + key + account;
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        String res = DigestUtils.md5DigestAsHex(bytes);
        System.out.println(res);
    }
}
