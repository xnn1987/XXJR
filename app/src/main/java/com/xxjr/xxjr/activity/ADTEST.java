package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class ADTEST extends SlidBackActivity {

    private ProgressWebView webView;
    private String applyId;
    private ValueCallback<Uri> mUploadMessage;
    private static int FILECHOOSER_RESULTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu_chat2);
        getIntentDatas();
        initWebView();
        SetTitleBar.setTitleText(ADTEST.this,"客服聊天");
    }

    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.serviceChat_Pw_chat);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.31.113/cpQuery/app/calc/gfnlEvalCalc");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        /*webView.setWebChromeClient(new WebChromeClient() {
            // For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image*//*");
                KeFuChatActivity2.this.startActivityForResult(
                        Intent.createChooser(i, "File Chooser"),
                        ConstantUtils.CHAT_FILECHOOSER_RESULTCODE);

            }

        });*/

        webView.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                if (mUploadMessage != null) return;
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult( Intent.createChooser( i, "File Chooser" ),
                        ADTEST.FILECHOOSER_RESULTCODE );
            }
            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser( uploadMsg, "" );
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        }
    }



    public void getIntentDatas() {
        Intent intent = getIntent();
        applyId = intent.getStringExtra("applyId");
    }
}
