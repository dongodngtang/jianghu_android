/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: EnCoder.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-10
 *
 * Changes (from 2015-10-10)
 * -----------------------------------------------------------------
 * 2015-10-10 创建EnCoder.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 2015-10-10 修改 encryptMD5()方法加密密匙(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加解密
 * 
 * @author leiving
 *
 */
public class EnCoder {
	/**
	 * 
	 * @param string
	 * 			传入需要加密的字符串
	 * @return String
	 * 			返回加密的字符串
	 */
	public static String MD5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}
	
	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	public static String encryptMD5(String inStr) {
		int code = 848;  //密匙
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ code);
		}
		String s = new String(a);
		return s;
	}
}
