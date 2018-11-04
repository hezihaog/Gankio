package me.wally.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.adapter.CommonFragmentStatePageAdapter;
import me.wally.gankio.base.BaseFragment;
import me.wally.gankio.enums.GankCategoryType;
import me.wally.gankio.widget.SlidingViewPager;

/**
 * Package: me.wally.gankio.fragment
 * FileName: HomeFragment
 * Date: on 2018/10/30  下午4:07
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.home_tab_layout)
    TabLayout mHomeTabLayout;
    @BindView(R.id.home_view_pager)
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(this).titleBar(mToolBar);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
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
        return UIApplication.shareInstance().getResources().getString(R.string.page_home_title);
    }
}