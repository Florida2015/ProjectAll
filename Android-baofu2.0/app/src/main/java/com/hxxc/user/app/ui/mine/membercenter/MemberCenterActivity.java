package com.hxxc.user.app.ui.mine.membercenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.data.bean.MemberCenterBean;
import com.hxxc.user.app.data.bean.MemberCenterDetailBean;
import com.hxxc.user.app.data.bean.MemberCenterPrivilegeBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.dialogfragment.LendDialogListener;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.mine.dialog.LendDialogFragment;
import com.hxxc.user.app.ui.mine.integral.IntegralListActivity;
import com.hxxc.user.app.ui.mine.invitation.InvitationFriendsActivity;
import com.hxxc.user.app.ui.mine.sign.SignActivity;
import com.hxxc.user.app.ui.mine.web.WebActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.ui.product.BaofuBingCardActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.widget.CircleImageView;
import com.numberprogressbar.NumberProgressBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/10/28.
 * 会员中心
 */

public class MemberCenterActivity extends BaseRxActivity implements View.OnClickListener,LendDialogListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.view_titlebar)
    View view_titlebar;


    @BindView(R.id.listview_member_center)
    ListView listview_member_center;


    View mView;

    CircleImageView img_member_icon;
    TextView tv_member_name;
    TextView tv_member_level;

    TextView tv_member_level_name;

    TextView tv_member_integal;//积分

    LinearLayout linear_member_center;//中间按钮

    //进度条
    NumberProgressBar mMyProgressbar;

    TextView  tv_home_mine_center_title;

    Button btn_integral_list;
    Button btn_integral_city;

    private String urlMall;//商城url

    public static MemberCenterActivity instance;

    @Override
    protected void onResume() {
        super.onResume();
        getTaskData(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.member_center_activity;
    }

    @Override
    public void initView() {
        //沉浸式状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        instance = this;
        toolbar_title.setText("会员中心");
        toolbar_title.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationIcon(R.mipmap.nav_back);
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue_1f80));
        view_titlebar.setVisibility(View.GONE);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //给页面设置工具栏
//        if (collapsingToolbar != null) {
//            //设置隐藏图片时候ToolBar的颜色
//            collapsingToolbar.setContentScrimColor(Color.parseColor("#0195ff"));
//            //设置工具栏标题
//            collapsingToolbar.setTitle(" ");
//        }

//        mMyProgressbar = (LineTopProgressbar) findViewById(R.id.progressbar_merber);

        lists = new ArrayList<MemberCenterBean>();


//        urlMall = HttpRequest.indexBaseUrl + HttpRequest.UrlMail;

//        getTaskData(1);

    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //点击了返回箭头
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    MemberCenterDetailBean centerDetailBean;
    public void initData(MemberCenterDetailBean bean){
        if (mView==null)return;
        centerDetailBean=bean;
        img_member_icon = (CircleImageView) mView.findViewById(R.id.img_member_icon);
        tv_member_name = (TextView) mView.findViewById(R.id.tv_member_name);
        tv_member_level = (TextView) mView.findViewById(R.id.tv_member_level);
        tv_member_level_name = (TextView) mView.findViewById(R.id.tv_member_level_name);
        tv_member_integal = (TextView) mView.findViewById(R.id.tv_member_integal);

        tv_home_mine_center_title= (TextView) mView.findViewById(R.id.tv_home_mine_center_title);

        mMyProgressbar = (NumberProgressBar) mView.findViewById(R.id.progressbar_merber);

        linear_member_center = (LinearLayout) mView.findViewById(R.id.linear_member_center);

        btn_integral_list = (Button) mView.findViewById(R.id.btn_integral_list);
        btn_integral_city = (Button) mView.findViewById(R.id.btn_integral_city);
        btn_integral_list.setOnClickListener(this);
        btn_integral_city.setOnClickListener(this);

                Picasso.with(mContext)
                .load(bean.getRealIcon())
                .placeholder(R.drawable.gestures_to_head)
                .error(R.drawable.gestures_to_head)
                .into( img_member_icon);

        tv_member_name.setText(bean.getMemberName());

        tv_member_level.setText(""+bean.getLevel());//level
        tv_member_level_name.setText(""+bean.getIdentityName());
        tv_member_integal.setText("积分："+bean.getPointValue());

//        mMyProgressbar.setProgress(60);//进度条
//        mMyProgressbar.setProgress((int) (Math.random()*10));//TODO info.getProportion()

        String integral_today =bean.getLeftExperienceValue()+"";

        final SpannableStringBuilder sp = new SpannableStringBuilder("还差"+integral_today+"财气值升级至V"+bean.getNextLevel()+bean.getNextIdentityName());
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_fbb0)), 2, 2+integral_today.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 2+integral_today.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(12, true), 0, sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_home_mine_center_title.setText(sp);

        mMyProgressbar.setMax(bean.getNextMinExperienceValue());
        mMyProgressbar.setProgress(bean.getExperienceValue());
        mMyProgressbar.incrementProgressBy(0);

        getButtonData();
        getShopMallUrl();//获取积分商城
    }

    /**
     * 添加 按钮 url
     * @param list
     */
    public void addButton(List<MemberCenterPrivilegeBean> list){
        if (linear_member_center==null)return;
        if (list==null)return;

        linear_member_center.removeAllViews();

        for (int i=0;i<list.size();i++){
           View view =  View.inflate(mContext, R.layout.member_center_button, null);
            ImageView img_center = (ImageView) view.findViewById(R.id.img_member_tip);
            TextView tv_center = (TextView) view.findViewById(R.id.tv_member_tip);

                Picasso.with(mContext)
                .load(list.get(i).getMobileIcon())
                .placeholder(R.mipmap.tip_none)
                .error(R.mipmap.tip_none)
                .into( img_center);

//            Picasso.with(mContext)
//                    .load("https://lcsitn.huaxiafinance.com/picture/tequan/birthday_gift@3x.png")
//                    .placeholder(R.mipmap.tip_none)
//                    .error(R.mipmap.tip_none)
//                    .into( img_center);

            tv_center.setText(list.get(i).getPrivilegeName());

            if (list.get(i).isMeetPrivilege()){
                tv_center.setTextColor(mContext.getResources().getColor(R.color.white));
            }else tv_center.setTextColor(mContext.getResources().getColor(R.color.transparent_58));

            int t = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(t).isMeetPrivilege()){//跳转
                        startActivity(new Intent(mContext,WebActivity.class).
                                putExtra("title","权益说明").
                                putExtra("url",list.get(t).getJumpMobileUrl()).
                                putExtra("flag",false));
//                    toast("index="+t);

                    }
                }
            });

            linear_member_center.addView(view);
        }
    }

    MermberAdapter mermberAdapter;
    List<MemberCenterBean> lists = new ArrayList<MemberCenterBean>();
    public void initAdapter(){
        mView =  View.inflate(mContext, R.layout.mermber_center_top, null);

        if (lists==null)lists = new ArrayList<MemberCenterBean>();
        listview_member_center.setDividerHeight(0);
        if (listview_member_center.getHeaderViewsCount()==0)listview_member_center.addHeaderView(mView);
        mermberAdapter = new MermberAdapter(mContext,lists,R.layout.mermber_center_item);
        listview_member_center.setAdapter(mermberAdapter);

        getMemberDetail();
    }

    @Override
    public void onClick(View view) {
        Intent mIntent = new Intent();
        switch (view.getId()){
            case R.id.btn_integral_list://积分记录
                mIntent.setClass(mContext, IntegralListActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_integral_city://积分商城
                if(BtnUtils.isFastDoubleClick()){
//                    if (!TextUtils.isEmpty(urlMall)){
//                        mIntent.setClass(mContext, AdsActivity.class);
//                        mIntent.putExtra("url",urlMall);
//                        mIntent.putExtra("title", Constants.TEXT_INTEGRAL);
//                        startActivity(mIntent);
//                    }
                    mIntent.setClass(mContext, AdsActivity.class);
                    mIntent.putExtra("url",HttpRequest.UrlMail);
                    mIntent.putExtra("title", Constants.TEXT_INTEGRAL);
                    startActivity(mIntent);
                }
                break;
        }
    }

    public void jumpOpen(int index){
        Intent mIntent = new Intent();
        switch (index){
            case 1://每日签到
                mIntent.setClass(mContext, SignActivity.class);
                startActivity(mIntent);
                break;
            case 2://邀请好友
                mIntent.setClass(mContext, InvitationFriendsActivity.class);
                startActivity(mIntent);
                break;
            case 3://出借规则 dilog
                LendDialogFragment lendDialogFragment = new LendDialogFragment().newInstance(HttpRequest.indexBaseUrl+HttpRequest.httpUrlInteger);
                lendDialogFragment.show(getFragmentManager(), "lendDialogFragment");
                break;
            case 4://实名认证
                mIntent.setClass(mContext, AuthenticationActivity.class);
                startActivity(mIntent);
                break;
            case 5://绑定银行卡
                UserInfo userInfo = SPUtils.geTinstance().getUserInfo();
                if (userInfo!=null&&userInfo.getRnaStatus() == 0){//未认证
                    mIntent.setClass(mContext, AuthenticationActivity.class);
                    startActivity(mIntent);
                }else if(userInfo!=null&&userInfo.getRnaStatus() == 1&&userInfo.getBindcardStatus()==0){//认证 去绑卡
                    startActivity(new Intent(MemberCenterActivity.this, BaofuBingCardActivity.class));
                }
                break;
           default:

               break;
        }
    }

    /**
     * 跳转 产品列表
     * @param resId
     */
    @Override
    public void onLendId(int resId) {
        if (resId==R.id.btn_member_lend){
           // 跳转 产品列表
            EventBus.getDefault().post(new MainEvent(1).setLoginType(MainEvent.FROMFINDPASSWORD));
            MemberCenterActivity.this.finish();


        }
    }


    /**
     *  适配
     */
    public class MermberAdapter extends CommonAdapter<MemberCenterBean>{

        public MermberAdapter(Context context, List list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, MemberCenterBean item, int position) {
            super.convert(helper, item, position);
            if (position==0){
                helper.getView(R.id.view_mermber_1).setVisibility(View.VISIBLE);
                helper.getView(R.id.relative_mermber_title).setVisibility(View.VISIBLE);
                helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_day_name,"每日任务");
            }else {
                helper.getView(R.id.view_mermber_1).setVisibility(View.GONE);
                helper.getView(R.id.relative_mermber_title).setVisibility(View.GONE);
                helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
            }

            if (position>0&&item.getTaskType()==0){
                if (lists!=null&&item.getTaskType()!=lists.get(position-1).getTaskType()){
                    helper.getView(R.id.view_mermber_1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.relative_mermber_title).setVisibility(View.VISIBLE);
                    helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_day_name,"成长任务");
                }else {
                    helper.getView(R.id.view_mermber_1).setVisibility(View.GONE);
                    helper.getView(R.id.relative_mermber_title).setVisibility(View.GONE);
                    helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
                }
            }

            helper.setText(R.id.tv_mermber_1,item.getTaskName());

            if (item.getPointsType()==1){
                helper.setText(R.id.tv_mermber_2,"+"+item.getRewardsPoints()+"积分");
            }else{
                helper.setText(R.id.tv_mermber_2,item.getPointsTypeStr());
            }

            if (TextUtils.isEmpty(item.getButtonTxt())){
                helper.getView(R.id.tv_btn_text).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.tv_btn_text).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_btn_text,item.getButtonTxt());
                if (item.getIsDealOk()==1){
                    ((TextView) helper.getView(R.id.tv_btn_text)).setEnabled(false);
                    ((TextView) helper.getView(R.id.tv_btn_text)).setTextColor(getResources().getColor(R.color.black_aaaa));
                    ((TextView) helper.getView(R.id.tv_btn_text)).setBackgroundResource(R.drawable.btn_back_gray);
                }else {
                    ((TextView) helper.getView(R.id.tv_btn_text)).setEnabled(true);
                    ((TextView) helper.getView(R.id.tv_btn_text)).setTextColor(getResources().getColor(R.color.blue_1f80));
                    ((TextView) helper.getView(R.id.tv_btn_text)).setBackgroundResource(R.drawable.btn_background_border);
                }
            }

            //设置
            helper.getView(R.id.tv_btn_text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ToastUtil.ToastShort(mContext,""+item.getTaskId());

                    if (!item.isJumpFlag()){

                        jumpOpen(item.getTaskId());

                    }else {//跳转url
                        MemberCenterActivity.this.startActivity(new Intent(MemberCenterActivity.this, WebActivity.class).putExtra("url",item.getJumpMobileUrl()));
                    }

                }
            });

            if (lists!=null&&lists.size()==position+1)helper.getView(R.id.view_member_bottom).setVisibility(View.VISIBLE);
            else helper.getView(R.id.view_member_bottom).setVisibility(View.GONE);


            helper.getView(R.id.relative_mermber_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position==0){//每日任务

                        startActivity(new Intent(MemberCenterActivity.this,AwardTaskActivity.class).putExtra("index",1).
                                putExtra("value",centerDetailBean==null?"0":centerDetailBean.getPointValue()+"").
                                putExtra("mailUrl",urlMall));

                    }else{//成长任务

                        startActivity(new Intent(MemberCenterActivity.this,AwardTaskActivity.class).putExtra("index",0).
                                putExtra("value",centerDetailBean==null?"0":centerDetailBean.getPointValue()+"").
                                putExtra("mailUrl",urlMall));

                    }
                }
            });

        }
    }


    public void getTaskData(int type){
        Api.getClient().getGetMemberSingTask(Api.uid,type).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<MemberCenterBean>>(mContext) {
                              @Override
                              public void onSuccess(List<MemberCenterBean> memberCenterBeen) {
                                    if (type==1){
                                        if (lists!=null)lists.clear();
                                        lists.addAll(memberCenterBeen);
                                        getTaskData(0);
                                    }else{
                                        lists.addAll(memberCenterBeen);
                                        initAdapter();
                                    }
                              }
                          }
                );

    }


    public void getButtonData(){
            //Api.uid
        Api.getClient().getFindMemberPrivilege(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<MemberCenterPrivilegeBean>>(mContext) {
                              @Override
                              public void onSuccess(List<MemberCenterPrivilegeBean> memberCenterPrivilegeBeen) {
                                  addButton(memberCenterPrivilegeBeen);
                              }
                          }
                );
    }

    public void getMemberDetail(){

        Api.getClient().getLoadMemberByToken(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<MemberCenterDetailBean>(mContext) {
                              @Override
                              public void onSuccess(MemberCenterDetailBean bean) {
                                  initData(bean);
                              }
                          }
                );
    }

    /**
     * 获取商城url
     */
    public void getShopMallUrl(){
        Api.getClient().getMailUrl(SPUtils.geTinstance().getUid(),"").compose(RxApiThread.convert()).subscribe(new Subscriber<BaseResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResult<String> baseResult) {
                if (baseResult.getSuccess()){
                    urlMall = baseResult.getData();
                }else{

                }
            }
        });
    }


}
