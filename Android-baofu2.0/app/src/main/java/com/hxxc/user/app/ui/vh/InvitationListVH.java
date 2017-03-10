package com.hxxc.user.app.ui.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.InvitationListBean;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

/**
 * Created by houwen.lai on 2016/11/21.
 * 邀友记录
 */

public class InvitationListVH extends BaseViewHolder<InvitationListBean> {

    RelativeLayout relative_invitation_record_title;

    TextView tv_list_name;
    TextView tv_list_date;

    public InvitationListVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.invitation_record_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, InvitationListBean obj) {

        if (position==0)relative_invitation_record_title.setVisibility(View.VISIBLE);
        else relative_invitation_record_title.setVisibility(View.GONE);

        tv_list_name.setText(obj.getUserVo()!=null?
                (TextUtils.isEmpty(obj.getUserVo().getMaskUserName())?obj.getUserVo().getMobileMask():obj.getUserVo().getMaskUserName()):"");

        tv_list_date.setText(obj.getCreateTimeStr());//DateUtil.getmstodate(obj.getCreateTime(),DateUtil.YYYYMMDDHHMMSS)

    }
}
