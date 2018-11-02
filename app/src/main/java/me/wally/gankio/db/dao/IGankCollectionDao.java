package me.wally.gankio.db.dao;

import java.util.List;

import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.enums.GankCollectionType;

/**
 * Package: me.wally.gankio.db.dao
 * FileName: IGankCollectionDao
 * Date: on 2018/11/1  上午11:57
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankCollectionDao {
    boolean addCollection(GankCollectionDTO dto);

    boolean removeCollection(String remoteCollectionId);

    /**
     * 用安卓收藏表Id获取对应的收藏信息
     */
    GankCollectionVO getCollectionById(String remoteCollectionId);

    /**
     * 检查是否存在收藏了
     *
     * @param remoteCollectionId 后台返回的Id
     */
    boolean checkIsCollectionExist(String remoteCollectionId);

    List<GankCollectionVO> getCollectionList(GankCollectionType collectionType);
}