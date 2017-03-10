package com.hxxc.user.app.ui.pager.mine;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;

/**
 * Created by Administrator on 2016/10/25.
 * 我的 banner 滑动
 */

public class MineViewPager extends LinearLayout implements ViewPager.OnPageChangeListener{

    private Context mContext;
    private FragmentManager fragmentManager;

    public MineViewPager(Context context,FragmentManager fragmentManager) {
        super(context);
        this.mContext = context;
        this.fragmentManager = fragmentManager;
        init();
    }

    public MineViewPager(Context context, AttributeSet attrs,FragmentManager fragmentManager) {
        super(context, attrs);
        this.mContext = context;
        this.fragmentManager = fragmentManager;
        init();
    }

    public MineViewPager(Context context, AttributeSet attrs, int defStyleAttr,FragmentManager fragmentManager) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.fragmentManager = fragmentManager;
        init();
    }



    private ViewPager viewpage_mine_banner;

    private ImageView img_tab_1;
    private ImageView img_tab_2;

    private LinearLayout linear_capitail;

    private FragmentMineBanner fragmentMineBanner1;
    private FragmentMineBanner fragmentMineBanner2;


    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.home_mine_banner, null);
        View layout = inflater.inflate(R.layout.home_mine_top, null);
        layout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));

        linear_capitail = (LinearLayout) layout.findViewById(R.id.linear_capitail);

        tv_income_money = (TextView) layout.findViewById(R.id.tv_income_money);
        tv_total_assets = (TextView) layout.findViewById(R.id.tv_total_assets);
        tv_yestoday_income = (TextView) layout.findViewById(R.id.tv_yestoday_income);
//        tv_extra_assets= (TextView) layout.findViewById(R.id.tv_extra_assets);

        viewpage_mine_banner= (ViewPager) layout.findViewById(R.id.viewpage_mine_banner);
        img_tab_1 = (ImageView) layout.findViewById(R.id.img_tab_1);
        img_tab_2 = (ImageView) layout.findViewById(R.id.img_tab_2);

        viewpage_mine_banner.setOnPageChangeListener(this);

        this.addView(layout);

        fragmentMineBanner1 = FragmentMineBanner.newInstance(0);
        fragmentMineBanner2= FragmentMineBanner.newInstance(1);

        viewpage_mine_banner.setAdapter(new FragmentPagerAdapter(this.fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if (position==0)return fragmentMineBanner1;
                else  return fragmentMineBanner2;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        setCurrunt(0);
    }




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

    public void setCurrunt(int index){
        if (index==0){
            img_tab_1.setImageResource(R.mipmap.icon_center_rolling_s);
            img_tab_2.setImageResource(R.mipmap.icon_center_rolling_n);
        }else {
            img_tab_1.setImageResource(R.mipmap.icon_center_rolling_n);
            img_tab_2.setImageResource(R.mipmap.icon_center_rolling_s);
        }
    }

    TextView tv_income_money;//累计收益
    TextView tv_total_assets;//总资产
    TextView tv_yestoday_income;//昨日收益
//    TextView tv_extra_assets;//余额




    /**
     * 设置金额
     */
    public void setTopData(String income_money,String total_assets,String remainder_money,String extra_assets){
        tv_income_money.setText(""+income_money);
        tv_total_assets.setText(""+total_assets);
        tv_yestoday_income.setText(""+remainder_money);
//        tv_extra_assets.setText(""+extra_assets);
    }

}
