package com.rokid.soa.vo.manage;

import java.io.Serializable;
import java.util.List;

public class WorkVo implements Serializable {
    private String id;

    private String time;
    
    private Long startTime;
    
    private Long endTime;
    
    private Boolean voice;
    
    private Boolean asr;
    
    private Boolean chat;

    private List<?> userList;

    private Long crtTime;

    private String crtUser;

    private String memo;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public List<?> getUserList() {
		return userList;
	}

	public void setUserList(List<?> userList) {
		this.userList = userList;
	}

	public Long getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Long crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser == null ? null : crtUser.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public Boolean getVoice() {
		return voice;
	}

	public void setVoice(Boolean voice) {
		this.voice = voice;
	}

	public Boolean getAsr() {
		return asr;
	}

	public void setAsr(Boolean asr) {
		this.asr = asr;
	}

	public Boolean getChat() {
		return chat;
	}

	public void setChat(Boolean chat) {
		this.chat = chat;
	}   
}