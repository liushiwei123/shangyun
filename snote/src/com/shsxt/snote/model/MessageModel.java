package com.shsxt.snote.model;

import com.shsxt.snote.constant.NoteConstant;

/**
 * 结果信息类，用于返回信息
 * @author Administrator
 *
 */
public class MessageModel {
	private Integer resultCode=NoteConstant.OPTION_SUCCESS_CODE;
	private String msg=NoteConstant.OPTION_SUCCESS_MSG;
	private Object result;
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}
