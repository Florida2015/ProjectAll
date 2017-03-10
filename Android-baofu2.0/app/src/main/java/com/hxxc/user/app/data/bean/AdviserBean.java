package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;

import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by houwen.lai on 2016/10/8.
 * 理财师顾问列表
 */

public class AdviserBean extends BaseEntity.ListBean {

    private int index = 0;
    @Override
    public Observable getPageAt(int page) {
//        index ++;
//        if(index == 2){
//            return Api.getClient().getFpListRandom(param.get("showType"),param.get("cityName"),param.get("searchKey")).compose(RxApiThread.convert());
//        }
        return Api.getClient().getFpList(param.get("showType"),param.get("cityName"),param.get("searchKey"),page, Constants.ROWS).compose(RxApiThread.convert());

    }

    /**
     * fid : 1
     * financialno : HX0000021
     * username : xiajincheng
     * password : e10adc3949ba59abbe56e057f20f883e
     * fname : 夏晋城
     * icon : /bespeakBg/pic/financial/Chrysanthemum.jpg
     * nickname : 理财师
     * mobile : 18221274393
     * email : 41493171@qq.com
     * servicecount : 52
     * investmentamout : 56000000
     * department : 1
     * status : 1
     * homepagestatus : 1
     * homepagesort : 0
     * productpagestatus : 1
     * productpagesort : 0
     * citypagestatus : 1
     * citypagesort : 0
     * registerpagestatus : 1
     * registerpagesort : 0
     * token : HX2860831164417
     * devicetoken : b1756c625e99ec8c3cb2918a60f9269ce7c1e6e45fb4747326f38977de241238
     * financialKey : 1
     * qrCodeUrl : /bespeakServices/pic/financial/qrcode/xiajincheng.jpg
     * createTime : 1462096041000
     * roleId : 1
     * pid : 0
     * remarks : 1
     * level : 1
     * isOnline : 1
     * bdata : 烦烦烦方法方法反反复复反复反复反复顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶灌灌灌灌灌灌灌灌灌灌
     * serviceadvantage :
     * successfulcase :
     * sysDepartmentVo : {"did":1,"name":"凤凰文化广场第一财富中心","address":"江苏省南通市崇川区凤凰文化广场2幢19楼","picurl":"/bespeakBg/pic/department/20160413/201604131530470671.jpg","city":1197,"telephone":"0513-89075089","province":1,"departmentNum":1,"status":1,"pics":"/caifu-back/pic/department/20160912/201609121721440441.jpg","areaCityVo":{"id":1197,"code":"31","name":"上海市","parentid":1195,"orders":1,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":{"id":1195,"code":"31","name":"上海市","parentid":1,"orders":2,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":""}}}
     */

    private int fid;
    private String financialno;
    private String username;
    private String password;
    private String fname;
    private String icon;
    private String nickname;
    private String mobile;
    private String email;
    private int servicecount;
    private BigDecimal investmentamout;
    private int department;
    private int status;
    private int homepagestatus;
    private int homepagesort;
    private int productpagestatus;
    private int productpagesort;
    private int citypagestatus;
    private int citypagesort;
    private int registerpagestatus;
    private int registerpagesort;
    private String token;
    private String devicetoken;
    private String financialKey;
    private String qrCodeUrl;
    private long createTime;
    private int roleId;
    private int pid;
    private String remarks;
    private int level;
    private int isOnline;
    private String bdata;
    private String serviceadvantage;
    private String successfulcase;
    private String realIcon;//t头像

    /**
     * did : 1
     * name : 凤凰文化广场第一财富中心
     * address : 江苏省南通市崇川区凤凰文化广场2幢19楼
     * picurl : /bespeakBg/pic/department/20160413/201604131530470671.jpg
     * city : 1197
     * telephone : 0513-89075089
     * province : 1
     * departmentNum : 1
     * status : 1
     * pics : /caifu-back/pic/department/20160912/201609121721440441.jpg
     * areaCityVo : {"id":1197,"code":"31","name":"上海市","parentid":1195,"orders":1,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":{"id":1195,"code":"31","name":"上海市","parentid":1,"orders":2,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":""}}
     */

    private SysDepartmentVoBean sysDepartmentVo;

    public String getRealIcon() {
        return realIcon;
    }

    public void setRealIcon(String realIcon) {
        this.realIcon = realIcon;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFinancialno() {
        return financialno;
    }

    public void setFinancialno(String financialno) {
        this.financialno = financialno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getServicecount() {
        return servicecount;
    }

    public void setServicecount(int servicecount) {
        this.servicecount = servicecount;
    }

    public BigDecimal getInvestmentamout() {
        return investmentamout;
    }

    public void setInvestmentamout(BigDecimal investmentamout) {
        this.investmentamout = investmentamout;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHomepagestatus() {
        return homepagestatus;
    }

    public void setHomepagestatus(int homepagestatus) {
        this.homepagestatus = homepagestatus;
    }

    public int getHomepagesort() {
        return homepagesort;
    }

    public void setHomepagesort(int homepagesort) {
        this.homepagesort = homepagesort;
    }

    public int getProductpagestatus() {
        return productpagestatus;
    }

    public void setProductpagestatus(int productpagestatus) {
        this.productpagestatus = productpagestatus;
    }

    public int getProductpagesort() {
        return productpagesort;
    }

    public void setProductpagesort(int productpagesort) {
        this.productpagesort = productpagesort;
    }

    public int getCitypagestatus() {
        return citypagestatus;
    }

    public void setCitypagestatus(int citypagestatus) {
        this.citypagestatus = citypagestatus;
    }

    public int getCitypagesort() {
        return citypagesort;
    }

    public void setCitypagesort(int citypagesort) {
        this.citypagesort = citypagesort;
    }

    public int getRegisterpagestatus() {
        return registerpagestatus;
    }

    public void setRegisterpagestatus(int registerpagestatus) {
        this.registerpagestatus = registerpagestatus;
    }

    public int getRegisterpagesort() {
        return registerpagesort;
    }

    public void setRegisterpagesort(int registerpagesort) {
        this.registerpagesort = registerpagesort;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getFinancialKey() {
        return financialKey;
    }

    public void setFinancialKey(String financialKey) {
        this.financialKey = financialKey;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getBdata() {
        return bdata;
    }

    public void setBdata(String bdata) {
        this.bdata = bdata;
    }

    public String getServiceadvantage() {
        return serviceadvantage;
    }

    public void setServiceadvantage(String serviceadvantage) {
        this.serviceadvantage = serviceadvantage;
    }

    public String getSuccessfulcase() {
        return successfulcase;
    }

    public void setSuccessfulcase(String successfulcase) {
        this.successfulcase = successfulcase;
    }

    public SysDepartmentVoBean getSysDepartmentVo() {
        return sysDepartmentVo;
    }

    public void setSysDepartmentVo(SysDepartmentVoBean sysDepartmentVo) {
        this.sysDepartmentVo = sysDepartmentVo;
    }


    public static class SysDepartmentVoBean {
        private int did;
        private String name;
        private String address;
        private String picurl;
        private int city;
        private String telephone;
        private int province;
        private int departmentNum;
        private int status;
        private String pics;
        /**
         * id : 1197
         * code : 31
         * name : 上海市
         * parentid : 1195
         * orders : 1
         * nameen : shanghai
         * shortnameen : shanghai
         * areaProvinceVo : {"id":1195,"code":"31","name":"上海市","parentid":1,"orders":2,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":""}
         */

        private AreaCityVoBean areaCityVo;

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getDepartmentNum() {
            return departmentNum;
        }

        public void setDepartmentNum(int departmentNum) {
            this.departmentNum = departmentNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public AreaCityVoBean getAreaCityVo() {
            return areaCityVo;
        }

        public void setAreaCityVo(AreaCityVoBean areaCityVo) {
            this.areaCityVo = areaCityVo;
        }



        public static class AreaCityVoBean {
            private int id;
            private String code;
            private String name;
            private int parentid;
            private int orders;
            private String nameen;
            private String shortnameen;
            /**
             * id : 1195
             * code : 31
             * name : 上海市
             * parentid : 1
             * orders : 2
             * nameen : shanghai
             * shortnameen : shanghai
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
    }
}
