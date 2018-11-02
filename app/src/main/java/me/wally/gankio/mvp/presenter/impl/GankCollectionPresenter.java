package me.wally.gankio.mvp.presenter.impl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.enums.GankCollectionType;
import me.wally.gankio.mvp.base.BasePresenter;
import me.wally.gankio.mvp.model.IGankCollectionModel;
import me.wally.gankio.mvp.model.impl.GankCollectionModel;
import me.wally.gankio.mvp.presenter.IGankCollectionPresenter;
import me.wally.gankio.mvp.view.IGankCollectionView;

/**
 * Package: me.wally.gankio.mvp.presenter.impl
 * FileName: GankCollectionPresenter
 * Date: on 2018/11/1  下午12:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCollectionPresenter extends BasePresenter implements IGankCollectionPresenter {
    private IGankCollectionModel mModel;
    private IGankCollectionView.IAddCollectionView mAddCollectionView;
    private IGankCollectionView.IRemoveCollectionView mRemoveCollectionView;
    private IGankCollectionView.IObtainCollectionView mObtainCollectionView;
    private IGankCollectionView.ICheckCollectionView mCheckCollectionView;

    public GankCollectionPresenter(
            IGankCollectionView.IAddCollectionView addCollectionView,
            IGankCollectionView.IRemoveCollectionView removeCollectionView,
            IGankCollectionView.IObtainCollectionView obtainCollectionView,
            IGankCollectionView.ICheckCollectionView checkCollectionView) {
        this.mAddCollectionView = addCollectionView;
        this.mRemoveCollectionView = removeCollectionView;
        this.mObtainCollectionView = obtainCollectionView;
        this.mCheckCollectionView = checkCollectionView;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mModel = new GankCollectionModel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAddCollectionView = null;
        this.mRemoveCollectionView = null;
        this.mObtainCollectionView = null;
        this.mCheckCollectionView = null;
        this.mModel = null;
    }

    @Override
    public void addCollection(GankCollectionDTO dto) {
        Disposable disposable = mModel.addCollection(dto)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mAddCollectionView != null) {
                            mAddCollectionView.showLoading();
                        }
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mAddCollectionView != null) {
                            mAddCollectionView.hideLoading();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        if (mAddCollectionView != null) {
                            if (result) {
                                mAddCollectionView.onAddCollectionSuccess();
                            } else {
                                mAddCollectionView.onAddCollectionFail("添加失败，请重试");
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mAddCollectionView != null) {
                            mAddCollectionView.onAddCollectionError(throwable);
                        }
                    }
                });
        addDispose(disposable);
    }

    @Override
    public void removeCollection(String remoteCollectionId) {
        Disposable disposable = mModel.removeCollection(remoteCollectionId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mRemoveCollectionView != null) {
                            mRemoveCollectionView.showLoading();
                        }
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRemoveCollectionView != null) {
                            mRemoveCollectionView.hideLoading();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        if (mRemoveCollectionView != null) {
                            if (result) {
                                mRemoveCollectionView.onRemoveCollectionSuccess();
                            } else {
                                mRemoveCollectionView.onRemoveCollectionFail("收藏失败，请重试");
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mRemoveCollectionView != null) {
                            mRemoveCollectionView.onRemoveCollectionError(throwable);
                        }
                    }
                });
        addDispose(disposable);
    }

    @Override
    public void getCollectionList(GankCollectionType collectionType) {
        Disposable disposable = mModel
                .getCollectionList(collectionType)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mObtainCollectionView != null) {
                            mObtainCollectionView.showLoading();
                        }
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mObtainCollectionView != null) {
                            mObtainCollectionView.hideLoading();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GankCollectionVO>>() {
                    @Override
                    public void accept(List<GankCollectionVO> gankCollectionVOS) throws Exception {
                        if (mObtainCollectionView != null) {
                            if (gankCollectionVOS != null) {
                                mObtainCollectionView.onGetCollectionListSuccess(gankCollectionVOS);
                            } else {
                                mObtainCollectionView.onGetCollectionListFail("获取收藏列表失败，请重试");
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mObtainCollectionView != null) {
                            mObtainCollectionView.onGetCollectionListError(throwable);
                        }
                    }
                });
        addDispose(disposable);
    }

    @Override
    public void checkIsCollection(String remoteCollectionId) {
        Disposable disposable = mModel
                .checkIsCollection(remoteCollectionId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mCheckCollectionView != null) {
                            mCheckCollectionView.showLoading();
                        }
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mCheckCollectionView != null) {
                            mCheckCollectionView.hideLoading();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isCollection) throws Exception {
                        if (mCheckCollectionView != null) {
                            mCheckCollectionView.onCheckIsCollectionCallback(isCollection);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mCheckCollectionView != null) {
                            mCheckCollectionView.onCheckIsCollectionError(throwable);
                        }
                    }
                });
        addDispose(disposable);
    }
}