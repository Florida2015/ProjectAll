package com.hxxc.huaxing.app.ui.mine.more;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.UpdateBean;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.OnUpdateAppListener;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.UpdateAppDialogFragment;
import com.hxxc.huaxing.app.ui.mine.appupdata.AppUpdataContract;
import com.hxxc.huaxing.app.ui.mine.appupdata.AppUpdataPresenter;
import com.hxxc.huaxing.app.ui.mine.userstatus.UpdatePasswordActivity;
import com.hxxc.huaxing.app.utils.ApplicationUtils;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/20.
 * 更多
 */

public class MineMoreActivity extends BaseActivity<AppUpdataPresenter,AppUpdataContract.Model> implements AppUpdataContract.View,OnUpdateAppListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.relative_version)
    RelativeLayout relative_version;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_service_phone)
    TextView tv_service_phone;

    @BindView(R.id.relative_updata_pass)
    RelativeLayout relative_updata_pass;
    @BindView(R.id.relative_serivce_phone)
    RelativeLayout relative_serivce_phone;


    @Override
    public int getLayoutId() {
        return R.layout.mine_more_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("更多");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        tv_version.setText(getVersion());
        tv_service_phone.setText(getResources().getString(R.string.text_service_mobile));

    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
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

    @OnClick({R.id.relative_version,R.id.relative_updata_pass,R.id.relative_serivce_phone})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.relative_version://版本
                if (BtnUtils.isFastDoubleClick()){
                    mPresenter.toAppUpdata(mContext,"about", ApplicationUtils.getVersionCode(this),UserInfoConfig.Type_User );
                }
                break;
            case R.id.relative_updata_pass://修改密码
                    if (BtnUtils.isFastDoubleClick()){
                        startActivity(new Intent(mContext, UpdatePasswordActivity.class));
                    }
                break;
            case R.id.relative_serivce_phone://拨打客服电话
                String phoneNum = (String) tv_service_phone.getText();
                Uri uri = Uri.parse("tel:" + phoneNum);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    UpdateBean updateBean;

    @Override
    public void onUpdateApk(int resId) {
        switch (resId){
            case R.id.btn_sure://
            case R.id.btn_fouse://强更
                if (updateBean!=null) {
                    UserInfoConfig.UPDATE_URL = updateBean.getUrl();
                    Intent mIntent = new Intent();//mContext,DownloadService.class
                    mIntent.setAction("com.huaxia.finance.service.start");//你定义的service的action
                    mIntent.setPackage(getPackageName());//这里你需要设置你应用的包名
                    mIntent.putExtra("url", updateBean.getUrl());
                    mIntent.putExtra("versionName", updateBean.getVersionName());
                    startService(mIntent);
                    LogUtil.d("api_下载apk,更新=" + updateBean.getUrl());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void toAppUpdata(String type, UpdateBean updateBean) {
        this.updateBean = updateBean;
        //判断是否升级
        if (updateBean.getIsForceUpdate()==1){//强更
            UpdateAppDialogFragment updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(true,updateBean.getContents(),updateBean.getVersionCode());
            updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");
        }else{
            if (updateBean.getIsUpdate().equals("1")){
                UpdateAppDialogFragment updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(false,updateBean.getContents(),updateBean.getVersionCode());
                updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");
            }
        }
    }

    @Override
    public void showMsg(String msg) {
        toast(msg);
    }
}
