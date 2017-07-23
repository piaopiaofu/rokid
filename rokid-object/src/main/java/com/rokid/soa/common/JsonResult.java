package com.rokid.soa.common;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by huage on 14-6-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;

	private String message;

	private String data;

	public JsonResult() {
	}

	public JsonResult(int code, String message, String data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		if (null == data) {
			return "";
		}

		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
