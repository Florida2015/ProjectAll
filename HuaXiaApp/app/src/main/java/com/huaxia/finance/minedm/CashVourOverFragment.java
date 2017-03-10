package com.huaxia.finance.minedm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.pullrefresh.ui.PullToRefreshBase;
import com.framwork.pullrefresh.ui.PullToRefreshListView;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.adapter.CommonAdapter;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.BasePageModel;
import com.huaxia.finance.model.CashVoucherModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券 过期
 * Created by houwen.lai on 2016/2/22.
 */
public class CashVourOverFragment extends Fragment{

    List<CashVoucherModel> couponList ;
    CashVoucherAdapter cashVoucherAdapter;

    private TextView tv_empty_nolist;
    private PullToRefreshListView pull_listview_over;

    private int pageSize=0;
    private String mPageName;
    int mNum;

    private View view_add_warming;

    private boolean flagRefresh=false;


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }
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

        view_add_warming = View.inflate(getActivity(),R.layout.text_waming_invest,null);

        tv_empty_nolist = (TextView) getActivity().findViewById(R.id.tv_empty_over_nolist);
        pull_listview_over = (PullToRefreshListView) getActivity().findViewById(R.id.pull_listview_cashvour_over);
        pull_listview_over.setPullRefreshEnabled(true);
        pull_listview_over.setPullLoadEnabled(true);
        pull_listview_over.setScrollLoadEnabled(false);
        pull_listview_over.setHasMoreData(true);
        pull_listview_over.getRefreshableView().setDivider(null);
        pull_listview_over.getRefreshableView().setDividerHeight(0);
        pull_listview_over.getRefreshableView().setFastScrollEnabled(false);
        pull_listview_over.getRefreshableView().setVerticalScrollBarEnabled(false);
        pull_listview_over.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
                pull_listview_over.setLastUpdatedLabel(text);
                pageSize = 0;
                flagRefresh = true;
                getHttpRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                flagRefresh = false;
                getHttpRequest();
            }
        });

        pull_listview_over.doPullRefreshing(true, 100);

        cashVoucherAdapter = new CashVoucherAdapter(getActivity(), new ArrayList<CashVoucherModel>(),R.layout.coupon_item);
        pull_listview_over.getRefreshableView().addFooterView(view_add_warming);
        pull_listview_over.getRefreshableView().setAdapter(cashVoucherAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mPageName = String.format("fragment %d", mNum);

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
        LayoutInflater myInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = myInflater.inflate(R.layout.cash_vouch_over_fragment, container,
                false);
        return view;
    }

    public void setListDate(BasePageModel<CashVoucherModel> model){
        if (model==null)return;
        if (model.getList()==null)return;
        if (couponList==null)couponList=new ArrayList<CashVoucherModel>();
        if (pageSize==0){
            couponList.clear();
        }

        couponList.addAll(model.getList());

        if (cashVoucherAdapter==null) {
            cashVoucherAdapter = new CashVoucherAdapter(getActivity(), model.getList(),R.layout.coupon_item);
            pull_listview_over.getRefreshableView().addFooterView(view_add_warming);
            pull_listview_over.getRefreshableView().setAdapter(cashVoucherAdapter);
        } else {
            if (pageSize==0){
                cashVoucherAdapter.clearAdapterListData();
            }
            cashVoucherAdapter.setAdapterListData(-1, model.getList());
            cashVoucherAdapter.notifyDataSetChanged();
        }
        pageSize = pageSize+10;
        if (cashVoucherAdapter.getCount()==0){
            tv_empty_nolist.setVisibility(View.VISIBLE);
        }else{
            tv_empty_nolist.setVisibility(View.GONE);
        }
    }

    /**
     *根据用户id和优惠券状态获取优惠券
     */
    public void getHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("status", "2");//0可用 1不可用 2
        params.put("start",""+pageSize);//
        params.put("size","10");//
        String url = UrlConstants.urlBase+UrlConstants.urlFindCashVoucherByStatus;
        DMApplication.getInstance().getHttpClient(getActivity()).get(getActivity(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                if (flagRefresh)pull_listview_over.onPullDownRefreshComplete();
                else pull_listview_over.onPullUpRefreshComplete();
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<BasePageModel<CashVoucherModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<BasePageModel<CashVoucherModel>>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

                            setListDate(baseModel.getData());

                        } else {
                            if (cashVoucherAdapter!=null){
                                if (cashVoucherAdapter.getCount()==0){
                                    tv_empty_nolist.setVisibility(View.VISIBLE);
                                }
                            }else{
                                tv_empty_nolist.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                if (flagRefresh)pull_listview_over.onPullDownRefreshComplete();
                else pull_listview_over.onPullUpRefreshComplete();

            }
        });
    }


    /**
     * 优惠券适配器
     */
    private class CashVoucherAdapter extends CommonAdapter<CashVoucherModel> {

        public CashVoucherAdapter(Context context, List<CashVoucherModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, CashVoucherModel item) {
            //设置值
            TextView tv_title_coupon = helper.getView(R.id.tv_title_coupon);
            tv_title_coupon.setVisibility(View.GONE);

            TextView tv_coupon_title = helper.getView(R.id.tv_coupon_title);
            TextView tv_coupon_detail = helper.getView(R.id.tv_coupon_detail);
            TextView tv_over_time= helper.getView(R.id.tv_over_time);

            tv_coupon_title.setTextColor(getResources().getColor(R.color.black_d9d9));
            tv_coupon_detail.setTextColor(getResources().getColor(R.color.black_d9d9));
            tv_over_time.setTextColor(getResources().getColor(R.color.black_d9d9));
            helper.getView(R.id.img_cash_status).setVisibility(View.VISIBLE);
            helper.setImageResource(R.id.img_cash_status, R.drawable.tip_over);
            if (item.getGainType()==1){//1.收益率
                helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.payment_n_d);
            }else if(item.getGainType()==2){//2.本金券
                helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.gold_n_d);
            }else if(item.getGainType()==3){//3.抵扣券
                helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.allow_n_s);
            }else if(item.getGainType()==4){//4.加金券
                helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.add_n_d);
            }else if(item.getGainType()==5){//5.现金券
                helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.money_n_d);//
            }else{
                helper.getView(R.id.linear_coupon).setBackgroundResource(R.drawable.add_n_d);
            }

            tv_coupon_title.setText(item.getName());
            tv_coupon_detail.setText(item.getComments());
            tv_over_time.setText(DateUtils.getInstanse().getmstodated(item.getEndDate2(), DateUtils.YYYYMMDD)+"到期");

            helper.getView(R.id.view_line).setVisibility(View.GONE);

        }
    }

}
