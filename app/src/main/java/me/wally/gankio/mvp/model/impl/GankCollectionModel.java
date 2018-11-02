package me.wally.gankio.mvp.model.impl;

import java.util.List;

import io.reactivex.Observable;
import me.wally.gankio.db.biz.IGankCollectionBiz;
import me.wally.gankio.db.biz.impl.GankCollectionBiz;
import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.enums.GankCollectionType;
import me.wally.gankio.mvp.model.IGankCollectionModel;

/**
 * Package: me.wally.gankio.mvp.model.impl
 * FileName: GankCollectionModel
 * Date: on 2018/11/1  下午12:06
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCollectionModel implements IGankCollectionModel {
    private final IGankCollectionBiz mBiz;

    public GankCollectionModel() {
        mBiz = new GankCollectionBiz();
    }

    @Override
    public Observable<Boolean> addCollection(GankCollectionDTO dto) {
        return Observable.just(mBiz.addCollection(dto));
    }

    @Override
    public Observable<Boolean> removeCollection(String remoteCollectionId) {
        return Observable.just(mBiz.removeCollection(remoteCollectionId));
    }

    @Override
    public Observable<List<GankCollectionVO>> getCollectionList(GankCollectionType collectionType) {
        return Observable.just(mBiz.getCollectionList(collectionType));
    }

    @Override
    public Observable<Boolean> checkIsCollection(String remoteCollectionId) {
        return Observable.just(mBiz.checkIsCollection(remoteCollectionId));
    }
}