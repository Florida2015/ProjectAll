package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/1.
 * 签到
 */

public class SignCalBean implements Serializable {

    private long calDate;
    private String uid;
    private String siginInTime;
    private int isExcessRewards;
    private int weekCycleNo;
    private int weekCycleNoForDeal;
    private String calYear;
    private String calMonth;
    private String calDay;
    private String siginInCode;
    private int conDatys;//连续多少天
    private int leftConDatys;
    private int points;
    private int rewardPoints;
    private int conTaskNumber;
    private boolean today;
    private boolean siginIn;

    public SignCalBean() {
        super();
    }

    public boolean isSiginIn() {
        return siginIn;
    }

    public void setSiginIn(boolean siginIn) {
        this.siginIn = siginIn;
    }

    public long getCalDate() {
        return calDate;
    }

    public void setCalDate(long calDate) {
        this.calDate = calDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSiginInTime() {
        return siginInTime;
    }

    public void setSiginInTime(String siginInTime) {
        this.siginInTime = siginInTime;
    }

    public int getIsExcessRewards() {
        return isExcessRewards;
    }

    public void setIsExcessRewards(int isExcessRewards) {
        this.isExcessRewards = isExcessRewards;
    }

    public int getWeekCycleNo() {
        return weekCycleNo;
    }

    public void setWeekCycleNo(int weekCycleNo) {
        this.weekCycleNo = weekCycleNo;
    }

    public int getWeekCycleNoForDeal() {
        return weekCycleNoForDeal;
    }

    public void setWeekCycleNoForDeal(int weekCycleNoForDeal) {
        this.weekCycleNoForDeal = weekCycleNoForDeal;
    }

    public String getCalYear() {
        return calYear;
    }

    public void setCalYear(String calYear) {
        this.calYear = calYear;
    }

    public String getCalMonth() {
        return calMonth;
    }

    public void setCalMonth(String calMonth) {
        this.calMonth = calMonth;
    }

    public String getCalDay() {
        return calDay;
    }

    public void setCalDay(String calDay) {
        this.calDay = calDay;
    }

    public String getSiginInCode() {
        return siginInCode;
    }

    public void setSiginInCode(String siginInCode) {
        this.siginInCode = siginInCode;
    }

    public int getConDatys() {
        return conDatys;
    }

    public void setConDatys(int conDatys) {
        this.conDatys = conDatys;
    }

    public int getLeftConDatys() {
        return leftConDatys;
    }

    public void setLeftConDatys(int leftConDatys) {
        this.leftConDatys = leftConDatys;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getConTaskNumber() {
        return conTaskNumber;
    }

    public void setConTaskNumber(int conTaskNumber) {
        this.conTaskNumber = conTaskNumber;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

    @Override
    public String toString() {
        return "SignCalBean{" +
                "calDate=" + calDate +
                ", uid='" + uid + '\'' +
                ", siginInTime='" + siginInTime + '\'' +
                ", isExcessRewards=" + isExcessRewards +
                ", weekCycleNo=" + weekCycleNo +
                ", weekCycleNoForDeal=" + weekCycleNoForDeal +
                ", calYear='" + calYear + '\'' +
                ", calMonth='" + calMonth + '\'' +
                ", calDay=" + calDay +
                ", siginInCode='" + siginInCode + '\'' +
                ", conDatys=" + conDatys +
                ", leftConDatys=" + leftConDatys +
                ", points=" + points +
                ", rewardPoints=" + rewardPoints +
                ", conTaskNumber=" + conTaskNumber +
                ", today=" + today +
                ", siginIn=" + siginIn +
                '}';
    }
}
