package com.huaxia.finance.moredm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.NoticeListModel;
import com.huaxia.finance.model.NoticeModel;
import com.huaxia.finance.request.BaseRequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * 花虾公告
 * Created by houwen.lai on 2016/2/19.
 */
public class NoticeListActivity extends BaseActivity {

    private PullToRefreshListView pull_listview_notice;

    private Context mContext;

    private boolean flagRefresh=false;
    private int pageSize=0;

    private String mPageName;
    int mNum;

    private NoticeListAdapter noticeListAdapter;
    private List<NoticeModel> noticeModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.notice_list);
        mContext=this;
        findAllViews();

    }

    public void findAllViews(){
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("公告");
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pull_listview_notice = (PullToRefreshListView) findViewById(R.id.pull_listview_notice);
        pull_listview_notice.setPullRefreshEnabled(true);
        pull_listview_notice.setPullLoadEnabled(true);
        pull_listview_notice.setScrollLoadEnabled(false);
        pull_listview_notice.setHasMoreData(true);
        pull_listview_notice.getRefreshableView().setDivider(new ColorDrawable(getResources().getColor(R.color.black_dcdc)));
        pull_listview_notice.getRefreshableView().setDividerHeight(1);
        pull_listview_notice.setVerticalScrollBarEnabled(false);
        pull_listview_notice.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
                pull_listview_notice.setLastUpdatedLabel(text);
                pageSize = 0;
                flagRefresh = true;
                setHttpRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                flagRefresh = false;
                setHttpRequest();
            }
        });
        pull_listview_notice.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入通知详情

                startActivity(new Intent(NoticeListActivity.this, NoticeDetailActivity.class).putExtra("noticeNo", noticeListAdapter.getItem(position).getNoticeNo()));
            }
        });

        //请求数据
        if (NetWorkUtils.isNetworkConnected(this)){
            relative_no_work.setVisibility(View.GONE);
            pull_listview_notice.doPullRefreshing(true,100);
        }else{
            relative_no_work.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_no_wifi);
            tv_reloading.setVisibility(View.VISIBLE);
            tv_reloading.setText("网络出现问题啦!");
        }
    }

    public void setInitData(NoticeListModel model){
        if (model.getList()==null||model.getList().size()==0)return;
        if (noticeModelList==null){
            noticeModelList = new ArrayList<NoticeModel>();
        }
        if (pageSize==0||pageSize==1){
            noticeModelList.clear();
        }
        noticeModelList.addAll(model.getList());

        if (noticeListAdapter==null){
            noticeListAdapter = new NoticeListAdapter(mContext,noticeModelList,R.layout.notice_item);
            pull_listview_notice.getRefreshableView().setAdapter(noticeListAdapter);
        }else{
            if (pageSize==0||pageSize==1){
                noticeListAdapter.clearAdapterListData();
            }
            noticeListAdapter.setAdapterListData(-1,model.getList());
            noticeListAdapter.notifyDataSetChanged();
        }
        if (noticeListAdapter!=null){
            if (noticeListAdapter.getCount()==0){
                relative_no_work.setVisibility(View.VISIBLE);
                img_empty.setVisibility(View.VISIBLE);
                img_empty.setImageResource(R.drawable.icon_empty_a);
                tv_reloading.setVisibility(View.GONE);
            }
        }else{
            relative_no_work.setVisibility(View.GONE);
        }
        pageSize = pageSize+10;
    }

    public void setHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("start",""+pageSize);//
        params.put("size", "10");//
        String url = UrlConstants.urlBase+UrlConstants.urlNoticeList;
        MyLog.d("api_url=" + url);
        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                if (flagRefresh) pull_listview_notice.onPullDownRefreshComplete();
                else pull_listview_notice.onPullUpRefreshComplete();
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<NoticeListModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<NoticeListModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

                            setInitData(baseModel.getData());

                        } else ToastUtils.showShort(baseModel.getMsg());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                if (flagRefresh) pull_listview_notice.onPullDownRefreshComplete();
                else pull_listview_notice.onPullUpRefreshComplete();
                if (noticeListAdapter!=null){
                    if (noticeListAdapter.getCount()==0){
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
     * 通知适配器
     */
    private class NoticeListAdapter extends CommonAdapter<NoticeModel> {

        public NoticeListAdapter(Context context, List<NoticeModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, NoticeModel item) {
            //设置值

            helper.setText(R.id.tv_notice_name, TextUtils.isEmpty(item.getNoticeSubject()) ? "通知" : item.getNoticeSubject());
            helper.setText(R.id.tv_notice_time, DateUtils.getInstanse().getmstodated(item.getBeginDate(), DateUtils.YYYYMMDD));
            helper.setText(R.id.tv_notice_context, TextUtils.isEmpty(item.getContents()) ? "" : item.getContents());

        }
    }


}
