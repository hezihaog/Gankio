package me.wally.gankio.mvp.model;

import java.util.List;

import io.reactivex.Observable;
import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.enums.GankCollectionType;
import me.wally.gankio.mvp.base.IModel;

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