package com.partimestudy.global.cipher;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


/**
 * 암호화 처리 모듈
 */
@Component
@RequiredArgsConstructor
public class CipherCore {

    private Aes128 aes128;
    private HmacSha256 hmacSha256;

    @PostConstruct
    private void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.aes128 = new Aes128(StandardCharsets.UTF_8);
        this.hmacSha256 = new HmacSha256();
    }


    public String encryptAes128(String plainText) throws Exception {
        return aes128.encrypt(plainText);
    }

    public String decryptAes128(String encrypted) throws Exception {
        return aes128.decrypt(encrypted);
    }

    public String encryptSha256(String plainText, String key) throws Exception {
        return hmacSha256.hmacSha256Base64(plainText, key);
    }

}
