package com.huaxia.finance.recommenddm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.FileUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.arcanimator.ArcAnimator;
import com.framwork.arcanimator.ArcUtils;
import com.framwork.arcanimator.Side;
import com.framwork.arcanimator.SupportAnimator;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.pullrefresh.ui.PullToRefreshBase;
import com.framwork.pullrefresh.ui.PullToRefreshScrollView;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UmengConstants;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.mangemoneydm.ProductExplainActivity;
import com.huaxia.finance.model.ActivitiesModel;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.NoticeListModel;
import com.huaxia.finance.model.NoticeModel;
import com.huaxia.finance.model.ProductItemModel;
import com.huaxia.finance.moredm.NoticeDetailActivity;
import com.huaxia.finance.request.BaseRequestParams;
import com.library.BannerLayout;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by houwen.lai on 2016/1/18.
 * 推荐页面 功能： banner轮播图 通知 推荐产品
 */
public class RecommendFragment  extends Fragment implements View.OnClickListener{

    private final int whantNotice = 0x00;
    private final int whantDelay = 0x01;

    private String mPageName;
    int mNum;


    private PullToRefreshScrollView pull_scrollview;
//    private Advertising advertis;
    private BannerLayout banner;

    private RelativeLayout relative_notice;
    private TextSwitcher text_switcher;
    private ImageButton imgbtn_dis_notice;

    private TextView tv_recommend_product;//产品
    private ImageView img_in_circle;
    private ImageView img_out_circle;
    private ImageView img_product_tip;//新手专享
    private ImageView img_product_tip_h;

    private TextView tv_rate_detail;
    private TextView tv_rate;//预期年化收益率
    private TextView tv_bbin;//预期年化收益率描述

    private TextView tv_sum_time;//总天数
    private TextView tv_money_min;//起投金额
    private TextView tv_extra_money;//限额

    private LinearLayout linear_circle;

    private Button btn_buy_product;

    private TextView tv_dhcp;//协议

    private List<TextView> noticeLit;

    /**
     * 滚动显示通知
     */
    private int length=0;
    private int temp=0;
    // 计时器
    android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == whantNotice) {
                temp++;
                if (temp>noticeModelList.size()-1){
                    temp=0;
                }
                text_switcher.setText(noticeModelList.get(temp).getNoticeSubject());
                handler.sendEmptyMessageDelayed(whantNotice,5000);
            }else if(msg.what ==whantDelay){

                josonReadSdcard();
            }

        };
    };

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart(mPageName);

//        initViews(getActivity());
    }
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mPageName = String.format("fragment %d", mNum);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (container == null) {
            // Currently in a layout without a container, so no
            // reason to create our view.
            return null;
        }
//        LayoutInflater myInflater = (LayoutInflater) getActivity()
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);//
//        View view = inflater.inflate(R.layout.recommend_fragment,container, false);
        pull_scrollview = new PullToRefreshScrollView(getActivity());
        return pull_scrollview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater myInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);//
        View mView = myInflater.inflate(R.layout.recommend_fragment,null, false);
        pull_scrollview.getRefreshableView().addView(mView);
        initViews(mView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void initViews(View mview){
//        advertis= (Advertising) mview.findViewById(R.id.advertis);
        banner= (BannerLayout) mview.findViewById(R.id.banner);

        relative_notice = (RelativeLayout) mview.findViewById(R.id.relative_notice);
        text_switcher = (TextSwitcher) mview.findViewById(R.id.text_switcher);
        imgbtn_dis_notice = (ImageButton) mview.findViewById(R.id.imgbtn_dis_notice);
        text_switcher.removeAllViews();
        text_switcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                TextView tv = new TextView(getActivity());
//                Drawable drawable= getResources().getDrawable(R.drawable.icon_notice);
//                /// 这一步必须要做,否则不会显示.
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tv.setCompoundDrawablePadding(2);
//                tv.setCompoundDrawables(drawable, null, null, null);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setSingleLine();
                tv.setTextSize(12);
                tv.setTextColor(getActivity().getResources().getColor(R.color.black_9999));
                return tv;
            }
        });
        text_switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入通知详情
//                if (temp>textString.length-1){
//                    MyLog.d("api_text_notice="+textString[0]+"_noticeno="+noticeModelList.get(temp).getNoticeNo());
//                }else MyLog.d("api_text_notice="+textString[temp]+"_noticeno="+noticeModelList.get(temp).getNoticeNo());
                MyLog.d("api_text_switcher_temp=" + temp + "_textlenth=" + textString.length);
                if (NetWorkUtils.isNetworkConnected(getActivity())) {
                    if (noticeModelList!=null&&noticeModelList.size()>0&&temp<noticeModelList.size()){
                        getActivity().startActivity(new Intent(getActivity(), NoticeDetailActivity.class).putExtra("noticeNo", noticeModelList.get(temp).getNoticeNo()));
    //                    getActivity().overridePendingTransition(R.anim.fade, R.anim.fade);
                    }
                }else ToastUtils.showShort("网络不给力");
            }
        });
        //
        text_switcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.top_in));//android.R.anim.slide_in_left
//        text_switcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.top_out));// android.R.anim.slide_out_right

        imgbtn_dis_notice.setOnClickListener(this);

        if (DMApplication.getInstance().isNoticeFlag)relative_notice.setVisibility(View.INVISIBLE);
        else relative_notice.setVisibility(View.VISIBLE);

        tv_recommend_product = (TextView) mview.findViewById(R.id.tv_recommend_product);//产品
        img_in_circle = (ImageView) mview.findViewById(R.id.img_in_circle);
        img_out_circle = (ImageView) mview.findViewById(R.id.img_out_circle);
        img_product_tip = (ImageView) mview.findViewById(R.id.img_product_tip);//新手专享
        img_product_tip_h = (ImageView) mview.findViewById(R.id.img_product_tip_h);//新手专享

        tv_rate_detail = (TextView) mview.findViewById(R.id.tv_rate_detail);
        tv_rate = (TextView) mview.findViewById(R.id.tv_rate);//预期年化收益率
        tv_bbin = (TextView) mview.findViewById(R.id.tv_bbin);//预期年化收益率描述

        tv_sum_time = (TextView) mview.findViewById(R.id.tv_sum_time);//总天数
        tv_money_min  = (TextView) mview.findViewById(R.id.tv_money_min);//起投金额
        tv_extra_money = (TextView) mview.findViewById(R.id.tv_extra_money);//限额

        img_in_circle = (ImageView) mview.findViewById(R.id.img_in_circle);//
        img_in_circle.setOnClickListener(this);
        linear_circle = (LinearLayout) mview.findViewById(R.id.linear_circle);//
        linear_circle.setOnClickListener(this);

        btn_buy_product = (Button) mview.findViewById(R.id.btn_buy_product);
        btn_buy_product.setOnClickListener(this);

        tv_dhcp = (TextView) mview.findViewById(R.id.tv_dhcp);//协议

        pull_scrollview.setPullRefreshEnabled(true);
        pull_scrollview.setScrollLoadEnabled(false);
        pull_scrollview.setPullLoadEnabled(false);
        pull_scrollview.getRefreshableView().setVerticalScrollBarEnabled(false);
//        pull_scrollview.getFooterLoadingLayout().setBackgroundColor(getActivity().getResources().getColor(R.color.black_ecec));
//        pull_scrollview.getFooterLoadingLayout().findViewById(R.id.pull_to_load_footer_progressbar).setVisibility(View.GONE);
//        pull_scrollview.getFooterLoadingLayout().findViewById(R.id.pull_to_load_footer_hint_textview).setVisibility(View.VISIBLE);
//        ((TextView)pull_scrollview.getFooterLoadingLayout().findViewById(R.id.pull_to_load_footer_hint_textview)).setText(getActivity().getResources().getString(R.string.text_warming_invest));

        pull_scrollview.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        pull_scrollview.getRefreshableView().setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        pull_scrollview.setLastUpdatedLabel(DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS));
        pull_scrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                pull_scrollview.setLastUpdatedLabel(DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS));
                getBannerHttpRequest();
                getNoticeHttpRequest();
                getProductRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                pull_scrollview.onPullUpRefreshComplete();
            }
        });

        if (NetWorkUtils.isNetworkConnected(getActivity())){
            getBannerHttpRequest();
            getNoticeHttpRequest();
            getProductRequest();
        }else{

            handler.sendEmptyMessageDelayed(whantDelay,300);

        }
        flagAnimator=true;
    }

    /**
     *
     */
    public void josonReadSdcard(){
        String textB= FileUtils.getInstance().readFileToSdcardByFileInputStream(UserConstant.pathCache,UserConstant.pathBanner);
        MyLog.d("text_banner=" + textB);
        if (TextUtils.isEmpty(textB))
            getBannerHttpRequest();
        else jsonBannerData(textB);

        String textN= FileUtils.getInstance().readFileToSdcardByFileInputStream(UserConstant.pathCache,UserConstant.pathNotice);
        MyLog.d("text_notice=" + textN);
        if (TextUtils.isEmpty(textN))
            getNoticeHttpRequest();
        else jsonNoticeData(textN);

        String textHP= FileUtils.getInstance().readFileToSdcardByFileInputStream(UserConstant.pathCache,UserConstant.pathHomePageProduct);
        MyLog.d("text_homePage=" + textHP);
        if (TextUtils.isEmpty(textHP))
            getProductRequest();
        else jsonHomePageProductData(textHP);

    }

    private String[] img = new String[]{UrlConstants.banner_img_1,UrlConstants.banner_img_2,UrlConstants.banner_img_3};
    private String[] textString = new String[]{"投资5万融iPhone6s手机活动延期通知","通知花虾金融春节期间安排通知！"};
    private List<NoticeModel> noticeModelList;
    /**
     * 初始化 公告
     */
    public void InitAds(final List<ActivitiesModel> models) {
        List<String> urls = new ArrayList<>();
        if (models!=null){
            for (int i=0;i<models.size();i++){
                urls.add(models.get(i).getHomeUrl());
            }
        }
//        advertis.setAdsPicArraysUrl(models);
//        advertis.setAdsClickLister(new Advertising.OnAdsClikLister() {
//            @Override
//            public void OnAdsClikLister(View view, int index) {
//                if (index < models.size())
//                    startActivity(new Intent(getActivity(), ActivtiesWebActivity.class).putExtra("mode", models.get(index)));
//            }
//        });
        banner.setViewUrls(urls,getActivity().getResources().getDrawable(R.drawable.ads_loading));
        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (NetWorkUtils.isNetworkConnected(getActivity())) {
                    if (models != null && position < models.size()){
                        startActivity(new Intent(getActivity(), ActivtiesWebActivity.class).putExtra("activityId", models.get(position).getActivityId()));
//                        getActivity().overridePendingTransition(R.anim.fade, R.anim.fade);
                    }
                }else ToastUtils.showShort("网络不给力");
                }
        });

    }

    /**
     * 初始化 通知
     */
    public void InitNotic(List<NoticeModel> noticeModelsLits){
        //通知列表
        noticeLit = new ArrayList<TextView>();
//        if (textString != null && textString.length > 0) {
//            text_switcher.setText(textString[temp]);
//            handler.sendEmptyMessage(whantNotice);
//        } else relative_notice.setVisibility(View.GONE);
        if (noticeModelsLits!=null&&noticeModelsLits.size()>0) {
                text_switcher.setText(noticeModelsLits.get(temp).getNoticeSubject());
                handler.sendEmptyMessage(whantNotice);
        } else relative_notice.setVisibility(View.INVISIBLE);
    }

    private String productId;
    private String productName;
    /**
     * 初始化产品
     */
    public void ProductInit(ProductItemModel model){
        tv_recommend_product.setText("【"+model.getProductName()+"】 第"+model.getProductNum()+"期");
        tv_rate_detail.setText("预期年化收益率(%)");
        tv_rate.setText(DateUtils.getInstanse().getTwo(model.getYield()));
        tv_bbin.setText("体验金");

        tv_sum_time.setText(model.getPeriod()+"天");
        tv_money_min.setText(DateUtils.getInstanse().getInt(model.getMinPrice())+"元起投");
        if (model.getMaxPrice()>0)
        tv_extra_money.setText("限购"+DateUtils.getInstanse().getDataType((long)model.getMaxPrice()));
        else tv_extra_money.setVisibility(View.GONE);

        SpannableString msp = new SpannableString("账户资金安全由太平洋保险承保");
    // 设置字体颜色
        msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.black_9999)), 0, 7,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
        msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange_ff92)), 7, 12,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
        msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.black_9999)), 12, msp.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
        tv_dhcp.setText(msp);
        tv_dhcp.setMovementMethod(LinkMovementMethod.getInstance());

        productId=model.getProductId();
        productName = model.getProductName();

        InitAnimator();
    }

    /**
     * 初始化动画
     */
    public void InitTipAnimation(){
        //外圈动画 缩放动画
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_big));
        animationSet.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_small));
        img_out_circle.setAnimation(animationSet);
        animationSet.startNow();

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
//                ObjectAnimator.ofFloat(img_out_circle, "rotationX", 0, 360),
//                ObjectAnimator.ofFloat(img_out_circle, "rotationY", 0, 180),
//                ObjectAnimator.ofFloat(img_out_circle, "rotation", 0, -90),
//                ObjectAnimator.ofFloat(img_out_circle, "translationX", 0, 90),
//                ObjectAnimator.ofFloat(img_out_circle, "translationY", 0, 90),
                ObjectAnimator.ofFloat(img_out_circle, "scaleX", 1, 1.15f),
                ObjectAnimator.ofFloat(img_out_circle, "scaleY", 1, 1.15f),
                ObjectAnimator.ofFloat(img_out_circle, "scaleX", 1.15f, 1),
                ObjectAnimator.ofFloat(img_out_circle, "scaleY", 1.15f, 1)
//                ObjectAnimator.ofFloat(img_out_circle, "alpha", 1, 0.25f, 1)
        );
        set.setDuration(1500).setStartDelay(2000);

        //tip转圈 动画
//        AnimationSet animationSettip = new AnimationSet(true);
////        RotateAnimation rotateAnimation = new RotateAnimation(0f,359f, Animation.RELATIVE_TO_PARENT, 0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
//        animationSettip.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_tip));
////        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        animationSettip.addAnimation(animationSettip);
////        animationSettip.setStartOffset(1000);//延迟开始
//        img_product_tip.setAnimation(animationSettip);
//        animationSettip.startNow();


//        Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_tip);
//        LinearInterpolator lin = new LinearInterpolator();
//        operatingAnim.setInterpolator(lin);
//        if (operatingAnim != null) {
//            img_product_tip.startAnimation(operatingAnim);
//        }

    }

    boolean flagAnimator=true;
    float startX;
    float startY;

    float endX;
    float endY;
//    ArcAnimator arcAnimator;
//    ArcAnimator arcAnimatorM;

    final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
    final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
    public void  InitAnimator(){
        //外圈动画 缩放动画
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_big));
//        animationSet.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_small));
//        img_out_circle.setAnimation(animationSet);
//        animationSet.startNow();

        if (flagAnimator){
            flagAnimator=false;
            startX = ArcUtils.centerX(img_product_tip);
            startY = ArcUtils.centerY(img_product_tip);
            endX = ArcUtils.centerX(img_product_tip_h);
            endY = ArcUtils.centerY(img_product_tip_h);
            ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(img_product_tip, endX,endY, 180, Side.RIGHT)
                .setDuration(1500);
            arcAnimator.cancel();
            arcAnimator.removeAllListeners();
            arcAnimator.setInterpolator(ACCELERATE);
            arcAnimator.addListener(new SimpleListener(){
                @Override
                public void onAnimationEnd(Animator animation) {
                    ArcAnimator arcAnimatorM = ArcAnimator.createArcAnimator(img_product_tip,startX,startY, 180, Side.LEFT)
                            .setDuration(1500);
                    arcAnimatorM.setInterpolator(ACCELERATE_DECELERATE);
                    arcAnimatorM.start();

                }
            });
            arcAnimator.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgbtn_dis_notice://隐藏通知
                relative_notice.setVisibility(View.INVISIBLE);
                DMApplication.getInstance().isNoticeFlag=true;
                break;
            case R.id.linear_circle:
            case R.id.img_in_circle:
            case R.id.btn_buy_product://进入产品详情页
                //请求数据
                if (NetWorkUtils.isNetworkConnected(getActivity())) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("productId", productId + "");
                    map.put("productName", productName + "");
                    MobclickAgent.onEvent(getActivity(), UmengConstants.huaxia_001, map);
                    if (!TextUtils.isEmpty(productId)){
                        getActivity().startActivity(new Intent(getActivity(), ProductExplainActivity.class).putExtra("produnctId", productId));
//                        getActivity().overridePendingTransition(R.anim.fade, R.anim.fade);
                    }
                }else ToastUtils.showShort("网络不给力");
                break;
            case R.id.tv_dhcp://进入协议
//                startActivity(new Intent(getActivity(), AgreementActivity.class));
                break;
        }
    }

    /**
     * banner 解析
     */
    public void jsonBannerData(String result){
        BaseModel<List<ActivitiesModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<List<ActivitiesModel>>>() {
        }.getType());
        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

            InitAds(baseModel.getData());

        }
    }



    /**
     * 获取首页banner轮播活动
     */
    public void getBannerHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        String url = UrlConstants.urlBase+UrlConstants.urlBanner;
        MyLog.d("api_url="+url);
        DMApplication.getInstance().getHttpClient(getActivity()).get(getActivity(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                pull_scrollview.onPullDownRefreshComplete();
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));

                    FileUtils.getInstance().writeFileToSdcardByFileOutputStream(UserConstant.pathCache,UserConstant.pathBanner,result);

                    if (statusCode == 200) {

                        jsonBannerData(result);

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");

                pull_scrollview.onPullDownRefreshComplete();
            }
        });
    }

    /**
     * notice json
     */
    public void jsonNoticeData(String result){

        BaseModel<NoticeListModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<NoticeListModel>>() {
        }.getType());
        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
            noticeModelList = baseModel.getData().getList();
            InitNotic(noticeModelList);

        }else relative_notice.setVisibility(View.INVISIBLE);


    }

    /**
     * 获取首页通知
     */
    public void getNoticeHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("start","0");//
        params.put("size", "10");//
        String url = UrlConstants.urlBase+UrlConstants.urlNoticeList;
        MyLog.d("api_url=" + url);
        DMApplication.getInstance().getHttpClient(getActivity()).get(getActivity(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                pull_scrollview.onPullDownRefreshComplete();
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));

                    FileUtils.getInstance().writeFileToSdcardByFileOutputStream(UserConstant.pathCache, UserConstant.pathNotice, result);

                    if (statusCode == 200) {

                        jsonNoticeData(result);

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                pull_scrollview.onPullDownRefreshComplete();
            }
        });
    }

    /**
     * home page product json
     */
    public void jsonHomePageProductData(String result){

        BaseModel<ProductItemModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<ProductItemModel>>() {
        }.getType());
        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

            ProductInit(baseModel.getData());

        }
    }

    public void getProductRequest(){
        final BaseRequestParams params = new BaseRequestParams();
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlOnHome);
        MyLog.d("api_url="+url.toString());
        DMApplication.getInstance().getHttpClient(getActivity()).get(getActivity(), url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                pull_scrollview.onPullDownRefreshComplete();
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);

                    FileUtils.getInstance().writeFileToSdcardByFileOutputStream(UserConstant.pathCache, UserConstant.pathHomePageProduct, resultDate);

                    if (statusCode == 200) {

                        jsonHomePageProductData(resultDate);

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                pull_scrollview.onPullDownRefreshComplete();
            }
        });
    }

    private static class SimpleListener implements SupportAnimator.AnimatorListener, ObjectAnimator.AnimatorListener{


        @Override
        public void onAnimationStart(Animator animator) {
            MyLog.d("api_onAnimation_Start");
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            MyLog.d("api_onAnimation_End");
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            MyLog.d("api_onAnimation_Cancel");
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
            MyLog.d("api_onAnimation_Repeat");
        }

        @Override
        public void onAnimationStart() {
            MyLog.d("api_onAnimation_Start__");
        }

        @Override
        public void onAnimationEnd() {
            MyLog.d("api_onAnimation_End__");
        }

        @Override
        public void onAnimationCancel() {
            MyLog.d("api_onAnimation_Cancel__");
        }

        @Override
        public void onAnimationRepeat() {
            MyLog.d("api_onAnimation_Repeat__");
        }
    }
}
