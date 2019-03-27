package com.zh.base.util.rx;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * <b>Package:</b> com.linghit.lingjidashi.base.lib.utils <br>
 * <b>Create Date:</b> 2019/1/25  4:00 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
public class RxUtil {
    private RxUtil() {
    }

    /**
     * Rx倒计时
     *
     * @param startDelayTime 开始前的延时时间，例如开始前有1秒缓冲
     * @param cycle          周期，每隔多久重复执行，例如1秒执行一次的倒计时功能
     * @param time           执行多久，例如倒计时3秒，则为3
     * @param unit           时间单位，倒计时3秒，单位为秒
     */
    public static Observable<Integer> countdown(long startDelayTime, long cycle, int time, TimeUnit unit) {
        final int countTime = time < 0 ? 0 : time;
        return Observable.interval(startDelayTime, cycle, unit)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long increaseTime) throws Exception {
                        return countTime - increaseTime.intValue();
                    }
                })
                //take指定到多少次就停止，这里指定到时间后就结束
                .take(countTime + 1);
    }

    /**
     * 延时操作
     */
    public static Observable<Long> delayed(int time, TimeUnit unit) {
        return Observable.timer(time, unit);
    }

    /**
     * 点击防抖
     */
    public static Observable<Object> click(View view) {
        return RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS);
    }

    public static Observable<Activity> click(View view, Activity activity) {
        return click(view).filter(new Predicate<Object>() {
            @Override
            public boolean test(Object o) throws Exception {
                return activity != null && !activity.isFinishing();
            }
        }).map(new Function<Object, Activity>() {
            @Override
            public Activity apply(Object o) throws Exception {
                return activity;
            }
        });
    }

    /**
     * 文字改变监听
     */
    public static Observable<CharSequence> textChanges(TextView textView) {
        return RxTextView.textChanges(textView);
    }

    /**
     * 文字改变监听，去掉第一次发送的事件，一般第一次发送的是空的字符串，可以选择过滤掉
     */
    public static Observable<CharSequence> textChangesSkipFirst(TextView textView) {
        return textChanges(textView).skip(1);
    }

    /**
     * 文字改变监听，只保留指定时间内的最后一次，一般用于自动搜索关键字
     */
    public static Observable<CharSequence> testChangesWithThrottle(TextView textView) {
        return textChanges(textView)
                //500毫秒内，发送多次，只保留最后一次
                .throttleWithTimeout(500, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取一个什么都不做的Consumer
     */
    public static Consumer<Throwable> nothingErrorConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        };
    }

    public static <T> Observer<T> nothingObserver() {
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Object o) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }
}