
package com.rokid.soa.bo.report;

import java.io.Serializable;

public class ActiveReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ymd;
	
	private String time;

	private Integer voicecnt;
	
	private Integer asrcnt;

	public String getYmd() {
		return ymd;
	}

	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getVoicecnt() {
		return voicecnt;
	}

	public void setVoicecnt(Integer voicecnt) {
		this.voicecnt = voicecnt;
	}

	public Integer getAsrcnt() {
		return asrcnt;
	}

	public void setAsrcnt(Integer asrcnt) {
		this.asrcnt = asrcnt;
	}
}