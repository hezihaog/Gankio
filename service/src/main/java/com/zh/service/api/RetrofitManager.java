package com.zh.service.api;

import com.zh.base.util.GsonUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Package: me.wally.gankio.api
 * FileName: RetrofitManager
 * Date: on 2018/10/31  下午5:16
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class RetrofitManager {
    private static final long CONNECT_TIMEOUT = 30L;
    private static final long READ_TIMEOUT = 10L;
    private HashMap<String, Object> mServiceList = new HashMap<>();

    private static final class SingleHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstace() {
        return SingleHolder.INSTANCE;
    }

    public <T> T obtainService(String baseUrl, Class<T> serviceClazz) {
        Object service = mServiceList.get(serviceClazz.getName());
        if (service == null) {
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            service = retrofit.create(serviceClazz);
            mServiceList.put(serviceClazz.getName(), service);
        }
        return (T) service;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }
}