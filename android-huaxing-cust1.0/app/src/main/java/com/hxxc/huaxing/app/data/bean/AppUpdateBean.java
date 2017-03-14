package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

public class AppUpdateBean implements Serializable{


	/**
	 * id : 1
	 * versionsId : 1
	 * versionName : Android
	 * versionCode : 1.1.1.1.1.1
	 * type : 1
	 * url : 111
	 * createDate : 1473314243000
	 * isForceUpdate : 0
	 * pushTime : 1473313877000
	 * contents : 1111
	 */

	public int id;
	public String versionsId;
	public String versionName;
	public String versionCode;
	public int type;
	public String url;
	public long createDate;
	public int isForceUpdate;
	public long pushTime;
	public String contents;

	public AppUpdateBean() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersionsId() {
		return versionsId;
	}

	public void setVersionsId(String versionsId) {
		this.versionsId = versionsId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public int getIsForceUpdate() {
		return isForceUpdate;
	}

	public void setIsForceUpdate(int isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	public long getPushTime() {
		return pushTime;
	}

	public void setPushTime(long pushTime) {
		this.pushTime = pushTime;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}


}
