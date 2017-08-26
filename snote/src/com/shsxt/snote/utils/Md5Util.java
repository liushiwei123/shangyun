package com.shsxt.snote.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;

import com.shsxt.snote.vo.User;

public class Md5Util {
	public static String encode(String msg){
		try {
			MessageDigest messageDigest=MessageDigest.getInstance("md5");
			return Base64.encodeBase64String(messageDigest.digest(msg.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		/*User user=new User();
		user.setUserName("zyy");
		String userPwd="zyy";
		user.setUserPwd(Md5Util.encode(userPwd));*/
		System.out.println(Md5Util.encode("zyy"));
	}
}
