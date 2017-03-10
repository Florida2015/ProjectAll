package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/27.
 *
 *
 */

public class ContractBillNewBean implements Serializable {

    private int currentTerm;
    private String billPath;

    public ContractBillNewBean() {
        super();
    }

    public int getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(int currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String getBillPath() {
        return billPath;
    }

    public void setBillPath(String billPath) {
        this.billPath = billPath;
    }

    @Override
    public String toString() {
        return "ContractBillNewBean{" +
                "currentTerm=" + currentTerm +
                ", billPath='" + billPath + '\'' +
                '}';
    }
}
