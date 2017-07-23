package com.rokid.soa.controller.biaozhu;

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
import com.rokid.soa.service.biaozhu.IChatService;

/**
 *聊天Filter Controller Created by tong on 16-10-28。
 */
@Controller
@RequestMapping("/biaozhu/chat")
public class ChatController extends SessionController{

	@Autowired
	private IChatService chatFilterService;

	/**
	 * 话题Filter一栏
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/list")
	@ResponseBody
	public JsonResult list(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = chatFilterService.list(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新话题
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
			map = chatFilterService.update(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}	
	
	/**
	 * 删除话题
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/del")
	@ResponseBody
	public JsonResult del(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = chatFilterService.del(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
