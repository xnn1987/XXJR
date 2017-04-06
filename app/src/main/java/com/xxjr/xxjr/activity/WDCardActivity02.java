package com.xxjr.xxjr.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.image.imageCircle.CircleImage;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

public class WDCardActivity02 extends SlidBackActivity implements View.OnClickListener {
    private int WDCARDACTIVITY02 = 1;
    private TextView mTvName;
    private TextView mTvTel;
    private TextView mTvCompany;
    private TextView mTvPosition;
    private TextView mTvWx;
    private TextView mTvDesc;
    private CircleImage mIvHead;
    private Map<String, Object> attrMap;
//    private String cardId;
    private PopupWindow mPopupWindow;
    private LinearLayout mLlIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdcard02);
        SetTitleBar.setThreeTitleHasIcon(WDCardActivity02.this,"微店名片",R.mipmap.gengduo);
        initViews();
        setListener();
        getIntentDatas();
        setInitViewData();
        initPopupWindow();
        
    }

    private void initViews() {
        mLlIcon = (LinearLayout) findViewById(R.id.ThreeTitle2Icon_ll_Icon);
        mIvHead = (CircleImage) findViewById(R.id.wdcard02_iv_head);
        mTvName = (TextView) findViewById(R.id.wdcard02_iv_name);
        mTvTel = (TextView) findViewById(R.id.wdcard02_tv_tel);
        mTvCompany = (TextView) findViewById(R.id.wdcard02_tv_company);
        mTvPosition = (TextView) findViewById(R.id.wdcard02_tv_position);
        mTvWx = (TextView) findViewById(R.id.wdcard02_tv_wx);
        mTvDesc = (TextView) findViewById(R.id.wdcard02_tv_desc);

    }

    private void setListener() {
        mLlIcon.setOnClickListener(this);
    }

    private void setInitViewData() {
        MyApplication.imageLoader.displayImage(Urls.RANQING + Urls.BUSSINESS_CUSTCARD_HEADICON +
                attrMap.get("headImgUrl"), mIvHead, MyApplication.optionsHead);
        mTvName.setText(attrMap.get("custName").toString());
        mTvTel.setText(attrMap.get("telephone").toString());
        mTvCompany.setText(attrMap.get("company").toString());
        mTvPosition.setText(attrMap.get("job").toString());
        if (attrMap.get("wxNum")!=null)
            mTvWx.setText(attrMap.get("wxNum").toString());
        if (attrMap.get("custDesc")!=null)
            mTvDesc.setText(attrMap.get("custDesc").toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ThreeTitle2Icon_ll_Icon:
                if (!mPopupWindow.isShowing()){
                    mPopupWindow.showAsDropDown(mLlIcon);
                }else {
                    mPopupWindow.dismiss();
                }
                break;
        }
    }
    private void getIntentDatas(){
        Intent intent = getIntent();
        attrMap = (Map<String, Object>) intent.getExtras().getSerializable("attrMap");

    }
    private void initPopupWindow(){
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custcard_popupwindow,null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        LinearLayout mLlAlter = (LinearLayout) view.findViewById(R.id.custCardPupop_ll_alter);
        LinearLayout mLlShared = (LinearLayout) view.findViewById(R.id.custCardPupop_ll_shared);
        mLlAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WDCardSetActivity.class);
                intent.putExtra(ConstantUtils.WDCARD_ISFIRST_WD_CARD,false);
                startActivityForResult(intent,WDCARDACTIVITY02);
                mPopupWindow.dismiss();
            }
        });
        //分享
        mLlShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WDCardSharedActivity.class);
                startActivity(intent);
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WDCARDACTIVITY02 && resultCode == RESULT_OK){
            wdCardDownLoad(Urls.WD_CARD_SETTING_QUERY);
        }
    }

    //  判断  微店名片是否有数据
    private void wdCardDownLoad(String url) {
        AppUtil.showProgress(WDCardActivity02.this,ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Map<String, Object> attrMap1 = (Map<String, Object>) map.get("attr");
                            attrMap = attrMap1;
                            setInitViewData();
                        } else {
                            Toast.makeText(WDCardActivity02.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
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
}
