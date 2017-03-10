package com.hxxc.user.app.rest.rx;

import android.text.TextUtils;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.KICKOUTEvent;
import com.hxxc.user.app.utils.EventBusUtils;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by chenqun on 2016/7/7.
 */
public class BaseResponseFunc<T>  implements Func1<BaseResult<T>, Observable<T>> {

    @Override
    public Observable<T> call(BaseResult<T> tBaseResult) {
        //遇到非-2错误统一处理,将BaseResponse转换成您想要的对象
        if (!tBaseResult.getSuccess()) {
            if(!TextUtils.isEmpty(tBaseResult.getErrorCode()) && tBaseResult.getErrorCode().contains(Constants.ErrorCode_login_out)){
                EventBusUtils.getInstance().kickOff(KICKOUTEvent.From_BACK);
                return Observable.error(new Throwable("账号已在其它设备上登录"));
            }
            return Observable.error(new Throwable(tBaseResult.getErrorMsg()));
        }else{
            return Observable.just(tBaseResult.getData());
        }
    }
}
