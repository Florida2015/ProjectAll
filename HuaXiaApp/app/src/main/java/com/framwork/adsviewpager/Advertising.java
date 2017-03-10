package com.framwork.adsviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.framwork.widget.CustomViewpager;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.model.ActivitiesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 4张滑动广告控件
 * 
 * @author houwen.lai 2014.11.07 13:55
 */
public class Advertising extends RelativeLayout implements OnPageChangeListener {

	private Context mContext;
	private View layout;
	//
	private RelativeLayout relative_ads;
	private CustomViewpager ads_view_page;
	//
	private LinearLayout linear_ads;
	//
	private int[] picDrawdles;//本地资源图片
	private String[] picStrings;//图片链接
	//
	private List<View> viewslist;//图片
    private int resid = R.drawable.guide_dark_dot;//小点背景
    private int resid_choice = R.drawable.guide_white_dot;//小点背景

    public void setAdsClickLister(OnAdsClikLister onAdsClikLister) {
        this.onAdsClikLister = onAdsClikLister;
    }
    /**
     *
     */
    public interface OnAdsClikLister{

        public void OnAdsClikLister(View view, int index);
    };

    private OnAdsClikLister onAdsClikLister;

    private int length=0;
    private int temp=0;
    // 计时器
    android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 101) {
                if (temp>=length-1){
                    temp=0;
                    ads_view_page.setCurrentItem(temp,true);
                }else {
                    temp++;
                    ads_view_page.setCurrentItem(temp,true);
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
    int mPlayTime=5000;
    public void setPlayTime(int playTime){
        mPlayTime = playTime;
    }

    /**
     * 设置小点的位置
     * Gravity.RIGHT 默认右边
     * Gravity.CENTER 默认中间
     * @param
     */
    int mGravity = Gravity.LEFT;//默认右边
    public void setButtomGravity(int gravity){
        if(linear_ads!=null){
            linear_ads.setGravity(Gravity.CENTER_VERTICAL|gravity);
        }
    }

	public Advertising(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		Init();
	}

	public Advertising(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		Init();
	}

	public Advertising(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		Init();
	}


	public void Init() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.ads_activity, null);
		layout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		//
		relative_ads = (RelativeLayout) layout.findViewById(R.id.relative_ads);
		ads_view_page = (CustomViewpager) layout.findViewById(R.id.ads_view_page);
        ads_view_page.setScanScroll(false);
		//
		linear_ads = (LinearLayout) layout.findViewById(R.id.linear_ads);

        //android:src="@drawable/guide_dot"
        //android:src="@drawable/guide_white_dot"
		this.addView(layout);
	}

	// 图片链接
	public void setAdsPicArraysUrl(String[] picArraysUrl) {
		if (picArraysUrl == null || picArraysUrl.length==0) {
			return;
		}
		if (viewslist == null) {
            viewslist = new ArrayList<View>();
		}
        viewslist.clear();
        linear_ads.removeAllViews();
        length = picArraysUrl.length;
        for (int i = 0; i < picArraysUrl.length; i++) {

			LinearLayout layout = new LinearLayout(mContext);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));

			final ImageView img = new ImageView(mContext);
			img.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
			img.setScaleType(ScaleType.FIT_XY);

            DMApplication.getInstance().getImageLoader().DisplayImage(picArraysUrl[i], img, R.drawable.ads_loading);

			layout.addView(img);
			viewslist.add(layout);
            final int index = i;
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAdsClikLister.OnAdsClikLister(v, index);
                }
            });
            //点
            ImageView imgDot = new ImageView(mContext);
            imgDot.setImageResource(resid);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            imgDot.setLayoutParams(params);

            linear_ads.addView(imgDot);
		}
		setAdsAdapter(viewslist);
        if (viewslist.size()==1)linear_ads.removeAllViews();
        setButtomGravity(mGravity);
        setScrollAuto(true);
	}
    // 图片链接
    public void setAdsPicArraysUrl(List<ActivitiesModel> models) {
        if (models == null || models.size()==0) {
            return;
        }
        if (viewslist == null) {
            viewslist = new ArrayList<View>();
        }
        viewslist.clear();
        linear_ads.removeAllViews();
        length = models.size();
        for (int i = 0; i < models.size(); i++) {

            LinearLayout layout = new LinearLayout(mContext);
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));

            final ImageView img = new ImageView(mContext);
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
            img.setScaleType(ScaleType.FIT_XY);

            DMApplication.getInstance().getImageLoader().DisplayImage(models.get(i).getHomeUrl(), img, R.drawable.ads_loading,true);

            layout.addView(img);
            viewslist.add(layout);
            final int index = i;
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAdsClikLister.OnAdsClikLister(v, index);
                }
            });
            //点
            ImageView imgDot = new ImageView(mContext);
            imgDot.setImageResource(resid);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            imgDot.setLayoutParams(params);

            linear_ads.addView(imgDot);
        }
        setAdsAdapter(viewslist);
        if (viewslist.size()==1)linear_ads.removeAllViews();
        setButtomGravity(mGravity);
        setScrollAuto(true);
    }

	// 资源文件
	public void setAdsPicArraysDrawables(int[] picArraysDrawables) {
		if (picArraysDrawables == null ||picArraysDrawables.length==0) {
			return;
		}
        if (viewslist == null) {
            viewslist = new ArrayList<View>();
        }
        viewslist.clear();
        linear_ads.removeAllViews();
        length = picArraysDrawables.length;
		for (int i = 0; i < picArraysDrawables.length; i++) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));

			ImageView img = new ImageView(mContext);
			img.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
			img.setScaleType(ScaleType.FIT_XY);
			img.setBackgroundResource(picArraysDrawables[i]);
            layout.addView(img);
            viewslist.add(layout);
            final int index = i;
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAdsClikLister.OnAdsClikLister(v, index);
                }
            });
            //点
            ImageView imgDot = new ImageView(mContext);
            imgDot.setImageResource(resid);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            imgDot.setLayoutParams(params);

            linear_ads.addView(imgDot);
		}
        setAdsAdapter(viewslist);
        if (viewslist.size()==1)linear_ads.removeAllViews();
        setButtomGravity(mGravity);
        setScrollAuto(true);
	}

    /**
     * 设置显示
     */
    private void setDotDiaplsy(int position){
        if (viewslist!=null&&viewslist.size()>0&&linear_ads.getChildCount()>0){
            if(position<=viewslist.size()-1){
                for (int i=0;i<viewslist.size();i++){
                    if (position==i){
                        ((ImageView)linear_ads.getChildAt(i)).setImageResource(resid_choice);
                    }else {
                        ((ImageView)linear_ads.getChildAt(i)).setImageResource(resid);
                    }
                }
            }
        }
    }

	//
	public void setAdsAdapter(List<View> list) {
		ads_view_page.setAdapter(new AdsAdapter(list));
		ads_view_page.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
//        temp = arg0;
//        setDotDiaplsy(arg0);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
        temp = arg0;
        setDotDiaplsy(arg0);
//        if ( viewslist.size() > 1) { //多于1，才会循环跳转
//            if ( temp < 1) { //首位之前，跳转到末尾（N）
//                temp = viewslist.size(); //注意这里是mList，而不是mViews
//                ads_view_page.setCurrentItem(temp, false);
//            } else if ( temp > viewslist.size()) { //末位之后，跳转到首位（1）
//                ads_view_page.setCurrentItem(1, false); //false:不显示跳转过程的动画
//                temp = 1;
//            }
//        }
    }

    /**
     * 适配器
     */
    public class AdsAdapter extends PagerAdapter {

        List<View> vList = new ArrayList<View>();

        public AdsAdapter(List<View> vList) {
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
