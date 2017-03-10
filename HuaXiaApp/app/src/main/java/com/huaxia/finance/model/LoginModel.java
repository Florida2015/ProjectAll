package com.huaxia.finance.model;

/**
 * 功能：登录model
 * Created by houwen.lai on 2015/11/26.
 */
public class LoginModel extends BaseModel {

    private String backUrl;
    private String message_login;

    public LoginModel(){
        super();
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getMessage_login() {
        return message_login;
    }

    public void setMessage_login(String message_login) {
        this.message_login = message_login;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "backUrl='" + backUrl + '\'' +
                ", message_login='" + message_login + '\'' +
                '}';
    }

//    public static LoginModel prase(String text){
//        return  DMApplication.getInstance().getGson().fromJson(text,new TypeToken<LoginModel>(){}.getType());
//    }
}
