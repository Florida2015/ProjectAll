package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

public class City implements Serializable {
	/**
	 * id : 1192
	 * code : 320600
	 * name : 南通市
	 * parentid : 1191
	 * orders : 11
	 * nameen : nantongshi
	 * shortnameen : nantongshi
	 */

	private int id;
	private String code;
	private String name;
	private int parentid;
	private int orders;
	private String nameen;
	private String shortnameen;
	private PAreaVoBean pAreaVo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public String getNameen() {
		return nameen;
	}

	public void setNameen(String nameen) {
		this.nameen = nameen;
	}

	public String getShortnameen() {
		return shortnameen;
	}

	public void setShortnameen(String shortnameen) {
		this.shortnameen = shortnameen;
	}

	public PAreaVoBean getpAreaVo() {
		return pAreaVo;
	}

	public void setpAreaVo(PAreaVoBean pAreaVo) {
		this.pAreaVo = pAreaVo;
	}

	@Override
	public String toString() {
		return "City{" +
				"id=" + id +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				", parentid=" + parentid +
				", orders=" + orders +
				", nameen='" + nameen + '\'' +
				", shortnameen='" + shortnameen + '\'' +
				", pAreaVo=" + pAreaVo +
				'}';
	}
}
