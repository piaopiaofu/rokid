package com.rokid.soa.bo.manage;

import java.io.Serializable;

public class Work implements Serializable {
    private String id;

    private String time;
    
    private Long startTime;
    
    private Long endTime;

    private String users;
    
    private Boolean voice;
    
    private Boolean asr;
    
    private Boolean chat;
    
    private Long voiceEnd;
    
    private Long asrEnd;
    
    private Long chatEnd;

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

	public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users == null ? null : users.trim();
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

	public Long getVoiceEnd() {
		return voiceEnd;
	}

	public void setVoiceEnd(Long voiceEnd) {
		this.voiceEnd = voiceEnd;
	}

	public Long getAsrEnd() {
		return asrEnd;
	}

	public void setAsrEnd(Long asrEnd) {
		this.asrEnd = asrEnd;
	}

	public Long getChatEnd() {
		return chatEnd;
	}

	public void setChatEnd(Long chatEnd) {
		this.chatEnd = chatEnd;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Work other = (Work) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getUsers() == null ? other.getUsers() == null : this.getUsers().equals(other.getUsers()))
            && (this.getCrtTime() == null ? other.getCrtTime() == null : this.getCrtTime().equals(other.getCrtTime()))
            && (this.getCrtUser() == null ? other.getCrtUser() == null : this.getCrtUser().equals(other.getCrtUser()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getUsers() == null) ? 0 : getUsers().hashCode());
        result = prime * result + ((getCrtTime() == null) ? 0 : getCrtTime().hashCode());
        result = prime * result + ((getCrtUser() == null) ? 0 : getCrtUser().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        return result;
    }
}