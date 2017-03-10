package com.hxxc.user.app.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxxc.user.app.R;
import com.hxxc.user.app.widget.CircleImageView;

/**
 * Created by chenqun on 2016/6/21.
 */
public abstract class ToolbarActivity extends BaseActivity {

    public static final int IM = 0;
    public static final int SHARE = 1;

//    private Toolbar mToolbar;
    public TextView mTitle;
    public ImageButton mIvShare;
    public Button mButtonRight;
    public TextView mPositionText;
    public RelativeLayout mMessageLayout;
    public CircleImageView imageView;
    public ImageView mUnread;

    public RelativeLayout toolbar;
    public ImageButton back;
    public View view_line;

    protected abstract int provideContentViewId();

    protected abstract void setTitle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());


        //*************************
        back = (ImageButton) findViewById(R.id.back);
        if(null != back){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        //*************************

        toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        view_line = findViewById(R.id.view);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mPositionText = (TextView) findViewById(R.id.position_text);

        mMessageLayout = (RelativeLayout) findViewById(R.id.message_layout);
        imageView = (CircleImageView) findViewById(R.id.imageView);
        mUnread = (ImageView) findViewById(R.id.munread_img);

        mButtonRight = (Button) findViewById(R.id.btn_right);
        mIvShare = (ImageButton) findViewById(R.id.toolbar_iv_share);

        if(null != mIvShare){
            mIvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toShare();
                }
            });
        }
//        mToolbar.setTitle("");
        setTitle();
//        if (mToolbar == null) {
//            throw new IllegalStateException(
//                    "The subclass of ToolbarActivity must contain a toolbar.");
//        }
//        setSupportActionBar(mToolbar);
        if (canBack()) {
//            ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setDisplayHomeAsUpEnabled(true);
//            }
            if(null != back){
                back.setVisibility(View.VISIBLE);
            }
        }
//        if (Build.VERSION.SDK_INT >= 21) {
////            mAppBar.setElevation(10.6f);//设置toolbar的阴影
//        }
    }

    //分享方法
    protected void toShare() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean canBack() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * toast
     */
    public void toast(@NonNull CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast
     */
    public void toast(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }
}
