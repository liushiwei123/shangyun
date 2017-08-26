package com.shsxt.snote.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.shsxt.snote.model.MessageModel;
import com.shsxt.snote.service.NoteTypeService;
import com.shsxt.snote.vo.NoteType;

public class TestNoteTypeService {
	private NoteTypeService noteTypeService;
	@Before
	public void init(){
		noteTypeService=new NoteTypeService();
	}

	@Test
	public void testSaveOrUpdateNoteType() {
		NoteType noteType=new NoteType();
		noteType.setId(18);
		noteType.setTypeName("lsw");
		noteType.setUserId(2);
		MessageModel messageModel=noteTypeService.saveOrUpdateNoteType(noteType);
		if(messageModel.getResultCode()==200){
			NoteType noteType2=(NoteType)messageModel.getResult();
			System.out.println(noteType2.getId()+":"+noteType2.getTypeName());
		}else{
			System.out.println("错误!");
		}
	}

}
