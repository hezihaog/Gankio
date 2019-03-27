package me.wally.gankio.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.wally.gankio.controller.base.UIViewControllerManager;
import me.wally.gankio.mvp.base.IPresenter;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Package: me.wally.gankio.base
 * FileName: BaseFragment
 * Date: on 2018/11/1  下午2:46
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public abstract class BaseFragment extends SwipeBackFragment implements LayoutCallback {
    private ArrayList<IPresenter> mPresenterList;
    private Unbinder mButterKnifeBinder;

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLayoutBefore();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ARouter自动注入跳转参数
        ARouter.getInstance().inject(this);
        int layoutId = onLayoutId();
        if (layoutId > 0) {
            View rootLayout = inflater.inflate(layoutId, container, false);
            //设置为true才能回调onCreateOptionsMenu
            setHasOptionsMenu(true);
            //必须套一层侧滑返回布局才能发生侧滑
            return attachToSwipeBack(rootLayout);
        } else {
            return null;
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenterList = new ArrayList<>();
        onSetupPresenters(mPresenterList);
        for (IPresenter presenter : mPresenterList) {
            presenter.onStart();
        }
        onLayoutAfter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButterKnifeBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (setupSwipeBackEnable()) {
            setSwipeBackEnable(true);
            setParallaxOffset(0.5f);
        } else {
            setSwipeBackEnable(false);
        }
    }

    /**
     * 子类复写该方法返回true开启侧滑返回
     */
    protected Boolean setupSwipeBackEnable() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnifeBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenterList != null && mPresenterList.size() > 0) {
            for (IPresenter presenter : mPresenterList) {
                presenter.onDestroy();
            }
        }
    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onLayoutAfter() {
    }

    public abstract String getPageTitle();

    protected void onSetupPresenters(ArrayList<IPresenter> presenterList) {
    }

    public UIViewControllerManager getViewControllerManager() {
        return ((BaseActivity) getActivity()).getViewControllerManager();
    }

    public BaseFragment getSelf() {
        return this;
    }
}
