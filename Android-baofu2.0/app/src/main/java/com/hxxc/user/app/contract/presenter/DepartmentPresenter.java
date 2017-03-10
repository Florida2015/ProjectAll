package com.hxxc.user.app.contract.presenter;

import android.text.TextUtils;

import com.hxxc.user.app.bean.Department;
import com.hxxc.user.app.contract.DepartmentContract;
import com.hxxc.user.app.rest.rx.SimpleCallback;

import java.util.List;

import rx.Subscription;

/**
 * Created by chenqun on 2016/6/27.
 */
public class DepartmentPresenter extends RxBasePresenter implements DepartmentContract.Presenter {
    private DepartmentContract.View mView;
    private String oldPosition;
    private int page = 1;

    public DepartmentPresenter(DepartmentContract.View view, String position) {
        this.oldPosition = position;
        this.mView = view;
    }

    @Override
    public void subscribe() {
        getDepartmentList(false);
    }

    @Override
    public void unsubscribe() {
        mView = null;
        super.unsubscribe();
    }

    @Override
    public void doReflush() {
        page = 1;
        getDepartmentList(false);
    }

    public void doReflush(String position) {
        page = 1;
        oldPosition = position;
        getDepartmentList(false);
    }

    @Override
    public void onLoadingmore() {
        page++;
        getDepartmentList(true);
    }

    private void getDepartmentList(boolean isLoadingmore) {
        if (TextUtils.isEmpty(oldPosition)) {
            mView.setDepartmentList(null, isLoadingmore);
            mView.onReflushEnd();
            return;
        }
        Subscription s = mApiManager.getDepartmentList(oldPosition, page + "",  "8", new SimpleCallback<List<Department>>() {
            @Override
            public void onNext(List<Department> departments) {
                mView.setDepartmentList(departments, isLoadingmore);
                mView.onReflushEnd();
            }

            @Override
            public void onError() {
                mView.setDepartmentList(null, isLoadingmore);
                mView.onReflushEnd();
            }
        });
    }

}
