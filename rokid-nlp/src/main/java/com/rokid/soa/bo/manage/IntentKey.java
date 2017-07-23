package com.rokid.soa.bo.manage;

import java.io.Serializable;

public class IntentKey implements Serializable {
    private String domain;

    private String intent;

    private static final long serialVersionUID = 1L;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent == null ? null : intent.trim();
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
        IntentKey other = (IntentKey) that;
        return (this.getDomain() == null ? other.getDomain() == null : this.getDomain().equals(other.getDomain()))
            && (this.getIntent() == null ? other.getIntent() == null : this.getIntent().equals(other.getIntent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDomain() == null) ? 0 : getDomain().hashCode());
        result = prime * result + ((getIntent() == null) ? 0 : getIntent().hashCode());
        return result;
    }
}