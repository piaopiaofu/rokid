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
import com.rokid.soa.service.manage.ISyncService;

/**
 *统计 Controller Created by tong on 16-10-22。
 */
@Controller
@RequestMapping("/manage/import")
public class FirstController extends SessionController{

	@Autowired
	private ISyncService syncService;
	
	/**
	 * VOICE输入首次导入
	 * 
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/voice")
	@ResponseBody
	public JsonResult voice(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = syncService.SyncVoice();
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * Question首次数据导入
	 * 
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/question")
	@ResponseBody
	public JsonResult chat(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = syncService.SyncQuestion();
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * CHAT首次数据导入
	 * 
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/chat")
	@ResponseBody
	public JsonResult chatNew(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = syncService.SyncChat();
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * Answer首次数据导入
	 *  
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/answer")
	@ResponseBody
	public JsonResult chatNewDel(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = syncService.SyncAnswer();
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
