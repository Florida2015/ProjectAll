package com.hxxc.huaxing.app.wedgit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.hxxc.huaxing.app.R;

public class MyCheckBox extends ImageButton{

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

	public boolean toogle = true;
	
	private void init() {
		MyCheckBox.this.setImageResource(R.mipmap.icon_chox_y);
		this.post(new Runnable() {
			
			@Override
			public void run() {
				MyCheckBox.this.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						System.out.println("********************toogle"+toogle);
						if (toogle) {
							MyCheckBox.this.setImageResource(R.mipmap.icon_chox_n);
							toogle = false;
						}else {
							MyCheckBox.this.setImageResource(R.mipmap.icon_chox_y);
							toogle = true;
						}
					}
				});
			}
		});
		
	}

}
