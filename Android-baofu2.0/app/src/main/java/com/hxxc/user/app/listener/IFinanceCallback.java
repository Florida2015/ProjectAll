package com.hxxc.user.app.listener;

import com.hxxc.user.app.bean.FinancialPlanner;

/**
 * Created by chenqun on 2016/9/6.
 */
public interface IFinanceCallback {
    void refreshFinance(FinancialPlanner financialPlanner);
}
