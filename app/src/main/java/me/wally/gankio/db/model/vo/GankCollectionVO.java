package me.wally.gankio.db.model.vo;

import me.wally.gankio.enums.GankCollectionType;

/**
 * Package: me.wally.gankio.db.model.vo
 * FileName: GankCollectionVO
 * Date: on 2018/11/1  下午12:02
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCollectionVO {
    /**
     * 表主键Id，必须为Long
     */
    public Long id;

    /**
     * 接口返回的Id
     */
    private String remoteId;

    /**
     * 收藏的枚举类型
     */
    private GankCollectionType collectionType;

    /**
     * 创建时间，"2018-10-22T06:43:35.440Z"
     */
    private String createdAt;

    /**
     * 对干货内容的描述
     */
    private String desc;

    /**
     * 发布时间，"2018-10-22T00:00:00.0Z"
     */
    private String publishedAt;

    /**
     * 来源，"web"
     */
    public String source;

    /**
     * 类型，Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     */
    private String type;

    /**
     * 资源地址，"https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg"
     */
    private String url;

    /**
     * 是否禁用
     */
    private Boolean used;

    /**
     * 作者，"lijinshanmx"
     */
    private String who;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}