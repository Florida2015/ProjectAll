package com.hxxc.huaxing.app.ui.mine.autobid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.InvestQueryBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/2.
 * 选择年化收益率
 */

public class ChoiseInvestActivity extends Activity implements AdapterView.OnItemClickListener {

    @BindView(R.id.img_btn_close_choise)
    ImageButton img_btn_close_choise;
    @BindView(R.id.list_bid_data)
    ListView list_bid_data;

    InvestAdapter investAdapter;

    private String text = "";
    private int index = 0;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_auto_data);
        ButterKnife.bind(this);

        mContext = this;

        text = getIntent().getStringExtra("text");

        img_btn_close_choise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
        list_bid_data.setDividerHeight(1);
        list_bid_data.setOnItemClickListener(this);


        getYeildInvest();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.tv_cancal_auto})
    public void onClick(View view){
        if (view.getId() == R.id.tv_cancal_auto){//完成
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //选择
        investAdapter.getItem(index).setFlag(false);
        investAdapter.getItem(position).setFlag(true);
        index=position;
        investAdapter.notifyDataSetChanged();

        setResult(RESULT_OK,new Intent().putExtra("text",investAdapter.getItem(position).getRemarks()));
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    private List<InvestQueryBean> lists = new ArrayList<InvestQueryBean>();

    private void InitData(String[] been){
        if (been==null) return;
        lists.clear();
        for (int i=0;i< been.length;i++){
            InvestQueryBean investQueryBean = new InvestQueryBean();
            if (!TextUtils.isEmpty(been[i])&&been[i].equals(text)){
                index = i;
                investQueryBean.setFlag(true);
            }else   investQueryBean.setFlag(false);
            investQueryBean.setRemarks(been[i]+"%");
            lists.add(investQueryBean);
        }
        investAdapter = new InvestAdapter(this,lists,R.layout.bid_auto_data_item);
        list_bid_data.setAdapter(investAdapter);
    }

    /**
     * 选择期限 adapter
     */
    public class InvestAdapter extends CommonAdapter<InvestQueryBean> {

        public InvestAdapter(Context context, List<InvestQueryBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, InvestQueryBean item, int position) {
            super.convert(helper, item, position);

            helper.setText(R.id.tv_bid_item_name,item.getRemarks());
            if (item.isFlag())
                ((ImageView)helper.getView(R.id.img_bid_choise)).setImageResource(R.mipmap.icon_chox_y);
            else  ((ImageView)helper.getView(R.id.img_bid_choise)).setImageResource(R.mipmap.icon_chox_n);
        }
    }


    /**
     * 获取年化收益
     */
    public void getYeildInvest(){
        Api.getClient().getDictInvestValue().compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<String[]>(mContext) {
                    @Override
                    public void onSuccess(String[] investBeen) {
                        InitData(investBeen);
                    }

                    @Override
                    public void onFail(String fail) {

                    }

                });
    }


}
