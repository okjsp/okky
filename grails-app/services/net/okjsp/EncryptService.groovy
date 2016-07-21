package net.okjsp

@grails.transaction.Transactional

import grails.converters.JSON
import grails.transaction.Transactional

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import java.security.Key

class EncryptService {

    def grailsApplication

    boolean encryption = true

    SecretKeySpec skeySpec = null
    IvParameterSpec ivParameterSpec = null
    
    public byte [] encryptWithCRC(byte [] value) {

        if(!encryption) return value

        def mil = new Date().time
        def encrypted = new String(encrypt(value))
        def crc = "${encrypted}${salt}${mil}".encodeAsSHA256()

        def result = "${mil}:${encrypted}:${crc}".encodeAsBase64()

        result.bytes
    }

    public def decryptWithCRC(byte [] value) {

        if(!encryption) return JSON.parse(new String(value))

        String [] encryptedWithCRC = new String(new String(value).decodeBase64()).split(":")

        String mil = encryptedWithCRC[0]
        String encrypted = encryptedWithCRC[1]
        String crc = encryptedWithCRC[2]

        String newCRC = "${encrypted}${salt}${mil}".encodeAsSHA256()

        if(!crc.equals(newCRC)) {
            println "\n==== Request CRC Error : ${crc} == ${newCRC}\n"
            throw new Exception("CRC Error")
        }

        def json = JSON.parse(new String(decrypt(encrypted.bytes)))

        json
    }

    public byte [] encrypt(byte [] value) {
        try {

            if(!encryption) return value

            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE)

            byte[] encrypted = cipher.doFinal(value)

            return encrypted.encodeAsBase64().bytes

        } catch (Exception ex) {
            ex.printStackTrace()
        }
        return null
    }

    public byte [] decrypt(byte [] encrypted) {
        try {

            if(!encryption) return encrypted

            Cipher cipher = getCipher(Cipher.DECRYPT_MODE)

            byte[] original = cipher.doFinal(encrypted.decodeBase64())

            return original

        } catch (Exception ex) {
            ex.printStackTrace()
        }
        return null
    }

    public Cipher getCipher(int mode) {
        
        if(skeySpec == null) {
            init()
        }
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(mode, skeySpec, ivParameterSpec)

        cipher
    }
    
    private void init() {
        
        String key1 = grailsApplication.config.grails.encrypt.key
        String salt = grailsApplication.config.grails.encrypt.salt
        
        synchronized(EncryptService.class) {
            
            if (skeySpec == null) {
                
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                PBEKeySpec pbeKeySpec = new PBEKeySpec(key1.chars, salt.getBytes("UTF-8"), 1000, 256)
                Key secretKey = factory.generateSecret(pbeKeySpec)

                byte[] key = new byte[16]
                byte[] iv = new byte[16]

                System.arraycopy(secretKey.encoded, 0, key, 0, 16)
                System.arraycopy(secretKey.encoded, 16, iv, 0, 16)

                skeySpec = new SecretKeySpec(key, "AES")
                ivParameterSpec = new IvParameterSpec(iv)
            }
        }
    }

}