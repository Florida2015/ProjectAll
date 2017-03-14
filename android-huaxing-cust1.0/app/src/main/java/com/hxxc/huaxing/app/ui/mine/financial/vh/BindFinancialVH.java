package com.hxxc.huaxing.app.ui.mine.financial.vh;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.AdviserBean;
import com.hxxc.huaxing.app.ui.mine.financial.BindingFinancialActivity;
import com.hxxc.huaxing.app.ui.mine.financial.DialogBindingFinancial;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_DOWN;

/**
 * Created by Administrator on 2016/10/8.
 * 绑定顾问
 */

public class BindFinancialVH extends BaseViewHolder<AdviserBean> {

    LinearLayout linear_binding_financial;

    TextView tv_recommend;
    View line_recommend;

    ImageView img_icon;

    TextView name_text;
    TextView service_count_text;
    TextView finance_no_text;
    TextView amout_text;

    ImageView is_check_img;
    int index=0;

    public BindFinancialVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.adapter_finance_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, AdviserBean obj) {
        if (position==0){
            tv_recommend.setVisibility(View.VISIBLE);
            line_recommend.setVisibility(View.VISIBLE);
        }else {
            tv_recommend.setVisibility(View.GONE);
            line_recommend.setVisibility(View.GONE);
        }
        is_check_img.setVisibility(View.GONE);

        if (position%2==0)  linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        else linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));

        Picasso.with(mContext)
                .load(obj.getIcon())
                .placeholder(R.mipmap.icon_my_head)
                .error(R.mipmap.icon_my_head)
                .into( img_icon);

        name_text.setText(obj.getFname());
        finance_no_text.setText(obj.getFinancialno());
        service_count_text.setText(""+obj.getServicecount());
        amout_text.setText(MoneyUtil.addComma((BigDecimal.valueOf(obj.getInvestmentamout().doubleValue()/10000)),0,ROUND_DOWN,MoneyUtil.point)+"万");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //是否选择该理财师做顾问
                try{
                    BindingFinancialActivity.getmXRecyclerView().getAdapter().notifyDataSetChanged();
                }catch (Exception e){

                }

                linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.grey_e2e2));

                String mobile = ((Activity)mContext).getIntent().getStringExtra("mobile");
                String code = ((Activity)mContext).getIntent().getStringExtra("code");
                String pass = ((Activity)mContext).getIntent().getStringExtra("pass");
                String pass_again = ((Activity)mContext).getIntent().getStringExtra("pass_again");
                String pCode = ((Activity)mContext).getIntent().getStringExtra("pCode");
                LogUtil.d("顾问 name="+obj.getFname()+"__mobile="+mobile+"_code="+code+"_pass="+pass+"_pass_again="+pass_again+"_pCode="+pCode);

                Intent intent = new Intent((Activity)mContext, DialogBindingFinancial.class);
                intent.putExtra("name",obj.getFname());
                intent.putExtra("mobile",mobile);
                intent.putExtra("code",code);
                intent.putExtra("pass",pass);
                intent.putExtra("pass_again",pass_again);
                intent.putExtra("pCode",pCode);
                intent.putExtra("fid",""+obj.getFid());
                mContext.startActivity(intent);

            }
        });
    }


}
