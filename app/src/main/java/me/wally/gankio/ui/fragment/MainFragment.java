package me.wally.gankio.ui.fragment;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.LinkedHashMap;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.base.BaseFragment;

public class MainFragment extends BaseFragment {
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        final LinkedHashMap<Integer, BaseFragment> fragments = new LinkedHashMap<>();
        if (findChildFragment(HomeFragment.class) == null) {
            HomeFragment homeFragment = HomeFragment.newInstance();
            WelfareFragment welfareFragment = WelfareFragment.newInstance();
            CollectionFragment collectionFragment = CollectionFragment.newInstance();
            fragments.put(R.id.tab_home, homeFragment);
            fragments.put(R.id.tab_welfare, welfareFragment);
            fragments.put(R.id.tab_collection, collectionFragment);
            loadMultipleRootFragment(
                    R.id.main_tab_container,
                    0,
                    homeFragment,
                    welfareFragment,
                    collectionFragment);
        } else {
            fragments.put(R.id.tab_home, findChildFragment(HomeFragment.class));
            fragments.put(R.id.tab_welfare, findChildFragment(WelfareFragment.class));
            fragments.put(R.id.tab_collection, findChildFragment(CollectionFragment.class));
        }
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                BaseFragment fragment = fragments.get(tabId);
                showHideFragment(fragment);
            }
        });
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.app_name);
    }
}
