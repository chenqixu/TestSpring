package utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class EncryptUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtilTest.class);

    @Test
    public void aesEncrypt() throws GeneralSecurityException, IOException {
        String key = "0563340000000000";
        String content = "{\"verifyCode\":\"056334\",\"userId\":\"9000001\",\"mobile_phone\":\"13900000001\",\"session_sign\":0}";
        String encrypt = EncryptUtil.aesEncrypt(content, key);
        String decrypt = EncryptUtil.aesDecrypt(encrypt, key);
        logger.info(String.format("[content]%s, [encrypt]%s, [decrypt]%s", content, encrypt, decrypt));
    }

    @Test
    public void aesDecrypt() {
        logger.info(String.format("[aesDecrypt]%s", EncryptUtil.aesDecrypt("NBr1/CU8hEvM/IZh3igb69A/LeHbjenA+XB7+sKT9v3gNCRHXOh4dO1l52++Vh3R")));
    }
}