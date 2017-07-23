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
import com.rokid.soa.service.biaozhu.IAsrNlpService;
import com.rokid.soa.service.manage.ISyncService;

/**
 *ASRNLP标注 Controller Created by tong on 16-10-24。
 */
@Controller
@RequestMapping("/biaozhu/nlp")
public class NlpController extends SessionController{

	@Autowired
	private IAsrNlpService asrNlpService;
	
	@Autowired
	private ISyncService syncService;
	
	@RequestMapping("/v1/init")
	@ResponseBody
	public JsonResult init(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap(); 
			map = asrNlpService.init(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * NLP标注一栏
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
			map = asrNlpService.list(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新NLP信息
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
			map = asrNlpService.update(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新NLP信息
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/updates")
	@ResponseBody
	public JsonResult updates(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = asrNlpService.updates(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 统计
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/count")
	@ResponseBody
	public JsonResult count(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = asrNlpService.count(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 统计
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/syncDomain")
	@ResponseBody
	public JsonResult syncDomain(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = syncService.SyncDomain();
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新domain分组
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/selDomainGroup")
	@ResponseBody
	public JsonResult selDomainGroup(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = asrNlpService.selDomainGroup(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * 更新domain分组
	 * @param requestMap 请求参数
	 * @return
	 */
	@RequestMapping("/v1/updDomainGroup")
	@ResponseBody
	public JsonResult updDomainGroup(@RequestBody RequestMap requestMap, HttpServletRequest request){
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;
			
			//业务处理
			ResponseMap map = new ResponseMap();
			map = asrNlpService.updDomainGroup(requestMap);
			return new JsonResultApi(map);
		} catch (Throwable t) {
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}
