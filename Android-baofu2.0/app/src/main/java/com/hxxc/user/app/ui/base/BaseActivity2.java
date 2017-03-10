package com.hxxc.user.app.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;

/**
 * Created by chenqun on 2017/2/22.
 */

public class BaseActivity2 extends BaseActivity {
    private TextView title;
    private ImageButton back;
    private LinearLayout rootLayout;
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        // 经测试在代码里直接声明透明状态栏更有效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        initToolbar();
    }

    private void initToolbar() {
        back = (ImageButton) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.toolbar_title);

        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setBackInvisible() {
        if (null != back) back.setVisibility(View.GONE);
    }

    protected void setTitle(String msg) {
        if (title != null) {
            title.setText(msg);
        }
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (rootLayout == null) return;
        rootLayout.addView(view, params);
    }
}
