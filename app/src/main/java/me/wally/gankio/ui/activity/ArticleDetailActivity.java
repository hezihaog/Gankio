package me.wally.gankio.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import me.wally.gankio.R;
import me.wally.gankio.UIRouterPath;
import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.base.BaseActivity;
import me.wally.gankio.controller.ArticleDetailViewController;

/**
 * Package: me.wally.gankio.ui.activity
 * FileName: ArticleDetailActivity
 * Date: on 2018/11/1  下午3:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Route(path = UIRouterPath.ARTICLE_DETAIL)
public class ArticleDetailActivity extends BaseActivity {
    public static final String KEY_DATA_LIST = "key_data_list";

    @Autowired(name = KEY_DATA_LIST)
    protected GankBean.ResultsBean mArticleBean;

    @Override
    public int onLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        ArticleDetailViewController controller = ArticleDetailViewController.newInstance(mArticleBean);
        getViewControllerManager()
                .add(R.id.container, controller, ArticleDetailViewController.class.getName(), true);
    }
}
