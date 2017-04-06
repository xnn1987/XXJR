package com.xxjr.xxjr.application;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.Md5Utils;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.map.LocationService;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/3/25.
 */
public class MyApplication extends Application{
    public static boolean haveWd ;
    public static boolean isChangeWDCardActivity  = false;
    public static String tempId ;
    public static String uid ;
    public static String device  ;
    public static String cookies  = "";
    public static String city = "深圳";
    public static String cityCode="4403";
    public static float density;//像素密度
    public static Map<String, Object> userInfo = null;
    public static Map<String, Object> userQuanJu = null;
    public static RequestQueue mQueue;
    public static DisplayImageOptions options;
    public static DisplayImageOptions optionsHead;
    public static DisplayImageOptions optionsBaner;//banner
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    public static int screenWidth;
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        getDPI();//  获取像素密度
        threeSDK();//第三方登录
        getDevidAndUid();
        initImageLoad();
        initImageLoadHead();

        mapInit();

        mQueue = Volley.newRequestQueue(this);//volley初始化

        com.umeng.socialize.utils.Log.LOG = true;//LogCat中观察友盟日志
    }
    //  百度地图初始化
    private void mapInit() {
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(this);
    }

    /**
     * 第三方登录
     */
    private void threeSDK() {
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public void getDevidAndUid() {
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        device = Md5Utils.string2MD5(tm.getDeviceId());//获取设备UUID
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext(), ConstantUtils.SP_USER_NAME);
        uid = sharedPrefUtil.getString(ConstantUtils.SP_USER_UID, null);

    }
    //  初始化信息
    private void initImageLoad() {
        initImageLoader(getApplicationContext());
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.chenggong01)// 加载等待 时显示的图片
                .showImageForEmptyUri(R.mipmap.chenggong01)// 加载数据为空时显示的图片
                .showImageOnFail(R.mipmap.shibai01)// 加载失败时显示的图片
                .cacheInMemory().cacheOnDisc() /**
                 * .displayer(new
                 * RoundedBitmapDisplayer(20))
                 **/
                .build();
    }
    //  初始化  头像
    private void initImageLoadHead() {
        initImageLoader(getApplicationContext());
        optionsHead = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.touxiang)// 加载等待 时显示的图片
                .showImageForEmptyUri(R.mipmap.touxiang)// 加载数据为空时显示的图片
                .showImageOnFail(R.mipmap.touxiang)// 加载失败时显示的图片
                .cacheInMemory().cacheOnDisc() /**
                 * .displayer(new
                 * RoundedBitmapDisplayer(20))
                 **/
                .build();
    }

    //  初始化  baner
    private void initImageLoadBanner() {
        initImageLoader(getApplicationContext());
        optionsBaner = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.banerjiazaizhong)// 加载等待 时显示的图片
                .showImageForEmptyUri(R.mipmap.banerjiazaizhong)// 加载数据为空时显示的图片
                .showImageOnFail(R.mipmap.bannershibai)// 加载失败时显示的图片
                .cacheInMemory().cacheOnDisc() /**
                 * .displayer(new
                 * RoundedBitmapDisplayer(20))
                 **/
                .build();
    }


    //  初始化imageload
    public  void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
//				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // in
                        // common
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        // imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    private void getDPI(){
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
         density  = dm.density;
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
         screenWidth = wm.getDefaultDisplay().getWidth();

    }


}
