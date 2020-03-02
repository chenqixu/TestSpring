package com.cqx.testspring.webservice.common.util.other;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * EncryptUtil
 *
 * @author chenqixu
 */
public class EncryptUtil {
    private static final String PASSWORD_CRYPT_KEY = "__nlbi__";
    private static final String DES = "DES";

    public EncryptUtil() {
    }

    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, securekey, sr);
        return cipher.doFinal(src);
    }

    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, securekey, sr);
        return cipher.doFinal(src);
    }

    public static final String decrypt(String data) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()), "__nlbi__".getBytes()));
        } catch (Exception var2) {
            return null;
        }
    }

    public static final String encrypt(String password) {
        try {
            return byte2hex(encrypt(password.getBytes(), "__nlbi__".getBytes()));
        } catch (Exception var2) {
            return null;
        }
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if (b.length % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        } else {
            byte[] b2 = new byte[b.length / 2];

            for (int n = 0; n < b.length; n += 2) {
                String item = new String(b, n, 2);
                b2[n / 2] = (byte) Integer.parseInt(item, 16);
            }

            return b2;
        }
    }

    public static String strToAsc(String str) {
        String returnStr = "";

        for (int i = 0; i < str.length(); ++i) {
            int asc = str.charAt(i);
            if (i == 0) {
                returnStr = String.valueOf(asc);
            } else {
                returnStr = returnStr + "008008" + asc;
            }
        }

        return returnStr;
    }

    public static String ascToStr(String str) {
        String returnStr = "";
        String[] ascStr = str.split("008008");

        for (int i = 0; i < ascStr.length; ++i) {
            int strInt = Integer.valueOf(ascStr[i]);
            char strs = (char) strInt;
            returnStr = returnStr + strs;
        }

        return returnStr;
    }
}
