package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class MyRecriutActivity extends SlidBackActivity {
    private ProgressWebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recriut);
        initWebView();
        setTitleBack();
    }

    public void setTitleBack() {
        LinearLayout mLlBack = (LinearLayout) findViewById(R.id.titleBar_ll_back);
        TextView mTvTitle = (TextView) findViewById(R.id.titleBar_tv_title);
        mTvTitle.setText("我的收徒");
        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyBackPressed(webView);
            }
        });
    }

    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.MyRecruit_wv_MyRecruit);
        webView.loadUrl(Urls.RANQING + Urls.MY_RECTUIT + "?UUID=" + MyApplication.device + "&uid=" + MyApplication.uid);

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
