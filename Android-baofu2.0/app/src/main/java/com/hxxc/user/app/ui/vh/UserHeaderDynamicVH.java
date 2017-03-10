package com.hxxc.user.app.ui.vh;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AccountInfo;
import com.hxxc.user.app.ui.mine.assetsrecord.AssetsRecordActivity;
import com.hxxc.user.app.ui.mine.assetsrecord.AssetsRecordNewActivity;
import com.hxxc.user.app.ui.pager.mine.FragmentMineBanner;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by Administrator on 2016/11/18.
 */

public class UserHeaderDynamicVH extends BaseViewHolder<AccountInfo> {

    TextView tv_income_money;//累计收益
    TextView tv_total_assets;//总资产
    TextView tv_yestoday_income;//昨日收益

    ViewPager viewpage_mine_banner;

    LinearLayout linear_capitail;

    ImageButton imgbtn_doubt;

    ImageView img_tab_1;
    ImageView img_tab_2;

    FragmentMineBanner fragmentMineBanner1;
    FragmentMineBanner fragmentMineBanner2;

    LinearLayout ll_emptyview_mine;

    public UserHeaderDynamicVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.home_mine_top;
    }

    @Override
    public void onBindViewHolder(View view, int position, AccountInfo obj) {

        if (obj != null && obj.getCumulativeProfit() != null)
            tv_income_money.setText(MoneyUtil.addComma(obj.getCumulativeProfit(), 2, ROUND_FLOOR));
        if (obj != null && obj.getTatalAmount() != null)
            tv_total_assets.setText(MoneyUtil.addComma(obj.getTatalAmount(), 2, ROUND_FLOOR));
        if (obj != null && obj.getYesterdayProfit() != null)
            tv_yestoday_income.setText(MoneyUtil.addComma(obj.getYesterdayProfit(), 2, ROUND_FLOOR));

        viewpage_mine_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrunt(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imgbtn_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,R.string.text_mine_tip,Toast.LENGTH_LONG).show();
                ToastUtil.show(R.string.text_mine_tip,8000);
            }
        });

        fragmentMineBanner1 = FragmentMineBanner.newInstance(0);
        fragmentMineBanner2 = FragmentMineBanner.newInstance(1);

        viewpage_mine_banner.setAdapter(new FragmentPagerAdapter(((AppCompatActivity) mContext).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) return fragmentMineBanner1;
                else return fragmentMineBanner2;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        setCurrunt(0);

        linear_capitail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//资产分析页
//                mContext.startActivity(new Intent(mContext, AssetsRecordActivity.class).putExtra("info", obj));
                //
                mContext.startActivity(new Intent(mContext, AssetsRecordNewActivity.class).putExtra("info", obj));
            }
        });

        if (obj==null){
            ll_emptyview_mine.setVisibility(View.VISIBLE);
            return;
        }else ll_emptyview_mine.setVisibility(View.GONE);

    }

    public void setCurrunt(int index) {
        if (index == 0) {
            img_tab_1.setImageResource(R.mipmap.icon_center_rolling_s);
            img_tab_2.setImageResource(R.mipmap.icon_center_rolling_n);
        } else {
            img_tab_1.setImageResource(R.mipmap.icon_center_rolling_n);
            img_tab_2.setImageResource(R.mipmap.icon_center_rolling_s);
        }
    }

}
