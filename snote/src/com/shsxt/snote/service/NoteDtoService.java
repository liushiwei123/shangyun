package com.shsxt.snote.service;

import java.util.List;

import com.shsxt.snote.dao.NoteDtoDao;
import com.shsxt.snote.vo.NoteDto;

public class NoteDtoService {
	private NoteDtoDao noteDtoDao=new NoteDtoDao();
	public List<NoteDto> countNoteByTypeName(int uid){
		return noteDtoDao.countNoteByTypeName(uid);
	}
	public List<NoteDto> countNoteByPubDate(int uid){
		return noteDtoDao.countNoteByPubDate(uid);
	}
}
