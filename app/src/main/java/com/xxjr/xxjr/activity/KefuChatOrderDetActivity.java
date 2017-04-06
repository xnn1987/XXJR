package com.xxjr.xxjr.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import java.io.File;
import java.io.IOException;

public class KefuChatOrderDetActivity extends SlidBackActivity {
    private ProgressWebView webView;
    private Object intentDatas;
    private String name;
    private String applyId;

    private ValueCallback<Uri> mUploadMessage;
    private static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu_chat_order_det);
        getIntentDatas();
        initWebView();
        SetTitleBar.setTitleText(KefuChatOrderDetActivity.this,name+"-聊天");

    }

    private void initWebView() {
        webView = (ProgressWebView) findViewById(R.id.kefuchat_wv_chat);
        webView.loadUrl(Urls.RANQING + Urls.ORDER_DET_CHAT+"?uid="+ MyApplication.uid+"&applyId="+applyId);
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

        webView.setWebChromeClient(
                new WebChromeClient() {

                    //扩展支持alert事件
                    @Override
                    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("小小金融客服提示").setMessage(message).setPositiveButton("确定", null);
                        builder.setCancelable(false);
                        builder.setIcon(R.mipmap.xxjr_logo);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        result.confirm();
                        return true;
                    }

                    //扩展浏览器上传文件
                    //3.0++版本
                    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                        openFileChooserImpl(uploadMsg);
                    }

                    //3.0--版本
                    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                        openFileChooserImpl(uploadMsg);
                    }

                    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                        openFileChooserImpl(uploadMsg);
                    }

                    // For Android > 5.0
                    public boolean onShowFileChooser (WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
                        openFileChooserImplForAndroid5(uploadMsg);
                        return true;
                    }
                }
        );
    }
    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null: intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5){
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null: intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }


    public void getIntentDatas() {
        Intent intent = getIntent();
        applyId = intent.getStringExtra("applyId");
        name = intent.getStringExtra("name");
    }
}
