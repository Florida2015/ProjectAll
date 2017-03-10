package com.hxxc.user.app.ui.vh;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.EquityNoteBean;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

/**
 * Created by houwen.lai on 2016/11/3.
 *
 */

public class EquityNoteVH extends BaseViewHolder<EquityNoteBean> {

    View view_equity;
    LinearLayout linear_equity_note;
    TextView tv_equity_title;
    TextView tv_equity_context;

    public EquityNoteVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.equity_note_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, EquityNoteBean obj) {
        if (position==0)view_equity.setVisibility(View.VISIBLE);
            else view_equity.setVisibility(View.GONE);

        if (position%2==0)linear_equity_note.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        else linear_equity_note.setBackgroundColor(mContext.getResources().getColor(R.color.grey_fafa));

        tv_equity_title.setText("标题");
        tv_equity_context.setText("标题");
    }
}
