package com.hxxc.user.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;

public class TransactionInfoItem extends FrameLayout{

	private TextView tv_left;
	private TextView tv_right;

	public TransactionInfoItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TransactionInfoItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}


	public TransactionInfoItem(Context context) {
		super(context);
	}

	private void init(Context context, AttributeSet attrs) {
		View.inflate(context, R.layout.item_transactioninfo, this);
		tv_left = (TextView) findViewById(R.id.tv_left);
		tv_right = (TextView) findViewById(R.id.tv_right);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TransactionInfo);
		String left = ta.getString(R.styleable.TransactionInfo_leftText);
		String right = ta.getString(R.styleable.TransactionInfo_rightText);
		ta.recycle();
		tv_left.setText(left);
		tv_right.setText(right);
	}
	
	public void setLeftText(String str){
		tv_left.setText(str);
	}

	public void setRightText(String str){
		tv_right.setText(str);
	}
}
