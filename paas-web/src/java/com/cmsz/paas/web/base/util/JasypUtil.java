package com.cmsz.paas.web.base.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasypUtil {
	private static String KEY = "salt"; //密钥
    public static void main(String[] args) {
        String text2 = decrypt("CsFUGA7e41ZjNp+GwPwd5K7i2Kr9A2Ol");
        System.out.println("解密内容：" + text2);
    }
     
    /** 
     * 加密 
     * @param text 明文 
     * @return     密文 
     */  
    public static String encrypt(String text) {  
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();  
        encryptor.setAlgorithm("PBEWithMD5AndDES"); 
        encryptor.setPassword(KEY);  
        return encryptor.encrypt(text);  
    }  
       
    /** 
     * 解密 
     * @param ciphertext 密文 
     * @return           明文 
     */  
    public static String decrypt(String ciphertext) {  
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();  
        encryptor.setAlgorithm("PBEWithMD5AndDES"); 
        encryptor.setPassword(KEY);  
        return encryptor.decrypt(ciphertext);  
    } 
}
