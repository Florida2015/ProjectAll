package com.hxxc.huaxing.app.ui.mine.notify;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.NotifyBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;

import butterknife.BindView;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/10/20.
 * 通知详情
 */

public class NotifyDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_notify_title)
    TextView tv_notify_title;
    @BindView(R.id.tv_notify_time)
    TextView tv_notify_time;
    @BindView(R.id.tv_notify_context)
    TextView tv_notify_context;

    private String msgId;

    @Override
    public int getLayoutId() {
        return R.layout.notifiy_detail_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("通知详情");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        msgId = getIntent().getStringExtra("msgId");

        getNotifyDetail(msgId);

    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNotifyDetail(NotifyBean bean){
        tv_notify_title.setText(bean.getTitle());
        tv_notify_time.setText(bean.getCreateTimeStr());
        tv_notify_context.setText(bean.getContent());
    }

    public void getNotifyDetail(String msgId){

        Api.getClient().getReadUserMsg(Api.uid,msgId).compose(RxApiThread.convert()).
                subscribe(new Subscriber<BaseBean<NotifyBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toast("数据异常");
                    }

                    @Override
                    public void onNext(BaseBean<NotifyBean> stringBaseBean) {
                        if (stringBaseBean.isSuccess()){
                            setNotifyDetail(stringBaseBean.getModel());
                        }else {
                            toast("");
                        }
                    }
                });

    }

}
