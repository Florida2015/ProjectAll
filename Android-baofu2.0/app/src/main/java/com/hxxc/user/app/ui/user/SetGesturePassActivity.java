package com.hxxc.user.app.ui.user;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.base.BaseActivity;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.BtnUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by houwen.lai on 2016/12/8.
 * 设置手势密码
 *
 */

public class SetGesturePassActivity extends BaseRxActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    public int getLayoutId() {
        return R.layout.use_set_gesture_pass;
    }

    @Override
    public void initView() {
        toolbar_title.setText("手势密码");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.relative_choise_data})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.relative_choise_data://
                if (BtnUtils.isFastDoubleClick()){

                }
                break;
        }
    }
}
