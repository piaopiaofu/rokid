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
import com.rokid.soa.service.biaozhu.IVoiceTriggerService;

/**
 *语音标注 Controller Created by tong on 16-10-24。
 */
@Controller
@RequestMapping("/biaozhu/voice")
public class VoiceTriggerController extends SessionController{

	@Autowired
	private IVoiceTriggerService voiceTriggerService;

	/**
	 * 更新语音无法播放错误
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/waverr")
	@ResponseBody
	public JsonResult waverr(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = voiceTriggerService.updWavErr(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 语音标注一栏
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
			map = voiceTriggerService.list(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新语音标注信息
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
			map = voiceTriggerService.update(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
