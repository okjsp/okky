package net.okjsp.utils

import java.security.MessageDigest

class TokenUtil {
    static String create(String username, String seed) {
        String dateStr = new Date().format("yyyy-MM-dd'T'HH:mm")
        String original = username+seed+dateStr
        String hashed = hash(username+seed+dateStr)
        String merged = username + ":" + hashed
        String encoded = (merged).encodeAsBase64()

        println original
        println hashed
        println merged
        println encoded

        encoded
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
