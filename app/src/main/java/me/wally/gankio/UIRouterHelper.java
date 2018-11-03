package me.wally.gankio;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;

import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.controller.ImageBrowserViewController;
import me.wally.gankio.ui.fragment.ArticleDetailFragment;
import me.wally.gankio.ui.fragment.MeiziBrowserFragment;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: me.wally.gankio
 * FileName: UIRouterHelper
 * Date: on 2018/11/1  下午3:49
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class UIRouterHelper {
    private UIRouterHelper() {
    }

    private static void startFragment(SupportActivity activity, ISupportFragment fragment) {
        activity.start(fragment, ISupportFragment.SINGLETOP);
    }

    /**
     * 跳转到文章详情
     */
    public static void routerArticleDetail(SupportActivity activity, GankBean.ResultsBean articleBean) {
        ISupportFragment fragment = (ISupportFragment) ARouter
                .getInstance()
                .build(UIRouterPath.ARTICLE_DETAIL)
                .withSerializable(ArticleDetailFragment.KEY_DATA_LIST, articleBean)
                .navigation();
        startFragment(activity, fragment);
    }

    /**
     * 跳转到图片详情
     */
    public static void routerMeiziDetail(SupportActivity activity, ArrayList<GankBean.ResultsBean> meziBeans, int browserIndex) {
        ISupportFragment fragment = (ISupportFragment) ARouter
                .getInstance()
                .build(UIRouterPath.MEIZI_DETAIL)
                .withSerializable(MeiziBrowserFragment.KEY_MEI_ZI_BEANS, meziBeans)
                .withInt(ImageBrowserViewController.KEY_IMAGE_BROWSER_CURRENT_INDEX, browserIndex)
                .navigation();
        startFragment(activity, fragment);
    }
}