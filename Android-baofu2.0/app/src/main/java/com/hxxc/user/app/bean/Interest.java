package com.hxxc.user.app.bean;

import java.io.Serializable;

public class Interest implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3280983214526563521L;

	private Integer interestid;

    private String name;

    private Integer type;

    public Integer getInterestid() {
        return interestid;
    }

    public void setInterestid(Integer interestid) {
        this.interestid = interestid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}