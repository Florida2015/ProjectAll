package com.hxxc.user.app.ui.mine;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huaxiafinance.lc.pickerview.OptionsPickerView;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.UserInfoEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Province;
import com.hxxc.user.app.bean.Type;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.contract.UserInfoV;
import com.hxxc.user.app.contract.presenter.UserInfoPresenter;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.widget.CircleImageView;
import com.hxxc.user.app.widget.CustomUploadBoard;
import com.hxxc.user.app.widget.LeftAndRightTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static com.hxxc.user.app.widget.CustomUploadBoard.PERMISSION_CAMERA;

/**
 * Created by chenqun on 2016/10/27.
 * 个人信息
 */

public class UserInfoActivity extends ToolbarActivity implements UserInfoV {
    public static final int Request_Other = 3;

    @BindView(R.id.icon_layout)
    RelativeLayout icon_layout;

    @BindView(R.id.user_icon_userinfo)
    CircleImageView user_icon_userinfo;

    @BindView(R.id.tv_1)//真实姓名
            LeftAndRightTextView tv_1;
    @BindView(R.id.tv_2)//性别
            LeftAndRightTextView tv_2;
    @BindView(R.id.tv_3)//出生年月
            LeftAndRightTextView tv_3;
    @BindView(R.id.tv_4)//户籍地
            LeftAndRightTextView tv_4;
    @BindView(R.id.tv_5)//户籍地址
            LeftAndRightTextView tv_5;
    @BindView(R.id.tv_6)//教育水平
            LeftAndRightTextView tv_6;
    @BindView(R.id.tv_7)//婚姻状况
            LeftAndRightTextView tv_7;
    @BindView(R.id.tv_8)//职业类型
            LeftAndRightTextView tv_8;
    @BindView(R.id.tv_9)//居住地
            LeftAndRightTextView tv_9;
    @BindView(R.id.tv_10)//居住地址
            LeftAndRightTextView tv_10;
    private CustomUploadBoard customUploadBoard;
    private UserInfoPresenter presenter;

    private OptionsPickerView mPickerView;
    private ArrayList<Province> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<Province.CityAreaInfoVosBean>> options2Items = new ArrayList<>();
    //    private TimePickerView mTimePicker;
    private UserInfo mUserInfo;
    //    private OptionsPickerView mSexPicker;
    private OptionsPickerView mMarriagePicker;
    private OptionsPickerView mEducationPicker;

    private ArrayList<Type> marriageItem = new ArrayList<>();
    private ArrayList<Type> educationItem = new ArrayList<>();
    //    private ArrayList<Type> sexItem = new ArrayList<>();
    private OptionsPickerView mPickerView2;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("个人信息");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        Midhandler.getUserInfo(null);
        super.onDestroy();
        presenter = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = new UserInfoPresenter(this);
        mUserInfo = SPUtils.geTinstance().getUserInfo();
        try {
            initCache();
            InitData();
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            finish();
        }
    }

    //获取缓存
    private void initCache() {
//        String cacheSex = SPUtils.geTinstance().get(Constants.Cache_Sex, "");
        String cacheEducation = SPUtils.geTinstance().get(Constants.Cache_Education, "");
        String cacheMarriage = SPUtils.geTinstance().get(Constants.Cache_Marriage, "");
        String cacheProvince = SPUtils.geTinstance().get(Constants.Cache_Province, "");

        if (TextUtils.isEmpty(cacheProvince)) {
            return;
        }
//        BaseResult<List<Type>> list1 = new Gson().fromJson(cacheSex, new TypeToken<BaseResult<List<Type>>>() {
//        }.getType());
//        sexItem.clear();
//        sexItem.addAll(list1.getData());

        BaseResult<List<Type>> list2 = new Gson().fromJson(cacheEducation, new TypeToken<BaseResult<List<Type>>>() {
        }.getType());
        educationItem.clear();
        educationItem.addAll(list2.getData());

        BaseResult<List<Type>> list3 = new Gson().fromJson(cacheMarriage, new TypeToken<BaseResult<List<Type>>>() {
        }.getType());
        marriageItem.clear();
        marriageItem.addAll(list3.getData());

        BaseResult<List<Province>> list4 = new Gson().fromJson(cacheProvince, new TypeToken<BaseResult<List<Province>>>() {
        }.getType());
        options1Items.clear();
        options2Items.clear();
        options1Items.addAll(list4.getData());
        for (Province p : options1Items) {
            options2Items.add(p.getCityAreaInfoVos());
        }
    }

    public void InitData() {
        if (null == mUserInfo) {
            return;
        }
        Picasso.with(this).load(mUserInfo.getRealIcon()).error(R.mipmap.icon_my_head).placeholder(R.mipmap.icon_my_head).into(user_icon_userinfo);

        if (mUserInfo.getRnaStatus() == 1) {
            tv_1.setRightText(mUserInfo.getUserName());
            tv_2.setRightText(mUserInfo.getGenderStr());//性别
            tv_3.setRightText(mUserInfo.getBirthdayStr());//生日
            tv_1.removeArr();
            tv_2.removeArr();
            tv_3.removeArr();
        }


        if (!TextUtils.isEmpty(mUserInfo.getEducationStr()))
            tv_6.setRightText(mUserInfo.getEducationStr());//教育水平
        if (!TextUtils.isEmpty(mUserInfo.getMarriageStatusStr()))
            tv_7.setRightText(mUserInfo.getMarriageStatusStr());//婚姻状况

        if (!TextUtils.isEmpty(mUserInfo.getOccupation()))
            tv_8.setRightText(mUserInfo.getOccupation());//职业类型
        if (!TextUtils.isEmpty(mUserInfo.getAddress()))
            tv_10.setRightText(mUserInfo.getAddress());//居住地址
        if (!TextUtils.isEmpty(mUserInfo.getDomicile()))
            tv_5.setRightText(mUserInfo.getDomicile());//户籍地址

        if (mUserInfo.getLiveAreaInfoVo() != null && mUserInfo.getLiveAreaInfoVo().getPAreaInfoVo() != null)
            tv_9.setRightText(mUserInfo.getLiveAreaInfoVo().getPAreaInfoVo().getAreaName() + " " + mUserInfo.getLiveAreaInfoVo().getAreaName());//居住地
        if (mUserInfo.getHrAreaInfoVo() != null && mUserInfo.getHrAreaInfoVo().getPAreaInfoVo() != null)
            tv_4.setRightText(mUserInfo.getHrAreaInfoVo().getPAreaInfoVo().getAreaName() + " " + mUserInfo.getHrAreaInfoVo().getAreaName());//户籍地
    }

    @OnClick({R.id.icon_layout, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9, R.id.tv_10})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_layout:
//                if (null == customUploadBoard) {
                customUploadBoard = new CustomUploadBoard(this);
//                }
                customUploadBoard.show();
                break;
            case R.id.tv_1://真实姓名
            case R.id.tv_2://性别
            case R.id.tv_3://出生年月
                if (mUserInfo.getRnaStatus() != 1) {
                    Intent intent = new Intent(this, AuthenticationActivity.class);
                    intent.putExtra("from", AuthenticationActivity.FROM_UserInfo);
                    startActivity(intent);
                }
                break;

            case R.id.tv_4://户籍地
                showHouseholdDialog();
                break;
            case R.id.tv_6://教育水平
                showEducationDialog();
                break;
            case R.id.tv_7://婚姻状况
                showMarriageDialog();
                break;
            case R.id.tv_9://居住地
                showLiveAreaDialog();
                break;

            case R.id.tv_5://户籍地址
            case R.id.tv_8://职业类型
            case R.id.tv_10://居住地址
                toChangeActivity(view);
                break;
        }
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
                    parmas.put(Constants.EDUCATION, type.getValue() + "");
                    presenter.updateUserInfo(parmas);

                    tv_6.setRightText(type.getDesc());
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
                    parmas.put(Constants.MARRIAGE, type.getValue() + "");
                    presenter.updateUserInfo(parmas);

                    tv_7.setRightText(type.getDesc());
                }
            });
        }
        mMarriagePicker.setSelectOptions(mUserInfo.getMarriageStatus());
        mMarriagePicker.show();
    }


//    private void showSexDialog() {
//        if (null == mSexPicker) {
//            mSexPicker = new OptionsPickerView(this);
//            mSexPicker.setPicker(sexItem);
//            mSexPicker.setCancelable(true);
//            mSexPicker.setCyclic(false);
//            mSexPicker.setTitle("请选择性别");
//            mSexPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int option2, int options3) {
//                    Type type = sexItem.get(options1);
//                    Map<String, String> parmas = new HashMap<>();
//                    parmas.put(Constants.SEX, type.getValue() + "");
//                    presenter.updateUserInfo(parmas);
//
//                    tv_2.setRightText(type.getPickerViewText());
//                }
//            });
//        }
//        mSexPicker.setSelectOptions(mUserInfo.getGender());
//        mSexPicker.show();
//    }

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
                    presenter.updateUserInfo(parmas);

                    tv_4.setRightText(tx);
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
                    presenter.updateUserInfo(parmas);
                    tv_9.setRightText(tx);
                }
            });
        }
        mPickerView2.show();
    }
//
//    //时间选择器
//    private void showBirthdayDialog() {
//        if (mTimePicker == null) {
//            mTimePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
//            //控制时间范围
//            Calendar calendar = Calendar.getInstance();
//            mTimePicker.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
//            mTimePicker.setCyclic(false);
//            mTimePicker.setCancelable(true);
//            //时间选择后回调
//            mTimePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
//
//                @Override
//                public void onTimeSelect(Date date) {
//                    if(date.getTime() > new Date().getTime()){
//                        date = new Date();
//                    }
//
//                    String dateLong = DateUtil.getDateLong(date);
//                    Map<String, String> parmas = new HashMap<>();
//                    parmas.put(Constants.Birthday, dateLong);
//                    presenter.updateUserInfo(parmas);
//
//                    tv_3.setRightText(dateLong);
//                }
//            });
//        }
//        Date date = new Date();
//        if (mUserInfo.getBirthday() != 0) {
//            date.setTime(mUserInfo.getBirthday());
//        }
//        mTimePicker.setTime(date);
//        mTimePicker.show();
//    }

    private void toChangeActivity(View view) {
        String text = "";
        String type = "";
        String content = "";
        switch (view.getId()) {
            case R.id.tv_10://居住地址
                text = "居住地址";
                type = Constants.Address;
                content = tv_10.getRightText();
                break;
            case R.id.tv_5://户籍地址
                text = "户籍地址";
                type = Constants.Domicile;
                content = tv_5.getRightText();
                break;
            case R.id.tv_8://职业
                text = "职业";
                type = Constants.Occupation;
                content = tv_8.getRightText();
                break;
        }
        Intent intent = new Intent(this, UserInfoItemActivity.class);
        intent.putExtra("from", text);
        intent.putExtra("type", type);
        intent.putExtra("content", content);
        startActivityForResult(intent, Request_Other);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LogUtil.e("takephoto**********************3");
                customUploadBoard.startTakePhoto();
            } else {
                customUploadBoard.dismiss();
            }
        }
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
                if (Constants.Address.equals(type)) {//居住地址
                    tv_10.setRightText(content);
                } else if (Constants.Domicile.equals(type)) {//户籍地址
                    tv_5.setRightText(content);
                } else if (Constants.Occupation.equals(type)) {//职业
                    tv_8.setRightText(content);
                }
                submit(type, from, content);
            }
            return;
        }
        presenter.onTakePhotos(this, requestCode, resultCode, data);
    }

    private void submit(String type, String from, String content) {
        //上传用户信息
        Map<String, String> parmas = new HashMap<>();
        parmas.put(type, content);
        presenter.updateUserInfo(parmas);//TODO
    }

    @Override
    public void onReflushUserInfo(UserInfo model) {
        mUserInfo = model;
        InitData();
    }

    @Override
    public void onUploadIcon(UserInfo userInfo) {
        mUserInfo = userInfo;
        Picasso.with(this).load(mUserInfo.getRealIcon()).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.mipmap.icon_my_head).placeholder(R.mipmap.icon_my_head).into(user_icon_userinfo);
    }

    public void onEventMainThread(UserInfoEvent event) {
        if (null != event) {
            tv_1.setRightText(event.name);
            mUserInfo = SPUtils.geTinstance().getUserInfo();
        }
    }
}
