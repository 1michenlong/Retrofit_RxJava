package com.example.tecl.rxjavademo;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application{
    public static final String BaseUrl="https://test-api.yimifudao.com/v2.4/";
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    public static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        OkHttpInstance();
        RetrofitInstance();
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //启用Log日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);
    }

    /**
     * 初始化okHttp
     * @return
     */
    public static OkHttpClient OkHttpInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient().newBuilder()
                    //设置连接时间
                    .connectTimeout(500, TimeUnit.MILLISECONDS)
                    //设置读时间
                    .readTimeout(1000, TimeUnit.MILLISECONDS)
                    //设置写时间
                    .writeTimeout(1000, TimeUnit.MILLISECONDS)
                    //设置缓存
                    .cache(new Cache(app.getExternalCacheDir(), 10 * 1024 * 1024))
                    .build();
            return okHttpClient;
        } else {
            return okHttpClient;
        }
    }

    /**
     * 初始化retrofit
     */
    public static Retrofit RetrofitInstance(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    //添加Gson支持
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加Retrofit对Rxjava的支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //配置OkHttp
                    .client(okHttpClient).build();
            return retrofit;
        }else{
            return retrofit;
        }
    }
}
