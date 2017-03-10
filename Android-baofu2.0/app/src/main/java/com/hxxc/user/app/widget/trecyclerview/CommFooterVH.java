package com.hxxc.user.app.widget.trecyclerview;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.LogUtil;

import java.util.Map;

public class CommFooterVH extends BaseViewHolder<Object> {

    public LinearLayout ll_load_more;
    public LinearLayout ll_emptyview;

    public ProgressBar progressbar;
    public TextView tv_state;
    public static final int LAYOUT_TYPE = R.layout.list_footer_view;

//    @BindView(R.id.progressbar)
//    ProgressBar progressbar;
//    @BindView(R.id.tv_state)
//    TextView tv_state;

    public CommFooterVH(View view) {
        super(view);
    }

    @Override
    public int getType() {
        return LAYOUT_TYPE;
    }

    @Override
    public void onBindViewHolder(View view,int position,Object o) {

//        boolean isHasMore = (o == null ? false : true);
//        progressbar.setVisibility(isHasMore ?View.VISIBLE :View.GONE);//
//        tv_state.setText(isHasMore ? "正在加载":(position==0?"":""));//(position==0?"暂无数据":"已经到底")

    }

    @Override
    public void onBindViewHolder(View view, int position, Object obj, Map<String, String> param, boolean isHasMore, int headerCount) {
        super.onBindViewHolder(view, position, obj, param, isHasMore, headerCount);
        LogUtil.d("onBindViewHolder commfooter position="+position + "_isHasMore="+isHasMore+"_headerCount=" + headerCount);
        if (!isHasMore){
            if (headerCount==0&&position==0){
                ll_load_more.setVisibility(View.GONE);
                ll_emptyview.setVisibility(View.VISIBLE);
            }else if(headerCount==0&&position>0){
                ll_load_more.setVisibility(View.VISIBLE);
                ll_emptyview.setVisibility(View.GONE);
                progressbar.setVisibility(isHasMore ?View.VISIBLE :View.GONE);//
                tv_state.setText(isHasMore ? "正在加载":(position==0?"暂无数据":"已经到底"));//(position==0?"暂无数据":"已经到底")
            } else if(headerCount>0&&(position==0||position==1)){
                ll_load_more.setVisibility(View.GONE);
                ll_emptyview.setVisibility(View.VISIBLE);
            }else{
                ll_load_more.setVisibility(View.VISIBLE);
                ll_emptyview.setVisibility(View.GONE);
                progressbar.setVisibility(isHasMore ?View.VISIBLE :View.GONE);//
                tv_state.setText(isHasMore ? "正在加载":(position==0?"暂无数据":(position==1?"暂无数据":"已经到底")));//(position==0?"暂无数据":"已经到底")
            }
        }else {
            ll_load_more.setVisibility(View.VISIBLE);
            ll_emptyview.setVisibility(View.GONE);
            progressbar.setVisibility(isHasMore ?View.VISIBLE :View.GONE);//
            tv_state.setText(isHasMore ? "正在加载":(position==0?"暂无数据":(position==1?"暂无数据":"已经到底")));//(position==0?"暂无数据":"已经到底")
        }
    }

}