package com.huaxia.finance.minedm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.adapter.CommonAdapter;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.mangemoneydm.ApproveActivity;
import com.huaxia.finance.model.BankItemModel;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行卡列表
 * Created by houwen.lai on 2016/2/5.
 */
public class UserBankListActivity extends BaseActivity {
    private final String mPageName = UserBankListActivity.class.getSimpleName();

    private Context mContext;
    private ListView back_listview;

    private View view_add_bank;
    private Button btn_add_bank;
    private View view_add_warming;

    private TextView tv_no_bank;
    private Button btn_add_bank_n;

    private Map<String,Integer> icon_bank=new HashMap<String,Integer>();

    private List<BankItemModel> bankList;

    private BankAdapter bankAdapter;

    private int RequestCode = 0x12;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==RequestCode&&resultCode==RESULT_OK){
            setUserHttpRequest();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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
        setBaseContentView(R.layout.user_bank_list);

        mContext = this;

        initViews();


    }
    public void initViews() {
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title_bar.setText("银行卡");
        img_btn_title_right.setVisibility(View.GONE);

        InitDate();

    }

    public void InitDate(){
        tv_no_bank = (TextView) findViewById(R.id.tv_no_bank);
        btn_add_bank_n = (Button) findViewById(R.id.btn_add_bank_n);
        btn_add_bank_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UserBankListActivity.this, ApproveActivity.class).putExtra("title", "实名认证"), RequestCode);
            }
        });

        back_listview  = (ListView) findViewById(R.id.listview_bank);
        back_listview.setDividerHeight(0);
        back_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bankAdapter.getAdapterListData() != null && bankAdapter.getAdapterListData().size() > 0) {
                    if (bankAdapter.getAdapterListData().get(position).getIsDefault().equals("1") && !bankAdapter.getAdapterListData().get(position).getPayStatus().equals("10")) {
                        setDefunltBankHttpRequest(bankAdapter.getItem(position).getPkId());
                    }
                }
            }
        });

        icon_bank=new HashMap<String,Integer>();
        icon_bank.put("ICBC",R.drawable.bank_gongshang);
        icon_bank.put("CEB",R.drawable.bank_guangda);
        icon_bank.put("GDB",R.drawable.bank_guangfa);
        icon_bank.put("GZCB",R.drawable.bank_guangzhou);
        icon_bank.put("GRCB",R.drawable.bank_guangzhounongshang);
        icon_bank.put("HXB",R.drawable.bank_huaxia);
        icon_bank.put("CCB",R.drawable.bank_jianshe);
        icon_bank.put("CMBC",R.drawable.bank_minsheng);
        icon_bank.put("PAB",R.drawable.bank_pingan);
        icon_bank.put("SDB",R.drawable.bank_shenfazhan);
        icon_bank.put("BOC",R.drawable.bank_zhongguo);
        icon_bank.put("CITIC",R.drawable.bank_zhongxin);
        icon_bank.put("PSBC",R.drawable.bank_youzheng);
        icon_bank.put("SPDB",R.drawable.bank_pufa);
        icon_bank.put("CIB",R.drawable.bank_xingye);
        icon_bank.put("CMB",R.drawable.bank_zhaoshang);
        bankList = new ArrayList<BankItemModel>();

        view_add_bank = View.inflate(this,R.layout.btn_bank_add,null);
        btn_add_bank = (Button) view_add_bank.findViewById(R.id.btn_add_bank);
        btn_add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UserBankListActivity.this, ApproveActivity.class),RequestCode );
            }
        });

        view_add_warming = View.inflate(this,R.layout.text_waming_invest,null);

        //请求数据
        if (NetWorkUtils.isNetworkConnected(this)){
            relative_no_work.setVisibility(View.GONE);
            setUserHttpRequest();
        }else{
            relative_no_work.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_no_wifi);
            tv_reloading.setVisibility(View.VISIBLE);
            tv_reloading.setText("网络出现问题啦!");
        }

    }

    public void InitDateAdapter(List<BankItemModel> models){
        if (models==null||models.size()==0){
            back_listview.setVisibility(View.GONE);
            tv_no_bank.setVisibility(View.VISIBLE);
            btn_add_bank_n.setVisibility(View.VISIBLE);
            return;
        }
        if (bankList==null){
            bankList = new ArrayList<BankItemModel>();
        }
        bankList.addAll(models);
        back_listview.setVisibility(View.VISIBLE);
        tv_no_bank.setVisibility(View.GONE);
        btn_add_bank_n.setVisibility(View.GONE);
        if (bankAdapter==null){
            bankAdapter = new BankAdapter(this,bankList,R.layout.user_bank_item);
            back_listview.addFooterView(view_add_bank);
            back_listview.addFooterView(view_add_warming);
            back_listview.setAdapter(bankAdapter);
        }else {
            bankAdapter.clearAdapterListData();
            bankAdapter.setAdapterListData(-1, models);
            bankAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 用户银行卡列表
     */
    public void setUserHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("payStatus","10+20");
        String url = UrlConstants.urlBase+UrlConstants.urlUserBankList;
        MyLog.d("api_url="+url.toString());
        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<List<BankItemModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<List<BankItemModel>>>() {
                        }.getType());
                        bankList.clear();
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

                            InitDateAdapter(baseModel.getData());
                        }else ToastUtils.showShort(baseModel.getMsg());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");

                if (bankAdapter!=null){
                    if (bankAdapter.getCount()==0){
                        relative_no_work.setVisibility(View.VISIBLE);
                        img_empty.setVisibility(View.VISIBLE);
                        img_empty.setImageResource(R.drawable.icon_no_wifi);
                        tv_reloading.setVisibility(View.VISIBLE);
                        tv_reloading.setText("网络出现问题啦!");
                    }
                }else{
                    relative_no_work.setVisibility(View.VISIBLE);
                    img_empty.setVisibility(View.VISIBLE);
                    img_empty.setImageResource(R.drawable.icon_no_wifi);
                    tv_reloading.setVisibility(View.VISIBLE);
                    tv_reloading.setText("网络出现问题啦!");
                }
            }
        });
    }

    /**
     * 设置默认银行卡
     */
    public void setDefunltBankHttpRequest(String pkId){
        BaseRequestParams params = new BaseRequestParams();
        params.put("pkId",pkId);
        String url = UrlConstants.urlBase+UrlConstants.urlUserSetDefCard;
        MyLog.d("api_setDefcard_url="+url+"_pkId="+pkId);
        DMApplication.getInstance().getHttpClient(this).post(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<String> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<String>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            ToastUtils.showShort(baseModel.getMsg());
                            setUserHttpRequest();
                        }else ToastUtils.showShort(baseModel.getMsg());
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

    /**
     * 产品适配器
     */
    private class BankAdapter extends CommonAdapter<BankItemModel> {

        public BankAdapter(Context context, List<BankItemModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, BankItemModel item) {
            //设置值

            helper.setImageResource(R.id.img_icon_bank, icon_bank.get(item.getBankCode()));

            helper.setText(R.id.tv_bank_name, item.getBankName());
            TextView tv_bank_detail = helper.getView(R.id.tv_bank_detail);

            if (TextUtils.isEmpty(item.getDailyLimit())){
                tv_bank_detail.setText(new StringBuffer("单笔限额").append(item.getSingleLimit()).append("万,单日不限").toString());
            }else{
                tv_bank_detail.setText(new StringBuffer("单笔限额").append(item.getSingleLimit()).append("万,单日").append(item.getDailyLimit()).append("万").toString());
            }

            TextView tv_name_logo = (TextView)helper.getView(R.id.tv_bank_default);
            if (item.getPayStatus().equals("10")){//
                tv_name_logo.setCompoundDrawables(null, null, null, null);
                tv_name_logo.setTextColor(getResources().getColor(R.color.blue_2299));
                tv_name_logo.setText("处理中");
            }else if (item.getIsDefault().equals("0")) {//"0"默认  "1"
                Drawable drawable = getResources().getDrawable(R.drawable.icon_choice_y);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tv_name_logo.setCompoundDrawablePadding(10);
                tv_name_logo.setCompoundDrawables(drawable, null, null, null);
                tv_name_logo.setTextColor(getResources().getColor(R.color.orange_ff92));
                tv_name_logo.setText("默认支付卡");
            }else {
                Drawable drawable = getResources().getDrawable(R.drawable.icon_choice_n);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tv_name_logo.setCompoundDrawablePadding(10);
                tv_name_logo.setCompoundDrawables(drawable, null, null, null);
                tv_name_logo.setTextColor(getResources().getColor(R.color.black_9999));
                tv_name_logo.setText("设为默认卡");
            }
//            helper.setText(R.id.tv_bank_cardno, FunctionUtils.hideCertNo(item.getCardNo()));
            helper.setText(R.id.tv_bank_cardno,"xxxx xxxx xxxx "+item.getCardNo());

        }
    }


}
