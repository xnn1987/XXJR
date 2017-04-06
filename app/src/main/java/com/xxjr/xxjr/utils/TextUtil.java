package com.xxjr.xxjr.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;

/**
 * Created by Administrator on 2016/4/14.
 */
public class TextUtil {

    public static SpannableStringBuilder changColor(String text,int color,int start,int end){
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(color);
        builder.setSpan(blackSpan,start,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    //  这样可以测量宽度
    private void measureView(View view){
        int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int heigh = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(width,heigh);
    }

    public static String getTextToString(Object object){
        if (object==null){
            return "";
        }else {
            return object.toString().trim();
        }

    }
}
