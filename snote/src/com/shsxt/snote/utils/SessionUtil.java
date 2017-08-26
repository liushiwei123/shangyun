package com.shsxt.snote.utils;

import javax.servlet.http.HttpServletRequest;

import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.vo.User;

public class SessionUtil {
	public static User queryUserBySession(HttpServletRequest req) {
		MessageModel messageModel = (MessageModel) req.getSession()
				.getAttribute("userInfo");
		User user = null;
		if (null != messageModel) {
			user= (User)messageModel.getResult();
		}
		return user;
	}
}
