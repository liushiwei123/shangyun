package com.shsxt.snote.dao;

import java.util.List;

import com.shsxt.snote.vo.NoteType;

public class NoteTypeDao extends BaseDao<NoteType> {
	/**
	 * 查询所有类别
	 * @return
	 */
	public List<NoteType> queryNoteTypeByUid(Integer uid){
		String sql="select id,type_name as typeName from t_note_type where user_id=?";
		return queryRows(sql, NoteType.class, uid);
	}
	/**
	 * 添加类别
	 * @param noteType
	 * @return
	 */
	public int addNoteType(NoteType noteType){
		String sql="insert into t_note_type(type_name,user_id) values(?,?)";
		return executeUpdate(sql, noteType.getTypeName(),noteType.getUserId());
	}
	/**
	 * 修改方法
	 * @param noteType
	 * @return
	 */
	public int updateNoteType(NoteType noteType){
		String sql="update t_note_type set type_name=? where id=?";
		return executeUpdate(sql, noteType.getTypeName(),noteType.getId());
	}
	/**
	 * 回填|查询类别名称不能重复
	 * @param uid
	 * @param typeName
	 * @return
	 */
	public NoteType queryNoteTypeByTypeNameUid(Integer uid,String typeName){
		String sql="select id,type_name as typeName from t_note_type where user_id=? and type_name=?";
		return querySingleRow(sql, NoteType.class, uid,typeName);
	}
	/**
	 * 删除之前判断该记录是否存在
	 * @param id
	 * @return
	 */
	public NoteType queryNoteTypeById(Integer id){
		String sql="select id,type_name as typeName from t_note_type where id=?";
		return querySingleRow(sql, NoteType.class, id);
	}
	/**
	 * 删除方法
	 * @param id
	 * @return
	 */
	public int deleteNoteType(Integer id){
		String sql="delete from t_note_type where id=?";
		return executeUpdate(sql, id);
	}
}
