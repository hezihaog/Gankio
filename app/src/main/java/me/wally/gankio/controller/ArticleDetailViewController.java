package me.wally.gankio.controller;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.enums.GankCollectionType;
import me.wally.gankio.mvp.base.IPresenter;
import me.wally.gankio.mvp.presenter.IGankCollectionPresenter;
import me.wally.gankio.mvp.presenter.IGankWelfarePresenter;
import me.wally.gankio.mvp.presenter.impl.GankCollectionPresenter;
import me.wally.gankio.mvp.presenter.impl.GankWelfarePresenter;
import me.wally.gankio.mvp.view.IGankCollectionView;
import me.wally.gankio.mvp.view.IGankWelfareView;
import me.wally.gankio.ui.activity.ArticleDetailActivity;
import me.wally.gankio.util.ToastUtil;

/**
 * Package: me.wally.gankio.controller
 * FileName: ArticleDetailViewController
 * Date: on 2018/11/1  下午4:00
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ArticleDetailViewController extends BaseUIViewController implements
        IGankCollectionView.IRemoveCollectionView,
        IGankCollectionView.ICheckCollectionView,
        IGankCollectionView.IAddCollectionView,
        IGankWelfareView {
    @BindView(R.id.image_iv)
    ImageView mImageIv;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.web_browser_container)
    FrameLayout mWebBrowserContainer;
    @BindView(R.id.like_fab)
    FloatingActionButton mLikeFab;

    private GankBean.ResultsBean mArticleBean;
    private WebBrowserViewController mWebBrowserViewController;
    private IGankCollectionPresenter mPresenter;
    private boolean mIsCollection;
    private IGankWelfarePresenter mWelfarePresenter;

    public static ArticleDetailViewController newInstance(GankBean.ResultsBean articleBean) {
        ArticleDetailViewController controller = new ArticleDetailViewController();
        Bundle args = new Bundle();
        args.putSerializable(ArticleDetailActivity.KEY_DATA_LIST, articleBean);
        controller.setProps(args);
        return controller;
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
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mArticleBean = (GankBean.ResultsBean) getProps().getSerializable(ArticleDetailActivity.KEY_DATA_LIST);
    }

    @Override
    public int onLayoutId() {
        return R.layout.page_article_detail;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mToolBar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
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
    public boolean onBackPressed() {
        boolean isHandle = mWebBrowserViewController.onBackPressed();
        if (isHandle) {
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_article_detail_title);
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
        finish();
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
