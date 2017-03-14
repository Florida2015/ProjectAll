package com.wujay.fund.test;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wujay.fund.R;
import com.wujay.fund.view.LockPatternUtils;
import com.wujay.fund.view.LockPatternView;

import java.util.List;

/**
 * 解锁手势密码
 */
public class UnlockGesturePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    public static final  int From_GestureManager = 888;
    private LockPatternView mLockPatternView;
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;
    private TextView mHeadTextView;
    private Animation mShakeAnim;

    private Toast mToast;
    private TextView tv_des;
    private int from;
    private LockPatternUtils lockPatternUtils;

    private void showToast(CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturepassword_unlock);
        lockPatternUtils= new LockPatternUtils(this);

        from = getIntent().getIntExtra("from", 0);
        Button gesturepwd_unlock_forget = (Button) findViewById(R.id.gesturepwd_unlock_forget);
        gesturepwd_unlock_forget.setOnClickListener(this);

        Button btn_change_account = (Button) findViewById(R.id.btn_change_account);
        btn_change_account.setOnClickListener(this);

        mLockPatternView = (LockPatternView) this
                .findViewById(R.id.gesturepwd_unlock_lockview);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);


//        UserInfo userInfo = SPUtils.geTinstance().getUserInfo();
//        if (null != userInfo) {
//            CircleImageView icon = (CircleImageView) findViewById(R.id.icon);
//            TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
//            Picasso.with(this).load(userInfo.getRealIcon()).error(R.drawable.gestures_to_head).placeholder(R.drawable.gestures_to_head).into(icon);
//            tv_phone.setText(StringUtil.getRatStr2(SPUtils.geTinstance().getUserName()));
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (!HXXCApplication.getInstance().getLockPatternUtils().savedPatternExists()) {
//			startActivity(new Intent(this, GuideGesturePasswordActivity.class));
//            finish();
//        }
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        if (null != dialog) {
            dialog.dismiss();
        }
        super.onDestroy();
        if (mCountdownTimer != null)
            mCountdownTimer.cancel();
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        @Override
        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            if (pattern == null)
                return;
            Log.i("way", "result = " + pattern.toString());
            if (lockPatternUtils.checkPattern(pattern)) {//验证手势密码
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Correct);
                //TODO
                //解锁成功后的操作
//                HXXCApplication.getInstance().setIsInBackground(false);
                if(from == From_GestureManager){
//                    EventBus.getDefault().post(new GestureManagerEvent(GestureManagerEvent.From_UnlockGesture).setPatterns(LockPatternUtils.patternToStrings(pattern)));
                }else{
                    showToast("解锁成功");
//                    EventBus.getDefault().post(new MainEvent(3).setLoginType(MainEvent.FROMFINDPASSWORD));
                }
//                finish();
            } else {
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Wrong);
                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                            - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry >= 0) {
                        if (retry == 0)
                            showToast("您已5次输错密码，请30秒后再试");
                        mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
                        mHeadTextView.setTextColor(Color.parseColor("#fffbb02b"));
                        mHeadTextView.startAnimation(mShakeAnim);
                    }

                } else {
                    showToast("输入长度不够，请重试");
                }

                if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
//                    mHandler.postDelayed(attemptLockout, 2000);

//                    ThreadManager.getShortPool().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            EventBus.getDefault().post(new GestureEvent(-2));
//
//                            for (int i = 31; i >= -1 ; i--) {
//                                EventBus.getDefault().post(new GestureEvent(i));
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });

                } else {
                    mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
                }
            }
        }

        private void patternInProgress() {
        }
    };
    Runnable attemptLockout = new Runnable() {

        @Override
        public void run() {
            mLockPatternView.clearPattern();
            mLockPatternView.setEnabled(false);
            mCountdownTimer = new CountDownTimer(
                    LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
                    if (secondsRemaining > 0) {
                        mHeadTextView.setText(secondsRemaining + " 秒后重试");
                    } else {
                        mHeadTextView.setText("请绘制手势密码");
                        mHeadTextView.setTextColor(Color.WHITE);
                    }

                }

                @Override
                public void onFinish() {
                    mLockPatternView.setEnabled(true);
                    mFailedPatternAttemptsSinceLastTimeout = 0;
                }
            }.start();
        }
    };
    private RelativeLayout title_layout;

    private Dialog dialog;

    @Override
    public void onClick(View v) {
//        if (BtnUtils.isFastDoubleClick()) {
            if (dialog == null) {
//                dialog = new Dialog(this, R.style.loadingDialogTheme);
//                View inflate = View.inflate(this, R.layout.dialog_hint, null);
//                tv_des = (TextView) inflate.findViewById(R.id.tv_des);
//                inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (null != dialog) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//                inflate.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(UnlockGesturePasswordActivity.this, LoginActivity.class);
//                        if (clickType == 0) {
//                            intent.putExtra("startType", LoginActivity.NoRestartMain);
//                            intent.putExtra("todo", "gesture");
//                        }else{
//                            intent.putExtra("todo", "changeAccount");
//                        }
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//                dialog.setContentView(inflate);
            }
            switch (v.getId()) {
                case R.id.gesturepwd_unlock_forget:
                    tv_des.setText("忘记手势密码，需重新登录");
                    clickType = 0;
                    break;
                case R.id.btn_change_account:
                    tv_des.setText("切换账号会注销已登录账号，是否继续");
                    clickType = 1;
                    break;
            }
            dialog.show();
//        }
    }

    private int clickType = 0;

    @Override
    public void onBackPressed() {
//        EventBus.getDefault().post(new MainEvent(0).setLoginType(MainEvent.BACK));
        super.onBackPressed();
    }

//    public void onEventMainThread(final GestureEvent event) {
//        if (event.index ==  -2) {
//            mLockPatternView.clearPattern();
//            mLockPatternView.setEnabled(false);
//        } else if(event.index <=30 && event.index >=0){
//            if(mLockPatternView.isEnabled()){
//                mLockPatternView.clearPattern();
//                mLockPatternView.setEnabled(false);
//                mHeadTextView.setTextColor(Color.parseColor("#fffbb02b"));
//            }
//            mHeadTextView.setText(event.index + " 秒后重试");
//        }else if(event.index == -1){
//            mHeadTextView.setText("请绘制手势密码");
//            mHeadTextView.setTextColor(Color.WHITE);
//
//            mLockPatternView.setEnabled(true);
//            mFailedPatternAttemptsSinceLastTimeout = 0;
//        }
//    }
}
