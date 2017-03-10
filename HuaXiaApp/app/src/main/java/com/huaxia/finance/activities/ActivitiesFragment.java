package com.huaxia.finance.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.FileUtils;
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
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.ActivitiesModel;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.BasePageModel;
import com.huaxia.finance.recommenddm.ActivtiesWebActivity;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/1/18.
 */
public class ActivitiesFragment  extends Fragment{

    private String mPageName;
    int mNum;

    private View view_add_warming;

    private PullToRefreshListView pull_listview_activities;

    private List<ActivitiesModel> activitiesModelList;
    private ActivitiesAdapter activitiesAdapter;

    private int pageSize=0;
    private boolean flagRefresh=false;

    private RelativeLayout relative_no_work;
    private ImageView img_empty;
    private TextView tv_reloading;

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

        relative_no_work = (RelativeLayout)getActivity().findViewById(R.id.relative_no_work_activity);
        relative_no_work.setVisibility(View.GONE);
        img_empty = (ImageView)getActivity().findViewById(R.id.img_empty_activity);
        tv_reloading = (TextView)getActivity().findViewById(R.id.tv_reloading_activity);

        view_add_warming = View.inflate(getActivity(),R.layout.text_waming_invest,null);

        pull_listview_activities = (PullToRefreshListView) getActivity().findViewById(R.id.pull_listview_activities);
        pull_listview_activities.setPullRefreshEnabled(true);
        pull_listview_activities.setPullLoadEnabled(true);
        pull_listview_activities.setScrollLoadEnabled(false);
        pull_listview_activities.setHasMoreData(true);
        pull_listview_activities.getRefreshableView().setFastScrollEnabled(false);
        pull_listview_activities.getRefreshableView().setDivider(null);
        pull_listview_activities.getRefreshableView().setDividerHeight(0);
        pull_listview_activities.getRefreshableView().setVerticalScrollBarEnabled(false);
        pull_listview_activities.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//下拉刷新
                String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
                pull_listview_activities.setLastUpdatedLabel(text);
                pageSize = 0;
                flagRefresh = true;
                RequestDate();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                flagRefresh = false;
                RequestDate();
            }
        });
        pull_listview_activities.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (NetWorkUtils.isNetworkConnected(getActivity())) {
                    if (position<activitiesModelList.size()){
                        startActivity(new Intent(getActivity(), ActivtiesWebActivity.class).putExtra("activityId", activitiesModelList.get(position).getActivityId()));
                    }
                }else ToastUtils.showShort("网络不给力");
            }
        });

        //请求数据
        if (NetWorkUtils.isNetworkConnected(getActivity())){
            relative_no_work.setVisibility(View.GONE);
            pull_listview_activities.doPullRefreshing(true,100);
        }else{
            String textA = FileUtils.getInstance().readFileToSdcardByFileInputStream(UserConstant.pathCache,UserConstant.pathActivttyList);
            MyLog.d("text_activitytlist=" + textA);
            if (!TextUtils.isEmpty(textA))jsonActivityData(textA);
            else{
                relative_no_work.setVisibility(View.VISIBLE);
                img_empty.setVisibility(View.VISIBLE);
                img_empty.setImageResource(R.drawable.icon_no_wifi);
                tv_reloading.setVisibility(View.VISIBLE);
                tv_reloading.setText("网络出现问题啦!");
            }
        }

    }
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mPageName = String.format("fragment %d", mNum);

    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
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
        View view = myInflater.inflate(R.layout.activities_fragment, container,
                false);
        return view;
    }

    public void setListDate(BasePageModel model){
        if (model==null)return;
        if (model.getList() ==null)return;
        if (activitiesModelList==null)activitiesModelList=new ArrayList<ActivitiesModel>();
        if (pageSize==0){
            activitiesModelList.clear();
//            ActivitiesModel mmodel = new ActivitiesModel();
//            mmodel.setActivityId("more");
//            activitiesModelList.add(mmodel);
        }
        activitiesModelList.addAll(model.getList());

        if (activitiesAdapter==null) {
            activitiesAdapter = new ActivitiesAdapter(getActivity(),model.getList(),R.layout.activit_item);
            pull_listview_activities.getRefreshableView().addFooterView(view_add_warming);
            pull_listview_activities.getRefreshableView().setAdapter(activitiesAdapter);
        } else {
            if (pageSize==0){
                activitiesAdapter.clearAdapterListData();
//                ActivitiesModel mmodel = new ActivitiesModel();
//                mmodel.setActivityId("more");
//                model.getList().add(mmodel);
                activitiesAdapter.setAdapterListData(-1,model.getList());
            }else   activitiesAdapter.setAdapterListData(-1,model.getList());
            activitiesAdapter.notifyDataSetChanged();
        }
        if (activitiesAdapter.getAdapterListData().size()==0){
            relative_no_work.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_empty_a);
            tv_reloading.setVisibility(View.GONE);
        }else{
            relative_no_work.setVisibility(View.GONE);
        }
        pageSize = pageSize+10;
    }

    /**
     * json
     */
    public void jsonActivityData(String resultDate){

        BaseModel<BasePageModel<ActivitiesModel>> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate, new TypeToken<BaseModel<BasePageModel<ActivitiesModel>>>() {
        }.getType());
        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
            setListDate(baseModel.getData());
        }else{
            if (activitiesAdapter!=null){
                if (activitiesAdapter.getCount()==0){
                    relative_no_work.setVisibility(View.VISIBLE);
                    img_empty.setVisibility(View.VISIBLE);
                    img_empty.setImageResource(R.drawable.icon_empty_a);
                    tv_reloading.setVisibility(View.GONE);
                }
            }else{
                relative_no_work.setVisibility(View.VISIBLE);
                img_empty.setVisibility(View.VISIBLE);
                img_empty.setImageResource(R.drawable.icon_empty_a);
                tv_reloading.setVisibility(View.GONE);
            }
        }


    }


    /**
     * 控件初始化
     */
    public void RequestDate(){
        final BaseRequestParams params = new BaseRequestParams();
        params.put("start",""+pageSize);
        params.put("size", "" + 10);
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlActivitiesList);
        MyLog.d("api_url="+url.toString());
        DMApplication.getInstance().getHttpClient(getActivity()).get(getActivity(), url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                if (flagRefresh) pull_listview_activities.onPullDownRefreshComplete();
                else pull_listview_activities.onPullUpRefreshComplete();
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);

                    FileUtils.getInstance().writeFileToSdcardByFileOutputStream(UserConstant.pathCache, UserConstant.pathActivttyList, resultDate);

                    if (statusCode == 200) {

                        jsonActivityData(resultDate);

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                if (flagRefresh) pull_listview_activities.onPullDownRefreshComplete();
                else pull_listview_activities.onPullUpRefreshComplete();
                if (activitiesAdapter!=null){
                    if (activitiesAdapter.getCount()==0){
                        relative_no_work.setVisibility(View.VISIBLE);
                        img_empty.setVisibility(View.VISIBLE);
                        img_empty.setImageResource(R.drawable.icon_no_wifi);
                        tv_reloading.setVisibility(View.VISIBLE);
                        tv_reloading.setText("网络出现问题啦!");
                    }
                }else{
                    relative_no_work.setVisibility(View.VISIBLE);
                    img_empty.setVisibility(View.VISIBLE);
                    img_empty.setImageResource(R.drawable.icon_no_wifi);
                    tv_reloading.setVisibility(View.VISIBLE);
                    tv_reloading.setText("网络出现问题啦!");
                }
            }
        });
    }

    /**
     * 产品适配器
     */
    private class ActivitiesAdapter extends CommonAdapter<ActivitiesModel> {

        public ActivitiesAdapter(Context context, List<ActivitiesModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, ActivitiesModel item) {
            //设置值
            if (item.getActivityId().equals("more")){
                helper.getView(R.id.tv_activities_name).setVisibility(View.GONE);
                helper.getView(R.id.img_activit_icon).setVisibility(View.GONE);
                helper.getView(R.id.tv_activities_more).setVisibility(View.VISIBLE);
                helper.getView(R.id.view_bottom_activity).setVisibility(View.VISIBLE);
            }else{
                helper.getView(R.id.tv_activities_name).setVisibility(View.GONE);
                helper.getView(R.id.img_activit_icon).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_activities_more).setVisibility(View.GONE);
                helper.getView(R.id.view_bottom_activity).setVisibility(View.GONE);

                ImageView img_activit_icon = helper.getView(R.id.img_activit_icon);

                if (TextUtils.isEmpty(item.getHomeUrl())){
                    img_activit_icon.setBackgroundResource(R.drawable.ads_loading);
                }else DMApplication.getInstance().getImageLoader().DisplayImage(item.getHomeUrl(),img_activit_icon,R.drawable.ads_loading);

                helper.setText(R.id.tv_activities_name, item.getActivityName());
            }

        }
    }
}
