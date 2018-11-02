package me.wally.gankio.db.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import me.wally.gankio.db.DatabaseManager;
import me.wally.gankio.db.dao.IGankCollectionDao;
import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.db.model.entity.GankCollectionEntity;
import me.wally.gankio.db.model.entity.GankCollectionEntity_;
import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.enums.DeleteFlag;
import me.wally.gankio.enums.GankCollectionType;

/**
 * Package: me.wally.gankio.db.dao.impl
 * FileName: GankCollectionDao
 * Date: on 2018/11/1  下午12:00
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCollectionDao implements IGankCollectionDao {
    private final Box<GankCollectionEntity> mEntityBox;

    public GankCollectionDao() {
        mEntityBox = DatabaseManager.shareInstance().getBox(GankCollectionEntity.class);
    }

    @Override
    public boolean addCollection(GankCollectionDTO dto) {
        GankCollectionEntity entity = new GankCollectionEntity();
        entity.remoteId = dto.getRemoteId();
        entity.collectionType = dto.getCollectionType().getType();
        entity.createdAt = dto.getCreatedAt();
        entity.desc = dto.getDesc();
        entity.publishedAt = dto.getPublishedAt();
        entity.source = dto.getSource();
        entity.type = dto.getType();
        entity.url = dto.getUrl();
        entity.used = dto.getUsed();
        entity.who = dto.getWho();
        entity.createTime = new Date();
        entity.updateTime = new Date();
        entity.deleteFlag = DeleteFlag.DELETED.getCode();
        long id = mEntityBox.put(entity);
        return id > 0;
    }

    @Override
    public boolean removeCollection(String remoteCollectionId) {
        try {
            GankCollectionVO collection = getCollectionById(remoteCollectionId);
            mEntityBox.remove(collection.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public GankCollectionVO getCollectionById(String remoteCollectionId) {
        GankCollectionEntity collectionEntity = mEntityBox
                .query()
                .equal(GankCollectionEntity_.remoteId, remoteCollectionId)
                .build()
                .findUnique();
        if (collectionEntity == null) {
            return null;
        }
        return buildVO(collectionEntity);
    }

    @Override
    public boolean checkIsCollectionExist(String remoteCollectionId) {
        GankCollectionEntity entity = mEntityBox
                .query()
                .equal(GankCollectionEntity_.remoteId, remoteCollectionId)
                .build()
                .findUnique();
        return entity != null;
    }

    @Override
    public List<GankCollectionVO> getCollectionList(GankCollectionType collectionType) {
        List<GankCollectionEntity> resultList = mEntityBox
                .query()
                .equal(GankCollectionEntity_.collectionType, collectionType.getType())
                .orderDesc(GankCollectionEntity_.createTime)
                .build()
                .find();
        List<GankCollectionVO> result = new ArrayList<>();
        for (GankCollectionEntity entity : resultList) {
            result.add(buildVO(entity));
        }
        return result;
    }

    private GankCollectionVO buildVO(GankCollectionEntity entity) {
        GankCollectionVO vo = new GankCollectionVO();
        vo.setId(entity.id);
        vo.setRemoteId(entity.remoteId);
        vo.setCollectionType(GankCollectionType.mapper(entity.collectionType));
        vo.setCreatedAt(entity.createdAt);
        vo.setDesc(entity.desc);
        vo.setPublishedAt(entity.publishedAt);
        vo.setSource(entity.source);
        vo.setType(entity.type);
        vo.setUrl(entity.url);
        vo.setUsed(entity.used);
        vo.setWho(entity.who);
        return vo;
    }
}