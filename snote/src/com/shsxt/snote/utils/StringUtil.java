package com.shsxt.snote.utils;
/**
 * 该类用于判断字符串为不为空
 * @author Administrator
 *
 */
public class StringUtil {
	public static boolean isNullOrEmpty(String str){
		if(str==null|| str.trim().equals("")){
			return true;
		}
		return false;
	}
}
