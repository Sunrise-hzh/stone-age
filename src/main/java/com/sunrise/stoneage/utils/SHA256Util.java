package com.sunrise.stoneage.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class SHA256Util {
    private SHA256Util(){}
    // 加密算法
    public final static String HASH_ALGORITHM_NAME = "SHA-256";

    // 循环次数
    public final static int HASH_ITERATIONS = 15;

    // 执行SHA256和盐值加密
    public static String sha256(String password, String salt){
        return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
    }
}
