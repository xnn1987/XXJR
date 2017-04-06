package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class WDCardSharedActivity extends SlidBackActivity {

    private ProgressWebView webView;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdcard_shared);
        SetTitleBar.setTitleText(WDCardSharedActivity.this,"名片分享");
        cardId = getIntent().getStringExtra("cardId");
        initWebView();

    }


    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.wdcardshared_wv_wdcard);
        webView.loadUrl(Urls.RANQING + Urls.WD_CARD_SHARED+"?wdCardFlag=true&fromType=2&cardInfoId="+cardId);
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
}
