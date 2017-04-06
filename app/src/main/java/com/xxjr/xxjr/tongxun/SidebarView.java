package com.xxjr.xxjr.tongxun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.xxjr.xxjr.R;


public class SidebarView extends View {
    public String[] arrLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private OnLetterClickedListener listener = null;
    private TextView textView_dialog;
    private int isChoosedPosition = -1;

    public void setTextView(TextView textView) {
        textView_dialog = textView;
    }

    public SidebarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 当前view的宽度
        int width = getWidth();
        // 当前view的高度
        int height = getHeight();
        // 当前view中每个字母所占的高度
        int singleTextHeight = height / arrLetters.length;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT);
        for (int i = 0; i < arrLetters.length; i++) {
            if (i == isChoosedPosition) {
                paint.setColor(getResources().getColor(R.color.title_color));
                paint.setFakeBoldText(true);
            }
            float x = (width - paint.measureText(arrLetters[i])) / 2;
            float y = singleTextHeight * (i + 1);
            DisplayMetrics metric = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
            float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
            int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
            paint.setTextSize(10 * density);
            paint.setColor(Color.parseColor("#9a9a9a"));
            canvas.drawText(arrLetters[i], x, y, paint);
            paint.reset();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int position = (int) (y / getHeight() * arrLetters.length);
        int lastChoosedPosition = isChoosedPosition;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                if (textView_dialog != null) {
                    textView_dialog.setVisibility(View.GONE);
                }
                isChoosedPosition = -1;
                invalidate();
                break;
            default:
                // 触摸边框背景颜色改变
                setBackgroundColor(Color.parseColor("#f7f7f7"));
                if (lastChoosedPosition != position) {
                    if (position >= 0 && position < arrLetters.length) {
                        if (listener != null) {
                            listener.onLetterClicked(arrLetters[position]);
                        }
                        if (textView_dialog != null) {
                            textView_dialog.setVisibility(View.VISIBLE);
                            textView_dialog.setText(arrLetters[position]);
                        }
                        isChoosedPosition = position;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public interface OnLetterClickedListener {
        public void onLetterClicked(String str);
    }

    public void setOnLetterClickedListener(OnLetterClickedListener listener) {
        this.listener = listener;
    }

}
