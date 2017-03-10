package com.huaxia.finance.moredm;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.NoticeModel;
import com.huaxia.finance.request.BaseRequestParams;

import org.apache.http.Header;

/**
 * 花虾详情
 * Created by houwen.lai on 2016/2/19.
 */
public class NoticeDetailActivity extends BaseActivity {

    private TextView tv_notice_title;
    private TextView tv_notice_time;
    private TextView tv_notice_context;

    private Context mContext;

    private String noticeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.notice_detail);
        mContext = this;
        noticeNo = getIntent().getStringExtra("noticeNo");
        findAllViews();
    }

    public void findAllViews(){
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("公告详情");
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_notice_title= (TextView) findViewById(R.id.tv_notice_title);
        tv_notice_time= (TextView) findViewById(R.id.tv_notice_time);
        tv_notice_context= (TextView) findViewById(R.id.tv_notice_context);


        setHttpRequest();

    }

    public void setInitData(NoticeModel model){
        tv_notice_title.setText(model.getNoticeSubject());
        tv_notice_time.setText(DateUtils.getInstanse().getmstodated(model.getBeginDate(),DateUtils.YYYYMMDD));
        tv_notice_context.setText(model.getContents());
    }

    public void setHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("noticeNo", "" + noticeNo);//
        String url = UrlConstants.urlBase+UrlConstants.urlNoticeDetail;
        MyLog.d("api_url=" + url);
        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<NoticeModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<NoticeModel>>() {
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

            }
        });
    }

}
