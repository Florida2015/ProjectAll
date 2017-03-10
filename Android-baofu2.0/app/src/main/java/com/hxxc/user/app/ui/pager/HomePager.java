package com.hxxc.user.app.ui.pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.contract.IndexContract;
import com.hxxc.user.app.contract.presenter.IndexPresenter;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.HomeAdapter;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.mine.membercenter.MemberCenterActivity;
import com.hxxc.user.app.ui.mine.noticelist.MessageActivity;
import com.hxxc.user.app.ui.product.ProductDetailActivity;
import com.hxxc.user.app.ui.user.LoginActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import de.greenrobot.event.EventBus;

import static com.hxxc.user.app.utils.SPUtils.geTinstance;

/**
 * Created by chenqun on 2016/8/17.
 */
public class HomePager extends BaseTitlePager implements IndexContract.View, View.OnClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.fl_toolbar)
    FrameLayout fl_toolbar;
//    @BindView(R.id.toolbar_iv_message)
//    ImageButton toolbar_iv_message;

    @BindView(R.id.rl_unread)
    RelativeLayout rl_unread;

    @BindView(R.id.tv_unread)
    TextView tv_unread;

    private List<String> mDatas = new ArrayList<>();
    private final Handler mHandler;
    private Unbinder unbinder;
    private IndexPresenter presenter;
    private BGABanner mBanner;
    private HomeAdapter myAdapter;
    public float mAlpha;
    private boolean mIsLoadingmore = false;

    public HomePager(Context context) {
        super(context);
        this.mHandler = new Handler(Looper.getMainLooper());
        initToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"首页");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"首页");
    }

    @Override
    protected void setTitle() {
        mTitle.setText("华夏信财");
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.activity_index, null);
        unbinder = ButterKnife.bind(this, view);
        presenter = new IndexPresenter(this);
        init();
        return view;
    }

    private void init() {
        initRecycleView();
    }

    //初始化Toolbar
    private void initToolbar() {
        mTitle.setTextColor(Color.WHITE);
        rl_unread.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(Color.parseColor("#ff1f80d1"));
        toolbar.setAlpha(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(mContext);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_toolbar.getLayoutParams();
            layoutParams.setMargins(0, statusBarHeight, 0, 0);
            fl_toolbar.setLayoutParams(layoutParams);
        }
    }

    @SuppressLint("NewApi")
    private void initRecycleView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        // TODO 初始化HeaderView
        View headerView = View.inflate(mContext, R.layout.item_listview_home_header, null);
        initHeaderView(headerView);

        // TODO 初始化Recyclerview
        myAdapter = new HomeAdapter(mContext, new ArrayList<Product>(), recyclerview);
        myAdapter.addHeaderView(headerView);
        myAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("pid", myAdapter.mList.get(tag).getId() + "");
                mContext.startActivity(intent);
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
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 设置toolbar动画
                if (((LinearLayoutManager) recyclerview.getLayoutManager()).findFirstVisibleItemPosition() < 1) {//只有在可视条目少于1的情况下有效
                    int height = DisplayUtil.dip2px(mContext, 150);//头布局的高度
                    List<View> headerView1 = myAdapter.getHeaderView();
                    View view = headerView1.get(0);
                    mAlpha = Math.abs(view.getY() / height);
                    toolbar.setAlpha(mAlpha);
//                    ((MainActivity2) getActivity()).setStatusBarViews(mAlpha);
                    EventBus.getDefault().post(new MainEvent(mAlpha).setLoginType(MainEvent.STATUS_BAR));

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
                        presenter.onLoadingmore();
                    }
                }
            }
        });
    }


    //初始化recyclerview头部view
    private void initHeaderView(View view) {
        mBanner = (BGABanner) view.findViewById(R.id.banner);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
        params.width = ImageUtils.getInstance().getmWindowWidth();
        params.height = (int) (params.width * Constants.RATE_HOME);
        mBanner.setLayoutParams(params);

        mBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                if (model != null) {
                    String url = ((IndexAds) model).getConnectUrl();
                    if (TextUtils.isEmpty(url)) return;
                    if (url.contains("?"))
                        url = url + "&reqFrom=android";
                    else
                        url = url + "?reqFrom=android";

                    Intent in = new Intent(mContext, AdsActivity.class);
                    in.putExtra("url", url);
                    if (((IndexAds) model).getShareVo() != null) {
                        in.putExtra("isShare", true);
                        in.putExtra("shareTitle", ((IndexAds) model).getShareVo().getShareTitle());
                        in.putExtra("shareDes", ((IndexAds) model).getShareVo().getShareContents());
                        in.putExtra("shareUrl", ((IndexAds) model).getShareVo().getActivityUrl());
                        in.putExtra("shareImg", ((IndexAds) model).getShareVo().getRealShareIcon());
                    } else in.putExtra("isShare", false);
                    mContext.startActivity(in);
                }
            }
        });
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                // 展示当前的图片
//                Picasso.with(mContext).load(((IndexAds)model).getRealPictureSourceUrl()).error(R.drawable.banner).into((ImageView)view);
                ImageUtils.getInstance().displayImage(((IndexAds) model).getRealPictureSourceUrl(), ((ImageView) view), ImageUtils.mOptionsForIndex);
            }
        });

        TextView textview1 = (TextView) view.findViewById(R.id.textview1);
        TextView textview2 = (TextView) view.findViewById(R.id.textview2);
        TextView textview3 = (TextView) view.findViewById(R.id.textview3);
        textview1.setOnClickListener(this);
        textview2.setOnClickListener(this);
        textview3.setOnClickListener(this);
    }


    @Override
    public void initData() {
        recyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.subscribe();
            }
        }, 500);
        setRefresh(true);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void initViewPagerData(final List<IndexAds> list) {
        // TODO 刷新HeaderView

        if (list == null || list.size() <= 1) {
            mBanner.setAutoPlayAble(false);
        } else {
            mBanner.setAutoPlayAble(true);
        }
        mBanner.setData(list, null);
    }

    @Override
    public void onGetDatas(List<Product> list, boolean isLoadingmore) {
        mIsLoadingmore = false;
        myAdapter.notifyDatasChanged(list, isLoadingmore);
        setRefresh(false);
    }

    @Override
    protected void onRefresh() {
        presenter.doReflush();
    }

    @OnClick({R.id.rl_unread})
    public void onClick(View view) {
//        Intent intent = new Intent(mContext, AdsActivity.class);
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.textview1://会员中心
                    if (TextUtils.isEmpty(geTinstance().getUid())) {
                        Intent in = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(in);
                    } else {
                        mContext.startActivity(new Intent(mContext, MemberCenterActivity.class));
                    }
                    break;
                case R.id.textview2://邀请好友
                    UserInfo info = geTinstance().getUserInfo();
                    Intent intent = new Intent(mContext, AdsActivity.class);
                    intent.putExtra("title", "邀请好友");
                    intent.putExtra("shareUrlFrom", 5);
                    intent.putExtra("isShare", true);
//                    if (null == info)
//                        intent.putExtra("url", HttpRequest.indexBaseUrl + HttpRequest.indexurl2);
//                    else
//                        intent.putExtra("url", HttpRequest.indexBaseUrl + HttpRequest.indexurl2 + "?invitedCode=" + info.getInvitedCode() + "&fid=" + info.getFid() + "&uid=" + info.getUid());
                    mContext.startActivity(intent);
                    break;
                case R.id.textview3://安全保障
                    mContext.startActivity(new Intent(mContext, AdsActivity.class)
                            .putExtra("title", "安全保障")
                            .putExtra("url", HttpRequest.indexBaseUrl + HttpRequest.indexurl3));//HttpRequest.indexBaseUrl + HttpRequest.indexurl3
                    break;
                case R.id.rl_unread://消息列表
                    if (TextUtils.isEmpty(geTinstance().getUid())) {
                        Intent in = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(in);
                    } else {
                        mContext.startActivity(new Intent(mContext, MessageActivity.class));
                    }
                    return;
            }
        }
//        intent.putExtra("isShare", false);
//        mContext.startActivity(intent);
    }

    @Override
    public void onUnreadMessageEvent(int count) {
        super.onUnreadMessageEvent(count);
        if (null == tv_unread) return;
        if (count > 0) tv_unread.setVisibility(View.VISIBLE);
        else tv_unread.setVisibility(View.INVISIBLE);
    }
}
