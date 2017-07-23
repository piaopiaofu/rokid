package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.User;
import com.rokid.soa.vo.manage.IdNameVo;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /** 登陆 */
    User login(String username, String password);

	/** 用户一栏数量  */
	int listCount(String userName, Short isValid);
	
	/** 用户一栏  */
	List<User> list(String userName, Integer start, Integer pageSize, Short isValid);
	
	/** 检查用户名是否已经存在 */
	int checkUser(String userName);
	
	/** 用户下拉框用  */
	List<IdNameVo> idNameList();
}