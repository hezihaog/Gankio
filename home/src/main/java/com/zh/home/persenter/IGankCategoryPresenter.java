package com.zh.home.persenter;

import com.zh.service.common.enums.GankCategoryType;
import com.zh.base.base.mvp.IPresenter;

/**
 * Package: me.wally.gankio.mvp.presenter
 * FileName: IGankCategoryPresenter
 * Date: on 2018/10/31  下午4:19
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankCategoryPresenter extends IPresenter {
    void getGankAtCategory(GankCategoryType categoryType,
                           int pageSize,
                           int page,
                           boolean isRefresh);
}