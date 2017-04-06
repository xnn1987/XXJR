package com.xxjr.xxjr.custview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/5/6.
 */
public class CustRecycleView extends RecyclerView {
    public CustRecycleView(Context context) {
        super(context);
    }

    public CustRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec  = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
