package me.wally.gankio.mvp.model;

import io.reactivex.Observable;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.mvp.base.IModel;

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