package com.hxxc.user.app.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hxxc.user.app.contract.ProductDetailVertical2V;
import com.hxxc.user.app.ui.base.BaseFragment2;
import com.hxxc.user.app.ui.product.AFragment;
import com.hxxc.user.app.ui.product.BFragment;
import com.hxxc.user.app.ui.product.CFragment2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenqun on 2016/10/25.
 */

public class ProductDetailVertical2Adapter  extends FragmentPagerAdapter {
    private  ProductDetailVertical2V mView;
    private String[] mTitles = {"产品详情", "出借记录", "常见问题"};
    private static final int PRODUCT_PAGER_COUNT = 3;

    private Map<Integer , BaseFragment2> mFragments = new HashMap<>();

    public ProductDetailVertical2Adapter(FragmentManager fm, ProductDetailVertical2V view) {
        super(fm);
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
                    fragment = new CFragment2();
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
                return mTitles[0];
            case 1:
                return mTitles[1];
            case 2:
                return mTitles[2];
            default:
                return mTitles[0];
        }
    }

    @Override
    public int getCount() {
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

    public void clear() {
        mView = null;
    }
}
