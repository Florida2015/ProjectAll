package com.hxxc.user.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.hxxc.user.app.R;

public class MyCheckBox extends ImageButton{
	public static final int TYPE_NOMAL = 0;

	public MyCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyCheckBox(Context context) {
		super(context);
		init();
	}

	private int type = TYPE_NOMAL;
	public void setType(int type){
		this.type = type;
		init();
	}

	public boolean toogle = true;
	
	private void init() {
		if(toogle){
			MyCheckBox.this.setImageResource(type == TYPE_NOMAL?R.drawable.ico_yes:R.drawable.agreement_s);
		}else{
			MyCheckBox.this.setImageResource(type == TYPE_NOMAL?R.drawable.ico_no:R.drawable.agreement_n);
		}

		this.post(new Runnable() {
			
			@Override
			public void run() {
				MyCheckBox.this.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						System.out.println("********************toogle"+toogle);
						if (toogle) {
							MyCheckBox.this.setImageResource(type == TYPE_NOMAL?R.drawable.ico_no:R.drawable.agreement_n);
							toogle = false;
						}else {
							MyCheckBox.this.setImageResource(type == TYPE_NOMAL?R.drawable.ico_yes:R.drawable.agreement_s);
							toogle = true;
						}
					}
				});
			}
		});
		
	}

	public void setToggle(boolean toggle){
		toogle = toggle;
		init();
	}
}
