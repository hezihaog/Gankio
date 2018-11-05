package me.wally.gankio.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Package: me.wally.gankio.util
 * FileName: ImageDownloadUtil
 * Date: on 2018/11/5  下午12:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ImageDownloadUtil {
    private ImageDownloadUtil() {
    }

    public static Observable<String> saveImageObservable(final Context context, final String imageUrl, final String savePath, final String fileName) {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                try {
                    Bitmap bitmap = Glide.with(context).asBitmap().load(imageUrl).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    if (bitmap == null) {
                        emitter.onError(new Exception("无法下载图片，请重试"));
                    }
                    emitter.onNext(bitmap);
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).flatMap(new Function<Bitmap, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Bitmap bitmap) {
                File savePathDir = new File(savePath);
                if (!savePathDir.exists()) {
                    savePathDir.mkdir();
                }
                String name = fileName + ".jpg";
                File file = new File(savePathDir, name);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    boolean compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return Observable.just(file.getAbsolutePath());
            }
        });
    }

    public static void notifyImageSaveGallery(Context context, String imagePath) {
        //更新图库
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(imagePath)));
        context.getApplicationContext().sendBroadcast(scannerIntent);
    }
}