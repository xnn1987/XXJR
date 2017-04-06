package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.CommonMath;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.DialogSaveCommon;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.xxjr.xxjr.utils.network.MyOkHttpUtils;
import com.ypy.eventbus.EventBus;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PictureSeriesActivity extends SlidBackActivity {

    private String id;
    private List<String> mList = new ArrayList<>();
    private String title;
    private LinearLayout mLlPicContain;
    private String catgory;
    private boolean isCollected;
    private String url;
    private DialogSaveCommon dialogSaveCommon;
    private boolean isSingleSave = true;
    private List<ImageView> mIvList = new ArrayList<>();
    private int singleSelectedIndex = 0;
    private DialogSaveCommon.DialogListener dialogListener = new DialogSaveCommon.DialogListener() {
        @Override
        public void addDialogListener(boolean isSure) {
            if (isSure) {
                if (isSingleSave) {
                    try {
                        saveSingleBitmap(singleSelectedIndex);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    saveAllBitmap();
                }
            }
        }
    };
    private Map<String, Object> attrMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_series);
        initViews();
        setDialogInit();
        getIntentDatas();
        downLoadPic();
        SetTitleBar.setTitleText(this, title);
    }

    /**
     * 对话空的初始化以及监听
     */
    private void setDialogInit() {
        dialogSaveCommon = new DialogSaveCommon(ConstantUtils.DIALOG_CONTENT_02);
        dialogSaveCommon.setListener(dialogListener);

    }


    private void initViews() {
        mLlPicContain = (LinearLayout) findViewById(R.id.picSeries_ll_pic);
    }

    //todo  先请求数据
    private void downLoadPic() {
        Map<String, String> map = new HashMap<>();
        map.put("name", TextUtil.getTextToString(attrMap.get("custName")));
        map.put("mobile", TextUtil.getTextToString(attrMap.get("telephone")));
        map.put("company", TextUtil.getTextToString(attrMap.get("company")));
        map.put("email", TextUtil.getTextToString(attrMap.get("email")));
        map.put("job", TextUtil.getTextToString(attrMap.get("job")));
        map.put("address", TextUtil.getTextToString(attrMap.get("address")));
        map.put("info", TextUtil.getTextToString(attrMap.get("custDesc")));
        map.put("city", TextUtil.getTextToString(attrMap.get("city")));
        map.put("id", id);
        map.put("catgory", catgory);
        OkHttpUtils.post()
                .url(Urls.QUTU_SERIES)
                .params(map)
                .build().execute(new MapCallback(this) {
            @Override
            public void onResponse(Map responseMap) {
                super.onResponse(responseMap);
                final Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
                final List<String> imagesList = (List<String>) dataMap.get("images");
                mList.addAll(imagesList);

                for (int i = 0; i < mList.size(); i++) {
                    final ImageView imageView = new ImageView(PictureSeriesActivity.this);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView.setLayoutParams(params);
                    MyApplication.imageLoader.displayImage( mList.get(i), imageView, MyApplication.options);
                    LongListener(imageView, i);
                    mLlPicContain.addView(imageView);
                    mIvList.add(imageView);
                }
            }
        });
    }

    /**
     * 图片长按  监听
     *
     * @param imageView
     * @param finalI
     */
    private void LongListener(final ImageView imageView, final int finalI) {
        //  长按的处理
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isSingleSave = true;
                dialogSaveCommon.setContent(ConstantUtils.DIALOG_CONTENT_03);
                dialogSaveCommon.AlterDialog(PictureSeriesActivity.this);
                singleSelectedIndex = finalI;
                return true;
            }
        });
    }

    /**
     * 保存单张图片
     *
     * @param selectIndex
     */
    private void saveSingleBitmap(int selectIndex) throws Exception {
        singleSelectedIndex = selectIndex;
        Bitmap bitmap = ((BitmapDrawable) mIvList.get(singleSelectedIndex).getDrawable()).getBitmap();
        String bufferUrl = mList.get(singleSelectedIndex);
        String[] splitStr = bufferUrl.split("/");
        try {
            CommonMath.saveBitmap(PictureSeriesActivity.this, bitmap, splitStr[splitStr.length - 1]);
            if (isSingleSave) {
                DebugLog.Toast(PictureSeriesActivity.this, "保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isSingleSave) {
                DebugLog.Toast(PictureSeriesActivity.this, "保存失败");
            } else {
                throw e;
            }
        }
    }

    /**
     * 遍历保存所有图片
     */
    private void saveAllBitmap() {
        try {
            for (int i = 0; i < mIvList.size(); i++) {
                saveSingleBitmap(i);
            }
            DebugLog.Toast(PictureSeriesActivity.this, "保存成功");
        } catch (Exception e) {
            DebugLog.Toast(PictureSeriesActivity.this, "保存失败");
        }
    }


    /**
     * 保存点击事件
     *
     * @param view
     */
    public void onClickSavaPic(View view) {
        isSingleSave = false;
        dialogSaveCommon.setContent(ConstantUtils.DIALOG_CONTENT_02);
        dialogSaveCommon.AlterDialog(this);

    }

    public void getIntentDatas() {
        Intent in = getIntent();
        if (in != null) {
            id = in.getStringExtra("id");
            title = in.getStringExtra("title");
            catgory = in.getStringExtra("catgory");
            isCollected = in.getBooleanExtra("isCollected", false);
            attrMap = (Map<String, Object>) in.getSerializableExtra("attrMap");
        }
    }
}
