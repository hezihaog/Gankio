package com.zh.collect;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zh.base.base.BaseFragment;
import com.zh.service.api.bean.GankBean;
import com.zh.service.base.ARouterUrl;
import com.zh.service.base.BaseService;
import com.zh.service.collect.CollectService;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * <b>Package:</b> com.zh.collect <br>
 * <b>Create Date:</b> 2019/3/27  6:51 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
@Route(path = ARouterUrl.COLLECT_SERVICES, name = "收藏模块服务")
public class CollectServiceImpl extends BaseService implements CollectService {

    @Override
    public void init(Context context) {
    }

    @Override
    public BaseFragment getCollectionFragment() {
        return CollectionFragment.newInstance();
    }

    @Override
    public void routerArticleDetail(SupportActivity activity, GankBean.ResultsBean articleBean) {
//        ISupportFragment fragment = (ISupportFragment) ARouter
//                .getInstance()
//                .build(ARouterUrl.ARTICLE_DETAIL)
//                .withSerializable(ArticleDetailFragment.KEY_DATA_LIST, articleBean)
//                .navigation();
        ArticleDetailFragment fragment = ArticleDetailFragment.newInstance(articleBean);
        startFragment(activity, fragment);
    }
}