package com.hxxc.user.app.ui.order;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.data.bean.OrderContundBean;
import com.hxxc.user.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/11/10.
 * 订单详情 续投对话框
 */

public class OrderContunedFragment extends Fragment{// implements ContinuedContract.View

    UpDateLister upDateLister;

    private ListView listview;
    private View mView;
    private TextView tv_textview_xutou;

//    ContinuedPresenter continuedPresenter;

    private String ppid;

    public static OrderContunedFragment newInstance(String orderStatus,String pid) {
        Bundle arguments = new Bundle();
        arguments.putString("type",orderStatus);
        arguments.putString("pid",pid);
        OrderContunedFragment fragment = new OrderContunedFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){//相当于Fragment的onResume
//            getContunedData(getActivity(),getArguments().getString("type"));
        }else {////相当于Fragment的onPause

        }
    }

    public OrderContunedFragment() {
        super();
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        upDateLister = (UpDateLister) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.order_status_fragment, null);

        if(mView!=null) listview = (ListView) mView.findViewById(R.id.list_order_status);
        listview.setDividerHeight(0);

        ppid = getArguments().getString("pid");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.d("onitemclick="+i);
                if (i>0){
                    indexId = i;
                    ppid = lists.get(i-1).getId()+"";
                    upDateLister.setData(getArguments().getString("type"),lists.get(i-1).getId()+"");
                    initData(false,true);
                }
            }
        });

//        mView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_textview_xutou, null);
//        tv_textview_xutou = (TextView) mView.findViewById(R.id.tv_textview_xutou);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        continuedPresenter = new ContinuedPresenter();
//        continuedPresenter.setVM(this);
//        continuedPresenter.toContinuedInvestment(getActivity(),getArguments().getString("type"));


    }

    @Override
    public void onStart() {
        super.onStart();
//        getContunedData(getActivity(),getArguments().getString("type"));
    }



    public void setInitData(List<OrderContundBean> orderContundBeen){
        if (orderContundBeen==null)return;
        if (lists==null)lists = new ArrayList<OrderContundBean>();
        lists.clear();
        lists.addAll(orderContundBeen);

        initData(false,false);
    }

    ContundAdapter contundAdapter;
    List<OrderContundBean> lists = new ArrayList<OrderContundBean>();
    int indexId = -1;
    public void initAdapter(){
        mView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_textview_xutou, null);
        tv_textview_xutou = (TextView) mView.findViewById(R.id.tv_textview_xutou);
        if (getArguments().getString("type").equals("2")){
            tv_textview_xutou.setText(getResources().getString(R.string.text_continue_y));
        }else if(getArguments().getString("type").equals("1")){
            tv_textview_xutou.setText(getResources().getString(R.string.text_continue_x));
        }

        if (listview.getHeaderViewsCount()==0)  listview.addHeaderView(mView);
        contundAdapter = new ContundAdapter(getActivity(),lists,R.layout.order_contund_item);
        listview.setAdapter(contundAdapter);
        listview.setSelection(indexId);
        contundAdapter.notifyDataSetChanged();

    }

    public void setNotifyDataSetChanged(){
        if (contundAdapter==null)return;

        contundAdapter.notifyDataSetChanged();
    }

    public void initData(boolean flag,boolean flag2){
//        if (indexId<0)return;
        LogUtil.d("order chooise="+flag+"___flag2="+flag2);
        if (lists==null)return;
        for (int i=0;i<lists.size();i++){
            if (flag) lists.get(i).setFlag(false);
            else {
                if (("" + lists.get(i).getId()).equals(ppid) && !lists.get(i).isFlag()) {
                    indexId = i;
                    lists.get(i).setFlag(true);
                } else lists.get(i).setFlag(false);
            }
        }
        if (flag2)setNotifyDataSetChanged();
        else  initAdapter();
    }

    /**
     *
     */
    public class ContundAdapter extends CommonAdapter<OrderContundBean> {

        public ContundAdapter(Context context, List<OrderContundBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, OrderContundBean item, int position) {
            super.convert(helper, item, position);

            helper.setText(R.id.tv_yeard_contund,TextUtils.isEmpty(item.getYearRateText())?"0.0%": item.getYearRateText());
            if (item.getType()==1){//加利率//DateUtil.getInstance().getOne(100*item.getYearRate())+"%"
                helper.setText(R.id.tv_yeard_extra,TextUtils.isEmpty(item.getAdditionalText())?"":item.getAdditionalText());
            }else {
                helper.setText(R.id.tv_yeard_extra,"");
            }

//            if (item.getAdditional()>0){//DateUtil.getInstance().getOne(100*item.getAdditional())+"%"
//                helper.setText(R.id.tv_yeard_extra,TextUtils.isEmpty(item.getAdditionalText())?"0.0%":item.getAdditionalText());
//            }else  helper.setText(R.id.tv_yeard_extra,"");

            helper.setText(R.id.tv_yeard_title,item.getProductName().trim()+" "+item.getPeriods()+"个月");

            if (item.isFlag()){
                ((ImageView)helper.getView(R.id.img_choise_continud)).setImageResource(R.mipmap.icon_chox_y);
            }else  ((ImageView)helper.getView(R.id.img_choise_continud)).setImageResource(R.mipmap.icon_chox_n);

        }
    }

//    public void getContunedData(Context context,String type){
//        Api.getClient().getGetXuTouList(type).compose(RxApiThread.convert()).
//                subscribe(new BaseSubscriber<List<OrderContundBean>>(context) {
//                              @Override
//                              public void onSuccess(List<OrderContundBean> orderContundBeen) {
//
//                                  if (orderContundBeen==null)return;
//                                  if (lists==null)lists = new ArrayList<OrderContundBean>();
//                                  lists.clear();
//                                  lists.addAll(orderContundBeen);
//
//                                  initData();
//                              }
//                          }
//                );
//

}
