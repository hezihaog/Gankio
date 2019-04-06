package com.zh.collect;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.zh.base.base.BaseActivity;
import com.zh.base.base.BaseApplication;
import com.zh.base.base.BaseFragment;
import com.zh.base.base.mvp.IPresenter;
import com.zh.base.controller.WebBrowserViewController;
import com.zh.base.util.ToastUtil;
import com.zh.service.api.bean.GankBean;
import com.zh.service.base.ARouterUrl;
import com.zh.service.common.enums.GankCollectionType;
import com.zh.service.db.model.dto.GankCollectionDTO;
import com.zh.service.welfare.mvp.presenter.IGankCollectionPresenter;
import com.zh.service.welfare.mvp.presenter.IGankWelfarePresenter;
import com.zh.service.welfare.mvp.presenter.impl.GankCollectionPresenter;
import com.zh.service.welfare.mvp.presenter.impl.GankWelfarePresenter;
import com.zh.service.welfare.mvp.view.IGankCollectionView;
import com.zh.service.welfare.mvp.view.IGankWelfareView;

import java.util.ArrayList;

/**
 * Package: me.wally.gankio.ui.fragment
 * FileName: ArticleDetailFragment
 * Date: on 2018/11/1  下午3:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Route(path = ARouterUrl.ARTICLE_DETAIL)
public class ArticleDetailFragment extends BaseFragment implements
        IGankCollectionView.IRemoveCollectionView,
        IGankCollectionView.ICheckCollectionView,
        IGankCollectionView.IAddCollectionView,
        IGankWelfareView {

    public static final String KEY_DATA_LIST = "key_data_list";

//    @Autowired(name = KEY_DATA_LIST)
    protected GankBean.ResultsBean mArticleBean;

    private ImageView mImageIv;
    private Toolbar mToolBar;
    private FloatingActionButton mLikeFab;

    private WebBrowserViewController mWebBrowserViewController;
    private IGankCollectionPresenter mPresenter;
    private boolean mIsCollection;
    private IGankWelfarePresenter mWelfarePresenter;

    public static ArticleDetailFragment newInstance(GankBean.ResultsBean articleBean) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATA_LIST, articleBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mArticleBean = (GankBean.ResultsBean) getArguments().getSerializable(KEY_DATA_LIST);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    protected Boolean setupSwipeBackEnable() {
        return true;
    }

    @Override
    protected void onSetupPresenters(ArrayList<IPresenter> presenterList) {
        super.onSetupPresenters(presenterList);
        mPresenter = new GankCollectionPresenter(
                this,
                this,
                null,
                this);
        mWelfarePresenter = new GankWelfarePresenter(this);
        presenterList.add(mPresenter);
        presenterList.add(mWelfarePresenter);
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_article_detail;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        findView(getView());
        bindView();
    }

    private void findView(View view) {
        mImageIv = view.findViewById(R.id.image_iv);
        mToolBar = view.findViewById(R.id.tool_bar);
        mLikeFab = view.findViewById(R.id.like_fab);
    }

    private void bindView() {
        ImmersionBar.with(this).titleBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getContext()).pop();
            }
        });
        mToolBar.setTitle(mArticleBean.getDesc());
        //随机获取一张妹子图
        mWelfarePresenter.getGankWelfare(1, true);
        //加载文章Url
        mWebBrowserViewController = WebBrowserViewController.newInstance(mArticleBean.getUrl());
        getViewControllerManager().add(R.id.web_browser_container, mWebBrowserViewController);
        mLikeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsCollection) {
                    mPresenter.removeCollection(mArticleBean.getId());
                } else {
                    mPresenter.addCollection(new GankCollectionDTO(
                            mArticleBean.getId(),
                            GankCollectionType.ARTICLE,
                            mArticleBean.getCreatedAt(),
                            mArticleBean.getDesc(),
                            mArticleBean.getPublishedAt(),
                            mArticleBean.getSource(),
                            mArticleBean.getType(),
                            mArticleBean.getUrl(),
                            mArticleBean.isUsed(),
                            mArticleBean.getWho()
                    ));
                }
            }
        });
        mPresenter.checkIsCollection(mArticleBean.getId());
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mWebBrowserViewController.onBackPressed()) {
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    @Override
    public String getPageTitle() {
        return BaseApplication.shareInstance().getResources().getString(R.string.page_article_detail_title);
    }

    @Override
    public void onAddCollectionSuccess() {
        mIsCollection = true;
        toggleLikeImageButton(true);
        ToastUtil.toast(getContext(), "收藏成功");
    }

    @Override
    public void onAddCollectionFail(String message) {
        ToastUtil.toast(getContext(), "添加收藏失败，请重试");
    }

    @Override
    public void onAddCollectionError(Throwable error) {
        ToastUtil.toast(getContext(), "收藏失败，请重试");
    }

    @Override
    public void onRemoveCollectionSuccess() {
        this.mIsCollection = false;
        toggleLikeImageButton(false);
    }

    @Override
    public void onRemoveCollectionFail(String message) {
        ToastUtil.toast(getContext(), "取消收藏失败，请重试");
    }

    @Override
    public void onRemoveCollectionError(Throwable error) {
        ToastUtil.toast(getContext(), "取消收藏失败，请重试");
    }

    @Override
    public void onCheckIsCollectionCallback(boolean isCollection) {
        this.mIsCollection = isCollection;
        toggleLikeImageButton(isCollection);
    }

    @Override
    public void onCheckIsCollectionError(Throwable error) {
        getActivity().finish();
    }

    private void toggleLikeImageButton(boolean isLike) {
        if (isLike) {
            mLikeFab.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorAccent)));
        } else {
            mLikeFab.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.C4)));
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage() {
    }

    @Override
    public void onLoadGankWelfare(GankBean bean, boolean isRefresh) {
        Glide.with(getContext()).load(bean.getResults().get(0).getUrl()).into(mImageIv);
    }

    @Override
    public void onLoadGankWelfareFail(String message, boolean isRefresh) {
    }

    @Override
    public void onLoadGankWelfareError(Throwable error, boolean isRefresh) {
    }
}
