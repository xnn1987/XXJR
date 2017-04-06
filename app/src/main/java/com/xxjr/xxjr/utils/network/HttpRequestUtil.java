package com.xxjr.xxjr.utils.network;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.TextUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwf on 2016/1/12.
 */
public class HttpRequestUtil {

    public  Map<String,String> params = new HashMap<>();
    private Activity activity;

    public HttpRequestUtil(Activity activity) {
        this.activity = activity;
    }

    public HttpRequestUtil() {
    }

    public  void jsonObjectRequestPostSuccess(String url, Response.Listener<JSONObject> listener,
                                              Response.ErrorListener errorListener){
        params.put("UUID", MyApplication.device);
        params.put("uid", MyApplication.uid);
        DebugLog.e("uid==",MyApplication.uid+"-- UUID=="+MyApplication.device);
        DebugLog.e("seeco== 旧版本", TextUtil.getTextToString(MyApplication.cookies));
        final String mRequestBody = appendParameter(Urls.RANQING + url,params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.RANQING+ url,null,listener ,errorListener){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
            }
            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeaders=new HashMap<String, String>();
                mHeaders.put("Cookie", MyApplication.cookies);
                return mHeaders;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Response<JSONObject> superRespomse = super.parseNetworkResponse(response);
                Map<String,String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                if(!TextUtils.isEmpty(rawCookies)){
                    MyApplication.cookies = rawCookies.substring(0, rawCookies.indexOf(";"));
                }
                return superRespomse;
            }

        };
        MyApplication.mQueue.add(jsonObjectRequest);
    }

    private  String appendParameter(String url,Map<String,String> params){
        Uri uri = Uri.parse(url);
        Uri.Builder builder = uri.buildUpon();
        for(Map.Entry<String,String> entry:params.entrySet()){
            builder.appendQueryParameter(entry.getKey(),entry.getValue());
        }
        return builder.build().getQuery();
    }


}
