package com.hxxc.huaxing.app.ui.mine.lend;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.LendDetailItemBean;
import com.hxxc.huaxing.app.data.bean.LendDetailOrderInfoLogBean;
import com.hxxc.huaxing.app.ui.base.BaseFragment;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/29.
 * 出借详情 - 出借状态
 */

public class LendStatusFragment extends BaseFragment {

    public static LendStatusFragment newInstance(LendDetailItemBean beans) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("beans",beans);
        LendStatusFragment fragment = new LendStatusFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.list_lend_status)
    ListView list_lend_status;

    List<LendDetailOrderInfoLogBean> lists;
    LendStatusAdpter lendStatusAdpter;

    @Override
    protected void initDagger() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.lend_status_fragment;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {

        if (lists==null)lists = new ArrayList<LendDetailOrderInfoLogBean>();

        lists = ((LendDetailItemBean)getArguments().getSerializable("beans")).getOrderInfoLogs();

        if (lists!=null) LogUtil.d("lend detail = "+lists.toString());
        list_lend_status.setDividerHeight(0);
        lendStatusAdpter = new LendStatusAdpter(getActivity(),lists,R.layout.lend_status_item);
        list_lend_status.setAdapter(lendStatusAdpter);

    }

    /**
     *
     */
    public class LendStatusAdpter extends CommonAdapter<LendDetailOrderInfoLogBean>{

        public LendStatusAdpter(Context context, List<LendDetailOrderInfoLogBean> list, int layoutId) {
            super(context, list, layoutId);


        }

        @Override
        public void convert(ViewHolder helper, LendDetailOrderInfoLogBean item, int position) {
            super.convert(helper, item, position);

            if (position%2==0)helper.getView(R.id.relative_lend_status).setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            else helper.getView(R.id.relative_lend_status).setBackgroundColor(getActivity().getResources().getColor(R.color.white_fafa));

            if (lists!=null&&position+1==lists.size()){
                helper.getView(R.id.view_lend_line).setVisibility(View.VISIBLE);
                helper.getView(R.id.view_lend_lin_2).setVisibility(View.GONE);
                helper.getView(R.id.view_lend_dot).setBackgroundResource(R.drawable.organ_lend_detail_item);
            }else {
                helper.getView(R.id.view_lend_line).setVisibility(View.VISIBLE);
                helper.getView(R.id.view_lend_lin_2).setVisibility(View.VISIBLE);
                helper.getView(R.id.view_lend_dot).setBackgroundResource(R.drawable.blue_lend_detail_item);
            }

            if (position==0) helper.getView(R.id.view_lend_line).setVisibility(View.GONE);

            helper.setText(R.id.tv_lend_status_name,item.getTitles());
            if (!TextUtils.isEmpty(item.getCreateTime())){
                helper.setText(R.id.tv_lend_status_data, DateUtil.getmstodate(Long.parseLong(item.getCreateTime()),DateUtil.YYYYMMDDHHMM));
            }

            helper.setText(R.id.tv_lend_status_detail,item.getContents());


        }
    }



}
