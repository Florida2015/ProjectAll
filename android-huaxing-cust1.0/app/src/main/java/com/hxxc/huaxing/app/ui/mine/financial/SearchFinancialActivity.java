package com.hxxc.huaxing.app.ui.mine.financial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.AdviserBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static android.R.id.list;
import static java.math.BigDecimal.ROUND_DOWN;

/**
 * Created by Administrator on 2016/9/27.
 * 搜索顾问页
 */

public class SearchFinancialActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_search_financial)
    EditText ed_search_financial;
    @BindView(R.id.btn_search_financial)
    Button btn_search_financial;

    @BindView(R.id.listview_search_financial)
    ListView listview;

    private boolean FlagSearch=true;//搜索

    private String areaId;

    public static SearchFinancialActivity searchFinancialActivity = null ;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.financial_search_financial;
    }

    @Override
    public void initView() {
        toolbar_title.setText("搜索顾问");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        searchFinancialActivity = this;
        AppManager.getAppManager().addActivity(this);

        areaId = getIntent().getStringExtra("areaId");

        ed_search_financial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0){
                    FlagSearch=false;
                    btn_search_financial.setText("搜索");
                    btn_delete_search.setText("");
                }else{
                    btn_search_financial.setText("取消");
                    btn_delete_search.setText("清除历史记录");
                    FlagSearch=true;
                    searchHistory();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listview.setOnItemClickListener(this);

        view_delete = View.inflate(mContext,R.layout.financial_search_delete_his,null);
        btn_delete_search = (Button) view_delete.findViewById(R.id.btn_delete_search);
        view_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清除历史记录
                historyList.clear();
                SharedPreUtils.getInstanse().putKeyValue(mContext,UserInfoConfig.TypeSearchHistory,HXXCApplication.getInstance().getGson().toJson(historyList));
                searchHisttoryAdapter.notifyDataSetChanged();
            }
        });

        listview.addFooterView(view_delete);

        String history = SharedPreUtils.getInstanse().getKeyValue(mContext,UserInfoConfig.TypeSearchHistory);

        if (historyList==null)historyList = new ArrayList<String>();
        historyList.clear();
        if (!TextUtils.isEmpty(history))
            historyList = HXXCApplication.getInstance().getGson().fromJson(history, new TypeToken<List<String>>() {}.getType());

        searchHistory();

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

    @OnClick(R.id.btn_search_financial)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_search_financial://搜索
                if (BtnUtils.isFastDoubleClick()){
                    if (btn_search_financial.getText().toString().contains("搜索")){
                        FlagSearch=false;
                        searchFinancial(ed_search_financial.getText().toString().trim());
                    }else ed_search_financial.setText("");
                }

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //搜索
        if (FlagSearch){
            FlagSearch=false;
            ed_search_financial.setText(historyList.get(i));
            ed_search_financial.setSelection(historyList.get(i).length());
            searchFinancial(historyList.get(i));

        }else{//理财师信息

            //是否选择该理财师做顾问
            String mobile = getIntent().getStringExtra("mobile");
            String code = getIntent().getStringExtra("code");
            String pass = getIntent().getStringExtra("pass");
            String pass_again = getIntent().getStringExtra("pass_again");
            String pCode = getIntent().getStringExtra("pCode");
            LogUtil.d("顾问 mobile="+mobile+"_code="+code+"_pass="+pass+"_pass_again="+pass_again+"_pCode="+pCode);

            Intent intent = new Intent((Activity)mContext, DialogBindingFinancial.class);
            intent.putExtra("name",searchFinancialAdapter.getAdapterListData().get(i).getFname());
            intent.putExtra("mobile",mobile);
            intent.putExtra("code",code);
            intent.putExtra("pass",pass);
            intent.putExtra("pass_again",pass_again);
            intent.putExtra("pCode",pCode);
            intent.putExtra("fid",""+searchFinancialAdapter.getAdapterListData().get(i).getFid());
            mContext.startActivity(intent);
        }
    }

    public void searchFinancial(String text){
        //请求数据
        searchHisttoryAdapter.notifyDataSetChanged();

//TODO
        boolean flag_b = false;
        for (int i=0;i<historyList.size();i++){
            if (!TextUtils.isEmpty(text)&&text.equals(historyList.get(i))){
                flag_b = true;
            }
        }
        if (!flag_b)
        historyList.add(0,text);

//        HashSet h = new HashSet(historyList);
//        historyList.clear();
//        historyList.addAll(h);
//        System.out.println(historyList);
        //TODO

        SharedPreUtils.getInstanse().putKeyValue(mContext,UserInfoConfig.TypeSearchHistory,HXXCApplication.getInstance().getGson().toJson(historyList));
//TextUtils.isEmpty(areaId)?"":areaId

        Api.getClient().getFpList(UserInfoConfig.ShowType_register, "",text,1,12).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<AdviserBean>>(mContext) {
                    @Override
                    public void onSuccess(List<AdviserBean> adviserBeen) {
                        setSearchFinancialAdapter(adviserBeen);
                    }

                    @Override
                    public void onFail(String fail) {
                        btn_delete_search.setText("没有相关顾问");
                    }
                }
     );
    }

    private String[] history = new String[]{"王小闲1","王小闲2","王小闲3"};
    SearchFinancialHisttoryAdapter searchHisttoryAdapter;
    private List<String> historyList;
    private View view_delete;
    private Button btn_delete_search;
    /**
     * 历史搜索记录
     *
     */
    public void searchHistory(){


       LogUtil.d("搜索顾问"+HXXCApplication.getInstance().getGson().toJson(historyList));

        searchHisttoryAdapter = new SearchFinancialHisttoryAdapter(mContext,historyList,R.layout.financial_search_item);

        listview.setAdapter(searchHisttoryAdapter);

    }

    /**
     * 搜索历史
     *
     */
    public class SearchFinancialHisttoryAdapter extends CommonAdapter<String>{

        public SearchFinancialHisttoryAdapter(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item, int position) {
            super.convert(helper, item, position);
            if (position%2==1){helper.getConvertView().setBackgroundColor(getResources().getColor(R.color.grey_fafa));
            }else helper.getConvertView().setBackgroundColor(getResources().getColor(R.color.white));

            helper.setText(R.id.tv_search_financial_name,item);

        }
    }


    SearchFinancialAdapter searchFinancialAdapter;
    public void setSearchFinancialAdapter(List<AdviserBean> list){
        if (list!=null&&list.size()>0)
        btn_delete_search.setText("");
        else  btn_delete_search.setText("没有相关顾问");
        searchFinancialAdapter = new SearchFinancialAdapter(mContext,list,R.layout.adapter_finance_item);
        listview.setAdapter(searchFinancialAdapter);
    }
    /**
     * 搜索理财师
     *
     */
    public class SearchFinancialAdapter extends CommonAdapter<AdviserBean>{


        public SearchFinancialAdapter(Context context, List<AdviserBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, AdviserBean item, int position) {
            super.convert(helper, item, position);

            LinearLayout linear_binding_financial = helper.getView(R.id.linear_binding_financial);

            TextView tv_recommend = helper.getView(R.id.tv_recommend);
            View line_recommend = helper.getView(R.id.line_recommend);

            ImageView img_icon = helper.getView(R.id.img_icon);

            TextView name_text = helper.getView(R.id.name_text);
            TextView service_count_text = helper.getView(R.id.service_count_text);
            TextView finance_no_text = helper.getView(R.id.finance_no_text);
            TextView amout_text = helper.getView(R.id.amout_text);

            ImageView is_check_img = helper.getView(R.id.is_check_img);

            if (position==0){
                tv_recommend.setVisibility(View.VISIBLE);
                line_recommend.setVisibility(View.VISIBLE);
            }else {
                tv_recommend.setVisibility(View.GONE);
                line_recommend.setVisibility(View.GONE);
            }
            is_check_img.setVisibility(View.GONE);

            if (position%2==0)  linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            else linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));

            Picasso.with(mContext)
                    .load(item.getIcon())
                    .placeholder(R.mipmap.icon_my_head)
                    .error(R.mipmap.icon_my_head)
                    .into( img_icon);

            name_text.setText(item.getFname());
            finance_no_text.setText(item.getFinancialno());
            service_count_text.setText(""+item.getServicecount());
            amout_text.setText(MoneyUtil.addComma((BigDecimal.valueOf(item.getInvestmentamout().doubleValue()/10000)),0,ROUND_DOWN,MoneyUtil.point)+"万");

        }

    }


}
