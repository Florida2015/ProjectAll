package com.huaxia.finance.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.loadingballs.BallView;
import com.huaxia.finance.R;

/**
 * 基类
 * Created by houwen.lai on 2015/11/18.
 */
public class BaseActivity extends AppCompatActivity {

    public RelativeLayout relative_title_bar;
    public ImageButton img_btn_title_back;
    public TextView tv_title_bar;
    public ImageButton img_btn_title_right;

    public FrameLayout context_fragment;

    public RelativeLayout relative_no_work;
    public ImageView img_empty;
    public TextView tv_reloading;
    public BallView ballview;

    FrameLayout.LayoutParams params;
    LayoutInflater inflater;

    public int dipW;
    public int dipH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dipW = dm.widthPixels;
        dipH = dm.heightPixels;

        relative_title_bar = (RelativeLayout) findViewById(R.id.relative_title_bar);
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);

        context_fragment = (FrameLayout) findViewById(R.id.context_fragment);

        relative_no_work = (RelativeLayout) findViewById(R.id.relative_no_work);
        img_empty = (ImageView) findViewById(R.id.img_empty);
        tv_reloading = (TextView) findViewById(R.id.tv_reloading);
        ballview = (BallView) findViewById(R.id.ballview);
        ballview.setVisibility(View.GONE);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);//定义框架布局器参数
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


    }

    public void setBaseContentView(int layoutResID) {

        View view = inflater.inflate(layoutResID, null);
//        view.setLayoutParams(new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        context_fragment.addView(view, params);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
//                MyLog.d("ontouch_X="+sX+"_mX="+ev.getX());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                eX = ev.getX();
//                MyLog.d("ontouch_X="+sX+"_mX="+ev.getX());
                if (eX - sX >dipW/3) {
                    BaseActivity.this.finish();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    public float sX = 0;
    public float eX= 0;

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return super.onGenericMotionEvent(event);
    }

}
