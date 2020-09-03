package com.garen.sys.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * @Author sxm
 * @Description DESede对称加密算法
 * @Date 2018年3月22日
 */
public class DESedeCoder extends Coder {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     * 生成密钥
     *
     * @return 密钥
     */
    public static String initkey() throws Exception {
        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥生成器
        kg.init(168);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(String key) throws Exception {
        // 实例化Des密钥
        DESedeKeySpec dks = new DESedeKeySpec(decryptBASE64(key));
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance(KEY_ALGORITHM);
        // 生成密钥
        return keyFactory.generateSecret(dks);
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return String 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        // 还原密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        return encryptBASE64(cipher.doFinal(dataBytes));
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        // 欢迎密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        byte[] dataBytes = cipher.doFinal(decryptBASE64(data));
        return new String(dataBytes, StandardCharsets.UTF_8);
    }

    /**
     * 进行加解密的测试
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String str = "DESede";
        System.out.println("原文：" + str);
        // 初始化密钥
        String key = DESedeCoder.initkey();
        System.out.println("密钥：" + key);
        // 加密数据
        String data = DESedeCoder.encrypt(str, key);
        System.out.println("加密后：" + data);
        // 解密数据
        String b = DESedeCoder.decrypt(data, key);
        System.out.println("解密后：" + b);
    }
}
