package utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtilTest.class);

    @Test
    public void aesEncrypt() {
        String content = "{username: \"admin\", password: \"123456\"}";
        String encrypt = EncryptUtil.aesEncrypt(content);
        String decrypt = EncryptUtil.aesDecrypt(encrypt);
        logger.info(String.format("[content]%s, [encrypt]%s, [decrypt]%s", content, encrypt, decrypt));
    }

    @Test
    public void aesDecrypt() {
        logger.info(String.format("[aesDecrypt]%s", EncryptUtil.aesDecrypt("NBr1/CU8hEvM/IZh3igb69A/LeHbjenA+XB7+sKT9v3gNCRHXOh4dO1l52++Vh3R")));
    }
}