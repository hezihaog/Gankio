package com.zh.collect;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.zh.base.base.BaseApplication;
import com.zh.base.base.BaseFragment;
import com.zh.base.base.adapter.CommonFragmentStatePageAdapter;
import com.zh.base.widget.SlidingViewPager;

import java.util.ArrayList;


/**
 * Package: me.wally.gankio.fragment
 * FileName: CollectionFragment
 * Date: on 2018/10/30  下午4:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class CollectionFragment extends BaseFragment {
    Toolbar mToolBar;
    TabLayout mCollectionTabLayout;
    SlidingViewPager mCollectionViewPager;

    public static CollectionFragment newInstance() {
        return new CollectionFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        findView(getView());
        bindView();
    }

    private void findView(View view) {
        mToolBar = view.findViewById(R.id.tool_bar);
        mCollectionTabLayout = view.findViewById(R.id.collection_tab_layout);
        mCollectionViewPager = view.findViewById(R.id.collection_view_pager);
    }

    private void bindView() {
        ImmersionBar.with(this).titleBar(mToolBar);
        mCollectionTabLayout.setupWithViewPager(mCollectionViewPager);
        final ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(MeiziFragment.newInstance());
        fragments.add(ArticleFragment.newInstance());
        mCollectionViewPager.setAdapter(new CommonFragmentStatePageAdapter(getChildFragmentManager(), fragments) {

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return fragments.get(position).getPageTitle();
            }
        });
    }

    @Override
    public String getPageTitle() {
        return BaseApplication.shareInstance().getResources().getString(R.string.page_collection_title);
    }
}