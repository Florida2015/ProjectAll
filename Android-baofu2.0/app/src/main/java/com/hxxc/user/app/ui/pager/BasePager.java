package com.hxxc.user.app.ui.pager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.ExitLoginEvent;
import com.hxxc.user.app.Event.LoginEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.widget.MultiSwipeRefreshLayout;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePager {
	public final ApiManager mApiManager = ApiManager.getInstance();

	public Context mContext;
//	private Activity mActivity ;
	private View rootView;
	@Nullable
	public MultiSwipeRefreshLayout mSwipeRefreshLayout;
	// 是否加载成功
	public boolean isLoading = false;
	private CompositeSubscription mCompositeSubscription;

	public BasePager(Context context) {
		this.mContext = context;
		rootView = initView();
		mSwipeRefreshLayout = (MultiSwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
		trySetupSwipeRefresh();
	}

//
//	public BasePager(Activity context) {
//		this.mActivity = context;
//		this.mContext = context;
//		rootView = initView();
//		mSwipeRefreshLayout = (MultiSwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
//		trySetupSwipeRefresh();
//	}

//	public Activity getActivity(){
//		return mActivity;
//	}

	void trySetupSwipeRefresh() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
					R.color.colorPrimaryDark, R.color.colorPrimary);
			mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override public void onRefresh() {
					BasePager.this.onRefresh();
				}
			});
//			mSwipeRefreshLayout.setAnimateToRefreshDuration(1000);
//			mSwipeRefreshLayout.setAnimateToStartDuration(1000);
//			mSwipeRefreshLayout.setDragDistanceConverter(new IDragDistanceConverter() {
//				@Override
//				public float convert(float scrollDistance, float refreshDistance) {
//					return scrollDistance * 0.5f;
//				}
//			});
//			mSwipeRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
//				@Override
//				public void onRefresh() {
//					BasePager.this.onRefresh();
//				}
//			});
		}
	}
	//子类需要重写
	protected void onRefresh() {

	}
	public void setRefresh(boolean requestDataRefresh) {
		if (mSwipeRefreshLayout == null) {
			return;
		}
		if (!requestDataRefresh) {
			// 防止刷新消失太快，让子弹飞一会儿.
			rootView.postDelayed(new Runnable() {
				@Override public void run() {
					mSwipeRefreshLayout.setRefreshing(false);
				}

			}, Constants.Delay_Reflush_false);
		} else {
			rootView.postDelayed(new Runnable() {
				@Override
				public void run() {
					mSwipeRefreshLayout.setRefreshing(true);
				}
			},Constants.Delay_Reflush_true);
		}
	}

	public abstract View initView();

	public abstract void initData();
	
	public View getRootView() {
		return rootView;
	}

	//TODO 生命周期需要调用
	public void onResume() {
		LogUtils.e("onResume");
	}
	public void onPause(){
		LogUtils.e("onPause");
	}
	public void onDestroy(){
		LogUtils.e("onDestroy");
		unSubscriber();
//		mActivity = null;
		mContext = null;
	}

	public void addSubscription(Subscription s) {
		if (this.mCompositeSubscription == null) {
			this.mCompositeSubscription = new CompositeSubscription();
		}

		this.mCompositeSubscription.add(s);
	}

	public void unSubscriber(){
		if (this.mCompositeSubscription != null) {
			this.mCompositeSubscription.unsubscribe();
		}
	}

	public  void onLoginEvent(LoginEvent event){
		LogUtils.e("onLoginEvent");
	}
	public  void onExitLoginEvent(ExitLoginEvent event){
		LogUtils.e("onExitLoginEvent");
	}

	public void onUnreadMessageEvent(int count){

	}
}
