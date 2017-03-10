package com.hxxc.user.app.share;

//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.bean.SocializeEntity;
//import com.umeng.socialize.bean.StatusCode;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;

//public class CustomShareBoardDialog extends Dialog implements View.OnClickListener {

//    private static UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
//
//    private Context mContext;
//
//    public CustomShareBoardDialog(Context context, boolean cancelable,
//                                  OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }
//
//    public CustomShareBoardDialog(Context context, int theme) {
//        super(context, theme);
//    }
//
//    public CustomShareBoardDialog(Context context) {
//        super(context, R.style.AddressDialog);
//        this.mContext = context;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_share_layout);
//        findViewById(R.id.sns_sian_layout).setOnClickListener(this);
//        findViewById(R.id.sns_qq_layout).setOnClickListener(this);
//        findViewById(R.id.sns_wx_fs_layout).setOnClickListener(this);
//        findViewById(R.id.sns_wx_layout).setOnClickListener(this);
//        findViewById(R.id.sns_qq_zone_layout).setOnClickListener(this);
//        findViewById(R.id.close_btn).setOnClickListener(this);
//
//        initPosition();
//    }
//
//    private void initPosition() {
//        // 获取到窗体
//        Window window = getWindow();
//        // 获取到窗体的属性
//        LayoutParams params = window.getAttributes();
//
//        params.height = LayoutParams.WRAP_CONTENT;
//        params.width = LayoutParams.MATCH_PARENT;
//        // 让对话框展示到屏幕的下边
//        params.gravity = Gravity.BOTTOM;
//
//        window.setAttributes(params);
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.sns_sian_layout:
//                performShare(SHARE_MEDIA.SINA);
//                break;
//            case R.id.sns_qq_layout:
//                performShare(SHARE_MEDIA.QQ);
//                break;
//            case R.id.sns_wx_fs_layout:
//                performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
//                break;
//            case R.id.sns_wx_layout:
//                performShare(SHARE_MEDIA.WEIXIN);
//                break;
//            case R.id.sns_qq_zone_layout:
//                performShare(SHARE_MEDIA.QZONE);
//                break;
//            case R.id.close_btn:
//                dismiss();
//                break;
//            default:
//                break;
//        }
//    }
//
//    public SnsPostListener snsListener = new SnsPostListener() {
//        @Override
//        public void onStart() {
//            //开始分享
////            ToastUtil.ToastShort(mContext, "分享中...");
//        }
//
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//            CommonUtil.hideKeyBoard((Activity) mContext);
//            if (eCode == StatusCode.ST_CODE_SUCCESSED && platform == lastestPlatform) {
//                CustomShareBoardDialog.this.dismiss();
//                ToastUtil.ToastShort(mContext, "分享成功");
//            }
//        }
//    };
//
//    public SHARE_MEDIA lastestPlatform;
//
//    private long time = 0;
//    private final long intervalTime = 1000 * 1;//1秒的周期
//
//    private void performShare(SHARE_MEDIA platform) {
//        long newTime = new Date().getTime();
//        if (0 == time) {
//            time = newTime;
//            doShare(platform);
//        } else {
//            long l = newTime - time;
//            if (l >= intervalTime) {
//                doShare(platform);
//                time = newTime;
//            }
//        }
//    }
//
//    private void doShare(final SHARE_MEDIA platform) {
//        lastestPlatform = platform;
//        mController.postShare(mContext, platform, snsListener);
//    }
//}
