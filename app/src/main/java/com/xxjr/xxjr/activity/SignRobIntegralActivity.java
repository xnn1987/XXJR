package com.xxjr.xxjr.activity;

import android.support.v7.app.AppCompatActivity;
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
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class SignRobIntegralActivity extends SlidBackActivity {

    private ProgressWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_rob_integral);
        initWebView();
        setTitleBack();

    }


    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.Integral_wv_count);
        webView.loadUrl(Urls.RANQING + Urls.SIGN_INTEGRAL+"?uid="+ MyApplication.uid+"&UUID="+MyApplication.device);
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

    public void setTitleBack() {
        LinearLayout mLlBack = (LinearLayout) findViewById(R.id.titleBar_ll_back);
        TextView mTvTitle = (TextView) findViewById(R.id.titleBar_tv_title);
        mTvTitle.setText("积分详情");
        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyBackPressed(webView);
            }
        });
    }
}
