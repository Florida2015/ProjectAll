package com.hxxc.user.app.ui.discovery.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.BindBankEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Province;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ThreadManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/11/11.
 */

public class SelectActivity extends ToolbarActivity {
    @BindView(R.id.sidrbar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.country_lvcountry)
    ListView sortListView;
    private List<Province> mProvinceData;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_select;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("请选择省");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
    }

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList = new ArrayList<>();
    private int mSelectedProvince = -1;
    private int mSelectedCity = -1;
    ArrayList<Province.CityAreaInfoVosBean> mCityDatas;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private SortAdapter adapter;

    private void initViews() {
        //实例化汉字转拼音类
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                if (!TextUtils.isEmpty(s)) {
                    if (null != adapter) {
                        int position = adapter.getPositionForSection(s.charAt(0));
                        if (position != -1) {
                            sortListView.setSelection(position);
                        }
                    }
                }
            }
        });
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position < adapter.getCount()) {
//                    Intent intent = new Intent();
//                    intent.putExtra("city", adapter.getItem(position).getName());
//                    setResult(RESULT_OK, intent);
//                    finish();

                    if (-1 == mSelectedProvince) {
                        mSelectedProvince = position;
                        mTitle.setText("请选择市");
                        mCityDatas = mProvinceData.get(position).getCityAreaInfoVos();
                        if (null != mCityDatas) Collections.sort(mCityDatas);
                        InitData2(mCityDatas);
                        sortListView.setSelection(0);
                    } else {
                        mSelectedCity = position;
                        BindBankEvent event = new BindBankEvent(null);
                        event.provinceName = mProvinceData.get(mSelectedProvince).getAreaName();
                        event.provinceCode = mProvinceData.get(mSelectedProvince).getAreaCode();
                        event.cityName = mCityDatas.get(mSelectedCity).getAreaName();
                        event.cityCode = mCityDatas.get(mSelectedCity).getAreaCode();
                        EventBus.getDefault().post(event);
                        finish();
                    }

                }
            }
        });
        getAddressData();//请求数据
    }

    //获取数据
    private void getAddressData() {
        String cacheProvince = SPUtils.geTinstance().get(Constants.Cache_Province, "");
        if (TextUtils.isEmpty(cacheProvince)) {
            return;
        }
        BaseResult<List<Province>> list4 = new Gson().fromJson(cacheProvince, new TypeToken<BaseResult<List<Province>>>() {
        }.getType());
        mProvinceData = list4.getData();
        if (null == mProvinceData) return;

        Collections.sort(mProvinceData);
        InitData(mProvinceData);
    }

    private void InitData(final List<Province> data) {
        ThreadManager.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                SourceDateList.clear();
                final List<SortModel> sortModels = filledData(data);
                //适配
                SelectActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SourceDateList.addAll(sortModels);
                        // 根据a-z进行排序源数据
//                        Collections.sort(SourceDateList, pinyinComparator);
                        Collections.sort(SourceDateList);
                        sideBar.setDatas(mAList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    private void InitData2(final List<Province.CityAreaInfoVosBean> data) {
        ThreadManager.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                SourceDateList.clear();
                final List<SortModel> sortModels = filledData2(data);
                //适配
                SelectActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SourceDateList.addAll(sortModels);
                        // 根据a-z进行排序源数据
//                        Collections.sort(SourceDateList, pinyinComparator);
//                        Collections.sort(SourceDateList);
                        sideBar.setDatas(mAList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    //右边导航的字母兰数据
    List<String> mAList = new ArrayList<>();

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<SortModel> filledData(List<Province> baseBean) {
        mAList.clear();
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < baseBean.size(); i++) {
            SortModel sortModel = new SortModel();
            if (!TextUtils.isEmpty(baseBean.get(i).getAreaName())) {
                sortModel.setName(baseBean.get(i).getAreaName());

                String sortString = baseBean.get(i).getNameEn().substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    if (!mAList.contains(sortString)) {
                        mAList.add(sortString);
                    }
                    sortModel.setSortLetters(sortString);
                } else {
                    sortModel.setSortLetters("#");
                }

            } else {
                sortModel.setName("未知");
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        mAList.add("#");
//        Collections.sort(mAList, new Comparator<String>() {
//            @Override
//            public int compare(String s, String t1) {
//                if (t1.equals("#")) {
//                    return -1;
//                } else if (s.equals("#")) {
//                    return 1;
//                } else {
//                    return s.compareTo(t1);
//                }
//            }
//        });
        return mSortList;
    }

    private List<SortModel> filledData2(List<Province.CityAreaInfoVosBean> baseBean) {
        mAList.clear();
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < baseBean.size(); i++) {
            SortModel sortModel = new SortModel();
            if (!TextUtils.isEmpty(baseBean.get(i).getAreaName())) {
                sortModel.setName(baseBean.get(i).getAreaName());

                String sortString = baseBean.get(i).getNameEn().substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    if (!mAList.contains(sortString)) {
                        mAList.add(sortString);
                    }
                    sortModel.setSortLetters(sortString);
                } else {
                    sortModel.setSortLetters("#");
                }
            } else {
                sortModel.setName("未知");
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        mAList.add("#");
        Collections.sort(mAList, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                if (t1.equals("#")) {
                    return -1;
                } else if (s.equals("#")) {
                    return 1;
                } else {
                    return s.compareTo(t1);
                }
            }
        });
        return mSortList;
    }

    @Override
    public void onBackPressed() {
        if (mSelectedProvince == -1) {
            super.onBackPressed();
        }
        mSelectedProvince = -1;
        mTitle.setText("请选择省");
        InitData(mProvinceData);
    }
}
