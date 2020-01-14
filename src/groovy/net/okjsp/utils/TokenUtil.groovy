package net.okjsp.utils

import java.security.MessageDigest

class TokenUtil {
    static String create(String username, String seed) {
        String dateStr = new Date().format("yyyy-MM-dd'T'HH:mm")
        (username + ":" + hash(username+seed+dateStr)).encodeAsBase64()
    }

    static String hash(String value) {

        String sha1 = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(value.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }

        sha1
    }
}
