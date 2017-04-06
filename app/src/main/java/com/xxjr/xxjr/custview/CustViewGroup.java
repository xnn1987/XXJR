package com.xxjr.xxjr.custview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxjr.xxjr.R;


/**
 * Created by Administrator on 2016/3/3.
 */
public class CustViewGroup extends ViewGroup{
    private static final int PADDING_HOR = 10;//水平方向padding
    private static final int PADDING_VERTICAL = 5;//垂直方向padding
    private static final int SIDE_MARGIN = 10;//左右间距
    private static final int TEXT_MARGIN = 10;
    private int removePosition;
    private boolean isFist = false;
    private boolean isFisrtMes = false;

    public CustViewGroup(Context context) {
        super(context);
    }

    public CustViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int autualWidth = r - l;
        int x = SIDE_MARGIN;// 横坐标开始
        int y = 0;//纵坐标开始
        int rows = 1;
        for(int i=0;i<childCount;i++){
            TextView view = (TextView) getChildAt(i);
            view.setBackgroundResource(R.drawable.tag_normal);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            x += width+TEXT_MARGIN;
            if(x>autualWidth){
                break;
            }
            y = rows*(height+TEXT_MARGIN);
            if(i==0){
                view.layout(x-width-TEXT_MARGIN, y-height, x-TEXT_MARGIN, y);
            }else{
                view.layout(x-width, y-height, x, y);
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int x = 0;//横坐标
        int y = 0;//纵坐标
        int rows = 1;//总行数
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int actualWidth = specWidth - SIDE_MARGIN * 2;//实际宽度
        int childCount = getChildCount();
        for(int index = 0;index<childCount;index++){
            View child = getChildAt(index);
            child.setPadding(PADDING_HOR, PADDING_VERTICAL, PADDING_HOR, PADDING_VERTICAL);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            x += width+TEXT_MARGIN;
            if(x>actualWidth){//换行
                    break;

            }
            y = rows*(height+TEXT_MARGIN);
        }
        setMeasuredDimension(actualWidth, y);
    }
}
