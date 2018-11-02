package me.wally.gankio.mvp.presenter;

import me.wally.gankio.mvp.base.IPresenter;

/**
 * Package: me.wally.gankio.mvp.presenter
 * FileName: IGankWelfarePresenter
 * Date: on 2018/10/31  下午7:20
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankWelfarePresenter extends IPresenter {
    void getGankWelfare(int count, boolean isRefresh);
}