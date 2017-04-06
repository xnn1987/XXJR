package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.WelcomeVPAdapter;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends CommomAcitivity implements View.OnClickListener,OnPageChangeListener {

    private Button mButton;
    private List<View> views;
//    private static  final  int[] pics = {R.mipmap.daitijiao,R.mipmap.checked_mark,R.mipmap.erweima_2x};
    private static  final  int[] pics = {R.mipmap.welcom01_2,R.mipmap.welcom02_2,R.mipmap.welcom03_2};
    private ViewPager vp;
    private WelcomeVPAdapter vpAdapter;
    private ImageView[] dots;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mButton = (Button) findViewById(R.id.ViewpagerActivity_btn_welcome);
        views = new ArrayList<>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i<pics.length; i++){
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        vp = (ViewPager) findViewById(R.id.ViewpagerActivity_vp_welcome);
        vpAdapter = new WelcomeVPAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.addOnPageChangeListener(this);

        //初始化底部图片
        initDots();
        mButton.setOnClickListener(new View.OnClickListener() {//跳转
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initDots() {
        LinearLayout mllDots = (LinearLayout) findViewById(R.id.ViewpagerActivity_ll_welcome);
        dots = new ImageView[pics.length];

        for (int i=0;i<pics.length;i++){
            dots[i] = (ImageView) mllDots.getChildAt(i);
            dots[i].setEnabled(true);
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置TAG   ，方便取出与当前位置对应；
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色。  初始化
    }

    /**
     * 设置当前引导页
     * @param position
     */
    private void setCurView(int position){
        if (position <0 || position >=pics.length){
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     * 这只当前引导小点
     * @param position
     */
    private void setCurDot(int position){
        if (position <0 ||position>pics.length-1 || currentIndex == position){
            return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }



    @Override
    public void onPageSelected(int position) {
        setCurDot(position);
        if (position == 2){
            mButton.setVisibility(View.VISIBLE);
        }else {
            mButton.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        int position  = (int) v.getTag();
        setCurDot(position);
        setCurView(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
