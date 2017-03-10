package com.hxxc.user.app.bean;

import com.huaxiafinance.lc.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.ArrayList;


public class Province implements Serializable , IPickerViewData ,Comparable<Province>{
	private static final long serialVersionUID = 460077300104952547L;


	/**
	 * id : 2
	 * areaCode : 110000
	 * areaName : 北京市
	 * pid : 000000
	 * sort :
	 * nameEn : beijingshi
	 * pAreaInfoVo :
	 * cityAreaInfoIndexVos :
	 * cityAreaInfoVos : [{"id":34,"areaCode":"110200","areaName":"北京市","pid":"110000","sort":"","nameEn":"beijingshi","pAreaInfoVo":"","cityAreaInfoIndexVos":"","cityAreaInfoVos":""}]
	 */

	private int id;
	private String areaCode;
	private String areaName;
	private String pid;
	private String sort;
	private String nameEn;
	private String pAreaInfoVo;
	private String cityAreaInfoIndexVos;
	/**
	 * id : 34
	 * areaCode : 110200
	 * areaName : 北京市
	 * pid : 110000
	 * sort :
	 * nameEn : beijingshi
	 * pAreaInfoVo :
	 * cityAreaInfoIndexVos :
	 * cityAreaInfoVos :
	 */

	private ArrayList<CityAreaInfoVosBean> cityAreaInfoVos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getPAreaInfoVo() {
		return pAreaInfoVo;
	}

	public void setPAreaInfoVo(String pAreaInfoVo) {
		this.pAreaInfoVo = pAreaInfoVo;
	}

	public String getCityAreaInfoIndexVos() {
		return cityAreaInfoIndexVos;
	}

	public void setCityAreaInfoIndexVos(String cityAreaInfoIndexVos) {
		this.cityAreaInfoIndexVos = cityAreaInfoIndexVos;
	}

	public ArrayList<CityAreaInfoVosBean> getCityAreaInfoVos() {
		return cityAreaInfoVos;
	}

	public void setCityAreaInfoVos(ArrayList<CityAreaInfoVosBean> cityAreaInfoVos) {
		this.cityAreaInfoVos = cityAreaInfoVos;
	}

	@Override
	public String getPickerViewText() {
		return areaName;
	}

	@Override
	public int compareTo(Province another) {
		return this.getNameEn().substring(0,1).compareTo(another.getNameEn().substring(0,1));
	}

	public static class CityAreaInfoVosBean implements IPickerViewData,Comparable<CityAreaInfoVosBean>{
		private int id;
		private String areaCode;
		private String areaName;
		private String pid;
		private String sort;
		private String nameEn;
		private String pAreaInfoVo;
		private String cityAreaInfoIndexVos;
		private String cityAreaInfoVos;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAreaCode() {
			return areaCode;
		}

		public void setAreaCode(String areaCode) {
			this.areaCode = areaCode;
		}

		public String getAreaName() {
			return areaName;
		}

		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public String getNameEn() {
			return nameEn;
		}

		public void setNameEn(String nameEn) {
			this.nameEn = nameEn;
		}

		public String getPAreaInfoVo() {
			return pAreaInfoVo;
		}

		public void setPAreaInfoVo(String pAreaInfoVo) {
			this.pAreaInfoVo = pAreaInfoVo;
		}

		public String getCityAreaInfoIndexVos() {
			return cityAreaInfoIndexVos;
		}

		public void setCityAreaInfoIndexVos(String cityAreaInfoIndexVos) {
			this.cityAreaInfoIndexVos = cityAreaInfoIndexVos;
		}

		public String getCityAreaInfoVos() {
			return cityAreaInfoVos;
		}

		public void setCityAreaInfoVos(String cityAreaInfoVos) {
			this.cityAreaInfoVos = cityAreaInfoVos;
		}

		@Override
		public String getPickerViewText() {
			return areaName;
		}

		@Override
		public int compareTo(CityAreaInfoVosBean another) {
			return this.getNameEn().substring(0,1).compareTo(another.getNameEn().substring(0,1));
		}
	}
}
