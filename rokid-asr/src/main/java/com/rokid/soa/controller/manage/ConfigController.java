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
import com.rokid.soa.service.manage.IConfigService;

/**
 *配置接口 Controller Created by tong on 16-10-22。
 */
@Controller
@RequestMapping("/manage/conf")
public class ConfigController extends SessionController{

	@Autowired
	private IConfigService configService;

	/**
	 * 获取列表
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
			ResponseMap map = configService.list(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 配置保存
	 * 
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/update")
	@ResponseBody
	public JsonResult update(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = configService.update(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
