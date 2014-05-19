package com.upyun.sdk.utils;

import java.io.IOException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class SecurityUtil {

	private static Logger logger = Logger.getLogger(SecurityUtil.class);
	private static final String ALGORITHM = "DESede";
	private static final String sourcekey = "upyun5f9jhews8";
	private static SecretKey secretKey = null;

	private static SecretKey initSecurite() {
		try {
			if (secretKey == null) {
				SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
				String key = base64Encode(sourcekey);
				DESedeKeySpec keySpec = new DESedeKeySpec(key.getBytes());
				secretKey = factory.generateSecret(keySpec);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return secretKey;
	}

	public static String encrypt(String str) {
		if(str == null) {
			str = "";
		}
		String encryptedString = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, initSecurite());
			byte[] encrypted = cipher.doFinal(str.getBytes());
			encryptedString = base64Encode(encrypted);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return encryptedString;
	}

	public static String decrypt(String encryptedString) {
		if(encryptedString == null) {
			encryptedString = "";
		}
		
		String decryptedString = "";
		try {
			byte[] encrypted = base64DecodeBuffer(encryptedString);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, initSecurite());
			byte[] decrypted = cipher.doFinal(encrypted);
			decryptedString = new String(decrypted);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return decryptedString;
	}

	public static void main(String[] args) throws IOException {
		String encrypt = "";
		
		System.out.print("Please input the words you want to encrypte:");
		Scanner scanner = new Scanner(System.in);
		String words =scanner.next();
		encrypt = SecurityUtil.encrypt(words);
		
		logger.info("encryted string :"+encrypt);
		logger.info("decrypt string:"+SecurityUtil.decrypt(encrypt));
	}

    private static String base64Encode(String str) {
		Base64 base64 = new Base64();
		byte[] data = base64.encode(str.getBytes());
		return new String(data);
	}
    
    private static String base64Encode(byte[] d) {
		Base64 base64 = new Base64();
		byte[] data = base64.encode(d);
		return new String(data);
	}

    private static byte[] base64DecodeBuffer(String str) {
		Base64 base64 = new Base64();
		byte[] data = base64.decode(str.getBytes());
		return data;
	}
}
