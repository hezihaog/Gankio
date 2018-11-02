package me.wally.gankio.mvp.presenter.impl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.enums.GankCategoryType;
import me.wally.gankio.mvp.base.BasePresenter;
import me.wally.gankio.mvp.model.IGankCategoryModel;
import me.wally.gankio.mvp.model.impl.GankCategoryModel;
import me.wally.gankio.mvp.presenter.IGankCategoryPresenter;
import me.wally.gankio.mvp.view.IGankCategoryView;

/**
 * Package: me.wally.gankio.mvp.presenter.impl
 * FileName: GankCategoryPresenterImpl
 * Date: on 2018/10/31  下午5:07
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCategoryPresenter extends BasePresenter implements IGankCategoryPresenter {
    private IGankCategoryModel mModel;
    private IGankCategoryView mView;

    public GankCategoryPresenter(IGankCategoryView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mModel = new GankCategoryModel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void getGankAtCategory(GankCategoryType categoryType, int pageSize, int page, final boolean isRefresh) {
        Disposable disposable = mModel
                .requestGankAtCategory(categoryType, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribe(new Consumer<GankBean>() {
                    @Override
                    public void accept(GankBean bean) throws Exception {
                        if (bean == null || bean.error) {
                            if (mView != null) {
                                mView.onLoadGankAtCategoryFail("请求数据异常，请重试", isRefresh);
                                return;
                            }
                        }
                        if (mView != null) {
                            mView.onLoadGankAtCategory(bean, isRefresh);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView != null) {
                            mView.onLoadGankAtCategoryError(throwable, isRefresh);
                        }
                    }
                });
        addDispose(disposable);
    }
}