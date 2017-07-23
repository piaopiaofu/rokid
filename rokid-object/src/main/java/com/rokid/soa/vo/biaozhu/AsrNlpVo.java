package com.rokid.soa.vo.biaozhu;

import java.io.Serializable;

public class AsrNlpVo implements Serializable {
	
	/** ID*/
    private String id;

    /** 序列号*/
    private String sn;

    /** 语音路径*/
    private String path;
    
    private Integer fstatus;

    /** 语音时间*/
    private Long time;

    /** Asr类型*/
    private Short asrType;
    
    /** 语音文本*/
    private String asr;
    
    /** 修改后语音文本*/
    private String asrEdit;
    
    /** 修改后语音文本*/
    private Short asrEditCnt;

    /** 设备域名*/
    private String domain;

    /** intent*/
    private String intent;

    /** slot*/
    private String slot;

    /** 标注类型*/
    private Short type;
    
    private Short editAdmin;
    
	/** 标注者 */
	private String updName;
	
	public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

    private static final long serialVersionUID = 1L;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getFstatus() {
		return fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getAsr() {
		return asr;
	}

	public void setAsr(String asr) {
		this.asr = asr;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getAsrType() {
		return asrType;
	}

	public void setAsrType(Short asrType) {
		this.asrType = asrType;
	}

	public String getAsrEdit() {
		return asrEdit;
	}

	public void setAsrEdit(String asrEdit) {
		this.asrEdit = asrEdit;
	}

	public Short getAsrEditCnt() {
		return asrEditCnt;
	}

	public void setAsrEditCnt(Short asrEditCnt) {
		this.asrEditCnt = asrEditCnt;
	}

	public Short getEditAdmin() {
		return editAdmin;
	}

	public void setEditAdmin(Short editAdmin) {
		this.editAdmin = editAdmin;
	}
}