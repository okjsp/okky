package com.estorm.framework.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class OTPUtiles {
	
	public static String getCurrentTime(String format) {
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(format);
		String currentTime = dayTime.format(new Date(time));
		return currentTime;
	}


	
	
	public static String[] getCreateRSAKeyPair() {
        String[] rsaKeyPair = new String[2];
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.genKeyPair();
            Key publicKey = keyPair.getPublic();
            Key privateKey = keyPair.getPrivate();

            
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(publicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpecX509);
            String strPublicKey = new BASE64Encoder().encode(pubKey.getEncoded());//Base64Util.getEncData(pubKey.getEncoded());

            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            PrivateKey privKey = keyFactory.generatePrivate(keySpecPKCS8);
            String strPrivateKey = new BASE64Encoder().encode(privKey.getEncoded());//Base64Util.getEncData(privKey.getEncoded());

            rsaKeyPair[0] = strPublicKey;
            rsaKeyPair[1] = strPrivateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsaKeyPair;
    }

	public static String getEncryptRSAFromPublicKey(String input, String strPublicKey) {
		String strCipher = null;
		try {
			byte[] baPublicKey = new BASE64Decoder().decodeBuffer(strPublicKey);//Base64Util.getDecData(strPublicKey);
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(baPublicKey));
			Cipher clsCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			clsCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] baCipherData = clsCipher.doFinal(input.getBytes());
			strCipher = new BASE64Encoder().encode(baCipherData);//Base64Util.getEncData(baCipherData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strCipher;
	}
	
	public static String getDecryptRSAFromPrivateKey(String input, String strPrivateKey) {
		String strResult = null;
		try {
			byte[] encrypted = new BASE64Decoder().decodeBuffer(input);//Base64Util.getDecData(input.getBytes());
			byte[] baPrivateKey = new BASE64Decoder().decodeBuffer(strPrivateKey);//Base64Util.getDecData(strPrivateKey.getBytes());
			PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(baPrivateKey));
			Cipher clsCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			clsCipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] baData = clsCipher.doFinal(encrypted);
			strResult = new String(baData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}
	
	public static String getEncryptRSAFromPrivateKey(String input, String strPrivateKey) {
		String strCipher = null;
		try {
			byte[] baPrivateKey = new BASE64Decoder().decodeBuffer(strPrivateKey);//Base64Util.getDecData(strPrivateKey);
			PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(baPrivateKey));
			Cipher clsCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			clsCipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] baCipherData = clsCipher.doFinal(input.getBytes());
			strCipher = new BASE64Encoder().encode(baCipherData);//Base64Util.getEncData(baCipherData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strCipher;
	}
	
	public static String getDecryptRSAFromPublicKey(String input, String strPublicKey) {
		String strResult = null;
		try {
			byte[] encrypted = new BASE64Decoder().decodeBuffer(input);//Base64Util.getDecData(input.getBytes());
			byte[] baPublicKey = new BASE64Decoder().decodeBuffer(strPublicKey);//Base64Util.getDecData(strPublicKey.getBytes());
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(baPublicKey));
			Cipher clsCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			clsCipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] baData = clsCipher.doFinal(encrypted);
			strResult = new String(baData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}
	
	public static String getEncryptAES(String strTarget, String strKey) {
		String strRet = null;
		byte[] key = strKey.getBytes();
		String strIV = strKey;
		if ( key == null || strIV == null ) return null;
		try {
			SecretKey secureKey = new SecretKeySpec(key, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(strIV.getBytes()));
			byte[] encrypted = c.doFinal(strTarget.getBytes("UTF-8"));
			strRet = new BASE64Encoder().encode(encrypted);//Base64Util.getEncData(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}
	
	public static String getDecryptAES(String encrypted, String strKey) {
		String strRet = null;
		byte[] key = strKey.getBytes();
		String strIV = strKey;
		if ( key == null || strIV == null ) return null;
		try {
			SecretKey secureKey = new SecretKeySpec(key, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(strIV.getBytes("UTF-8")));
			byte[] byteStr = new BASE64Decoder().decodeBuffer(encrypted);//Base64Util.getDecData(encrypted);
			strRet = new String(c.doFinal(byteStr),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}
		
}
