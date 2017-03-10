package com.hxxc.user.app.ui.vh;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.data.bean.TradeRecordBean;
import com.hxxc.user.app.ui.mine.tradelist.TradeDetailActivity;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

import java.util.Map;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/11/8.
 * 交易记录
 */
//
public class TradeVH extends BaseViewHolder<TradeRecordBean> {

    RelativeLayout relative_integral_month;
    TextView tv_integral_month;
    TextView tv_integral_useing;
    TextView tv_integral_getting;

    RelativeLayout relative_integral;
    TextView tv_integral_title;
    TextView tv_integral_date;
    TextView tv_integral_count;

    public TradeVH(View v) {
        super(v);

    }

    @Override
    public int getType() {
        return R.layout.integral_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, TradeRecordBean obj) {

//        if (position==0){
//            relative_integral_month.setVisibility(View.VISIBLE);
//            tv_integral_month.setText("交易对象");
//            tv_integral_useing.setText("回款:"+MoneyUtil.addComma(obj.getBackMoney(),2,ROUND_FLOOR));
//            tv_integral_getting.setText("出借:"+MoneyUtil.addComma(obj.getPayMoney(),2,ROUND_FLOOR));
//        }else relative_integral_month.setVisibility(View.GONE);
//
//        if (position%2==0){
//            relative_integral.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//        }else {
//            relative_integral.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));
//        }
//
//        tv_integral_title.setText(obj.getTypeText());

//        tv_integral_date.setText(obj.getCreateTimeText());
//
//        if (obj.getTradeType()==2){//支出
//            tv_integral_count.setText("-"+MoneyUtil.addComma(obj.getMoney(),2,ROUND_FLOOR));
//        }else {//1收入
//            tv_integral_count.setText("+"+MoneyUtil.addComma(obj.getMoney(),2,ROUND_FLOOR));
//        }
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, TradeDetailActivity.class).putExtra("id",""+obj.getId()));
//            }
//        });
    }

    @Override
    public void onBindViewHolder(View view, int position, TradeRecordBean obj, int positionF, TradeRecordBean objF,Map<String, String> param) {
        super.onBindViewHolder(view, position, obj, positionF, objF,param);

        if (param!=null&&!TextUtils.isEmpty(param.get("trade"))){
            Log.d("aaa",""+param.get("trade"));
            if (param.get("trade").equals("0")){
                tv_integral_useing.setVisibility(View.VISIBLE);
                tv_integral_getting.setVisibility(View.VISIBLE);
            }else if(param.get("trade").equals("1")){
                tv_integral_useing.setVisibility(View.VISIBLE);
                tv_integral_getting.setVisibility(View.GONE);
            }else if(param.get("trade").equals("2")){
                tv_integral_useing.setVisibility(View.GONE);
                tv_integral_getting.setVisibility(View.VISIBLE);
            }
        }

        if (position==0){
            relative_integral_month.setVisibility(View.VISIBLE);
            tv_integral_month.setText(obj.getMonth()+"月累计");
            tv_integral_useing.setText("回款:"+MoneyUtil.addComma(obj.getBackMoney(),2,ROUND_FLOOR));
            tv_integral_getting.setText("出借:"+MoneyUtil.addComma(obj.getPayMoney(),2,ROUND_FLOOR));
        }else if(position>0&&position==positionF+1){
            if (obj.getMonth()!=objF.getMonth()){
                relative_integral_month.setVisibility(View.VISIBLE);
                tv_integral_month.setText(obj.getMonth()+"月累计");
                tv_integral_useing.setText("回款:"+MoneyUtil.addComma(obj.getBackMoney(),2,ROUND_FLOOR));
                tv_integral_getting.setText("出借:"+MoneyUtil.addComma(obj.getPayMoney(),2,ROUND_FLOOR));
            }else  relative_integral_month.setVisibility(View.GONE);
        }else relative_integral_month.setVisibility(View.GONE);


        if (position%2==0){
            relative_integral.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            relative_integral.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));
        }

        tv_integral_title.setText(obj.getTypeText());

        tv_integral_date.setText(obj.getCreateTimeText());

        if (obj.getTradeType()==2){//支出
            tv_integral_count.setText("-"+MoneyUtil.addComma(obj.getMoney(),2,ROUND_FLOOR));
        }else if(obj.getTradeType()==1) {//1收入
            tv_integral_count.setText("+"+MoneyUtil.addComma(obj.getMoney(),2,ROUND_FLOOR));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, TradeDetailActivity.class).putExtra("id",""+obj.getId()));
            }
        });
    }
}
