package com.hxxc.user.app.contract;

import com.hxxc.user.app.BaseView;
import com.hxxc.user.app.bean.Department;

import java.util.List;

/**
 * Created by chenqun on 2016/6/27.
 */
public interface DepartmentContract {

    interface View extends BaseView {

        void setDepartmentList(List<Department> data,boolean isLoadingmore);
    }

    interface Presenter {
        void doReflush(String position);
        void onLoadingmore();
    }
}
