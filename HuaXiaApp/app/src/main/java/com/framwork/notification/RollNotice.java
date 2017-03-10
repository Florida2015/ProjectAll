package com.framwork.notification;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framwork.widget.CustomViewpager;
import com.huaxia.finance.R;
import com.huaxia.finance.model.NoticeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/1/19.
 * 滚动通知
 */
public class RollNotice extends LinearLayout implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private View layout;
    private LinearLayout linear_ads;

    private CustomViewpager notice_view_page;

    private List<View> viewslist;

    private int length=0;
    private int temp=0;
    // 计时器
    android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 101) {
                if (temp>=length-1){
                    temp=0;
                    notice_view_page.setCurrentItem(temp,true);
                }else {
                    temp++;
                    notice_view_page.setCurrentItem(temp,true);
                }
            }
        };
    };

    private class CountDownTime implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            try {
                while (flagScroll) {
                    Thread.sleep(mPlayTime);
                    handler.sendEmptyMessage(101);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private boolean flagScroll=false;
    private CountDownTime countDownTime;
    private Thread thread;
    //是否播放
    public void setScrollAuto(boolean flag){
        if (flag&&length>0){
            if (handler!=null&&countDownTime!=null) handler.removeCallbacks(countDownTime);
            this.flagScroll = flag;
            if (countDownTime==null)countDownTime=new CountDownTime();
            temp=0;
            if (thread==null)thread=new Thread(countDownTime);
            if (!thread.isAlive())  thread.start();
        }
    }
    /**
     * 播放时间
     */
    int mPlayTime=2000;
    public void setPlayTime(int playTime){
        mPlayTime = playTime;
    }



    public void setNoticeClickLister(OnNoticeClikLister onAdsClikLister) {
        this.onAdsClikLister = onAdsClikLister;
    }
    /**
     *
     */
    public interface OnNoticeClikLister{
        public void OnNoticeClikLister(View view, int index,String text,String url);
    };

    private OnNoticeClikLister onAdsClikLister;

    public RollNotice(Context context) {
        super(context);
        this.mContext = context;
        Init();
    }

    public RollNotice(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        Init();
    }

    public RollNotice(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        Init();
    }

    public void Init() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.ads_activity, null);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //
        notice_view_page = (CustomViewpager) layout.findViewById(R.id.ads_view_page);
        notice_view_page.setScanScroll(true);
        //
        linear_ads = (LinearLayout) layout.findViewById(R.id.linear_ads);
        linear_ads.setVisibility(GONE);
        this.addView(layout);
    }


    // 资源文件
    public void setNoticeArrayList(final List<NoticeModel> modellist) {
        if (modellist==null||modellist.size()==0)return;
        if (viewslist == null) {
            viewslist = new ArrayList<View>();
        }
        length=modellist.size();
        viewslist.clear();
//        for (int i = 0; i < modellist.size(); i++) {
//            LayoutInflater inflater = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.notice_item, null);
//            TextView tv_notice = (TextView) view.findViewById(R.id.tv_notice);
//            if (!TextUtils.isEmpty(modellist.get(i).getDetail())){
//                tv_notice.setText(""+modellist.get(i).getDetail());
//            }else{
//                tv_notice.setText("");
//            }
//            viewslist.add(view);
//            final int index = i;
//            layout.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onAdsClikLister.OnNoticeClikLister(v,index,modellist.get(index).getDetail(),modellist.get(index).getUrl());
//                }
//            });
//            //点
////            ImageView imgDot = new ImageView(mContext);
////            imgDot.setImageResource(resid);
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////            params.setMargins(10, 0, 10, 0);
////            imgDot.setLayoutParams(params);
//
////            linear_ads.addView(imgDot);
//        }
//        setNoticeAdapter(viewslist);
////        if (viewslist.size()==1)linear_ads.removeAllViews();
////        setButtomGravity(mGravity);
//        setScrollAuto(true);
    }

    //
    public void setNoticeAdapter(List<View> list) {
        notice_view_page.setAdapter(new BaseAdapter(list));
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        temp = arg0;
    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        temp = arg0;
    }

    /**
     * 适配器
     */
    public class BaseAdapter extends PagerAdapter {

        List<View> vList = new ArrayList<View>();

        public BaseAdapter(List<View> vList) {
            if (this.vList!=null){
                this.vList.clear();
                this.vList.addAll(vList);
            }else {
                this.vList = new ArrayList<View>();
                this.vList.clear();
                this.vList.addAll(vList);
            }
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
}
