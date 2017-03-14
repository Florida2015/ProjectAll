package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by chenqun on 2016/10/9.
 */

public class ProductInfo implements Serializable {


    /**
     * customer : {"borrowerName":"孙渊","borrowerSex":"男","borrowerAge":40,"maritalStatus":"已婚","borrowerEducationLevel":"大专","borrowerJobProperty":1,"borrowerCompanyProperty":"未知","borrowerOwnedIndustry":"建筑业","positionName":"部门级","monthlyIncome":12000,"otherIncome":0,"enterpriseType":"私企","shareRatio":0,"employeesNumber":"0","businessPlace":"公积金按揭","propertyType":"未知","permanentAddress":"上海市","homeAddress":"上海市","borrowerDescription":"","acount":"89d45363-c1b7-4e7c-a948-4973e8ef6345"}
     * carInfo : {"vehicleBrand":"奥迪","vehicleModels":"A8","bodyColor":"白色","purchaseDate":"2016-10-20 00:00:00.0","purchasePrice":140000,"travelKilometer":"300","isWhetherUsedCar":"未知","areAccident":"无","evaluationPrice":19}
     * photos : {"carUrl":"/hxadmin/car_photo_2016-11-18-10:03:57.jpg","drivingLicenseUrl":"/hxadmin/driverLicense_photo_2016-11-18-10:03:57.jpg","evhiclesRregisteredUrl":"/hxadmin/car_Login_2016-11-18-10:03:57.jpg","idurl":"/hxadmin/idcard_photo_2016-11-18-10:03:57.jpg"}
     * productName : 奥迪
     * yearRate : 0.08
     * periods : 12
     * periodMonth : 12
     * surplus : 25000
     * amount : 50000
     * status : 4
     * proportion : 0.0
     * number : 1
     * interestType : 等额本息
     * interestTypeText :
     * interestStandard : 满标起息
     * minAmount : 1000
     * maxAmount : 50000
     * securityStyle :
     * platformInfos : []
     * statusText : 已结清
     */
    //    债转标预期收益
    private BigDecimal expectedProfit;
    private int bidType;

    private CustomerBean customer;
    private CarInfoBean carInfo;
    private PhotosBean photos;
    private String productName;
    private BigDecimal yearRate;
    private int periods;
    private int periodMonth;
    private String periodMonthStr;

    private BigDecimal surplus;
    private BigDecimal amount;
    private int status;
    private double proportion;
    private int number;
    private String interestType;
    private String interestTypeText;
    private String interestStandard;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String securityStyle;
    private String statusText;
    private List<?> platformInfos;

    private String transferor;

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public CarInfoBean getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfoBean carInfo) {
        this.carInfo = carInfo;
    }

    public PhotosBean getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosBean photos) {
        this.photos = photos;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getYearRate() {
        return yearRate;
    }

    public void setYearRate(BigDecimal yearRate) {
        this.yearRate = yearRate;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    public BigDecimal getSurplus() {
        return surplus;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getInterestTypeText() {
        return interestTypeText;
    }

    public void setInterestTypeText(String interestTypeText) {
        this.interestTypeText = interestTypeText;
    }

    public String getInterestStandard() {
        return interestStandard;
    }

    public void setInterestStandard(String interestStandard) {
        this.interestStandard = interestStandard;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getSecurityStyle() {
        return securityStyle;
    }

    public void setSecurityStyle(String securityStyle) {
        this.securityStyle = securityStyle;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public List<?> getPlatformInfos() {
        return platformInfos;
    }

    public void setPlatformInfos(List<?> platformInfos) {
        this.platformInfos = platformInfos;
    }

    public int getBidType() {
        return bidType;
    }

    public void setBidType(int bidType) {
        this.bidType = bidType;
    }

    public String getPeriodMonthStr() {
        return periodMonthStr;
    }

    public void setPeriodMonthStr(String periodMonthStr) {
        this.periodMonthStr = periodMonthStr;
    }

    public String getTransferor() {
        return transferor;
    }

    public void setTransferor(String transferor) {
        this.transferor = transferor;
    }

    public BigDecimal getExpectedProfit() {
        return expectedProfit;
    }

    public void setExpectedProfit(BigDecimal expectedProfit) {
        this.expectedProfit = expectedProfit;
    }

    public static class CustomerBean implements Serializable {
        /**
         * borrowerName : 孙渊
         * borrowerSex : 男
         * borrowerAge : 40
         * maritalStatus : 已婚
         * borrowerEducationLevel : 大专
         * borrowerJobProperty : 1
         * borrowerCompanyProperty : 未知
         * borrowerOwnedIndustry : 建筑业
         * positionName : 部门级
         * monthlyIncome : 12000
         * otherIncome : 0
         * enterpriseType : 私企
         * shareRatio : 0
         * employeesNumber : 0
         * businessPlace : 公积金按揭
         * propertyType : 未知
         * permanentAddress : 上海市
         * homeAddress : 上海市
         * borrowerDescription :
         * acount : 89d45363-c1b7-4e7c-a948-4973e8ef6345
         */

        private String borrowerName;
        private String borrowerSex;
        private int borrowerAge;
        private String maritalStatus;
        private String borrowerEducationLevel;
        private int borrowerJobProperty;
        private String borrowerCompanyProperty;
        private String borrowerOwnedIndustry;
        private String positionName;
        private int monthlyIncome;
        private int otherIncome;
        private String enterpriseType;
        private int shareRatio;
        private String employeesNumber;
        private String businessPlace;
        private String propertyType;
        private String permanentAddress;
        private String homeAddress;
        private String borrowerDescription;
        private String acount;

        public String getBorrowerName() {
            return borrowerName;
        }

        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        public String getBorrowerSex() {
            return borrowerSex;
        }

        public void setBorrowerSex(String borrowerSex) {
            this.borrowerSex = borrowerSex;
        }

        public int getBorrowerAge() {
            return borrowerAge;
        }

        public void setBorrowerAge(int borrowerAge) {
            this.borrowerAge = borrowerAge;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getBorrowerEducationLevel() {
            return borrowerEducationLevel;
        }

        public void setBorrowerEducationLevel(String borrowerEducationLevel) {
            this.borrowerEducationLevel = borrowerEducationLevel;
        }

        public int getBorrowerJobProperty() {
            return borrowerJobProperty;
        }

        public void setBorrowerJobProperty(int borrowerJobProperty) {
            this.borrowerJobProperty = borrowerJobProperty;
        }

        public String getBorrowerCompanyProperty() {
            return borrowerCompanyProperty;
        }

        public void setBorrowerCompanyProperty(String borrowerCompanyProperty) {
            this.borrowerCompanyProperty = borrowerCompanyProperty;
        }

        public String getBorrowerOwnedIndustry() {
            return borrowerOwnedIndustry;
        }

        public void setBorrowerOwnedIndustry(String borrowerOwnedIndustry) {
            this.borrowerOwnedIndustry = borrowerOwnedIndustry;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public int getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(int monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public int getOtherIncome() {
            return otherIncome;
        }

        public void setOtherIncome(int otherIncome) {
            this.otherIncome = otherIncome;
        }

        public String getEnterpriseType() {
            return enterpriseType;
        }

        public void setEnterpriseType(String enterpriseType) {
            this.enterpriseType = enterpriseType;
        }

        public int getShareRatio() {
            return shareRatio;
        }

        public void setShareRatio(int shareRatio) {
            this.shareRatio = shareRatio;
        }

        public String getEmployeesNumber() {
            return employeesNumber;
        }

        public void setEmployeesNumber(String employeesNumber) {
            this.employeesNumber = employeesNumber;
        }

        public String getBusinessPlace() {
            return businessPlace;
        }

        public void setBusinessPlace(String businessPlace) {
            this.businessPlace = businessPlace;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public String getPermanentAddress() {
            return permanentAddress;
        }

        public void setPermanentAddress(String permanentAddress) {
            this.permanentAddress = permanentAddress;
        }

        public String getHomeAddress() {
            return homeAddress;
        }

        public void setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
        }

        public String getBorrowerDescription() {
            return borrowerDescription;
        }

        public void setBorrowerDescription(String borrowerDescription) {
            this.borrowerDescription = borrowerDescription;
        }

        public String getAcount() {
            return acount;
        }

        public void setAcount(String acount) {
            this.acount = acount;
        }
    }

    public static class CarInfoBean implements Serializable {
        /**
         * vehicleBrand : 奥迪
         * vehicleModels : A8
         * bodyColor : 白色
         * purchaseDate : 2016-10-20 00:00:00.0
         * purchasePrice : 140000
         * travelKilometer : 300
         * isWhetherUsedCar : 未知
         * areAccident : 无
         * evaluationPrice : 19
         */

        private String vehicleBrand;
        private String vehicleModels;
        private String bodyColor;
        private String purchaseDate;
        private int purchasePrice;
        private String travelKilometer;
        private String isWhetherUsedCar;
        private String areAccident;
        private float evaluationPrice;

        public String getVehicleBrand() {
            return vehicleBrand;
        }

        public void setVehicleBrand(String vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
        }

        public String getVehicleModels() {
            return vehicleModels;
        }

        public void setVehicleModels(String vehicleModels) {
            this.vehicleModels = vehicleModels;
        }

        public String getBodyColor() {
            return bodyColor;
        }

        public void setBodyColor(String bodyColor) {
            this.bodyColor = bodyColor;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public int getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(int purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getTravelKilometer() {
            return travelKilometer;
        }

        public void setTravelKilometer(String travelKilometer) {
            this.travelKilometer = travelKilometer;
        }

        public String getIsWhetherUsedCar() {
            return isWhetherUsedCar;
        }

        public void setIsWhetherUsedCar(String isWhetherUsedCar) {
            this.isWhetherUsedCar = isWhetherUsedCar;
        }

        public String getAreAccident() {
            return areAccident;
        }

        public void setAreAccident(String areAccident) {
            this.areAccident = areAccident;
        }

        public float getEvaluationPrice() {
            return evaluationPrice;
        }

        public void setEvaluationPrice(float evaluationPrice) {
            this.evaluationPrice = evaluationPrice;
        }
    }

    public static class PhotosBean implements Serializable {
        /**
         * carUrl : /hxadmin/car_photo_2016-11-18-10:03:57.jpg
         * drivingLicenseUrl : /hxadmin/driverLicense_photo_2016-11-18-10:03:57.jpg
         * evhiclesRregisteredUrl : /hxadmin/car_Login_2016-11-18-10:03:57.jpg
         * idurl : /hxadmin/idcard_photo_2016-11-18-10:03:57.jpg
         */

        private String carUrl;
        private String drivingLicenseUrl;
        private String evhiclesRregisteredUrl;
        private String idurl;

        public String getCarUrl() {
            return carUrl;
        }

        public void setCarUrl(String carUrl) {
            this.carUrl = carUrl;
        }

        public String getDrivingLicenseUrl() {
            return drivingLicenseUrl;
        }

        public void setDrivingLicenseUrl(String drivingLicenseUrl) {
            this.drivingLicenseUrl = drivingLicenseUrl;
        }

        public String getEvhiclesRregisteredUrl() {
            return evhiclesRregisteredUrl;
        }

        public void setEvhiclesRregisteredUrl(String evhiclesRregisteredUrl) {
            this.evhiclesRregisteredUrl = evhiclesRregisteredUrl;
        }

        public String getIdurl() {
            return idurl;
        }

        public void setIdurl(String idurl) {
            this.idurl = idurl;
        }
    }
}
