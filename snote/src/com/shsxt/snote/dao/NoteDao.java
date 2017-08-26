package com.shsxt.snote.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shsxt.snote.utils.PageInfo;
import com.shsxt.snote.utils.StringUtil;
import com.shsxt.snote.vo.Note;

public class NoteDao extends BaseDao<Note>{
	/**
	 * 删除云记类别时：需要判断该类别下是否存在云记
	 * @param id
	 * @return
	 */
	public Long queryNoteTypeCountById(Integer id){
		String sql="select count(1) from t_note where type_id=?";
		return (long)querySingleValue(sql, id);
	}
	/**
	 * 新增云记
	 * @param note
	 * @return
	 */
	public int addNote(Note note){
		String sql="insert into t_note (title,type_id,pub_date,content,update_date) values(?,?,?,?,?)";
		return executeUpdate(sql, note.getTitle(),note.getTypeId(),new Date(),note.getContent(),new Date());
	}
	/**
	 * 通过分页查询
	 * @param uid
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Note>queryNoteByParams(int uid,int pageNum,String content,String pubDate,String typeName){
		String sqlQuery="SELECT n.title,DATE_FORMAT(n.pub_date,'%Y年%m月%d日') as pubDateStr,n.id"
				+" from t_note n LEFT JOIN  t_note_type nt"
				+" ON n.type_id=nt.id where nt.user_id=?";
		String sqlCount="select count(1)"
						+" from t_note n LEFT JOIN  t_note_type nt"
						+" ON n.type_id=nt.id where nt.user_id=?";
		//查询条件的list
		List params=new ArrayList();
		params.add(uid);
		StringBuffer stringBuffer=new StringBuffer();
		/**
		 * 动态拼接SQL
		 */
		if(!StringUtil.isNullOrEmpty(content)){
			stringBuffer.append(" and n.content like concat('%',?,'%')");
			params.add(content);
		}
		if(!StringUtil.isNullOrEmpty(pubDate)){
			stringBuffer.append(" and DATE_FORMAT(n.pub_date,'%y年%m月') =?");
			params.add(pubDate);
		}
		if(!StringUtil.isNullOrEmpty(typeName)){
			stringBuffer.append(" and nt.type_name=?");
			params.add(typeName);
		}
		PageInfo<Note> pageInfo=new PageInfo<Note>(sqlCount+stringBuffer.toString(), sqlQuery+stringBuffer.toString(),
				params.toArray(), pageNum, Note.class);
		return pageInfo;
	}
	/**
	 * 回填数据
	 * @param id
	 * @return
	 */
	public Note queryNoteById(int id){
		String sql="SELECT n.id,title,content,n.pub_date as pubDate,nt.type_name as typeName,nt.id as typeId"
				+" from t_note n LEFT JOIN t_note_type nt ON n.type_id=nt.id where n.id=?";
		return querySingleRow(sql, Note.class, id);
	}
	/**
	 * 修改云记
	 * @param note
	 * @return
	 */
	public int updateNote(Note note){
		String sql="update t_note set title=?,type_id=?,content=?,update_date=? where id=?";
		return executeUpdate(sql, note.getTitle(),note.getTypeId(),note.getContent(),new Date(),note.getId());
	}
	public int delNote(int id){
		String sql="delete from t_note where id=?";
		return executeUpdate(sql, id);
	}
}
