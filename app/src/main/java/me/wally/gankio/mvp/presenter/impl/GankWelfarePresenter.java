package me.wally.gankio.mvp.presenter.impl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.mvp.base.BasePresenter;
import me.wally.gankio.mvp.model.IGankWelfareModel;
import me.wally.gankio.mvp.model.impl.GankWelfareModel;
import me.wally.gankio.mvp.presenter.IGankWelfarePresenter;
import me.wally.gankio.mvp.view.IGankWelfareView;

/**
 * Package: me.wally.gankio.mvp.presenter.impl
 * FileName: GankWelfarePresenter
 * Date: on 2018/10/31  下午7:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankWelfarePresenter extends BasePresenter implements IGankWelfarePresenter {
    private IGankWelfareModel mModel;
    private IGankWelfareView mView;

    public GankWelfarePresenter(IGankWelfareView view) {
        this.mView = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mModel = new GankWelfareModel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void getGankWelfare(final int count, final boolean isRefresh) {
        Disposable disposable = mModel.requestGankWelfare(count)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mView != null) {
                            mView.showLoading();
                        }
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mView != null) {
                            mView.hideLoading();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankBean>() {
                    @Override
                    public void accept(GankBean bean) throws Exception {
                        if (bean == null || bean.results == null || bean.error) {
                            if (mView != null) {
                                mView.onLoadGankWelfareFail("加载数据异常，请重试", isRefresh);
                                return;
                            }
                        }
                        if (mView != null) {
                            mView.onLoadGankWelfare(bean, isRefresh);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView != null) {
                            mView.onLoadGankWelfareError(throwable, isRefresh);
                        }
                    }
                });
        addDispose(disposable);
    }
}
