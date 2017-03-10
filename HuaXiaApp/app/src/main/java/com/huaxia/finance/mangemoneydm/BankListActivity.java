package com.huaxia.finance.mangemoneydm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.asychttpclient.RequestParams;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.adapter.CommonAdapter;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BankItemModel;
import com.huaxia.finance.model.BaseModel;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 所有的银行卡列表页面
 * Created by houwen.lai on 2016/2/1.
 */
public class BankListActivity extends BaseActivity{
    private final String mPageName = BankListActivity.class.getSimpleName();

    private ListView back_listview;

    private BankListAdapter bankListAdapter;

    private Map<String,Integer> icon_bank=new HashMap<String,Integer>();
    private List<BankItemModel> bankList;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.bank_list_activity);

        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("选择银行卡");
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        back_listview = (ListView) findViewById(R.id.back_listview);

        back_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bankListAdapter.getAdapterListData().size()>position){
                    setResult(RESULT_OK,new Intent().putExtra("bank",bankListAdapter.getItem(position)));
                    finish();
                }
            }
        });
        InitDate();
    }

    public void InitDate(){
        icon_bank=new HashMap<String,Integer>();
        icon_bank.put("ICBC",R.drawable.bank_gongshang);
        icon_bank.put("CEB",R.drawable.bank_guangda);
        icon_bank.put("GDB",R.drawable.bank_guangfa);
        icon_bank.put("GZCB",R.drawable.bank_guangzhou);
        icon_bank.put("GRCB",R.drawable.bank_guangzhounongshang);
        icon_bank.put("HXB", R.drawable.bank_huaxia);
        icon_bank.put("CCB", R.drawable.bank_jianshe);
        icon_bank.put("CMBC",R.drawable.bank_minsheng);
        icon_bank.put("PAB",R.drawable.bank_pingan);
        icon_bank.put("SDB",R.drawable.bank_shenfazhan);
        icon_bank.put("BOC", R.drawable.bank_zhongguo);
        icon_bank.put("CITIC",R.drawable.bank_zhongxin);
        icon_bank.put("PSBC", R.drawable.bank_youzheng);
        icon_bank.put("SPDB", R.drawable.bank_pufa);
        icon_bank.put("CIB", R.drawable.bank_xingye);
        icon_bank.put("CMB", R.drawable.bank_zhaoshang);
        bankList = new ArrayList<BankItemModel>();

        setHttpRequest();
    }
    public void InitDateAdapter(){
        if (bankList==null||bankList.size()==0)return;
        if (bankListAdapter==null){
            bankListAdapter = new BankListAdapter(this,bankList,R.layout.bank_all_item);
            back_listview.setAdapter(bankListAdapter);
        }
    }
    /**
     * 所有银行卡列表
     */
    public void setHttpRequest(){
        RequestParams params = new RequestParams();
        String url = UrlConstants.urlBase+UrlConstants.urlBankList;
        DMApplication.getInstance().getHttpClient(this).get(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    MyLog.d("api_heads_header=" + headers.toString());
                    if (statusCode == 200) {
                        BaseModel<List<BankItemModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<List<BankItemModel>>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            bankList.addAll(baseModel.getData());
                            InitDateAdapter();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");


            }
        });
    }

    public class BankListAdapter extends CommonAdapter<BankItemModel>{


        public BankListAdapter(Context context, List<BankItemModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, BankItemModel item) {

            TextView tv_bank_name = (TextView) helper.getView(R.id.tv_bank_name);

            Drawable drawable = getResources().getDrawable(icon_bank.get(item.getBankCode()));
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_bank_name.setCompoundDrawables(drawable, null, null, null);

            tv_bank_name.setText(item.getBankName());
            if (TextUtils.isEmpty(item.getDailyLimit())){
                helper.setText(R.id.tv_bank_des,new StringBuffer("单笔限额").append(item.getSingleLimit()).append("万,").append("单日不限").toString());
            }else{
                helper.setText(R.id.tv_bank_des,new StringBuffer("单笔限额").append(item.getSingleLimit()).append("万,").append("单日").append(item.getDailyLimit()).append("万").toString());
            }

        }

    }

}
