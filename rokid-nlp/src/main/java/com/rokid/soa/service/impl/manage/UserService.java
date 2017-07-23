package com.rokid.soa.service.impl.manage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.manage.User;
import com.rokid.soa.common.BeanUtil;
import com.rokid.soa.common.MD5Util;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.dao.manage.UserDao;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.manage.IUserService;
import com.rokid.soa.vo.manage.UserVo;
/**
 * 用户SERVICE Created by tong on 16/10/22.
 */
@Transactional
@Service
public class UserService extends CommonService implements IUserService {

	/** 用户DAO */
	@Autowired
	private UserDao userDao;
	
	@Override
	public ResponseMap login(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String userName = reqMap.getStringValue("用户名", "userName", RequestMap.MUST_INPUT);
		String password = reqMap.getStringValue("登陆密码", "password", RequestMap.MUST_INPUT);
		
		//业务处理
		try {
			UserVo vo = new UserVo();
			// Md5加密
			password = MD5Util.MD5(password);
			// 用户登陆验证
			User bo = userDao.login(userName, password);
			
			if(bo == null){
				// 登陆错误
				resMap.setFailReturn("用户名或登陆密码错误！");
			}else{
				
				// 用户被禁用
				if(bo.getIsValid() == 0){
					resMap.setFailReturn("账户已被管理员禁用！");
					return resMap;
				}
				
				// 数据拷贝
				BeanUtil.copyProperties(vo, bo);
				// 用户类型
				if(bo.getType() == (byte) 1){
					vo.setTypeName("管理者");
				}else if(bo.getType() == (byte) 2){
					vo.setTypeName("标注者");
				}else{
					vo.setTypeName("未知用户");
				}
				// 数据VO返回
				resMap.put("vo", vo);
				
				// 更新登陆时间
				User updBo = new User();
				updBo.setId(bo.getId());
				updBo.setLoginTime(RokidUtils.getSysTime());
				userDao.updateByPrimaryKeySelective(updBo);
				
				// 成功返回
				resMap.setSuccessReturn();
			}
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	
	
	@Override
	public ResponseMap list(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String user = reqMap.getStringValue("用户名", "user", RequestMap.NOT_MUST_INPUT);
		Integer page = reqMap.getIntegerValue("页码", "page", RequestMap.NOT_MUST_INPUT);
		Short isValid = reqMap.getShortValue("页码", "isValid", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {
			List<UserVo> list= new ArrayList<UserVo>();
			// 用户一栏查询
			int count = userDao.listCount(user, isValid);
			// 获取总页数
			int pageCount = RokidUtils.getPageCnt(count);
			if(page == null) page = 1;
			if(page > pageCount){
				page = pageCount;
			}
			// 开始数据下标
			int start = (page - 1) * RokidUtils.getPageSize();
			// 用户一栏查询
			List<User> listBo = userDao.list(user, start, RokidUtils.getPageSize(), isValid);
			for(int i = 0; i < listBo.size(); i++){
				UserVo vo = new UserVo();
				User bo = listBo.get(i);
				BeanUtil.copyProperties(vo, bo);
				// 用户类型
				if(bo.getType() == (byte) 1){
					vo.setTypeName("管理者");
				}else{
					vo.setTypeName("标注者");
				}
				list.add(vo);
			}
			
			// 一栏记录返回
			resMap.put("page", page);
			resMap.put("pageCount", pageCount);
			resMap.put("pageSize", RokidUtils.getPageSize());
			resMap.put("list", list);
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;		
	}
	
	@Override
	public ResponseMap update(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String id = reqMap.getStringValue("用户ID", "id", RequestMap.NOT_MUST_INPUT);
		boolean mustInput = false;
		if(id == null){
			mustInput = true;
		}
		// 用户类型
		Short type = reqMap.getShortValue("用户类型", "type", mustInput);
		// 用户昵称
		String nick = reqMap.getStringValue("昵称", "nick", mustInput);
		// 用户名
		String user = reqMap.getStringValue("用户名", "user", mustInput);
		// 密码
		String passwd = reqMap.getStringValue("用户密码", "password", mustInput);
		// 是否有效
		Short isValid = reqMap.getShortValue("是否有效", "isValid", RequestMap.NOT_MUST_INPUT);

		//业务处理
		try {
			User bo = new User();
			// ID
			bo.setId(id);
			// 昵称
			bo.setNick(nick);
			// 用户类型
			bo.setType(type);
			// 用户名
			bo.setUser(user);
			// 密码
			if(!StringUtils.isEmpty(passwd)) bo.setPasswd(MD5Util.MD5(passwd));
			// 用户名
			bo.setIsValid(isValid);

			if(id != null){
				// 数据更新
				int ret = userDao.updateByPrimaryKeySelective(bo);
				if(ret == 0){
					resMap.setFailReturn("用户不存在或已被删除！");
					return resMap;
				}
			}else{
				// 检查用户名是否已存在
				if(userDao.checkUser(user.trim()) > 0){
					resMap.setFailReturn("用户名已被使用！");
					return resMap;
				}
				// ID
				bo.setId(RokidUtils.getGUID());
				// 最后登陆时间
				bo.setLoginTime(0L);
				// 创建时间
				bo.setCrtTime(RokidUtils.getSysTime());
				// 有效
				bo.setIsValid((short) 1);
				// 数据插入
				userDao.insert(bo);
			}
			
			UserVo vo = new UserVo();
			BeanUtil.copyProperties(vo, bo);
			// 数据VO返回
			resMap.put("vo", vo);

			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;		
	}
	
	@Override
	public ResponseMap delete(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String id = reqMap.getStringValue("用户ID", "id", RequestMap.MUST_INPUT);
				
		//业务处理
		try {
			User bo = new User();
			bo.setId(id);
			// 用户登陆验证
			int ret = userDao.deleteByPrimaryKeySelective(bo);
			if(ret == 0){
				resMap.setFailReturn("用户不存在或已被删除！");
				return resMap;
			}

			// 成功返回
			resMap.setSuccessReturn();				
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;		
	}

	@Override
	public ResponseMap loginOut(RequestMap requestMap) {
		// TODO Auto-generated method stub
		return null;
	}
}