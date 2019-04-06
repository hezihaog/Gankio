package com.zh.home;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zh.service.base.ARouterUrl;
import com.zh.service.home.HomeService;

/**
 * <b>Package:</b> com.zh.home <br>
 * <b>Create Date:</b> 2019/3/27  6:49 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
@Route(path = ARouterUrl.HOME_SERVICES, name = "首页模块服务")
public class HomeServiceImpl implements HomeService {
    @Override
    public void init(Context context) {

    }
}