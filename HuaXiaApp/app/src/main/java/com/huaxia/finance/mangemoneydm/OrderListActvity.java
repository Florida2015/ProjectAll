package com.huaxia.finance.mangemoneydm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.OrderDetailModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
/**
 * 4块 订单列表
 * Created by houwen.lai on 2016/1/26.
 */
public class OrderListActvity extends BaseActivity implements View.OnClickListener {
    private final String mPageName = OrderListActvity.class.getSimpleName();

    private Button btn_order_succes;
    private Button btn_order_refund;
    private Button btn_order_fail;
    private Button btn_order_dealing;

    private static Fragment currunt;

    OrderSuccessFragment orderSuccessFragment;
    OrderRefundFragment orderRefundFragment;
    OrderFailFragment orderFailFragment;
    OrderDeailWithFragment orderDeailWithFragment;

    private int xindex = 0;

    private String orderId;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x011){
                slectButton(xindex);
            }
            super.handleMessage(msg);
        }
    };

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
        setBaseContentView(R.layout.orderlist_activity);
        xindex = getIntent().getIntExtra("type", 0);
        orderId = getIntent().getStringExtra("orderId");
        findAllViews();

    }

    public void findAllViews() {
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("我的订单");
        img_btn_title_back.setOnClickListener(this);

        btn_order_succes = (Button) findViewById(R.id.btn_order_succes);
        btn_order_refund = (Button) findViewById(R.id.btn_order_refund);
        btn_order_fail = (Button) findViewById(R.id.btn_order_fail);
        btn_order_dealing = (Button) findViewById(R.id.btn_order_dealing);
        btn_order_succes.setOnClickListener(this);
        btn_order_refund.setOnClickListener(this);
        btn_order_fail.setOnClickListener(this);
        btn_order_dealing.setOnClickListener(this);


        orderSuccessFragment = new OrderSuccessFragment();
        orderRefundFragment = new OrderRefundFragment();
        orderFailFragment = new OrderFailFragment();
        orderDeailWithFragment = new OrderDeailWithFragment();

        currunt = orderFailFragment;

        if (TextUtils.isEmpty(orderId)) {
//            slectButton(xindex);
            handler.sendEmptyMessageDelayed(0x011,800);
        } else {
            RequestDate();
        }

    }

    public void switchContent(Fragment to) {
        Fragment from;
        if (currunt != null && currunt != to) {
            from = currunt;
            currunt = to;//this.getSupportFragmentManager()
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction().setTransition(
                            TRANSIT_FRAGMENT_FADE);
            if (!to.isAdded()) { // 先判断是否被add过
                if (!from.isAdded()) {
                    transaction.add(R.id.frame_content_order, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).add(R.id.frame_content_order, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                }
//                transaction.hide(from).add(R.id.frame_content, to).commitAllowingStateLoss(); //
                // 隐藏当前的fragment，add下一个到Activity中
            } else {
                //commit(x)可能报异常：http://www.cnblogs.com/zgz345/archive/2013/03/04/2942553.html
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_title_back:
//                MenuTwoActivity.getInstance().setTab(3);
                finish();
                break;
            case R.id.btn_order_succes://成功订单
                slectButton(0);
                break;
            case R.id.btn_order_refund://已还款
                slectButton(1);
                break;
            case R.id.btn_order_fail://失败订单
                slectButton(2);
                break;
            case R.id.btn_order_dealing://处理中
                slectButton(3);
                break;
        }
    }

    public void slectButton(int index) {
        switch (index) {
            case 0:
                btn_order_succes.setSelected(true);
                btn_order_refund.setSelected(false);
                btn_order_fail.setSelected(false);
                btn_order_dealing.setSelected(false);
                switchContent(orderSuccessFragment);
                break;
            case 1:
                btn_order_succes.setSelected(false);
                btn_order_refund.setSelected(true);
                btn_order_fail.setSelected(false);
                btn_order_dealing.setSelected(false);
                switchContent(orderRefundFragment);
                break;
            case 2:
                btn_order_succes.setSelected(false);
                btn_order_refund.setSelected(false);
                btn_order_fail.setSelected(true);
                btn_order_dealing.setSelected(false);
                switchContent(orderFailFragment);
                break;
            case 3:
                btn_order_succes.setSelected(false);
                btn_order_refund.setSelected(false);
                btn_order_fail.setSelected(false);
                btn_order_dealing.setSelected(true);
                switchContent(orderDeailWithFragment);
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
//            MenuTwoActivity.getInstance().setTab(3);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     */
    public void RequestDate() {
        BaseRequestParams params = new BaseRequestParams();
        params.put("orderId", "" + orderId);
        params.put("isFindResult", "false");
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlOrderInfo);
        MyLog.d("api=" + url + "_orderId=" + orderId);
        DMApplication.getInstance().getHttpClient(this).get(this, url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);
                    if (statusCode == 200) {
                        BaseModel<OrderDetailModel> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate, new TypeToken<BaseModel<OrderDetailModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            MyLog.d("api_basemodel=" + baseModel.getData().toString());
                            if (baseModel.getData().getOrderStatus().equals("10")) {
                                xindex = 3;
                                slectButton(3);
                            } else if (baseModel.getData().getOrderStatus().equals("21")) {
                                xindex = 2;
                                slectButton(2);
                            } else if (baseModel.getData().getOrderStatus().equals("20")) {
                                xindex = 0;
                                slectButton(0);
                            }
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
