package com.hxxc.huaxing.app.retrofit;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.HttpConfig;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.NetWorkUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * retrofit2 okhttp3 请求
 * 调用方法：RestClient.getClient().{方法}
 *
 *
 *
 */
public class Api {

    public static String token="";
    public static String uid="";

    public static ApiService getClient() {
//        if (TextUtils.isEmpty(token))
            token= SharedPreUtils.getInstanse().getToken();
        LogUtil.d("token="+token);
        uid=SharedPreUtils.getInstanse().getUid();
        LogUtil.d("uid="+uid);

        //log 设置请求log拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//NONE,BASIC ,HEADERS, BODY
        File cacheFile = new File(HXXCApplication.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //添加证书
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpOkFromBodyHeader())
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache).build();
//https 秘钥
//.newBuilder().sslSocketFactory(SSLContextUtil.getSslSocketFactory(DemoApp.getInstance().getAssets().open("server.crt"))).build()

        Retrofit client = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okClient)
                .build();

        return client.create(ApiService.class);
    }
    public static ApiService getClientNo() {
//        if (TextUtils.isEmpty(token))
        token= SharedPreUtils.getInstanse().getToken();
        LogUtil.d("token="+token);
        uid=SharedPreUtils.getInstanse().getUid();
        LogUtil.d("uid="+uid);

        //log 设置请求log拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//NONE,BASIC ,HEADERS, BODY
        File cacheFile = new File(HXXCApplication.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //添加证书
        OkHttpClient okClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache).build();
//https 秘钥
//.newBuilder().sslSocketFactory(SSLContextUtil.getSslSocketFactory(DemoApp.getInstance().getAssets().open("server.crt"))).build()

        Retrofit client = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okClient)
                .build();

        return client.create(ApiService.class);
    }
    /**
     * 拦截器
     * 添加公共参数
     */
    static class HttpOkFromBodyHeader implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            //Body
            FormBody responseBody =  new FormBody.Builder()
                    .add("version", "1")
                    .add("transTime", System.currentTimeMillis()+"")
                    .add("instId","HX0000001")
                    .add("token",TextUtils.isEmpty(token)?"":token)
                    .add("channel","2")
                    .build();//【0:wap、1:ios、2:android、3:pc】
            //header添加公共参数
            Request request = chain.request().newBuilder()
                    .addHeader("token", "add token here")
                    .addHeader("Cookie", "add cookies here")
                    .addHeader("version", "1")
                    .addHeader("from", "android")
                    .addHeader("header1", "headerValue1")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .post(responseBody)
                    .build();
            return chain.proceed(request);//chain.proceed(chain.request());
        }
    }

    //网络拦截器
    static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(HXXCApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
                Toast.makeText(HXXCApplication.getInstance(),"网络异常",Toast.LENGTH_SHORT).show();
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(HXXCApplication.getInstance())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
