package me.wally.gankio.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
    public static final String KEY_IMAGE_BROWSER_CURRENT_INDEX = "key_image_browser_current_index";

    @BindView(R.id.photo_view_pager)
    SlidingViewPager mPhotoViewPager;

    private OnPhotoTapCallback mPhotoTapCallback;
    private ArrayList<String> targetUrlList;
    private int mBrowserCurrentIndex;
    private OnPhotoPageChangeCallback mPhotoPageChangeCallback;

    public static ImageBrowserViewController newInstance(ArrayList<String> urlList, int browserCurrentIndex) {
        ImageBrowserViewController controller = new ImageBrowserViewController();
        Bundle args = new Bundle();
        args.putSerializable(KEY_IMAGE_URLS, urlList);
        args.putInt(KEY_IMAGE_BROWSER_CURRENT_INDEX, browserCurrentIndex);
        controller.setArguments(args);
        return controller;
    }

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        targetUrlList = (ArrayList<String>) getArguments().getSerializable(KEY_IMAGE_URLS);
        mBrowserCurrentIndex = (int) getArguments().getSerializable(KEY_IMAGE_BROWSER_CURRENT_INDEX);
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
                if (mPhotoTapCallback != null) {
                    mPhotoTapCallback.onPhotoTap(view, x, y);
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
        mPhotoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mPhotoPageChangeCallback != null) {
                    mPhotoPageChangeCallback.onPhotoPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mPhotoPageChangeCallback != null) {
                    mPhotoPageChangeCallback.onPhotoPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mPhotoPageChangeCallback != null) {
                    mPhotoPageChangeCallback.onPhotoPageScrollStateChanged(state);
                }
            }
        });
        mPhotoViewPager.setCurrentItem(mBrowserCurrentIndex);
    }

    public String getCurrentBrowserImageUrl() {
        int currentItemPosition = getCurrentBrowserIndex();
        return targetUrlList.get(currentItemPosition);
    }

    public int getCurrentBrowserIndex() {
        return mPhotoViewPager.getCurrentItem();
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_image_browser_title);
    }

    public interface OnPhotoTapCallback {
        void onPhotoTap(ImageView view, float x, float y);
    }

    public interface OnPhotoPageChangeCallback {
        void onPhotoPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPhotoPageSelected(int position);

        void onPhotoPageScrollStateChanged(int state);
    }

    public static class SimplePhotoPageChangeCallback implements OnPhotoPageChangeCallback {

        @Override
        public void onPhotoPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPhotoPageSelected(int position) {

        }

        @Override
        public void onPhotoPageScrollStateChanged(int state) {

        }
    }

    public void setOnPhotoTapCallback(OnPhotoTapCallback callback) {
        this.mPhotoTapCallback = callback;
    }

    public void setOnPhotoPageChangeCallback(OnPhotoPageChangeCallback callback) {
        this.mPhotoPageChangeCallback = callback;
    }
}
