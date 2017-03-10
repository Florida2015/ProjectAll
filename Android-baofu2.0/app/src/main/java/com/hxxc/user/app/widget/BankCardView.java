package com.hxxc.user.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;

public class BankCardView extends LinearLayout{

	private ImageView bank_icon_img;
	private TextView bank_name_text;
	private TextView user_name_text;
	private TextView card_no_text;
	private View view;
	private LinearLayout viewBackground;

	public BankCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BankCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public BankCardView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		view = View.inflate(context, R.layout.view_bankcard, this);
		viewBackground = (LinearLayout) findViewById(R.id.view);
		bank_icon_img = (ImageView) findViewById(R.id.bank_icon_img);
		bank_name_text = (TextView) findViewById(R.id.bank_name_text);
		user_name_text = (TextView) findViewById(R.id.user_name_text);
		card_no_text = (TextView) findViewById(R.id.card_no_text);
	}
	
	public void setBankIcon(int src){
		bank_icon_img.setImageResource(src);
	}
	public void setBankName(String name){
		bank_name_text.setText(name);
	}
	public void setUserName(String name){
		user_name_text.setText(name);
	}
	public void setCardNo(String card){
		card_no_text.setText(card);
	}
	public void setBackground(int src){
		viewBackground.setBackgroundResource(src);
	}
}
