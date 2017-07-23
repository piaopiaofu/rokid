package com.rokid.soa.bo.manage;

import java.io.Serializable;

public class WorkVer extends WorkVerKey implements Serializable {
    private Integer ver;

    private static final long serialVersionUID = 1L;

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
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
        WorkVer other = (WorkVer) that;
        return (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getVer() == null ? other.getVer() == null : this.getVer().equals(other.getVer()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getVer() == null) ? 0 : getVer().hashCode());
        return result;
    }
}