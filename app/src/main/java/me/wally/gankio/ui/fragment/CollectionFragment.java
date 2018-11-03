package me.wally.gankio.ui.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.adapter.CommonFragmentStatePageAdapter;
import me.wally.gankio.base.BaseFragment;
import me.wally.gankio.widget.SlidingViewPager;

/**
 * Package: me.wally.gankio.fragment
 * FileName: CollectionFragment
 * Date: on 2018/10/30  下午4:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class CollectionFragment extends BaseFragment {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.collection_tab_layout)
    TabLayout mCollectionTabLayout;
    @BindView(R.id.collection_view_pager)
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
        return UIApplication.shareInstance().getResources().getString(R.string.page_collection_title);
    }
}