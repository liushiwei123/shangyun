package com.shsxt.snote.dao;

import java.util.List;

import com.shsxt.snote.vo.NoteDto;

public class NoteDtoDao extends BaseDao<NoteDto>{
	/**
	 * 通过类型ID分组
	 * @param uid
	 * @return
	 */
	public List<NoteDto> countNoteByTypeName(int uid){
		String sql="SELECT nt.type_name as typeName,count(nt.id) as count"
				+" from t_note t LEFT JOIN t_note_type nt"
				+" ON t.type_id=nt.id"
				+" where nt.user_id=?"
				+" GROUP BY(nt.id)";
		return queryRows(sql, NoteDto.class, uid);
	}
	public List<NoteDto> countNoteByPubDate(int uid){
		String sql="SELECT DATE_FORMAT(n.pub_date,'%y年%m月') as pubDate,count(nt.id) as count"
				+" from t_note n LEFT JOIN t_note_type nt"
				+" ON n.type_id=nt.id"
				+" where nt.user_id=?"
				+" GROUP BY(DATE_FORMAT(n.pub_date,'%y年%m月'))";
		return queryRows(sql, NoteDto.class, uid);
	}
}
