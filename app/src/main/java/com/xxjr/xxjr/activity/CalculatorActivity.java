package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class CalculatorActivity extends SlidBackActivity {

    private ProgressWebView webView;
    private LinearLayout mLlTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initWebView();
        setTitleBack();

    }

    public void setTitleBack() {
        mLlTitle = (LinearLayout) findViewById(R.id.onlytitle_ll_main);
        TextView mTvTitle = (TextView) findViewById(R.id.titleBar_tv_title);
        mTvTitle.setText("计算器");
        mLlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyBackPressed(webView);
            }
        });
    }

    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.calculator_wv_calculator);
//        webView.loadUrl("http://192.168.31.209/cpQuery/app/calc/calcTool");
        webView.loadUrl(Urls.RANQING + Urls.CALCULATOR);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);  //原网页基础上缩放
        webView.getSettings().setUseWideViewPort(true);      //任意比例缩放

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                mLlTitle.setVisibility(View.GONE);
                view.loadUrl(url);
                return true;
            }

        });
    }


    @Override
    public void onBackPressed() {

        onMyBackPressed(webView);
    }

    private void onMyBackPressed(WebView webView) {
        if (webView.canGoBack()) {
            webView.goBack();
           /* if (!webView.canGoBack()){
                mLlTitle.setVisibility(View.VISIBLE);
            }*/
        } else {
            finish();
        }
    }
}
