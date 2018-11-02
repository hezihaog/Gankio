package me.wally.gankio.controller.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by danney on 16/1/18.
 */
public class UIViewControllerManager {
    private static final int MSG_ADD = 0;
    private static final int MSG_MOVE_TO_STARTED = 1;
    private int state = UIViewController.INITIALIZING;
    /**
     * ViewControllerMgr的持有者
     */
    private ViewControllerHostCallback host;

    /**
     * 等待被添加的ViewController
     */
    private ArrayList<UIViewController> addingList;

    /**
     * 已经被添加到Mgr的ViewController
     */
    private ArrayList<UIViewController> addedList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ADD: {
                    addSubViewContainers();
                    break;
                }
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public UIViewControllerManager() {
        addedList = new ArrayList<>();
    }

    /**
     * 将一个ViewController添加到管理器中，这样vc才能接受到所有的Activity的生命周期事件
     */
    public void add(int containerViewId, UIViewController vc, String tag, boolean isSync) {
        if (vc == null) {
            throw new RuntimeException("ViewController can not be null");
        }

        if (addingList == null) {
            addingList = new ArrayList<UIViewController>();
        }

        vc.containerViewId = containerViewId;
        vc.tag = tag;
        vc.setContext(host.getContext());
        //配置VC的参数，自身参数加上宿主的
//        Bundle hostProps = host.getProps();
//        Bundle vcProps = vc.getProps();
//        if (hostProps == null) {
//            hostProps = new Bundle();
//        }
//        hostProps.putAll(vcProps);
//        vc.setProps(hostProps);
        //通知onCreate()
        vc.performCreate(null);

        if (isSync) {
            LayoutInflater inflater = LayoutInflater.from(host.getContext());
            addSubViewContainer(inflater, vc);
        } else {
            addingList.add(vc);
            handler.sendEmptyMessage(MSG_ADD);
        }
    }

    public void add(UIViewController vc, String tag, boolean isSync) {
        add(0, vc, tag, isSync);
    }

    public void add(int containerViewId, UIViewController vc) {
        add(containerViewId, vc, null, false);
    }

    /**
     * 添加ViewControllerMgr的持有者callback，用于从ViewControllerMgr中来调用持有者的一些接口
     */
    public void addHost(ViewControllerHostCallback host) {
        this.host = host;
    }

    public UIViewController findViewControllerByTag(String tag) {
        if (tag != null && tag.length() > 0) {
            for (int i = 0; i < addedList.size(); ++i) {
                String vcTag = addedList.get(i).tag;
                if (tag.equals(vcTag)) {
                    return addedList.get(i);
                }
            }
        }
        return null;
    }

    public void dispatchCreate() {
        this.state = UIViewController.CREATED;
    }

    public void dispatchStart() {
        this.state = UIViewController.STARTED;
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performStart();
                }
            }
        }
    }

    public void dispatchResume() {
        this.state = UIViewController.RESUMED;
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performResume();
                }
            }
        }
    }

    public void dispatchNewIntent(Intent intent) {
        //this.state = ViewController.RESUMED;
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performNewIntent(intent);
                }
            }
        }
    }

    public void dispatchPause() {
        this.state = UIViewController.STARTED;
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performPause();
                }
            }
        }
    }

    public void dispatchStop() {
        this.state = UIViewController.STOPPED;
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performStop();
                }
            }
        }
    }

    public void dispatchDestroy() {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performDestroy();
                }
            }
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public void dispatchSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performSaveInstanceState(outState, outPersistentState);
                }
            }
        }
    }

    public void dispatchRestoreInstanceState(Bundle savedInstanceState) {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performRestoreInstanceState(savedInstanceState);
                }
            }
        }
    }

    public void dispatchActivityResult(int requestCode, int resultCode, Intent data) {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performConfigurationChanged(newConfig);
                }
            }
        }
    }

    public void dispatchLowMemory() {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performLowMemory();
                }
            }
        }
    }

    public boolean dispatchBackPressed() {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    if (vc.performBackPressed()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void dispatchRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (addedList != null) {
            for (int i = 0; i < addedList.size(); i++) {
                UIViewController vc = addedList.get(i);
                if (vc != null) {
                    vc.performRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }

    private void addSubViewContainers() {
        LayoutInflater inflater = LayoutInflater.from(host.getContext());
        int size = addingList.size();
        for (int i = 0; i < size; ++i) {
            if (host == null) {
                throw new RuntimeException("there is no host callback in ViewControllerManager");
            }
            UIViewController vc = addingList.get(i);
            addSubViewContainer(inflater, vc);
        }
        addingList.clear();
    }

    private void addSubViewContainer(LayoutInflater inflater, final UIViewController vc) {
        ViewGroup containerView = null;
        if (vc.containerViewId > 0) {
            containerView = (ViewGroup) host.findViewById(vc.containerViewId);
        }

        View view = vc.performCreateView(inflater, containerView, null);
        if (containerView != null) {
            containerView.addView(view);
        }

        addedList.add(vc);

        if (this.state > vc.state) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    vc.performStart();
                }
            });
        }
    }

    /**
     * 外层持有者实现
     */
    public interface ViewControllerHostCallback {
        View findViewById(int viewId);

        Context getContext();

        Bundle getProps();
    }

    public void hideAllViewController() {
        for (UIViewController controller : this.addedList) {
            if (controller.view != null) {
                controller.view.setVisibility(View.GONE);
            }
        }
    }

    public void showHideViewController(int showIndex) {
        hideAllViewController();
        if (this.addedList.get(showIndex).view != null) {
            this.addedList.get(showIndex).view.setVisibility(View.VISIBLE);
        }
    }
}