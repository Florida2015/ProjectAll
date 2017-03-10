package com.hxxc.user.app.listener.gsonConvert;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by chenqun on 2016/11/18.
 */

public class MyGsonConverterFactory extends Converter.Factory {

    public static MyGsonConverterFactory create() {
        return new MyGsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if(String.class == type){
            return MyConverter.INSTANCE;
        }
        return null;
    }

    static final class MyConverter  implements Converter<ResponseBody, String>{
        static final MyConverter INSTANCE = new MyConverter();
        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.toString();
        }
    }
}
