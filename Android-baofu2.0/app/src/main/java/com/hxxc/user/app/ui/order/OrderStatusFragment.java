package com.hxxc.user.app.ui.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.data.bean.ContractBillNewBean;
import com.hxxc.user.app.data.bean.OrderDetailBean;
import com.hxxc.user.app.data.bean.OrderDetailStatusBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.mine.web.WebActivity;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/10/26.
 * 订单详情 状态
 */

public class OrderStatusFragment extends Fragment {

    public static OrderStatusFragment newInstance(OrderDetailBean beans) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("beans",beans);
        OrderStatusFragment fragment = new OrderStatusFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

//    @BindView(R.id.list_order_status)
    ListView listView;

    List<OrderDetailStatusBean> lists = new ArrayList<OrderDetailStatusBean>();
    OrderStatusAdpter orderStatusAdpter;

    private View mView;

    private String cridet_url;
    private String contract_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.order_status_fragment, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) mView.findViewById(R.id.list_order_status);

//        lists = ((OrderDetailBean)getArguments().getSerializable("beans")).getOrderStatusMessages();
//
//        orderStatusAdpter = new OrderStatusAdpter(getActivity(),lists,R.layout.order_status_item);
//        listView.setAdapter(orderStatusAdpter);
    }

    public void setDetailData(OrderDetailBean listss){
        this.contract_url = listss.getContractUrl();
        if (lists==null)lists=new ArrayList<OrderDetailStatusBean>();
        lists.clear();
        lists.addAll(listss.getOrderStatusMessages());
        listView.setDividerHeight(0);
        orderStatusAdpter = new OrderStatusAdpter(getActivity(),lists,R.layout.order_status_item);
        listView.setAdapter(orderStatusAdpter);
    }

    public void setCridet_url(String url){
        this.cridet_url = url;
        if (lists==null)return;
        if (orderStatusAdpter==null)return;
        if (listView==null)return;
        orderStatusAdpter.notifyDataSetChanged();
    }

    /**
     *
     */
    public class OrderStatusAdpter extends CommonAdapter<OrderDetailStatusBean> {

        private int hight = 0;
        private int hight_top = 0;

        public OrderStatusAdpter(Context context, List<OrderDetailStatusBean> list, int layoutId) {
            super(context, list, layoutId);


        }

        @Override
        public void convert(ViewHolder helper, OrderDetailStatusBean item, int position) {
            super.convert(helper, item, position);

            if (position%2==0)helper.getView(R.id.relative_lend_status).setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            else helper.getView(R.id.relative_lend_status).setBackgroundColor(getActivity().getResources().getColor(R.color.white_fafa));

            if(item.getCurrStatus()==1){
                helper.getView(R.id.view_lend_dot).setBackgroundResource(R.drawable.organ_lend_detail_item);
            }else if (item.getStatus()==1){//
                helper.getView(R.id.view_lend_dot).setBackgroundResource(R.drawable.blue_lend_detail_item);
            }else {
                helper.getView(R.id.view_lend_dot).setBackgroundResource(R.drawable.blue_store);
            }

            helper.setText(R.id.tv_lend_status_name,item.getTitles());
//            helper.setText(R.id.tv_lend_status_data, DateUtil.getmstodate(item.getCreateTime(),DateUtil.YYYYMMDDHHMMSS));
            helper.setText(R.id.tv_lend_status_data, item.getTime());
            helper.setText(R.id.tv_lend_status_detail,item.getMessage());

            float size = ((TextView) helper.getView(R.id.tv_lend_status_name)).getTextSize();
            LogUtil.d("text size="+ size);//小24,中28,大 32 ,特大 36,超大

            LogUtil.d("item hight ="+ helper.getView(R.id.relative_lend_status).getMeasuredHeight());

            if (!TextUtils.isEmpty(item.getAction())){
                ViewGroup.LayoutParams params=helper.getView(R.id.view_lend_lin_2).getLayoutParams();
                if (size<=30)
                    params.height = 158;//relative_lend_status
                else  params.height = 206;
                helper.getView(R.id.view_lend_lin_2).setLayoutParams(params);

                helper.getView(R.id.btn_order_status).setVisibility(View.VISIBLE);
                helper.setText(R.id.btn_order_status,item.getAction());
            } else {
                ViewGroup.LayoutParams params=helper.getView(R.id.view_lend_lin_2).getLayoutParams();
                if (size<=30)
                    params.height = 98;//relative_lend_status
                else  params.height = 136;
                helper.getView(R.id.view_lend_lin_2).setLayoutParams(params);

                helper.getView(R.id.view_lend_lin_2).setVisibility(View.VISIBLE);
                helper.getView(R.id.btn_order_status).setVisibility(View.GONE);
            }

            if (position==0) {// 第一个 item
                helper.getView(R.id.view_lend_line).setVisibility(View.INVISIBLE);
                helper.getView(R.id.view_lend_lin_2).setVisibility(View.VISIBLE);
            }else if(lists!=null&&position+1==lists.size()){
                helper.getView(R.id.view_lend_line).setVisibility(View.VISIBLE);
                helper.getView(R.id.view_lend_lin_2).setVisibility(View.GONE);
                ViewGroup.LayoutParams params=helper.getView(R.id.view_lend_lin_2).getLayoutParams();
                params.height = 98;
                helper.getView(R.id.view_lend_lin_2).setLayoutParams(params);
            }else {
                helper.getView(R.id.view_lend_line).setVisibility(View.VISIBLE);
                helper.getView(R.id.view_lend_lin_2).setVisibility(View.VISIBLE);
            }

            //
            if (!TextUtils.isEmpty(item.getKeyType())&&item.getKeyType().contains("ordeinfo")) {//查看订单
                helper.getView(R.id.btn_order_status).setVisibility(View.GONE);
            }else if(!TextUtils.isEmpty(item.getKeyType())&&item.getKeyType().contains("contract")){//查看合同
                if (!TextUtils.isEmpty(contract_url)){
                    helper.setText(R.id.btn_order_status,TextUtils.isEmpty(item.getAction())?"":item.getAction());
                    helper.getView(R.id.btn_order_status).setEnabled(true);
                    ((TextView)helper.getView(R.id.btn_order_status)).setTextColor(getActivity().getResources().getColor(R.color.blue_1f80));
                }else{
                    helper.setText(R.id.btn_order_status,"合同处理中");
                    helper.getView(R.id.btn_order_status).setEnabled(false);
                    ((TextView)helper.getView(R.id.btn_order_status)).setTextColor(getActivity().getResources().getColor(R.color.black_aaaa));
                }
            }else if(!TextUtils.isEmpty(item.getKeyType())&&item.getKeyType().contains("credit")){//查看债权
                if (!TextUtils.isEmpty(cridet_url)) {
                    helper.setText(R.id.btn_order_status,TextUtils.isEmpty(item.getAction())?"":item.getAction());
                    helper.getView(R.id.btn_order_status).setEnabled(true);
                    ((TextView)helper.getView(R.id.btn_order_status)).setTextColor(getActivity().getResources().getColor(R.color.blue_1f80));
                }else {
                    helper.setText(R.id.btn_order_status,"债权处理中");
                    helper.getView(R.id.btn_order_status).setEnabled(false);
                    ((TextView)helper.getView(R.id.btn_order_status)).setTextColor(getActivity().getResources().getColor(R.color.black_aaaa));
                }
            }else if(!TextUtils.isEmpty(item.getKeyType())&&item.getKeyType().contains("securityDdeposit")){//风险备用金保障
                helper.setText(R.id.btn_order_status,TextUtils.isEmpty(item.getAction())?"":item.getAction());
                helper.getView(R.id.btn_order_status).setEnabled(true);
                ((TextView)helper.getView(R.id.btn_order_status)).setTextColor(getActivity().getResources().getColor(R.color.blue_1f80));
            }else{
                helper.getView(R.id.btn_order_status).setVisibility(View.GONE);
            }

            helper.getView(R.id.btn_order_status).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ToastUtil.ToastShort(mContext,item.getAction());
                    if (item.getKeyType().contains("contract")){//查看合同
                        if (!TextUtils.isEmpty(contract_url))  mContext.startActivity(new Intent(mContext, WebActivity.class).putExtra("title", UserInfoConfig.Contract).
                            putExtra("url", contract_url).putExtra("flag",true));//HttpRequest.baseUrl_credit+

                    }else if(item.getKeyType().contains("credit")){//查看债权
                        if (!TextUtils.isEmpty(cridet_url))
                            getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", UserInfoConfig.Creditor).
                                    putExtra("url",cridet_url));

                    }else if(item.getKeyType().contains("account")){//查看账户
//                mContext.startActivity(new Intent(mContext,));
                    }else if(item.getKeyType().contains("securityDdeposit")){//风险备用金保障
                        if (!TextUtils.isEmpty(item.getKeyword()))  mContext.startActivity(new Intent(mContext, WebActivity.class).putExtra("title","风险备用金保障").
                                putExtra("url", item.getKeyword()).putExtra("flag",false));//

                    }
                }
            });

        }
    }


}
