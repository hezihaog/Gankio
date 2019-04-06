package com.zh.base.base.controller;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by danney on 16/1/27.
 */
public abstract class UIViewControllerPageAdapter extends PagerAdapter {
    private UIViewControllerManager viewControllerManager;

    public UIViewControllerPageAdapter(UIViewControllerManager vcm) {
        viewControllerManager = vcm;
    }

    public abstract UIViewController getItem(int position);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String tag = makeTag(container.getId(), position);
        UIViewController vc = viewControllerManager.findViewControllerByTag(tag);
        if (vc != null) {
            container.addView(vc.getView());
        } else {
            vc = getItem(position);
            viewControllerManager.add(container.getId(), vc, tag, false);
        }
        return vc;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((UIViewController) object).getView());
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((UIViewController) object).getView() == view;
    }

    private String makeTag(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}