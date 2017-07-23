package com.rokid.soa.bo.biaozhu;

import java.io.Serializable;

public class VoiceTrigger implements Serializable {
    private String id;
    
    private Integer version;
    
    private String sn;

    private String path;
    
    private Integer fstatus;

    private Long time;

    private Short type;
    
    private String extField;
    
    private Short editAdmin;

    private Long updTime;

    private String updId;
    
    private String updName;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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

    public String getExtField() {
		return extField;
	}

	public void setExtField(String extField) {
		this.extField = extField;
	}

	public Short getEditAdmin() {
		return editAdmin;
	}

	public void setEditAdmin(Short editAdmin) {
		this.editAdmin = editAdmin;
	}

	public Long getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Long updTime) {
        this.updTime = updTime;
    }

    public String getUpdId() {
        return updId;
    }

    public void setUpdId(String updId) {
        this.updId = updId == null ? null : updId.trim();
    }
    
    public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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
        VoiceTrigger other = (VoiceTrigger) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getUpdTime() == null ? other.getUpdTime() == null : this.getUpdTime().equals(other.getUpdTime()))
            && (this.getUpdId() == null ? other.getUpdId() == null : this.getUpdId().equals(other.getUpdId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUpdTime() == null) ? 0 : getUpdTime().hashCode());
        result = prime * result + ((getUpdId() == null) ? 0 : getUpdId().hashCode());
        return result;
    }
}