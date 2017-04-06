package com.xxjr.xxjr.activity.zx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class ZxWebviewActivity extends SlidBackActivity {
    private ProgressWebView webView;
    private LinearLayout mLlTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zx_webview);
        initWebView();
//        setTitleBack();

    }

   /* public void setTitleBack() {
        mLlTitle = (LinearLayout) findViewById(R.id.onlytitle_ll_main);
        TextView mTvTitle = (TextView) findViewById(R.id.titleBar_tv_title);
        mTvTitle.setText("个人征信");
        mLlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyBackPressed(webView);
            }
        });
    }*/

    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.ZX_webvview);
//        webView.loadUrl("http://192.168.31.209/cpQuery/app/calc/calcTool");
        webView.loadUrl(Urls.RANQING + Urls.ZX_PERSON+"?uid="+ MyApplication.uid+"&UUID="+MyApplication.device);
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
