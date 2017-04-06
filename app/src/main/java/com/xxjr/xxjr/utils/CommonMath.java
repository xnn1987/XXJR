package com.xxjr.xxjr.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.xxjr.xxjr.activity.LoginActivity;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.zhy.http.okhttp.request.RequestCall;

import org.ddq.common.util.JsonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 */
public class CommonMath {

    /**
     * 判断有没有登录
     *
     * @param activity
     * @return
     */
    public static boolean isLogin(Activity activity) {
        if (MyApplication.uid == null || TextUtils.isEmpty(MyApplication.uid) || MyApplication.userInfo == null) {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            return false;
        } else {
            return true;
        }
    }



    /**
     * 跳转  制作趣图的intent  activity
     *
     * @param activity
     * @param id
     * @param title
     * @param catgory     制作图片的类型
     * @param isCollected 是否收藏
     */
    /*private static void intentPicSeriesActivity(Activity activity, String id, String title, String catgory, boolean isCollected) {
        Intent intent = new Intent(activity, PictureSeriesActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("catgory", catgory);
        intent.putExtra("isCollected", isCollected);
        activity.startActivity(intent);
    }*/

    /**
     * 获取用户信息  并且跳转到趣图
     *
     * @param activity
     */
    /*public static void downloadInfu(final Activity activity, final String id,
                                    final String title, final String catgory, final boolean isCollected) {
        RequestCall requestCall = MyOkHttpUtils.postRequest(Urls.CARD_SET_QUERY, null);
        requestCall.execute(new MapCallback(activity) {
            @Override
            public void onResponse(Map responseMap) {
                super.onResponse(responseMap);
                if (responseMap != null) {
                    Map<String, Object> attrMap = (Map<String, Object>) responseMap.get("attr");
                    MyApplication.userInfo = attrMap;
                    intentPicSeriesActivity(activity, id, title, catgory, isCollected);
                }
            }
        });
    }*/

    /**
     * 获取用户  userinfo  不进行任何处理
     * @param activity
     */
   /* public static void getUserInfo(final Activity activity) {
        RequestCall requestCall = MyOkHttpUtils.postRequest(Urls.CARD_SET_QUERY, null);
        requestCall.execute(new MapCallback(activity, false) {
            @Override
            public void onResponse(Map responseMap) {
                super.onResponse(responseMap);
                if (responseMap != null) {
                    Map<String, Object> attrMap = (Map<String, Object>) responseMap.get("attr");
                    MyApplication.userInfo = attrMap;
                    spPutMainCache(activity.getApplicationContext(), attrMap, ConstantUtils.SP_USER_INFO);
                }
            }
        });
    }*/

    /**
     * 有没有收藏的一个判断
     *
     * @param imageView
     * @param isCollected 是否收藏
     */
   /* public static void shouCangJ(ImageView imageView, boolean isCollected) {
        if (isCollected) {
            imageView.setImageResource(R.mipmap.shoucang);
        } else {
            imageView.setImageResource(R.mipmap.shouc);
        }
    }*/

    /**
     * 对控件的  绝对测量
     *
     * @param view
     */
    public static void measureView(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heigh = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, heigh);
    }

    /**
     * 将bitmap转成png格式的图片并且保存
     *
     * @param activity
     * @param bitmap
     * @param picName
     */
    public static void saveBitmap(Activity activity, Bitmap bitmap, String picName) throws Exception {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File directore = new File(Environment.getExternalStorageDirectory() + "/qutu");
            if (!directore.exists()) {
                directore.mkdirs();
            }
            File file = new File(directore, picName);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } else {
            DebugLog.Toast(activity, "请插入SD卡");
            throw new Exception("没有SD卡");
        }
    }


    /**
     * 离线缓存
     *
     * @param map
     * @param nameStr
     */
    public static void spPutMainCache(Context context, Map<String, Object> map, String nameStr) {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(context, ConstantUtils.SP_MAIN_CACHE);
        String cacheStr = JsonUtil.getInstance().object2JSON(map);
        sharedPrefUtil.putString(nameStr, cacheStr);
        sharedPrefUtil.commit();
    }

    /**
     * 获取  离线缓存
     *
     * @param context
     * @param nameStr
     * @return
     */
    public static Map<String, Object> spGetMainCache(Context context, String nameStr) {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(context, ConstantUtils.SP_MAIN_CACHE);
        String string = sharedPrefUtil.getString(nameStr, null);
        Map<String, Object> map = null;
        if (string != null) {
            map = JsonUtil.getInstance().json2Object(string, Map.class);
        } else {
            sharedPrefUtil.remove(ConstantUtils.SP_MAIN_CACHE);
            sharedPrefUtil.commit();
        }
        return map;
    }
}
