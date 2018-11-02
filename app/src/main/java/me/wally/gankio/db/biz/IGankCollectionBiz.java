package me.wally.gankio.db.biz;

import java.util.List;

import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.enums.GankCollectionType;

/**
 * Package: me.wally.gankio.db.biz
 * FileName: IGankCollectionBiz
 * Date: on 2018/11/1  上午11:57
 * Auther: zihe
 * Descirbe:收藏表Db业务层
 * Email: hezihao@linghit.com
 */
public interface IGankCollectionBiz {
    boolean addCollection(GankCollectionDTO dto);

    boolean removeCollection(String remoteCollectionId);

    List<GankCollectionVO> getCollectionList(GankCollectionType collectionType);

    Boolean checkIsCollection(String remoteCollectionId);
}