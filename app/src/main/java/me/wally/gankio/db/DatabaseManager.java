package me.wally.gankio.db;

import android.content.Context;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import me.wally.gankio.db.model.entity.MyObjectBox;

/**
 * Package: me.wally.gankio.db
 * FileName: DatabaseManager
 * Date: on 2018/11/1  上午11:38
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class DatabaseManager {
    private BoxStore mBoxStore;
    private Context mContext;

    private DatabaseManager() {
    }

    private static final class SingleHolder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager shareInstance() {
        return SingleHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public Context getContext() {
        return mContext;
    }

    public <T> Box<T> getBox(Class<T> entityClass) {
        if (mBoxStore == null) {
            mBoxStore = MyObjectBox
                    .builder()
                    .androidContext(getContext())
                    .build();
        }
        return mBoxStore.boxFor(entityClass);
    }
}