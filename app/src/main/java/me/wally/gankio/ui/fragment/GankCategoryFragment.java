package me.wally.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.mario.baseadapter.VBaseAdapter;
import com.mario.baseadapter.holder.VBaseHolderHelper;
import com.mario.baseadapter.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.UIRouterHelper;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.base.BaseFragment;
import me.wally.gankio.constant.GankConstant;
import me.wally.gankio.enums.GankCategoryType;
import me.wally.gankio.mvp.base.IPresenter;
import me.wally.gankio.mvp.presenter.IGankCategoryPresenter;
import me.wally.gankio.mvp.presenter.impl.GankCategoryPresenter;
import me.wally.gankio.mvp.view.IGankCategoryView;
import me.wally.gankio.util.DimenUtil;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: me.wally.gankio.fragment
 * FileName: GankCategoryFragment
 * Date: on 2018/10/31  下午2:53
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankCategoryFragment extends BaseFragment implements IGankCategoryView {
    private static final String KEY_CATEGORY_TYPE = "key_category_type";

    @BindView(R.id.home_recycler_view)
    RecyclerView mHomeRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private GankCategoryType mCategoryType;
    private IGankCategoryPresenter mPresenter;
    private VBaseAdapter<GankBean.ResultsBean> mCategoryTypeAdapter;
    private int pageIndex = GankConstant.Api.DEFAULT_PAGE_INDEX;
    private ArrayList<GankBean.ResultsBean> mDataList = new ArrayList<>();

    public static GankCategoryFragment newInstance(GankCategoryType categoryType) {
        GankCategoryFragment controller = new GankCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_CATEGORY_TYPE, categoryType);
        controller.setArguments(args);
        return controller;
    }

    @Override
    protected void onSetupPresenters(ArrayList<IPresenter> presenterList) {
        super.onSetupPresenters(presenterList);
        mPresenter = new GankCategoryPresenter(this);
        presenterList.add(mPresenter);
    }

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mCategoryType = (GankCategoryType) getArguments().getSerializable(KEY_CATEGORY_TYPE);
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_gank_category;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(getContext());
        mHomeRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0, 10);
        mHomeRecyclerView.setRecycledViewPool(viewPool);
        DelegateAdapter adapter = new DelegateAdapter(layoutManager, true);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper(DimenUtil.dip2px(getContext(), 15f));
        linearLayoutHelper.setMargin(
                DimenUtil.dip2px(getContext(), 5f),
                DimenUtil.dip2px(getContext(), 15f),
                DimenUtil.dip2px(getContext(), 5f),
                DimenUtil.dip2px(getContext(), 5f));
        mCategoryTypeAdapter = new VBaseAdapter<GankBean.ResultsBean>(mDataList, R.layout.item_gank_category_view, linearLayoutHelper) {
            @Override
            protected void convert(VBaseHolderHelper helper, GankBean.ResultsBean resultsBean, int position) {
                super.convert(helper, resultsBean, position);
                helper.setText(R.id.auther_tv, resultsBean.getWho());
                helper.setText(R.id.desc_tv, resultsBean.getDesc());
            }
        };
        mCategoryTypeAdapter.addOnItemClickListener(new OnItemClickListener<GankBean.ResultsBean>() {
            @Override
            public void onItemClick(View view, int position, GankBean.ResultsBean resultsBean) {
                UIRouterHelper.routerArticleDetail((SupportActivity) getActivity(), mDataList.get(position));
            }
        });
        adapter.addAdapter(mCategoryTypeAdapter);
        mHomeRecyclerView.setAdapter(adapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = GankConstant.Api.DEFAULT_PAGE_INDEX;
                mPresenter.getGankAtCategory(mCategoryType, GankConstant.Api.DEFAULT_PAGE_SIZE, pageIndex, true);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                mPresenter.getGankAtCategory(mCategoryType, GankConstant.Api.DEFAULT_PAGE_SIZE, pageIndex, false);
            }
        });
        mRefreshLayout.setEnableOverScrollBounce(false);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableAutoLoadMore(true);
        mRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public String getPageTitle() {
        mCategoryType = (GankCategoryType) getArguments().getSerializable(KEY_CATEGORY_TYPE);
        if (mCategoryType.equals(GankCategoryType.ANDROID)) {
            return UIApplication.shareInstance().getResources().getString(R.string.page_home_android_title);
        } else if (mCategoryType.equals(GankCategoryType.IOS)) {
            return UIApplication.shareInstance().getResources().getString(R.string.page_home_ios_title);
        } else if (mCategoryType.equals(GankCategoryType.WEB)) {
            return UIApplication.shareInstance().getResources().getString(R.string.page_home_web_title);
        } else {
            return "";
        }
    }

    private void finishLoadStatus(boolean isRefresh, boolean isNoMore) {
        if (isRefresh) {
            mRefreshLayout.finishRefresh();
        } else {
            if (isNoMore) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void onLoadGankAtCategory(GankBean bean, boolean isRefresh) {
        if (isRefresh) {
            mDataList.clear();
        }
        mDataList.addAll(bean.results);
        mCategoryTypeAdapter.notifyDataSetChangedWrapper();
        finishLoadStatus(isRefresh, bean.results.size() < GankConstant.Api.DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onLoadGankAtCategoryFail(String msg, boolean isRefresh) {
        finishLoadStatus(isRefresh, false);
    }

    @Override
    public void onLoadGankAtCategoryError(Throwable error, boolean isRefresh) {
        finishLoadStatus(isRefresh, false);
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
}
