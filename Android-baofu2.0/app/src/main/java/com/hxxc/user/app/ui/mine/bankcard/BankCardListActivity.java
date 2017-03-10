package com.hxxc.user.app.ui.mine.bankcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hxxc.user.app.Event.BankCardEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.data.bean.BankCardItemBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.ui.product.BaofuBingCardActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by houwen.lai on 2016/10/28.
 * 银行卡列表
 */

public class BankCardListActivity extends BaseRxActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_share)
    ImageButton toolbar_share;

    @BindView(R.id.listview_bankcard)
    ListView listview;

    @BindView(R.id.linearLayout_banklist)
    LinearLayout linearLayout_banklist;

    List<BankCardItemBean> lists;
    BankCardAdapter bankcardAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.bankcard_list;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        toolbar_title.setText("我的银行卡");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar_share.setVisibility(View.VISIBLE);
        toolbar_share.setImageResource(R.mipmap.ico_add);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setDefaultBankCard(bankcardAdapter.getItem(i).getIid() + "");
            }
        });
        listview.post(new Runnable() {
            @Override
            public void run() {
                getBankList();
            }
        });


//        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getBankList();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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

    @OnClick({R.id.toolbar_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_share://添加银行卡
                if (BtnUtils.isFastDoubleClick()) {
                    if (SPUtils.geTinstance().getUserInfo() != null && SPUtils.geTinstance().getUserInfo().getRnaStatus() == 0) {//认证
                        ToastUtil.showSafeToast(this, "请认证");
                        startActivity(new Intent(mContext, AuthenticationActivity.class).putExtra("from", AuthenticationActivity.FROM_MyBankCard));
                    } else {//绑卡
                        startActivity(new Intent(mContext, BaofuBingCardActivity.class));
                    }
                }
                break;
        }
    }

    public void setAdapter() {

        if (lists == null) lists = new ArrayList<BankCardItemBean>();

        for (int i = 0; i < 10; i++) {
            BankCardItemBean bean = new BankCardItemBean();
            bean.setBankName("招商银行");
            bean.setBankCard("**** **** **** 6941");

            bean.setFlag(i % 2 == 0 ? true : false);
            bean.setQuota("每日限额1万");
            lists.add(bean);
        }

//        bankcardAdapter = new BankCardAdapter(mContext,lists,R.layout.bankcard_item);
//        listview.setAdapter(bankcardAdapter);
    }

    /**
     *
     */
    public class BankCardAdapter extends CommonAdapter<Account> {

        public BankCardAdapter(Context context, List<Account> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, Account item, int position) {
            super.convert(helper, item, position);

            Picasso.with(mContext)
                    .load(item.getBankColour())
                    .placeholder(R.drawable.red_bank)
                    .error(R.drawable.red_bank)
                    .into((ImageView) helper.getView(R.id.img_bank_bg));


//            helper.setImageResource(R.id.img_icon_bank,R.mipmap.icon_pay_bank);
            Picasso.with(mContext)
                    .load(item.getMobileLogoUrl())
                    .placeholder(R.mipmap.icon_my_head)
                    .error(R.mipmap.icon_my_head)
                    .into((ImageView) helper.getView(R.id.img_icon_bank));

            helper.setText(R.id.tv_bank_name, item.getBank());
            helper.setText(R.id.tv_bank_quota, "单笔" + CommonUtil.checkMoney2(item.getSingleWLimit().floatValue()) + "万,单日" + CommonUtil.checkMoney2(item.getDailyWLimit().floatValue()) + "万");
            helper.setText(R.id.tv_bank_card, "**** **** **** **** " + item.getBankCard());

            if (item.getIsDefaultCard() == 1) {
                helper.setText(R.id.tv_bank_choise, "默认银行卡");
                ((ImageView) helper.getView(R.id.checkBox_bank)).setImageResource(R.mipmap.add_bank_s);
            } else {
                helper.setText(R.id.tv_bank_choise, "设为默认卡");
                ((ImageView) helper.getView(R.id.checkBox_bank)).setImageResource(R.mipmap.add_bank_n);
            }

        }
    }

    private List<Account> list = new ArrayList<>();

    /**
     * 获取银行卡列表
     */
    public void getBankList() {
        ApiManager.getInstance().querySubAccountList(SPUtils.geTinstance().getUid(), new SimpleCallback<List<Account>>() {
            @Override
            public void onNext(List<Account> datas) {
                list.clear();
                if (null != datas) {
                    list.addAll(datas);
                }
                if (datas != null && datas.size() > 0) {
                    if (null == bankcardAdapter) {
                        bankcardAdapter = new BankCardAdapter(mContext, list, R.layout.bankcard_item);
                        listview.setAdapter(bankcardAdapter);
                    } else {
                        bankcardAdapter.notifyDataSetChanged();
                    }
                    listview.setVisibility(View.VISIBLE);
                    linearLayout_banklist.setVisibility(View.GONE);
                } else {
                    if (null != bankcardAdapter) {
                        bankcardAdapter.notifyDataSetChanged();
                    }
                    listview.setVisibility(View.GONE);
                    linearLayout_banklist.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                listview.setVisibility(View.GONE);
                linearLayout_banklist.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setDefaultBankCard(String iid) {
        Api.getClient().getAccountDefault(Api.uid, iid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<String>(mContext) {
                    @Override
                    public void onSuccess(String s) {
                        ToastUtil.ToastShort(mContext, "默认卡设置成功");
                        getBankList();
                    }

                    @Override
                    public void onFail(String fail) {
                    }
                });
    }

    public void onEventMainThread(BankCardEvent event) {
        getBankList();
    }
}
