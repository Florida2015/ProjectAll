package com.huaxia.finance.moredm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.ApplicationUtils;
import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.PhoneUtils;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.UrlConstants;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by houwen.lai on 2016/1/19.
 *
 */
public class MoreFragment extends Fragment implements View.OnClickListener{

    private String mPageName;
    int mNum;

    private RelativeLayout relative_about;
    private RelativeLayout relative_question;
    private RelativeLayout relative_exemption;
    private RelativeLayout relative_privacy;
    private RelativeLayout relative_notice;
    private RelativeLayout relative_phone;
    private RelativeLayout relative_notice_update;
    private TextView tv_version_name;

    private TextView tv_phone;
    private TextView tv_phone_time;

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

        findAllViews();
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
        View view = myInflater.inflate(R.layout.fragment_more, container,
                false);
//        initViews(view);
        return view;
    }


    public void findAllViews(){
        relative_about = (RelativeLayout)getActivity().findViewById(R.id.relative_about);
        relative_question = (RelativeLayout)getActivity().findViewById(R.id.relative_question);
        relative_exemption = (RelativeLayout)getActivity().findViewById(R.id.relative_exemption);
        relative_privacy = (RelativeLayout)getActivity().findViewById(R.id.relative_privacy);
        relative_notice = (RelativeLayout)getActivity().findViewById(R.id.relative_notice_more);
        relative_phone = (RelativeLayout)getActivity().findViewById(R.id.relative_phone);
        relative_notice_update= (RelativeLayout)getActivity().findViewById(R.id.relative_notice_update);
        tv_version_name= (TextView)getActivity().findViewById(R.id.tv_version_name);
        tv_version_name.setText("当前版本"+ ApplicationUtils.getVersionName(getActivity()));

        tv_phone = (TextView)getActivity().findViewById(R.id.tv_phone);
        tv_phone_time = (TextView)getActivity().findViewById(R.id.tv_phone_time);
        SpannableStringBuilder msp = new SpannableStringBuilder("客服热线 工作日9:30-18:30");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_3333)), 0, 4,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_9999)), 4, msp.toString().trim().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
        // dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        tv_phone_time.setText(msp);
        tv_phone_time.setMovementMethod(LinkMovementMethod.getInstance());

        relative_about.setOnClickListener(this);
        relative_question.setOnClickListener(this);
        relative_exemption.setOnClickListener(this);
        relative_privacy.setOnClickListener(this);
        relative_notice.setOnClickListener(this);
        relative_phone.setOnClickListener(this);
        relative_notice_update.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_about://http://192.168.9.140:8080/#/about/us   ///
                getActivity().startActivity(new Intent(getActivity(), MoreWebActivity.class).putExtra("title", "关于花虾").putExtra("url",UrlConstants.urlBase_web+UrlConstants.urlAbout));// UrlConstants.urlBase_web+UrlConstants.urlAbout
                break;
            case R.id.relative_question:
                getActivity().startActivity(new Intent(getActivity(), MoreWebActivity.class).putExtra("title", "常见问题").putExtra("url", UrlConstants.urlBase_web+UrlConstants.urlProblem));
                break;
            case R.id.relative_exemption:
                getActivity().startActivity(new Intent(getActivity(), MoreWebActivity.class).putExtra("title", "免责声明").putExtra("url", UrlConstants.urlBase_web+UrlConstants.urlDisclaimer));
                break;
            case R.id.relative_privacy:
                getActivity().startActivity(new Intent(getActivity(), MoreWebActivity.class).putExtra("title", "隐私政策").putExtra("url", UrlConstants.urlBase_web+UrlConstants.urlPivacy));
                break;
            case R.id.relative_notice_more:
                getActivity().startActivity(new Intent(getActivity(), NoticeListActivity.class));
                break;
            case R.id.relative_notice_update://版本升级
                if (BtnUtils.isFastDoubleClick())
                MenuTwoActivity.getInstance().RequestUpDate(true);
                break;
            case R.id.relative_phone:
                if (BtnUtils.isFastDoubleClick())
                PhoneUtils.showTel(getActivity(),getActivity().getResources().getString(R.string.text_phone));
                break;
        }
    }
}
