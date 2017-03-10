package com.hxxc.user.app.ui.mine.invitation;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.data.bean.InvitationRecordBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.share.ShareActivity;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.MoneyUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/10/27.
 * 邀请好友
 */

public class InvitationFriendsActivity extends BaseRxActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.img_invitation)
    ImageView img_invitation;

    @BindView(R.id.relative_invitation_reward)
    RelativeLayout relative_invitation_reward;
    @BindView(R.id.relative_invitation_detail)
    RelativeLayout relative_invitation_detail;

    @BindView(R.id.tv_order_detail_item_name)
    TextView tv_order_detail_item_name;
    @BindView(R.id.tv_order_detail_item_detail)
    TextView tv_order_detail_item_detail;

    @Override
    public int getLayoutId() {
        return R.layout.mine_invitation;
    }

    @Override
    public void initView() {
        toolbar_title.setText("邀请好友");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        AppManager.getAppManager().addActivity(this);

        tv_order_detail_item_name.setText("0.00元");
        tv_order_detail_item_detail.setText("0人");

        getInviteDate();

        getInviteShareUrl();

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

    @OnClick({R.id.img_invitation, R.id.relative_invitation_reward, R.id.relative_invitation_detail, R.id.btn_invitation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_invitation://图片
                if (BtnUtils.isFastDoubleClick()) {
                    startActivity(new Intent(mContext, AdsActivity.class)
                            .putExtra("title", TextUtils.isEmpty(connectTitle)?"分享活动":connectTitle)
                            .putExtra("shareUrlFrom", 5)
                            .putExtra("isShare", true));
                }
                break;
            case R.id.relative_invitation_reward://邀请奖励
                if (BtnUtils.isFastDoubleClick()) {
                    startActivity(new Intent(mContext, InvitationRecordActivity.class).putExtra("index", 0).putExtra("bean", bean));

                }
                break;
            case R.id.relative_invitation_detail://邀请明细
                if (BtnUtils.isFastDoubleClick()) {
                    startActivity(new Intent(mContext, InvitationRecordActivity.class).putExtra("index", 1).putExtra("bean", bean));
                }
                break;
            case R.id.btn_invitation://邀请好友赚钱
                if (BtnUtils.isFastDoubleClick()) {//分享
                    startActivity(new Intent(mContext, ShareActivity.class).
                            putExtra("title", TextUtils.isEmpty(shareTitle) ? "邀友" : shareTitle).
                            putExtra("des", TextUtils.isEmpty(shareDes) ? "邀友" : shareDes).
                            putExtra("url", TextUtils.isEmpty(shareUrl) ? "" : shareUrl).
                            putExtra("img", TextUtils.isEmpty(shareImg) ? "" : shareImg));
                }
                break;
        }
    }

    InvitationRecordBean bean;

    public void getInviteDate() {
        Api.getClient().getGetUsrInviteCount(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<InvitationRecordBean>(mContext) {
                    @Override
                    public void onSuccess(InvitationRecordBean invitationRecordBean) {
                        bean = invitationRecordBean;
                        tv_order_detail_item_name.setText(MoneyUtil.addComma(invitationRecordBean.getInvitation(), 2, ROUND_FLOOR) + "元");
                        tv_order_detail_item_detail.setText(invitationRecordBean.getInvitationTotalNumber() + "人");
                    }

                    @Override
                    public void onFail(String fail) {

                    }
                });
    }

    private String shareTitle;
    private String shareDes;
    private String shareUrl;
    private String shareImg;

    private String connectTitle;
    private String connectUrl;

    //获取分享信息
    public void getInviteShareUrl() {
        Api.getClientNo().getIndexAdsLists("18", "2").compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<IndexAds>>(mContext) {
                    @Override
                    public void onSuccess(List<IndexAds> indexAdses) {

                        if (indexAdses != null && indexAdses.size() > 0) {
                            //
                            Picasso.with(mContext).load(indexAdses.get(0).getRealPictureSourceUrl()).into(img_invitation);

                            if (indexAdses.get(0).getShareVo() != null) {
                                shareTitle = indexAdses.get(0).getShareVo().getShareTitle();
                                shareDes = indexAdses.get(0).getShareVo().getShareContents();
                                shareUrl = indexAdses.get(0).getShareVo().getActivityUrl();
                                shareImg = indexAdses.get(0).getShareVo().getRealShareIcon();

                                connectTitle = indexAdses.get(0).getName();
                                connectUrl = indexAdses.get(0).getConnectUrl();
                            }
                        }
                    }
                });
    }
}
