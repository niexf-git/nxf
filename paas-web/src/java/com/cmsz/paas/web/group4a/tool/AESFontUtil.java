package com.cmsz.paas.web.group4a.tool;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.codec.binary.Base64;

/**
 * @author jiayz
 * @date 2019年4月22日
 */
public class AESFontUtil {
	
	 //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
    private static String KEY = "dvyYRQlnPRCMdQSe";
 
    private static String IV = "face0123456789ai";
 
 
    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
 
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
            int blockSize = cipher.getBlockSize();
 
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
 
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
 
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
 
            return new Base64().encodeToString(encrypted);
 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
 
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data){
    	try {
    		return encrypt(data, KEY, IV);
		} catch (Exception e) {
		}
        return "";
    }
 
    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV).trim();
    }
 
 
 
    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {
    	System.out.println(desEncrypt("4TRhQ2YFRpW6nXYfaNq7Bg=="));
    	 JSONObject json = JSONObject.fromObject("{'username':'gaoa4aaaa', 'password':'haha', 'nested':{'f':4, 'ss':33}}");    
         XMLSerializer xmlSerializer = new XMLSerializer();  
         xmlSerializer.setTypeHintsEnabled( false );      
         xmlSerializer.setRootName("body" );     
         String xml = xmlSerializer.write( json );     
         System.out.println(xml); 
    }


}
