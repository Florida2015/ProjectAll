package com.hxxc.user.app.ui.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Department;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.ImageUtils;
import com.hxxc.user.app.widget.RecyclerImageView;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2016/6/28.
 */
public class PhotoActivity extends ToolbarActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("服务网点");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }
    private ArrayList<String> lists;
    private Department department;
    private void initView() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        lists = intent.getStringArrayListExtra("list");
        viewpager.setAdapter(new MyViewPagerAdapter());
        viewpager.setCurrentItem(position, false);
    }
    public class MyViewPagerAdapter extends PagerAdapter {
        private ImageLoadingListener animateFirstListener = ImageUtils.AnimateFirstDisplayListener.getInstance();

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
            RecyclerImageView imageView = new RecyclerImageView(PhotoActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(params);
            ImageUtils.getInstance().displayImage(lists.get(position),imageView, ImageUtils.mOptionsNoRound,animateFirstListener);
            container.addView(imageView);
            return imageView;
        }
    }

}
