package com.srtp.key;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Henry Huang on 2015/1/12.
 */
public class AllKey {
    /**
     * BASE64 解密
     * @param key
     * @return
     */
    public static byte[] fromBASE64(String key) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64 加密
     * @param key
     * @return
     */
    public static String toBASE64(byte[] key){
        return (new BASE64Encoder()).encode(key);
    }

    /**
     * MD5 加密
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String toMD5(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return toBASE64(md5.digest());
    }

    /**
     * SHA 加密
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] toSHA(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
        return sha.digest();
    }

    /**
     * HMAC 初始化
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String initMacKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("MAC");
        SecretKey secretKey = keyGenerator.generateKey();
        return toBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC 加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] toHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(fromBASE64(key), "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal();
    }

}
