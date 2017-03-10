package com.hxxc.user.app.ui.order;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.data.bean.ContractBillNewBean;
import com.hxxc.user.app.data.bean.OrderDetailBean;
import com.hxxc.user.app.data.bean.OrderDetailLoanDetailsBean;
import com.hxxc.user.app.data.bean.OrderDoubleItemBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.mine.web.WebActivity;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/10/26.
 * 订单详情 详情
 */

public class OrderDetailFragment extends Fragment {

    public static OrderDetailFragment newInstance(OrderDetailLoanDetailsBean bean) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("bean",bean);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private View mView;

    private String cridet_url;
    private String contract_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.order_status_fragment, container, false);
//        ButterKnife.bind(getActivity(),mView);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list_lend_status = (ListView) mView.findViewById(R.id.list_order_status);
//        bean = (OrderDetailLoanDetailsBean) getArguments().getSerializable("bean");
        list_lend_status.setDividerHeight(0);

    }

    OrderDetailBean detailBean;
    public void setDetailData(OrderDetailBean listss){//OrderDetailBean listss
        this.contract_url = listss.getContractUrl();
        detailBean=listss;
        if (list==null)list = new ArrayList<OrderDoubleItemBean>();
        list.clear();
        bean =listss.getOrderDefaultInfo();
        for (int i=0;i<names.length;i++){
            OrderDoubleItemBean bean = new OrderDoubleItemBean();
            bean.setName(names[i]);
            bean.setValue(getValues(i));
            list.add(bean);
        }

        lendDetailAdapter = new LendDetailAdapter(getActivity(),list,R.layout.order_detail_item);
        list_lend_status.setAdapter(lendDetailAdapter);
    }

    public void setCridetUrl(String url){
        this.cridet_url = url;
        if (list==null)return;
        if (lendDetailAdapter==null)return;

        list.get(names.length-2).setValue(url);
        lendDetailAdapter.notifyDataSetChanged();
    }

//    @BindView(R.id.list_order_status)
    ListView list_lend_status;

    List<OrderDoubleItemBean> list;
    LendDetailAdapter lendDetailAdapter;

    OrderDetailLoanDetailsBean bean;
//,"财富顾问"
    String[] names = new String[]{"订单编号","订单名称","年化收益","出借期限","购买金额","红包抵扣",
            "实付金额","预期收益","收益方式","订单时间","到期时间","订单状态","债权明细","订单合同"};
//    String[] values = new String[]{"hxx000","10.5%","12个月","10,000元","2,000.50元","等额本息","xxx","2016-8-9 14:03:55"};
    public class LendDetailAdapter extends CommonAdapter<OrderDoubleItemBean> {


        public LendDetailAdapter(Context context, List<OrderDoubleItemBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, OrderDoubleItemBean item, int position) {
            super.convert(helper, item, position);
            RelativeLayout relative_lend_detail = helper.getView(R.id.relative_lend_detail);
            if (position%2==0)relative_lend_detail.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            else relative_lend_detail.setBackgroundColor(getActivity().getResources().getColor(R.color.white_fafa));

            helper.setText(R.id.tv_order_detail_item_name,item.getName());
            helper.setText(R.id.tv_order_detail_item_value,item.getValue());

            if (list!=null&&(position==list.size()-1)){//合同
                if (!TextUtils.isEmpty(contract_url)){
                    helper.setText(R.id.tv_order_detail_item_value,"查看合同");
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setEnabled(true);
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setTextColor(mContext.getResources().getColor(R.color.orange_fbb0));
                }else{
                    helper.setText(R.id.tv_order_detail_item_value,"处理中");
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setEnabled(false);
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
                }
            }else if(list!=null&&position==list.size()-2){//债权
                if (!TextUtils.isEmpty(item.getValue())){
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setEnabled(true);
                    helper.setText(R.id.tv_order_detail_item_value,"查看债权");
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setTextColor(mContext.getResources().getColor(R.color.orange_fbb0));
                }else{
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setEnabled(false);
                    helper.setText(R.id.tv_order_detail_item_value,"处理中");
                    ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
                }
            }else if(list!=null&&position==list.size()-3){//订单状态
                ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setTextColor(mContext.getResources().getColor(R.color.orange_fbb0));
            }else ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setTextColor(mContext.getResources().getColor(R.color.black_aaaa));

            ((TextView)helper.getView(R.id.tv_order_detail_item_value)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position==list.size()-1){//订单合同
                        if(!TextUtils.isEmpty(contract_url))
                            mContext.startActivity(new Intent(mContext, WebActivity.class).putExtra("title",UserInfoConfig.Contract).
                                    putExtra("url",contract_url).putExtra("flag",true));//HttpRequest.baseUrl_credit+bean.getContract()

                    }else if (position==list.size()-2){//债券
                        if(!TextUtils.isEmpty(cridet_url))
                            getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", UserInfoConfig.Creditor).
                                    putExtra("url",cridet_url));

                    }
                }
            });
        }
    }

    private String getValues(int index){
        if (bean==null)return "";
        StringBuffer temp=new StringBuffer();
        switch (index) {
            case 0:
                return bean.getOrderNo();
            case 1:
               return bean.getProductName();
            case 2:
//                return DateUtil.getInstance().getOne(100*bean.getYearRate())+"%";
                return bean.getYearRateText();
            case 3:
                return bean.getPeriods()+"个月";
            case 4:
                return MoneyUtil.addComma(bean.getMoney(),2,ROUND_FLOOR)+"元";
            case 5://红包抵扣 bean.getUserRewardType()  1.加息券 2.现金券（红包） 3.现金
                if (!TextUtils.isEmpty(bean.getUserRewardType())&&(bean.getUserRewardType().equals("3")||bean.getUserRewardType().equals("2")))
                return MoneyUtil.addComma(bean.getUserRewardMoney(),2,ROUND_FLOOR)+"元";
                else return "0.00元";
            case 6:
                return MoneyUtil.addComma(bean.getPayMoney(),2,ROUND_FLOOR)+"元";
            case 7:
                return MoneyUtil.addComma(bean.getExpectProfit(),2,ROUND_FLOOR)+"元";
            case 8://收益方式
                return bean.getInterestTypeStr();
            case 9://
                return DateUtil.getmstodate(bean.getSignTime(),DateUtil.YYYYMMDDHHMMSS);
            case 10://
                return DateUtil.getmstodate(bean.getExpireTime(),DateUtil.YYYYMMDDHHMMSS);
            case 11:
                return detailBean==null?"":detailBean.getOrderStatusText();
            case 12:
                return "";//债权
            case 13:
                if (TextUtils.isEmpty(contract_url))return "处理中";
                else return "查看合同";
            default:
                break;
        }
        return "";
    }

}
