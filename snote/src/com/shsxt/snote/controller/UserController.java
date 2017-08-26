package com.shsxt.snote.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.deploy.LoginConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.taglibs.standard.tag.el.sql.UpdateTag;

import com.shsxt.snote.constant.NoteConstant;
import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.service.UserService;
import com.shsxt.snote.utils.JsonUtil;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.User;
@WebServlet(urlPatterns="/user")
public class UserController extends HttpServlet{
	private UserService userService;
	@Override
	public void init() throws ServletException {
		userService=new UserService();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String act=req.getParameter("act");
		if(!StringUtil.isNullOrEmpty(act)){
			if(act.equals("login")){
				login(req, resp);
			}else if(act.equals("logout")){
				logout(req,resp);
			}else if(act.equals("userInfo")){
				userInfo(req,resp);
			}else if(act.equals("queryNickName")){
				queryNickName(req,resp);
			}else if(act.equals("updateUser")){
				updateUser(req,resp);
			}else if(act.equals("upImg")){
				upImg(req,resp);
			}
		}
	}
	/**
	 * 显示图像
	 * @param req
	 * @param resp
	 */
	private void upImg(HttpServletRequest req, HttpServletResponse resp){
		String img=req.getParameter("img");
		File file=new File(req.getServletContext().getRealPath("/WEB-INF/upload/"+img));
		//如果
		if(file.isDirectory()||!file.exists()){
//			System.out.println("文件不存在");
			return;
		}
		 // 获取文件后缀名
	    String type=img.substring(img.lastIndexOf(".")+1);
	    // 设置响应到客户端 内容类型
	    if(type.equalsIgnoreCase("gif")){
	        resp.setContentType("image/gif;charset=utf-8");
	    }
	    if(type.equalsIgnoreCase("jpg")){
	        resp.setContentType("image/jpg;charset=utf-8");
	    }
	    if(type.equalsIgnoreCase("bmp")){
	        resp.setContentType("image/bmp;charset=utf-8");
	    }
	    if(type.equalsIgnoreCase("png")){
	        resp.setContentType("image/png;charset=utf-8");
	    }
	 // 通过流  写出文件
	    BufferedOutputStream bos=null;
	    BufferedInputStream bis=null;
	    try {
	        bos=new BufferedOutputStream(resp.getOutputStream());
	        bis=new BufferedInputStream(new FileInputStream(file));
	        byte[] car=new byte[2048];
	        int len=0;
	        while((len=bis.read(car))!=-1){
	            bos.write(car, 0, len);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }finally{
	        try {
	            bos.flush();
	            if(null!=bis){
	                bis.close();
	            }
	            if(null!=bos){
	                bos.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	/**
	 * 修改用户信息
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		MessageModel messageModel=(MessageModel)req.getSession().getAttribute("userInfo");
		//获取修改之前的用户信息
		User sessionUser=(User)messageModel.getResult();
		//获取条目工厂
		DiskFileItemFactory factory =new DiskFileItemFactory();
		//设置缓冲大小
		factory.setSizeThreshold(5*1024*1024);
		//设置缓冲目录
		String path=req.getServletContext().getRealPath("/WEB-INF");
		//省略判断是否为空
		File temp=new File(path+"/temp");
		if(!temp.exists()){
			temp.mkdirs();
		}
		factory.setRepository(temp);
		//获取上传对象
		ServletFileUpload upload=new ServletFileUpload(factory);
		//设置头信息|编码
		upload.setHeaderEncoding("utf-8");
		upload.setSizeMax(5*1024*1024);
		try {
			//接收表单信息
			List<FileItem> items=upload.parseRequest(req);
			if(items!=null && items.size()>0){
				for(FileItem item:items){
					if(item.isFormField()){//正常表单提交
						if(item.getFieldName().equals("nickName")){//设置昵称
							//设置编码
							String value=item.getString("utf-8");
							sessionUser.setNickName(value);
						}
						if(item.getFieldName().equals("mood")){//设置心情
							//设置编码
							String value=item.getString("utf-8");
							sessionUser.setMood(value);
						}
					}else if(!StringUtil.isNullOrEmpty(item.getFieldName())){
						String fileName=System.currentTimeMillis()+item.getName();
						File uploadFile=new File(path+"/upload/");
						if(!uploadFile.exists()){
							uploadFile.mkdirs();
						}
						item.write(new File(uploadFile,fileName));
						sessionUser.setImg(fileName);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//更新用户数据
		messageModel=userService.updateUser(sessionUser);
		if(messageModel.getResultCode()==NoteConstant.OPTION_FAILED_CODE){
			userInfo(req,resp);
		}
		messageModel.setResult(sessionUser);
		resp.sendRedirect("main.jsp");
	}
	/**
	 * 查询昵称
	 * @param req
	 * @param resp
	 */
	private void queryNickName(HttpServletRequest req, HttpServletResponse resp) {
	    // 查询昵称
	    String nickName=req.getParameter("nickName");
	    JsonUtil.toJson(resp, userService.checkNickName(nickName));
	}
	/**
	 * 登录用户界面
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void userInfo(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("change", "user/user_info.jsp");
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
	/**
	 * 退出
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void logout(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		//先清空session中的值
		req.getSession().setAttribute("userInfo", null);
		//还要清空cookie
		Cookie cookie=new Cookie("userInfo",null);
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		//跳转到login.jsp
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	/**
	 * 登录
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void login(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		String userName=req.getParameter("uname");
		String userPwd=req.getParameter("upwd");
		MessageModel messageModel=userService.userLogin(userName, userPwd);
		if(messageModel.getResultCode()==NoteConstant.OPTION_FAILED_CODE){
			req.setAttribute("msg", messageModel.getMsg());
			req.setAttribute("userName", userName);
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		req.getSession().setAttribute("userInfo", messageModel);
		//是否勾选"记住我"选项
		String rm=req.getParameter("rm");
		if(!StringUtil.isNullOrEmpty(rm) && rm.equals("1")){
			Cookie cookie=new Cookie("userInfo", userName+"-"+userPwd);
			//设置失效时间
			cookie.setMaxAge(60*60*3);
			//设置路径
			cookie.setPath(req.getContextPath());
			resp.addCookie(cookie);
		}
		req.getRequestDispatcher("main?act=mainInfo").forward(req, resp);
	}
}
