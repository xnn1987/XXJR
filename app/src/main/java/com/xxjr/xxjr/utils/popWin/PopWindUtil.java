package com.xxjr.xxjr.utils.popWin;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.xxjr.xxjr.R;


/**
 * Created by Administrator on 2016/5/17.
 */
public  class PopWindUtil {

    private static PopupWindow mPopupWindow;
    private static View view;

    public static PopupWindow popWind(Activity activity,int viewId){
        LayoutInflater inflater  = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(viewId,null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        return mPopupWindow;
    }

    public PopupWindow getmPopupWindow() {
        return mPopupWindow;
    }

    public View getView() {
        return view;
    }
}
