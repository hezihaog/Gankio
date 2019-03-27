package me.wally.gankio.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zh.base.util.rx.RxLifecycleUtil;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.UIRouterPath;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.base.BaseActivity;
import me.wally.gankio.base.BaseFragment;
import me.wally.gankio.controller.ImageBrowserViewController;
import me.wally.gankio.util.FileUtil;
import me.wally.gankio.util.ImageDownloadUtil;
import me.wally.gankio.util.StatusBarUtil;
import me.wally.gankio.util.ToastUtil;

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
    @BindView(R.id.download_image_iv)
    FloatingActionButton mDownloadImageIv;

    private boolean isHideToolBar = false;

    @Override
    public void onDestroy() {
        StatusBarUtil.showStatusBar(getActivity());
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

    @Override
    protected Boolean setupSwipeBackEnable() {
        return true;
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_meizi_browser;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        ImmersionBar.with(this).titleBar(mToolBar);
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
        final ImageBrowserViewController browserViewController = ImageBrowserViewController.newInstance(urls, mBrowserIndex);
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
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        RxView
                .clicks(mDownloadImageIv)
                .compose(rxPermissions.ensureEachCombined(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .as(RxLifecycleUtil.bindLifecycle(this))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        final Context context = getActivity().getApplicationContext();
                        if (permission.granted) {
                            int currentBrowserIndex = browserViewController.getCurrentBrowserIndex();
                            String currentBrowserImageUrl = browserViewController.getCurrentBrowserImageUrl();
                            final String savePath = FileUtil.getAppRootDirPath(context);
                            ImageDownloadUtil.saveImageObservable(context,
                                    currentBrowserImageUrl,
                                    savePath,
                                    mMeiziBeans.get(currentBrowserIndex).getId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .as(RxLifecycleUtil.bindLifecycle(getSelf()))
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String resultPath) throws Exception {
                                            ImageDownloadUtil.notifyImageSaveGallery(context, resultPath);
                                            String msg = String.format(context.getResources().getString(R.string.picture_save_success_tip), resultPath);
                                            ToastUtil.toastLong(context, msg);
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            ToastUtil.toast(context, throwable.getMessage());
                                        }
                                    });
                        } else {
                            ToastUtil.toast(context, "允许权限，才能保存图片喔");
                        }
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
            StatusBarUtil.hideStatusBar(getActivity());
        } else {
            //显示
            mToolBar.animate()
                    .translationY(0f)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            StatusBarUtil.showStatusBar(getActivity());
        }
        isHideToolBar = !isHideToolBar;
    }
}
