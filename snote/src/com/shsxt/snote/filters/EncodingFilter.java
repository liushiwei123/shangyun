package com.shsxt.snote.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.soap.InitParam;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
@WebFilter(urlPatterns="/*",initParams={@WebInitParam(name="charset",value="utf-8")})
public class EncodingFilter implements Filter {
	private FilterConfig filterConfig;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		/**
		 * Tomcat 版本
		 * 		1)7及以下  ISO-8859-1
		 * 		2)8及以上    UTF-8
		 * 若服务器是7及以下
		 * 		此时需要判断是GET  POST请求
		 * 		GET:继承HttpServletRequestWrapper  重写getParameter()方法
		 * 		POST:UTF-8
		 */
		//Apache tomcat/7.0.9  Apache tomcat/8.0.9  
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		//静态资源需要放行
		String uri=request.getRequestURI();
		if(uri.contains("/statics")){
			chain.doFilter(request, response);
			return;
         }
		String serverInfo=filterConfig.getServletContext().getServerInfo();
//		System.out.println("serverInfo:"+serverInfo);
		Pattern pattern=Pattern.compile("cat/(\\d)");
		Matcher matcher=pattern.matcher(serverInfo);
		if(matcher.find()){
			int version=Integer.parseInt(matcher.group(1));
//			System.out.println(version);
			//获取当前配置中的编码格式
			String charset=filterConfig.getInitParameter("charset");
//			System.out.println(charset);
			request.setCharacterEncoding(charset);
			response.setCharacterEncoding(charset);
			if(version>=8){
				chain.doFilter(request, response);
				return;
			}
			//若没走if则证明此时的服务版本为7-
			String method=request.getMethod();
//			System.out.println("请求方式:"+method);
			if(method.equalsIgnoreCase("POST")){
				chain.doFilter(request, response);
				return;
			}
			//7-  GET
			chain.doFilter(new EncodeWrapper(request), response);
		}

	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig=filterConfig;
	}
}
class EncodeWrapper extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	public EncodeWrapper(HttpServletRequest request) {
		super(request);
		this.request=request;
	}
	@Override
	public String getParameter(String name) {
		String result=null;
		String value=request.getParameter(name);
		if(value==null){
			return "";
		}
		try {
			result=new String(value.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
