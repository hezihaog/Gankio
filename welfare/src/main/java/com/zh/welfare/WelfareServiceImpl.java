package com.zh.welfare;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zh.base.base.BaseFragment;
import com.zh.base.controller.ImageBrowserViewController;
import com.zh.service.api.bean.GankBean;
import com.zh.service.base.BaseService;
import com.zh.service.base.ARouterUrl;
import com.zh.service.welfare.WelfareService;

import java.util.ArrayList;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * <b>Package:</b> com.zh.welfare <br>
 * <b>Create Date:</b> 2019/3/27  6:50 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
@Route(path = ARouterUrl.WELFARE_SERVICES, name = "福利模块服务")
public class WelfareServiceImpl extends BaseService implements WelfareService {

    @Override
    public void init(Context context) {
    }

    @Override
    public BaseFragment getWelfareFragment() {
        return WelfareFragment.newInstance();
    }

    @Override
    public void routerMeiziDetail(SupportActivity activity, ArrayList<GankBean.ResultsBean> meziBeans, int browserIndex) {
        ISupportFragment fragment = (ISupportFragment) ARouter
                .getInstance()
                .build(ARouterUrl.MEIZI_DETAIL)
                .withSerializable(MeiziBrowserFragment.KEY_MEI_ZI_BEANS, meziBeans)
                .withInt(ImageBrowserViewController.KEY_IMAGE_BROWSER_CURRENT_INDEX, browserIndex)
                .navigation();
        startFragment(activity, fragment);
    }
}