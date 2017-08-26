package com.shsxt.snote.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shsxt.snote.service.NoteService;
import com.shsxt.snote.utils.PageInfo;
import com.shsxt.snote.utils.SessionUtil;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.Note;
import com.shsxt.snote.vo.User;
@WebServlet(urlPatterns="/main")
public class MainController extends HttpServlet{
	private NoteService noteService;
	@Override
	public void init() throws ServletException {
		noteService=new NoteService();
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
			if(act.equals("mainInfo")){
				mainInfo(req,resp);
			}else if(act.equals("queryNoteByParams")){
				queryNoteByParams(req,resp);
			}
		}
	}
	private void queryNoteByParams(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		User user=SessionUtil.queryUserBySession(req);
		String pageNum=req.getParameter("pageNum");
		//获取查询条件
		String content=req.getParameter("content");
		String pubDate=req.getParameter("pubDate");
		String typeName=req.getParameter("typeName");
		if(StringUtil.isNullOrEmpty(pageNum)){
			pageNum="1";
		}
		PageInfo<Note>pageInfo=noteService.queryNoteByParams(user.getId(), Integer.parseInt(pageNum), content, pubDate, typeName);
		req.setAttribute("pageInfo", pageInfo);
		//记住查询条件
		req.setAttribute("content", content);
		req.setAttribute("pubDate", pubDate);
		req.setAttribute("typeName", typeName);
		req.setAttribute("change", "note/note_list.jsp");
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
	/**
	 * 回到主页面
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void mainInfo(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
}
