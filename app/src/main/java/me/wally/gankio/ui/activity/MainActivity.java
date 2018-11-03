package me.wally.gankio.ui.activity;

import me.wally.gankio.R;
import me.wally.gankio.base.BaseActivity;
import me.wally.gankio.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

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
