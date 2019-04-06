package com.zh.home.model;

import com.zh.base.base.mvp.IModel;
import com.zh.service.api.bean.GankBean;
import com.zh.service.common.enums.GankCategoryType;

import io.reactivex.Observable;

/**
 * Package: me.wally.gankio.mvp.view
 * FileName: IGankCategoryModel
 * Date: on 2018/10/31  下午4:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankCategoryModel extends IModel {
    Observable<GankBean> requestGankAtCategory(GankCategoryType categoryType,
                                               int pageSize,
                                               int page);
}