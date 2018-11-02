package me.wally.gankio.mvp.model.impl;

import io.reactivex.Observable;
import me.wally.gankio.api.GankService;
import me.wally.gankio.api.RetrofitManager;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.constant.GankConstant;
import me.wally.gankio.enums.GankCategoryType;
import me.wally.gankio.mvp.model.IGankCategoryModel;

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