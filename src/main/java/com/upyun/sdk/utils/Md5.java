
package com.upyun.sdk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class Md5 {
	private static final Logger logger = Logger.getLogger(Md5.class);
	
	/**
	 * MD5加密
	 * 
	 * @param plainText
	 * @return
	 */
	public static String MD5(String plainText) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

		} catch (NoSuchAlgorithmException e) {
			LogUtil.exception(logger, e);
		}
		return buf.toString();
	}
}
