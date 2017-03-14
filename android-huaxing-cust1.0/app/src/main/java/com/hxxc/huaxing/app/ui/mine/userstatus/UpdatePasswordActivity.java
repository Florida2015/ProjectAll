package com.hxxc.huaxing.app.ui.mine.userstatus;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/26.
 * 修改密码 页
 */

public class UpdatePasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_set_old_pass)
    EditText ed_set_old_pass;
    @BindView(R.id.ed_set_new_pass)
    EditText ed_set_new_pass;
    @BindView(R.id.ed_set_new_pass_again)
    EditText ed_set_new_pass_again;

    @BindView(R.id.btn_updata_pass)
    FancyButton btn_updata_pass;

    @Override
    public int getLayoutId() {
        return R.layout.user_update_password;
    }

    @Override
    public void initView() {
        toolbar_title.setText("修改密码");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


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

    @OnClick(R.id.btn_updata_pass)
    public void onClick(View view){
        if (view.getId()==R.id.btn_updata_pass){

            getUpdataData();

        }
    }

    public void getUpdataData(){
        String oldPass = ed_set_old_pass.getText().toString().trim();
        String newPass = ed_set_new_pass.getText().toString().trim();
        String newPassAga = ed_set_new_pass_again.getText().toString().trim();

        if (TextUtils.isEmpty(oldPass)){
            toast("请输入原始密码");
            return;
        }
        if (TextUtils.isEmpty(newPass)){
            toast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(newPassAga)){
            toast("请输入确认密码");
            return;
        }

        if (!CommonUtil.isNumerLetter(newPass)){
            toast(R.string.text_pass_text);
            return;
        }
        if (!CommonUtil.isNumerLetter(newPassAga)){
            toast(R.string.text_pass_text_again);
            return;
        }

        if (!newPass.equals(newPassAga)){
            toast("两次密码输入不一致");
            return;
        }

        if (oldPass.equals(newPass)){
            toast("原始密码不能与新密码一致");
            return;
        }
        Api.getClient().getUpdatePass(Api.uid,oldPass,newPass,newPassAga).compose(RxApiThread.convert()).
                subscribe(new Subscriber<BaseBean<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toast("数据异常");
                    }

                    @Override
                    public void onNext(BaseBean<String> stringBaseBean) {
                        if (stringBaseBean.isSuccess()){
                            ToastUtil.ToastShort(mContext,"密码修改成功");
                            UpdatePasswordActivity.this.finish();
                        }else {
                            if (!TextUtils.isEmpty(stringBaseBean.getErrMsg()))  toast(stringBaseBean.getErrMsg());
                        }
                    }
                });
    }

}
