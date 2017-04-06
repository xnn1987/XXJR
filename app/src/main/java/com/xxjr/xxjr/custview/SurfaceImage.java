package com.xxjr.xxjr.custview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/5/12.
 */
public class SurfaceImage extends ImageView {
    public SurfaceImage(Context context) {
        super(context);
    }

    public SurfaceImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SurfaceImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public Canvas canvas;
    private Bitmap bitmap;
    public Point mRockerPosition;
    public Point mCtrlPoint;
    private int mRudderRadius = 25;
    public int mWheelRadius = 80;
    private Paint mPaint;
    public int isHide = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            this.canvas = canvas;
            canvas.drawBitmap(bitmap, mRockerPosition.x - mRudderRadius, mRockerPosition.y
                    - mRudderRadius, mPaint);
        }
        super.onDraw(canvas);
    }

    int len;
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (isHide == 0) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        len = MathUtils.getLength(mCtrlPoint.x, mCtrlPoint.y, event.getX(), event.getY());
                        // 如果屏幕接触点不在摇杆挥动范围内,则不处理
                        if(len > mWheelRadius) {
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        len = MathUtils.getLength(mCtrlPoint.x, mCtrlPoint.y, event.getX(), event.getY());
                        if(len <= mWheelRadius) {
                            //如果手指在摇杆活动范围内，则摇杆处于手指触摸位置
                            mRockerPosition.set((int)event.getX(), (int)event.getY());
                        }else{
                            //设置摇杆位置，使其处于手指触摸方向的 摇杆活动范围边缘
                            mRockerPosition = MathUtils.getBorderPoint(mCtrlPoint, new Point((int)event.getX(), (int)event.getY()), mWheelRadius);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mRockerPosition = new Point(mCtrlPoint);
                        break;
                }
                Thread.sleep(40);
            }else {
                Thread.sleep(200);
            }
        } catch (Exception e) {

        }
        invalidate();//更新布局
        return true;
    }*/
}
