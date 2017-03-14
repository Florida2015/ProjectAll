package com.hxxc.huaxing.app.ui.mine.autobid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.BidDataItemBean;
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
 * Created by Administrator on 2016/9/28.
 * 选择 期限
 *
 */

public class ChoiseTermDataActivity extends Activity implements AdapterView.OnItemClickListener {

    @BindView(R.id.img_btn_close_choise)
    ImageButton img_btn_close_choise;
    @BindView(R.id.list_bid_data)
    ListView list_bid_data;

    BidDataAdapter bidDataAdapter;

    private int index = 0;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_auto_data);
        ButterKnife.bind(this);

        mContext = this;

        index = getIntent().getIntExtra("index",0);

        img_btn_close_choise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
        list_bid_data.setDividerHeight(1);
        list_bid_data.setOnItemClickListener(this);

//        InitData();

        getAutoInvest();
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

    private List<InvestQueryBean> lists = new ArrayList<InvestQueryBean>();

    private void InitData(List<InvestQueryBean> been){
        if (been==null) return;

        for (int i=0;i< been.size();i++){
           if (been.get(i).getId() == index){
               been.get(i).setFlag(true);
           }else  been.get(i).setFlag(false);
        }
        bidDataAdapter = new BidDataAdapter(this,been,R.layout.bid_auto_data_item);
        list_bid_data.setAdapter(bidDataAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //选择
        bidDataAdapter.getItem(index).setFlag(false);
        bidDataAdapter.getItem(position).setFlag(true);
        index=position;
        bidDataAdapter.notifyDataSetChanged();
        //

        setResult(RESULT_OK,new Intent().putExtra("index",bidDataAdapter.getItem(position)));
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    /**
     * 选择期限 adapter
     */
    public class BidDataAdapter extends CommonAdapter<InvestQueryBean> {

        public BidDataAdapter(Context context, List<InvestQueryBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, InvestQueryBean item, int position) {
            super.convert(helper, item, position);
//   R.mipmap.icon_chox_y
//            R.mipmap.icon_chox_n

            helper.setText(R.id.tv_bid_item_name,item.getRemarks());
            if (item.isFlag())
            ((ImageView)helper.getView(R.id.img_bid_choise)).setImageResource(R.mipmap.icon_chox_y);
            else  ((ImageView)helper.getView(R.id.img_bid_choise)).setImageResource(R.mipmap.icon_chox_n);
        }
    }


    /**
     * 获取期限
     */
    public void getAutoInvest(){
        Api.getClient().getAutoInvestQuery().compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<InvestQueryBean>>(mContext) {
                    @Override
                    public void onSuccess(List<InvestQueryBean> investQueryBeen) {
                        InitData(investQueryBeen);
                    }

                    @Override
                    public void onFail(String fail) {

                    }

                });
    }


}
