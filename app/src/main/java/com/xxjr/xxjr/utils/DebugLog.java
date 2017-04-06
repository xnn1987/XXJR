package com.xxjr.xxjr.utils;

import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/26.
 */
public class DebugLog {

    public static void e(String name,String log){
            Log.e(name,log);
    }
    public static void Toast(Activity activity,String content){
        Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();

    }
}
