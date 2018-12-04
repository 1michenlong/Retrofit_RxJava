package com.example.tecl.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tecl.rxjavademo.bean.HomePageInfo;
import com.example.tecl.rxjavademo.net.ApiService;
import com.example.tecl.rxjavademo.net.NetUtlis;
import com.example.tecl.rxjavademo.utlis.TestFun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private HomePageInfo homePageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestFun.getInstance().fun1();

        HashMap map = new HashMap<String, String>();
        NetUtlis.getInstance().getUrlAndParams("app/index/getIndexData",map);

//        Retrofit retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                //baseUrl:参数之前的部分
//                .baseUrl(BaseUrl).build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        Observable<HomePageInfo> observable = apiService.getHomePageInfo(map);
//        observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<HomePageInfo>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i("SSSS","onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(HomePageInfo hp) {
//                        Log.i("SSSS","onNext=="+hp);
//                        homePageInfo=hp;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("SSSS","onError=="+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i("SSSS","onComplete");
//
//                    }
//                });

        ApiService apiService = MyApplication.RetrofitInstance().create(ApiService.class);
        apiService.getHomePageInfo(map)
                //设置call()执行的线程为新起一个线程
                .subscribeOn(Schedulers.newThread())
                //设置onNext()执行的线程在主线程中
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomePageInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("SSSS","onSubscribe");
                    }

                    @Override
                    public void onNext(HomePageInfo hp) {
                        homePageInfo=hp;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("SSSS","onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("SSSS","onComplete");
                    }
                });

    }
}
