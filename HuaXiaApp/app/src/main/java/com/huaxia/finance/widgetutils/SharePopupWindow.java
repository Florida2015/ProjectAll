package com.huaxia.finance.widgetutils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.huaxia.finance.R;

/**
 * 弹出分享
 * Created by houwen.lai on 2015/11/25.
 */
public class SharePopupWindow extends PopupWindow {


    private Button btn_weixin_circle;
    private Button btn_weixin_friends;
    private Button btn_weibo;

    private View mMenuView;

    public SharePopupWindow(final Activity context,View.OnClickListener itemsOnClick) {
        super(context);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.share_activity, null);

        mMenuView = LayoutInflater.from(context).inflate(R.layout.share_activity, null);

        btn_weixin_circle = (Button) mMenuView.findViewById(R.id.btn_weixin_circle);
        btn_weixin_friends = (Button) mMenuView.findViewById(R.id.btn_weixin_friends);
        btn_weibo = (Button) mMenuView.findViewById(R.id.btn_weibo);

        //设置按钮监听
        btn_weixin_circle.setOnClickListener(itemsOnClick);
        btn_weixin_friends.setOnClickListener(itemsOnClick);
        btn_weibo.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_buttom);
        //实例化一个ColorDrawable颜色为半透明0xb0000000
        ColorDrawable dw = new ColorDrawable(0xe0000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        darkenBackgroud(context,0.4f);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.relative_share).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackgroud(context,1f);
            }
        });
    }

    private void darkenBackgroud(Activity activity,Float bgcolor) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

}