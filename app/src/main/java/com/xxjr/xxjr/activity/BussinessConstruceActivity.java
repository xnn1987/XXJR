package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.utils.Shared.SocialUmengShare;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class BussinessConstruceActivity extends SlidBackActivity {

    private ProgressWebView webView;
    private String zxTag;
    private String novelId;
    private String title = "";
    private String smallImg;
    private String sharedTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_construce);
        getIntentDatas();
        setTitleBack();
        initWebView();

    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        zxTag = intent.getStringExtra("zxTag");
        novelId = intent.getStringExtra("novelId");
        smallImg = intent.getStringExtra("smallImg");
        title = intent.getStringExtra("title");
        sharedTitle = title;
        if (title.length()>7){
            title = title.substring(0,6)+"...";
        }
    }

    public void setTitleBack() {
        ImageView mIvBack = (ImageView) findViewById(R.id.ThreeTitleBar_iv_back);
        TextView mTvTitle = (TextView) findViewById(R.id.ThreeTitleBar_tv_title);
        TextView mTvSave = (TextView) findViewById(R.id.ThreeTitleBar_tv_click);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyBackPressed(webView);
            }
        });
        mTvTitle.setText(title);
        mTvSave.setText("分享");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SocialUmengShare(BussinessConstruceActivity.this,getResources().getString(R.string.app_name),sharedTitle,
                        Urls.SHARED_CONSULT + "?novelId=" + novelId + "&zxTag=" + zxTag,smallImg)
                        .showPop(findViewById(R.id.construce_ll_main));

            }
        });
    }


    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.bussinessConstruce_wv_calculator);
        webView.loadUrl(Urls.RANQING + Urls.BUSSINESS_CONSTRUCE+"?zxTag="+zxTag+"&novelId="+novelId);
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

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            onMyBackPressed(webView);
        }
        return super.onKeyDown(keyCode, event);
    }*/

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


}
