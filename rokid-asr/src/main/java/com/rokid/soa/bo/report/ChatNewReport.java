package com.rokid.soa.bo.report;

import java.io.Serializable;

public class ChatNewReport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** id */
	private String id;
	
	/** 机器编码 */
	private String sn;
	
	/** 日期 */
	private String time;
	
	/** 内容  */
	private String topic;
    
    /** 指派 */
    private String assignId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}
}