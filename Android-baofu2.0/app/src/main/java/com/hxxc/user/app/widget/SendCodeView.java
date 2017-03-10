package com.hxxc.user.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.RestUtils;
import com.hxxc.user.app.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

public class SendCodeView extends FrameLayout {
    public static final int TYPE_REGIST = 10;
    public static final int TYPE_FIND_PASSWORD = 11;
    public static final int TYPE_UPDATE_EMAIL = 13;

    private TextView tv_getcode;
    private ProgressBar progressbar;

    private String mMobile;
    private Context mContext;
    private MyOnClickListener mListener;
    public Map<String, String> mParams = new HashMap<>();

    private int type = 0;
    private ApiManager mApiManager;
    private MyThread myThread;
    public boolean mOnDestory = false;
    private int i;

    public SendCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public SendCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SendCodeView(Context context) {
        super(context);
    }

    private ICode mActivity;

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        mApiManager = ApiManager.getInstance();
        View.inflate(context, R.layout.view_code, this);

        tv_getcode = (TextView) findViewById(R.id.tv_getcode);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        tv_getcode.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    boolean b = mListener.onPre();
                    if (b) {
                        return;
                    }
                }
                if (null == mParams) {
                    return;
                }
                if (netType != PAY) {
                    mMobile = mActivity.getMobile();

                    if (TextUtils.isEmpty(mMobile)) {
                        if (type == 0) {
                            ToastUtil.ToastShort(mContext, "请输入手机号码");
                        } else if (type == 1) {
                            ToastUtil.ToastShort(mContext, "请输入邮箱地址");
                        }
                        return;
                    }

                    if (type == 0) {
                        if (!CommonUtil.isMobileNo(mMobile)) {
                            ToastUtil.ToastShort(mContext, "请输入正确的手机号码");
                            return;
                        }
                    } else if (type == 1) {
                        if (!CommonUtil.isEmailNo(mMobile)) {
                            ToastUtil.ToastShort(mContext, "请输入正确的邮箱地址");
                            return;
                        }
                    }
                }
                if (type == 0) {
                    if (netType != PAY) {
                        mParams.put("mobile", mMobile);
                    }
                } else if (type == 1) {
                    mParams.put("email", mMobile);
                }

                if (null == mListener) {
                    return;
                }
                //TODO
                tv_getcode.setEnabled(false);
                progressbar.setVisibility(View.VISIBLE);
                tv_getcode.setVisibility(View.INVISIBLE);
                doNet();
            }
        });
    }

    public static int NOMAL = 0;
    public static int PAY = 1;
    public static int BINDCARD = 2;
    public static int EMAIL = 3;
    public static int BALANCE_PAY = 4;
    private int netType = NOMAL;

    public void setNetType(int b) {
        netType = b;
    }

    public int getNetType() {
        return netType;
    }

    protected void doNet() {
        mParams = RestUtils.setParamss(mParams);
        if (netType == PAY) {
            sendCodeForPay(mParams);
        } else if (netType == BINDCARD) {
            sendCodeForBindCard(mParams);
        } else if (netType == EMAIL) {
            sendCodeForEmail(mParams);
        }
//        else if (netType == BALANCE_PAY) {
//            sendCodeForBalancePay(mParams);
//        }
        else {
            sendCode(mParams);
        }

    }

//    private void sendCodeForBalancePay(Map<String, String> mParams) {
//        mApiManager.sendCodeForBalancePay(mParams, new SimpleCallback<String>() {
//            @Override
//            public void onNext(String s) {
//                onHttpNext(s);
//            }
//
//            @Override
//            public void onError() {
//                onHttpError();
//            }
//        });
//    }

    private void onHttpNext(String s) {
        mListener.onSuccess(s);
        progressbar.setVisibility(View.INVISIBLE);
        tv_getcode.setVisibility(View.VISIBLE);
        startThread();
    }

    private void onHttpError() {
        mListener.onFailure("");
        progressbar.setVisibility(View.INVISIBLE);
        tv_getcode.setVisibility(View.VISIBLE);
        tv_getcode.setEnabled(true);
    }

    private void sendCodeForEmail(Map<String, String> mParams) {
        mApiManager.sendCodeForEmail(mParams, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                onHttpNext(s);
            }

            @Override
            public void onError() {
                onHttpError();
            }
        });
    }

    private void sendCodeForPay(Map<String, String> mParams) {
        mApiManager.sendCodeForPay(mParams, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                onHttpNext(s);
            }

            @Override
            public void onError() {
                onHttpError();
            }
        });
    }

    private void sendCodeForBindCard(Map<String, String> mParams) {
        mApiManager.sendCodeForBindCard(mParams, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                onHttpNext(s);
            }

            @Override
            public void onError() {
                onHttpError();
            }
        });
    }

    private void sendCode(Map<String, String> mParams) {
        mApiManager.sendCode(mParams, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                onHttpNext(s);
            }

            @Override
            public void onError() {
                onHttpError();
            }
        });
    }


    public void initDatas(ICode a, int type, Map<String, String> params, MyOnClickListener listener) {
        this.type = type;
        this.mActivity = a;
        mParams.clear();
        if (null != params) {
            mParams.putAll(params);
        }
        this.mListener = listener;
        if (type == 1) {
            tv_getcode.setText("获取验证码");
        }
    }

    public void setParams(Map<String, String> params) {
        mParams.clear();
        mParams.putAll(params);
    }

    public Map<String, String> getParams() {
        return mParams;
    }


    private void startThread() {
        mOnDestory = false;
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
//        myThread = new MyThread();
//        myThread.start();

        i = 60;
        doStart();
    }

    private void doStart() {
        if (mOnDestory || i <= 0) {
            Message msg = new Message();
            msg.what = 0;
            if (i <= 0)
                handler.sendMessageDelayed(msg, 1000);
            else
                handler.sendMessage(msg);
            return;
        }
        i--;
        Message msg = new Message();
        msg.what = 2;
        msg.obj = "剩余" + i + "秒";
        handler.sendMessageDelayed(msg, 1000);
    }

    public void onDestory() {
        mActivity = null;
        mOnDestory = true;
        try {
            if (null != myThread) {
                myThread.stop();
                myThread.interrupt();
                myThread = null;
            }
        } catch (Exception e) {
            LogUtils.e("Thread.interrupt()--" + e.getMessage());
        }
    }

    public interface MyOnClickListener {
        boolean onPre();

        void onSuccess(String t);

        void onFailure(String t);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    tv_getcode.setEnabled(true);
                    tv_getcode.setText("获取验证码");
                    break;
                case 1:
                    tv_getcode.setEnabled(false);
                    tv_getcode.setText("剩余60秒");
                    break;
                case 2:
                    tv_getcode.setText(msg.obj.toString());
                    doStart();
                    break;
            }
        }
    };

    public interface ICode {

        String getMobile();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            int i = 60;
            while (i > 0) {
                if (mOnDestory) {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                }
                i--;
                Message msg = new Message();
                msg.what = 2;
                msg.obj = "剩余" + i + "秒";
                handler.sendMessage(msg);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
