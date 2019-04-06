package com.zh.service.common.enums;

/**
 * Package: me.wally.gankio.enums
 * FileName: GankCollectionType
 * Date: on 2018/11/1  上午10:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public enum GankCollectionType {
    MEIZI("1", "妹子"),
    ARTICLE("2", "文章");

    private String mType;
    private String mName;

    GankCollectionType(String type, String name) {
        mType = type;
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public static GankCollectionType mapper(String type) {
        if (MEIZI.getType().equals(type)) {
            return MEIZI;
        } else if (ARTICLE.getType().equals(type)) {
            return ARTICLE;
        }
        throw new IllegalArgumentException("没有找到指定的收藏类型");
    }

    @Override
    public String toString() {
        return "GankCollectionType{" +
                "mType=" + mType +
                ", mName='" + mName + '\'' +
                '}';
    }
}