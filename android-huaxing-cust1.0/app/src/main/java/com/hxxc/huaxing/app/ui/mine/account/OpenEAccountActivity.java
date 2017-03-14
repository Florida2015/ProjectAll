package com.hxxc.huaxing.app.ui.mine.account;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.huaxing.app.HttpConfig;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.OpenAccountBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.WebActivity;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.IDCardUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/23.
 * 开通E账户
 */
public class OpenEAccountActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_e_name)
    EditText ed_e_name;
    @BindView(R.id.ed_e_card)
    EditText ed_e_card;

    @BindView(R.id.btn_open_e)
    FancyButton btn_open_e;

    @Override
    public int getLayoutId() {
        return R.layout.e_account;
    }

    @Override
    public void initView() {
        toolbar_title.setText("开通E账户");
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

    @OnClick(R.id.btn_open_e)
    public void onClick(View view){
        //开通@账户
        if (view.getId()==R.id.btn_open_e){
//            LogUtil.d("开通@账户");
            RequestEOpen();
        }
    }

    public void RequestEOpen(){
        String name = ed_e_name.getText().toString().trim();
        String card = ed_e_card.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            toast("请输入真实姓名");
            return;
        }

        if (!StringUtil.getNameIs(name)){
            toast("请输入真实姓名");
            return;
        }

        if (TextUtils.isEmpty(card)){
            toast("请输入身份证号");
            return;
        }

        if (!IDCardUtil.IDCardValidate_b(card)){
            toast("身份证格式错误");
            return;
        }

        Api.getClient().getUserOpenAccount(Api.uid,name,card).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<OpenAccountBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                toast("数据异常");
            }

            @Override
            public void onNext(BaseBean<OpenAccountBean> baseBean) {
                LogUtil.d("open_account = "+baseBean);
                if (baseBean.isSuccess()){

//                    startActivity(new Intent(OpenEAccountActivity.this,EAcountActivity.class));
//                    startActivity(new Intent(OpenEAccountActivity.this,WebActivity.class).putExtra("data",stringBaseBean).putExtra("title","开通E账户"));

                    if (!TextUtils.isEmpty(baseBean.getModel().getBaseUrl()))
                    startActivity(new Intent(OpenEAccountActivity.this,WebOpenEaccountActivity.class).
                            putExtra("url",baseBean.getModel().getBaseUrl()).putExtra("title","开户关联").
                            putExtra("data",baseBean.getModel().getHtmlStr()).putExtra("flag",false));

//                    finish();

                }else{
                    toast(baseBean.getErrMsg());
                }
            }
        });

    }



}
