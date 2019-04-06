package com.zh.base.base.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zh.base.base.LayoutCallback;
import com.zh.base.base.mvp.IPresenter;

import java.util.ArrayList;


/**
 * Package: me.wally.gankio.controller
 * FileName: BaseUIViewController
 * Date: on 2018/10/31  下午12:27
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public abstract class BaseUIViewController extends UIViewController implements LayoutCallback {
    private ArrayList<IPresenter> mPresenterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLayoutBefore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenterList != null && mPresenterList.size() > 0) {
            for (IPresenter presenter : mPresenterList) {
                presenter.onDestroy();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(onLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenterList = new ArrayList<>();
        onSetupPresenters(mPresenterList);
        for (IPresenter presenter : mPresenterList) {
            presenter.onStart();
        }
        onLayoutAfter();
    }

    protected void onSetupPresenters(ArrayList<IPresenter> presenterList) {
    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onLayoutAfter() {
    }

    public abstract String getPageTitle();
}