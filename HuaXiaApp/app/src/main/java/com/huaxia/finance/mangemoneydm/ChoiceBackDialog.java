package com.huaxia.finance.mangemoneydm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.asychttpclient.RequestParams;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.adapter.CommonAdapter;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
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
 * 选择银行卡
 * Created by houwen.lai on 2016/1/23.
 */
public class ChoiceBackDialog extends Activity {
    private final String mPageName = ChoiceBackDialog.class.getSimpleName();

    private ImageButton btn_news_password;
    private View view_empty;
    private ListView back_listview;

    private Map<String,Integer> icon_bank=new HashMap<String,Integer>();

    private Context mContext;

    private List<BankItemModel> bankList;

    private BankAdapter bankAdapter;

    private boolean hasflag=false;
    private boolean hasflagUL=false;
    private boolean hasflagBL=false;

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
        setContentView(R.layout.choise_bank);
        mContext = this;
        btn_news_password = (ImageButton) findViewById(R.id.btn_news_password);
        btn_news_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);
            }
        });
        InitDate();

    }


    public void InitDate(){
        view_empty= findViewById(R.id.view_empty);
        view_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        back_listview  = (ListView) findViewById(R.id.back_listview);
        back_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bankAdapter.getAdapterListData()!=null&&bankAdapter.getAdapterListData().size()>0){
                    setResult(RESULT_OK,new Intent().putExtra("bank",bankAdapter.getAdapterListData().get(position)));
                    ChoiceBackDialog.this.finish();
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

        setUserHttpRequest();
    }

    public void InitDateAdapter(){
        if (bankList==null||bankList.size()==0)return;
        if (bankAdapter==null){
            bankAdapter = new BankAdapter(this,bankList,R.layout.bank_item);
            back_listview.setAdapter(bankAdapter);
        }else {
            bankAdapter.clearAdapterListData();
            bankAdapter.setAdapterListData(-1, bankList);
            bankAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 所有银行卡列表
     */
    public void setHttpRequest(){
        RequestParams params = new RequestParams();
        String url = UrlConstants.urlBase+UrlConstants.urlBankList;
        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<List<BankItemModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result,new TypeToken<BaseModel<List<BankItemModel>>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)){
                            if (baseModel.getData()!=null&&baseModel.getData().size()>0){
                                baseModel.getData().get(0).setFlagText("可认证");
                                baseModel.getData().get(0).setFlagVisiable(true);
                            }
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

    public List<BankItemModel> getBankListInit(List<BankItemModel> mList){
        List<BankItemModel> list = new ArrayList<BankItemModel>();
        if (mList==null)return null;
        for (int i=0;i<mList.size();i++){
            if (!mList.get(i).getPayStatus().equals("10")){
                list.add(mList.get(i));
            }
        }
        return list;
    }

    /**
     * 用户银行卡列表
     */
    public void setUserHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("payStatus","20");
        String url = UrlConstants.urlBase+UrlConstants.urlUserBankList;//10：认证中；20：认证成功；30：认证失败；

        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<List<BankItemModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result,new TypeToken<BaseModel<List<BankItemModel>>>() {
                        }.getType());
                        bankList.clear();
                        if (baseModel.getStatus().equals(StateConstant.Status_success)){
                            if (baseModel.getData()!=null&&baseModel.getData().size()>0){
                                baseModel.getData().get(0).setFlagText("已认证");
                                baseModel.getData().get(0).setFlagVisiable(true);
                            }
                            //(getBankListInit(baseModel.getData())
                            bankList.addAll(baseModel.getData());//baseModel.getData()
                        }
                        setHttpRequest();
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
     * 银行卡适配器
     */
    private class BankAdapter extends CommonAdapter<BankItemModel> {

        public BankAdapter(Context context, List<BankItemModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @SuppressLint("NewApi")
        @Override
        public void convert(ViewHolder helper, BankItemModel item) {
            //设置值
            if (hasflag){
                if (!hasflagUL){
                    hasflagUL = true;
                    helper.getView(R.id.tv_approve).setVisibility(View.VISIBLE);
                }else helper.getView(R.id.tv_approve).setVisibility(View.GONE);
                helper.setText(R.id.tv_approve,"已认证");
                if (item.getIsDefault().equals("0")){
                    helper.getView(R.id.tv_default).setVisibility(View.VISIBLE);
                }else helper.getView(R.id.tv_default).setVisibility(View.GONE);
            }else {
                if (!hasflagBL){
                    hasflagBL = true;
                    helper.getView(R.id.tv_approve).setVisibility(View.VISIBLE);
                }else helper.getView(R.id.tv_approve).setVisibility(View.GONE);
                helper.setText(R.id.tv_approve, "可认证");

                if (item.isFlagVisiable()){
                    helper.getView(R.id.tv_approve).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_approve,item.getFlagText());
                }else{
                    helper.getView(R.id.tv_approve).setVisibility(View.GONE);
                }
            }
            if(icon_bank.get(item.getBankCode())!=null){
                helper.setImageResource(R.id.img_bank_icon,icon_bank.get(item.getBankCode()));
            }

//           TextView tv_name_logo = (TextView)helper.getView(R.id.img_bank_icon);
//            if (icon_bank.get(item.getBankCode())!=null) {
//                Drawable drawable = getResources().getDrawable(icon_bank.get(item.getBankCode()));
//                /// 这一步必须要做,否则不会显示.
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tv_name_logo.setCompoundDrawables(drawable, null, null, null);
//            }

            if (TextUtils.isEmpty(item.getPkId())){
                helper.setText(R.id.tv_name_bank,item.getBankName());
//                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.black_f5f5));
                helper.getView(R.id.linear_choise_bank).setBackgroundResource(R.drawable.back_gray);
            }else {
//                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.white));
                helper.getView(R.id.linear_choise_bank).setBackgroundResource(R.drawable.back_white);

                String bankNo = item.getPkId();
                helper.setText(R.id.tv_name_bank,item.getBankName()+" 尾号"+item.getCardNo());
            }

            if (TextUtils.isEmpty(item.getDailyLimit())){
                helper.setText(R.id.tv_name_detail,new StringBuffer("单笔限额").append(item.getSingleLimit()).append("万,单日不限").toString());
            }else{
                helper.setText(R.id.tv_name_detail,new StringBuffer("单笔限额").append(item.getSingleLimit()).append("万,单日").append(item.getDailyLimit()).append("万").toString());
            }
            if (item.getIsDefault().equals("0")) helper.getView(R.id.tv_default).setVisibility(View.VISIBLE);
            else helper.getView(R.id.tv_default).setVisibility(View.GONE);
        }
    }

}
