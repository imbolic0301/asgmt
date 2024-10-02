package com.partimestudy.global.cipher;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class Aes128 {

    private static final String PASSWORD_PARAMETER_SECRET_KEY = "weJiSEvR5yAC5ftB";
    private static final String ALGORITHM_NAME = "AES";
    private static final String CIPHER_INSTANCE_TYPE = "AES/ECB/PKCS5Padding";
    private final SecretKeySpec secretKeySpec;
    private final Cipher cipher;

    public Aes128(Charset encodingType) throws NoSuchPaddingException, NoSuchAlgorithmException {
        secretKeySpec = new SecretKeySpec(PASSWORD_PARAMETER_SECRET_KEY.getBytes(encodingType), ALGORITHM_NAME);
        cipher = Cipher.getInstance(CIPHER_INSTANCE_TYPE);
    }


    public String encrypt(String toBeEncrypt) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
        return Base64.encodeBase64String(encrypted);
    }

    public String decrypt(String encrypted) throws InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(decryptedBytes);
    }

}
