package com.xxjr.xxjr.utils.popWin;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MainPop implements View.OnClickListener {

    private final PopupWindow popupWindow;
    private LinearLayout mLlLoad;
    private LinearLayout mLlLiCai;
    private LinearLayout mLlInsure;
    private LinearLayout mLlLanded;
    private List<LinearLayout> mLlLeftList = new ArrayList<>();
    private List<TextView> mTvLeftList = new ArrayList<>();
    private List<ImageView> mIvLeftList = new ArrayList<>();
    private List<TextView> mTvRightList = new ArrayList<>();
    private TextView mTvAll;
    private TextView mTvWeekday;
    private TextView mTvTaday;
    private TextView mTvThreeDay;
    private TextView mTvLoad;
    private TextView mTvLicai;
    private TextView mTvInsure;
    private TextView mTvLanded;
    private ImageView mIvLoad;
    private ImageView mIvLicai;
    private ImageView mIvInsure;
    private ImageView mIvLanded;
    private boolean leftFlag[] = {true,false,false,false};
    private boolean rightFlag[] = {true,false,false,false};
    private String[] dayValues = {"","0","3","7"};
    private final View contentView;
    private PopListener listener;

    public MainPop(Activity activity) {
        popupWindow = PopWindUtil.popWind(activity, R.layout.popwind_card_fm);
        contentView = popupWindow.getContentView();
        initViews();
        setListener();
        leftSelete(0);
        rightSeleted(0);
    }

    public void initViews(){
        mLlLoad = (LinearLayout) contentView.findViewById(R.id.popwind_card_ll_load);
        mLlLiCai = (LinearLayout) contentView.findViewById(R.id.popwind_card_ll_licai);
        mLlInsure = (LinearLayout) contentView.findViewById(R.id.popwind_card_ll_insure);
        mLlLanded = (LinearLayout) contentView.findViewById(R.id.popwind_card_ll_landed);
        mLlLeftList.add(mLlLoad);
        mLlLeftList.add(mLlLiCai);
        mLlLeftList.add(mLlInsure);
        mLlLeftList.add(mLlLanded);

        mTvLoad = (TextView) contentView.findViewById(R.id.popwind_card_tv_load);
        mTvLicai = (TextView) contentView.findViewById(R.id.popwind_card_tv_licai);
        mTvInsure = (TextView) contentView.findViewById(R.id.popwind_card_tv_insure);
        mTvLanded = (TextView) contentView.findViewById(R.id.popwind_card_tv_landed);
        mTvLeftList.add(mTvLoad);
        mTvLeftList.add(mTvLicai);
        mTvLeftList.add(mTvInsure);
        mTvLeftList.add(mTvLanded);

        mIvLoad = (ImageView) contentView.findViewById(R.id.popwind_card_iv_load);
        mIvLicai = (ImageView) contentView.findViewById(R.id.popwind_card_iv_licai);
        mIvInsure = (ImageView) contentView.findViewById(R.id.popwind_card_iv_insure);
        mIvLanded = (ImageView) contentView.findViewById(R.id.popwind_card_iv_landed);
        mIvLeftList.add(mIvLoad);
        mIvLeftList.add(mIvLicai);
        mIvLeftList.add(mIvInsure);
        mIvLeftList.add(mIvLanded);

        mTvAll = (TextView) contentView.findViewById(R.id.popwind_card_tv_all);
        mTvTaday = (TextView) contentView.findViewById(R.id.popwind_card_tv_taday);
        mTvThreeDay = (TextView) contentView.findViewById(R.id.popwind_card_tv_threeDay);
        mTvWeekday = (TextView) contentView.findViewById(R.id.popwind_card_tv_weekday);
        mTvRightList.add(mTvAll);
        mTvRightList.add(mTvTaday);
        mTvRightList.add(mTvThreeDay);
        mTvRightList.add(mTvWeekday);


    }

    private void setListener(){
        mLlLoad.setOnClickListener(this);
        mLlLiCai.setOnClickListener(this);
        mLlInsure.setOnClickListener(this);
        mLlLanded.setOnClickListener(this);
        mTvAll.setOnClickListener(this);
        mTvTaday.setOnClickListener(this);
        mTvThreeDay.setOnClickListener(this);
        mTvWeekday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popwind_card_ll_load:
                leftSelete(0);
                break;
            case R.id.popwind_card_ll_licai:
                leftSelete(1);
                break;
            case R.id.popwind_card_ll_insure:
                leftSelete(2);
                break;
            case R.id.popwind_card_ll_landed:
                leftSelete(3);
                break;
            case R.id.popwind_card_tv_all:
                rightSeleted(0);

                dismiss();
                break;
            case R.id.popwind_card_tv_taday:
                rightSeleted(1);
                dismiss();
                break;
            case R.id.popwind_card_tv_threeDay:
                rightSeleted(2);
                dismiss();
                break;
            case R.id.popwind_card_tv_weekday:
                rightSeleted(3);
                dismiss();
                break;
        }
    }

    /**
     * 左边的选中
     * @param index
     */
    private void leftSelete(int index){
        for (int i=0; i<leftFlag.length; i++){
            if (i == index){
                leftFlag[i]=true;
                mLlLeftList.get(i).setSelected(true);
                mIvLeftList.get(i).setSelected(true);
                mTvLeftList.get(i).setSelected(true);

            }else {
                leftFlag[i]=false;
                mLlLeftList.get(i).setSelected(false);
                mIvLeftList.get(i).setSelected(false);
                mTvLeftList.get(i).setSelected(false);
            }
        }

        if (listener!=null){
            listener.leftListener(getLeftContent(),index);
        }
    }

    /**
     * 左边选中的文本
     * @return
     */
    public String getLeftContent(){
        for (int i=0; i<leftFlag.length; i++){
            if (leftFlag[i] == true){
                return mTvLeftList.get(i).getText().toString();
            }
        }
        return mTvLeftList.get(0).getText().toString();
    }

    /**
     * 右边的选中
     * @param index
     */
    private void rightSeleted(int index){
        for (int i=0; i<rightFlag.length; i++){
            if (i == index){
                rightFlag[i]=true;
                mTvRightList.get(i).setSelected(true);
            }else {
                rightFlag[i]=false;
                mTvRightList.get(i).setSelected(false);
            }
        }
        if (listener!=null){
            listener.rightListener(getRightContent(),dayValues[index]);
        }

    }
    /**
     * 右边选中的文本
     * @return
     */
    public String getRightContent(){
        int index  = 0;
        for (int i=0; i<rightFlag.length; i++){
            if (rightFlag[i] == true){
                index = i;
            }
        }
        return mTvRightList.get(index).getText().toString();
    }

    public interface PopListener {
        void rightListener(String rightContent, String dayValue);
        void leftListener(String leftContent, int catgoryInfo);
    }

    public void setRightListener(PopListener listener){
        this.listener = listener;
    }
    public void show(View view){
        popupWindow.showAsDropDown(view);
    }
    public void dismiss(){
        popupWindow.dismiss();
    }

}
