package com.hxxc.user.app.ui.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.SwipeRefreshBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2016/11/29.
 */

public class HtmlActivity extends SwipeRefreshBaseActivity {
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.ll_thumbs)
    LinearLayout ll_thumbs;
    @BindView(R.id.tv_good)
    TextView tv_good;
    @BindView(R.id.tv_bad)
    TextView tv_bad;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private ContentBean content;
    private String title;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_html;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //.发现页面精选文章
        content = (ContentBean) intent.getSerializableExtra("content");
        //.标题
        title = intent.getStringExtra("title");

        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        } else {
            mTitle.setText("华夏信财");
        }

//        assert mSwipeRefreshLayout != null;
//        mSwipeRefreshLayout.setScrollUpChild(tv_content);
        initThumbs();
        if (null != content) {
            tv_content.setText(Html.fromHtml(content.getWapContent()));
        }
    }

    @Override
    protected void onDestroy() {
        if (null != content) {
            doThumbs();
        }
        super.onDestroy();
    }

    @Override
    public void onReflush() {
        super.onReflush();
        tv_content.setText(Html.fromHtml(content.getWapContent()));
    }

    private int thumbs = 0;

    private void initThumbs() {
        ll_thumbs.setVisibility(View.VISIBLE);
        tv_good.setText(content.getGoods() + "");
        tv_bad.setText(content.getNoGoods() + "");
        tv_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == thumbs) {
                    tv_good.setText(content.getGoods() + "");
                    tv_bad.setText(content.getNoGoods() + "");
                    thumbs = 0;
                } else {
                    tv_good.setText(content.getGoods() + 1 + "");
                    tv_bad.setText(content.getNoGoods() + "");
                    thumbs = 1;
                }
            }
        });
        tv_bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (2 == thumbs) {
                    tv_bad.setText(content.getNoGoods() + "");
                    tv_good.setText(content.getGoods() + "");
                    thumbs = 0;
                } else {
                    tv_bad.setText(content.getNoGoods() + 1 + "");
                    tv_good.setText(content.getGoods() + "");
                    thumbs = 2;
                }
            }
        });
    }

    private void doThumbs() {
        if (thumbs == 1) {
            mApiManager.doThumbs1(content.getId() + "", new SimpleCallback<String>() {
                @Override
                public void onNext(String b) {

                }

                @Override
                public void onError() {
                }
            });
        } else if (2 == thumbs) {
            mApiManager.doThumbs2(content.getId() + "", new SimpleCallback<String>() {
                @Override
                public void onNext(String b) {

                }

                @Override
                public void onError() {
                }
            });
        }
    }
}
