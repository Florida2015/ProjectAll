package com.hxxc.huaxing.app.ui.search;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.City;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.utils.ThreadManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * Created by chenqun on 2016/6/30.
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.sidrbar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.filter_edit)
    ClearEditText mClearEditText;
    @BindView(R.id.country_lvcountry)
    ListView sortListView;
    @BindView(R.id.curr_local_text)
    TextView curr_local_text;

    @Override
    public int getLayoutId() {
        return R.layout.item_sort_list;
    }

    @Override
    public void initView() {
        initViews();
    }

    @Override
    public void initPresenter() {

    }

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList = new ArrayList<SortModel>();
    private List<City> addressBeanList = new ArrayList<>();
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private SortAdapter adapter;

    private void initViews() {
        toolbar_title.setText("选择城市");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        String locationCity =  SharedPreUtils.getInstanse().getKeyValue(getApplicationContext(),UserInfoConfig.LASTCITY, "上海市");
        curr_local_text.setText(locationCity);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

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
                    Intent intent = new Intent();
                    intent.putExtra("city", adapter.getItem(position).getName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        curr_local_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("city", curr_local_text.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getAddressData();//请求数据
    }

    private void getAddressData() {
        Api.getClient().getAreaList().compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<List<City>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LogUtil.i(e.getMessage());
            }

            @Override
            public void onNext(BaseBean<List<City>> listBaseBean) {
                LogUtil.d(listBaseBean.toString());
                if (null == listBaseBean.getModel()) return;
                InitData(listBaseBean.getModel());
            }
        });
    }

    private void InitData(final List<City> data) {
        ThreadManager.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                SourceDateList.clear();
                final List<SortModel> sortModels = filledData(data);
                //适配
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SourceDateList.addAll(sortModels);
                        // 根据a-z进行排序源数据
                        Collections.sort(SourceDateList, pinyinComparator);
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
    private List<SortModel> filledData(List<City> baseBean) {
        mAList.clear();
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < baseBean.size(); i++) {
            SortModel sortModel = new SortModel();
            if (!TextUtils.isEmpty(baseBean.get(i).getName())) {
                sortModel.setName(baseBean.get(i).getName());

                //汉字转换成拼音
//                String pinyin = characterParser.getSelling(baseBean.get(i).name);
//                String sortString = pinyin.substring(0, 1).toUpperCase();
                String sortString = baseBean.get(i).getNameen().substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    if(!mAList.contains(sortString)){
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
                if(t1.equals("#")){
                    return -1;
                }else if(s.equals("#")){
                    return 1;
                }else{
                    return s.compareTo(t1);
                }
            }
        });
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString().toLowerCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
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
}
