package me.wally.gankio.db.model.dto;

import me.wally.gankio.enums.GankCollectionType;

/**
 * Package: me.wally.gankio.db.model.dto
 * FileName: GankCollectionDTO
 * Date: on 2018/11/1  上午11:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCollectionDTO {
    public GankCollectionDTO(String remoteId, GankCollectionType collectionType, String createdAt, String desc, String publishedAt, String source, String type, String url, Boolean used, String who) {
        this.remoteId = remoteId;
        this.collectionType = collectionType;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    private String remoteId;

    /**
     * 收藏类型枚举
     */
    private GankCollectionType collectionType;

    private String createdAt;

    private String desc;

    private String publishedAt;

    private String source;

    private String type;

    private String url;

    private Boolean used;

    private String who;

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public GankCollectionType getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(GankCollectionType collectionType) {
        this.collectionType = collectionType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}