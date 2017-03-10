package com.hxxc.user.app.ui.vh;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.AdviserBean;
import com.hxxc.user.app.ui.financial.BindingFinancialActivity;
import com.hxxc.user.app.ui.financial.DialogBindingFinancial;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.widget.CircleImageView;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/10/8.
 * 绑定顾问
 */

public class BindFinancialVH extends BaseViewHolder<AdviserBean> {

    LinearLayout linear_binding_financial;

    TextView tv_recommend;
    View line_recommend;

    CircleImageView img_icon;

    TextView name_text;
    TextView service_count_text;
    TextView finance_no_text;
    TextView amout_text;

    ImageView is_check_img;
    int index = 0;

    public BindFinancialVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.adapter_finance_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, AdviserBean obj) {
        LogUtil.d("bind financial=" + obj.toString());

        if (position == 0) {
            tv_recommend.setVisibility(View.VISIBLE);
            line_recommend.setVisibility(View.VISIBLE);
        } else {
            tv_recommend.setVisibility(View.GONE);
            line_recommend.setVisibility(View.GONE);
        }
        is_check_img.setVisibility(View.GONE);

        if (position % 2 == 0)
            linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        else
            linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));

        Picasso.with(mContext)
                .load(obj.getRealIcon())
                .placeholder(R.drawable.default_financier_pic)
                .error(R.drawable.default_financier_pic)
                .into(img_icon);

        name_text.setText(obj.getFname());
        finance_no_text.setText(obj.getFinancialno());
        service_count_text.setText("" + obj.getServicecount()+"位");
//        BigDecimal dd = BigDecimal.valueOf(obj.getInvestmentamout().doubleValue()/10000);
//        amout_text.setText(MoneyUtil.addComma(dd,2,ROUND_DOWN)+"万");

        amout_text.setText(CommonUtil.moneyType(obj.getInvestmentamout().doubleValue() / 10000f) + "万");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //是否选择该理财师做顾问
                try {
                    BindingFinancialActivity.getmXRecyclerView().getAdapter().notifyDataSetChanged();
                } catch (Exception e) {

                }

                linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.grey_e2e2));

                String mobile = ((Activity) mContext).getIntent().getStringExtra("mobile");
                String code = ((Activity) mContext).getIntent().getStringExtra("code");
                String pass = ((Activity) mContext).getIntent().getStringExtra("pass");
                String pass_again = ((Activity) mContext).getIntent().getStringExtra("pass_again");
                String pCode = ((Activity) mContext).getIntent().getStringExtra("pCode");
                int from = ((Activity) mContext).getIntent().getIntExtra("from",-1);
                LogUtil.d("顾问 mobile=" + mobile + "_code=" + code + "_pass=" + pass + "_pass_again=" + pass_again + "_pCode=" + pCode);

                Intent intent = new Intent(mContext, DialogBindingFinancial.class);
                intent.putExtra("mobile", mobile);
                intent.putExtra("code", code);
                intent.putExtra("pass", pass);
                intent.putExtra("pass_again", pass_again);
                intent.putExtra("pCode", pCode);
                intent.putExtra("fid", "" + obj.getFid());
                intent.putExtra("fname", "" + obj.getFname());
                intent.putExtra("from", from);
                mContext.startActivity(intent);

//                mContext.startActivity(new Intent(mContext, DialogBindingFinancial.class));

//                EventBus.getDefault().post(new BindingFinancialEvent(obj.getFid()));
            }
        });
    }
}
