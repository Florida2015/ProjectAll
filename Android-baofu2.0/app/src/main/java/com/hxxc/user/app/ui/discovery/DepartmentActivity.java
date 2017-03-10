package com.hxxc.user.app.ui.discovery;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Department;
import com.hxxc.user.app.contract.DepartmentContract;
import com.hxxc.user.app.contract.presenter.DepartmentPresenter;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.DepartmentAdapter;
import com.hxxc.user.app.ui.base.SwipeRefreshBaseActivity;
import com.hxxc.user.app.ui.discovery.search.SearchActivity;
import com.hxxc.user.app.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/10/25.
 */

public class DepartmentActivity extends SwipeRefreshBaseActivity implements DepartmentContract.View {
    public static final int requestCode = 11;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private DepartmentAdapter mAdapter;
    private boolean mIsLoadingmore;
    private DepartmentPresenter presenter;
    private String oldPosition;
    private Dialog dialog;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_department;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("服务网点");
    }

    @OnClick({R.id.position_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.position_text:
                Intent intent = new Intent(DepartmentActivity.this, SearchActivity.class);
                intent.putExtra("currCity", mPositionText.getText().toString());
                startActivityForResult(intent, requestCode);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oldPosition = SPUtils.geTinstance().get(Constants.SELECTED_CITY, "");
        if (TextUtils.isEmpty(oldPosition))
            oldPosition = SPUtils.geTinstance().get(Constants.LASTCITY, "");
        mPositionText.setVisibility(View.VISIBLE);
        mPositionText.setText(oldPosition);
        presenter = new DepartmentPresenter(this, oldPosition);
        setPresenter(presenter);
        setRefresh(true);
        initRecyclerView();

        if (TextUtils.isEmpty(oldPosition)) {
            mPositionText.post(new Runnable() {
                @Override
                public void run() {
                    openDialog();
                }
            });
        }
    }

    private void openDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(this, R.layout.dialog_hint, null);
            TextView btn_sure = (TextView) inflate.findViewById(R.id.btn_sure);
            btn_sure.setText("选择城市");
            TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
            tv_des.setText("获取当前位置信息失败");
            inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != dialog) {
                        dialog.dismiss();
                    }
                }
            });
            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DepartmentActivity.this, SearchActivity.class);
                    intent.putExtra("currCity", "");
                    startActivityForResult(intent, requestCode);
                    dialog.dismiss();
                }
            });
            dialog.setContentView(inflate);
        }
        dialog.show();
    }

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DepartmentAdapter(this, new ArrayList<Department>(), recyclerview);
        mAdapter.rows = 8;
        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                if (null != mAdapter.mList && mAdapter.mList.size() > 0) {
                    Intent intent = new Intent(DepartmentActivity.this, DepartmentDefaultActivity.class);
                    intent.putExtra("department", mAdapter.mList.get(tag));
                    startActivity(intent);
                }
            }

            @Override
            public void setOnItemLongClick(View view, int tag) {

            }
        });
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        presenter.onLoadingmore();
                    }
                }
            }
        });
    }

    @Override
    public void onReflushEnd() {
        setRefresh(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DepartmentActivity.requestCode && resultCode == RESULT_OK) {  //  部门界面根据城市刷新
            if (data != null) {
                String city = data.getStringExtra("city");
                mPositionText.setText(city);
                presenter.doReflush(city);
            }
        }
    }

    @Override
    public void setDepartmentList(List<Department> data, boolean isLoadingmore) {
        mAdapter.notifyDatasChanged(data, isLoadingmore);
        if (null != data && data.size() > 0) {
            empty_view.setVisibility(View.INVISIBLE);
        } else {
            if (!isLoadingmore) {
                empty_view.setVisibility(View.VISIBLE);
            }
        }
        mIsLoadingmore = false;
    }
}
