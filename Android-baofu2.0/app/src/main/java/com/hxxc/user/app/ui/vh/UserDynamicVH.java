package com.hxxc.user.app.ui.vh;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.Event.MineEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.UserDynamicBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.mine.assetsrecord.ClanderActivity;
import com.hxxc.user.app.ui.mine.tradelist.TradeListActivity;
import com.hxxc.user.app.ui.order.OrderDetailActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/11/18.
 * <p>
 * 我的 获取动态消息列表
 */

public class UserDynamicVH extends BaseViewHolder<UserDynamicBean> {

    ImageView img_home_icon;
    TextView tv_home_mine_title;
    TextView tv_home_mine_time;

    ImageView img_mine_tip;

    TextView tv_home_mine_context;
    Button btn_text_mine;

    public UserDynamicVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.home_mine_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, UserDynamicBean obj) {

//        img_home_icon
        Picasso.with(mContext)
                .load(obj.getRealMobileIconUrl())
                .placeholder(R.mipmap.icon_my_head)
                .error(R.mipmap.icon_my_head)
                .into(img_home_icon);

        tv_home_mine_title.setText(obj.getTitle());
        tv_home_mine_time.setText(DateUtil.getmstodate(obj.getCreateTime(), DateUtil.YYYYMMDDHHMMSS));

        tv_home_mine_context.setText(obj.getContents());

        if (TextUtils.isEmpty(obj.getBizTypeStr())){
            btn_text_mine.setText("查看");
        }else if (obj.getBizTypeStr().contains("1.0")) {//1:系统 认证，应该跳转去认证的页面
            btn_text_mine.setText("查看");
        } else if (obj.getBizTypeStr().contains("2.0")) {//2是红包，应该跳转产品列表页面让用户去购买产品
            btn_text_mine.setText("立即使用");
        } else if (obj.getBizTypeStr().contains("3.0")) {//3是订单，如果是订单，会有个bizVlue，这值是订单编号，你们通过它跳转到订单详细页面
            btn_text_mine.setText("查看出借状态");
        } else if(obj.getBizTypeStr().contains("2.2")){//查看账单 账单2.2
            btn_text_mine.setText("查看");
        }  else if(obj.getBizTypeStr().contains("2.1")){//查看日历 2.1
            btn_text_mine.setText("查看");
        } else {
            btn_text_mine.setText("查看");
        }

        btn_text_mine.setOnClickListener(new OnClickListener() {//查看
            @Override
            public void onClick(View view) {
                if (BtnUtils.isFastDoubleClick()){
                if (!TextUtils.isEmpty(obj.getBizTypeStr())&&obj.getBizTypeStr().contains("1.0")) {//1:系统 认证，应该跳转去认证的页面
                    readMessageId(obj.getId()+"");
                    mContext.startActivity(new Intent(mContext, AuthenticationActivity.class));
                } else if (!TextUtils.isEmpty(obj.getBizTypeStr())&&obj.getBizTypeStr().contains("2.0")) {//2是红包，应该跳转产品列表页面让用户去购买产品
//                    mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("pid",obj.getBizValue()));
//                    readMessageId(obj.getId()+"");
                    httpIgroneMessage(obj.getId() + "");
                    EventBus.getDefault().post(new MainEvent(1).setLoginType(MainEvent.FROMFINDPASSWORD));
                } else if (!TextUtils.isEmpty(obj.getBizTypeStr())&&obj.getBizTypeStr().contains("3.0")) {//3是订单，如果是订单，会有个bizVlue，这值是订单编号，你们通过它跳转到订单详细页面
                    readMessageId(obj.getId()+"");
                    mContext.startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("orderNo", obj.getBizValue()));
                }else if(!TextUtils.isEmpty(obj.getBizTypeStr())&&obj.getBizTypeStr().contains("2.2")){//查看账单
                    readMessageId(obj.getId()+"");
                    mContext.startActivity(new Intent(mContext, TradeListActivity.class));
                } else if(!TextUtils.isEmpty(obj.getBizTypeStr())&&obj.getBizTypeStr().contains("2.1")){//查看日历
                    readMessageId(obj.getId()+"");
                    try {
                        JSONObject  object = new JSONObject(obj.getBizValue());
                        String endTime = object.getString("endTime");
                        int endTimeMonth = object.getInt("endTimeMonth");

                        mContext.startActivity(new Intent(mContext, ClanderActivity.class).
                                putExtra("flagIndex", true).putExtra("endTimeMonthStr",""+endTimeMonth).putExtra("end_time",endTime));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
//                    httpIgroneMessage(obj.getId() + "");
                    readMessageId(obj.getId()+"");
                }
                }
            }
        });
        img_mine_tip.setOnClickListener(new OnClickListener() {//忽略
            @Override
            public void onClick(View view) {
                makePopWindow(img_mine_tip, obj);

            }
        });
    }

    /**
     * popwindow
     * tv_popop_window
     */
    private SharePopupWindow sharePopupWindow;

    private void makePopWindow(View mView, UserDynamicBean obj) {
        sharePopupWindow = new SharePopupWindow(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpIgroneMessage(obj.getId() + "");
//                sharePopupWindow.dismiss();
            }
        });
        ////监听窗口的焦点事件，点击窗口外面则取消显示
        sharePopupWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                sharePopupWindow.dismiss();
            }
        });
        //设置默认获取焦点
        sharePopupWindow.setFocusable(true);
//以某个控件的x和y的偏移量位置开始显示窗口
        sharePopupWindow.showAsDropDown(mView, 0, -138);
//        sharePopupWindow.showAtLocation(mView, Gravity.BOTTOM,mView.getTop(),mView.getRight());

//如果窗口存在，则更新
//        sharePopupWindow.update();
        sharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                EventBus.getDefault().post(new MainEvent(1.0f).setLoginType(MainEvent.FROM_MINE_PAGER));
            }
        });
    }

    public class SharePopupWindow extends PopupWindow {

        private TextView tv_pop;

        private View mMenuView;

        public SharePopupWindow(Context context, View.OnClickListener itemsOnClick) {
            super(context);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.share_activity, null);

            mMenuView = LayoutInflater.from(context).inflate(R.layout.popop_window, null);
            tv_pop = (TextView) mMenuView.findViewById(R.id.tv_popop_window);

            //设置按钮监听
            tv_pop.setOnClickListener(itemsOnClick);
            //设置SelectPicPopupWindow的View
            this.setContentView(mMenuView);
            //设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
            //设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            //设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            //设置SelectPicPopupWindow弹出窗体动画效果
            this.setAnimationStyle(R.style.AnimTools);
            //实例化一个ColorDrawable颜色为半透明0xb0000000
            ColorDrawable dw = new ColorDrawable(0x00000000);//
//        //设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);

            EventBus.getDefault().post(new MainEvent(0.4f).setLoginType(MainEvent.FROM_MINE_PAGER));

            //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//            mMenuView.setOnTouchListener(new View.OnTouchListener() {
//
//                public boolean onTouch(View v, MotionEvent event) {
//
//                    int height = mMenuView.findViewById(R.id.relative_share).getTop();
//                    int y = (int) event.getY();
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        if (y < height) {
//                            dismiss();
//                        }
//                    }
//                    return true;
//                }
//            });
//            this.setOnDismissListener(new OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    darkenBackgroud(context, 1f);
//                }
//            });
        }

    }

    /**
     * 忽略消息
     */
    public void httpIgroneMessage(String sid) {
        Api.getClient().getIgnoewUserDynamic(Api.uid, sid).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResult<String> baseBean) {
                if (baseBean.getSuccess()) {
                    ToastUtil.ToastShort(mContext, "该消息已忽略");
                    if (sharePopupWindow != null && sharePopupWindow.isShowing()) {
                        sharePopupWindow.dismiss();
                    }
                    EventBus.getDefault().post(new MineEvent());
                }
            }
        });
    }

    /**
     * 我 根据dybId和uid读取动态消息-(需要登陆)
     */
    public void readMessageId(String dId) {
        Api.getClient().getReadUserBizinfo(Api.uid, dId).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResult<String> stringBaseResult) {
                if (stringBaseResult.getSuccess()) {
                    EventBus.getDefault().post(new MineEvent());
//                    ToastUtil.ToastShort(mContext,"该消息已读");
                }
            }
        });

    }

}
