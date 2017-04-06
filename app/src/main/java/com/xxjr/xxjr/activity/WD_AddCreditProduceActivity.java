package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.other.seekbar.MaterialRangeSlider;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WD_AddCreditProduceActivity extends CommomAcitivity implements View.OnClickListener {

    private   int ADDPRODUCE = 1;
    private Button mBtnGeRen;
    private Button mBtnQiye;
    private LinearLayout mLlTiTle;
    private TextView mTvFuWuDuiXiang;
    private EditText mEtProduceName;
    private TextView mTvCity;
    private ImageView mIvBenxi;
    private ImageView mIvBenjin;
    private ImageView mIvXianxihouben;
    private TextView mTvErDu;
    private TextView mTvTime;
    private MaterialRangeSlider mSbTime, mSbErDu, mSbYueLiLv, mSbLimit, mSbYiCiXing;
    private TextView mTvYueLiLv;
    private TextView mTvYearLimit;
    private TextView mTvYiCiXing;
    private boolean[] huanKuanWay = {true,false,false} ;
    private List<TextView> mTvLists = new ArrayList<>();
    private List<MaterialRangeSlider> mSbLists = new ArrayList<>();
    private List<ImageView> mIvList = new ArrayList<>();
    private List<TextView> mtvList = new ArrayList<>();
    private int[] minDatas = {10, 1, 50, 1, 0};
    private int[] maxDatas = {100, 15, 200, 12, 100};
    private ImageView mIvVisible;
    private boolean ivVisible = false;
    private int serviceType = 1;//  服务对象
    private int repayType = 1;// 还款方式
    private int showFree = 1;//  展示一次性收费
    private Map<String,String> addMap = new HashMap<>();
    private TextView mTvBenxi,mTvBenJin,mTvXianXiHouBen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd__add_credit_produce);
        SetTitleBar.setTitleText(WD_AddCreditProduceActivity.this,"添加信贷产品","下一步");
        initViews();
        setCityInit();//  初始化城市
        setListener();
        setHuanKuanWay(1);

    }

    private void setListener() {
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(this);
        findViewById(R.id.addcreditproduce_rl_benxi).setOnClickListener(this);
        findViewById(R.id.addcreditproduce_rl_benjin).setOnClickListener(this);
        findViewById(R.id.addcreditproduce_rl_xianxihouben).setOnClickListener(this);
        findViewById(R.id.addcreditproduce_rl_fuwu).setOnClickListener(this);//  城市列表的监听
        mBtnGeRen.setOnClickListener(this);
        mBtnQiye.setOnClickListener(this);
        mIvVisible.setOnClickListener(this);

        for (int i = 0; i < mSbLists.size(); i++) {
            final int choicePosition = i;
            mSbLists.get(i).setRangeSliderListener(new MaterialRangeSlider.RangeSliderListener() {
                @Override
                public void onMaxChanged(int newValue) {
                    maxDatas[choicePosition] = newValue;
                    if (choicePosition == 0) {
                        mTvLists.get(choicePosition).setText(minDatas[choicePosition]+"万-"+maxDatas[choicePosition]+"万");
                    } else if (choicePosition == 1) {
                        mTvLists.get(choicePosition).setText(minDatas[choicePosition]+"天-"+maxDatas[choicePosition]+"天");
                    } else if (choicePosition == 2) {
                        mTvLists.get(choicePosition).setText((double)minDatas[choicePosition]/100+"%-"
                                +(double) maxDatas[choicePosition]/100+"%");
                    } else if (choicePosition == 3) {
                        mTvLists.get(choicePosition).setText(minDatas[choicePosition]*3+"月-"+maxDatas[choicePosition]*3+"月");
                    } else if (choicePosition == 4) {
                        mTvLists.get(choicePosition).setText((double)maxDatas[choicePosition]/100+"%");
                    }

                }

                @Override
                public void onMinChanged(int newValue) {
                    minDatas[choicePosition] = newValue;
                    if (choicePosition == 0) {
                        mTvLists.get(choicePosition).setText(minDatas[choicePosition]+"万-"+maxDatas[choicePosition]+"万");
                    } else if (choicePosition == 1) {
                        mTvLists.get(choicePosition).setText(minDatas[choicePosition]+"天-"+maxDatas[choicePosition]+"天");
                    } else if (choicePosition == 2) {
                        mTvLists.get(choicePosition).setText((double)minDatas[choicePosition]/100+"%-"
                                +(double) maxDatas[choicePosition]/100+"%");
                    } else if (choicePosition == 3) {
                        mTvLists.get(choicePosition).setText(minDatas[choicePosition]*3+"月-"+maxDatas[choicePosition]*3+"月");
                    } else if (choicePosition == 4) {
                        mTvLists.get(choicePosition).setText((double)maxDatas[choicePosition]/100+"%");
                    }

                }
            });
        }

    }

    private void initViews() {
        mLlTiTle = (LinearLayout) findViewById(R.id.addcreditproduce_ll_choice);
        mBtnQiye = (Button) findViewById(R.id.addcreditproduce_btn_qiye);
        mBtnGeRen = (Button) findViewById(R.id.addcreditproduce_btn_geren);
        mTvFuWuDuiXiang = (TextView) findViewById(R.id.addcreditproduce_tv_duixiang);
        mEtProduceName = (EditText) findViewById(R.id.addcreditproduce_et_produce);
        mTvCity = (TextView) findViewById(R.id.addcreditproduce_tv_fuwu);//城市

        mTvBenxi = (TextView) findViewById(R.id.addcreditproduce_tv_benxi);
         mTvBenJin = (TextView) findViewById(R.id.addcreditproduce_tv_benjin);
         mTvXianXiHouBen = (TextView) findViewById(R.id.addcreditproduce_tv_xianxihouben);
        mIvBenxi = (ImageView) findViewById(R.id.addcreditproduce_iv_benxi);//  勾选图片
        mIvBenjin = (ImageView) findViewById(R.id.addcreditproduce_iv_benjin);
        mIvXianxihouben = (ImageView) findViewById(R.id.addcreditproduce_iv_xianxihouben);
        mIvVisible = (ImageView) findViewById(R.id.addcreditproduce_iv_visible);
        mIvList.add(mIvBenxi);
        mIvList.add(mIvBenjin);
        mIvList.add(mIvXianxihouben);
        mtvList.add(mTvBenxi);
        mtvList.add(mTvBenJin);
        mtvList.add(mTvXianXiHouBen);

        //seek bar 系列
        mTvErDu = (TextView) findViewById(R.id.addcreditproduce_tv_erdu);
        mTvTime = (TextView) findViewById(R.id.addcreditproduce_tv_time);
        mTvYueLiLv = (TextView) findViewById(R.id.addcreditproduce_yue_fanwei);
        mTvYearLimit = (TextView) findViewById(R.id.addcreditproduce_tv_nianxian);
        mTvYiCiXing = (TextView) findViewById(R.id.addcreditproduce_tv_yicixing);

        mSbErDu = (MaterialRangeSlider) findViewById(R.id.creditproduce_rs_erdu);
        mSbTime = (MaterialRangeSlider) findViewById(R.id.creditproduce_rs_time);
        mSbYueLiLv = (MaterialRangeSlider) findViewById(R.id.creditproduce_rs_yue);
        mSbLimit = (MaterialRangeSlider) findViewById(R.id.creditproduce_rs_yearlimit);
        mSbYiCiXing = (MaterialRangeSlider) findViewById(R.id.creditproduce_rs_yicixing);
        setSeekBarInitDatas(mSbErDu, 1, 200, minDatas[0], maxDatas[0]);
        setSeekBarInitDatas(mSbTime, 1, 60, minDatas[1], maxDatas[1]);
        setSeekBarInitDatas(mSbYueLiLv, 30, 400, minDatas[2], maxDatas[2]);
        setSeekBarInitDatas(mSbLimit, 1, 120, minDatas[3], maxDatas[3]);
        setSeekBarInitDatas(mSbYiCiXing, 0, 2000, minDatas[4], maxDatas[4]);

        mTvLists.add(mTvErDu);
        mTvLists.add(mTvTime);
        mTvLists.add(mTvYueLiLv);
        mTvLists.add(mTvYearLimit);
        mTvLists.add(mTvYiCiXing);

        mSbLists.add(mSbErDu);
        mSbLists.add(mSbTime);
        mSbLists.add(mSbYueLiLv);
        mSbLists.add(mSbLimit);
        mSbLists.add(mSbYiCiXing);

        fitTitleImage();


    }
    //  初始化城市
    private void setCityInit() {
        SharedPrefUtil sp = new SharedPrefUtil(getApplicationContext(),ConstantUtils.SP_WEIDIAN);
        String city = sp.getString(ConstantUtils.SP_WD_SERVECITY,"");
        mTvCity.setText(sp.getString(ConstantUtils.SP_WD_SERVECITY,""));
    }

    private void setSeekBarInitDatas(MaterialRangeSlider rangeSlider, int min, int max, int start, int end) {
        rangeSlider.setMin(min);
        rangeSlider.setMax(max);
        rangeSlider.setStartingMinMax(start, end);
    }

    /**
     * 对标题 背景图片的一个适配，通过比例来弄
     */
    private void fitTitleImage(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.qiye);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int mesure_h = (int) (((float)h * MyApplication.screenWidth)/w);
        mLlTiTle.getLayoutParams().height  = mesure_h;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ThreeTitleBar_ll_click://  标题下一步
                if (TextUtils.isEmpty(mEtProduceName.getText().toString())){
                    Toast.makeText(this, "请输入产品名称", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(mTvCity.getText().toString()) ){
                    Toast.makeText(this, "请选择服务城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                addMap.put("serviceType",serviceType+"");//  对象类型
                addMap.put("productName",mEtProduceName.getText().toString());
                addMap.put("includeCity",mTvCity.getText().toString());
                addMap.put("repayType",repayType+"");
                addMap.put("creditMin",minDatas[0]+"");//  额度
                addMap.put("creditMax",maxDatas[0]+"");
                addMap.put("creditDateMin",minDatas[1]+"");//放款时间
                addMap.put("creditDateMax",maxDatas[1]+"");
                addMap.put("rateMin",(float)minDatas[2]/100+"");
                addMap.put("rateMax",(float)maxDatas[2]/100+"");//  月率
                addMap.put("loanDateMin",minDatas[3]*3+"");//  贷款时间   贷款期限
                addMap.put("loanDateMax",maxDatas[3]*3+"");
                addMap.put("free",(float)maxDatas[4]/100+"");//一次性收费
                addMap.put("showFree",showFree+"");//是否展示一次性收费

                Bundle bundle = new Bundle();
                bundle.putSerializable("addMap",(Serializable)addMap);
                if (serviceType==1){
                     intent.setClass(getApplicationContext(),WD_GeRenProduceActivity.class);
                }else {
                    intent.setClass(getApplicationContext(),WD_QiYeActivity.class);
                }

                intent.putExtras(bundle);
                startActivityForResult(intent,ADDPRODUCE);


                break;
            case R.id.addcreditproduce_btn_geren://  个人
                mLlTiTle.setSelected(false);
                mTvFuWuDuiXiang.setText("个人");
                serviceType = 1;
                break;
            case R.id.addcreditproduce_btn_qiye://  企业
                mLlTiTle.setSelected(true);
                mTvFuWuDuiXiang.setText("企业");
                serviceType = 2;
                break;
            case R.id.addcreditproduce_rl_fuwu://  城市
                 intent.setClass(getApplicationContext(),WD_SeverCityActivity.class);
                String cityName  = "";
                if (!mTvCity.getText().toString().equals("服务城市"))
                    cityName = mTvCity.getText().toString();
                intent.putExtra("cityName",cityName);
                startActivityForResult(intent, ConstantUtils.WD_CITY);
                break;
            case R.id.addcreditproduce_rl_benxi://  还款方式  本息
                setHuanKuanWay(1);
                break;
            case R.id.addcreditproduce_rl_benjin://  还款方式  本金
                setHuanKuanWay(2);
                break;
            case R.id.addcreditproduce_rl_xianxihouben://  还款方式    先息后本
                setHuanKuanWay(3);
                break;
            case R.id.addcreditproduce_iv_visible:
                if (!ivVisible){
                    ivVisible = true;
                    mIvVisible.setImageResource(R.mipmap.yiyuedu_h);
                    showFree = 1;
                }else {
                    ivVisible = false;
                    mIvVisible.setImageResource(R.mipmap.yuedu_h);
                    showFree = 0;
                }

                break;
        }
    }

    /**
     * 还款方式的判定
     * @param choicePosition
     */
    private void setHuanKuanWay(int choicePosition){
        for (int i=1; i<= huanKuanWay.length; i++){
            if (i==choicePosition){
                huanKuanWay[i-1] = true;
                repayType = choicePosition;
            }else {
                huanKuanWay[i-1] = false;
            }
            if (huanKuanWay[i-1]){
                mtvList.get(i-1).setSelected(true);
                mIvList.get(i-1).setVisibility(View.VISIBLE);
            }else {
                mtvList.get(i-1).setSelected(false);
                mIvList.get(i-1).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == ConstantUtils.WD_CITY && resultCode == RESULT_OK){
            if (data!=null)
                mTvCity.setText(data.getStringExtra("cityname"));
        }
        if (requestCode== ADDPRODUCE &&   resultCode == RESULT_OK){
            finish();
        }
    }
}
