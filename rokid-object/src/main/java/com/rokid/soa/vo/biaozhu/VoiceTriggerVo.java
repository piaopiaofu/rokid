package com.rokid.soa.vo.biaozhu;

import java.io.Serializable;

public class VoiceTriggerVo implements Serializable {

	/** 默认序列号*/
	private static final long serialVersionUID = 1L;
	
	/** id*/
	private String id;
	
	private String sn;
	
	/** 语音路径*/
	private String path;
	
	private Integer fstatus;
	
	/** 创建时间*/
	private Long time;
	
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getEditAdmin() {
		return editAdmin;
	}

	public void setEditAdmin(Short editAdmin) {
		this.editAdmin = editAdmin;
	}	
}