package com.example.tecl.rxjavademo.net;

import com.example.tecl.rxjavademo.bean.HomePageInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {

    @POST("app/index/getIndexData")
//    @POST
    Observable<HomePageInfo> getHomePageInfo(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/index/getIndexData")
    Observable<String> getHomePageInfo2(@FieldMap Map<String, String> map);
}
