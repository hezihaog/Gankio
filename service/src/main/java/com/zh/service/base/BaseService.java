package com.zh.service.base;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * <b>Package:</b> com.zh.service.base <br>
 * <b>Create Date:</b> 2019/4/6  4:44 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
public class BaseService {
    protected static void startFragment(SupportActivity activity, ISupportFragment fragment) {
        activity.start(fragment, ISupportFragment.SINGLETOP);
    }
}