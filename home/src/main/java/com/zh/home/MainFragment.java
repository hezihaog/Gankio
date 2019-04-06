package com.zh.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.zh.base.base.BaseApplication;
import com.zh.base.base.BaseFragment;
import com.zh.service.base.ARouterUrl;
import com.zh.service.collect.CollectService;
import com.zh.service.welfare.WelfareService;

import java.util.LinkedHashMap;

public class MainFragment extends BaseFragment {
    @Autowired(name = ARouterUrl.WELFARE_SERVICES)
    WelfareService mWelfareService;
    @Autowired(name = ARouterUrl.COLLECT_SERVICES)
    CollectService mCollectService;

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
        findView(getView());
        bindView();
    }

    private void findView(View view) {
        mBottomBar = view.findViewById(R.id.bottom_bar);
    }

    private void bindView() {
        final LinkedHashMap<Integer, BaseFragment> fragments = new LinkedHashMap<>();
        if (findChildFragment(HomeFragment.class) == null) {
            HomeFragment homeFragment = HomeFragment.newInstance();
            BaseFragment welfareFragment = mWelfareService.getWelfareFragment();
            BaseFragment collectionFragment = mCollectService.getCollectionFragment();
            fragments.put(R.id.tab_home, homeFragment);
            fragments.put(R.id.tab_welfare, welfareFragment);
            fragments.put(R.id.tab_collection, collectionFragment);
            loadMultipleRootFragment(
                    R.id.main_tab_container,
                    0,
                    homeFragment,
                    welfareFragment,
                    collectionFragment);
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
        return BaseApplication.shareInstance().getResources().getString(R.string.app_name);
    }
}
