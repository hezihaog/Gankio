package me.wally.gankio.ui.activity;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.base.BaseActivity;
import me.wally.gankio.controller.BaseUIViewController;
import me.wally.gankio.controller.CollectionPageViewController;
import me.wally.gankio.controller.HomePageViewController;
import me.wally.gankio.controller.WelfarePageViewController;
import me.wally.gankio.controller.base.UIViewController;
import me.wally.gankio.controller.base.UIViewControllerPageAdapter;
import me.wally.gankio.widget.SlidingViewPager;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_view_page)
    SlidingViewPager mMainViewPage;
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    @Override
    public int onLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        final ArrayList<BaseUIViewController> pageControllers = new ArrayList<>();
        HomePageViewController homePageVC = HomePageViewController.newInstance();
        WelfarePageViewController welfarePageVC = WelfarePageViewController.newInstance();
        CollectionPageViewController collectionVC = CollectionPageViewController.newInstance();
        pageControllers.add(homePageVC);
        pageControllers.add(welfarePageVC);
        pageControllers.add(collectionVC);
        mMainViewPage.setEnableScrollable(false);
        mMainViewPage.setAdapter(new UIViewControllerPageAdapter(getViewControllerManager()) {
            @Override
            public UIViewController getItem(int position) {
                return pageControllers.get(position);
            }

            @Override
            public int getCount() {
                return pageControllers.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return pageControllers.get(position).getPageTitle();
            }
        });
        mMainViewPage.setOffscreenPageLimit(pageControllers.size());
        mMainViewPage.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mBottomBar.selectTabAtPosition(position);
            }
        });
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                int index = 0;
                switch (tabId) {
                    case R.id.tab_home:
                        index = 0;
                        break;
                    case R.id.tab_welfare:
                        index = 1;
                        break;
                    case R.id.tab_collection:
                        index = 2;
                        break;
                    default:
                        break;
                }
                mMainViewPage.setCurrentItem(index, false);
            }
        });
    }
}
