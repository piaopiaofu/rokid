package com.rokid.soa.dao.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rokid.soa.bo.manage.User;
import com.rokid.soa.dao.common.CommDao;
import com.rokid.soa.mapper.manage.UserMapper;


@Repository
/**
 * 用户DAO
 */
public class UserDao extends CommDao {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 用户登陆
	 * @return
	 */
	public User login(String userName, String password) {
		return userMapper.login(userName, password);
	}
	
	/**
	 * 用户一栏数量
	 * @return
	 */
	public int listCount(String userName, Short isValid) {
		return userMapper.listCount(userName, isValid);
	}
	
	/**
	 * 用户一栏
	 * @return
	 */
	public List<User> list(String userName, Integer start, Integer pageSize, Short isValid) {
		return userMapper.list(userName, start, pageSize, isValid);
	}
	
	/**
	 * 检查用户是否存在
	 * @return
	 */
	public int checkUser(String userName) {
		return userMapper.checkUser(userName);
	}	
}