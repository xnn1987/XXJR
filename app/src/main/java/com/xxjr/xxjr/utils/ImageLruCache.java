package com.xxjr.xxjr.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.xxjr.xxjr.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ImageLruCache {
    private LruCache<String,Bitmap> mMemoryCache;

    public ImageLruCache() {
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public Bitmap loadBitmap(String imagekey) {
        final Bitmap bitmap = getBitmapFromMemCache(imagekey);
        if (bitmap != null) {
            return bitmap;
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(bitmap);
            task.execute();
            return bitmap;
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private  Bitmap bitmap;
        public BitmapWorkerTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        // 在后台加载图片。
        @Override
        protected Bitmap doInBackground(String... params) {
            URL myFileUrl = null;
            try {
                myFileUrl = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
