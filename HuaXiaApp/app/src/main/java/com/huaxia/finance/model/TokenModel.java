package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/2.
 */
public class TokenModel implements Serializable {

    private String token;

    public TokenModel() {
        super();
    }

    public TokenModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "token='" + token + '\'' +
                '}';
    }
}
