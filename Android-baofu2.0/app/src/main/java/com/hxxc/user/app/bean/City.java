package com.hxxc.user.app.bean;
public class City {

	/**
	 * id : 1192
	 * code : 320600
	 * name : 南通市
	 * parentid : 1191
	 * orders : 11
	 * nameen : nantongshi
	 * shortnameen : nantongshi
	 * areaProvinceVo : {"id":1191,"code":"32","name":"江苏省","parentid":1,"orders":10,"nameen":"Jiangsu","shortnameen":"Jiangsu","areaProvinceVo":""}
	 */

	private int id;
	private String code;
	private String name;
	private int parentid;
	private int orders;
	private String nameen;
	private String shortnameen;
	/**
	 * id : 1191
	 * code : 32
	 * name : 江苏省
	 * parentid : 1
	 * orders : 10
	 * nameen : Jiangsu
	 * shortnameen : Jiangsu
	 * areaProvinceVo :
	 */

	private AreaProvinceVoBean areaProvinceVo;

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

	public AreaProvinceVoBean getAreaProvinceVo() {
		return areaProvinceVo;
	}

	public void setAreaProvinceVo(AreaProvinceVoBean areaProvinceVo) {
		this.areaProvinceVo = areaProvinceVo;
	}

	public static class AreaProvinceVoBean {
		private int id;
		private String code;
		private String name;
		private int parentid;
		private int orders;
		private String nameen;
		private String shortnameen;
		private String areaProvinceVo;

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

		public String getAreaProvinceVo() {
			return areaProvinceVo;
		}

		public void setAreaProvinceVo(String areaProvinceVo) {
			this.areaProvinceVo = areaProvinceVo;
		}
	}
}
