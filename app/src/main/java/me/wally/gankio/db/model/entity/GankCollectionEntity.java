package me.wally.gankio.db.model.entity;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Package: me.wally.gankio.db.model.entity
 * FileName: GankCollectionEntity
 * Date: on 2018/11/1  上午10:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Entity
public class GankCollectionEntity {
    /**
     * 表主键Id，必须为Long
     */
    @Id()
    public Long id;

    public Date createTime;

    public Date updateTime;

    public Integer deleteFlag;

    /**
     * 接口返回的Id
     */
    public String remoteId;

    /**
     * 收藏类型，自定义的枚举类型中的type
     */
    public String collectionType;

    /**
     * 创建时间，"2018-10-22T06:43:35.440Z"
     */
    public String createdAt;

    /**
     * 对干货内容的描述
     */
    public String desc;

    /**
     * 发布时间，"2018-10-22T00:00:00.0Z"
     */
    public String publishedAt;

    /**
     * 来源，"web"
     */
    public String source;

    /**
     * 类型，Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     */
    public String type;

    /**
     * 资源地址，"https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg"
     */
    public String url;

    /**
     * 是否禁用
     */
    public boolean used;

    /**
     * 作者，"lijinshanmx"
     */
    public String who;
}