package com.hxxc.user.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.utils.ImageUtils;

import java.util.List;

/**
 *
 * 自己可以跳动的viewpager
 *
 */
public class RollViewPager extends ViewPager {

	private SwipeRefreshLayout mSwipeRefreshLayout ;

	public RollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private Context mContext;
	private List<View> mDotLists = null;

	public RollViewPager(Context context, List<View> dotLists, SwipeRefreshLayout swipeRefreshLayout) {
		super(context);
		this.mSwipeRefreshLayout = swipeRefreshLayout;
		this.mContext = context;
		this.mDotLists = dotLists;

		mCurrentPosition = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2)%mDotLists.size();
		oldPosition = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2)%mDotLists.size()-1;

		// 每隔5秒钟跳动一次的任务
		task = new Task();
		// 设置触摸事件
		mTouchListener = new MyOnTouchListener();
		RollViewPager.this.setOnTouchListener(mTouchListener);
		// 第二种写法
//		RollViewPager.this.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN
//						|| event.getAction() == MotionEvent.ACTION_CANCEL) {
//					// 暂停
//					handler.removeCallbacksAndMessages(null);
//				} else if (event.getAction() == MotionEvent.ACTION_UP) {
//					// 跳动
//					// mTask.start();
//					start();
//				}
//				return false;
//			}
//		});

	}

	private long downCurrentThreadTimeMillis;
	private OnViewPagerClickListener mOnViewPagerClickListener;


	private class MyOnTouchListener implements OnTouchListener {

		private int oldX;
		private int oldY;

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {

			case MotionEvent.ACTION_CANCEL:
				start();
				break;

			case MotionEvent.ACTION_DOWN:
				oldX = (int) event.getX();
				oldY = (int) event.getY();

				// 区分到底是长按还是单机事件
				// 获取到当前的时间
				downCurrentThreadTimeMillis = System.currentTimeMillis();
				// 停止跳动
				handler.removeCallbacksAndMessages(null);

				// 判断当前时间如果小于500毫秒。那么就是单机事件

				break;
			case MotionEvent.ACTION_MOVE:
				handler.removeCallbacks(task);
				break;
			case MotionEvent.ACTION_UP:
				int newX = (int) event.getX();
				int newY = (int) event.getY();
				if (System.currentTimeMillis() - downCurrentThreadTimeMillis < 200 && Math.abs(newX-oldX)<40 && Math.abs(newY-oldY)<40) {
					//TODO  Toast.makeText(mContext, "点击事件", 0).show();

					if (null != mOnViewPagerClickListener) {
						mOnViewPagerClickListener.onViewPagerClick(RollViewPager.this.getCurrentItem()%mImageLists.size());
					}
				}
				start();
				break;
			}

			return false;
		}

	}

	public void setOnViewPagerClickListener(OnViewPagerClickListener listener){
		this.mOnViewPagerClickListener = listener;
	}

	public interface OnViewPagerClickListener{
		void onViewPagerClick(int position);
	}

	public TextView mTopNewsTitle = null;
	private List<String> mTitleLists = null;

	/**
	 * 设置文本标题(初始化)
	 *
	 * @param top_news_title
	 *            文本控件
	 * @param titleLists
	 *            文本控件上面的数据
	 */
	public void setTextTitle(TextView top_news_title, List<String> titleLists) {
		if (null != titleLists && titleLists.size() > 0
				&& null != top_news_title) {
			this.mTopNewsTitle = top_news_title;
			this.mTitleLists = titleLists;
			top_news_title.setText(mTitleLists.get(0));
		}

	}

	private List<IndexAds> mImageLists = null;

	/**
	 * 设置图片资源
	 *
	 * @param imageLists
	 */
	public void setImageRes(List<IndexAds> imageLists) {
		if (null != imageLists && imageLists.size() > 0) {
			this.mImageLists = imageLists;
		}

	}

	private boolean isHasAdapter = false;

	/**
	 * 轮播图跳转
	 */
	public void start() {
		if (!isHasAdapter) {
			isHasAdapter = true;
			MyOnPageChangeListener listener = new MyOnPageChangeListener();
			RollViewPager.this.setOnPageChangeListener(listener);
			RollViewPagerAdapter adapter = new RollViewPagerAdapter();
			RollViewPager.this.setAdapter(adapter);
			RollViewPager.this.setCurrentItem(mCurrentPosition);
		}

		// 过5秒钟发送一次延迟消息
		handler.postDelayed(task, 4000);
	}

	int oldPosition ;

	private class MyOnPageChangeListener implements OnPageChangeListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onPageSelected(int position) {
			mCurrentPosition = position;
			// 判断当前的文本控件不等于null。集合数据也不等于null
			if (null != mTitleLists && mTitleLists.size() > 0
					&& null != mTopNewsTitle) {
				mTopNewsTitle.setText(mTitleLists.get(position%mTitleLists.size()));
			}

			// 判断点的集合是否有数据
			if (null != mDotLists && mDotLists.size() == 1) {
				((ImageView)(mDotLists.get(position%mDotLists.size()))).setImageResource(R.drawable.point_white);
			}else if (null != mDotLists && mDotLists.size() > 0) {
				((ImageView)(mDotLists.get(position%mDotLists.size()))).setImageResource(R.drawable.point_white);
				((ImageView)(mDotLists.get(oldPosition%mDotLists.size()))).setImageResource(R.drawable.point_grey);
			}
			oldPosition = position;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

	}

	private class Task implements Runnable {

		@Override
		public void run() {
			//设置资源数量大于1时才跳动
			if (null != mImageLists && mImageLists.size()>1) {
//				mCurrentPosition = (mCurrentPosition + 1) % mImageLists.size();
				mCurrentPosition = (mCurrentPosition + 1);
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	// 判断当前的手指是否移动
	private boolean isMove = false;

	/**
	 * 事件分发
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dwonX = (int) ev.getX();
			downY = (int) ev.getY();
			isMove = false;
			break;
		case MotionEvent.ACTION_MOVE:
			int currentX = (int) ev.getX();
			int currentY = (int) ev.getY();
			// 判断到底是左右滑动还是上下滑动
			// 如果是左右滑动。那么走viewpager
			if (Math.abs(currentX - dwonX) > Math.abs(currentY - downY)) {
				mSwipeRefreshLayout.setEnabled(false);
				isMove = false;
			} else {
				// 如果是上下滑动。那么走listview
				isMove = true;
			}

			break;
		case MotionEvent.ACTION_UP:
			mSwipeRefreshLayout.setEnabled(true);

			break;
		}
		// 请求当前的父类不要拦截我
		getParent().requestDisallowInterceptTouchEvent(!isMove);
		return super.dispatchTouchEvent(ev);
	}

	private int mCurrentPosition ;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 设置当前的位置
			RollViewPager.this.setCurrentItem(mCurrentPosition, true);
			start();
		}
	};
	private Task task;
	private MyOnTouchListener mTouchListener;
	private int dwonX;
	private int downY;

	private class RollViewPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = View.inflate(mContext, R.layout.viewpager_item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
//			image.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					CommonUtil.showSafeToast((Activity)mContext, mImageLists.get(position).toString());
//					//TODO 设置点击事件
//					IndexAds ads = mImageLists.get(position);
//				}
//			});
			// 展示当前的图片
			ImageUtils.getInstance().displayImage(mImageLists.get(position%mImageLists.size()).getPictureSourceUrl(),image, ImageUtils.mOptionsForIndex);
			// 添加当前的图片
			container.addView(view);
			// 给当前的界面设置触摸事件
			// view.setOnTouchListener(mTouchListener);
			return view;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	/**
	 * 停止消息
	 */
	public void stop() {
		// TODO Auto-generated method stub
		handler.removeCallbacksAndMessages(null);
		if (null != mImageLists && mImageLists.size() > 0) {
//			mCurrentPosition = 0;
			mCurrentPosition = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2)%mImageLists.size();
			setCurrentItem(mCurrentPosition, false);
		}
	}
	public void removeAllCallback(){
		handler.removeCallbacks(task);
//		handler.removeCallbacksAndMessages(null);
	}

}
