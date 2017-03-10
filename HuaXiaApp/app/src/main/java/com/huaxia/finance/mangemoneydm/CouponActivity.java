package com.huaxia.finance.mangemoneydm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.adapter.CommonAdapter;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UmengConstants;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.CashVoucherModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 支付 优惠券列表
 * Created by houwen.lai on 2016/1/25.
 */
public class CouponActivity extends Activity implements View.OnClickListener{
    private final String mPageName = CouponActivity.class.getSimpleName();

    private Button btn_order_agreement;

    private View view_empty;

    private ListView coupon_listview;
    private TextView tv_empty_nolist;

    private Context mContext;

    private int PayMoney=0;
    private String productId="";

    List<CashVoucherModel> couponList ;
    CouponAdapter couponAdapter;

    private BaseModel<List<CashVoucherModel>> baseModel;

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
        setContentView(R.layout.coupon_activity);
        mContext = this;
        PayMoney = getIntent().getIntExtra("money", 0);
        productId = getIntent().getStringExtra("productId");
        baseModel = (BaseModel<List<CashVoucherModel>>) getIntent().getSerializableExtra("baseMode");
        if (baseModel!=null)
        MyLog.d("api_coup_List="+baseModel.toString());

        view_empty= findViewById(R.id.view_empty);
        view_empty.setOnClickListener(this);
        btn_order_agreement = (Button) findViewById(R.id.btn_order_agreement);
        btn_order_agreement.setOnClickListener(this);

        tv_empty_nolist = (TextView) findViewById(R.id.tv_empty_nolist);
        tv_empty_nolist.setVisibility(View.GONE);
        coupon_listview = (ListView) findViewById(R.id.coupon_listview);
        coupon_listview.setVisibility(View.VISIBLE);
        coupon_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(couponAdapter.getAdapterListData().get(position).getIsUseAble()==0&&PayMoney>=couponAdapter.getAdapterListData().get(position).getThreshold()){

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("productId", productId != null ?productId:"null");
                    map.put("pkid", couponAdapter.getAdapterListData().get(position).getPkid());
                    map.put("discountVoucherId", couponAdapter.getAdapterListData().get(position).getDiscountVoucherId());
                    map.put("payCouponListItemClick", "payCouponListItemClick");
                    MobclickAgent.onEvent(CouponActivity.this, UmengConstants.huaxia_083, map);

                    setResult(RESULT_OK, new Intent().putExtra("coupon", couponAdapter.getAdapterListData().get(position)));
                    CouponActivity.this.finish();
                    overridePendingTransition(R.anim.fade, R.anim.fade);
                }
            }
        });
//        setHttpRequest();
        if (baseModel!=null&&baseModel.getStatus().equals(StateConstant.Status_success)) {
            tv_empty_nolist.setVisibility(View.GONE);
            InitDateAdapter(DcouponInit(baseModel.getData()));
        } else {
            setHttpRequest();
        }
    }

    CashVoucherModel cashVoucherModel;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_order_agreement:
                setResult(RESULT_OK,new Intent().putExtra("coupon",cashVoucherModel));
                finish();
                overridePendingTransition(R.anim.fade, R.anim.fade);
//                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
                break;
            case R.id.view_empty:
                setResult(RESULT_OK,new Intent().putExtra("coupon",cashVoucherModel));
                finish();
                overridePendingTransition(R.anim.fade, R.anim.fade);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            setResult(RESULT_OK,new Intent().putExtra("coupon",cashVoucherModel));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public  List<CashVoucherModel> DcouponInit(List<CashVoucherModel> list){
        if (list==null)return null;
        List<CashVoucherModel> cList=new ArrayList<CashVoucherModel>();
        List<CashVoucherModel> pList=new ArrayList<CashVoucherModel>();
        for (int i=0;i<list.size();i++){
            list.get(i).setIndexx(0);
            if (list.get(i).getIsUseAble()==0&&PayMoney>=list.get(i).getThreshold()){
                if (i==0)list.get(i).setIndexx(1);
                cList.add(list.get(i));
            }else{
                pList.add(list.get(i));
            }
        }
        if (pList!=null&&pList.size()>0)
        pList.get(0).setIndexx(2);
        cList.addAll(pList);
        return cList;
    }


    public void InitDateAdapter(List<CashVoucherModel> list){
        if (couponList==null)couponList = new ArrayList<CashVoucherModel>();
        tv_empty_nolist.setVisibility(View.GONE);
        coupon_listview.setVisibility(View.VISIBLE);
        couponList.clear();
        couponList.addAll(list);
        couponAdapter = new CouponAdapter(this,couponList,R.layout.coupon_item);
        coupon_listview.setAdapter(couponAdapter);
        if (couponList.size()==0){
            coupon_listview.setVisibility(View.GONE);
            tv_empty_nolist.setVisibility(View.VISIBLE);
        }else{
            coupon_listview.setVisibility(View.VISIBLE);
            tv_empty_nolist.setVisibility(View.GONE);
        }
    }

    /**
     * 根据用户id和产品分类获取优惠券
     */
    public void setHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("productId", productId);//产品类型
        String url = UrlConstants.urlBase+UrlConstants.urlFindCashVoucher;
        MyLog.d("api_url="+url+"_productId="+productId);
        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<List<CashVoucherModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<List<CashVoucherModel>>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            tv_empty_nolist.setVisibility(View.GONE);
                            InitDateAdapter(DcouponInit(baseModel.getData()));
                        } else {
                            tv_empty_nolist.setVisibility(View.VISIBLE);
                            coupon_listview.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    tv_empty_nolist.setVisibility(View.VISIBLE);
                    coupon_listview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");


            }
        });
    }




    /**
     * 优惠券适配器
     */
    private class CouponAdapter extends CommonAdapter<CashVoucherModel> {

        public CouponAdapter(Context context, List<CashVoucherModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, CashVoucherModel item) {
            //设置值
            TextView tv_title_coupon = helper.getView(R.id.tv_title_coupon);
            tv_title_coupon.setVisibility(View.GONE);

            TextView tv_coupon_title = helper.getView(R.id.tv_coupon_title);
            TextView tv_coupon_detail = helper.getView(R.id.tv_coupon_detail);
            TextView tv_over_time= helper.getView(R.id.tv_over_time);

            if (item.getIndexx()==1){
                tv_title_coupon.setText("可用");
                tv_title_coupon.setVisibility(View.VISIBLE);
            }else if(item.getIndexx()==2){
                tv_title_coupon.setText("不可用");
                tv_title_coupon.setVisibility(View.VISIBLE);
            }else{
                tv_title_coupon.setVisibility(View.GONE);
            }

            if (item.getIsUseAble()==0&&PayMoney>=item.getThreshold()) {//可用
                tv_coupon_detail.setTextColor(getResources().getColor(R.color.black_9999));
                tv_over_time.setTextColor(getResources().getColor(R.color.black_6666));
                if (item.getGainType()==1){//1.收益率
                    tv_coupon_title.setTextColor(getResources().getColor(R.color.red_e44b));
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.payment_y_d);
                }else if(item.getGainType()==2){//2.本金券
                    tv_coupon_title.setTextColor(getResources().getColor(R.color.orange_ff92));
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.gold_y_d);
                }else if(item.getGainType()==3){//3.抵扣券
                    tv_coupon_title.setTextColor(getResources().getColor(R.color.blue_2299));
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.allow_y_s);
                }else if(item.getGainType()==4){//4.加金券
                    tv_coupon_title.setTextColor(getResources().getColor(R.color.orange_fbbd));
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.add_y_d);
                }else if(item.getGainType()==5){//5.现金券
                    tv_coupon_title.setTextColor(getResources().getColor(R.color.red_e44b));
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.money_y_d);//
                }else{
                    tv_coupon_title.setTextColor(getResources().getColor(R.color.orange_fbbd));
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.add_y_d);
                }
            }else{
                tv_coupon_title.setTextColor(getResources().getColor(R.color.black_d9d9));
                tv_coupon_detail.setTextColor(getResources().getColor(R.color.black_d9d9));
                tv_over_time.setTextColor(getResources().getColor(R.color.black_d9d9));
                if (item.getGainType()==1){//1.收益率
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.payment_n_d);
                }else if(item.getGainType()==2){//2.本金券
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.gold_n_d);
                }else if(item.getGainType()==3){//3.抵扣券
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.allow_n_s);
                }else if(item.getGainType()==4){//4.加金券
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.add_n_d);
                }else if(item.getGainType()==5){//5.现金券
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.money_n_d);//
                }else{
                    helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.add_n_d);
                }
            }

            tv_coupon_title.setText(item.getName());
            tv_coupon_detail.setText(item.getComments());
            tv_over_time.setText(DateUtils.getInstanse().getmstodated(item.getEndDate2(), DateUtils.YYYYMMDD)+"到期");

        }
    }
}
