package com.hxxc.user.app.bean;

import java.io.Serializable;
import java.util.List;

public class FinancialPlanner implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2226877243888939883L;


    /**
     * fid : 2
     * financialno : JSNT-001
     * username : yanming
     * password : dc483e80a7a0bd9ef71d8cf973673924
     * fname : 严敏
     * icon : 17
     * nickname : 事业部经理
     * mobile : 13120986306
     * email : yanming01@huaxiafinan1ce.com
     * servicecount : 20
     * investmentamout : 24900009
     * department : 1
     * status : 1
     * homepagestatus : 0
     * homepagesort : 0
     * productpagestatus : 0
     * productpagesort : 0
     * citypagestatus : 0
     * citypagesort : 0
     * registerpagestatus : 0
     * registerpagesort : 0
     * token : HX4640824163839
     * devicetoken : AlSdoKO7uKK4sH6bZGAdNffsJEI2_BqCxDrgfkez5CMr
     * financialKey : 2
     * qrCodeUrl : /bespeakServices/pic/financial/qrcode/yanming.jpg
     * createTime : 1462096047000
     * roleId : 1
     * pid : 0
     * remarks : 1
     * level : 1
     * isOnline : 1
     * bdata : 15年工作经验，自营服装，销售涉及领域主要各大商场商品推广销售和房屋抵押贷款，曾任上海狄威实业有限公司南通区域营运经理。1998年毕业于南通中专计算机系。
     * serviceadvantage :
     * successfulcase :
     * sysDepartmentVo : {"did":1,"name":"凤凰文化广场第一财富中心","address":"江苏省南通市崇川区凤凰文化广场2幢19楼","picurl":"/bespeakBg/pic/department/20160413/201604131530470671.jpg","city":1197,"telephone":"0513-89075089","province":1,"departmentNum":1,"status":1,"pics":"17,17,17","areaCityVo":{"id":1197,"code":"31","name":"上海市","parentid":1195,"orders":1,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":{"id":1195,"code":"31","name":"上海市","parentid":1,"orders":2,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":""}},"sysAttachmentFileVo":{"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":"","operator":"","operateDate":"","segment":"","remarks":"","enabled":1,"bytes":""},"realPicUrl":"http://192.168.11.48:8089/picture/usericon/2016/11/9/9/1478654496007.jpg","sysDepartmentPicVos":[],"realPicUrls":""}
     * role :
     * sysAttachmentFileVo : {"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":"","operator":"","operateDate":"","segment":"","remarks":"","enabled":1,"bytes":""}
     * realIcon : http://192.168.11.48:8089/picture/usericon/2016/11/9/9/1478654496007.jpg
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
    private double investmentamout;
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
     * pics : 17,17,17
     * areaCityVo : {"id":1197,"code":"31","name":"上海市","parentid":1195,"orders":1,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":{"id":1195,"code":"31","name":"上海市","parentid":1,"orders":2,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":""}}
     * sysAttachmentFileVo : {"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":"","operator":"","operateDate":"","segment":"","remarks":"","enabled":1,"bytes":""}
     * realPicUrl : http://192.168.11.48:8089/picture/usericon/2016/11/9/9/1478654496007.jpg
     * sysDepartmentPicVos : []
     * realPicUrls :
     */

    private SysDepartmentVoBean sysDepartmentVo;
    private String role;
    /**
     * fileId : 17
     * locate : /usericon/2016/11/9/9/
     * fileName : 1477019905727.jpg
     * fileExtName : jpg
     * fileType : usericon
     * saveName : 1478654496007.jpg
     * refObject :
     * operator :
     * operateDate :
     * segment :
     * remarks :
     * enabled : 1
     * bytes :
     */

    private SysAttachmentFileVoBean sysAttachmentFileVo;
    private String realIcon;

    private String countryPhone;//400电话

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

    public double getInvestmentamout() {
        return investmentamout;
    }

    public void setInvestmentamout(double investmentamout) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public SysAttachmentFileVoBean getSysAttachmentFileVo() {
        return sysAttachmentFileVo;
    }

    public void setSysAttachmentFileVo(SysAttachmentFileVoBean sysAttachmentFileVo) {
        this.sysAttachmentFileVo = sysAttachmentFileVo;
    }

    public String getRealIcon() {
        return realIcon;
    }

    public void setRealIcon(String realIcon) {
        this.realIcon = realIcon;
    }

    public String getCountryPhone() {
        return countryPhone;
    }

    public void setCountryPhone(String countryPhone) {
        this.countryPhone = countryPhone;
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
        /**
         * fileId : 17
         * locate : /usericon/2016/11/9/9/
         * fileName : 1477019905727.jpg
         * fileExtName : jpg
         * fileType : usericon
         * saveName : 1478654496007.jpg
         * refObject :
         * operator :
         * operateDate :
         * segment :
         * remarks :
         * enabled : 1
         * bytes :
         */

        private SysAttachmentFileVoBean sysAttachmentFileVo;
        private String realPicUrl;
        private String realPicUrls;
        private List<?> sysDepartmentPicVos;

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

        public SysAttachmentFileVoBean getSysAttachmentFileVo() {
            return sysAttachmentFileVo;
        }

        public void setSysAttachmentFileVo(SysAttachmentFileVoBean sysAttachmentFileVo) {
            this.sysAttachmentFileVo = sysAttachmentFileVo;
        }

        public String getRealPicUrl() {
            return realPicUrl;
        }

        public void setRealPicUrl(String realPicUrl) {
            this.realPicUrl = realPicUrl;
        }

        public String getRealPicUrls() {
            return realPicUrls;
        }

        public void setRealPicUrls(String realPicUrls) {
            this.realPicUrls = realPicUrls;
        }

        public List<?> getSysDepartmentPicVos() {
            return sysDepartmentPicVos;
        }

        public void setSysDepartmentPicVos(List<?> sysDepartmentPicVos) {
            this.sysDepartmentPicVos = sysDepartmentPicVos;
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

        public static class SysAttachmentFileVoBean {
            private int fileId;
            private String locate;
            private String fileName;
            private String fileExtName;
            private String fileType;
            private String saveName;
            private String refObject;
            private String operator;
            private String operateDate;
            private String segment;
            private String remarks;
            private int enabled;
            private String bytes;

            public int getFileId() {
                return fileId;
            }

            public void setFileId(int fileId) {
                this.fileId = fileId;
            }

            public String getLocate() {
                return locate;
            }

            public void setLocate(String locate) {
                this.locate = locate;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getFileExtName() {
                return fileExtName;
            }

            public void setFileExtName(String fileExtName) {
                this.fileExtName = fileExtName;
            }

            public String getFileType() {
                return fileType;
            }

            public void setFileType(String fileType) {
                this.fileType = fileType;
            }

            public String getSaveName() {
                return saveName;
            }

            public void setSaveName(String saveName) {
                this.saveName = saveName;
            }

            public String getRefObject() {
                return refObject;
            }

            public void setRefObject(String refObject) {
                this.refObject = refObject;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getOperateDate() {
                return operateDate;
            }

            public void setOperateDate(String operateDate) {
                this.operateDate = operateDate;
            }

            public String getSegment() {
                return segment;
            }

            public void setSegment(String segment) {
                this.segment = segment;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public int getEnabled() {
                return enabled;
            }

            public void setEnabled(int enabled) {
                this.enabled = enabled;
            }

            public String getBytes() {
                return bytes;
            }

            public void setBytes(String bytes) {
                this.bytes = bytes;
            }
        }
    }

    public static class SysAttachmentFileVoBean {
        private int fileId;
        private String locate;
        private String fileName;
        private String fileExtName;
        private String fileType;
        private String saveName;
        private String refObject;
        private String operator;
        private String operateDate;
        private String segment;
        private String remarks;
        private int enabled;
        private String bytes;

        public int getFileId() {
            return fileId;
        }

        public void setFileId(int fileId) {
            this.fileId = fileId;
        }

        public String getLocate() {
            return locate;
        }

        public void setLocate(String locate) {
            this.locate = locate;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileExtName() {
            return fileExtName;
        }

        public void setFileExtName(String fileExtName) {
            this.fileExtName = fileExtName;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getSaveName() {
            return saveName;
        }

        public void setSaveName(String saveName) {
            this.saveName = saveName;
        }

        public String getRefObject() {
            return refObject;
        }

        public void setRefObject(String refObject) {
            this.refObject = refObject;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getOperateDate() {
            return operateDate;
        }

        public void setOperateDate(String operateDate) {
            this.operateDate = operateDate;
        }

        public String getSegment() {
            return segment;
        }

        public void setSegment(String segment) {
            this.segment = segment;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public String getBytes() {
            return bytes;
        }

        public void setBytes(String bytes) {
            this.bytes = bytes;
        }
    }
}