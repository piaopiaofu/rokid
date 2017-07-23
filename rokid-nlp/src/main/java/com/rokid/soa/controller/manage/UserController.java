package com.rokid.soa.controller.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rokid.soa.common.Constants;
import com.rokid.soa.common.JsonResult;
import com.rokid.soa.common.JsonResultApi;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.controller.common.SessionController;
import com.rokid.soa.service.manage.IUserService;
import com.rokid.soa.vo.manage.UserVo;

/**
 *用户 Controller Created by tong on 16-10-22。
 */
@Controller
@RequestMapping("/manage/user")
public class UserController extends SessionController{

	@Autowired
	private IUserService userService;

	/**
	 * 登录
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/login")
	@ResponseBody
	public JsonResult login(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try{
			ResponseMap map = userService.login(requestMap);
			if (Constants.SUCCESS.equals(map.get(ResponseMap.RETURN))) {
				UserVo userVo = (UserVo) map.get("vo");
				if (userVo !=null){
					// 用户名SESSION保存
					HttpSession session = request.getSession();					
					session.setAttribute(Constants.SESSION_USER_ID, userVo.getId());
					session.setAttribute(Constants.SESSION_USER_NAME, userVo.getUser());
					session.setAttribute(Constants.SESSION_USER_TYPE, userVo.getType());
				}
			}
			return new JsonResultApi(map);
		} catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 退出登录
	 * 
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/logout")
	@ResponseBody
	public JsonResult logout(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			
			checkSession(requestMap, request);
			ResponseMap map = new ResponseMap();
			userService.loginOut(requestMap);
			
			HttpSession session = request.getSession();
			session.removeAttribute(Constants.SESSION_USER_ID);
			
			map.setSuccessReturn();
			return new JsonResultApi(map);
		} catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 用户一栏
	 * 
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/list")
	@ResponseBody
	public JsonResult list(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = userService.list(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 删除用户
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/delete")
	@ResponseBody
	public JsonResult delete(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = userService.delete(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新用户
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/update")
	@ResponseBody
	public JsonResult update(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = userService.update(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
