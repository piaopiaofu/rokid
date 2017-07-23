package com.rokid.soa.controller.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rokid.soa.common.JsonResult;
import com.rokid.soa.common.JsonResultApi;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.controller.common.SessionController;
import com.rokid.soa.service.manage.ICountService;

/**
 *统计 Controller Created by tong on 16-10-22。
 */
@Controller
@RequestMapping("/manage/count")
public class CountController extends SessionController{

	@Autowired
	private ICountService countService;

	/**
	 * 用户按年月统计
	 * 
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/userYm")
	@ResponseBody
	public JsonResult list(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = countService.userYm(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
