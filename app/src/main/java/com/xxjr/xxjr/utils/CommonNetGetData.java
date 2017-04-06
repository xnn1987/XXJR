package com.xxjr.xxjr.utils;

import android.app.Activity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/22.
 */
public class CommonNetGetData {

    /**
     * 获取全局信息
     */
    public void getQuanJuData(final Activity activity) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(Urls.QUANJU,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) responseMap.get("success");
                        if (success) {
                            Map<String, Object> userMap = (Map<String, Object>) responseMap.get("attr");
                            MyApplication.userQuanJu = (Map<String, Object>) userMap.get("userInfo");

                            String userQuanJu = JsonUtil.getInstance().object2JSON(MyApplication.userQuanJu);
                            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(activity,ConstantUtils.SP_USER_NAME);
                            sharedPrefUtil.putString(ConstantUtils.SP_USER_QUANJU,userQuanJu);
                            sharedPrefUtil.commit();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfoData(final Activity activity) {
        AppUtil.showProgress(activity,"请稍后");
        final SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(activity, ConstantUtils.SP_USER_NAME);
        MyApplication.uid = sharedPrefUtil.getString(ConstantUtils.SP_USER_UID, null);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(Urls.CUST_INFO,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            DebugLog.e("userinfo",response.toString());
                            Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                            Map<String,Object> attrMap = (Map<String, Object>) map.get("attr");
                            List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("rows");

                            if ((boolean)map.get("success")) {
                                if (infoList != null && infoList.size() > 0) {
                                    MyApplication.haveWd = (boolean) attrMap.get("haveWd");
                                    Map<String, Object> infoMap = infoList.get(0);
                                    MyApplication.userInfo = infoMap;
                                    String userInfo = JsonUtil.getInstance().object2JSON(infoMap);
                                    sharedPrefUtil.putString(ConstantUtils.SP_USER_INFO,userInfo);
                                    sharedPrefUtil.putString(ConstantUtils.SP_USER_UID,MyApplication.uid);
                                    sharedPrefUtil.putBoolean(ConstantUtils.SP_USER_HAVEWD,MyApplication.haveWd);
                                    sharedPrefUtil.commit();
                                }
                            }else {
                                if (map.get("errorCode")!=null ) {
                                    if (map.get("errorCode").toString().equals("99") ) {
                                        MyApplication.uid = null;
                                        MyApplication.userInfo = null;
                                        MyApplication.userQuanJu = null;
                                        return;
                                    }
                                }
                                DebugLog.Toast(activity,map.get("message").toString());
                            }
                            AppUtil.dismissProgress();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            DebugLog.Toast(activity,ConstantUtils.DIALOG_ERROR);
                        }
                    }
            );
    }
}
