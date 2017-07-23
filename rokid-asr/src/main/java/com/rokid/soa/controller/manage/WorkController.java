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
import com.rokid.soa.service.manage.IWorkService;

/**
 *分工指派  Controller Created by tong on 16-11-03。
 */
@Controller
@RequestMapping("/manage/work")
public class WorkController extends SessionController{

	@Autowired
	private IWorkService workService;
	
	/**
	 * 一栏
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
			ResponseMap map = workService.list(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 删除
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
			map = workService.delete(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新
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
			map = workService.update(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
