package me.wally.gankio.mvp.model;

import io.reactivex.Observable;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.enums.GankCategoryType;
import me.wally.gankio.mvp.base.IModel;

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