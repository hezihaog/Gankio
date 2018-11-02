package me.wally.gankio;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;

import me.wally.gankio.api.bean.GankBean;
import me.wally.gankio.controller.ImageBrowserViewController;
import me.wally.gankio.ui.activity.ArticleDetailActivity;
import me.wally.gankio.ui.activity.MeiziBrowserActivity;

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

    /**
     * 跳转到文章详情
     */
    public static void routerArticleDetail(GankBean.ResultsBean articleBean) {
        ARouter
                .getInstance()
                .build(UIRouterPath.ARTICLE_DETAIL)
                .withSerializable(ArticleDetailActivity.KEY_DATA_LIST, articleBean)
                .navigation();
    }

    /**
     * 跳转到图片详情
     */
    public static void routerMeiziDetail(ArrayList<GankBean.ResultsBean> meziBeans, int browserIndex) {
        ARouter
                .getInstance()
                .build(UIRouterPath.MEIZI_DETAIL)
                .withSerializable(MeiziBrowserActivity.KEY_MEI_ZI_BEANS, meziBeans)
                .withInt(ImageBrowserViewController.KEY_IMAGE_BROWSER_CURRENT_INDEX, browserIndex)
                .navigation();
    }
}