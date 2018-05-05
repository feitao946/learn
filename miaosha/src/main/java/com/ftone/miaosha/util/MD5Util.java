package com.ftone.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	public static final String  salt="1a2b3c4d";
	
	public static String MD5(String str){
		return DigestUtils.md5Hex(str);
	}
	
	public static String inputpassToFormpass(String inputpass){
		String str=""+salt.charAt(0)+salt.charAt(2)+inputpass+salt.charAt(4)+salt.charAt(5);
		System.out.println(str);
		return MD5(str);
		
	}
	
	public static String formpassToDBpass(String formpass,String DBSalt){
		String str=DBSalt.charAt(0)+DBSalt.charAt(2)+formpass+DBSalt.charAt(4)+DBSalt.charAt(5);
		return MD5(str);
	}
	
	public static String inputpassToDBpass(String inputpass,String DBSalt){
		String formPass = inputpassToFormpass(inputpass);
		String dbPass = formpassToDBpass(formPass,DBSalt);
		return dbPass;
	}
	public static void main(String[] args) {
		System.out.println(inputpassToFormpass("123456"));;
		System.out.println(formpassToDBpass(inputpassToFormpass("123456"), "1a2b3c4d"));
		System.out.println(inputpassToDBpass("123456", "1a2b3c4d"));
	}
}
/*f2266f92e31d032983464ea05d6fb4e4
fc01546a114674577b10f14c9c91d7b2
75f4a36ff953093f175d9fe84915cefe*/
