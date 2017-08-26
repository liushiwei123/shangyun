package com.shsxt.snote.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shsxt.snote.constant.NoteConstant;
import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.service.NoteTypeService;
import com.shsxt.snote.utils.JsonUtil;
import com.shsxt.snote.utils.SessionUtil;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.NoteType;
import com.shsxt.snote.vo.User;

@WebServlet(urlPatterns = "/noteType")
public class NoteTypeController extends HttpServlet {
	private NoteTypeService noteTypeService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		noteTypeService = new NoteTypeService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String act = req.getParameter("act");
		if (!StringUtil.isNullOrEmpty(act)) {
			if (act.equals("noteTypeInfo")) {
				noteTypeInfo(req, resp);
			}else if(act.equals("savaOrUpdateNoteType")){
				savaOrUpdateNoteType(req,resp);
			}else if(act.equals("checkNoteType")){
				checkNoteType(req,resp);
			}else if(act.equals("delNoteType")){
				delNoteType(req,resp);
			}else if(act.equals("queryNoteTypeByUid")){
				queryNoteTypeByUid(req,resp);
			}
		}
	}
	/**
	 * 加载云记中类别下拉框
	 * @param req
	 * @param resp
	 */
	private void queryNoteTypeByUid(HttpServletRequest req,HttpServletResponse resp) {
		User user=SessionUtil.queryUserBySession(req);
		List<NoteType> noteTypes=noteTypeService.queryNoteTypeByUid(user.getId());
		JsonUtil.toJson(resp, noteTypes);
	}
	/**
	 * 删除云记类别
	 * @param req
	 * @param resp
	 */
	private void delNoteType(HttpServletRequest req,HttpServletResponse resp){
		MessageModel messageModel=new MessageModel();
		String id=req.getParameter("id");
		if(!StringUtil.isNullOrEmpty(id)){
			messageModel=noteTypeService.deleteNoteTypeById(Integer.parseInt(id));
		}else{
			messageModel.setMsg("请选择删除记录");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		JsonUtil.toJson(resp, messageModel);
	}
	/**
	 * 类别名称唯一性较验
	 * @param req
	 * @param resp
	 */
	private void checkNoteType(HttpServletRequest req,HttpServletResponse resp){
		String typeName=req.getParameter("typeName");
		User user=SessionUtil.queryUserBySession(req);
		MessageModel messageModel=new MessageModel();
		if(user!=null){
			messageModel=noteTypeService.checkNoteTypeByTypeNameUid(typeName, user.getId());
		}else{
			messageModel.setMsg("未登录！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		JsonUtil.toJson(resp, messageModel);
	}
	/**
	 * 
	 * @param req
	 * @param resp
	 */
	private void savaOrUpdateNoteType(HttpServletRequest req,HttpServletResponse resp){
		User user=SessionUtil.queryUserBySession(req);
		MessageModel messageModel=new MessageModel();
		//先获取
		String id=req.getParameter("id");
		String typeName=req.getParameter("typeName");
		if(null!=user){
			NoteType noteType=new NoteType();
			if(!StringUtil.isNullOrEmpty(id)){
				noteType.setId(Integer.parseInt(id));
			}
			noteType.setTypeName(typeName);
			noteType.setUserId(user.getId());
			messageModel=noteTypeService.saveOrUpdateNoteType(noteType);
		}else{
			messageModel.setMsg("该用户未登录！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		JsonUtil.toJson(resp, messageModel);
		
	}

	/**
	 * 加载类别
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void noteTypeInfo(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		User user=SessionUtil.queryUserBySession(req);
		List<NoteType>noteTypes=null;
		if(user!=null){
			noteTypes=noteTypeService.queryNoteTypeByUid(user.getId());
		}
		req.setAttribute("noteTypes", noteTypes);
		req.setAttribute("change","noteType/note_type.jsp");
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
}
