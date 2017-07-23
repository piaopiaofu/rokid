package com.rokid.soa.bo.report;

import java.io.Serializable;

public class VoiceReport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 机器编码 */
	private String sn;
	
	/** 日期 */
	private String time;
	
	/** 男 */
	private Integer male;
    
	/** 女 */
    private Integer female;
    
    /** 幼 */
    private Integer young;
    
    /** 环境音 */
    private Integer error;
    
    /** 环境音 */
    private Integer env;
    
    /** 句子中提到若琪 */
    private Integer takki;
    
    /** 未标 */
    private Integer noMarked;
    
    /** 总数  */
    private Integer allData;
    
    /** 误激活率 */
    private String rate;

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

	public Integer getMale() {
		return male;
	}

	public void setMale(Integer male) {
		this.male = male;
	}

	public Integer getFemale() {
		return female;
	}

	public void setFemale(Integer female) {
		this.female = female;
	}

	public Integer getYoung() {
		return young;
	}

	public void setYoung(Integer young) {
		this.young = young;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public Integer getEnv() {
		return env;
	}

	public void setEnv(Integer env) {
		this.env = env;
	}

	public Integer getTakki() {
		return takki;
	}

	public void setTakki(Integer takki) {
		this.takki = takki;
	}

	public Integer getNoMarked() {
		return noMarked;
	}

	public void setNoMarked(Integer noMarked) {
		this.noMarked = noMarked;
	}	

	public Integer getAllData() {
		return allData;
	}

	public void setAllData(Integer allData) {
		this.allData = allData;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}