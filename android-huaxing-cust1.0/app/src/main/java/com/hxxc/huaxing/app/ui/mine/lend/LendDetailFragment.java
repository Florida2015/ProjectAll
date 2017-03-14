package com.hxxc.huaxing.app.ui.mine.lend;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.LendDetailLoanDetailsBean;
import com.hxxc.huaxing.app.data.bean.LendDoubleItemBean;
import com.hxxc.huaxing.app.ui.base.BaseFragment;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by Administrator on 2016/10/10.
 * 出借详情 - 出借详情
 */

public class LendDetailFragment extends BaseFragment {

    public static LendDetailFragment newInstance(LendDetailLoanDetailsBean bean) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("bean",bean);
        LendDetailFragment fragment = new LendDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.list_lend_status)
    ListView list_lend_status;

    List<LendDoubleItemBean> list;
    LendDetailAdapter lendDetailAdapter;

    LendDetailLoanDetailsBean bean;

    String[] names = new String[]{"订单编号","年化收益","出借期限","投资金额","预期收益",
            "还款方式","财富顾问","下单时间"};
    String[] values = new String[]{"hxx000","10.5%","12个月","10,000元","2,000.50元","等额本息","xxx","2016-8-9 14:03:55"};

    @Override
    protected void initDagger() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.lend_status_fragment;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {

        bean = (LendDetailLoanDetailsBean) getArguments().getSerializable("bean");
        list_lend_status.setDividerHeight(0);
        if (list==null)list = new ArrayList<LendDoubleItemBean>();
        list.clear();

        for (int i=0;i<names.length;i++){
            LendDoubleItemBean bean = new LendDoubleItemBean();
            bean.setName(names[i]);
            bean.setValue(getValues(i));
            list.add(bean);
        }

        lendDetailAdapter = new LendDetailAdapter(getActivity(),list,R.layout.lend_detail_item);
        list_lend_status.setAdapter(lendDetailAdapter);
    }


    public class LendDetailAdapter extends CommonAdapter<LendDoubleItemBean>{


        public LendDetailAdapter(Context context, List<LendDoubleItemBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, LendDoubleItemBean item, int position) {
            super.convert(helper, item, position);
            RelativeLayout relative_lend_detail = helper.getView(R.id.relative_lend_detail);
            if (position%2==0)relative_lend_detail.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            else relative_lend_detail.setBackgroundColor(getActivity().getResources().getColor(R.color.white_fafa));

            helper.setText(R.id.tv_lend_detail_item_name,item.getName());
            helper.setText(R.id.tv_lend_detail_item_value,item.getValue());

        }

    }

    private String getValues(int index){
        if (bean==null)return "";
        StringBuffer temp=new StringBuffer();
            switch (index) {
                case 0:
                    temp.append(bean.getOrderNo());
                    break;
                case 1:
                    temp.append(DateUtil.getInstanse().getOne(100 * bean.getYearRate().doubleValue())).append("%");
                    break;
                case 2:
                    temp.append(bean.getPeriods()).append("个月");
                break;
                case 3:
                    temp.append(MoneyUtil.addComma(bean.getAmount(),2,ROUND_FLOOR)).append("元");
                    break;
                case 4:
                    temp.append(MoneyUtil.addComma(bean.getExpectProfit(),2,ROUND_FLOOR)).append("元");
                    break;
                case 5:
                    temp.append(bean.getInterestTypeStr());
                    break;
                case 6: temp.append(bean.getFname());
                    break;
                case 7:
                    temp.append(DateUtil.getmstodate(bean.getSignTime(), DateUtil.YYYYMMDDHHMMSS));
                    break;
                default:
                    break;
            }
        return temp.toString();
    }


}
