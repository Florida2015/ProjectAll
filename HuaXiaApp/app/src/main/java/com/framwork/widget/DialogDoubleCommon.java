package com.framwork.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huaxia.finance.R;

/**
 * 
 * @author wwt
 * 类说明：自定义弹窗 2个按钮的弹窗
 */
public class DialogDoubleCommon {

	private Dialog myDialog;
	private Button buttonLeft;
	private Button buttonRight;
	private OnClickListener dialogLeftBtnListener;
	private OnClickListener dialogRightBtnListener;

	private TextView titleView;
	private TextView contextView;

    private EditText ed_text;

	public DialogDoubleCommon(Context mContext) {
		createView(mContext);
	}

	private void createView(Context mContext) {
		myDialog = new Dialog(mContext, R.style.DefaultDialog);
		View dialogView = View.inflate(mContext, R.layout.common_dialog_hint_double, null);
		buttonLeft = (Button) dialogView.findViewById(R.id.button_left);
		buttonLeft.setText("取消");
		buttonRight = (Button) dialogView.findViewById(R.id.button_right);
		buttonRight.setText("确定");
		titleView = (TextView) dialogView.findViewById(R.id.hint_1);
		titleView.setText("提示");
        titleView.setVisibility(View.GONE);
		contextView = (TextView) dialogView.findViewById(R.id.hint_2);
        contextView.setVisibility(View.GONE);
        ed_text = (EditText) dialogView.findViewById(R.id.ed_text);
        ed_text.setVisibility(View.GONE);
		myDialog.setContentView(dialogView);
		myDialog.setCancelable(true);
		myDialog.setCanceledOnTouchOutside(true);
	}

	/**
	 * 设置左按钮的文本
	 * 
	 * @param txtStr
	 */
	public DialogDoubleCommon setLeftBtnText(String txtStr) {
		if (buttonLeft != null) {
			buttonLeft.setText(txtStr);
		}
		return this;
	}
	public DialogDoubleCommon setLeftBtnTextColor(int color) {
		if (buttonLeft != null) {
			buttonLeft.setTextColor(color);
		}
		return this;
	}
	/**
	 * 设置右按钮的文本
	 * 
	 * @param txtStr
	 */
	public DialogDoubleCommon setRightBtnText(String txtStr) {
		if (buttonRight != null) {
			buttonRight.setText(txtStr);
		}
		return this;
	}
	public DialogDoubleCommon setRightBtnTextColor(int color) {
		if (buttonRight != null) {
			buttonRight.setTextColor(color);
		}
		return this;
	}
	/**
	 * 设置标题的文本
	 * 
	 * @param txtStr
	 */
	public DialogDoubleCommon setTitleText(String txtStr) {
		if (titleView != null) {
            if (TextUtils.isEmpty(txtStr)){
                titleView.setVisibility(View.GONE);
            }else {
                titleView.setVisibility(View.VISIBLE);
                titleView.setText(txtStr);
            }
		}
		return this;
	}

	/**
	 * 设置主体的文本
	 * 
	 * @param txtStr
	 */
	public DialogDoubleCommon setContxtText(String txtStr) {
        if (contextView!=null){
            if (TextUtils.isEmpty(txtStr)){
                contextView.setVisibility(View.GONE);
            }else {
                contextView.setVisibility(View.VISIBLE);
                contextView.setText(txtStr);
            }
        }
		return this;
	}

    /**
     *
     */
    public void setEd_textVisiable(int visiable){
        if (ed_text!=null){
            ed_text.setVisibility(visiable);
        }
    }

    /**
     *
     */
    public EditText getEditView(){
        if (ed_text!=null)
        return ed_text;
        else return null;
    }


	/**
	 * 设置左按钮的事件
	 * 
	 * @param btnListener
	 */
	public DialogDoubleCommon setLeftBtnListener(OnClickListener btnListener) {
		dialogLeftBtnListener = btnListener;
		buttonLeft.setOnClickListener(btnListener);
		return this;
	}

	/**
	 * 设置右按钮的事件
	 * 
	 * @param btnListener
	 */
	public DialogDoubleCommon setRightBtnListener(OnClickListener btnListener) {
		dialogRightBtnListener = btnListener;
		buttonRight.setOnClickListener(btnListener);
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

	private OnClickListener onDimessClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onDimess();
		}
	};

	public boolean isShowing() {
		if (	myDialog!=null) {
		   return  myDialog.isShowing();
		}else{
			return false;
		}
	}

	/**
	 * 弹出
	 */
	public void onShow() {
		if (myDialog != null) {
			if (dialogLeftBtnListener == null) {
				setLeftBtnListener(onDimessClickListener);
			}
			if (dialogRightBtnListener == null) {
				setRightBtnListener(onDimessClickListener);
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