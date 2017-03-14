package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hxxc.huaxing.app.ui.wealth.BaseFragment2;
import com.hxxc.huaxing.app.ui.wealth.Fragment1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenqun on 2016/9/23.
 */

public class ProductDetailAdapter extends FragmentPagerAdapter {
    private static final int PRODUCT_PAGER_COUNT = 3;
    private static final int PRODUCT_PAGER_COUNT_ZHAIQUAN = 2;
    private int type;
    private  ProductDetailVertical2V mView;

    private Map<Integer , BaseFragment2> mFragments = new HashMap<>();

    public ProductDetailAdapter(FragmentManager fm, int type,ProductDetailVertical2V view) {
        super(fm);
        this.type = type;
        this.mView = view;
    }

    @Override
    public BaseFragment2 getItem(int position) {
        BaseFragment2 fragment = mFragments.get(position);
        if(null == fragment){
            switch (position){
                case 0:
                    fragment = new AFragment();
                    break;
                case 1:
                    fragment = new BFragment();
                    break;
                case 2:
                    fragment = new CFragment();
                    break;
            }
            assert fragment != null;
            fragment.setV(mView);
            mFragments.put(position,fragment);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "标的信息";
            case 1:
                return "相关图片";
            case 2:
                return "投标记录";
            default:
                return "";
        }
    }

    @Override
    public int getCount() {
        if(type == Fragment1.Type_Zhaiquan){
            return PRODUCT_PAGER_COUNT_ZHAIQUAN;
        }
        return PRODUCT_PAGER_COUNT;
    }

    public void onPagerSelected(int index){
        BaseFragment2 item = getItem(index);
        if(!item.isLoading){
            item.isLoading = true;
            item.initDatas();
        }
    }

    public void reset() {
        Set<Map.Entry<Integer, BaseFragment2>> entries = mFragments.entrySet();
        for(Map.Entry<Integer, BaseFragment2> map : entries){
            map.getValue().isLoading = false;
        }
    }
}
