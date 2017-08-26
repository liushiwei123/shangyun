package com.shsxt.snote.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shsxt.snote.service.NoteDtoService;
import com.shsxt.snote.utils.JsonUtil;
import com.shsxt.snote.utils.SessionUtil;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.NoteDto;
import com.shsxt.snote.vo.User;

@WebServlet(urlPatterns="/count")
public class NoteDtoController extends HttpServlet{
	private NoteDtoService noteDtoService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		noteDtoService=new NoteDtoService();
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
			if(act.equals("countNoteByPubDate")){
				countNoteByPubDate(req,resp);
			}else if(act.equals("countNoteByTypeName")){
				countNoteByTypeName(req,resp);
			}
		}
	}
	private void countNoteByTypeName(HttpServletRequest req,HttpServletResponse resp){
		User user=SessionUtil.queryUserBySession(req);
		List<NoteDto> list=noteDtoService.countNoteByTypeName(user.getId());
		JsonUtil.toJson(resp, list);
	}
	private void countNoteByPubDate(HttpServletRequest req,HttpServletResponse resp){
		User user=SessionUtil.queryUserBySession(req);
		List<NoteDto> list=noteDtoService.countNoteByPubDate(user.getId());
		JsonUtil.toJson(resp, list);
	}
}
