package me.wally.gankio.ui.activity;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;

import me.wally.gankio.R;
import me.wally.gankio.base.BaseActivity;
import me.wally.gankio.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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