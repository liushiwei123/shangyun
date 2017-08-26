package com.shsxt.snote.dao;

import java.util.Date;

import com.shsxt.snote.vo.User;

public class UserDao extends BaseDao<User> {
	/**
	 * 用户登录
	 * @param userName
	 * @return
	 */
	public User queryUserByUserName(String userName){
		String sql="select id,user_name as userName,user_pwd as userPwd,mood,img,"
				+ "nick_name as nickName from t_user where user_name=?";
		return querySingleRow(sql, User.class, userName);
	}
	/**
	 * 昵称唯一性较验
	 * @param nickName
	 * @return
	 */
	public long queryNickName(String nickName){
		String sql="select count(1) from t_user where nick_name=?";
	    return (long) querySingleValue(sql, nickName);
	}
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(User user){
		String sql="update t_user set nick_name=?,img=?,mood=?,update_time=? where id=?";
		return executeUpdate(sql, user.getNickName(),user.getImg(),user.getMood(),new Date(),user.getId());
	}
}
