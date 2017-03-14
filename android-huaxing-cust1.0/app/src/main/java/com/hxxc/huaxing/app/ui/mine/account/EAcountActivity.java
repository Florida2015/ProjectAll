package com.hxxc.huaxing.app.ui.mine.account;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.AssetsBean;
import com.hxxc.huaxing.app.data.bean.BankItemBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.OpenAccountBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.LoginActivity;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.hxxc.huaxing.app.wedgit.LoadingView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/26.
 *  E 账户
 */

public class EAcountActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.listview_e_account)
    ListView listview;

    public EAccountAdapter eAccountAdapter;

    private Dialog dialog2;

    @Override
    public int getLayoutId() {
        return R.layout.e_acount_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("E账户");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getEaccountInfo();

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

    public View view_add;//添加银行卡
    public ImageView img_e_account_add_icon;
    public TextView tv_e_account_add_type;
    public List<AssetsBean> lists = new ArrayList<AssetsBean>();

    public void IntintData(AssetsBean assetsBean){
        if (lists==null)lists = new ArrayList<AssetsBean>();
        lists.clear();

        AssetsBean bank = new AssetsBean();
        if (assetsBean==null){
            bank.setAcName("");
            bank.setAcNo("");
            bank.setBindcardStatus("0");
        }else{
            bank.setAcName(TextUtils.isEmpty(assetsBean.getAcName())?"":assetsBean.getAcName());
            bank.setAcNo(TextUtils.isEmpty(assetsBean.getAcNo())?"":assetsBean.getAcNo());
            bank.setBindcardStatus(assetsBean.getBindcardStatus());
        }

        lists.add(bank);

        view_add = View.inflate(mContext,R.layout.e_account_add_bank,null);//点击添加银行卡
        img_e_account_add_icon = (ImageView) view_add.findViewById(R.id.img_e_account_add_icon);
        tv_e_account_add_type= (TextView) view_add.findViewById(R.id.tv_e_account_add_type);
        view_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加银行卡
                LogUtil.d("添加银行卡");
                if (bank!=null&&!TextUtils.isEmpty(bank.getBindcardStatus())&&bank.getBindcardStatus().equals("0")){
                    getEaccountBind();

                }
            }
        });
        if (bank!=null&&!TextUtils.isEmpty(bank.getBindcardStatus())&&bank.getBindcardStatus().equals("1")){//已绑卡
            img_e_account_add_icon.setImageResource(R.mipmap.ico_binding);
            tv_e_account_add_type.setText("已绑定银行卡");
        }else {
            img_e_account_add_icon.setImageResource(R.mipmap.ico_addition);
            tv_e_account_add_type.setText("点击绑定银行卡");
        }

        listview.addFooterView(view_add);
        eAccountAdapter = new EAccountAdapter(mContext,lists,R.layout.e_account_item);
        listview.setAdapter(eAccountAdapter);

    }

    /**
     * adapter 适配
     *
     */
    public class EAccountAdapter extends CommonAdapter<AssetsBean> {


        public EAccountAdapter(Context context, List<AssetsBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, AssetsBean item, int position) {
            super.convert(helper, item, position);

                ((ImageView) helper.getView(R.id.img_icon_bank)).setImageResource(R.mipmap.icon_huaxing_logo);
//             Picasso.with(mContext)
//                    .load("url")
//                    .placeholder(R.mipmap.icon_huaxing_logo)
//                    .error(R.mipmap.icon_huaxing_logo)
//                    .into(((ImageView) helper.getView(R.id.img_icon_bank)));

            helper.setText(R.id.tv_e_account_name, item.getAcName());
            helper.setText(R.id.tv_e_account_bank_name, "广东华兴银行");
            helper.setText(R.id.tv_e_account_bank, "E账户账号：" + item.getAcNo());


        }
    }

    /**
     * 获取E账户信息
     */
    public void getEaccountInfo(){
        showMyDialog();
        Api.getClient().getUserAssets(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<AssetsBean>(mContext) {
                    @Override
                    public void onSuccess(AssetsBean assetsBean) {
                        closeDialog();
                        IntintData(assetsBean);
                    }

                    @Override
                    public void onFail(String fail) {
                        closeDialog();
                        IntintData(null);
                        if (!TextUtils.isEmpty(fail))
                            toast(fail);
                    }
                }
            );

    }

    /**
     * E账户绑卡
     */
    public void getEaccountBind(){
        showMyDialog();
        Api.getClient().getUserBindCard(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<OpenAccountBean>(mContext) {
                    @Override
                    public void onSuccess(OpenAccountBean openAccountBean) {
                        closeDialog();
                        //跳转华兴h5
                        if (openAccountBean!=null&&!TextUtils.isEmpty(openAccountBean.getBaseUrl()))
                            startActivity(new Intent(EAcountActivity.this, WebOpenEaccountActivity.class).
                                    putExtra("url", openAccountBean.getBaseUrl()).
                                    putExtra("title", "绑卡").
                                    putExtra("data", openAccountBean.getHtmlStr()).
                                    putExtra("flag",false));
                    }

                    @Override
                    public void onFail(String fail) {
                        closeDialog();
                        if (!TextUtils.isEmpty(fail)) toast(fail);
                    }
                });
    }

    private void showMyDialog() {
        if (dialog2!=null&&dialog2.isShowing())return;
        dialog2 = new Dialog(this, R.style.loadingDialogTheme);
        dialog2.setContentView(new LoadingView(this));
        dialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialog2.show();
    }

    private void closeDialog() {
        if (null != dialog2) {
            if (dialog2.isShowing()) {
                dialog2.dismiss();
            }
        }
    }

}
