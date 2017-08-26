package com.shsxt.snote.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shsxt.snote.constant.NoteConstant;
import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.service.NoteService;
import com.shsxt.snote.utils.JsonUtil;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.Note;
@WebServlet(urlPatterns="/note")
public class NoteController extends HttpServlet{
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
			if(act.equals("toAddNotePage")){
				toAddNotePage(req,resp);
			}else if(act.equals("saveOrUpdateNote")){
				saveOrUpdateNote(req,resp);
			}else if(act.equals("queryNoteById")){
				queryNoteById(req,resp);
			}else if(act.equals("delNote")){
				delNote(req,resp);
			}
		}
				
	}
	private void delNote(HttpServletRequest req,HttpServletResponse resp){
		String id=req.getParameter("id");
		MessageModel messageModel=noteService.delNote(Integer.parseInt(id));
		JsonUtil.toJson(resp, messageModel);
	}
	/**
	 * 通过ID进行查询
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void queryNoteById(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		String  id=req.getParameter("id");
		Note note=null;
		if(!StringUtil.isNullOrEmpty(id)){
			note=noteService.queryNoteById(Integer.parseInt(id));
		}
		req.setAttribute("note", note);
		req.setAttribute("change", "note/note_view.jsp");
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
	private void saveOrUpdateNote(HttpServletRequest req,HttpServletResponse resp){
		MessageModel messageModel=new MessageModel();
		String title=req.getParameter("title");
		String content=req.getParameter("content");
		String typeId=req.getParameter("typeId");
		String id=req.getParameter("id");
		Note note=new Note();
		note.setTitle(title);
		note.setContent(content);
		if(!StringUtil.isNullOrEmpty(id)){
			note.setId(Integer.parseInt(id));
		}
		if(StringUtil.isNullOrEmpty(typeId)){
			messageModel.setMsg("请选择云记类型");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}else{
			note.setTypeId(Integer.parseInt(typeId));
			messageModel=noteService.saveOrUpdateNote(note);
		}
		JsonUtil.toJson(resp, messageModel);
	}
	/**
	 * 新增|修改页面
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toAddNotePage(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		String id=req.getParameter("id");
		/**
		 * 若id为空：则为新增页面；若id不为空，则为修改页面，此时需要回填数据
		 */
		Note note=null;
		if(!StringUtil.isNullOrEmpty(id)){
			note=noteService.queryNoteById(Integer.parseInt(id));
		}
		//将页面赋值给change
		req.setAttribute("change", "note/add_update_note.jsp");
		req.setAttribute("note", note);
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
}
