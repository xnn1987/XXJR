package com.xxjr.xxjr.utils.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.zhy.http.okhttp.callback.Callback;


import org.ddq.common.util.JsonUtil;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class MapCallback extends Callback<Map> {
    private Activity activity;
    private String dialogContent;
    private boolean isDialog = true;
    private String errorMsg = ConstantUtils.DIALOG_ERROR;

    @Override
    public void onResponse(Map rMap) {
        if (rMap==null  &&  !errorMsg.equals(ConstantUtils.DIALOG_ERROR)){
            DebugLog.Toast(activity,errorMsg);
            AppUtil.dismissProgress();
        }
    }

    @Override
    public Map parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
        Map map = JsonUtil.getInstance().json2Object(string, Map.class);
        // 未登录
        if (map.get("errorCode") != null && Integer.parseInt(map.get("errorCode").toString()) == 99) {
            SharedPrefUtil sp = new SharedPrefUtil(activity, ConstantUtils.SP_USER_NAME);
            sp.clear();
            MyApplication.uid = null;
            MyApplication.userInfo = null;
            map = null;
        }

        if (map != null && !(boolean) map.get("success")) {
            errorMsg = TextUtil.getTextToString(map.get("message"));
            map = null;
        }
        DebugLog.e("response--> ", string);
        return map;
    }

    public MapCallback(Activity activity) {
        super();
        this.activity = activity;
        this.dialogContent = ConstantUtils.DIALOG_SHOW;
    }

    /**
     * @param activity
     * @param isDialog 默认要，
     */
    public MapCallback(Activity activity, boolean isDialog) {
        super();
        this.activity = activity;
        this.isDialog = isDialog;
        this.dialogContent = ConstantUtils.DIALOG_SHOW;
    }

    public MapCallback(Activity activity, String dialogContent) {
        super();
        this.activity = activity;
        this.dialogContent = dialogContent;
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
        if (isDialog)
            AppUtil.showProgress(activity, dialogContent);
    }

    @Override
    public void onAfter() {
        super.onAfter();
        AppUtil.dismissProgress();
    }

    @Override
    public void onError(Call call, Exception e) {
            DebugLog.Toast(activity, errorMsg);
    }

    @Override
    public void inProgress(float progress) {
        super.inProgress(progress);
    }

    public void setDialogContent(String dialogContent) {
        this.dialogContent = dialogContent;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
