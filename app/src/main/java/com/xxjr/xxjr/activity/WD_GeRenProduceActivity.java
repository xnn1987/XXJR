package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.seekbar.MaterialRangeSlider;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.Wd_CommonProduce;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WD_GeRenProduceActivity extends CommomAcitivity implements View.OnClickListener {

    private TextView mTvZhiye,mTvXinYong,mTvDaiKuanFangshi,mTvJieKuanYongtu,mTvWuFaFandKuan;
    private ImageView mIvXianjinfafang;
    private ImageView mIvDakafafang;
    private ImageView mIvWushebao;
    private ImageView mIvYoushebao;
    private ImageView mIvBujiaona;
    private ImageView mIvJiaona;
    private boolean isDakafafang = true,isXianJinFaFang = false;//    工资发放要求
    private boolean hasSheBao = true;//    有无社保
    private boolean hasGongJiJin = true;//    缴纳公积金

    private TextView mTvJiaona,mTvBujiaona;
    private TextView mTvYoushebao,mTvWushebao;
    private TextView mTvDakafafang,mTvXianjinfafang;
    private Map<String, String> addMap;
    private List<MaterialRangeSlider> mSbLists = new ArrayList<MaterialRangeSlider>();
    private List<TextView> mTvLists = new ArrayList<TextView>();
    private MaterialRangeSlider mSbAge,mSbYueShouRu,mSbZaiZhiTime;
    private TextView mTvAge,mTvYueShouru,mTvZaiZhiTime;
    private  int clickPostion = 0 ;
    private int[] minSbDatas ={25,1,1};
    private int[] maxSbDatas ={60,2,1};
    private int choicePosition = 0;
    private EditText mEtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd__ge_ren);
        SetTitleBar.setTitleText(WD_GeRenProduceActivity.this,"添加信贷产品-个人");
        getIntentDatas();
        initViews();
        setListener();
    }

    private void initViews() {
        mSbAge = (MaterialRangeSlider) findViewById(R.id.geren_sb_age);
        mSbYueShouRu = (MaterialRangeSlider) findViewById(R.id.geren_sb_yueshouru);
        mSbZaiZhiTime = (MaterialRangeSlider) findViewById(R.id.geren_sb_zaizhishijian);
        mTvAge = (TextView) findViewById(R.id.geren_tv_age);
        mTvYueShouru = (TextView) findViewById(R.id.geren_tv_yueshouru);
        mTvZaiZhiTime = (TextView) findViewById(R.id.geren_tv_zaizhishijian);
        mSbLists.add(mSbAge);
        mSbLists.add(mSbYueShouRu);
        mSbLists.add(mSbZaiZhiTime);
        mTvLists.add(mTvAge);
        mTvLists.add(mTvYueShouru);
        mTvLists.add(mTvZaiZhiTime);
        setSeekBarInitDatas(mSbAge, 18, 70, minSbDatas[0], maxSbDatas[0]);
        setSeekBarInitDatas(mSbYueShouRu, 1, 100, minSbDatas[1], maxSbDatas[1]);//   千元
        setSeekBarInitDatas(mSbZaiZhiTime, 1, 20,minSbDatas[2], maxSbDatas[2]);//   以3  倍数增长

        mIvJiaona = (ImageView) findViewById(R.id.geren_iv_jiaona);
        mTvJiaona = (TextView) findViewById(R.id.geren_tv_jiaona);
        mIvBujiaona = (ImageView) findViewById(R.id.geren_iv_bujiaona);
        mTvBujiaona = (TextView) findViewById(R.id.geren_tv_bujiaona);
        mIvYoushebao = (ImageView) findViewById(R.id.geren_iv_youshebao);
        mTvYoushebao = (TextView) findViewById(R.id.geren_tv_youshebao);
        mIvWushebao = (ImageView) findViewById(R.id.geren_iv_wushebao);
        mTvWushebao = (TextView) findViewById(R.id.geren_tv_wushebao);
        mIvDakafafang = (ImageView) findViewById(R.id.geren_iv_dakafafang);
        mTvDakafafang = (TextView) findViewById(R.id.geren_tv_dakafafang);
        mIvXianjinfafang = (ImageView) findViewById(R.id.geren_iv_xianjinfafang);
        mTvXianjinfafang = (TextView) findViewById(R.id.geren_tv_xianjinfafang);

        mTvZhiye = (TextView) findViewById(R.id.geren_tv_zhiye);
        mTvXinYong = (TextView) findViewById(R.id.geren_tv_xinyong);
        mTvDaiKuanFangshi = (TextView) findViewById(R.id.geren_tv_daikuanfangshi);
        mTvJieKuanYongtu = (TextView) findViewById(R.id.geren_tv_jiekunayongtu);
        mTvWuFaFandKuan = (TextView) findViewById(R.id.geren_tv_wufafangkuan);

        mEtDesc = (EditText) findViewById(R.id.geren_et_desc);
    }

    private void setListener() {
        findViewById(R.id.geren_rl_dakafafang).setOnClickListener(this);
        findViewById(R.id.geren_rl_xianjinfafang).setOnClickListener(this);
        findViewById(R.id.geren_rl_youshebao).setOnClickListener(this);
        findViewById(R.id.geren_rl_wushebao).setOnClickListener(this);
        findViewById(R.id.geren_rl_jiaona).setOnClickListener(this);
        findViewById(R.id.geren_rl_bujiaona).setOnClickListener(this);

        findViewById(R.id.geren_rl_zhiye).setOnClickListener(this);
        findViewById(R.id.geren_rl_xinyong).setOnClickListener(this);
        findViewById(R.id.geren_rl_daikuanfangshi).setOnClickListener(this);
        findViewById(R.id.geren_rl_jiekunayongtu).setOnClickListener(this);
        findViewById(R.id.geren_rl_wufafangkuan).setOnClickListener(this);

        findViewById(R.id.geren_btn_commit).setOnClickListener(this);//提交

        for (int i=0; i<mSbLists.size(); i++){
            final int click = i;
            mSbLists.get(i).setRangeSliderListener(new MaterialRangeSlider.RangeSliderListener() {
                @Override
                public void onMaxChanged(int newValue) {
                    maxSbDatas[click] = newValue;
                    clickPostion = click;
                    if (clickPostion == 0) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion]+"岁-"+maxSbDatas[clickPostion]+"岁");
                    } else if (clickPostion == 1) {
                        mTvLists.get(clickPostion).setText(maxSbDatas[clickPostion]*1000+"元以上");
                    } else if (clickPostion == 2) {
                        mTvLists.get(clickPostion).setText( maxSbDatas[clickPostion]*3+"月以上");
                    }
                }

                @Override
                public void onMinChanged(int newValue) {
                    minSbDatas[click] = newValue;
                    clickPostion = click;
                    if (clickPostion == 0) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion]+"岁-"+maxSbDatas[clickPostion]+"岁");
                    }
                }
            });
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.geren_rl_zhiye:// 职业要求
                choicePosition = 0;
                intentMultChoiceActivity(Wd_CommonProduce.zhiweiTagData(),mTvZhiye,choicePosition);
                break;
            case R.id.geren_rl_xinyong:// 信用记录
                choicePosition = 1;
                intentMultChoiceActivity(Wd_CommonProduce.xinyongTagData(),mTvXinYong,choicePosition);
                break;
            case R.id.geren_rl_daikuanfangshi:// 贷款方式
                choicePosition = 2;
                intentMultChoiceActivity(Wd_CommonProduce.daikuanfangshiTagData(),mTvDaiKuanFangshi,choicePosition);
                break;
            case R.id.geren_rl_jiekunayongtu:// 借款用途
                choicePosition = 3;
                intentMultChoiceActivity(Wd_CommonProduce.jiekuanYongtuTagData(),mTvJieKuanYongtu,choicePosition);
                break;
            case R.id.geren_rl_wufafangkuan:// 无法放款行业
                choicePosition = 4;
                intentMultChoiceActivity(Wd_CommonProduce.wufafangkuanTagData(),mTvWuFaFandKuan,choicePosition);
                break;

            case R.id.geren_rl_dakafafang:
                isDakafafang = !isDakafafang;
                selectedStatue(isDakafafang,mTvDakafafang,mIvDakafafang);
                break;
            case R.id.geren_rl_xianjinfafang:
                isXianJinFaFang = !isXianJinFaFang;
                selectedStatue(isXianJinFaFang,mTvXianjinfafang,mIvXianjinfafang);
                break;
            case R.id.geren_rl_youshebao:
                hasSheBao = true;
                selectedStatue(hasSheBao,mTvYoushebao,mIvYoushebao);
                selectedStatue(!hasSheBao,mTvWushebao,mIvWushebao);
                break;
            case R.id.geren_rl_wushebao:
                hasSheBao = false;
                selectedStatue(hasSheBao,mTvYoushebao,mIvYoushebao);
                selectedStatue(!hasSheBao,mTvWushebao,mIvWushebao);
                break;
            case R.id.geren_rl_jiaona:
                hasGongJiJin = true;
                selectedStatue(hasGongJiJin,mTvJiaona,mIvJiaona);
                selectedStatue(!hasGongJiJin,mTvBujiaona,mIvBujiaona);
                break;
            case R.id.geren_rl_bujiaona:
                hasGongJiJin = false;
                selectedStatue(hasGongJiJin,mTvJiaona,mIvJiaona);
                selectedStatue(!hasGongJiJin,mTvBujiaona,mIvBujiaona);
                break;

            case R.id.geren_btn_commit:
                if (mTvZhiye.getText().toString().equals("请选择")){
                    Toast.makeText(this, "请选择职业要求", Toast.LENGTH_SHORT).show();
                    return;
                }else if (mTvXinYong.getText().toString().equals("请选择")){
                    Toast.makeText(this, "请选择信用记录", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (mTvDaiKuanFangshi.getText().toString().equals("请选择")){
                    Toast.makeText(this, "请选择贷款方式", Toast.LENGTH_SHORT).show();
                    return;
                }else if (mTvJieKuanYongtu.getText().toString().equals("请选择")){
                    Toast.makeText(this, "请选择借款用途", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(mEtDesc.getText().toString().trim())){
                    Toast.makeText(this, "请填写产品描述", Toast.LENGTH_SHORT).show();
                    return;
                }
                String creditLimit = "0";
                String creditWorkLimit = "";
                String wagesType = "";//  工资发放
                addMap.put("workType",mTvZhiye.getText().toString());
                addMap.put("trustType",mTvXinYong.getText().toString());
                addMap.put("assureType",mTvDaiKuanFangshi.getText().toString());
                addMap.put("loanUseType",mTvJieKuanYongtu.getText().toString());
                if (mTvWuFaFandKuan.getText().toString().equals("无")){
                    creditLimit = "0";
                    creditWorkLimit = "";
                }else {
                    creditLimit = "1";
                    creditWorkLimit = mTvWuFaFandKuan.getText().toString();
                }
                addMap.put("creditLimit",creditLimit);//  是否有限制行业
                addMap.put("creditWorkLimit",creditWorkLimit);//  限制的行业
                addMap.put("ageMin",minSbDatas[0]+"");
                addMap.put("ageMax",maxSbDatas[0]+"");
                addMap.put("income",(double)maxSbDatas[1]*1000+"");
                addMap.put("workLimit",maxSbDatas[2]*3+"");

                if (isDakafafang){//  打卡发放
                    wagesType += mTvDakafafang.getText().toString();
                }
                if (isXianJinFaFang){
                    if (!TextUtils.isEmpty(wagesType))
                        wagesType+="、";
                    wagesType +=mTvXianjinfafang.getText().toString();
                }
                addMap.put("wagesType",wagesType);

                if (hasSheBao){//  是否有无社保
                    addMap.put("socialType","1");
                }else {
                    addMap.put("socialType","0");
                }
                if (hasGongJiJin) {
                    addMap.put("fundType", "1");
                }else {
                    addMap.put("fundType", "0");
                }
                addMap.put("descriptions",mEtDesc.getText().toString());
                uploadDatas(Urls.WD_ALTER_PRODUCE);
                break;

        }
    }
    //  上传用户信息
    private void uploadDatas(String url){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.putAll(addMap);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtil.dismissProgress();
                        Toast.makeText(WD_GeRenProduceActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),WDProduceActivity02.class));
                        setResult(RESULT_OK);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }


    private void setSeekBarInitDatas(MaterialRangeSlider rangeSlider, int min, int max, int start, int end) {
        rangeSlider.setMin(min);
        rangeSlider.setMax(max);
        rangeSlider.setStartingMinMax(start, end);
    }



    //   工资  社保   公积金等的判断
    private void selectedStatue(boolean isSelected,TextView textView,ImageView imageView){
        if (isSelected){
            textView.setTextColor(Color.parseColor("#f89169"));
            imageView.setVisibility(View.VISIBLE);
            textView.setBackgroundResource(R.drawable.layout_orange_line);
        }else {
            textView.setTextColor(getResources().getColor(R.color.word_gray_9a9a9a));
            textView.setBackgroundResource(R.drawable.layout_wd_gray_line);
            imageView.setVisibility(View.GONE);
        }
    }


    /**
     * 多选  标签
     * @param tagList
     */
    private void intentMultChoiceActivity(List<String> tagList,TextView textView,int position){
        Intent intent = new Intent(getApplicationContext(),WD_MultChoiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tag", (Serializable) tagList);
        if (!(textView.getText().toString().equals("请选择")|| textView.getText().toString().equals("无"))) {
            bundle.putString("tagName", textView.getText().toString());
        }else {
            bundle.putString("tagName", "");
        }
        if (position==0){
            bundle.putString("titleName","职业要求");
        }else if (position==1){
            bundle.putString("titleName","信用记录");
        }else if (position==2){
            bundle.putString("titleName","贷款方式");
        }else if (position==3){
            bundle.putString("titleName","借款用途");
        }else if (position==4){
            bundle.putString("titleName","无法放款行业");
        }
        intent.putExtras(bundle);
        startActivityForResult(intent,position);
    }

    private void getIntentDatas() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        addMap = (Map<String, String>) bundle.getSerializable("addMap");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null && resultCode ==RESULT_OK) {
            String tagContain = data.getStringExtra("tagContain");
            if (tagContain.equals("")){
                tagContain = "请选择";
                if (requestCode==4)
                    tagContain = "无";
            }
            switch (requestCode) {
                case 0:
                    mTvZhiye.setText(tagContain);
                    break;
                case 1:
                    mTvXinYong.setText(tagContain);
                    break;
                case 2:
                    mTvDaiKuanFangshi.setText(tagContain);
                    break;
                case 3:
                    mTvJieKuanYongtu.setText(tagContain);
                    break;
                case 4:
                    mTvWuFaFandKuan.setText(tagContain);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
