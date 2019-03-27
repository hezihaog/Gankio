package me.wally.gankio.base;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.ButterKnife;
import me.wally.gankio.controller.base.UIViewControllerActivity;

/**
 * Package: me.wally.gankio.base
 * FileName: BaseActivity
 * Date: on 2018/10/31  上午11:44
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public abstract class BaseActivity extends UIViewControllerActivity implements LayoutCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        onLayoutBefore();
        setContentView(onLayoutId());
        ButterKnife.bind(this);
        onLayoutAfter();
    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onLayoutAfter() {
    }
}
