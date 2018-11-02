package me.wally.gankio.db.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import me.wally.gankio.enums.DeleteFlag;


/**
 * Package: me.wally.gankio.db.model.entity
 * FileName: GankCollectionEntity
 * Date: on 2018/11/1  上午10:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Entity(nameInDb = "gank_collection")
public class GankCollectionEntity {
    /**
     * 表主键Id，必须为Long
     */
    @Id(autoincrement = true)
    public Long id;

    @Property(nameInDb = "create_time")
    @NotNull
    public Date createTime;

    @Property(nameInDb = "update_time")
    @NotNull
    public Date updateTime;

    /**
     * 删除标识，0：未删除，1：删除
     */
    @Property(nameInDb = "delete_flag")
    @NotNull
    public Integer deleteFlag = DeleteFlag.DELETED.getCode();

    /**
     * 版本号，默认1
     */
    @Property(nameInDb = "version")
    @NotNull
    private Integer version = 1;

    /**
     * 接口返回的Id
     */
    @Property(nameInDb = "remote_id")
    @NotNull
    public String remoteId;

    /**
     * 收藏类型，自定义的枚举类型中的type
     */
    @Property(nameInDb = "collection_type")
    @NotNull
    public String collectionType;

    /**
     * 创建时间，"2018-10-22T06:43:35.440Z"
     */
    @Property(nameInDb = "created_at")
    public String createdAt;

    /**
     * 对干货内容的描述
     */
    @Property(nameInDb = "desc")
    public String desc;

    /**
     * 发布时间，"2018-10-22T00:00:00.0Z"
     */
    @Property(nameInDb = "published_at")
    public String publishedAt;

    /**
     * 来源，"web"
     */
    @Property(nameInDb = "source")
    public String source;

    /**
     * 类型，Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     */
    @Property(nameInDb = "type")
    public String type;

    /**
     * 资源地址，"https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg"
     */
    @Property(nameInDb = "url")
    @NotNull
    public String url;

    /**
     * 是否禁用
     */
    @Property(nameInDb = "used")
    public Boolean used;

    /**
     * 作者，"lijinshanmx"
     */
    @Property(nameInDb = "who")
    public String who;

    @Generated(hash = 467215243)
    public GankCollectionEntity(Long id, @NotNull Date createTime,
            @NotNull Date updateTime, @NotNull Integer deleteFlag,
            @NotNull Integer version, @NotNull String remoteId,
            @NotNull String collectionType, String createdAt, String desc,
            String publishedAt, String source, String type, @NotNull String url,
            Boolean used, String who) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteFlag = deleteFlag;
        this.version = version;
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

    @Generated(hash = 1145307952)
    public GankCollectionEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRemoteId() {
        return this.remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getCollectionType() {
        return this.collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUsed() {
        return this.used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getWho() {
        return this.who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}