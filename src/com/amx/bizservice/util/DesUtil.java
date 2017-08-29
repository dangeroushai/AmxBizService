package com.amx.bizservice.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.util.Base64Utils;



public class DesUtil {
	private static final String DES = "DES";
	private static final String ENCODE = "GBK";
	private static final String DEFAULT_KEY = "AIMANXING";
	
	
	public static String encrypt(String src) throws Exception{
		//可信任的随机数源
		SecureRandom sr = new SecureRandom();
		
		DESKeySpec keySpec = new DESKeySpec(DEFAULT_KEY.getBytes(ENCODE));
		
		//创建密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		//把DESKeySpec转换成SecretKey对象
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		
		Cipher cipher = Cipher.getInstance(DES);
		//用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
		
		return Base64Utils.encodeToString(cipher.doFinal(src.getBytes(ENCODE)));
	}
	
	public static String decrypt(String src) throws Exception{
		//可信任的随机数源
		SecureRandom sr = new SecureRandom();
		
		DESKeySpec keySpec = new DESKeySpec(DEFAULT_KEY.getBytes(ENCODE));
		
		//创建密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		//把DESKeySpec转换成SecretKey对象
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		
		Cipher cipher = Cipher.getInstance(DES);
		
		//用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
		
		return new String (cipher.doFinal(Base64Utils.decodeFromString(src)), ENCODE);
	}
}
