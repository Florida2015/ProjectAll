package com.hxxc.huaxing.app.ui.HuaXingFragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.AdsBean;
import com.hxxc.huaxing.app.data.bean.AgreementBean;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.WebActivity;
import com.hxxc.huaxing.app.ui.base.BaseAdapter2;
import com.hxxc.huaxing.app.ui.home.HomeActivity;
import com.hxxc.huaxing.app.ui.wealth.BaseFragment2;
import com.hxxc.huaxing.app.ui.wealth.productdetail.ProductDetailActivity;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.DisplayUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2016/9/21.
 * 首页
 */
public class HuaXingFragment extends BaseFragment2 implements View.OnClickListener, HomeV {
    RecyclerView recyclerview;
    private BGABanner mBanner;
    private HomeAdapter myAdapter;
    private ArrayList<ProductBean> mDatas = new ArrayList<>();
    private boolean mIsLoadingmore = false;
    private RelativeLayout toolbar;
    public float mAlpha = 0;
    private HomePresenter mPresenter;
    private FrameLayout mEmptyView;

    public static HuaXingFragment newInstance(String users) {
        Bundle arguments = new Bundle();
        arguments.putString("user", users);
        HuaXingFragment fragment = new HuaXingFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected int getContentViewID() {
        return R.layout.home_huaxing_fragment;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
//        ButterKnife.bind(this, rootView);
        initToolbar(rootView);

        mPresenter = new HomePresenter(this);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mEmptyView = (FrameLayout) rootView.findViewById(R.id.empty_view);
        initRecycleView();
        rootView.post(new Runnable() {
            @Override
            public void run() {
                setRefresh(true);
                mPresenter.doReflush();
            }
        });
    }

    //初始化Toolbar
    private void initToolbar(View rootView) {
        toolbar = (RelativeLayout) rootView.findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(mContext);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(0, statusBarHeight, 0, 0);
            toolbar.setLayoutParams(layoutParams);
        }
        toolbar.setAlpha(0);
    }

    private void initRecycleView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        // TODO 初始化HeaderView
        View headerView = View.inflate(mContext, R.layout.item_listview_home_header, null);
        initHeaderView(headerView);


        // TODO 初始化Recyclerview
        myAdapter = new HomeAdapter(mContext, mDatas, recyclerview);
        myAdapter.addHeaderView(headerView);
        myAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                if (mDatas.size() > 0) {
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("pid", myAdapter.mList.get(tag).getId() + "");
                    startActivity(intent);

                    mPresenter.getDatas(false);//每次刷新数据
                }
            }

            @Override
            public void setOnItemLongClick(View view, int tag) {

            }
        });
        recyclerview.setAdapter(myAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.e("scrollY==" + recyclerView.getScrollY() + "***" + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 设置toolbar动画
                if (((LinearLayoutManager) recyclerview.getLayoutManager()).findFirstVisibleItemPosition() < 1) {//只有在可视条目少于1的情况下有效
                    int height = DisplayUtil.dip2px(mContext, 40);//头布局的高度
                    List<View> headerView1 = myAdapter.getHeaderView();
                    View view = headerView1.get(0);
                    mAlpha = Math.abs(view.getY() / height);
                    toolbar.setAlpha(mAlpha);
                    ((HomeActivity) getActivity()).setStatusBarViews(mAlpha);

                    LogUtil.e("y==" + height + "-" + view.getY() + "******dx==" + dx + "*****************dy==" + dy);
                    LogUtil.e("findFirstVisibleItemPosition==" + ((LinearLayoutManager) recyclerview.getLayoutManager()).findFirstVisibleItemPosition());
                    if (((LinearLayoutManager) recyclerview.getLayoutManager()).findFirstVisibleItemPosition() > 0) {
                        mAlpha = 1;
                        toolbar.setAlpha(1);
                    }
                }

                //TODO 设置下拉加载
                if (!mIsLoadingmore && myAdapter.hasDatas() && myAdapter.isLoadmore) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == myAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        mPresenter.onLoadingmore();
                    }
                }
            }
        });
    }

    //初始化recyclerview头部view
    private void initHeaderView(View view) {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        mBanner = (BGABanner) view.findViewById(R.id.banner);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
        params.width = metric.widthPixels;
        params.height = params.width / 2;
        mBanner.setLayoutParams(params);

        mBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                AdsBean bean = (AdsBean) model;
                if(null != bean && !TextUtils.isEmpty(bean.getConnectUrl())){
                    startActivity(new Intent(getContext(),WebActivity.class).putExtra("url",bean.getConnectUrl()));
                }
            }
        });
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                AdsBean bean = (AdsBean) model;
                if (null != bean) {
                    if (!TextUtils.isEmpty(bean.getPictureSourceUrl())) {
                        Picasso.with(mContext).load(bean.getPictureSourceUrl()).placeholder(R.mipmap.banner).error(R.mipmap.banner).into((ImageView) view);
                    } else {
                        Picasso.with(mContext).load(R.mipmap.banner).into((ImageView) view);
                    }

                } else {
                    Picasso.with(mContext).load(R.mipmap.banner).into((ImageView) view);
                }
            }
        });
        LinearLayout ll_security = (LinearLayout) view.findViewById(R.id.ll_security);
        LinearLayout ll_about = (LinearLayout) view.findViewById(R.id.ll_about);
        ll_security.setOnClickListener(this);
        ll_about.setOnClickListener(this);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mPresenter.doReflush();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_security://安全保障
                if (BtnUtils.isFastDoubleClick())
                    getByAgrementType("1");
//                getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title","安全保障").
//                        putExtra("url", HttpConfig.Pic_URL+UserInfoConfig.WebUrl_abountsafe).putExtra("flag",false));
                break;
            case R.id.ll_about://关于我们
                if (BtnUtils.isFastDoubleClick()) getByAgrementType("6");
//                getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title","关于我们").
//                        putExtra("url", HttpConfig.Pic_URL+UserInfoConfig.WebUrl_abountus).putExtra("flag",false));
                break;
        }
    }

    @Override
    public void onSuccess(List<ProductBean> datas, boolean isLoadingMore) {
        if (null == datas || datas.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
        }
        myAdapter.notifyDatasChanged(datas, isLoadingMore);
        setRefresh(false);
        mIsLoadingmore = false;
    }

    @Override
    public void onError() {
        mEmptyView.setVisibility(View.VISIBLE);
        setRefresh(false);
    }

    @Override
    public void onGetAds(List<AdsBean> model) {
        mBanner.setData(model, null);
    }

    /**
     * 根据协议类型获取协议列表协议类型:
     * 【1、安全保障 2、注册协议 3、风险揭示书 4、保密协议 5、隐私政策、6、关于我们 7.购买协议】
     * 7、银行卡充值引导  8、E账户余额充值引导引导 9、提现图文引导 10.购买协议
     */
    public void getByAgrementType(String type) {
        Api.getClient().getPubSelectByAgrementType(type).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<AgreementBean>>(getActivity()) {
                              @Override
                              public void onSuccess(List<AgreementBean> agreementBeen) {
                                  if (agreementBeen != null && agreementBeen.size() > 0) {
//                                if (type.equals("1")){
                                      getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", agreementBeen.get(0).getAgreementName()).
                                              putExtra("url", agreementBeen.get(0).getMobileViewUrl()).putExtra("flag", false));
//                                }else if(type.equals("6")){
//                                    getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title","关于我们").
//                                     putExtra("url", HttpConfig.Pic_URL+UserInfoConfig.WebUrl_abountus).putExtra("flag",false));
//                                }
                                  }
                              }

                              @Override
                              public void onFail(String fail) {
                                  if (!TextUtils.isEmpty(fail)) ToastUtil.ToastShort(getActivity(), fail);
                              }
                          }

                );

    }

}
