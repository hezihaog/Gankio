package me.wally.gankio.mvp.view;

import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.mvp.base.IView;

/**
 * Package: me.wally.gankio.mvp.view
 * FileName: IGankCategoryView
 * Date: on 2018/10/31  下午5:03
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankCategoryView extends IView {
    void onLoadGankAtCategory(GankBean bean, boolean isRefresh);

    void onLoadGankAtCategoryFail(String msg, boolean isRefresh);

    void onLoadGankAtCategoryError(Throwable error, boolean isRefresh);
}