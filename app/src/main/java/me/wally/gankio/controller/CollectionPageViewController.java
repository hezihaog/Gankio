package me.wally.gankio.controller;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.controller.base.UIViewController;
import me.wally.gankio.controller.base.UIViewControllerPageAdapter;
import me.wally.gankio.widget.SlidingViewPager;

/**
 * Package: me.wally.gankio.controller
 * FileName: CollectionPageViewController
 * Date: on 2018/10/30  下午4:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class CollectionPageViewController extends BaseUIViewController {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.collection_tab_layout)
    TabLayout mCollectionTabLayout;
    @BindView(R.id.collection_view_pager)
    SlidingViewPager mCollectionViewPager;

    public static CollectionPageViewController newInstance() {
        return new CollectionPageViewController();
    }

    @Override
    public int onLayoutId() {
        return R.layout.page_collection;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mCollectionTabLayout.setupWithViewPager(mCollectionViewPager);
        final ArrayList<BaseUIViewController> controllers = new ArrayList<>();
        controllers.add(MeiziViewController.newInstance());
        controllers.add(ArticleViewController.newInstance());
        mCollectionViewPager.setAdapter(new UIViewControllerPageAdapter(getViewControllerManager()) {
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
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_collection_title);
    }
}