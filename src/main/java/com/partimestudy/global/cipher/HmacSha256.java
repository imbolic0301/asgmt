package com.partimestudy.global.cipher;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

 class HmacSha256 {

    private final String DEFAULT_ENCODING = "UTF-8";
    private final String HMAC_SHA256 = "HmacSHA256";

    public String hmacSha256HexBase64(String data, String key) {
        return base64(hex(hmacSha256(data, key)));
    }

    public String hmacSha256Hex(String data, String key) {
        return hexString(hmacSha256(data, key));
    }

    public String hmacSha256Base64(String data, String key) {
        return base64(hmacSha256(data, key));
    }


    private byte[] hex(byte[] bytes) {
        if (bytes == null) return null;

        try {
            return hexString(bytes).getBytes(DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private String hexString(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }

    private String base64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private byte[] hmacSha256(byte[] data, byte[] key) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(key, HMAC_SHA256));
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] hmacSha256(String data, String key) {
        try {
            return hmacSha256(data, key.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] hmacSha256(String data, byte[] key) {
        return hmacSha256(data.getBytes(), key);
    }
}
