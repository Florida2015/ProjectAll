package com.hxxc.huaxing.app.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huaxiafinance.lc.pickerview.OptionsPickerView;
import com.huaxiafinance.lc.pickerview.TimePickerView;
import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.Province;
import com.hxxc.huaxing.app.data.bean.Type;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.dialogfragment.DialogMineOpenEFragment;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.OnOpenEListener;
import com.hxxc.huaxing.app.ui.mine.account.OpenEAccountActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.UserInfoV;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.wedgit.CircleImageView;
import com.hxxc.huaxing.app.wedgit.CustomUploadBoard;
import com.hxxc.huaxing.app.wedgit.LeftAndRightTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/22.
 * 个人信息页面
 */
public class MineInformationActivity extends BaseActivity implements UserInfoV, OnOpenEListener {
    public static final int PERMISSION_CAMERA = 11;
    public static final int FROM_CAMERA = 8;
    public static final int FROM_PICTURE = 9;

    public static final int Request_Other = 3;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.user_icon_userinfo)
    CircleImageView user_icon_userinfo;

    @BindView(R.id.relative_name)
    LeftAndRightTextView relative_name;
    @BindView(R.id.relative_depository)
    LeftAndRightTextView relative_depository;
    @BindView(R.id.relative_bank)
    LeftAndRightTextView relative_bank;

    @BindView(R.id.relative_sex)
    LeftAndRightTextView relative_sex;
    @BindView(R.id.relative_birthday)
    LeftAndRightTextView relative_birthday;
    @BindView(R.id.ll_household_register)
    LeftAndRightTextView ll_household_register;
    @BindView(R.id.ll_household_address)
    LeftAndRightTextView ll_household_address;


    @BindView(R.id.relative_edcation)
    LeftAndRightTextView relative_edcation;
    @BindView(R.id.department_layout)
    LeftAndRightTextView department_layout;
    @BindView(R.id.ll_occupation)
    LeftAndRightTextView ll_occupation;
    @BindView(R.id.ll_residence)
    LeftAndRightTextView ll_residence;
    @BindView(R.id.ll_residence_address)
    LeftAndRightTextView ll_residence_address;

    private CustomUploadBoard customUploadBoard;
    private OptionsPickerView mPickerView;

    private ArrayList<Province> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<Province.CityAreaInfoVosBean>> options2Items = new ArrayList<>();
    private UserInfoPresenter mPresenter;
    private TimePickerView mTimePicker;
    private UserInfoBean mUserInfo;
    private OptionsPickerView mSexPicker;
    private OptionsPickerView mMarriagePicker;
    private OptionsPickerView mEducationPicker;

    private ArrayList<Type> marriageItem = new ArrayList<>();
    private ArrayList<Type> educationItem = new ArrayList<>();
    private ArrayList<Type> sexItem = new ArrayList<>();
    private OptionsPickerView mPickerView2;

    @Override
    public int getLayoutId() {
        return R.layout.mine_information;
    }

    @Override
    public void initView() {
        toolbar_title.setText("个人信息");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        try {
            initCache();
            mUserInfo = SharedPreUtils.getInstanse().getUserInfo(this);
            InitData();
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            finish();
        }
    }

    //获取缓存
    private void initCache() {
        String cacheSex = SharedPreUtils.getInstanse().getKeyValue(this, UserInfoConfig.Cache_Sex);
        String cacheProvince = SharedPreUtils.getInstanse().getKeyValue(this, UserInfoConfig.Cache_Province);
        String cacheEducation = SharedPreUtils.getInstanse().getKeyValue(this, UserInfoConfig.Cache_Education);
        String cacheMarriage = SharedPreUtils.getInstanse().getKeyValue(this, UserInfoConfig.Cache_Marriage);

        if (TextUtils.isEmpty(cacheSex) || TextUtils.isEmpty(cacheProvince)) return;

        BaseBean<List<Type>> list1 = new Gson().fromJson(cacheSex, new TypeToken<BaseBean<List<Type>>>() {
        }.getType());
        sexItem.clear();
        sexItem.addAll(list1.getModel());

        BaseBean<List<Type>> list2 = new Gson().fromJson(cacheEducation, new TypeToken<BaseBean<List<Type>>>() {
        }.getType());
        educationItem.clear();
        educationItem.addAll(list2.getModel());

        BaseBean<List<Type>> list3 = new Gson().fromJson(cacheMarriage, new TypeToken<BaseBean<List<Type>>>() {
        }.getType());
        marriageItem.clear();
        marriageItem.addAll(list3.getModel());

        BaseBean<List<Province>> list4 = new Gson().fromJson(cacheProvince, new TypeToken<BaseBean<List<Province>>>() {
        }.getType());
        options1Items.clear();
        options2Items.clear();
        options1Items.addAll(list4.getModel());
        for (Province p : options1Items) {
            options2Items.add(p.getCityAreaInfoVos());
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter = null;
        UserInfoPresenter.getUserInfo();
        super.onDestroy();
    }

    @Override
    public void initPresenter() {
        mPresenter = new UserInfoPresenter(this);
        mPresenter.doReflush();
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

    /**
     *
     */
    public void InitData() {
        if (null == mUserInfo) {
            return;
        }//HttpConfig.HttpReadImage + "?fileId=" +
        Picasso.with(this).
                load( mUserInfo.getIcon()).
                error(R.mipmap.icon_my_head).
                placeholder(R.mipmap.icon_my_head).
                into(user_icon_userinfo);

        if (mUserInfo.getIsOpenEaccount() == 0) {
            relative_name.hasArr(true);
            relative_depository.hasArr(true);
            relative_bank.hasArr(true);
        } else {
            relative_name.hasArr(false);
            relative_depository.hasArr(false);

            if (mUserInfo.getBindcardStatus() == 0) {
                relative_bank.hasArr(true);
            } else {
                relative_bank.hasArr(false);
            }
        }
        relative_name.setRightText(mUserInfo.getUserName());
        relative_depository.setRightText(mUserInfo.getIsOpenEaccountStr());//是否开通E账户
        relative_bank.setRightText(mUserInfo.getBindcardStatusStr());//绑卡状态

        relative_sex.setRightText(mUserInfo.getGenderStr());//性别
        relative_birthday.setRightText(mUserInfo.getBirthdayStr());//生日

        relative_edcation.setRightText(mUserInfo.getEducationStr());//教育水平
        department_layout.setRightText(mUserInfo.getMarriageStatusStr());//婚姻状况
        ll_occupation.setRightText(mUserInfo.getOccupation());//职业类型
        ll_residence_address.setRightText(mUserInfo.getAddress());//居住地址
        ll_household_address.setRightText(mUserInfo.getDomicile());//户籍地址
        if (mUserInfo.getLiveAreaInfoVo() != null && mUserInfo.getLiveAreaInfoVo().getPAreaInfoVo() != null)
            ll_residence.setRightText(mUserInfo.getLiveAreaInfoVo().getPAreaInfoVo().getAreaName() + " " + mUserInfo.getLiveAreaInfoVo().getAreaName());//居住地
        if (mUserInfo.getHrAreaInfoVo() != null && mUserInfo.getHrAreaInfoVo().getPAreaInfoVo() != null)
            ll_household_register.setRightText(mUserInfo.getHrAreaInfoVo().getPAreaInfoVo().getAreaName() + " " + mUserInfo.getHrAreaInfoVo().getAreaName());//户籍地
    }

    @OnClick({R.id.icon_layout, R.id.relative_name, R.id.relative_depository, R.id.relative_bank,
            R.id.relative_sex, R.id.relative_birthday, R.id.ll_household_register,
            R.id.ll_household_address, R.id.relative_edcation, R.id.department_layout,
            R.id.ll_occupation, R.id.ll_residence, R.id.ll_residence_address, R.id.btn_exit_out})
    public void onClick(View view) {
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.icon_layout://头像
                    if (null == customUploadBoard) {
                        customUploadBoard = new CustomUploadBoard(this);
                    }
                    customUploadBoard.show();
                    break;
                case R.id.relative_name://名字
                case R.id.relative_depository://资金存管
                    if (mUserInfo != null && mUserInfo.getIsOpenEaccount() == 0) {
                        DialogMineOpenEFragment dialogMineOpenEFragment = new DialogMineOpenEFragment().newInstance();
                        dialogMineOpenEFragment.show(getSupportFragmentManager(), "updateAppDialogFragment");
                    }
                    break;
                case R.id.relative_bank://银行卡
//                    if (mUserInfo.getBindcardStatus() == 0 && mUserInfo.getIsOpenEaccount() == 1 && !TextUtils.isEmpty(mUserInfo.getUserName())) {
                    if (mUserInfo != null && (mUserInfo.getIsOpenEaccount() == 0 || mUserInfo.getBindcardStatus() == 0)) {
                        DialogMineOpenEFragment dialogMineOpenEFragment = new DialogMineOpenEFragment().newInstance();
                        dialogMineOpenEFragment.show(getSupportFragmentManager(), "updateAppDialogFragment");
                    }
                    break;
                case R.id.relative_sex://性别
                    showSexDialog();
                    break;
                case R.id.relative_birthday://出生年月
                    showBirthdayDialog();
                    break;
                case R.id.relative_edcation://教育水平
                    showEducationDialog();
                    break;
                case R.id.department_layout://婚姻状况
                    showMarriageDialog();
                    break;
                case R.id.ll_household_register://户籍地
                    showHouseholdDialog();
                    break;
                case R.id.ll_residence://居住地
                    showLiveAreaDialog();
                    break;
                case R.id.ll_residence_address://居住地址
                case R.id.ll_household_address://户籍地址
                case R.id.ll_occupation://职业
                    toChangeActivity(view);
                    break;
                case R.id.btn_exit_out://退出登录
                    exiteLogin();
                    break;
            }
        }
    }

    private Dialog systemDialog;

    private void exiteLogin() {
        systemDialog = new Dialog(this, R.style.loadingDialogTheme);
        View inflate = View.inflate(MineInformationActivity.this, R.layout.dialog_exit, null);
        TextView tv_quxiao = (TextView) inflate.findViewById(R.id.tv_quxiao);
        TextView tv_queding = (TextView) inflate.findViewById(R.id.tv_queding);

        tv_quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                systemDialog.dismiss();
            }
        });
        tv_queding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AppManager.getAppManager().ExitLogin();
                MineInformationActivity.this.setResult(RESULT_OK);
                systemDialog.dismiss();
                MineInformationActivity.this.finish();
//                HomeActivity.homeActivity.setCommonTabLayoutIndex(0);

            }
        });
        systemDialog.setContentView(inflate);
        systemDialog.show();
    }

    private void showEducationDialog() {
        if (null == mEducationPicker) {
            mEducationPicker = new OptionsPickerView(this);
            mEducationPicker.setPicker(educationItem);
            mEducationPicker.setCancelable(true);
            mEducationPicker.setCyclic(false);
            mEducationPicker.setTitle("教育水平");
            mEducationPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    Type type = educationItem.get(options1);
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put(UserInfoConfig.EDUCATION, type.getValue() + "");
                    mPresenter.updateUserInfo(parmas);

                    relative_edcation.setRightText(type.getLabel());
                    mUserInfo.setEducation(Integer.parseInt(type.getValue()));
                }
            });
        }
        mEducationPicker.setSelectOptions(mUserInfo.getEducation());
        mEducationPicker.show();
    }

    private void showMarriageDialog() {
        if (null == mMarriagePicker) {
            mMarriagePicker = new OptionsPickerView(this);
            mMarriagePicker.setPicker(marriageItem);
            mMarriagePicker.setCancelable(true);
            mMarriagePicker.setCyclic(false);
            mMarriagePicker.setTitle("婚姻状况");
            mMarriagePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    Type type = marriageItem.get(options1);
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put(UserInfoConfig.MARRIAGE, type.getValue() + "");
                    mPresenter.updateUserInfo(parmas);

                    department_layout.setRightText(type.getLabel());
                    mUserInfo.setMarriageStatus(Integer.parseInt(type.getValue()));
                }
            });
        }
        mMarriagePicker.setSelectOptions(mUserInfo.getMarriageStatus());
        mMarriagePicker.show();
    }


    private void showSexDialog() {
        if (null == mSexPicker) {
            mSexPicker = new OptionsPickerView(this);
            mSexPicker.setPicker(sexItem);
            mSexPicker.setCancelable(true);
            mSexPicker.setCyclic(false);
            mSexPicker.setTitle("请选择性别");
            mSexPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    Type type = sexItem.get(options1);
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put(UserInfoConfig.SEX, type.getValue() + "");
                    mPresenter.updateUserInfo(parmas);

                    relative_sex.setRightText(type.getPickerViewText());
                    mUserInfo.setGender(Integer.parseInt(type.getValue()));
                }
            });
        }
        mSexPicker.setSelectOptions(mUserInfo.getGender());
        mSexPicker.show();
    }

    //户籍地  选择
    private void showHouseholdDialog() {
        if (options1Items.size() == 0 || options2Items.size() == 0) {
            return;
        }
        if (mPickerView == null) {
            mPickerView = new OptionsPickerView(this);
            mPickerView.setCancelable(true);
            mPickerView.setPicker(options1Items, options2Items, true);
            mPickerView.setCyclic(false, false, false);
            mPickerView.setTitle("选择城市");


            mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText()
                            + " " + options2Items.get(options1).get(option2).getPickerViewText();
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put("hrAreaInfoId", options2Items.get(options1).get(option2).getId() + "");
                    mPresenter.updateUserInfo(parmas);

                    ll_household_register.setRightText(tx);
                }
            });
        }
        if (null != mUserInfo.getHrAreaInfoVo()) {
            for (int a = 0; a < options2Items.size(); a++) {
                ArrayList<Province.CityAreaInfoVosBean> cityAreaInfoVosBeen = options2Items.get(a);
                for (int b = 0; b < cityAreaInfoVosBeen.size(); b++) {
                    Province.CityAreaInfoVosBean cityAreaInfoVosBean = cityAreaInfoVosBeen.get(b);
                    if (cityAreaInfoVosBean.getId() == mUserInfo.getHrAreaInfoVo().getId()) {
                        mPickerView.setSelectOptions(a, b);
                    }
                }
            }
        }
        mPickerView.show();
    }

    // 居住地 选择
    private void showLiveAreaDialog() {
        if (options1Items.size() == 0 || options2Items.size() == 0) {
            return;
        }
        if (mPickerView2 == null) {
            mPickerView2 = new OptionsPickerView(this);
            mPickerView2.setCancelable(true);
            mPickerView2.setPicker(options1Items, options2Items, true);
            mPickerView2.setCyclic(false, false, false);
            mPickerView2.setTitle("选择城市");

            if (null != mUserInfo.getLiveAreaInfoVo()) {
                for (int a = 0; a < options2Items.size(); a++) {
                    ArrayList<Province.CityAreaInfoVosBean> cityAreaInfoVosBeen = options2Items.get(a);
                    for (int b = 0; b < cityAreaInfoVosBeen.size(); b++) {
                        Province.CityAreaInfoVosBean cityAreaInfoVosBean = cityAreaInfoVosBeen.get(b);
                        if (cityAreaInfoVosBean.getId() == mUserInfo.getLiveAreaInfoVo().getId()) {
                            mPickerView2.setSelectOptions(a, b);
                        }
                    }
                }
            }

            mPickerView2.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText()
                            + " " + options2Items.get(options1).get(option2).getPickerViewText();
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put("liveAreaInfoId", options2Items.get(options1).get(option2).getId() + "");
                    mPresenter.updateUserInfo(parmas);

                    ll_residence.setRightText(tx);
                }
            });
        }
        mPickerView2.show();
    }

    //时间选择器
    private void showBirthdayDialog() {
        if (mTimePicker == null) {
            mTimePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            //控制时间范围
            Calendar calendar = Calendar.getInstance();
            mTimePicker.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
            mTimePicker.setCyclic(false);
            mTimePicker.setCancelable(true);
            //时间选择后回调
            mTimePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                @Override
                public void onTimeSelect(Date date) {
                    if (date.getTime() > new Date().getTime()) {
                        toast("出生年月不能大于当前日期");
                        return;
                    }

                    String dateLong = DateUtil.getDateLong(date);
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put(UserInfoConfig.Birthday, dateLong);
                    mPresenter.updateUserInfo(parmas);

                    relative_birthday.setRightText(dateLong);
                    mUserInfo.setBirthday(date.getTime());
                }
            });
        }
        Date date = new Date();
        if (mUserInfo.getBirthday() != 0) {
            date.setTime(mUserInfo.getBirthday());
        }
        mTimePicker.setTime(date);
        mTimePicker.show();
    }

    private void toChangeActivity(View view) {
        String text = "";
        String type = "";
        String content = "";
        switch (view.getId()) {
            case R.id.ll_residence_address://居住地址
                text = "居住地址";
                type = UserInfoConfig.Address;
                content = ll_residence_address.getRightText();
                break;
            case R.id.ll_household_address://户籍地址
                text = "户籍地址";
                type = UserInfoConfig.Domicile;
                content = ll_household_address.getRightText();
                break;
            case R.id.ll_occupation://职业
                text = "职业";
                type = UserInfoConfig.Occupation;
                content = ll_occupation.getRightText();
                break;
        }
        Intent intent = new Intent(this, UserInfoItemActivity.class);
        intent.putExtra("from", text);
        intent.putExtra("type", type);
        intent.putExtra("content", content);
        startActivityForResult(intent, Request_Other);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request_Other) {
            if (resultCode == UserInfoItemActivity.Result_code) {
                //TODO
                String content = data.getStringExtra("content");
                String from = data.getStringExtra("from");
                String type = data.getStringExtra("type");
                if (UserInfoConfig.Address.equals(type)) {//居住地址
                    ll_residence_address.setRightText(content);
                } else if (UserInfoConfig.Domicile.equals(type)) {//户籍地址
                    ll_household_address.setRightText(content);
                } else if (UserInfoConfig.Occupation.equals(type)) {//职业
                    ll_occupation.setRightText(content);
                }
                submit(type, from, content);
            }
            return;
        }
        try {
            mPresenter.onTakePhotos(this, requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submit(String type, String from, String content) {
        //上传用户信息
        Map<String, String> parmas = new HashMap<>();
        parmas.put(type, content);
        mPresenter.updateUserInfo(parmas);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LogUtil.e("takephoto**********************请求权限成功");
                customUploadBoard.startTakePhone();
            } else {
                LogUtil.e("takephoto**********************请求权限失败");
                toast("请打开拍照权限");
                customUploadBoard.dismiss();
            }
        }
    }

    @Override
    public void onOpenEAccount(int resId) {
        //开通E账号
        startActivity(new Intent(mContext, OpenEAccountActivity.class));
    }

    @Override
    public void onReflushUserInfo(UserInfoBean model) {//获取最新的userinfo
        mUserInfo = model;
        InitData();
    }

    @Override
    public void onUploadIcon(String fileName) {
        Toast.makeText(HXXCApplication.getInstance(), "头像上传成功", Toast.LENGTH_SHORT).show();
        mPresenter.doReflush();
        try {//HttpConfig.HttpReadImage + "?fileId=" +
//            Picasso.with(this).
//                    load(mUserInfo.getIcon()).
//                    memoryPolicy(MemoryPolicy.NO_CACHE).
//                    error(R.mipmap.icon_my_head).
//                    placeholder(R.mipmap.icon_my_head).
//                    into(user_icon_userinfo);
        } catch (Exception e) {
            LogUtil.e("头像设置失败=" + e.getMessage());
        }
    }
}
