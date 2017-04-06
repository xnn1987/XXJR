package com.xxjr.xxjr.utils.Shared;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.Shared.SocialUmeng;

/**
 * Created by Administrator on 2016/4/19.
 */
public class SocialUmengShare implements View.OnClickListener{
    private Activity activity;
    private PopupWindow mPopupWindow;
    private String url;
    private String content;
    private String imageUrl;
    private String title;


    public SocialUmengShare(Activity activity,String title,String Content,String url,String imageUrl) {
        this.activity = activity;
        this.url = url;
        this.content = Content;
        this.title = title;
        this.imageUrl = imageUrl;
        initPopupWindow(this.activity);
    }
    public  void showPop(View view){
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }else {
            mPopupWindow.dismiss();
        }
    }

    private void dismissPop(){
        mPopupWindow.dismiss();
    }

    private PopupWindow initPopupWindow(Activity activity){
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.winpop_shared,null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_shared);
        initViews(view);


        return mPopupWindow;

    }

    private void initViews(View view) {
        view.findViewById(R.id.winpopShared_ll_cancel).setOnClickListener(this);
        view.findViewById(R.id.winpopShared_ll_pengyouquan).setOnClickListener(this);
        view.findViewById(R.id.winpopShared_ll_QQ).setOnClickListener(this);
        view.findViewById(R.id.winpopShared_ll_QZone).setOnClickListener(this);
        view.findViewById(R.id.winpopShared_ll_wx).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.winpopShared_ll_wx:
                SocialUmeng.SharedWeiXin(activity, title,content,url,imageUrl);
                break;
            case R.id.winpopShared_ll_QZone:
                SocialUmeng.QzoneShared(activity,title, content,url,imageUrl);
                break;
            case R.id.winpopShared_ll_QQ:
                SocialUmeng.QQShared(activity,title, content,url,imageUrl);
                break;
            case R.id.winpopShared_ll_pengyouquan:
                SocialUmeng.SharedWeiXinCircle(activity,title, content,url,imageUrl);
                break;
            case R.id.winpopShared_ll_cancel:
                dismissPop();
                break;
        }
        dismissPop();
    }
}
