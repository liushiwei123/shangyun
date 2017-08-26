package com.shsxt.snote.service;

import java.util.List;

import com.shsxt.snote.constant.NoteConstant;
import com.shsxt.snote.dao.NoteDao;
import com.shsxt.snote.dao.NoteTypeDao;
import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.NoteType;

public class NoteTypeService {
	private NoteTypeDao noteTypeDao=new NoteTypeDao();
	private NoteDao noteDao=new NoteDao();
	public List<NoteType>queryNoteTypeByUid(Integer uid){
		return noteTypeDao.queryNoteTypeByUid(uid);
	}
	/**
	 * 修改和新增方法
	 * @param noteType
	 * @return
	 */
	public MessageModel saveOrUpdateNoteType(NoteType noteType){
		MessageModel messageModel=new MessageModel();
		if(StringUtil.isNullOrEmpty(noteType.getTypeName())){
			messageModel.setMsg("类别名称不能为空！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		int result=0;
		
		if(noteType.getId()!=null){
			result=noteTypeDao.updateNoteType(noteType);
			messageModel.setResult(noteType);
		}else{
			result=noteTypeDao.addNoteType(noteType);
			NoteType noteType2=noteTypeDao.queryNoteTypeByTypeNameUid(noteType.getUserId(), noteType.getTypeName());
			messageModel.setResult(noteType2);
		}
		//判断SQL执行成功与否
		if(result<1){
			messageModel.setMsg(NoteConstant.OPTION_FAILED_MSG);
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		return messageModel;
		
	}
	/**
	 * 较验类型名称的唯一性
	 * @param typeName
	 * @param uid
	 * @return
	 */
	public MessageModel checkNoteTypeByTypeNameUid(String typeName,Integer uid){
		MessageModel messageModel=new MessageModel();
		if(StringUtil.isNullOrEmpty(typeName)){
			messageModel.setMsg("类型名称不能为空！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		NoteType noteType=noteTypeDao.queryNoteTypeByTypeNameUid(uid, typeName);
		if(noteType!=null){
			messageModel.setMsg("该类型已经存在!");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		return messageModel;
	}
	public MessageModel deleteNoteTypeById(Integer id){
		MessageModel messageModel=new MessageModel();
		//待删除的记录存在与否
		NoteType noteType=noteTypeDao.queryNoteTypeById(id);
		if(noteType==null){
			messageModel.setMsg("待删除的记录不存在！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		//查询该类别下是否存在云记
		long count=noteDao.queryNoteTypeCountById(id);
		if(count>=1){
			messageModel.setMsg("该云记类别下存在云记，不能删除");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		int result=noteTypeDao.deleteNoteType(id);
		if(result<1){
			messageModel.setMsg(NoteConstant.OPTION_FAILED_MSG);
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}
		return messageModel;
	}
}
