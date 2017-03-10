package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2017/2/24.
 * 日历
 */

public class ClanderBean implements Serializable {

    private long calDate;
    private int weekCycleNo;
    private int calYear;
    private int calHalfYear;
    private int calQuarter;
    private int calMonth;
    private int weekId;
    private int dweekId;
    private int isWork;
    private long lawHolidayType;
    private long officeTime;
    private long closingTime;
    private String cal_date_str;
    private String to_day_str;//天
    private boolean isToDay;//是否今天
    private  ProdInterestBean prodInterest;

    public ClanderBean() {
        super();
    }

    public long getCalDate() {
        return calDate;
    }

    public void setCalDate(long calDate) {
        this.calDate = calDate;
    }

    public int getWeekCycleNo() {
        return weekCycleNo;
    }

    public void setWeekCycleNo(int weekCycleNo) {
        this.weekCycleNo = weekCycleNo;
    }

    public int getCalYear() {
        return calYear;
    }

    public void setCalYear(int calYear) {
        this.calYear = calYear;
    }

    public int getCalHalfYear() {
        return calHalfYear;
    }

    public void setCalHalfYear(int calHalfYear) {
        this.calHalfYear = calHalfYear;
    }

    public int getCalQuarter() {
        return calQuarter;
    }

    public void setCalQuarter(int calQuarter) {
        this.calQuarter = calQuarter;
    }

    public int getCalMonth() {
        return calMonth;
    }

    public void setCalMonth(int calMonth) {
        this.calMonth = calMonth;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public int getDweekId() {
        return dweekId;
    }

    public void setDweekId(int dweekId) {
        this.dweekId = dweekId;
    }

    public int getIsWork() {
        return isWork;
    }

    public void setIsWork(int isWork) {
        this.isWork = isWork;
    }

    public long getLawHolidayType() {
        return lawHolidayType;
    }

    public void setLawHolidayType(long lawHolidayType) {
        this.lawHolidayType = lawHolidayType;
    }

    public long getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(long officeTime) {
        this.officeTime = officeTime;
    }

    public long getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(long closingTime) {
        this.closingTime = closingTime;
    }

    public String getCal_date_str() {
        return cal_date_str;
    }

    public void setCal_date_str(String cal_date_str) {
        this.cal_date_str = cal_date_str;
    }

    public String getTo_day_str() {
        return to_day_str;
    }

    public void setTo_day_str(String to_day_str) {
        this.to_day_str = to_day_str;
    }

    public ProdInterestBean getProdInterest() {
        return prodInterest;
    }

    public void setProdInterest(ProdInterestBean prodInterest) {
        this.prodInterest = prodInterest;
    }

    public boolean isToDay() {
        return isToDay;
    }

    public void setToDay(boolean toDay) {
        isToDay = toDay;
    }

    @Override
    public String toString() {
        return "ClanderBean{" +
                "calDate=" + calDate +
                ", weekCycleNo=" + weekCycleNo +
                ", calYear=" + calYear +
                ", calHalfYear=" + calHalfYear +
                ", calQuarter=" + calQuarter +
                ", calMonth=" + calMonth +
                ", weekId=" + weekId +
                ", dweekId=" + dweekId +
                ", isWork=" + isWork +
                ", lawHolidayType=" + lawHolidayType +
                ", officeTime=" + officeTime +
                ", closingTime=" + closingTime +
                ", cal_date_str='" + cal_date_str + '\'' +
                ", to_day_str='" + to_day_str + '\'' +
                ", prodInterest=" + prodInterest +
                '}';
    }

}
