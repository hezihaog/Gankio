package com.zh.collect;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.mario.baseadapter.VBaseAdapter;
import com.mario.baseadapter.holder.VBaseHolderHelper;
import com.mario.baseadapter.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zh.base.base.BaseApplication;
import com.zh.base.base.BaseFragment;
import com.zh.base.base.mvp.IPresenter;
import com.zh.base.util.DimenUtil;
import com.zh.service.api.bean.GankBean;
import com.zh.service.base.ARouterUrl;
import com.zh.service.collect.CollectService;
import com.zh.service.common.enums.GankCollectionType;
import com.zh.service.db.model.vo.GankCollectionVO;
import com.zh.service.welfare.mvp.presenter.IGankCollectionPresenter;
import com.zh.service.welfare.mvp.presenter.impl.GankCollectionPresenter;
import com.zh.service.welfare.mvp.view.IGankCollectionView;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: me.wally.gankio.fragment
 * FileName: ArticleFragment
 * Date: on 2018/11/1  上午10:31
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ArticleFragment extends BaseFragment implements IGankCollectionView.IObtainCollectionView {
    @Autowired(name = ARouterUrl.COLLECT_SERVICES)
    CollectService mCollectService;

    RecyclerView mArticleRecyclerView;
    SmartRefreshLayout mRefreshLayout;

    private IGankCollectionPresenter mCollectionPresenter;
    private VBaseAdapter<GankCollectionVO> mArticleAdapter;

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    protected void onSetupPresenters(ArrayList<IPresenter> presenterList) {
        super.onSetupPresenters(presenterList);
        mCollectionPresenter = new GankCollectionPresenter(
                null,
                null,
                this,
                null);
        presenterList.add(mCollectionPresenter);
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_collection_article;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        findView(getView());
        bindView();
    }

    private void findView(View view) {
        mArticleRecyclerView = view.findViewById(R.id.article_recycler_view);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
    }

    private void bindView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(getContext());
        mArticleRecyclerView.setLayoutManager(layoutManager);
        DelegateAdapter adapter = new DelegateAdapter(layoutManager, true);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper(DimenUtil.dip2px(getContext(), 15f));
        linearLayoutHelper.setMargin(
                DimenUtil.dip2px(getContext(), 5f),
                DimenUtil.dip2px(getContext(), 15f),
                DimenUtil.dip2px(getContext(), 5f),
                DimenUtil.dip2px(getContext(), 5f));
        mArticleAdapter = new VBaseAdapter<GankCollectionVO>(R.layout.item_gank_collection_article, linearLayoutHelper) {
            @Override
            protected void convert(VBaseHolderHelper helper, GankCollectionVO gankCollectionVO, int position) {
                super.convert(helper, gankCollectionVO, position);
                helper.setText(R.id.auther_tv, gankCollectionVO.getWho());
                helper.setText(R.id.desc_tv, gankCollectionVO.getDesc());
            }
        };
        mArticleAdapter.addOnItemClickListener(new OnItemClickListener<GankCollectionVO>() {
            @Override
            public void onItemClick(View view, int position, GankCollectionVO vo) {
                GankBean.ResultsBean bean = new GankBean.ResultsBean();
                bean.setId(vo.getRemoteId());
                bean.setCreatedAt(vo.getCreatedAt());
                bean.setDesc(vo.getDesc());
                bean.setPublishedAt(vo.getPublishedAt());
                bean.setSource(vo.getSource());
                bean.setType(vo.getType());
                bean.setUrl(vo.getUrl());
                bean.setUsed(vo.isUsed());
                bean.setWho(vo.getWho());
                mCollectService.routerArticleDetail((SupportActivity) getActivity(), bean);
            }
        });
        adapter.addAdapter(mArticleAdapter);
        mArticleRecyclerView.setAdapter(adapter);
        adapter.addAdapter(new VBaseAdapter());
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mCollectionPresenter.getCollectionList(GankCollectionType.ARTICLE);
            }
        });
        mRefreshLayout.setEnableOverScrollBounce(false);
        mRefreshLayout.setEnableOverScrollDrag(false);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public String getPageTitle() {
        return BaseApplication.shareInstance().getResources().getString(R.string.page_collection_article_title);
    }

    @Override
    public void onGetCollectionListSuccess(List<GankCollectionVO> gankCollectionVOS) {
        mArticleAdapter.clearData();
        mArticleAdapter.addItems(gankCollectionVOS);
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onGetCollectionListFail(String message) {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onGetCollectionListError(Throwable error) {
        mRefreshLayout.finishRefresh();
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