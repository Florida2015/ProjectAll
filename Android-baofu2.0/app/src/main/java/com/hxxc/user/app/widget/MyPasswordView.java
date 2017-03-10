package com.hxxc.user.app.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hxxc.user.app.R;

public class MyPasswordView extends LinearLayout{

	public EditText password_edit;
	public ImageButton imagebutton;

	public MyPasswordView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyPasswordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyPasswordView(Context context) {
		super(context);
		init(context);
	}

	public boolean toogle = false;
	private void init(Context context) {
		View.inflate(context, R.layout.item_password, this);
		password_edit = (EditText) findViewById(R.id.password_edit);
		imagebutton = (ImageButton) findViewById(R.id.imagebutton);
		imagebutton.setImageResource(R.drawable.close);
		this.post(new Runnable() {
			
			@Override
			public void run() {
				imagebutton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (toogle) {
							imagebutton.setImageResource(R.drawable.close);
							toogle = false;
							password_edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
						}else {
							imagebutton.setImageResource(R.drawable.open);
							toogle = true;
							password_edit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
						}
						password_edit.setSelection(password_edit.getText().toString().trim().length());
					}
				});
			}
		});
			
	}

}
