package com.xxjr.xxjr.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/1/6.
 */
public class SetTitleBar {

    public static void setTitleText(final Activity activity,String title){
        LinearLayout mLlBack = (LinearLayout) activity.findViewById(R.id.titleBar_ll_back);
        TextView mTvTitle = (TextView) activity.findViewById(R.id.titleBar_tv_title);
        mTvTitle.setText(title);
        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }




    /**
     * 三个标题   ---->>  局限 跳转的东西
     * @param activity
     */
    public static <T> void setTitleText(final Activity activity,String title,String rightTitle,
                                        final Class<T> tClass, final String[] params, final String[] paramsValue){
       LinearLayout mLlBack = (LinearLayout) activity.findViewById(R.id.ThreeTitleBar_ll_back);
       LinearLayout mLlRightTitle = (LinearLayout) activity.findViewById(R.id.ThreeTitleBar_ll_click);
        TextView mTvTitle = (TextView) activity.findViewById(R.id.ThreeTitleBar_tv_title);
        TextView mTvRight = (TextView) activity.findViewById(R.id.ThreeTitleBar_tv_click);
        mTvTitle.setText(title);
        mTvRight.setText(rightTitle);
        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        mLlRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(),tClass);
                if (params!=null){
                    for (int i=0; i<params.length;i++) {
                        intent.putExtra(params[i],paramsValue[i]);
                    }
                }
                activity.startActivity(intent);
            }
        });

    }

    /**
     * 手动扩展，    需要监听的 右边的小标题
     * @param activity
     * @param title
     * @param rightTitle
     */
    public static  void setTitleText(final Activity activity,String title,String rightTitle){
       LinearLayout mLlBack = (LinearLayout) activity.findViewById(R.id.ThreeTitleBar_ll_back);
       LinearLayout mLLRight = (LinearLayout) activity.findViewById(R.id.ThreeTitleBar_ll_click);
        TextView mTvTitle = (TextView) activity.findViewById(R.id.ThreeTitleBar_tv_title);
        TextView mTvRight = (TextView) activity.findViewById(R.id.ThreeTitleBar_tv_click);
        mTvTitle.setText(title);
        mTvRight.setText(rightTitle);
        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

    }

    /**
     * 包含图标的标题   三个的
     * @param activity
     * @param title
     * @param mipmapImage
     */
    public static <T> void setThreeTitleHasIcon(final Activity activity,String title,int mipmapImage,final Class<T> tClass, final String[] params, final String[] paramsValue){
        LinearLayout mLlBack = (LinearLayout) activity.findViewById(R.id.ThreeTitle2Icon_ll_back);
        LinearLayout mLlRightIcon = (LinearLayout) activity.findViewById(R.id.ThreeTitle2Icon_ll_Icon);
        TextView mTvTitle = (TextView) activity.findViewById(R.id.ThreeTitle2Icon_tv_title);
        mTvTitle.setText(title);
        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        mLlRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(),tClass);
                if (params!=null){
                    for (int i=0; i<params.length;i++) {
                        intent.putExtra(params[i],paramsValue[i]);
                    }
                }
                activity.startActivity(intent);
            }
        });
    }

    /**
     * 手动扩展右边的小标题
     * @param activity
     * @param title
     * @param mipmapImage
     * @param <T>
     */
    public static <T> void setThreeTitleHasIcon(final Activity activity,String title,int mipmapImage){
        LinearLayout mLlBack = (LinearLayout) activity.findViewById(R.id.ThreeTitle2Icon_ll_back);
        LinearLayout mLlRightIcon = (LinearLayout) activity.findViewById(R.id.ThreeTitle2Icon_ll_Icon);
        ImageView mIvRight = (ImageView) activity.findViewById(R.id.ThreeTitle2Icon_iv_icon);
        TextView mTvTitle = (TextView) activity.findViewById(R.id.ThreeTitle2Icon_tv_title);
        mIvRight.setImageResource(mipmapImage);
        mTvTitle.setText(title);
        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
