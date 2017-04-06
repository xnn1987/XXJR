package com.xxjr.xxjr.custview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/2/19.
 */
public class CustListview extends ListView {
    public CustListview(Context context) {
        super(context);
    }

    public CustListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec  = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
