package com.rokid.soa.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.rokid.soa.common.Constants;
import com.rokid.soa.common.JsonResult;
import com.rokid.soa.common.RequestMap;

/**
 * 共通Controller层
 */
public class SessionController{

	/**
	 * 用户登陆超时检查
	 * @param req
	 * @param request
	 * @return
	 */
	public JsonResult checkSession(RequestMap req, HttpServletRequest request){
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(Constants.SESSION_USER_ID) == null){
			return new JsonResult(-9, "未登录或登录超时，请重新登录！", null);
		}
		
		req.put(Constants.SESSION_USER_ID, session.getAttribute(Constants.SESSION_USER_ID));
		req.put(Constants.SESSION_USER_TYPE, session.getAttribute(Constants.SESSION_USER_TYPE));
		req.put(Constants.SESSION_USER_NAME, session.getAttribute(Constants.SESSION_USER_NAME));
		
		//req.put(Constants.SESSION_USER_ID, "4109a93e9c2444929845d264fda34da9");
		//req.put(Constants.SESSION_USER_TYPE, 1);
		
		return null;
	}
}
