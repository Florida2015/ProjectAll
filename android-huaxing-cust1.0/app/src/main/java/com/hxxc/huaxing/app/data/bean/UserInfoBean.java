package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/9.
 * 用户的信息
 */

public class UserInfoBean implements Serializable {

    /**
     * uid : 52
     * userNo : HX521012150068
     * mobile : 15250464603
     * email :
     * gender : 0
     * birthday : 1453824000000
     * password :
     * payPassword :
     * nationality :
     * marriageStatus : 0
     * occupation : hhh
     * address : 流量监控哦考虑考虑咯哦哦流量监控看看咯啦
     * documentType :
     * status : 1
     * token : ade7c64180cd805fc322d352b8e8877f52
     * fid : 2
     * deviceToken :
     * registerTime : 1476255622000
     * channel : 2
     * invitedCode : 521012150074
     * qrCodeUrl :
     * rnaStatus : 0
     * bindcardStatus : 0
     * customerType :
     * referUid :
     * createDate : 1476255622000
     * lockToTime :
     * loginCount : 8
     * nextLoginTime : 1476328507000
     * lastLoginTime : 1476778888000
     * isOpenEaccount : 0
     * isAutoBinAuth : 0
     * userName :
     * identityCard :
     * liveAreaInfoId : 254
     * hrAreaInfoId : 59
     * domicile : aaaa
     * education : 4
     * icon : http://192.168.11.93:808013
     * identityCardNo :
     * userNameNo :
     * birthdayStr : 2016-01-27
     * nextLoginTimeStr : 2016-10-13 11:15:07
     * liveAreaInfoVo : {"id":254,"areaCode":"450200","areaName":"柳州市","pid":"450000","sort":null,"pAreaInfoVo":{"id":21,"areaCode":"450000","areaName":"广西壮族自治区","pid":"","sort":"","pAreaInfoVo":"","cityAreaInfoVos":""},"cityAreaInfoVos":""}
     * hrAreaInfoVo : {"id":59,"areaCode":"150100","areaName":"呼和浩特市","pid":"150000","sort":"","pAreaInfoVo":{"id":6,"areaCode":"150000","areaName":"内蒙古自治区","pid":"000000","sort":"","pAreaInfoVo":"","cityAreaInfoVos":""},"cityAreaInfoVos":""}
     * genderStr : 男
     * marriageStatusStr : 未婚
     * documentTypeStr :
     * rnaStatusStr : 尚未认证
     * bindcardStatusStr : 未绑定
     * isOpenEaccountStr : 未开通
     * educationStr : 硕士及以上
     */

    private int uid;
    private String userNo;
    private String mobile;
    private String email;
    private int gender;
    private long birthday;
    private String password;
    private String payPassword;
    private String nationality;
    private int marriageStatus;
    private String occupation;
    private String address;
    private String documentType;
    private int status;
    private String token;
    private int fid;
    private String deviceToken;
    private long registerTime;
    private String channel;
    private String invitedCode;
    private String qrCodeUrl;
    private int rnaStatus;
    private int bindcardStatus;
    private String customerType;
    private String referUid;
    private long createDate;
    private String lockToTime;
    private int loginCount;
    private long nextLoginTime;
    private long lastLoginTime;
    private int isOpenEaccount;
    private int isAutoBinAuth;
    private String userName;
    private String identityCard;
    private int liveAreaInfoId;
    private int hrAreaInfoId;
    private String domicile;
    private int education;
    private String icon;
    private String identityCardNo;
    private String userNameNo;
    private String birthdayStr;
    private String nextLoginTimeStr;
    /**
     * id : 254
     * areaCode : 450200
     * areaName : 柳州市
     * pid : 450000
     * sort : null
     * pAreaInfoVo : {"id":21,"areaCode":"450000","areaName":"广西壮族自治区","pid":"","sort":"","pAreaInfoVo":"","cityAreaInfoVos":""}
     * cityAreaInfoVos :
     */

    private LiveAreaInfoVoBean liveAreaInfoVo;
    /**
     * id : 59
     * areaCode : 150100
     * areaName : 呼和浩特市
     * pid : 150000
     * sort :
     * pAreaInfoVo : {"id":6,"areaCode":"150000","areaName":"内蒙古自治区","pid":"000000","sort":"","pAreaInfoVo":"","cityAreaInfoVos":""}
     * cityAreaInfoVos :
     */

    private HrAreaInfoVoBean hrAreaInfoVo;
    private String genderStr;
    private String marriageStatusStr;
    private String documentTypeStr;
    private String rnaStatusStr;
    private String bindcardStatusStr;
    private String isOpenEaccountStr;
    private String educationStr;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(int marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public int getRnaStatus() {
        return rnaStatus;
    }

    public void setRnaStatus(int rnaStatus) {
        this.rnaStatus = rnaStatus;
    }

    public int getBindcardStatus() {
        return bindcardStatus;
    }

    public void setBindcardStatus(int bindcardStatus) {
        this.bindcardStatus = bindcardStatus;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getReferUid() {
        return referUid;
    }

    public void setReferUid(String referUid) {
        this.referUid = referUid;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getLockToTime() {
        return lockToTime;
    }

    public void setLockToTime(String lockToTime) {
        this.lockToTime = lockToTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public long getNextLoginTime() {
        return nextLoginTime;
    }

    public void setNextLoginTime(long nextLoginTime) {
        this.nextLoginTime = nextLoginTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getIsOpenEaccount() {
        return isOpenEaccount;
    }

    public void setIsOpenEaccount(int isOpenEaccount) {
        this.isOpenEaccount = isOpenEaccount;
    }

    public int getIsAutoBinAuth() {
        return isAutoBinAuth;
    }

    public void setIsAutoBinAuth(int isAutoBinAuth) {
        this.isAutoBinAuth = isAutoBinAuth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public int getLiveAreaInfoId() {
        return liveAreaInfoId;
    }

    public void setLiveAreaInfoId(int liveAreaInfoId) {
        this.liveAreaInfoId = liveAreaInfoId;
    }

    public int getHrAreaInfoId() {
        return hrAreaInfoId;
    }

    public void setHrAreaInfoId(int hrAreaInfoId) {
        this.hrAreaInfoId = hrAreaInfoId;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public String getUserNameNo() {
        return userNameNo;
    }

    public void setUserNameNo(String userNameNo) {
        this.userNameNo = userNameNo;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getNextLoginTimeStr() {
        return nextLoginTimeStr;
    }

    public void setNextLoginTimeStr(String nextLoginTimeStr) {
        this.nextLoginTimeStr = nextLoginTimeStr;
    }

    public LiveAreaInfoVoBean getLiveAreaInfoVo() {
        return liveAreaInfoVo;
    }

    public void setLiveAreaInfoVo(LiveAreaInfoVoBean liveAreaInfoVo) {
        this.liveAreaInfoVo = liveAreaInfoVo;
    }

    public HrAreaInfoVoBean getHrAreaInfoVo() {
        return hrAreaInfoVo;
    }

    public void setHrAreaInfoVo(HrAreaInfoVoBean hrAreaInfoVo) {
        this.hrAreaInfoVo = hrAreaInfoVo;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public String getMarriageStatusStr() {
        return marriageStatusStr;
    }

    public void setMarriageStatusStr(String marriageStatusStr) {
        this.marriageStatusStr = marriageStatusStr;
    }

    public String getDocumentTypeStr() {
        return documentTypeStr;
    }

    public void setDocumentTypeStr(String documentTypeStr) {
        this.documentTypeStr = documentTypeStr;
    }

    public String getRnaStatusStr() {
        return rnaStatusStr;
    }

    public void setRnaStatusStr(String rnaStatusStr) {
        this.rnaStatusStr = rnaStatusStr;
    }

    public String getBindcardStatusStr() {
        return bindcardStatusStr;
    }

    public void setBindcardStatusStr(String bindcardStatusStr) {
        this.bindcardStatusStr = bindcardStatusStr;
    }

    public String getIsOpenEaccountStr() {
        return isOpenEaccountStr;
    }

    public void setIsOpenEaccountStr(String isOpenEaccountStr) {
        this.isOpenEaccountStr = isOpenEaccountStr;
    }

    public String getEducationStr() {
        return educationStr;
    }

    public void setEducationStr(String educationStr) {
        this.educationStr = educationStr;
    }

    public static class LiveAreaInfoVoBean {
        private int id;
        private String areaCode;
        private String areaName;
        private String pid;
        private Object sort;
        /**
         * id : 21
         * areaCode : 450000
         * areaName : 广西壮族自治区
         * pid :
         * sort :
         * pAreaInfoVo :
         * cityAreaInfoVos :
         */

        private PAreaInfoVoBean pAreaInfoVo;
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

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public PAreaInfoVoBean getPAreaInfoVo() {
            return pAreaInfoVo;
        }

        public void setPAreaInfoVo(PAreaInfoVoBean pAreaInfoVo) {
            this.pAreaInfoVo = pAreaInfoVo;
        }

        public String getCityAreaInfoVos() {
            return cityAreaInfoVos;
        }

        public void setCityAreaInfoVos(String cityAreaInfoVos) {
            this.cityAreaInfoVos = cityAreaInfoVos;
        }

        public static class PAreaInfoVoBean {
            private int id;
            private String areaCode;
            private String areaName;
            private String pid;
            private String sort;
            private String pAreaInfoVo;
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

            public String getPAreaInfoVo() {
                return pAreaInfoVo;
            }

            public void setPAreaInfoVo(String pAreaInfoVo) {
                this.pAreaInfoVo = pAreaInfoVo;
            }

            public String getCityAreaInfoVos() {
                return cityAreaInfoVos;
            }

            public void setCityAreaInfoVos(String cityAreaInfoVos) {
                this.cityAreaInfoVos = cityAreaInfoVos;
            }
        }
    }

    public static class HrAreaInfoVoBean {
        private int id;
        private String areaCode;
        private String areaName;
        private String pid;
        private String sort;
        /**
         * id : 6
         * areaCode : 150000
         * areaName : 内蒙古自治区
         * pid : 000000
         * sort :
         * pAreaInfoVo :
         * cityAreaInfoVos :
         */

        private PAreaInfoVoBean pAreaInfoVo;
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

        public PAreaInfoVoBean getPAreaInfoVo() {
            return pAreaInfoVo;
        }

        public void setPAreaInfoVo(PAreaInfoVoBean pAreaInfoVo) {
            this.pAreaInfoVo = pAreaInfoVo;
        }

        public String getCityAreaInfoVos() {
            return cityAreaInfoVos;
        }

        public void setCityAreaInfoVos(String cityAreaInfoVos) {
            this.cityAreaInfoVos = cityAreaInfoVos;
        }

        public static class PAreaInfoVoBean {
            private int id;
            private String areaCode;
            private String areaName;
            private String pid;
            private String sort;
            private String pAreaInfoVo;
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

            public String getPAreaInfoVo() {
                return pAreaInfoVo;
            }

            public void setPAreaInfoVo(String pAreaInfoVo) {
                this.pAreaInfoVo = pAreaInfoVo;
            }

            public String getCityAreaInfoVos() {
                return cityAreaInfoVos;
            }

            public void setCityAreaInfoVos(String cityAreaInfoVos) {
                this.cityAreaInfoVos = cityAreaInfoVos;
            }
        }
    }
}
