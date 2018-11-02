package me.wally.gankio.controller;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.controller.base.UIViewController;
import me.wally.gankio.controller.base.UIViewControllerPageAdapter;
import me.wally.gankio.enums.GankCategoryType;
import me.wally.gankio.widget.SlidingViewPager;

/**
 * Package: me.wally.gankio.controller
 * FileName: HomePageViewController
 * Date: on 2018/10/30  下午4:07
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class HomePageViewController extends BaseUIViewController {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.home_tab_layout)
    TabLayout mHomeTabLayout;
    @BindView(R.id.home_view_pager)
    SlidingViewPager mHomeViewPager;

    public static HomePageViewController newInstance() {
        return new HomePageViewController();
    }

    @Override
    public int onLayoutId() {
        return R.layout.page_home;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        final ArrayList<BaseUIViewController> controllers = new ArrayList<>();
        controllers.add(GankCategoryViewController.newInstance(GankCategoryType.ANDROID));
        controllers.add(GankCategoryViewController.newInstance(GankCategoryType.IOS));
        controllers.add(GankCategoryViewController.newInstance(GankCategoryType.WEB));
        mHomeTabLayout.setupWithViewPager(mHomeViewPager);
        mHomeViewPager.setAdapter(new UIViewControllerPageAdapter(getViewControllerManager()) {
            @Override
            public UIViewController getItem(int position) {
                return controllers.get(position);
            }

            @Override
            public int getCount() {
                return controllers.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return controllers.get(position).getPageTitle();
            }
        });
        mHomeViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        mHomeViewPager.setOffscreenPageLimit(controllers.size());
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_home_title);
    }
}