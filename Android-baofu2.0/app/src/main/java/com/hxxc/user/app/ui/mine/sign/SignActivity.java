package com.hxxc.user.app.ui.mine.sign;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.data.bean.SignCalBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.dialogfragment.SignDialogListener;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by houwen.lai on 2016/10/27.
 * 我的 签到
 */

public class SignActivity extends BaseRxActivity implements SignDialogListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_share)
    ImageButton toolbar_share;//?

    @BindView(R.id.tv_sign_title)
    TextView tv_sign_title;
    @BindView(R.id.tv_sign_data)
    TextView tv_sign_data;

    @BindView(R.id.tv_sign_month)
    TextView  tv_sign_month;

    @BindView(R.id.grid_view_sign)
    GridView grid_view_sign;

    @Override
    public int getLayoutId() {
        return R.layout.mine_sign;
    }

    @Override
    public void initView() {
        toolbar_title.setText("签到");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar_share.setImageResource(R.mipmap.ico_doubt);
        toolbar_share.setVisibility(View.VISIBLE);

        initData();

        requestDataSignin();

        requestDataMonth();

        requestData();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.toolbar_share})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_share://问题
                if (BtnUtils.isFastDoubleClick()){
                    DialogSignRuleFragment signRuleFragment = new DialogSignRuleFragment().newInstance(HttpRequest.indexBaseUrl+HttpRequest.httpUrlSign,true);
                    signRuleFragment.show(getFragmentManager(), "DialogSignRuleFragment");
                }
                break;
        }
    }

    private void initData() {
        final SpannableStringBuilder sp = new SpannableStringBuilder("签到！赚积分，兑豪礼");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_fbb0)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue_1f80)), 3, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(15, true), 0, sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_sign_title.setText(sp);

        tv_sign_data.setText("今日已签到,已连续签到0天");
    }


    SignAdapter signAdapter;
    List<SignCalBean> lists;

    public void inintData(List<Map<String, SignCalBean>> mapList){
        if (lists==null)lists = new ArrayList<>();
        if (mapList==null)return;
        lists.clear();
        for (int i=0;i<mapList.size();i++){
            lists.add(mapList.get(i).get("0"));
            lists.add(mapList.get(i).get("1"));
            lists.add(mapList.get(i).get("2"));
            lists.add(mapList.get(i).get("3"));
            lists.add(mapList.get(i).get("4"));
            lists.add(mapList.get(i).get("5"));
            lists.add(mapList.get(i).get("6"));
        }

        LogUtil.d("签到 -lists="+lists.toString());
        signAdapter = new SignAdapter(mContext,lists,R.layout.mine_sign_item);
        grid_view_sign.setAdapter(signAdapter);

    }

    @Override
    public void onSignId(int resId) {
        requestDataSignin();
    }

    /**
     *
     */
    public class SignAdapter extends CommonAdapter<SignCalBean>{


        public SignAdapter(Context context, List<SignCalBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, SignCalBean item, int position) {
            super.convert(helper, item, position);
            TextView tv_sign_month = (TextView) helper.getView(R.id.tv_sign_month);
            ImageView img_sign = (ImageView) helper.getView(R.id.img_sign);
            if (item==null){
                tv_sign_month.setText("");
                tv_sign_month.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                img_sign.setVisibility(View.GONE);
                return;
            }
            tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.black_3333));
            tv_sign_month.setText(""+item.getCalDay());

            if (item.isSiginIn()){
                tv_sign_month.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                img_sign.setVisibility(View.VISIBLE);
                img_sign.setImageResource(R.mipmap.tip_sign_correctly);
            }else if (item.isToday()){
                tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_sign_month.setBackgroundResource(R.mipmap.tip_sign_now);
                img_sign.setVisibility(View.VISIBLE);
                img_sign.setImageResource(R.mipmap.tip_sign_correctly);
            }else  if (item.getIsExcessRewards()==1){
                tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_sign_month.setText("奖励");
                tv_sign_month.setBackgroundResource(R.mipmap.tip_sign_reward);
                img_sign.setVisibility(View.GONE);
            }else{
                tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.black_3333));
                tv_sign_month.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                img_sign.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 签到
     */
    public void requestDataSignin() {
        Api.getClient().getMemberUserSignIn(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<SignCalBean>(mContext) {
                              @Override
                              public void onSuccess(SignCalBean signCalBean) {
                                  tv_sign_data.setText("今日已签到,已连续签到"+signCalBean.getConDatys()+"天");
                                  if (!signCalBean.isSiginIn()) {
                                      DialogSignFragment dialogSignFragment = new DialogSignFragment().newInstance(signCalBean.getPoints() + "",
                                              signCalBean.getLeftConDatys() + "", signCalBean.getRewardPoints() + "");
                                      dialogSignFragment.show(SignActivity.this.getFragmentManager(), "updateAppDialogFragment");
                                  }
                              }
                          }
                );

    }

    /**
     * 获取月份
     */
    public void requestDataMonth() {
        Map<String, String> params = new HashMap<>();
        params.put("token","aaaa");
        Api.getClient().getMemberNowMonth(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<String>(mContext) {
                    @Override
                    public void onSuccess(String s) {
                        tv_sign_month.setText(s);
                    }
                }
        );
    }

    /**
     * 获取签到时间
     */
    public void requestData(){
        Map<String, String> params = new HashMap<>();
        params.put("token",Api.token);
        params.put("uid", Api.uid);
        Api.getClient().getMemberUserSignListForMonth(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<Map<String, SignCalBean>>>(mContext) {
                              @Override
                              public void onSuccess(List<Map<String, SignCalBean>> mapList) {
                                  inintData(mapList);
                              }
                          }
                );
    }

}
