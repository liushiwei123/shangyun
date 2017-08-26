package com.shsxt.snote.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.utils.StringUtil;
@WebFilter(urlPatterns="/*")
public class AutoLogin implements Filter{
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		// 获取session 用户信息
		MessageModel messageModel=(MessageModel)request.getSession().getAttribute("userInfo");
		if(messageModel!=null){
			chain.doFilter(request, response);
			return;
		}
		/**
		 * 自动登录
		 */
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0){
			for(Cookie cookie:cookies){
				String cName=cookie.getName();//userInfo  zyy-zyy
				if(!StringUtil.isNullOrEmpty(cName) && cName.equals("userInfo")){
					String cValue=cookie.getValue();
					String [] tempValue=cValue.split("-");
					request.getRequestDispatcher("user?act=login&uname="+tempValue[0]+"&upwd="+tempValue[1]).forward(request, response);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
