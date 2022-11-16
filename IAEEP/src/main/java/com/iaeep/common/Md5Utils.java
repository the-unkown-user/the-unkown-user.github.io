package com.iaeep.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	/***
	 * MD5加码 生成32位md5码
	 */
	//法一
	private static String getMd51(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
                hexValue.append("0");
            }
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
    //法二
	private static String getMd52(String plainText) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

		md5.update(plainText.getBytes());
		byte b[] = md5.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
                i += 256;
            }
			if (i < 16) {
                buf.append("0");
            }
			buf.append(Integer.toHexString(i));
		}		
		return buf.toString();// 32位加密		
		// return buf.toString().substring(8, 24);// 16位的加密

	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	private static String convertMD5(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 'v');
			a[i]=(char) (a[i]^ 'w');
			a[i]= (char) (a[i] ^ 'v');
		}
		String s = new String(a);
		return s;
	}
    //得到加密密码
    public static String getEncryptionPassword(String password){
        String encryptionPassword="";
        encryptionPassword=convertMD5(getMd51(password))+convertMD5(password);
        return encryptionPassword;
    }
	// 测试主函数
/*
	public static void main(String args[]) {
		String s = new String("#@@%@%^%@");
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + getMd51(s));
		System.out.println("MD5后：" + getMd52(s));
		System.out.println("加密的：" + convertMD5(s));
		System.out.println("md5+加密"+convertMD5(getMd51(s)));
		System.out.println("md5+解密"+convertMD5(convertMD5(getMd51(s))));
		System.out.println("解密的：" + convertMD5(convertMD5(s)));
		System.out.println(getEncryptionPassword(s));
	}

 */

}
