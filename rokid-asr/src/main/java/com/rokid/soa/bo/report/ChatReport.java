package com.rokid.soa.bo.report;

import java.io.Serializable;

public class ChatReport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 机器编码 */
	private String sn;
	
	/** 日期 */
	private String time;
	
	/** 已标  */
	private Integer marked;
	
	/** 未标  */
	private Integer noMarked;
	
    /** 总数  */
    private Integer allData;
    
    /** 指派 */
    private String assignId;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getMarked() {
		return marked;
	}

	public void setMarked(Integer marked) {
		this.marked = marked;
	}

	public Integer getNoMarked() {
		return noMarked;
	}

	public void setNoMarked(Integer noMarked) {
		this.noMarked = noMarked;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getAllData() {
		return allData;
	}

	public void setAllData(Integer allData) {
		this.allData = allData;
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}
}