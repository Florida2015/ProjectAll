package com.hxxc.user.app.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.adapter.MPagerAdapter;
import com.hxxc.user.app.ui.base.BaseActivity;
import com.hxxc.user.app.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {
    /**
     * 引导界面
     */
    private ViewPager viewPage;
    private LinearLayout dot_ll;
    private Button tiyan_btn;

    private List<ImageView> viewList;// view数组
    private List<View> mDotLists;
    int oldPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        dot_ll = (LinearLayout) findViewById(R.id.dot_ll);
        viewPage = (ViewPager) findViewById(R.id.viewpager);
        tiyan_btn = (Button) findViewById(R.id.tiyan_btn);
        tiyan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMain();
            }
        });
        initView();
    }

    protected void initView() {
        viewList = new ArrayList<>();
        mDotLists = new ArrayList<>();

        for(int a=0;a<5;a++){
            ImageView text = new ImageView(this);
            switch (a){
                case 0:
                    text.setImageResource(R.drawable.wecome1);
                    break;
                case 1:
                    text.setImageResource(R.drawable.wecome2);
                    break;
                case 2:
                    text.setImageResource(R.drawable.wecome3);
                    break;
                case 3:
                    text.setImageResource(R.drawable.wecome4);
                    break;
                case 4:
                    text.setImageResource(R.drawable.wecome5);
                    break;
            }
            text.setScaleType(ImageView.ScaleType.FIT_XY);
            viewList.add(text);
        }
//        ImageView text1 = new ImageView(this);
//        text1.setImageResource(R.drawable.wecome1);
//        text1.setScaleType(ImageView.ScaleType.FIT_XY);
//        viewList.add(text1);
//
//        ImageView text2 = new ImageView(this);
//        text2.setImageResource(R.drawable.wecome2);
//        text2.setScaleType(ImageView.ScaleType.FIT_XY);
//        viewList.add(text2);
//
//        ImageView text3 = new ImageView(this);
//        text3.setImageResource(R.drawable.wecome3);
//        text3.setScaleType(ImageView.ScaleType.FIT_XY);
//        viewList.add(text3);
//
//        ImageView text4 = new ImageView(this);
//        text4.setImageResource(R.drawable.wecome4);
//        text4.setScaleType(ImageView.ScaleType.FIT_XY);
//        viewList.add(text4);
//
//        ImageView text5 = new ImageView(this);
//        text5.setImageResource(R.drawable.wecome5);
//        text5.setScaleType(ImageView.ScaleType.FIT_XY);
//        viewList.add(text5);

        initDot(viewList.size());

        MPagerAdapter mPagerAdapter = new MPagerAdapter(viewList);
        viewPage.setAdapter(mPagerAdapter);
//        viewPage.setPageTransformer(true, new DepthPageTransformer());
        viewPage.addOnPageChangeListener(new OnPageChangeListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onPageSelected(int position) {
                if (position == viewList.size() - 1) {
                    tiyan_btn.setVisibility(View.VISIBLE);
                } else {
                    tiyan_btn.setVisibility(View.GONE);
                }
                // 判断点的集合是否有数据
                if (null != mDotLists && mDotLists.size() > 1) {
                    ((ImageView) (mDotLists.get(position))).setImageResource(R.drawable.point_white);
                    ((ImageView) (mDotLists.get(oldPosition))).setImageResource(R.drawable.point_grey);
                } else if (null != mDotLists && mDotLists.size() == 1) {
                    ((ImageView) (mDotLists.get(position))).setImageResource(R.drawable.point_white);
                }
                oldPosition = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * 初始化点
     *
     * @param size 几个点
     */
    @SuppressLint("NewApi")
    private void initDot(int size) {
        // 清除里面的点
        dot_ll.removeAllViews();
        mDotLists.clear();
        // 迭代所有的点
        for (int i = 0; i < size; i++) {
            ImageView view = new ImageView(this);

            // 设置点的宽和高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DisplayUtil.dip2px(this, 6), DisplayUtil.dip2px(this,
                    6));
            params.leftMargin = DisplayUtil.dip2px(this, 5);
            // 设置距离左边5个等价的像素
            if (i == 0) {
                view.setImageResource(R.drawable.point_white);
            } else {
                view.setImageResource(R.drawable.point_grey);
            }

            // 设置点的宽和高
            view.setLayoutParams(params);
            // 把点添加到布局里面
            dot_ll.addView(view);
            // 封装所有的点
            mDotLists.add(view);
        }
    }


    private void goMain() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        for (int a = 0; a < viewList.size(); a++) {
            ImageView imageView = viewList.get(a);
            if (null != imageView) {
                imageView.setImageDrawable(null);
            }
        }
        viewList.clear();

        for (int a = 0; a < mDotLists.size(); a++) {
            ImageView imageView = (ImageView) mDotLists.get(a);
            if (null != imageView) {
                imageView.setImageDrawable(null);
            }
        }
        mDotLists.clear();

        viewPage.removeAllViews();
        super.onDestroy();
    }
}
