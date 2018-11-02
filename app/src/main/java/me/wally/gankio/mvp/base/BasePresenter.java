package me.wally.gankio.mvp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Package: me.wally.gankio.mvp
 * FileName: BasePresenter
 * Date: on 2018/10/31  下午4:16
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class BasePresenter implements IPresenter {
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
