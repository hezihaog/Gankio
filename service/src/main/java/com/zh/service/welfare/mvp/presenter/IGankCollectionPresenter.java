package com.zh.service.welfare.mvp.presenter;

import com.zh.service.db.model.dto.GankCollectionDTO;
import com.zh.service.common.enums.GankCollectionType;
import com.zh.base.base.mvp.IPresenter;

/**
 * Package: me.wally.gankio.mvp.presenter
 * FileName: IGankCollectionPresenter
 * Date: on 2018/11/1  上午11:47
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankCollectionPresenter extends IPresenter {
    /**
     * 增加收藏
     *
     * @param dto 添加需要的参数字段包裹对象
     */
    void addCollection(GankCollectionDTO dto);

    /**
     * 移除一个收藏
     */
    void removeCollection(String remoteCollectionId);

    /**
     * 获取所有的收藏列表
     *
     * @param collectionType 收藏类型
     */
    void getCollectionList(GankCollectionType collectionType);

    /**
     * 检查是否已经收藏了
     *
     * @param remoteCollectionId 后台返回的收藏Id
     */
    void checkIsCollection(String remoteCollectionId);
}