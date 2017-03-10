package com.huaxia.finance.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/1/18.
 * PagerAdapter 基类
 */
public class BasePagerAdapter extends PagerAdapter {


    List<View> vList = new ArrayList<View>();

    public BasePagerAdapter(List<View> vList) {
        this.vList = vList;
    }

    @Override
    public int getCount() {
        return vList.size(); //
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;// 固定写法
    }

    /**
     * 加载页面
     *
     * 第一个参数就是ViewPager
     *
     * 一开始会初始化两页，当第二页进来显示的时候会创建第三页。确保有两个页面。
     *
     * 第一个是当前显示的页面，第二个是即将要显示的页面
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(vList.get(position));
        return vList.get(position);
    }

    // 销毁页面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(vList.get(position));
    }


}
