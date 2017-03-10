package com.framwork.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.huaxia.finance.R;

/**
 * 
 * @author wwt 类说明：自定义只有一个按钮的弹窗
 */
public class DialogOneCommon {

	public Dialog myDialog;
	private Button button;
	private OnClickListener dialogBtnListener;
	private TextView titleView;
	private TextView contextView;

    private static boolean flag = true;

	public DialogOneCommon(Context mContext) {
		createView(mContext);
	}

	private void createView(Context mContext) {
		myDialog = new Dialog(mContext, R.style.DefaultDialog);
		View dialogView = View.inflate(mContext, R.layout.common_dialog_single,
				null);
		button = (Button) dialogView.findViewById(R.id.button_ok);
		button.setText("确定");
		titleView = (TextView) dialogView.findViewById(R.id.hint_1);
        titleView.setVisibility(View.GONE);
		titleView.setText("提示");
		contextView = (TextView) dialogView.findViewById(R.id.hint_2);
        contextView.setVisibility(View.GONE);
		myDialog.setContentView(dialogView);
		myDialog.setCancelable(true);
		myDialog.setCanceledOnTouchOutside(flag);
	}

    /**
     *
     */
    public void setCanceledOnTouchOutside(boolean flag){
        if(myDialog!=null){
            myDialog.setCanceledOnTouchOutside(flag);
        }
    }

    /**
     * 当dialog显示时，监听activity的back键方法
     * @param listener
     */
    public void setOnCancelListener(DialogInterface.OnCancelListener listener){
        if(myDialog!=null){
            myDialog.setOnCancelListener(listener);
        }
    }
    /**
     * Android 如何解决dialog弹出时无法捕捉Activity的back事件
     * 当dialog显示时，监听activity的back键方法
     * @param listener
     */
    public void setOnKeyListener(DialogInterface.OnKeyListener listener){
        if(myDialog!=null){
            myDialog.setOnKeyListener(listener);
        }
    }

//    oneCommon.setOnKeyListener(new DialogInterface.OnKeyListener() {
//        @Override
//        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//            if(keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
//            {
//                dialog.dismiss();
//            }
//            return false;
//        }
//    });

	/**
	 * 设置按钮的文本
	 * 
	 * @param txtStr
	 */
	public DialogOneCommon setBtnText(String txtStr) {
		if (button != null && txtStr != null) {
			button.setText(txtStr);
		}
		return this;
	}

	/**
	 * 设置标题的文本
	 * 
	 * @param txtStr
	 */
	public DialogOneCommon setTitleText(String txtStr) {
		if (titleView != null && txtStr != null) {
            titleView.setVisibility(View.VISIBLE);
			titleView.setText(txtStr);
		}else {
            titleView.setVisibility(View.GONE);
        }
		return this;
	}

	/**
	 * 是否显示 title
	 * 
	 * @param flag
	 * @return
	 */
	public DialogOneCommon setTitleTextShow(boolean flag) {
		if (titleView != null) {
			if (flag) {
				titleView.setVisibility(View.VISIBLE);
			} else {
				titleView.setVisibility(View.GONE);
			}
		}
		return this;
	}

	/**
	 * 设置主体的文本
	 * 
	 * @param txtStr
	 */
	public DialogOneCommon setContxtText(String txtStr) {
		if (contextView != null&& !TextUtils.isEmpty(txtStr)) {
            contextView.setVisibility(View.VISIBLE);
			contextView.setText(txtStr);
		}else contextView.setVisibility(View.GONE);
		return this;
	}

	/**
	 * 是否显示 ContxtText
	 * 
	 * @param flag
	 * @return
	 */
	public DialogOneCommon setContxtTextShow(boolean flag) {
		if (contextView != null) {
			if (flag) {
				contextView.setVisibility(View.VISIBLE);
			} else {
				contextView.setVisibility(View.GONE);
			}
		}
		return this;
	}

	/**
	 * 设置按钮的事件
	 * 
	 * @param btnListener
	 */
	public DialogOneCommon setBtnListener(OnClickListener btnListener) {
		dialogBtnListener = btnListener;
		button.setOnClickListener(btnListener);
		return this;
	}

	/**
	 * 获取弹窗对象
	 * 
	 * @return
	 */
	public Dialog getMyDialog() {
		return myDialog;
	}

    public boolean isOnShow(){
        if (myDialog != null){
            return myDialog.isShowing();
        }
        return false;
    }

	/**
	 * 弹出
	 */
	public void onShow() {
		if (myDialog != null) {
			if (dialogBtnListener == null) {
				setBtnListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						onDimess();
					}
				});
			}
			myDialog.show();

		}
	}

	public void onDimess() {
		if (myDialog != null) {
			myDialog.dismiss();
		}
	}

}