package me.wally.gankio.mvp.view;

import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.mvp.base.IView;

/**
 * Package: me.wally.gankio.mvp.view
 * FileName: IGankWelfareView
 * Date: on 2018/10/31  下午7:25
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankWelfareView extends IView {
    void onLoadGankWelfare(GankBean bean, boolean isRefresh);

    void onLoadGankWelfareFail(String message, boolean isRefresh);

    void onLoadGankWelfareError(Throwable error, boolean isRefresh);
}