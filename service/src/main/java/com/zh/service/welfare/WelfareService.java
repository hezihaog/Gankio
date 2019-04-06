package com.zh.service.welfare;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.zh.base.base.BaseFragment;
import com.zh.service.api.bean.GankBean;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * <b>Package:</b> com.zh.service.welfare <br>
 * <b>Create Date:</b> 2019/3/27  6:45 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
public interface WelfareService extends IProvider {
    BaseFragment getWelfareFragment();

    /**
     * 跳转到图片详情
     */
    void routerMeiziDetail(SupportActivity activity, ArrayList<GankBean.ResultsBean> meziBeans, int browserIndex);
}