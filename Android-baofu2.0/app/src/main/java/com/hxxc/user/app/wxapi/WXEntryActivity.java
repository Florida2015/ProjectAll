package com.hxxc.user.app.wxapi;

//import com.hxxc.user.app.UserInfoConfig;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.widget.Toast;

import com.umeng.socialize.weixin.view.WXCallbackActivity;


/**
 * Created by  on 15/9/4.
 * 微信分享
 */
public class WXEntryActivity extends WXCallbackActivity {
//    public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
//
//    // IWXAPI  app openapi
//    private IWXAPI api;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(new LinearLayout(this));
//
//        // WXAPIFactory  ȡIWXAPI
//        api = WXAPIFactory.createWXAPI(this, UserInfoConfig.APP_ID_weixin, false);
//
//        // app
//        if (api!=null&&api.isWXAppInstalled())
//            api.registerApp(UserInfoConfig.APP_ID_weixin);
//
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//
//    // 微信发送请求到第三方应用时，会回调到该方法
////    @Override
////    public void onReq(BaseReq baseReq) {
////
////    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        int result = 0;
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//                result = R.string.errcode_success;
//                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
////                result = R.string.errcode_cancel;
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
////                result = R.string.errcode_deny;
//                break;
//            default:
////                result = R.string.errcode_unknown;
//                break;
//        }
//
////        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
////    @Override
////    public void onResp(BaseResp resp) {
////        int result = 0;
////        switch (resp.errCode) {
////            case BaseResp.ErrCode.ERR_OK:
////                result = R.string.errcode_success;
////                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
////                break;
////            case BaseResp.ErrCode.ERR_USER_CANCEL:
//////                result = R.string.errcode_cancel;
////                break;
////            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//////                result = R.string.errcode_deny;
////                break;
////            default:
//////                result = R.string.errcode_unknown;
////                break;
////        }
////
//////        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
////        finish();
////    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//@Override
//public void onResp(BaseResp resp) {
//    if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//        switch (resp.errCode) {
//            case 0:
//                Toast.makeText(this, "支付成功！", Toast.LENGTH_LONG).show();
//                break;
//            case -2:
//                Toast.makeText(this,"支付取消！",Toast.LENGTH_LONG).show();
//                break;
//            case -1:
//                Toast.makeText(this,"支付失败！",Toast.LENGTH_LONG).show();
//                break;
//            default:
//                Toast.makeText(this,"支付出错！",Toast.LENGTH_LONG).show();
//                break;
//        }
//    } else {
//        super.onResp(resp);//一定要加super，实现我们的方法，否则不能回调
//    }
//}
}
