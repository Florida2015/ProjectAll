package com.hxxc.user.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenqun on 2016/10/9.
 */

public class ProductInfo implements Serializable{


    /**
     * id : 10
     * productName : 新手专享
     * yearRate : 0.06
     * period : 25
     * periodDay : 750
     * surplus : 1000000
     * amount : 1000000
     * proportion : 0.17
     * minAmount : 100000
     * maxAmount : 1000000
     * stepAmount : 10000
     * activityLabel : ["aaaaa"]
     * status : 1
     * interestId : 0
     * surplusTime : 694430
     * surplusStatus : 2
     * productStart : 1478156208000
     * productEnd : 1480489010000
     * product : {"id":1,"productKey":"","productName":"","periods":25,"yearRate":0.06,"increase":"","autoContinuedInvestment":0,"exitTypeDes":"到期自动赎回，系统将通过债权转让自动完成退出","joinFee":0,"productBelong":1,"productType":"xszx","managerFee":0,"exitFee":0,"penalty":0,"securityStyle":"风险备用金计划","pictureSourceUrl":"","operator":"admin","operatorDate":1478156165000,"isPcRefer":"","isMobileRefer":"","isRepeatBuy":"","isPreferred":"","isPlan":"","interestStartTime":"T+1","redeemedDays":"3~4个工作日","sort":"","isActivity":"","isInvited":"","isContinued":"","interestId":1,"period":12,"periodUnit":"","mbContactUrl":"","pcContactUrl":"","type":"","productCode":"","description":"优势：适合家庭大额闲置资金，且每月收益可以支付房贷、车贷、子女教育、养老等日常生活消费","pcProductInfo":"&lt;div class=&quot;detailCaseLeftPic&quot;&gt;\n\t&lt;img src=&quot;/bespeakBg/attached/image/20160328/20160328152839_193.png&quot; alt=&quot;&quot; /&gt;&lt;br /&gt;\n&lt;/div&gt;\n&lt;div class=&quot;detailCaseRightTxt&quot;&gt;\n\t&lt;h3&gt;\n\t\t案例分析\n\t&lt;/h3&gt;\n\t&lt;div class=&quot;caseText&quot;&gt;\n\t\t万先生，45岁，从事高等教育工作，在北京有车有房并有一双儿女，子女都已20出头，万先生想为子女准备结婚费用。万先生每月收入20000元，平时写写文章年收入20000元，加上年终奖，年收入约在30万。现有闲散资金200万元，咨询了银行利息较低，万先生想找一家预期年化收益文件安全的公司，经过朋友介绍，来到了华夏信财公司，详细了解了华夏信财出借模式后决定让财富顾问做一个方案。\n\t&lt;/div&gt;\n\t&lt;h3 class=&quot;sugg&quot;&gt;\n\t\t出借目标\n\t&lt;/h3&gt;\n\t&lt;div class=&quot;caseText&quot;&gt;\n\t\t闲散资金，实现增值。\n\t&lt;/div&gt;\n\t&lt;h3 class=&quot;sugg&quot;&gt;\n\t\t出借建议\n\t&lt;/h3&gt;\n\t&lt;div class=&quot;caseText&quot;&gt;\n\t\t万先生有200万资金，因万先生子女年轻，可选择较长周期的出借模式。财富顾问推荐选择\u201c定信宝B\u201d，周期为24个月，24个月后本金加收益一共可以拿到254万元，如果万先生子女还未有结婚计划可选择续投，万先生年收入约为30万，每年可剩余20万，可在选择华信增出借模式。\n\t&lt;/div&gt;\n&lt;/div&gt;","wapProductInfo":"","purpose":"购车使用","assure":"","riskControl":"","pdfContactUrl":"http://192.168.11.48:8089/picture/contact/protocol50.pdf","htmlContactUrl":"www"}
     * productBidName : 新手专享2016102704期
     * currPeriod : 2016102704
     * interestType :
     * statusText : 抢购
     * productStartEnd : 1480489010000
     * productStartEndText : 11月30日14:56
     * productStartText : 11月03日14:56
     */

    private int id;
    private String productName;
    private double yearRate;
    private int period;
    private int periodDay;
    private long surplus;
    private long amount;
    private double proportion;
    private long minAmount;
    private long maxAmount;
    private int stepAmount;
    private int status;
    private int interestId;
    private long surplusTime;
    private int surplusStatus;
    private long productStart;
    private long productEnd;
    private ProductBean product;
    private String productBidName;
    private String currPeriod;
    private String statusText;
    private long productStartEnd;
    private String productStartEndText;
    private String productStartText;
    private List<String> activityLabel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriodDay() {
        return periodDay;
    }

    public void setPeriodDay(int periodDay) {
        this.periodDay = periodDay;
    }

    public long getSurplus() {
        return surplus;
    }

    public void setSurplus(long surplus) {
        this.surplus = surplus;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(long minAmount) {
        this.minAmount = minAmount;
    }

    public long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(int stepAmount) {
        this.stepAmount = stepAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    public long getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(long surplusTime) {
        this.surplusTime = surplusTime;
    }

    public int getSurplusStatus() {
        return surplusStatus;
    }

    public void setSurplusStatus(int surplusStatus) {
        this.surplusStatus = surplusStatus;
    }

    public long getProductStart() {
        return productStart;
    }

    public void setProductStart(long productStart) {
        this.productStart = productStart;
    }

    public long getProductEnd() {
        return productEnd;
    }

    public void setProductEnd(long productEnd) {
        this.productEnd = productEnd;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public String getProductBidName() {
        return productBidName;
    }

    public void setProductBidName(String productBidName) {
        this.productBidName = productBidName;
    }

    public String getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(String currPeriod) {
        this.currPeriod = currPeriod;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public long getProductStartEnd() {
        return productStartEnd;
    }

    public void setProductStartEnd(long productStartEnd) {
        this.productStartEnd = productStartEnd;
    }

    public String getProductStartEndText() {
        return productStartEndText;
    }

    public void setProductStartEndText(String productStartEndText) {
        this.productStartEndText = productStartEndText;
    }

    public String getProductStartText() {
        return productStartText;
    }

    public void setProductStartText(String productStartText) {
        this.productStartText = productStartText;
    }

    public List<String> getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(List<String> activityLabel) {
        this.activityLabel = activityLabel;
    }

    public static class ProductBean implements Serializable{
        /**
         * id : 1
         * productKey :
         * productName :
         * periods : 25
         * yearRate : 0.06
         * increase :
         * autoContinuedInvestment : 0
         * exitTypeDes : 到期自动赎回，系统将通过债权转让自动完成退出
         * joinFee : 0
         * productBelong : 1
         * productType : xszx
         * managerFee : 0
         * exitFee : 0
         * penalty : 0
         * securityStyle : 风险备用金计划
         * pictureSourceUrl :
         * operator : admin
         * operatorDate : 1478156165000
         * isPcRefer :
         * isMobileRefer :
         * isRepeatBuy :
         * isPreferred :
         * isPlan :
         * interestStartTime : T+1
         * redeemedDays : 3~4个工作日
         * sort :
         * isActivity :
         * isInvited :
         * isContinued :
         * interestId : 1
         * period : 12
         * periodUnit :
         * mbContactUrl :
         * pcContactUrl :
         * type :
         * productCode :
         * description : 优势：适合家庭大额闲置资金，且每月收益可以支付房贷、车贷、子女教育、养老等日常生活消费
         * pcProductInfo : &lt;div class=&quot;detailCaseLeftPic&quot;&gt;
         &lt;img src=&quot;/bespeakBg/attached/image/20160328/20160328152839_193.png&quot; alt=&quot;&quot; /&gt;&lt;br /&gt;
         &lt;/div&gt;
         &lt;div class=&quot;detailCaseRightTxt&quot;&gt;
         &lt;h3&gt;
         案例分析
         &lt;/h3&gt;
         &lt;div class=&quot;caseText&quot;&gt;
         万先生，45岁，从事高等教育工作，在北京有车有房并有一双儿女，子女都已20出头，万先生想为子女准备结婚费用。万先生每月收入20000元，平时写写文章年收入20000元，加上年终奖，年收入约在30万。现有闲散资金200万元，咨询了银行利息较低，万先生想找一家预期年化收益文件安全的公司，经过朋友介绍，来到了华夏信财公司，详细了解了华夏信财出借模式后决定让财富顾问做一个方案。
         &lt;/div&gt;
         &lt;h3 class=&quot;sugg&quot;&gt;
         出借目标
         &lt;/h3&gt;
         &lt;div class=&quot;caseText&quot;&gt;
         闲散资金，实现增值。
         &lt;/div&gt;
         &lt;h3 class=&quot;sugg&quot;&gt;
         出借建议
         &lt;/h3&gt;
         &lt;div class=&quot;caseText&quot;&gt;
         万先生有200万资金，因万先生子女年轻，可选择较长周期的出借模式。财富顾问推荐选择“定信宝B”，周期为24个月，24个月后本金加收益一共可以拿到254万元，如果万先生子女还未有结婚计划可选择续投，万先生年收入约为30万，每年可剩余20万，可在选择华信增出借模式。
         &lt;/div&gt;
         &lt;/div&gt;
         * wapProductInfo :
         * purpose : 购车使用
         * assure :
         * riskControl :
         * pdfContactUrl : http://192.168.11.48:8089/picture/contact/protocol50.pdf
         * htmlContactUrl : www
         */

        private String interestType;
        private int id;
        private String productKey;
        private String productName;
        private int periods;
        private double yearRate;
        private String increase;
        private int autoContinuedInvestment;
        private String exitTypeDes;
        private int joinFee;
        private int productBelong;
        private String productType;
        private int managerFee;
        private int exitFee;
        private float penalty;
        private String securityStyle;
        private String pictureSourceUrl;
        private String operator;
        private long operatorDate;
        private String isPcRefer;
        private String isMobileRefer;
        private String isRepeatBuy;
        private String isPreferred;
        private String isPlan;
        private String interestStartTime;
        private String redeemedDays;
        private String sort;
        private String isActivity;
        private String isInvited;
        private String isContinued;
        private int interestId;
        private int period;
        private String periodUnit;
        private String mbContactUrl;
        private String pcContactUrl;
        private String type;
        private String productCode;
        private String description;
        private String pcProductInfo;
        private String wapProductInfo;
        private String purpose;
        private String assure;
        private String riskControl;
        private String pdfContactUrl;
        private String htmlContactUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getPeriods() {
            return periods;
        }

        public void setPeriods(int periods) {
            this.periods = periods;
        }

        public double getYearRate() {
            return yearRate;
        }

        public void setYearRate(double yearRate) {
            this.yearRate = yearRate;
        }

        public String getIncrease() {
            return increase;
        }

        public void setIncrease(String increase) {
            this.increase = increase;
        }

        public int getAutoContinuedInvestment() {
            return autoContinuedInvestment;
        }

        public void setAutoContinuedInvestment(int autoContinuedInvestment) {
            this.autoContinuedInvestment = autoContinuedInvestment;
        }

        public String getExitTypeDes() {
            return exitTypeDes;
        }

        public void setExitTypeDes(String exitTypeDes) {
            this.exitTypeDes = exitTypeDes;
        }

        public int getJoinFee() {
            return joinFee;
        }

        public void setJoinFee(int joinFee) {
            this.joinFee = joinFee;
        }

        public int getProductBelong() {
            return productBelong;
        }

        public void setProductBelong(int productBelong) {
            this.productBelong = productBelong;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public int getManagerFee() {
            return managerFee;
        }

        public void setManagerFee(int managerFee) {
            this.managerFee = managerFee;
        }

        public int getExitFee() {
            return exitFee;
        }

        public void setExitFee(int exitFee) {
            this.exitFee = exitFee;
        }

        public float getPenalty() {
            return penalty;
        }

        public void setPenalty(float penalty) {
            this.penalty = penalty;
        }

        public String getSecurityStyle() {
            return securityStyle;
        }

        public void setSecurityStyle(String securityStyle) {
            this.securityStyle = securityStyle;
        }

        public String getPictureSourceUrl() {
            return pictureSourceUrl;
        }

        public void setPictureSourceUrl(String pictureSourceUrl) {
            this.pictureSourceUrl = pictureSourceUrl;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public long getOperatorDate() {
            return operatorDate;
        }

        public void setOperatorDate(long operatorDate) {
            this.operatorDate = operatorDate;
        }

        public String getIsPcRefer() {
            return isPcRefer;
        }

        public void setIsPcRefer(String isPcRefer) {
            this.isPcRefer = isPcRefer;
        }

        public String getIsMobileRefer() {
            return isMobileRefer;
        }

        public void setIsMobileRefer(String isMobileRefer) {
            this.isMobileRefer = isMobileRefer;
        }

        public String getIsRepeatBuy() {
            return isRepeatBuy;
        }

        public void setIsRepeatBuy(String isRepeatBuy) {
            this.isRepeatBuy = isRepeatBuy;
        }

        public String getIsPreferred() {
            return isPreferred;
        }

        public void setIsPreferred(String isPreferred) {
            this.isPreferred = isPreferred;
        }

        public String getIsPlan() {
            return isPlan;
        }

        public void setIsPlan(String isPlan) {
            this.isPlan = isPlan;
        }

        public String getInterestStartTime() {
            return interestStartTime;
        }

        public void setInterestStartTime(String interestStartTime) {
            this.interestStartTime = interestStartTime;
        }

        public String getRedeemedDays() {
            return redeemedDays;
        }

        public void setRedeemedDays(String redeemedDays) {
            this.redeemedDays = redeemedDays;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getIsActivity() {
            return isActivity;
        }

        public void setIsActivity(String isActivity) {
            this.isActivity = isActivity;
        }

        public String getIsInvited() {
            return isInvited;
        }

        public void setIsInvited(String isInvited) {
            this.isInvited = isInvited;
        }

        public String getIsContinued() {
            return isContinued;
        }

        public void setIsContinued(String isContinued) {
            this.isContinued = isContinued;
        }

        public int getInterestId() {
            return interestId;
        }

        public void setInterestId(int interestId) {
            this.interestId = interestId;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public String getPeriodUnit() {
            return periodUnit;
        }

        public void setPeriodUnit(String periodUnit) {
            this.periodUnit = periodUnit;
        }

        public String getMbContactUrl() {
            return mbContactUrl;
        }

        public void setMbContactUrl(String mbContactUrl) {
            this.mbContactUrl = mbContactUrl;
        }

        public String getPcContactUrl() {
            return pcContactUrl;
        }

        public void setPcContactUrl(String pcContactUrl) {
            this.pcContactUrl = pcContactUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPcProductInfo() {
            return pcProductInfo;
        }

        public void setPcProductInfo(String pcProductInfo) {
            this.pcProductInfo = pcProductInfo;
        }

        public String getWapProductInfo() {
            return wapProductInfo;
        }

        public void setWapProductInfo(String wapProductInfo) {
            this.wapProductInfo = wapProductInfo;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getAssure() {
            return assure;
        }

        public void setAssure(String assure) {
            this.assure = assure;
        }

        public String getRiskControl() {
            return riskControl;
        }

        public void setRiskControl(String riskControl) {
            this.riskControl = riskControl;
        }

        public String getPdfContactUrl() {
            return pdfContactUrl;
        }

        public void setPdfContactUrl(String pdfContactUrl) {
            this.pdfContactUrl = pdfContactUrl;
        }

        public String getHtmlContactUrl() {
            return htmlContactUrl;
        }

        public void setHtmlContactUrl(String htmlContactUrl) {
            this.htmlContactUrl = htmlContactUrl;
        }

        public String getInterestType() {
            return interestType;
        }

        public void setInterestType(String interestType) {
            this.interestType = interestType;
        }
    }
}
