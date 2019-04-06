package com.zh.service.collect;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.zh.base.base.BaseFragment;
import com.zh.service.api.bean.GankBean;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * <b>Package:</b> com.zh.service.collect <br>
 * <b>Create Date:</b> 2019/3/27  6:45 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
public interface CollectService extends IProvider {
    BaseFragment getCollectionFragment();

    /**
     * 跳转到文章详情
     */
    void routerArticleDetail(SupportActivity activity, GankBean.ResultsBean articleBean);
}