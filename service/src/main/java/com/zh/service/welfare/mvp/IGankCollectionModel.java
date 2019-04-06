package com.zh.service.welfare.mvp;

import com.zh.base.base.mvp.IModel;
import com.zh.service.common.enums.GankCollectionType;
import com.zh.service.db.model.dto.GankCollectionDTO;
import com.zh.service.db.model.vo.GankCollectionVO;

import java.util.List;

import io.reactivex.Observable;

/**
 * Package: me.wally.gankio.mvp.model
 * FileName: IGankCollectionModel
 * Date: on 2018/11/1  上午11:54
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankCollectionModel extends IModel {
    Observable<Boolean> addCollection(GankCollectionDTO dto);

    Observable<Boolean> removeCollection(String remoteCollectionId);

    Observable<List<GankCollectionVO>> getCollectionList(GankCollectionType collectionType);

    Observable<Boolean> checkIsCollection(String remoteCollectionId);
}