package com.hxxc.huaxing.app.ui.mine.autobid;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.wedgit.WaveView;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2016/9/23.
 * 自动投标页
 */

public class OpenAutoBidActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.img_ripple)
    ImageView img_ripple;
    @BindView(R.id.btn_open_e_aount)
    FancyButton btn_open_e_aount;

    Animator animator;

    @BindView(R.id.waveview_ripple)
    WaveView mWaveView;

    @Override
    public int getLayoutId() {
        return R.layout.bid_auto;
    }

    @Override
    public void initView() {
        toolbar_title.setText("自动投标");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mWaveView.setDuration(6000);
        mWaveView.setStyle(Paint.Style.FILL);
        mWaveView.setColor(getResources().getColor(R.color.blue_text));//
        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
        mWaveView.start();

    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_open_e_aount)
    public void onClick(View view){
        //自动投标
        if (view.getId()==R.id.btn_open_e_aount){
            startActivity(new Intent(mContext,AutoBidActivity.class));
            finish();
        }
    }


}
