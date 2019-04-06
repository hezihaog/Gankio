package com.zh.service.welfare.mvp.view;

import com.zh.service.api.bean.GankBean;
import com.zh.base.base.mvp.IView;

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