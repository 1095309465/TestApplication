package com.yf.personcheck.network;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author shaun_xu
 * @description 主要应用于网络请求相关的接口，比如同步请求，异步请求，还有设置相关的网络参数 主要功能封装第三方网络请求库的类，这个类对于调用者来说是不可见的，调用者可见的
 * 是HttpManager，HttpManager是处理网络请求UI和POST的请求回调处理的类
 * 设置参数，可以慢慢扩充此类。
 * @email xxcpqzm@126.com
 * @date 2015年12月15日 上午10:52:35
 */

public class HttpClient {

    private static OkHttpClient mOkHttpClient;
    private static int TIMEOUT_READ = 20;
    private static int TIMEOUT_CONNECTION = 10;

    public static OkHttpClient getInstance() {
        if (mOkHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                    .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }



    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        getInstance().newCall(request).enqueue(responseCallback);
    }


}
