package com.rokid.soa.controller.manage;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rokid.soa.bo.report.AsrReport;
import com.rokid.soa.common.JsonResult;
import com.rokid.soa.common.JsonResultApi;
import com.rokid.soa.common.PdfExportUtils;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.controller.common.SessionController;
import com.rokid.soa.service.impl.common.PropertiesUtil;
import com.rokid.soa.service.manage.IReportService;


/**
 *统计 Controller Created by tong on 16-10-22。
 */
@Controller
@RequestMapping("/manage/report")
public class ReportController extends SessionController{

	@Autowired
	private IReportService reportService;

	/**
	 * VOICE按年月统计
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
			ResponseMap map = reportService.voice(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}

	/**
	 * ASR/NLP按年月统计
	 *
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/asrNlp")
	@ResponseBody
	public JsonResult asrNlp(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;

			//业务处理
			ResponseMap map = reportService.asrNlp(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * ASR/NLP 性别按年月统计
	 *
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/asrNlpSex")
	@ResponseBody
	public JsonResult asrNlpSex(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;

			//业务处理
			ResponseMap map = reportService.asrNlpSex(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
	
	/**
	 * active 活跃度统计
	 *
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/active")
	@ResponseBody
	public JsonResult active(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;

			//业务处理
			ResponseMap map = reportService.active(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}

	/**
	 * PDF出力
	 *
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping(method = POST, value = "/v1/asrExp", produces = APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> asrExp(@RequestBody RequestMap requestMap, HttpServletRequest request) {

		final float[] pdfwidth= { 0.3f,0.14f,0.16f,0.2f,0.2f};//列宽设置与字段个数保持一致

		String pdfname ="Asr";//PDF名字

//		String startDate = requestMap.getStringValue("统计开始时间", "startDate", RequestMap.NOT_MUST_INPUT);
//		String endDate = requestMap.getStringValue("统计开始时间", "endDate", RequestMap.NOT_MUST_INPUT);



		String title =String.format("错误的语音识别一览");//title

		final String[]  pdfheader= { "日期", "机器号","语音标注",
				"语音识别", "语音识别修改"};//pdf头部字段 与databean顺序字段保持一致
		try {
			String pdfPathHome = PropertiesUtil.getConfiguration().getString("pdfPathHome");

			Path OutPath = Paths.get(pdfPathHome);
			try {
				Files.createDirectories(OutPath);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			JsonResult ret = checkSession(requestMap, request);
			// session检查
			if(ret != null){
				return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);

			}

			ResponseMap map = reportService.asrNlp(requestMap);

			//pdf文件名字拼接
			String filename = String.format("%s%s.pdf", pdfname, System.currentTimeMillis());

			Path exportFullPath = Paths.get(pdfPathHome, filename);

			Object contact = map.get("return");
			Object message = map.get("message");
			HttpHeaders headers = new HttpHeaders();
			ArrayList<AsrReport> pdfmeisa = new ArrayList<AsrReport>();
			if (contact.equals("success") && message == null) {
				pdfmeisa = (ArrayList<AsrReport>) map.get("list");
				FileOutputStream fos = new FileOutputStream(exportFullPath.toString());
				PdfExportUtils pdfExportUtils = new PdfExportUtils();
				pdfExportUtils.ToPdfTable(pdfwidth, pdfheader, pdfmeisa, fos,title);
				fos.close();

				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

				PathResource exportFileResource = new PathResource(exportFullPath);

				headers.setContentLength(exportFileResource.contentLength());
				headers.set("Contentdisposition", "attachment; filename=" + URLEncoder.encode(exportFileResource.getFilename(), "utf-8"));

				return new ResponseEntity<>(new InputStreamResource(exportFileResource.getInputStream()), headers, HttpStatus.OK);

			}else{
				return new ResponseEntity<>( HttpStatus.NO_CONTENT);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * CHAT按年月统计
	 *
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/chat")
	@ResponseBody
	public JsonResult chat(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;

			//业务处理
			ResponseMap map = reportService.chat(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}

	/**
	 * CHAT按年月统计
	 *
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/chatNew")
	@ResponseBody
	public JsonResult chatNew(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;

			//业务处理
			ResponseMap map = reportService.chatNew(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}

	/**
	 * CHAT删除选择
	 *
	 * @param requestMap
	 *            请求参数
	 * @return
	 */
	@RequestMapping("/v1/chatNewDel")
	@ResponseBody
	public JsonResult chatNewDel(@RequestBody RequestMap requestMap, HttpServletRequest request) {
		try {
			// session检查
			JsonResult ret = checkSession(requestMap, request);
			if(ret != null)	return ret;

			//业务处理
			ResponseMap map = reportService.chatNewDel(requestMap);
			return new JsonResultApi(map);
		}  catch (Throwable t) {
			t.printStackTrace();
			return new JsonResult(0, t.getMessage(), null);
		}
	}
}