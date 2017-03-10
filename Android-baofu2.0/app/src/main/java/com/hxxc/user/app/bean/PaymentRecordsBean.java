package com.hxxc.user.app.bean;

import java.math.BigDecimal;

/**
 * Created by chenqun on 2017/2/22.
 */

public class PaymentRecordsBean {

    /**
     * returnPrincipalCount : 260000.0
     * returnPaymentCount : 24997.76
     */

    private BigDecimal returnPrincipalCount;
    private BigDecimal returnPaymentCount;

    public BigDecimal getReturnPrincipalCount() {
        return returnPrincipalCount;
    }

    public void setReturnPrincipalCount(BigDecimal returnPrincipalCount) {
        this.returnPrincipalCount = returnPrincipalCount;
    }

    public BigDecimal getReturnPaymentCount() {
        return returnPaymentCount;
    }

    public void setReturnPaymentCount(BigDecimal returnPaymentCount) {
        this.returnPaymentCount = returnPaymentCount;
    }
}
