package com.rokid.soa.bo.manage;

import java.io.Serializable;

public class WorkUser implements Serializable {
    private String id;
    
    private String time;

    private String workId;

    private String user;
    
    private Short type;
    
    private Short workEnd;

    public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

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
		this.time = time;
	}

	public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public Short getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(Short workEnd) {
		this.workEnd = workEnd;
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
        WorkUser other = (WorkUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWorkId() == null ? other.getWorkId() == null : this.getWorkId().equals(other.getWorkId()))
            && (this.getUser() == null ? other.getUser() == null : this.getUser().equals(other.getUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWorkId() == null) ? 0 : getWorkId().hashCode());
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
        return result;
    }
}