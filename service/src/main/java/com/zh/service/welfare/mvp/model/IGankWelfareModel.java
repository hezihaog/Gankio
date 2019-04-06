package com.zh.service.welfare.mvp.model;

import com.zh.base.base.mvp.IModel;
import com.zh.service.api.bean.GankBean;

import io.reactivex.Observable;

/**
 * Package: me.wally.gankio.mvp.model
 * FileName: IGankWelfareModel
 * Date: on 2018/10/31  下午7:21
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankWelfareModel extends IModel {
    Observable<GankBean> requestGankWelfare(int count);
}