package com.hxxc.user.app.ui.pager;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;

import com.hxxc.user.app.Event.ExitLoginEvent;
import com.hxxc.user.app.Event.LoginEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenqun on 2016/8/23.
 */
public class PagerFactory {
    public static final int TAB_HOME = 0;
    public static final int TAB_PRODUCT = 1;
    public static final int TAB_DEPARTMENT = 2;
    public static final int TAB_MINE = 3;

    @SuppressLint("UseSparseArrays")
    private static Map<Integer, BasePager> mPagerMaps = new HashMap<Integer, BasePager>();

    public static BasePager getPager(AppCompatActivity context, int index) {
        BasePager pager = mPagerMaps.get(index);
        if (null == pager) {
            switch (index) {
                case TAB_HOME:
                    pager = new HomePager(context);
                    break;
                case TAB_PRODUCT:
                    pager = new ProductNewPager(context);
                    break;
                case TAB_DEPARTMENT:
                    pager = new DiscoveryPager(context);
                    break;
                case TAB_MINE:
//                    pager = new MinePager(activity);
                    pager = new MineNewPager(context, context.getSupportFragmentManager());
                    break;
            }
            mPagerMaps.put(index, pager);
        }
        return pager;
    }

    public static void onDestory() {
        Set<Map.Entry<Integer, BasePager>> entries = mPagerMaps.entrySet();
        for (Map.Entry<Integer, BasePager> map : entries) {
            map.getValue().onDestroy();
        }
        mPagerMaps.clear();
    }

    public static void onLoginEvent(LoginEvent event) {
        Set<Map.Entry<Integer, BasePager>> entries = mPagerMaps.entrySet();
        for (Map.Entry<Integer, BasePager> map : entries) {
            map.getValue().onLoginEvent(event);
        }
    }

    public static void onExitLoginEvent(ExitLoginEvent event) {
        Set<Map.Entry<Integer, BasePager>> entries = mPagerMaps.entrySet();
        for (Map.Entry<Integer, BasePager> map : entries) {
            map.getValue().onExitLoginEvent(event);
        }
    }

    public static void reflushPage3() {
        BasePager pager = mPagerMaps.get(3);
        if (null != pager && pager.isLoading) {
            ((MineNewPager) pager).onMineEvent();
        }
    }

    public static void onUnreadMessageEvent(AppCompatActivity context,int count) {
//        Set<Map.Entry<Integer, BasePager>> entries = mPagerMaps.entrySet();
//        for (Map.Entry<Integer, BasePager> map : entries) {
//            map.getValue().onUnreadMessageEvent(count);
//        }
        getPager(context,0).onUnreadMessageEvent(count);
        getPager(context,3).onUnreadMessageEvent(count);
    }
}
