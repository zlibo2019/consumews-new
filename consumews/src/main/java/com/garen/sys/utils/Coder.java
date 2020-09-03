package com.garen.sys.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * <dt><b>类名：</b></dt>
 * <dd>Coder</dd>
 * <dt><b>描述：</b></dt>
 * <dd>基础加密组件</dd>
 * <dt><b>日期：</b></dt>
 * <dd>2016-5-24 上午9:20:02</dd>
 * </pre>
 *
 * @author SXM
 * @version 0.1
 */
public class Coder extends DigestUtils {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";
    public static final String KEY_SHA256 = "SHA256";

    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC_MD5 = "HmacMD5";

    public static final String KEY_MAC_SHA256 = "HmacSHA256";

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    // /**
    //  * MD5加密
    //  *
    //  * @param data
    //  * @return
    //  * @throws Exception
    //  */
    // public static String encryptMD5(byte[] data) throws Exception {
    //     MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
    //     md5.update(data);
    //     return byte2Hex(md5.digest());
    // }
    //
    // /**
    //  * SHA加密
    //  *
    //  * @param data
    //  * @return
    //  * @throws Exception
    //  */
    // public static String encryptSHA(byte[] data) throws Exception {
    //     MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
    //     sha.update(data);
    //     return byte2Hex(sha.digest());
    // }

    /**
     * 初始化HMAC密钥
     *
     * @return
     * @throws Exception
     */
    private static String initHmacKey(String type) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encryptHmac(byte[] data, String key, String type) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), type);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    public static String initHmacMD5Key() throws Exception {
        return initHmacKey(KEY_MAC_MD5);
    }

    public static byte[] encryptHmacMD5(byte[] data, String key) throws Exception {
        return encryptHmac(data, key, KEY_MAC_MD5);
    }

    public static String initHmacSHA256Key() throws Exception {
        return initHmacKey(KEY_MAC_SHA256);
    }

    public static byte[] encryptHmacSHA256(byte[] data, String key) throws Exception {
        return encryptHmac(data, key, KEY_MAC_SHA256);
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String encryptSHA256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance(KEY_SHA256);
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}