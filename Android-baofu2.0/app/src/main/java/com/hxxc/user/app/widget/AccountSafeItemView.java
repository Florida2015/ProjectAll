package com.hxxc.user.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.R;


//自定义组合控件用于AccountSafeActivity
public class AccountSafeItemView extends FrameLayout{

	private ImageView image_left;
	private TextView content_right;


	public AccountSafeItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	public AccountSafeItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}


	public AccountSafeItemView(Context context) {
		super(context);
		initView(context, null);
	}


	private void initView(Context context, AttributeSet attrs) {
		View view = View.inflate(context, R.layout.mine_account_safe_activity_item, this);
		image_left = (ImageView) view.findViewById(R.id.image_left);
		TextView content_left = (TextView) view.findViewById(R.id.content_left);
		content_right = (TextView) view.findViewById(R.id.content_right);
		View line = view.findViewById(R.id.line);
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AccountSafeItemView);
		String leftContent = ta.getString(R.styleable.AccountSafeItemView_content_left);
		String rightContent = ta.getString(R.styleable.AccountSafeItemView_content_right);
		boolean has_line = ta.getBoolean(R.styleable.AccountSafeItemView_has_line, true);
		if (!has_line) {
			line.setVisibility(View.GONE);
		}
		content_left.setText(leftContent);
		content_right.setText(rightContent);
		ta.recycle();
	}
	
	/**
	 * 
	 * @param a int類型，0实名认证  1手机认证 2交易密码 3手势密码
	 * @param b boolean類型 是否認證
	 */
	public void setLeftImage(int a,boolean b){
		
		switch (a) {
		case 0:
			if (b) {
				image_left.setImageResource(R.drawable.ico_name_y);
			}else{
				image_left.setImageResource(R.drawable.ico_name_n);
			}
			break;
		case 1:
			if (b) {
				image_left.setImageResource(R.drawable.ico_mobile_y);
			}else{
				image_left.setImageResource(R.drawable.ico_mobile_n);
			}
			break;
		case 2:
			if (b) {
				image_left.setImageResource(R.drawable.ico_email_y);
			}else{
				image_left.setImageResource(R.drawable.ico_email_n);
			}
			break;
		case 3:
			if (b) {
				image_left.setImageResource(R.drawable.ico_mpwd_y);
			}else{
				image_left.setImageResource(R.drawable.ico_mpwd_n);
			}
			break;
		}
	}
	
	public void setRightContent(String a){
		content_right.setText(a);
	}
}
