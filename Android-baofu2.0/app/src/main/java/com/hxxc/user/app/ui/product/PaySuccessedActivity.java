package com.hxxc.user.app.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.Event.PaySuccessEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.MainActivity2;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.order.OrderDetailActivity;
import com.hxxc.user.app.utils.ThreadManager;
import com.hxxc.user.app.widget.MyClickButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/6/28.
 */
public class PaySuccessedActivity extends ToolbarActivity {
    @BindView(R.id.btn_buy)
    MyClickButton btn_buy;
    @BindView(R.id.btn_order)
    MyClickButton btn_order;
    @BindView(R.id.tv_count)
    TextView tv_count;
    public boolean a = true;
    private String orderNo;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("加入状态");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        orderNo = getIntent().getStringExtra("orderNo");
        initView();
        ThreadManager.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 3; i >= -1 && a; i--) {
                    EventBus.getDefault().post(new PaySuccessEvent(i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        a = false;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {

        btn_buy.setOnMyClickListener(new MyClickButton.MyClickListener() {

            @Override
            public void onMyClickListener() {
                btn_buy.begin();
                a = false;
                MainEvent event = new MainEvent(1);
                event.loginType = MainEvent.FROMFINDPASSWORD;
                EventBus.getDefault().post(event);
                Intent in = new Intent(PaySuccessedActivity.this, MainActivity2.class);
                startActivity(in);
                btn_buy.finish();
                PaySuccessedActivity.this.finish();
            }
        });
        btn_order.setOnMyClickListener(new MyClickButton.MyClickListener() {

            @Override
            public void onMyClickListener() {
                btn_order.begin();
                a = false;
                getLastestOrderInfo();
            }
        });
    }

    private void getLastestOrderInfo() {
        MainEvent event = new MainEvent(3);
        event.loginType = MainEvent.FROMFINDPASSWORD;
        EventBus.getDefault().post(event);

        HXXCApplication.getInstance().setIsInBackground(false);

        Intent in = new Intent(PaySuccessedActivity.this, OrderDetailActivity.class);
        in.putExtra("orderNo", orderNo);
        startActivity(in);
        btn_order.finish();
        PaySuccessedActivity.this.finish();
    }

    public void onEventMainThread(final PaySuccessEvent event) {
        if (event.i < 0) {
            getLastestOrderInfo();
        } else {
            tv_count.setText(event.i + "秒");
        }
    }
}
