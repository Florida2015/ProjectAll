package com.huaxia.finance.model;

/**
 * 功能：注册
 * Created by houwen.lai on 2015/11/26.
 */
public class RegisterModel extends BaseModel{

    private String RespCode;
    private String RespDesc;
    private String accountId;

    public RegisterModel(){
        super();
    }

    public String getRespCode() {
        return RespCode;
    }

    public void setRespCode(String respCode) {
        RespCode = respCode;
    }

    public String getRespDesc() {
        return RespDesc;
    }

    public void setRespDesc(String respDesc) {
        RespDesc = respDesc;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "RespCode='" + RespCode + '\'' +
                ", RespDesc='" + RespDesc + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }

//    public static RegisterModel prase(String text){
//        return  DMApplication.getInstance().getGson().fromJson(text,new TypeToken<RegisterModel>(){}.getType());
//    }

}
