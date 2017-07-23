
package com.rokid.soa.bo.report;

import java.io.Serializable;

public class AsrReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private String time;

	private String sn;

	private Integer male;

	private Integer female;

	private Integer young;

	private Integer takki;

	private Integer error;

	private Integer noMarked;

	private Integer allData;

	private String domain;

	private String intent;

	private String slot;

	private Integer chgAsr;

	private Integer okAsr;

	//识别正确[命中chat\idontknow]
	//private Integer okChatIdont;
	
	//无效数若琪[命中chat\idontknow] 
	//private Integer takkiChatIdont;
	
	//无效数环境[命中chat\idontknow]
	//private Integer errorChatIdont;

	//private Integer markChatIdont;

	//修改字数 整体
	private Integer asrEditCnt;
	//已标字数总数 整体
	private Integer asrLenCnt;
	
	//通用
	private Integer tyEditCnt;
	private Integer tyLenCnt;

	private Short type;

	private String asr;

	private String asrEdit;

	public Integer getAsrLenCnt() {
		return asrLenCnt;
	}

	public void setAsrLenCnt(Integer asrLenCnt) {
		this.asrLenCnt = asrLenCnt;
	}

	public Integer getOkAsr() {
		return okAsr;
	}

	public void setOkAsr(Integer okAsr) {
		this.okAsr = okAsr;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getAsr() {
		return asr;
	}

	public void setAsr(String asr) {
		this.asr = asr;
	}

	public Integer getTakki() {
		return takki;
	}

	public void setTakki(Integer takki) {
		this.takki = takki;
	}

	public Integer getChgAsr() {
		return chgAsr;
	}

	public void setChgAsr(Integer chgAsr) {
		this.chgAsr = chgAsr;
	}

	public String getAsrEdit() {
		return asrEdit;
	}

	public void setAsrEdit(String asrEdit) {
		this.asrEdit = asrEdit;
	}

//	public Integer getOkChatIdont() {
//		return okChatIdont;
//	}
//
//	public void setOkChatIdont(Integer okChatIdont) {
//		this.okChatIdont = okChatIdont;
//	}
//
//	public Integer getMarkChatIdont() {
//		return markChatIdont;
//	}
//
//	public void setMarkChatIdont(Integer markChatIdont) {
//		this.markChatIdont = markChatIdont;
//	}

	public Integer getAsrEditCnt() {
		return asrEditCnt;
	}

	public void setAsrEditCnt(Integer asrEditCnt) {
		this.asrEditCnt = asrEditCnt;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getTyEditCnt() {
		return tyEditCnt;
	}

	public void setTyEditCnt(Integer tyEditCnt) {
		this.tyEditCnt = tyEditCnt;
	}

	public Integer getTyLenCnt() {
		return tyLenCnt;
	}

	public void setTyLenCnt(Integer tyLenCnt) {
		this.tyLenCnt = tyLenCnt;
	}

//	public Integer getTakkiChatIdont() {
//		return takkiChatIdont;
//	}
//
//	public void setTakkiChatIdont(Integer takkiChatIdont) {
//		this.takkiChatIdont = takkiChatIdont;
//	}
//
//	public Integer getErrorChatIdont() {
//		return errorChatIdont;
//	}
//
//	public void setErrorChatIdont(Integer errorChatIdont) {
//		this.errorChatIdont = errorChatIdont;
//	}

}