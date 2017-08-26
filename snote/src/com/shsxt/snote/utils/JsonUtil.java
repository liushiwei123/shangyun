package com.shsxt.snote.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class JsonUtil {
	public static void toJson(HttpServletResponse resp,Object object){
		PrintWriter pw=null;
		try {
			pw = resp.getWriter();
			pw.println(new Gson().toJson(object));
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
}
