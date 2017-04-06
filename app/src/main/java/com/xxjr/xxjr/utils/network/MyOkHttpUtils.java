package com.xxjr.xxjr.utils.network;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.TextUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;


/**
 * Created by Administrator on 2016/5/13.
 */
public class MyOkHttpUtils {

    public static RequestCall postRequest(String url, Map<String, String> params) {
        RequestCall build = OkHttpUtils.post()
                .url(Urls.RANQING+url)
                .params(params)
                .addHeader("Cookie",MyApplication.cookies)
                .addParams("uid", TextUtil.getTextToString(MyApplication.uid))
                .addParams("UUID",  TextUtil.getTextToString(MyApplication.device))
                .build();
       return build;
    }

    /**
     * 文件上传
     * @param activity
     * @param fileUrl
     * @param uploadUrl
     * @return RequestCall  你在进行excute
     */
    public static RequestCall postFile(Activity activity, String fileUrl,String uploadUrl)
    {
        File file = new File(fileUrl);
        if (!file.exists())
        {
            Toast.makeText(activity, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return null;
        }
        RequestCall build = OkHttpUtils
                .postFile()
                .url(uploadUrl)
                .file(file)
                .build();

        return build;
    }
}
