package me.wally.gankio.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.dingmouren.layoutmanagergroup.slide.ItemConfig;
import com.dingmouren.layoutmanagergroup.slide.ItemTouchHelperCallback;
import com.dingmouren.layoutmanagergroup.slide.OnSlideListener;
import com.dingmouren.layoutmanagergroup.slide.SlideLayoutManager;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;
import me.wally.gankio.Const;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.base.BaseFragment;
import me.wally.gankio.db.model.dto.GankCollectionDTO;
import me.wally.gankio.enums.GankCollectionType;
import me.wally.gankio.mvp.base.IPresenter;
import me.wally.gankio.mvp.presenter.IGankCollectionPresenter;
import me.wally.gankio.mvp.presenter.IGankWelfarePresenter;
import me.wally.gankio.mvp.presenter.impl.GankCollectionPresenter;
import me.wally.gankio.mvp.presenter.impl.GankWelfarePresenter;
import me.wally.gankio.mvp.view.IGankCollectionView;
import me.wally.gankio.mvp.view.IGankWelfareView;
import me.wally.gankio.util.ToastUtil;
import me.wally.gankio.viewbinder.WelfareViewBinder;

/**
 * Package: me.wally.gankio.fragment
 * FileName: WelfareFragment
 * Date: on 2018/10/30  下午4:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class WelfareFragment extends BaseFragment implements IGankWelfareView, IGankCollectionView.IAddCollectionView {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.welfare_recycler_view)
    RecyclerView mWelfareRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private IGankWelfarePresenter mWelfarePresenter;
    private IGankCollectionPresenter mCollectionPresenter;
    private List<GankBean.ResultsBean> mDataList = new ArrayList<>();
    private MultiTypeAdapter mAdapter;

    public static WelfareFragment newInstance() {
        return new WelfareFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    protected void onSetupPresenters(ArrayList<IPresenter> presenterList) {
        super.onSetupPresenters(presenterList);
        mWelfarePresenter = new GankWelfarePresenter(this);
        mCollectionPresenter = new GankCollectionPresenter(this, null, null, null);
        presenterList.add(mWelfarePresenter);
        presenterList.add(mCollectionPresenter);
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        ImmersionBar.with(this).titleBar(mToolBar);
        mAdapter = new MultiTypeAdapter();
        mAdapter
                .register(GankBean.ResultsBean.class)
                .to(new WelfareViewBinder())
                .withClassLinker(new ClassLinker<GankBean.ResultsBean>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<GankBean.ResultsBean, ?>> index(int position, @NonNull GankBean.ResultsBean resultsBean) {
                        return WelfareViewBinder.class;
                    }
                });
        mWelfareRecyclerView.setAdapter(mAdapter);
        ItemTouchHelperCallback<GankBean.ResultsBean> touchHelperCallback = new ItemTouchHelperCallback<>(mWelfareRecyclerView.getAdapter(), mDataList);
        touchHelperCallback.setOnSlideListener(new OnSlideListener<GankBean.ResultsBean>() {
            @Override
            public void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                //滑动中
            }

            @Override
            public void onSlided(RecyclerView.ViewHolder viewHolder, GankBean.ResultsBean bean, int direction) {
                //划掉了
                if (direction == ItemConfig.SLIDED_LEFT) {
                    //滑到左边，不喜欢，不做处理
                    LogUtils.d("滑到左边，不喜欢");
                } else if (direction == ItemConfig.SLIDED_RIGHT) {
                    //滑到右边，喜欢
                    LogUtils.d("滑到右边，喜欢");
                    GankCollectionDTO dto = new GankCollectionDTO(
                            bean.getId(),
                            GankCollectionType.MEIZI,
                            bean.getCreatedAt(),
                            bean.getDesc(),
                            bean.getPublishedAt(),
                            bean.getSource(),
                            bean.getType(),
                            bean.getUrl(),
                            bean.isUsed(),
                            bean.getWho()
                    );
                    mCollectionPresenter.addCollection(dto);
                }
                //当前总条目数量少于2，预先加载一批
                int allItemCount = mWelfareRecyclerView.getAdapter().getItemCount();
                LogUtils.d("当前总条目数：" + allItemCount);
//                if (allItemCount <= 2) {
//                    mPresenter.getGankWelfare(false);
//                }
            }

            @Override
            public void onClear() {
                //全部滑掉了，请求下一批
                mRefreshLayout.autoRefresh();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        SlideLayoutManager slideLayoutManager = new SlideLayoutManager(mWelfareRecyclerView, itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mWelfareRecyclerView);
        mWelfareRecyclerView.setLayoutManager(slideLayoutManager);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mWelfarePresenter.getGankWelfare(Const.Default.GIRL_RANDOM_SIZE, true);
            }
        });
        mRefreshLayout.setEnableOverScrollDrag(false);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_welfare_title);
    }

    @Override
    public void onLoadGankWelfare(GankBean bean, boolean isRefresh) {
        if (isRefresh) {
            mDataList.clear();
        }
        mDataList.addAll(bean.results);
        mAdapter.setItems(mDataList);
        mAdapter.notifyDataSetChanged();
        if (isRefresh) {
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void onLoadGankWelfareFail(String message, boolean isRefresh) {
        mRefreshLayout.finishRefresh();
        Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadGankWelfareError(Throwable error, boolean isRefresh) {
        mRefreshLayout.finishRefresh();
        Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onAddCollectionSuccess() {
        ToastUtil.toast(getContext(), "收藏成功");
    }

    @Override
    public void onAddCollectionFail(String message) {
        ToastUtil.toast(getContext(), message);
    }

    @Override
    public void onAddCollectionError(Throwable error) {
        ToastUtil.toast(getContext(), error.getMessage());
    }
}