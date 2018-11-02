package me.wally.gankio.controller.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by danney on 16/1/18.
 */
public abstract class UIViewControllerActivity extends AppCompatActivity {
    private UIViewControllerManager mManager;

    public UIViewControllerManager getViewControllerManager() {
        if (mManager == null) {
            mManager = new UIViewControllerManager();
            mManager.addHost(new UIViewControllerManager.ViewControllerHostCallback() {
                @Override
                public View findViewById(int viewId) {
                    return getWindow().findViewById(viewId);
                }

                @Override
                public Context getContext() {
                    return UIViewControllerActivity.this;
                }

                @Override
                public Bundle getProps() {
                    return getIntent().getBundleExtra("props");
                }
            });
        }
        return mManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewControllerManager().dispatchCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mManager != null) {
            mManager.dispatchStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mManager != null) {
            mManager.dispatchResume();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mManager != null) {
            mManager.dispatchNewIntent(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mManager != null) {
            mManager.dispatchPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mManager != null) {
            mManager.dispatchStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            mManager.dispatchDestroy();
            mManager = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
        if (mManager != null) {
            mManager.dispatchSaveInstanceState(outState, outPersistentState);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mManager != null) {
            mManager.dispatchRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mManager != null) {
            mManager.dispatchActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mManager != null) {
            mManager.dispatchConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (mManager != null) {
            if (!mManager.dispatchBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mManager != null) {
            mManager.dispatchRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}