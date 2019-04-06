package com.zh.service.db.biz.impl;

import java.util.List;

import com.zh.service.db.biz.IGankCollectionBiz;
import com.zh.service.db.dao.IGankCollectionDao;
import com.zh.service.db.dao.impl.GankCollectionDao;
import com.zh.service.db.model.dto.GankCollectionDTO;
import com.zh.service.db.model.vo.GankCollectionVO;
import com.zh.service.common.enums.GankCollectionType;

/**
 * Package: me.wally.gankio.db.biz.impl
 * FileName: GankCollectionBiz
 * Date: on 2018/11/1  下午12:01
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCollectionBiz implements IGankCollectionBiz {
    private final IGankCollectionDao mDao;

    public GankCollectionBiz() {
        mDao = new GankCollectionDao();
    }

    @Override
    public boolean addCollection(GankCollectionDTO dto) {
        //业务层，增加前先查一次，后台的Id是唯一的，则用后台的Id进行查询
        boolean isExist = mDao.checkIsCollectionExist(dto.getRemoteId());
        //收藏过了，直接返回收藏成功
        if (isExist) {
            return true;
        } else {
            return mDao.addCollection(dto);
        }
    }

    @Override
    public boolean removeCollection(String remoteCollectionId) {
        //移除前，查一次是否存在，不存在，则删除失败
        GankCollectionVO vo = mDao.getCollectionById(remoteCollectionId);
        if (vo != null) {
            return mDao.removeCollection(remoteCollectionId);
        } else {
            //不存在，取消收藏失败
            throw new IllegalStateException("未收藏过，取消收藏失败");
        }
    }

    @Override
    public List<GankCollectionVO> getCollectionList(GankCollectionType collectionType) {
        return mDao.getCollectionList(collectionType);
    }

    @Override
    public Boolean checkIsCollection(String remoteCollectionId) {
        return mDao.checkIsCollectionExist(remoteCollectionId);
    }
}