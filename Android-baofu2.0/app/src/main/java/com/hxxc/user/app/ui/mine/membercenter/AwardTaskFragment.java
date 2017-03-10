package com.hxxc.user.app.ui.mine.membercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.data.bean.MemberCenterBean;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.mine.dialog.LendDialogFragment;
import com.hxxc.user.app.ui.mine.invitation.InvitationFriendsActivity;
import com.hxxc.user.app.ui.mine.sign.SignActivity;
import com.hxxc.user.app.ui.mine.web.WebActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.ui.product.BaofuBingCardActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/11/7.
 * 有奖任务 成长任务 每日任务
 */

public class AwardTaskFragment extends Fragment {

    private ListView listview;
    private View mView;

    public static Fragment newInstance(BaseResult<List<MemberCenterBean>> listBaseBean) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("list", listBaseBean);
        AwardTaskFragment fragment = new AwardTaskFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.member_award_task_fragment, null);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mView!=null) listview = (ListView) mView.findViewById(R.id.listview_award_task);
        listview.setDividerHeight(0);

        initAdapter();

    }
    MermberAdapter mermberAdapter;
    List<MemberCenterBean> lists;
    public void initAdapter(){
        if (lists==null)lists = new ArrayList<MemberCenterBean>();
        lists.clear();
        lists.addAll( ((BaseResult<List<MemberCenterBean>>) getArguments().getSerializable("list")).getData() );
        mermberAdapter = new MermberAdapter(getActivity(),lists,R.layout.mermber_center_item);
        listview.setAdapter(mermberAdapter);

    }
    public void jumpOpen(int index){
        Intent mIntent = new Intent();
        switch (index){
            case 1://每日签到
                if (BtnUtils.isFastDoubleClick()){
                    mIntent.setClass(getActivity(), SignActivity.class);
                    startActivity(mIntent);
                }

                break;
            case 2://邀请好友
                if (BtnUtils.isFastDoubleClick()){
                    mIntent.setClass(getActivity(), InvitationFriendsActivity.class);
                    startActivity(mIntent);
                }

                break;
            case 3://出借规则 dilog
                if (BtnUtils.isFastDoubleClick()){
                    LendDialogFragment lendDialogFragment = new LendDialogFragment().newInstance(HttpRequest.indexBaseUrl+HttpRequest.httpUrlInteger);
                    lendDialogFragment.show(getActivity().getFragmentManager(), "lendDialogFragment");
                }

                break;
            case 4://实名认证
                if (BtnUtils.isFastDoubleClick()){
                    mIntent.setClass(getActivity(), AuthenticationActivity.class);
                    startActivity(mIntent);
                }
                break;
            case 5://绑定银行卡
                if (BtnUtils.isFastDoubleClick()){
                    UserInfo userInfo = SPUtils.geTinstance().getUserInfo();
                    if (userInfo!=null&&userInfo.getRnaStatus() == 0){//未认证
                        mIntent.setClass(getActivity(), AuthenticationActivity.class);
                        startActivity(mIntent);
                    }else if(userInfo!=null&&userInfo.getRnaStatus() == 1&&userInfo.getBindcardStatus()==0){//认证 去绑卡
                        startActivity(new Intent(getActivity(), BaofuBingCardActivity.class));
                    }
                }
                break;
            default:

                break;
        }
    }
    /**
     *  适配
     */
    public class MermberAdapter extends CommonAdapter<MemberCenterBean> {

        public MermberAdapter(Context context, List list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, MemberCenterBean item, int position) {
            super.convert(helper, item, position);
//            if (position==0){
//                helper.getView(R.id.view_mermber_1).setVisibility(View.VISIBLE);
//                helper.getView(R.id.relative_mermber_title).setVisibility(View.VISIBLE);
//                helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
//                helper.setText(R.id.tv_day_name,"每日任务");
//            }else {
                helper.getView(R.id.view_mermber_1).setVisibility(View.GONE);
                helper.getView(R.id.relative_mermber_title).setVisibility(View.GONE);
                helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
//            }
//            if (position>0&&item.getTaskType()==0){
//                if (lists!=null&&item.getTaskType()!=lists.get(position-1).getTaskType()){
//                    helper.getView(R.id.view_mermber_1).setVisibility(View.VISIBLE);
//                    helper.getView(R.id.relative_mermber_title).setVisibility(View.VISIBLE);
//                    helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
//                    helper.setText(R.id.tv_day_name,"成长任务");
//                }else {
//                    helper.getView(R.id.view_mermber_1).setVisibility(View.GONE);
//                    helper.getView(R.id.relative_mermber_title).setVisibility(View.GONE);
//                    helper.getView(R.id.relative_mermber_context).setVisibility(View.VISIBLE);
//                }
//            }

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
                        getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("url",item.getJumpMobileUrl()));
                    }

                }
            });

            if (lists!=null&&lists.size()==position+1)helper.getView(R.id.view_member_bottom).setVisibility(View.VISIBLE);
            else helper.getView(R.id.view_member_bottom).setVisibility(View.GONE);


        }
    }

}
