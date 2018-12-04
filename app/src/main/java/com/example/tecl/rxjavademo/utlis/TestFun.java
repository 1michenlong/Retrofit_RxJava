package com.example.tecl.rxjavademo.utlis;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestFun {
    private static TestFun mTestFun;

    public TestFun () {

    }
    public static TestFun getInstance() {
        if (mTestFun == null) {
            synchronized (TestFun.class) {
                if (mTestFun == null) {
                    mTestFun = new TestFun();
                }
            }
        }
        return mTestFun;
    }

    public void fun1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("SSSS", "subscribe");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i("SSSS", "" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("SSSS", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("SSSS", "onComplete");
                    }
                });


//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();
//            }
//        });
//        //创建一个下游 Observer
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
////                Log.d(TAG, "subscribe");
//            }
//
//            @Override
//            public void onNext(Integer value) {
////                Log.d(TAG, "" + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
////                Log.d(TAG, "error");
//            }
//
//            @Override
//            public void onComplete() {
////                Log.d(TAG, "complete");
//            }
//        };
//        //建立连接
//        observable.subscribe(observer);
//    }

    }
}
