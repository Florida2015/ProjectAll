package com.hxxc.user.app.bean;

import java.io.Serializable;

public class Contract implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2169873095218653980L;

	private Integer cid;

    private String contractno;

    private Integer uid;

    private Long signintime;

    private String firstparty;

    private String documenttype;

    private String documentnumber;

    private Integer ctid;

    private String contracturl;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getSignintime() {
        return signintime;
    }

    public void setSignintime(Long signintime) {
        this.signintime = signintime;
    }

    public String getFirstparty() {
        return firstparty;
    }

    public void setFirstparty(String firstparty) {
        this.firstparty = firstparty == null ? null : firstparty.trim();
    }

    public String getDocumenttype() {
        return documenttype;
    }

    public void setDocumenttype(String documenttype) {
        this.documenttype = documenttype == null ? null : documenttype.trim();
    }

    public String getDocumentnumber() {
        return documentnumber;
    }

    public void setDocumentnumber(String documentnumber) {
        this.documentnumber = documentnumber == null ? null : documentnumber.trim();
    }

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public String getContracturl() {
        return contracturl;
    }

    public void setContracturl(String contracturl) {
        this.contracturl = contracturl == null ? null : contracturl.trim();
    }
}