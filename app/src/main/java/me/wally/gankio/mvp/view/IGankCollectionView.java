package me.wally.gankio.mvp.view;

import java.util.List;

import me.wally.gankio.db.model.vo.GankCollectionVO;
import me.wally.gankio.mvp.base.IView;

/**
 * Package: me.wally.gankio.mvp.view
 * FileName: IGankCollectionView
 * Date: on 2018/11/1  下午12:07
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IGankCollectionView extends IView {
    /**
     * add操作
     */
    interface IAddCollectionView extends IView {
        void onAddCollectionSuccess();

        /**
         * Fail类型的回调，业务层面拦截的错误
         *
         * @param message 错误原有
         */
        void onAddCollectionFail(String message);

        /**
         * Error类型的回调，抛出异常的错误
         *
         * @param error 异常对象
         */
        void onAddCollectionError(Throwable error);
    }

    /**
     * remove操作
     */
    interface IRemoveCollectionView extends IView {
        void onRemoveCollectionSuccess();

        void onRemoveCollectionFail(String message);

        void onRemoveCollectionError(Throwable error);
    }

    /**
     * get操作
     */
    interface IObtainCollectionView extends IView {
        void onGetCollectionListSuccess(List<GankCollectionVO> gankCollectionVOS);

        void onGetCollectionListFail(String message);

        void onGetCollectionListError(Throwable error);
    }

    /**
     * 检查是否收藏操作
     */
    interface ICheckCollectionView extends IView {
        void onCheckIsCollectionCallback(boolean isCollection);

        void onCheckIsCollectionError(Throwable error);
    }
}