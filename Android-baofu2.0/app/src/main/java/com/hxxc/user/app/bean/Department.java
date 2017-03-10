package com.hxxc.user.app.bean;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7987077902820489738L;


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
     * areaCityVo : {"id":1197,"code":"31","name":"上海市","parentid":1195,"orders":1,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":{"id":1195,"code":"31","name":"上海市","parentid":1,"orders":2,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":null}}
     * sysAttachmentFileVo : null
     * realPicUrl :
     * sysDepartmentPicVos : [{"id":1,"did":1,"fileId":17,"remarks":"aa","sysAttachmentFileVo":{"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":"","operator":"","operateDate":"","segment":"","remarks":"","enabled":1,"bytes":""}},{"id":2,"did":1,"fileId":17,"remarks":"aa","sysAttachmentFileVo":{"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":"","operator":"","operateDate":"","segment":"","remarks":"","enabled":1,"bytes":""}}]
     * realPicUrls : ["http://192.168.11.48:8089/picture/usericon/2016/11/9/9/1478654496007.jpg","http://192.168.11.48:8089/picture/usericon/2016/11/9/9/1478654496007.jpg"]
     */

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
     * areaProvinceVo : {"id":1195,"code":"31","name":"上海市","parentid":1,"orders":2,"nameen":"shanghai","shortnameen":"shanghai","areaProvinceVo":null}
     */

    private AreaCityVoBean areaCityVo;
    private Object sysAttachmentFileVo;
    private String realPicUrl;
    /**
     * id : 1
     * did : 1
     * fileId : 17
     * remarks : aa
     * sysAttachmentFileVo : {"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":"","operator":"","operateDate":"","segment":"","remarks":"","enabled":1,"bytes":""}
     */

    private List<SysDepartmentPicVosBean> sysDepartmentPicVos;
    private List<String> realPicUrls;

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

    public Object getSysAttachmentFileVo() {
        return sysAttachmentFileVo;
    }

    public void setSysAttachmentFileVo(Object sysAttachmentFileVo) {
        this.sysAttachmentFileVo = sysAttachmentFileVo;
    }

    public String getRealPicUrl() {
        return realPicUrl;
    }

    public void setRealPicUrl(String realPicUrl) {
        this.realPicUrl = realPicUrl;
    }

    public List<SysDepartmentPicVosBean> getSysDepartmentPicVos() {
        return sysDepartmentPicVos;
    }

    public void setSysDepartmentPicVos(List<SysDepartmentPicVosBean> sysDepartmentPicVos) {
        this.sysDepartmentPicVos = sysDepartmentPicVos;
    }

    public List<String> getRealPicUrls() {
        return realPicUrls;
    }

    public void setRealPicUrls(List<String> realPicUrls) {
        this.realPicUrls = realPicUrls;
    }

    public static class AreaCityVoBean implements Serializable{
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
         * areaProvinceVo : null
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

        public static class AreaProvinceVoBean implements Serializable{
            private int id;
            private String code;
            private String name;
            private int parentid;
            private int orders;
            private String nameen;
            private String shortnameen;
            private Object areaProvinceVo;

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

            public Object getAreaProvinceVo() {
                return areaProvinceVo;
            }

            public void setAreaProvinceVo(Object areaProvinceVo) {
                this.areaProvinceVo = areaProvinceVo;
            }
        }
    }

    public static class SysDepartmentPicVosBean implements Serializable{
        private int id;
        private int did;
        private int fileId;
        private String remarks;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public int getFileId() {
            return fileId;
        }

        public void setFileId(int fileId) {
            this.fileId = fileId;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public SysAttachmentFileVoBean getSysAttachmentFileVo() {
            return sysAttachmentFileVo;
        }

        public void setSysAttachmentFileVo(SysAttachmentFileVoBean sysAttachmentFileVo) {
            this.sysAttachmentFileVo = sysAttachmentFileVo;
        }

        public static class SysAttachmentFileVoBean implements Serializable{
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
}
