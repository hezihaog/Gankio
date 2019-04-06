package com.zh.service.api;

import io.reactivex.Observable;
import com.zh.service.api.bean.GankBean;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Package: me.wally.gankio.api
 * FileName: GankService
 * Date: on 2018/10/31  上午11:53
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface GankService {
    /**
     * 获取分类数据
     *
     * @param type     数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param pageSize 请求个数： 数字，大于0
     * @param page     第几页：数字，大于0
     */
    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<GankBean> getGankAtCategory(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") int page);

    /**
     * 随机获取妹子图
     *
     * @param count 获取多少个
     */
    @GET("api/random/data/福利/{count}")
    Observable<GankBean> getRandomGirl(@Path("count") int count);
}