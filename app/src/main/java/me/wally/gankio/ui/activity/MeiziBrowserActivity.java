package me.wally.gankio.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIRouterPath;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.base.BaseActivity;
import me.wally.gankio.controller.ImageBrowserViewController;
import me.wally.gankio.util.StatusBarUtil;

/**
 * Package: me.wally.gankio.ui.activity
 * FileName: MeiziBrowserActivity
 * Date: on 2018/11/2  下午3:29
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Route(path = UIRouterPath.MEIZI_DETAIL)
public class MeiziBrowserActivity extends BaseActivity {
    public static final String KEY_MEI_ZI_BEANS = "key_mei_zi_beans";
    public static final String KEY_MEI_ZI_BROWSER_INDEX = "key_mei_zi_browser_index";

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;

    @Autowired(name = KEY_MEI_ZI_BEANS)
    ArrayList<GankBean.ResultsBean> mMeiziBeans;
    @Autowired(name = KEY_MEI_ZI_BROWSER_INDEX)

    private int mBrowserIndex;
    private boolean isHideToolBar = false;

    @Override
    public int onLayoutId() {
        return R.layout.activity_meizi_browser;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mToolBar.setTitle(mMeiziBeans.get(mBrowserIndex).getDesc());
        mToolBar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArrayList<String> urls = new ArrayList<>();
        for (GankBean.ResultsBean bean : mMeiziBeans) {
            urls.add(bean.getUrl());
        }
        ImageBrowserViewController browserViewController = ImageBrowserViewController.newInstance(urls);
        getViewControllerManager().add(R.id.container, browserViewController, ImageBrowserViewController.class.getName(), true);
        browserViewController.setOnPhotoTapCallback(new ImageBrowserViewController.OnPhotoTapCallback() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                toggleToolBar();
            }
        });
    }

    private void toggleToolBar() {
        //隐藏
        if (!isHideToolBar) {
            mToolBar.animate()
                    .translationY((-mToolBar.getHeight() * 1.0f))
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            StatusBarUtil.hideStatusBar(this);
        } else {
            //显示
            mToolBar.animate()
                    .translationY(0f)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            StatusBarUtil.showStatusBar(this);
        }
        isHideToolBar = !isHideToolBar;
    }
}