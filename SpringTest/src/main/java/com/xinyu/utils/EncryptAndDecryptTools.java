package com.xinyu.utils;





import cn.hutool.core.codec.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author jiaoxy
 *
 * 加解密工具类
 * 提供了RSA加密RSA解密，AES加密AES解密。
 * 如果要使用RSA加密,rsa加密长度有限，例如密钥为2048长度 则最大能加密的字符串长度为
 * 2048/8 =256 但是还需要减去PKCS1Padding本身占用11个 所以最大245个字节 1024位密钥长度最大只有117字节加密。超过长度会报错
 * 如果字符串比较长 建议使用 AES加密。
 */
public class EncryptAndDecryptTools {
    private static Logger logger = LoggerFactory.getLogger(EncryptAndDecryptTools.class);


    /**
     * AES加密
     * @param data
     * @param key
     * @return
     */
    public static String AesEncryptToBase64(String data, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
            return Base64.encode(valueByte);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }

    }

    /**
     * AES解密
     * @param data
     * @param key
     * @return
     */
    public static String AesDecryptFromBase64(String data, String key) {
        try {
            byte[] originalData = Base64.decode(data.getBytes());
            byte[] valueByte = decrypt(originalData, key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
            return new String(valueByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }
    /**
     *
     * RSA加密 加密串最大长度为密钥长度/8 -11 字节
     * @param key
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String RsaEncrypt(String key, String plainText) throws Exception {
        if (StringUtils.isEmpty(key)|| StringUtils.isEmpty(plainText)) {
            logger.info("入参有误 - 》key,{} plainText,{}",key,plainText);
            throw new Exception("参数有误");
        }
        byte[] keyBytes = Base64.decode(key);
        return encrypt(keyBytes, plainText);
    }


    /**
     * RSA解密
     * @param publicKeyText
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String RsaDecrypt(String publicKeyText, String cipherText) throws Exception {
        if (StringUtils.isEmpty(publicKeyText)|| StringUtils.isEmpty(cipherText)) {
            logger.info("入参有误 - 》publicKeyText,{} cipherText,{}",publicKeyText,cipherText);
            throw new Exception("参数有误");
        }
        PublicKey publicKey = getPublicKey(publicKeyText);
        return decrypt(publicKey, cipherText);
    }

    /**
     * 获取RSA公私钥 keySize 最小限制512
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static String[] genRsaKeyPair(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        keySize = keySize < 512 ? 1024:keySize;
        byte[][] keyPairBytes = genKeyPairBytes(keySize);
        String[] keyPairs = new String[]{Base64.encode(keyPairBytes[0]), Base64.encode(keyPairBytes[1])};
        return keyPairs;
    }

    /**
     * rsa加密
     * @param keyBytes
     * @param plainText
     * @return
     * @throws Exception
     */
    private static String encrypt(byte[] keyBytes, String plainText) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
        PrivateKey privateKey = factory.generatePrivate(spec);
        Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM);

        try {
            cipher.init(1, privateKey);
        } catch (InvalidKeyException var10) {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            Key fakePublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(1, fakePublicKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedString = Base64.encode(encryptedBytes);
        return encryptedString;
    }

    /**
     * rsa解密
     * @param publicKey
     * @param cipherText
     * @return
     * @throws Exception
     */
    private static String decrypt(PublicKey publicKey, String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM);

        try {
            cipher.init(2, publicKey);
        } catch (InvalidKeyException var7) {
            RSAPublicKey rsaPublicKey = (RSAPublicKey)publicKey;
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(2, fakePrivateKey);
        }

        if (cipherText != null && cipherText.length() != 0) {
            byte[] cipherBytes = Base64.decode(cipherText);
            byte[] plainBytes = cipher.doFinal(cipherBytes);
            return new String(plainBytes);
        } else {
            return cipherText;
        }
    }

    /**
     * 获取rsa公钥
     * @param publicKeyText
     * @return
     */
    private static PublicKey getPublicKey(String publicKeyText) {
        if (publicKeyText == null || publicKeyText.length() == 0) {
            return null;
        }

        try {
            byte[] publicKeyBytes =Base64.decode(publicKeyText);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception var4) {
            throw new IllegalArgumentException("Failed to get public key", var4);
        }
    }

    /**
     * 获取rsa私钥
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    private static byte[][] genKeyPairBytes(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[][] keyPairBytes = new byte[2][];
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
        gen.initialize(keySize, new SecureRandom());
        KeyPair pair = gen.generateKeyPair();
        keyPairBytes[0] = pair.getPrivate().getEncoded();
        keyPairBytes[1] = pair.getPublic().getEncoded();
        return keyPairBytes;
    }


    /**
     * AES加密
     * @param data 需要加密的内容
     * @param key  加密密钥 16位限制
     * @return
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        if(data==null || key==null){
            throw new RuntimeException("data or key must be specified");
        }
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(key);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, seckey, iv);
            // 加密
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (Exception e) {
            logger.error("AES加密失败",e);
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * AES解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        if(data==null || key==null){
            throw new RuntimeException("data or key must be specified");
        }
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(key);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, seckey, iv);
            byte[] result = cipher.doFinal(data);
            // 解密
            return result;
        } catch (Exception e) {
            logger.error("AES解密失败",e);
            throw new RuntimeException("decrypt fail!", e);
        }
    }

}
