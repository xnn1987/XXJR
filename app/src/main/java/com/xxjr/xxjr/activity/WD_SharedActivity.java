package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.other.Cache.ImageFileCache;
import com.xxjr.xxjr.other.Cache.ImageGetFromHttp;
import com.xxjr.xxjr.other.Cache.ImageMemoryCache;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.Shared.Shared;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WD_SharedActivity extends CommomAcitivity {
    private ImageFileCache fileCache;
    private ImageMemoryCache memoryCache;
    public static List<Map<String,Object>> bitmapList =new ArrayList<>();
    private ProgressWebView webView;
    List<Map<String,Object>> mModuleList = new ArrayList<>();
    private LinearLayout mLlWdModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd__shared);
        SetTitleBar.setTitleText(WD_SharedActivity.this,"我的微店","更换模板");
        initviewAndListener();
        downLoadModule(Urls.WD_MODULE);
        setListener();
    }

    private void initviewAndListener() {
        mLlWdModule = (LinearLayout) findViewById(R.id.threetitleNoStatusNar_rl_title);
        webView = (ProgressWebView) findViewById(R.id.wdshared_wv_wdshared);
        webView.loadUrl(Urls.RANQING + Urls.WD_SHARED);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);  //原网页基础上缩放
        webView.getSettings().setUseWideViewPort(true);      //任意比例缩放
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }



    @Override
    public void onBackPressed() {

        onMyBackPressed(webView);
    }

    private void onMyBackPressed(WebView webView){
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }
    private void setListener() {
        //  更换模板        threetitle_rl_title
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapList.size() !=0) {
                    Intent intent = new Intent(getApplicationContext(), WD_ChangeModulActivity.class);
                    WD_SharedActivity.this.startActivityForResult(intent, 2);
                }else {
                    Toast.makeText(WD_SharedActivity.this, "网络不太稳定", Toast.LENGTH_SHORT).show();
                    downLoadModule(Urls.WD_MODULE);
                }
            }
        });
    }


    private void downLoadModule(String url){
        AppUtil.showProgress(WD_SharedActivity.this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("tempId", MyApplication.tempId);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        List<Map<String,Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        Map<String,Object> attrMap = (Map<String, Object>) map.get("attr");
                        String origTempId = attrMap.get("origTempId").toString();
                        mModuleList = rowsList;
                        setColorTitle(origTempId,mModuleList);
                        cacheImage();
                        AppUtil.dismissProgress();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }

    private void setColorTitle(String origTempId,List<Map<String,Object>> rowsList){
        for (int i = 0 ; i<rowsList.size() ; i++){
            if (origTempId.equals(rowsList.get(i).get("tempId").toString())){
                mLlWdModule.setBackgroundColor(Color.parseColor(rowsList.get(i).get("btnColor").toString()));
            }
        }

    }

    private void cacheImage(){
        bitmapList.clear();
        fileCache=new ImageFileCache();
        memoryCache=new ImageMemoryCache(this);
        for (int i=0; i<mModuleList.size(); i++){
            final String url =Urls.RANQING+ mModuleList.get(i).get("showUrl").toString();
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap result = memoryCache.getBitmapFromCache(url);
                    if (result == null) {
                        // 文件缓存中获取
                        result = fileCache.getImage(url);
                        if (result == null) {
                            // 从网络获取
                            result = ImageGetFromHttp.downloadBitmap(url);
                            if (result != null) {
                                fileCache.saveBitmap(result, url);
                                memoryCache.addBitmapToCache(url, result);
                            }
                        } else {
                            // 添加到内存缓存
                            memoryCache.addBitmapToCache(url, result);
                        }
                    }
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("bitmap",result);
                    map.put("btnColor",mModuleList.get(finalI).get("btnColor").toString());
                    map.put("tempId",mModuleList.get(finalI).get("tempId").toString());
                    bitmapList.add(map);
                }
            }).start();
        }
    }

    //  分享
    public void onClickSharedWd(View view){
         Shared.sharedWeiDian(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&& resultCode==RESULT_OK && data != null){
            MyApplication.tempId = data.getStringExtra("tempId");
            initviewAndListener();
            downLoadModule(Urls.WD_MODULE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i> bitmapList.size(); i++){
            ((Bitmap)bitmapList.get(i).get("bitmap")).recycle();
        }
        bitmapList.clear();;
        mModuleList.clear();
    }

}
