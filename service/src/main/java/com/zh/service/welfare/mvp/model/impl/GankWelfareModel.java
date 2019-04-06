package com.zh.service.welfare.mvp.model.impl;

import com.zh.base.constant.GankConstant;
import com.zh.service.api.GankService;
import com.zh.service.api.RetrofitManager;
import com.zh.service.api.bean.GankBean;
import com.zh.service.welfare.mvp.model.IGankWelfareModel;

import io.reactivex.Observable;

/**
 * Package: me.wally.gankio.mvp.model.impl
 * FileName: GankWelfareModel
 * Date: on 2018/10/31  下午7:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankWelfareModel implements IGankWelfareModel {
    @Override
    public Observable<GankBean> requestGankWelfare(int count) {
        return RetrofitManager
                .getInstace()
                .obtainService(GankConstant.Api.DOMAIN, GankService.class)
                .getRandomGirl(count);
    }
}