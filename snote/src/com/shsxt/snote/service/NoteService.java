package com.shsxt.snote.service;

import com.shsxt.snote.constant.NoteConstant;
import com.shsxt.snote.dao.NoteDao;
import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.utils.PageInfo;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.Note;

public class NoteService {
	private NoteDao noteDao=new NoteDao();
	/**
	 * 新增|修改
	 * @param note
	 * @return
	 */
	public MessageModel saveOrUpdateNote(Note note){
		MessageModel messageModel=new MessageModel();
		//判断选择类别与否
		if(note.getTypeId()==null || note.getTypeId()==-1){
			messageModel.setMsg("请选择类别！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		//标题不能为空
		if(StringUtil.isNullOrEmpty(note.getTitle())){
			messageModel.setMsg("请填写标题!");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		if(StringUtil.isNullOrEmpty(note.getContent())){
			messageModel.setMsg("请填写内容！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		int result;
		if(note.getId()==null){//为新增
			result=noteDao.addNote(note);
		}else{
			if(null==noteDao.queryNoteById(note.getId())){
				messageModel.setMsg("待更新记录不存在！");
				messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
				return messageModel;
			}
			result=noteDao.updateNote(note);
		}
		if(result<1){
			messageModel.setMsg(NoteConstant.OPTION_FAILED_MSG);
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		return messageModel;
	}
	/**
	 * 只是作中转
	 * @param uid
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Note> queryNoteByParams(int uid,int pageNum,String content,String pubDate,String typeName){
		return noteDao.queryNoteByParams(uid, pageNum, content, pubDate, typeName);
	}
	/**
	 * 只是作中转
	 * @param uid
	 * @param pageNum
	 * @return
	 */
	public Note queryNoteById(int id){
		return noteDao.queryNoteById(id);
	}
	/**
	 * 删除云记
	 * @param id
	 * @return
	 */
	public MessageModel delNote(int id){
		MessageModel messageModel=new MessageModel();
		if(null==noteDao.queryNoteById(id)){
			messageModel.setMsg("删除的记录不存在！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		int result=noteDao.delNote(id);
		if(result<1){
			messageModel.setMsg(NoteConstant.OPTION_FAILED_MSG);
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		return messageModel;
	}

}
