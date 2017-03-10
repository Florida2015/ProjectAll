package com.huaxia.finance.minedm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * 我的 优惠券
 * Created by houwen.lai on 2016/2/1.
 */
public class CashVoucherActivity extends BaseActivity implements View.OnClickListener {
    private final String mPageName = CashVoucherActivity.class.getSimpleName();

    public Button btn_user;
    public Button btn_is_user;
    public Button btn_user_over;
    public View view_user;
    public View view_is_user;
    public View view_user_over;

    private static Fragment currunt;

    CashVourIsUserFragment cashVourIsUserFragment;
    CashVourUserFragment cashVourUserFragment;
    CashVourOverFragment cashVourOverFragment;

    public static CashVoucherActivity instance;

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
        setBaseContentView(R.layout.cash_voucher_activity);
        instance = this;
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("优惠券");
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_user = (Button) findViewById(R.id.btn_user);
        btn_is_user = (Button) findViewById(R.id.btn_is_user);
        btn_user_over = (Button) findViewById(R.id.btn_user_over);
        view_user = (View) findViewById(R.id.view_user);
        view_is_user = (View) findViewById(R.id.view_is_user);
        view_user_over = (View) findViewById(R.id.view_user_over);

        btn_user.setOnClickListener(this);
        btn_is_user.setOnClickListener(this);
        btn_user_over.setOnClickListener(this);

        cashVourUserFragment = new CashVourUserFragment();
        cashVourIsUserFragment = new CashVourIsUserFragment();
        cashVourOverFragment = new CashVourOverFragment();

        currunt = cashVourOverFragment;

        SeleteTopChoice(0);
        switchContent(cashVourUserFragment);

    }

    public void switchContent(Fragment to) {
        Fragment from;
        if (currunt != null && currunt != to) {
            from = currunt;
            currunt = to;//this.getSupportFragmentManager()
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//TRANSIT_FRAGMENT_FADE
            if (!to.isAdded()) { // 先判断是否被add过
                if (!from.isAdded()) {
                    transaction.add(R.id.frame_content_cash, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).add(R.id.frame_content_cash, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
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
            case R.id.btn_user://
                SeleteTopChoice(0);
                switchContent(cashVourUserFragment);
                break;
            case R.id.btn_is_user://
                SeleteTopChoice(1);
                switchContent(cashVourIsUserFragment);
                break;
            case R.id.btn_user_over://
                SeleteTopChoice(2);
                switchContent(cashVourOverFragment);
                break;
        }
    }

    public void SeleteTopChoice(int index) {
        btn_user.setTextColor(getResources().getColor(R.color.black_9999));
        btn_is_user.setTextColor(getResources().getColor(R.color.black_9999));
        btn_user_over.setTextColor(getResources().getColor(R.color.black_9999));
        view_user.setBackgroundColor(getResources().getColor(R.color.white));
        view_is_user.setBackgroundColor(getResources().getColor(R.color.white));
        view_user_over.setBackgroundColor(getResources().getColor(R.color.white));
        if (0 == index) {
            btn_user.setTextColor(getResources().getColor(R.color.orange_ff92));
            view_user.setBackgroundColor(getResources().getColor(R.color.orange_ff92));
        } else if (index == 1) {
            btn_is_user.setTextColor(getResources().getColor(R.color.orange_ff92));
            view_is_user.setBackgroundColor(getResources().getColor(R.color.orange_ff92));
        } else if (index == 2) {
            btn_user_over.setTextColor(getResources().getColor(R.color.orange_ff92));
            view_user_over.setBackgroundColor(getResources().getColor(R.color.orange_ff92));
        }
    }

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x12){
                instance.finish();
            }
            super.handleMessage(msg);
        }
    };

    public static void finishCashActivity(){
        MenuTwoActivity.getInstance().setTab(1);
        handler.sendEmptyMessageDelayed(0x12,800);
    }

}
