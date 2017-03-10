package com.hxxc.user.app.ui.pager;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.widget.CircleImageView;

/**
 * Created by chenqun on 2016/8/17.
 */
public abstract class BaseTitlePager extends BasePager {
    public TextView mTitle;
    public ImageButton mIvShare;
    public Button mButtonRight;
    public TextView mPositionText;
    public RelativeLayout mMessageLayout;
    public CircleImageView imageView;
    public ImageView mUnread;

    public BaseTitlePager(Context context) {
        super(context);
        View rootView = getRootView();
        mTitle = (TextView) rootView.findViewById(R.id.toolbar_title);
        mPositionText = (TextView) rootView.findViewById(R.id.position_text);

        mMessageLayout = (RelativeLayout) rootView.findViewById(R.id.message_layout);
        imageView = (CircleImageView) rootView.findViewById(R.id.imageView);
        mUnread = (ImageView) rootView.findViewById(R.id.munread_img);

        mButtonRight = (Button) rootView.findViewById(R.id.btn_right);
        mIvShare = (ImageButton) rootView. findViewById(R.id.toolbar_iv_share);
        mIvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new ShareUtil((MainActivity2)mContext).setShareContentPre(R.mipmap.icon, Constants.SOCIAL_TITLE, Constants.SOCIAL_CONTENT, HttpRequest.shareLocation).postShare();
            }
        });
        setTitle();
    }
    protected abstract void setTitle();


}
