package com.shsxt.snote.service;

import com.shsxt.snote.constant.NoteConstant;
import com.shsxt.snote.dao.UserDao;
import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.utils.Md5Util;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.User;

public class UserService {
	private UserDao userDao=new UserDao();
	public MessageModel userLogin(String userName,String userPwd){
		MessageModel messageModel=new MessageModel();
		if(StringUtil.isNullOrEmpty(userName)){
			messageModel.setMsg("用户名不能为空！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		if(StringUtil.isNullOrEmpty(userPwd)){
			messageModel.setMsg("密码不能为空！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		//先查询用户名是否存在
		User user=userDao.queryUserByUserName(userName);
		if(user==null){
			messageModel.setMsg("该用户不存在！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		//判断密码是否正确		先使用Md5加密再进行比较
		if(!Md5Util.encode(userPwd).equals(user.getUserPwd())){
			messageModel.setMsg("密码不正确！");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		}else{
			messageModel.setResult(user);
		}
		return messageModel;
	}
	/**
	 * 昵称唯一性检查
	 * @param nickName
	 * @return
	 */
	public MessageModel checkNickName(String nickName){
	    MessageModel messageModel=new MessageModel();
	    long count= userDao.queryNickName(nickName);
	    if(count>0){
	        messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
	        messageModel.setMsg("该昵称已存在，请更换昵称!");
	    }
	    return messageModel;
	}
	/**
	 *修改用户信息
	 * @param user
	 * @return
	 */
	public MessageModel updateUser(User user){
	     MessageModel messageModel=new MessageModel();
	     if(StringUtil.isNullOrEmpty(user.getNickName())){
	    	  messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
		      messageModel.setMsg("昵称不能为空！");
		      return messageModel;
	     }
	     int result= userDao.updateUser(user);
	     if(result<1){
	         messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
	         messageModel.setMsg(NoteConstant.OPTION_FAILED_MSG);
	     }
	     return messageModel;
	}
}
