package me.wally.gankio.ui.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import java.util.LinkedHashMap;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.base.BaseFragment;

public class MainFragment extends BaseFragment {
    @BindView(R.id.bottom_bar)
    BottomNavigationView mBottomBar;

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
        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (fragments.containsKey(id)) {
                    BaseFragment fragment = fragments.get(id);
                    showHideFragment(fragment);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.app_name);
    }
}
