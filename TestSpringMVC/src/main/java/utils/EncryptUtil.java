package utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 * AES加密类
 */
public class EncryptUtil {
    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
    private static final String KEY = "newland_computer";
    private static final String KEYGENERATOR = "AES";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
//    private static final String ALGORITHMSTR = "AES/GCM/NoPadding";
    private static final String U_CODER_STR = "utf-8";
    private static final String KEY_LOG_AUDIT = "address_logaudit";

    private EncryptUtil() {
    }

    public static String getKey() {
        return KEY;
    }

    /**
     * base64Encode
     *
     * @param bytes xxx
     * @return String
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base64Decode
     *
     * @param base64Code xx
     * @return xx
     * @throws Exception xx
     */
    public static byte[] base64Decode(String base64Code) throws IOException {
        return new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * aesEncryptToBytes
     *
     * @param content    xxx
     * @param encryptKey xxx
     * @return xx
     * @throws Exception xxx
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws GeneralSecurityException, UnsupportedEncodingException {
        KeyGenerator kgen = KeyGenerator.getInstance(KEYGENERATOR);
        kgen.init(256);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(EncryptUtil.U_CODER_STR), KEYGENERATOR));
        return cipher.doFinal(content.getBytes(EncryptUtil.U_CODER_STR));
    }

    /**
     * aesEncrypt
     *
     * @param content    xxx
     * @param encryptKey xxx
     * @return xx
     * @throws Exception xxx
     */
    public static String aesEncrypt(String content, String encryptKey) throws GeneralSecurityException, IOException {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * aesDecryptByBytes
     *
     * @param encryptBytes xx
     * @param decryptKey   xx
     * @return xx
     * @throws Exception xxx
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws GeneralSecurityException, UnsupportedEncodingException {
        KeyGenerator kgen = KeyGenerator.getInstance(KEYGENERATOR);
        kgen.init(256);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(EncryptUtil.U_CODER_STR), KEYGENERATOR));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /***
     * aesDecrypt
     *
     * @param encryptStr xx
     * @param decryptKey  xxx
     * @return xxx
     * @throws Exception xxx
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws GeneralSecurityException, IOException {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static String logAuditAesEncrypt(String content) {
        try {
            return aesEncrypt(content, KEY_LOG_AUDIT);
        } catch (GeneralSecurityException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String logAuditAesDecrypt(String encryptStr) {
        try {
            return aesDecrypt(encryptStr, KEY_LOG_AUDIT);
        } catch (GeneralSecurityException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String aesEncrypt(String content) {
        try {
            return aesEncrypt(content, KEY);
        } catch (GeneralSecurityException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String aesDecrypt(String encryptStr) {
        try {
            return aesDecrypt(encryptStr, KEY);
        } catch (GeneralSecurityException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
