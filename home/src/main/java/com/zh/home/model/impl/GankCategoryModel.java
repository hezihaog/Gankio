package com.zh.home.model.impl;

import com.zh.base.constant.GankConstant;
import com.zh.home.model.IGankCategoryModel;
import com.zh.service.api.GankService;
import com.zh.service.api.RetrofitManager;
import com.zh.service.api.bean.GankBean;
import com.zh.service.common.enums.GankCategoryType;

import io.reactivex.Observable;

/**
 * Package: me.wally.gankio.mvp.model.impl
 * FileName: GankCategoryModel
 * Date: on 2018/10/31  下午5:13
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCategoryModel implements IGankCategoryModel {

    @Override
    public Observable<GankBean> requestGankAtCategory(GankCategoryType categoryType, int pageSize, int page) {
        return RetrofitManager
                .getInstace()
                .obtainService(GankConstant.Api.DOMAIN, GankService.class)
                .getGankAtCategory(categoryType.getType(), pageSize, page);
    }
}