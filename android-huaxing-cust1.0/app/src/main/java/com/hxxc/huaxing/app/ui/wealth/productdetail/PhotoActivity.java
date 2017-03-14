package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxxc.huaxing.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/9/29.
 */

public class PhotoActivity extends Activity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    private ArrayList<String> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        lists = intent.getStringArrayListExtra("list");
        viewpager.setAdapter(new MyViewPagerAdapter());
        viewpager.setCurrentItem(position, false);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (lists.size() == 1) {
                    iv_left.setVisibility(View.INVISIBLE);
                    iv_right.setVisibility(View.INVISIBLE);
                } else {
                    if (position == 0) {
                        iv_left.setVisibility(View.INVISIBLE);
                        iv_right.setVisibility(View.VISIBLE);
                    } else if (position == lists.size() - 1) {
                        iv_left.setVisibility(View.VISIBLE);
                        iv_right.setVisibility(View.INVISIBLE);
                    } else {
                        iv_left.setVisibility(View.VISIBLE);
                        iv_right.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(PhotoActivity.this);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(params);
            Picasso.with(PhotoActivity.this).load(lists.get(position)).fit().error(R.mipmap.error).placeholder(R.mipmap.error).into(imageView);
            container.addView(imageView);
            return imageView;
        }
    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onClick(View view) {
        int currentItem = viewpager.getCurrentItem();
        switch (view.getId()) {
            case R.id.iv_left:
                if (currentItem > 0) {
                    viewpager.setCurrentItem(currentItem - 1, true);
                }
                break;
            case R.id.iv_right:
                if (currentItem < lists.size()) {
                    viewpager.setCurrentItem(currentItem + 1, true);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
