package com.sunrise.stoneage.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密工具类
 */
public class EncryptionUtil {
    private EncryptionUtil(){}

    // 加密算法
    public final static String SHA_256 = "SHA-256";
    public final static String MD5 = "MD5";

    // 循环次数
    public final static int HASH_ITERATIONS = 15;

    // 执行SHA256和盐值加密
    public static String sha256(String password, String salt){
        return new SimpleHash(SHA_256, password, salt, HASH_ITERATIONS).toString();
    }

    // md5和盐值加密
    public static String md5(String password, String salt){
        return new SimpleHash(MD5, password, salt, HASH_ITERATIONS).toString();
    }
}
