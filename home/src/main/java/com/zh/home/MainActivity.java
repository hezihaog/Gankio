package com.zh.home;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.zh.base.base.BaseActivity;
import com.zh.base.util.StatusBarUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.showStatusBar(this);
        ImmersionBar.with(this).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public int onLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.main_root_container, MainFragment.newInstance());
        }
    }
}