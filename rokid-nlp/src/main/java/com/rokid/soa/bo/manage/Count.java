package com.rokid.soa.bo.manage;

import java.io.Serializable;

public class Count implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String time;

    private String domain;

    private Integer totalCnt;
    
    private Integer errorCnt;
    
    private Integer okCnt;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(Integer totalCnt) {
		this.totalCnt = totalCnt;
	}

	public Integer getErrorCnt() {
		return errorCnt;
	}

	public void setErrorCnt(Integer errorCnt) {
		this.errorCnt = errorCnt;
	}

	public Integer getOkCnt() {
		return okCnt;
	}

	public void setOkCnt(Integer okCnt) {
		this.okCnt = okCnt;
	}
}