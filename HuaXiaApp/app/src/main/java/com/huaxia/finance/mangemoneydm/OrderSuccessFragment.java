package com.huaxia.finance.mangemoneydm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
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
import com.huaxia.finance.model.OrderDetailModel;
import com.huaxia.finance.model.OrderModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * 成功订单
 * Created by houwen.lai on 2016/2/2.
 */
public class OrderSuccessFragment extends Fragment {

    private PullToRefreshListView pull_listview_product;
    private TextView img_empty;

    private int pageSize=0;

    private String mPageName;
    int mNum;

    private View view_add_warming;

    private OrderListAdapter orderListAdapter;
    private List<OrderDetailModel> orderDetailModelList;
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

        img_empty =  (TextView) getActivity().findViewById(R.id.tv_empty_success);
        pull_listview_product= (PullToRefreshListView) getActivity().findViewById(R.id.pull_listview_success);
        pull_listview_product.setPullRefreshEnabled(true);
        pull_listview_product.setPullLoadEnabled(true);
        pull_listview_product.setScrollLoadEnabled(false);
        pull_listview_product.setHasMoreData(true);
        pull_listview_product.getRefreshableView().setFastScrollEnabled(false);
        pull_listview_product.getRefreshableView().setDivider(null);
        pull_listview_product.getRefreshableView().setDividerHeight(0);
        pull_listview_product.getRefreshableView().setVerticalScrollBarEnabled(false);
        pull_listview_product.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
                pull_listview_product.setLastUpdatedLabel(text);
                pageSize = 0;
                flagRefresh=true;
                setOrderListHttpRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                flagRefresh=false;
                setOrderListHttpRequest();
            }
        });
//        pull_listview_product.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //进入订单详情
//                if (NetWorkUtils.isNetworkConnected(getActivity()))
//                getActivity().startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("orderId", orderListAdapter.getItem(position).getOrderId()));
//                else ToastUtils.showShort("网络不给力");
//            }
//        });
//        setOrderListHttpRequest();
        pull_listview_product.doPullRefreshing(true,100);
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
        View view = myInflater.inflate(R.layout.fragment_order_succes, container,
                false);
        return view;
    }

    public void setListDate(OrderModel model){
        if (model==null)return;
        if (model.getOrders()==null)return;
        if (orderDetailModelList==null)orderDetailModelList=new ArrayList<OrderDetailModel>();
        if (pageSize==0){
            orderDetailModelList.clear();
        }
        orderDetailModelList.addAll(model.getOrders());

        if (orderListAdapter==null) {
            orderListAdapter = new OrderListAdapter(getActivity(), model.getOrders(),R.layout.order_item);
            pull_listview_product.getRefreshableView().addFooterView(view_add_warming);
            pull_listview_product.getRefreshableView().setAdapter(orderListAdapter);
        } else {
            if (pageSize==0){
                orderListAdapter.clearAdapterListData();
            }
            orderListAdapter.setAdapterListData(-1, model.getOrders());
            orderListAdapter.notifyDataSetChanged();
        }
        pageSize = pageSize+10;
        if(orderListAdapter.getCount()==0){
            img_empty.setVisibility(View.VISIBLE);
            Drawable drawable= getResources().getDrawable(R.drawable.icon_empty_a);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            img_empty.setCompoundDrawables(null, drawable, null, null);
            img_empty.setText(getResources().getString(R.string.text_empty_order));
        }else{
            img_empty.setVisibility(View.GONE);
        }
    }

    public void setOrderListHttpRequest(){
        final BaseRequestParams params = new BaseRequestParams();
        params.put("status","20+30+40+50+70");//
        params.put("start",""+pageSize);//
        params.put("size","10");//
        String url = UrlConstants.urlBase+UrlConstants.urlOrderGetOrders;
        MyLog.d("api_order_list=" + url + "_status=20+30+40+50+70");
        DMApplication.getInstance().getHttpClient(getActivity()).get(getActivity(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                if (flagRefresh) pull_listview_product.onPullDownRefreshComplete();
                else pull_listview_product.onPullUpRefreshComplete();
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<OrderModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<OrderModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

                            setListDate(baseModel.getData());

                        } else {
                            ToastUtils.showShort(baseModel.getMsg());
                            if(orderListAdapter!=null){
                                if ( orderListAdapter.getCount()==0){
                                    img_empty.setVisibility(View.VISIBLE);
                                    Drawable drawable= getResources().getDrawable(R.drawable.icon_empty_a);
                                    // 这一步必须要做,否则不会显示.
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    img_empty.setCompoundDrawables(null, drawable, null, null);
                                    img_empty.setText(getResources().getString(R.string.text_empty_order));
                                }else img_empty.setVisibility(View.GONE);
                            }else{
                                img_empty.setVisibility(View.VISIBLE);
                                Drawable drawable= getResources().getDrawable(R.drawable.icon_empty_a);
                                // 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                img_empty.setCompoundDrawables(null, drawable, null, null);
                                img_empty.setText(getResources().getString(R.string.text_empty_order));
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                if (flagRefresh) pull_listview_product.onPullDownRefreshComplete();
                else pull_listview_product.onPullUpRefreshComplete();
                if(orderListAdapter!=null){
                    if ( orderListAdapter.getCount()==0){
                        img_empty.setVisibility(View.VISIBLE);
                        Drawable drawable= getActivity().getResources().getDrawable(R.drawable.icon_no_wifi);
                        // 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        img_empty.setCompoundDrawables(null, drawable, null, null);
                        img_empty.setText("");
                    }else img_empty.setVisibility(View.GONE);
                }else{
                    img_empty.setVisibility(View.VISIBLE);
                    Drawable drawable= getResources().getDrawable(R.drawable.icon_no_wifi);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    img_empty.setCompoundDrawables(null, drawable, null, null);
                    img_empty.setText("");
                }
            }
        });
    }
    /**
     * 订单适配器
     */
    private class OrderListAdapter extends CommonAdapter<OrderDetailModel> {

        public OrderListAdapter(Context context, List<OrderDetailModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, final OrderDetailModel item) {
            //设置值
            TextView tv_tip_type = helper.getView(R.id.tv_tip_type);
            if (item.getOrderStatus().equals("20")){//支付成功
                tv_tip_type.setText("支\n付\n成\n功");
                tv_tip_type.setBackgroundResource(R.drawable.tip_pay_success);
            }else if(item.getOrderStatus().equals("30")){
                tv_tip_type.setText("计\n息\n中");
                tv_tip_type.setBackgroundResource(R.drawable.tip_pay_rate);
            }else if(item.getOrderStatus().equals("50")){
                tv_tip_type.setText("还\n款\n中");
                tv_tip_type.setBackgroundResource(R.drawable.tip_pay_refund);
            }

            helper.setText(R.id.tv_product_name, new StringBuffer("[ ").append(item.getProductName()).append(" ] 第").append(item.getProductNum()).append("期").toString());
            helper.setText(R.id.tv_product_time, DateUtils.getInstanse().getmstodated(item.getCreateTime(), DateUtils.YYYYMMDDHHMM));
            helper.setText(R.id.tv_product_money, new StringBuffer("投资金额：").append(item.getAgreementAmount()).append("元\n").append("收益金额：").append(DateUtils.getInstanse().getTwo(item.getTotalIncome() + item.getAmount())).append("元").toString());
            if (item.getAwardRate()>0){
                helper.setText(R.id.tv_income_time, new StringBuffer("收益率：").append(DateUtils.getInstanse().getTwo(item.getYield())).append("%+").append(DateUtils.getInstanse().getTwo(item.getAwardRate())).append("%\n").append("到期日：").append(DateUtils.getInstanse().getmstodated(item.getExpiryDate(),DateUtils.YYYYMMDD)).toString());
            }else{
                helper.setText(R.id.tv_income_time, new StringBuffer("收益率：").append(DateUtils.getInstanse().getTwo(item.getYield())).append("%\n").append("到期日：").append(DateUtils.getInstanse().getmstodated(item.getExpiryDate(),DateUtils.YYYYMMDD)).toString());
            }

            RelativeLayout relative_success_item = helper.getView(R.id.relative_success_item);
            relative_success_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入详情
//进入订单详情
                    if (NetWorkUtils.isNetworkConnected(getActivity()))
                        getActivity().startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("orderId", item.getOrderId()));
                    else ToastUtils.showShort("网络不给力");

                }
            });

            TextView tv_continu_money = helper.getView(R.id.tv_continu_money);
            tv_continu_money.setVisibility(View.GONE);
            LinearLayout linearlayout_award_next = helper.getView(R.id.linearlayout_award_next);
            linearlayout_award_next.setVisibility(View.GONE);
            TextView tv_continu_text = helper.getView(R.id.tv_continu_text);
            TextView tv_award_next = helper.getView(R.id.tv_award_next);
            linearlayout_award_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //续投
                    if (NetWorkUtils.isNetworkConnected(getActivity()))
                        getActivity().startActivity(new Intent(getActivity(), ContinueOrderActivity.class).putExtra("model", item));
                    else ToastUtils.showShort("网络不给力");


                }
            });

            tv_award_next.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
            tv_award_next.setText("点击查看");
            //判断
            if (item.getIsConInvest()==1){
                linearlayout_award_next.setVisibility(View.GONE);
                tv_continu_money.setVisibility(View.VISIBLE);
            }else if(item.getIsConInvest()==0){
                linearlayout_award_next.setVisibility(View.GONE);
                tv_continu_money.setVisibility(View.GONE);
            }else {
                tv_continu_money.setVisibility(View.GONE);
                if (item.getProductStyle()==1&&(item.getOrderStatus().equals("20")||item.getOrderStatus().equals("30"))){
                    linearlayout_award_next.setVisibility(View.VISIBLE);
                }else  linearlayout_award_next.setVisibility(View.GONE);
            }
            linearlayout_award_next.setVisibility(View.GONE);
            tv_continu_money.setVisibility(View.GONE);
        }
    }
}
