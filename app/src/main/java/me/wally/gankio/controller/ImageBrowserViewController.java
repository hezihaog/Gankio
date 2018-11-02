package me.wally.gankio.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;
import me.wally.gankio.R;
import me.wally.gankio.UIApplication;
import me.wally.gankio.widget.SlidingViewPager;

/**
 * Package: me.wally.gankio.controller
 * FileName: ImageBrowserViewController
 * Date: on 2018/11/2  下午3:16
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ImageBrowserViewController extends BaseUIViewController {
    public static final String KEY_IMAGE_URLS = "key_image_urls";

    @BindView(R.id.photo_view_pager)
    SlidingViewPager mPhotoViewPager;

    private ArrayList<String> targetUrlList;
    private OnPhotoTapCallback mCallback;

    public static ImageBrowserViewController newInstance(ArrayList<String> urlList) {
        ImageBrowserViewController controller = new ImageBrowserViewController();
        Bundle args = new Bundle();
        args.putSerializable(KEY_IMAGE_URLS, urlList);
        controller.setProps(args);
        return controller;
    }

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        targetUrlList = (ArrayList<String>) getProps().getSerializable(KEY_IMAGE_URLS);
    }

    @Override
    public int onLayoutId() {
        return R.layout.page_image_browser;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        final OnPhotoTapListener photoTapAgent = new OnPhotoTapListener() {

            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (mCallback != null) {
                    mCallback.onPhotoTap(view, x, y);
                }
            }
        };
        mPhotoViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return targetUrlList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View contentView = LayoutInflater.from(getContext()).inflate(R.layout.view_image_browser_content, container, false);
                PhotoView photoView = contentView.findViewById(R.id.content_iv);
                Glide
                        .with(getContext())
                        .load(targetUrlList.get(position))
                        .into(photoView);
                container.addView(contentView);
                photoView.setOnPhotoTapListener(photoTapAgent);
                return contentView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_image_browser_title);
    }

    public interface OnPhotoTapCallback {
        void onPhotoTap(ImageView view, float x, float y);
    }

    public void setOnPhotoTapCallback(OnPhotoTapCallback callback) {
        this.mCallback = callback;
    }
}
