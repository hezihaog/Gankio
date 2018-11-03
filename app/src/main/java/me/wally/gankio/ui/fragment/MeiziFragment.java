package me.wally.gankio.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.bumptech.glide.Glide;
import com.mario.baseadapter.VBaseAdapter;
import com.mario.baseadapter.holder.VBaseHolderHelper;
import com.mario.baseadapter.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.UIRouterHelper;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.base.BaseFragment;
import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.enums.GankCollectionType;
import me.wally.gankio.mvp.base.IPresenter;
import me.wally.gankio.mvp.presenter.IGankCollectionPresenter;
import me.wally.gankio.mvp.presenter.impl.GankCollectionPresenter;
import me.wally.gankio.mvp.view.IGankCollectionView;
import me.wally.gankio.util.DimenUtil;
import me.wally.gankio.util.ToastUtil;
import me.wally.gankio.widget.RatioImageView;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: me.wally.gankio.fragment
 * FileName: MeiziFragment
 * Date: on 2018/11/1  上午10:31
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class MeiziFragment extends BaseFragment implements IGankCollectionView.IObtainCollectionView {
    @BindView(R.id.mezi_recycler_view)
    RecyclerView mMeziRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private IGankCollectionPresenter mCollectionPresenter;
    private VBaseAdapter<GankCollectionVO> mCollectionAdapter;
    private ArrayList<GankCollectionVO> mDataList = new ArrayList<>();

    public static MeiziFragment newInstance() {
        return new MeiziFragment();
    }

    @Override
    protected void onSetupPresenters(ArrayList<IPresenter> presenterList) {
        super.onSetupPresenters(presenterList);
        mCollectionPresenter = new GankCollectionPresenter(null, null, this, null);
        presenterList.add(mCollectionPresenter);
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_collection_meizi;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(getContext());
        mMeziRecyclerView.setLayoutManager(layoutManager);
        DelegateAdapter adapter = new DelegateAdapter(layoutManager, true);
        StaggeredGridLayoutHelper layoutHelper = new StaggeredGridLayoutHelper();
        //设置一行两列
        layoutHelper.setLane(2);
        layoutHelper.setMargin(
                DimenUtil.dip2px(getContext(), 5f),
                DimenUtil.dip2px(getContext(), 10f),
                DimenUtil.dip2px(getContext(), 5f),
                DimenUtil.dip2px(getContext(), 10f));
        //宽高比例
        //layoutHelper.setAspectRatio(0.8f);
        mCollectionAdapter = new VBaseAdapter<GankCollectionVO>(mDataList, R.layout.item_gank_collection_gril, layoutHelper) {
            @Override
            protected void convert(VBaseHolderHelper helper, GankCollectionVO gankCollectionVO, int position) {
                super.convert(helper, gankCollectionVO, position);
                //固定宽高比
                RatioImageView grilImageView = (RatioImageView) helper.getImageView(R.id.girl_iv);
                if (position % 2 == 0) {
                    grilImageView.setOriginalSize(50, 70);
                } else {
                    grilImageView.setOriginalSize(50, 80);
                }
                grilImageView.requestLayout();
                Glide
                        .with(getContext())
                        .load(gankCollectionVO.getUrl())
                        .into(grilImageView);
            }
        };
        mCollectionAdapter.addOnItemClickListener(new OnItemClickListener<GankCollectionVO>() {
            @Override
            public void onItemClick(View view, int position, GankCollectionVO gankCollectionVO) {
                ArrayList<GankBean.ResultsBean> meiziBeans = new ArrayList<>();
                for (GankCollectionVO vo : mDataList) {
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
                    meiziBeans.add(bean);
                }
                UIRouterHelper.routerMeiziDetail((SupportActivity) getActivity(), meiziBeans, position);
            }
        });
        adapter.addAdapter(mCollectionAdapter);
        mMeziRecyclerView.setAdapter(adapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mCollectionPresenter.getCollectionList(GankCollectionType.MEIZI);
            }
        });
        mRefreshLayout.setEnableOverScrollBounce(false);
        mRefreshLayout.setEnableOverScrollDrag(false);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_collection_meizi_title);
    }

    @Override
    public void onGetCollectionListSuccess(List<GankCollectionVO> gankCollectionVOS) {
        mDataList.clear();
        mDataList.addAll(gankCollectionVOS);
        mCollectionAdapter.notifyDataSetChangedWrapper();
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onGetCollectionListFail(String message) {
        ToastUtil.toast(getContext(), message);
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onGetCollectionListError(Throwable error) {
        ToastUtil.toast(getContext(), error.getMessage());
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