package com.zh.home;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.zh.base.base.BaseApplication;
import com.zh.base.base.BaseFragment;
import com.zh.base.base.adapter.CommonFragmentStatePageAdapter;
import com.zh.base.widget.SlidingViewPager;
import com.zh.service.common.enums.GankCategoryType;

import java.util.ArrayList;

/**
 * Package: me.wally.gankio.fragment
 * FileName: HomeFragment
 * Date: on 2018/10/30  下午4:07
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class HomeFragment extends BaseFragment {
    Toolbar mToolBar;
    TabLayout mHomeTabLayout;
    SlidingViewPager mHomeViewPager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        findView(getView());
        bindView();
    }

    private void findView(View view) {
        mToolBar = view.findViewById(R.id.tool_bar);
        mHomeTabLayout = view.findViewById(R.id.home_tab_layout);
        mHomeViewPager = view.findViewById(R.id.home_view_pager);
    }

    private void bindView() {
        ImmersionBar.with(this).titleBar(mToolBar);
        final ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(GankCategoryFragment.newInstance(GankCategoryType.ANDROID));
        fragments.add(GankCategoryFragment.newInstance(GankCategoryType.IOS));
        fragments.add(GankCategoryFragment.newInstance(GankCategoryType.WEB));
        mHomeTabLayout.setupWithViewPager(mHomeViewPager);
        mHomeViewPager.setAdapter(new CommonFragmentStatePageAdapter(getChildFragmentManager(), fragments) {

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return fragments.get(position).getPageTitle();
            }
        });
        mHomeViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        mHomeViewPager.setOffscreenPageLimit(fragments.size());
    }

    @Override
    public String getPageTitle() {
        return BaseApplication.shareInstance().getResources().getString(R.string.page_home_title);
    }
}