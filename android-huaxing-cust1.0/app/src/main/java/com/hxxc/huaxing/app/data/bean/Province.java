package com.hxxc.huaxing.app.data.bean;

import com.huaxiafinance.lc.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by chenqun on 2016/10/11.
 */

public class Province implements IPickerViewData {

    /**
     * id : 2
     * areaCode : 110000
     * areaName : 北京市
     * pid : 000000
     * sort : null
     * cityAreaInfoVos : [{"id":34,"areaCode":"110200","areaName":"北京市","pid":"110000","sort":null,"cityAreaInfoVos":null}]
     */

    private int id;
    private String areaCode;
    private String areaName;
    private String pid;
    private String sort;
    /**
     * id : 34
     * areaCode : 110200
     * areaName : 北京市
     * pid : 110000
     * sort : null
     * cityAreaInfoVos : null
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

    public static class CityAreaInfoVosBean implements IPickerViewData {
        private int id;
        private String areaCode;
        private String areaName;
        private String pid;
        private String sort;
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
    }
}
