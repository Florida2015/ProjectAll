package com.huaxia.finance.mangemoneydm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
 * 处理中 订单
 * Created by houwen.lai on 2016/2/2.
 */
public class OrderDeailWithFragment extends Fragment {

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

//        pull_listview_product= (PullToRefreshListView) getActivity().findViewById(R.id.pull_listview_deail);
//        pull_listview_product.setPullRefreshEnabled(true);
//        pull_listview_product.setPullLoadEnabled(true);
//        pull_listview_product.setScrollLoadEnabled(false);
//        pull_listview_product.setHasMoreData(true);
//        pull_listview_product.getRefreshableView().setFastScrollEnabled(false);
//        pull_listview_product.getRefreshableView().setDivider(null);
//        pull_listview_product.getRefreshableView().setDividerHeight(0);
//        pull_listview_product.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                //下拉刷新
//                String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
//                pull_listview_product.setLastUpdatedLabel(text);
//                pageSize = 0;
//                flagRefresh=true;
//                setOrderListHttpRequest();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                //加载更多
//                flagRefresh=false;
//                setOrderListHttpRequest();
//            }
//        });
//        pull_listview_product.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //进入订单详情
//
//                getActivity().startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("orderId", orderListAdapter.getItem(position).getOrderId()));
//            }
//        });
////        setOrderListHttpRequest();
//        pull_listview_product.doPullRefreshing(true,100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mPageName = String.format("fragment %d", mNum);

    }

    @Override
    public void onStart() {
        super.onStart();

        view_add_warming = View.inflate(getActivity(),R.layout.text_waming_invest,null);

        img_empty =  (TextView) getActivity().findViewById(R.id.tv_empty_deail);
        pull_listview_product= (PullToRefreshListView) getActivity().findViewById(R.id.pull_listview_deail);
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
        pull_listview_product.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入订单详情
                if (NetWorkUtils.isNetworkConnected(getActivity()))
                getActivity().startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("orderId", orderListAdapter.getItem(position).getOrderId()));
                else ToastUtils.showShort("网络不给力");
            }
        });
//        setOrderListHttpRequest();
        pull_listview_product.doPullRefreshing(true,100);
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
        View view = myInflater.inflate(R.layout.fragment_order_deailing, container,
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
            orderListAdapter = new OrderListAdapter(getActivity(),model.getOrders(),R.layout.order_item);
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
        if (orderListAdapter.getCount()==0){
            Drawable drawable= getResources().getDrawable(R.drawable.icon_empty_a);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            img_empty.setCompoundDrawables(null,drawable, null, null);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setText(getResources().getString(R.string.text_empty_order));
        }else{
            img_empty.setVisibility(View.GONE);
            img_empty.setText(getResources().getString(R.string.text_empty_order));
        }
    }

    public void setOrderListHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("status","10");//
        params.put("start",""+pageSize);//
        params.put("size","10");//
        String url = UrlConstants.urlBase+UrlConstants.urlOrderGetOrders;
        MyLog.d("api_order_list=" + url + "_status=10");
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
                            if (orderListAdapter !=null ){
                                if (orderListAdapter.getCount()==0){
                                    Drawable drawable= getResources().getDrawable(R.drawable.icon_empty_a);
                                    // 这一步必须要做,否则不会显示.
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    img_empty.setCompoundDrawables(null, drawable, null, null);
                                    img_empty.setVisibility(View.VISIBLE);
                                    img_empty.setText(getResources().getString(R.string.text_empty_order));
                                }
                            }else{
                                Drawable drawable= getResources().getDrawable(R.drawable.icon_empty_a);
                                // 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                img_empty.setCompoundDrawables(null, drawable, null, null);
                                img_empty.setVisibility(View.VISIBLE);
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
                if (orderListAdapter !=null ){
                    if (orderListAdapter.getCount()==0){
                        Drawable drawable= getResources().getDrawable(R.drawable.icon_no_wifi);
                        // 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        img_empty.setCompoundDrawables(null, drawable, null, null);
                        img_empty.setVisibility(View.VISIBLE);
                        img_empty.setText("");
                    }
                }else{
                    Drawable drawable= getResources().getDrawable(R.drawable.icon_no_wifi);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    img_empty.setCompoundDrawables(null, drawable, null, null);
                    img_empty.setVisibility(View.VISIBLE);
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
        public void convert(ViewHolder helper, OrderDetailModel item) {
            //设置值
            TextView tv_tip_type = helper.getView(R.id.tv_tip_type);
            if(item.getOrderStatus().equals("10")){
                tv_tip_type.setText("处\n理\n中");
                tv_tip_type.setBackgroundResource(R.drawable.tip_pay_dealing);
            }else tv_tip_type.setVisibility(View.GONE);

            helper.setText(R.id.tv_product_name, new StringBuffer("[ ").append(item.getProductName()).append(" ] 第").append(item.getProductNum()).append("期").toString());
            helper.setText(R.id.tv_product_time, DateUtils.getInstanse().getmstodated(item.getCreateTime(), DateUtils.YYYYMMDDHHMM));
            helper.setText(R.id.tv_product_money, new StringBuffer("投资金额：").append(item.getAgreementAmount()).append("元\n").append("收益金额：").append(DateUtils.getInstanse().getTwo(item.getTotalIncome() + item.getAmount())).append("元").toString());
            if (item.getAwardRate()>0)
            helper.setText(R.id.tv_income_time, new StringBuffer("收益率：").append(DateUtils.getInstanse().getTwo(item.getYield())).append("%+").append(DateUtils.getInstanse().getTwo(item.getAwardRate())).append("%\n").append("到期日：").append(DateUtils.getInstanse().getmstodated(item.getExpiryDate(),DateUtils.YYYYMMDD)).toString());
            else helper.setText(R.id.tv_income_time, new StringBuffer("收益率：").append(DateUtils.getInstanse().getTwo(item.getYield())).append("%\n").append("到期日：").append(DateUtils.getInstanse().getmstodated(item.getExpiryDate(),DateUtils.YYYYMMDD)).toString());

            TextView tv_continu_money = helper.getView(R.id.tv_continu_money);
            tv_continu_money.setVisibility(View.GONE);
            LinearLayout linearlayout_award_next = helper.getView(R.id.linearlayout_award_next);
            linearlayout_award_next.setVisibility(View.GONE);
        }
    }

}
