package me.wally.gankio.ui.fragment;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.UIRouterPath;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.base.BaseActivity;
import me.wally.gankio.base.BaseFragment;
import me.wally.gankio.controller.ImageBrowserViewController;

/**
 * Package: me.wally.gankio.ui.fragment
 * FileName: MeiziBrowserFragment
 * Date: on 2018/11/2  下午3:29
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Route(path = UIRouterPath.MEIZI_DETAIL)
public class MeiziBrowserFragment extends BaseFragment {
    public static final String KEY_MEI_ZI_BEANS = "key_mei_zi_beans";

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;

    @Autowired(name = KEY_MEI_ZI_BEANS)
    ArrayList<GankBean.ResultsBean> mMeiziBeans;
    @Autowired(name = ImageBrowserViewController.KEY_IMAGE_BROWSER_CURRENT_INDEX)
    int mBrowserIndex;

    private boolean isHideToolBar = false;

    @Override
    protected Boolean setupSwipeBackEnable() {
        return true;
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_meizi_browser;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mToolBar.setTitle(mMeiziBeans.get(mBrowserIndex).getDesc());
        mToolBar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        ArrayList<String> urls = new ArrayList<>();
        for (GankBean.ResultsBean bean : mMeiziBeans) {
            urls.add(bean.getUrl());
        }
        ImageBrowserViewController browserViewController = ImageBrowserViewController.newInstance(urls, mBrowserIndex);
        ((BaseActivity) getActivity()).getViewControllerManager().add(R.id.container, browserViewController, ImageBrowserViewController.class.getName(), true);
        browserViewController.setOnPhotoTapCallback(new ImageBrowserViewController.OnPhotoTapCallback() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                toggleToolBar();
            }
        });
        browserViewController.setOnPhotoPageChangeCallback(new ImageBrowserViewController.SimplePhotoPageChangeCallback() {

            @Override
            public void onPhotoPageSelected(int position) {
                mToolBar.setTitle(mMeiziBeans.get(position).getDesc());
            }
        });
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_image_browser_title);
    }

    private void toggleToolBar() {
        //隐藏
        if (!isHideToolBar) {
            mToolBar.animate()
                    .translationY((-mToolBar.getHeight() * 1.0f))
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            //StatusBarUtil.hideStatusBar(this);
        } else {
            //显示
            mToolBar.animate()
                    .translationY(0f)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            //StatusBarUtil.showStatusBar(this);
        }
        isHideToolBar = !isHideToolBar;
    }
}
