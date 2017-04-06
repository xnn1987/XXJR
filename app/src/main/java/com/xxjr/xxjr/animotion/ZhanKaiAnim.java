package com.xxjr.xxjr.animotion;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.DebugLog;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ZhanKaiAnim {
    private int contentLayoutHeight;
    /**
     * Collapse animation to hide the discoverable content.
     *
     * 收起来
     */
    public  void collapse(final ViewGroup contentLayout, ImageView rightIcon) {


        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        contentLayout.measure(w,h);
        int finalHeight = contentLayout.getMeasuredHeight();

        DebugLog.e("heigh  03  ",finalHeight+"");

        int x = rightIcon.getMeasuredWidth() / 2;
        int y = rightIcon.getMeasuredHeight() / 2;

        RotateAnimation rotateAnimator = new RotateAnimation(180, 0f, x, y);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(Animation.ABSOLUTE);
        rotateAnimator.setFillAfter(true);
        rotateAnimator.setDuration(ConstantUtils.DURATION);

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0,contentLayout);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                contentLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        rightIcon.startAnimation(rotateAnimator);
        mAnimator.start();
    }


    /**
     * Expand animation to display the discoverable content.
     */
    public  void expand(ViewGroup contentLayout,ImageView rightIcon) {

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        contentLayout.measure(w,h);
        int heigh  = contentLayout.getMeasuredHeight();
        DebugLog.e("heigh  01  ",heigh+"");



        //set Visible
        contentLayout.setVisibility(View.VISIBLE);
        int x = rightIcon.getMeasuredWidth() / 2;
        int y = rightIcon.getMeasuredHeight() / 2;

        RotateAnimation rotateAnimator = new RotateAnimation(0f, 180, x, y);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(Animation.ABSOLUTE);
        rotateAnimator.setFillAfter(true);
        rotateAnimator.setDuration(ConstantUtils.DURATION);
        rightIcon.startAnimation(rotateAnimator);
        int measuredHeight = contentLayout.getMeasuredHeight();
        DebugLog.e("heigh  02  ",measuredHeight+"");
        ValueAnimator animator = slideAnimator(0, measuredHeight,contentLayout);
        animator.start();
    }

    private  ValueAnimator slideAnimator(int start, int end, final ViewGroup contentLayout) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(ConstantUtils.DURATION);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = contentLayout.getLayoutParams();
                layoutParams.height = value;

                contentLayout.setLayoutParams(layoutParams);
                contentLayout.invalidate();

                /*if (outsideContentLayoutList != null && !outsideContentLayoutList.isEmpty()) {

                    for (ViewGroup outsideParam : outsideContentLayoutList) {
                        ViewGroup.LayoutParams params = outsideParam.getLayoutParams();

                        if (outsideContentHeight == 0) {
                            outsideContentHeight = params.height;
                        }

                        params.height = outsideContentHeight + value;
                        outsideParam.setLayoutParams(params);
                        outsideParam.invalidate();
                    }
                }*/
            }
        });
        return animator;
    }


}
