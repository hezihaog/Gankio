package me.wally.gankio.mvp.model.impl;

import io.reactivex.Observable;
import me.wally.gankio.api.GankService;
import me.wally.gankio.api.RetrofitManager;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.constant.GankConstant;
import me.wally.gankio.mvp.model.IGankWelfareModel;

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