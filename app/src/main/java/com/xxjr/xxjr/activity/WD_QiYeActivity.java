package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.seekbar.MaterialRangeSlider;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.Wd_CommonProduce;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WD_QiYeActivity extends CommomAcitivity implements View.OnClickListener {
    private TextView mTvQiYeType;
    private TextView mTvDanBaoWay;
    private TextView mTvWuFaFangkuan;
    private TextView mTvDuiGongLiuShui;
    private MaterialRangeSlider mSbDuigongLiushui;
    private TextView mTvDuiSiLiuShui;
    private MaterialRangeSlider mSbDuiSiLiushui;
    private TextView mTvYingYeShiChang;
    private MaterialRangeSlider mSbYingYeShiJian;
    private EditText mEtDesc;
    private List<MaterialRangeSlider> mSbList = new ArrayList<>();
    private int[] maxDatas = {5,5,4};
    private Map<String, String> addMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd__qi_ye);
        SetTitleBar.setTitleText(WD_QiYeActivity.this,"添加信贷产品 - 企业");
        getIntentDatas();
        initViews();
        setListener();
    }

    private void initViews() {
        mTvQiYeType = (TextView) findViewById(R.id.qiye_tv_qiyetype);
        mTvDanBaoWay = (TextView) findViewById(R.id.qiye_tv_danbao);
        mTvWuFaFangkuan = (TextView) findViewById(R.id.qiye_tv_wufafangkuanhangye);
        mEtDesc = (EditText) findViewById(R.id.qiye_et_desc);

        mTvDuiGongLiuShui = (TextView) findViewById(R.id.qiye_tv_duigongliushui);
        mTvDuiSiLiuShui = (TextView) findViewById(R.id.qiye_tv_duisiliushui);
        mTvYingYeShiChang = (TextView) findViewById(R.id.qiye_tv_yingyeshichang);
        mSbDuigongLiushui = (MaterialRangeSlider) findViewById(R.id.qiye_sb_duigongliushui);
        mSbDuiSiLiushui = (MaterialRangeSlider) findViewById(R.id.qiye_sb_duisiliushui);
        mSbYingYeShiJian = (MaterialRangeSlider) findViewById(R.id.qiye_sb_yingyeshichang);

        mSbList.add(mSbDuigongLiushui);
        mSbList.add(mSbDuiSiLiushui);
        mSbList.add(mSbYingYeShiJian);
        setSeekBarInitDatas(mSbDuigongLiushui, 1, 200, 1, maxDatas[0]);
        setSeekBarInitDatas(mSbDuiSiLiushui, 1, 100, 1, maxDatas[1]);
        setSeekBarInitDatas(mSbYingYeShiJian, 1, 20, 1, maxDatas[2]);
        for (int i=0; i<mSbList.size();i++){
            final int clickPositon = i;
            mSbList.get(i).setRangeSliderListener(new MaterialRangeSlider.RangeSliderListener() {
                @Override
                public void onMaxChanged(int newValue) {
                    maxDatas[clickPositon] = newValue;
                    if (clickPositon==0){
                        mTvDuiGongLiuShui.setText(maxDatas[0]+"万以上");
                    }else if (clickPositon==1){
                        mTvDuiSiLiuShui.setText(maxDatas[1]+"万以上");
                    }else if (clickPositon==2){
                        mTvYingYeShiChang.setText(maxDatas[2]*3+"月以上");
                    }
                }

                @Override
                public void onMinChanged(int newValue) {

                }
            });
        }
    }

    private void setListener() {
        findViewById(R.id.qiye_btn_commit).setOnClickListener(this);
        findViewById(R.id.qiye_rl_qiyetype).setOnClickListener(this);
        findViewById(R.id.qiye_rl_danbaoway).setOnClickListener(this);
        findViewById(R.id.qiye_rl_wufafangkuanhangye).setOnClickListener(this);
    }

    private void setSeekBarInitDatas(MaterialRangeSlider rangeSlider, int min, int max, int start, int end) {
        rangeSlider.setMin(min);
        rangeSlider.setMax(max);
        rangeSlider.setStartingMinMax(start, end);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qiye_rl_qiyetype:
                intentMultChoiceActivity(Wd_CommonProduce.qiyeTypeData(),mTvQiYeType,0);
                break;
            case R.id.qiye_rl_danbaoway:
                intentMultChoiceActivity(Wd_CommonProduce.danbaofangshiData(),mTvDanBaoWay,1);
                break;
            case R.id.qiye_rl_wufafangkuanhangye:
                intentMultChoiceActivity(Wd_CommonProduce.wufafangkuanDatas(),mTvWuFaFangkuan,2);
                break;
            case R.id.qiye_btn_commit:
                String creditLimit ;
                String creditWorkLimit ;
                if (mTvQiYeType.getText().toString().equals("请选择")){
                    Toast.makeText(this, "请选择企业类型", Toast.LENGTH_SHORT).show();
                    return;
                }else if (mTvDanBaoWay.getText().toString().equals("请选择")){
                    Toast.makeText(this, "请选择担保方式", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(mEtDesc.getText().toString().trim())){
                    Toast.makeText(this, "请填写产品描述", Toast.LENGTH_SHORT).show();
                    return;
                }
                addMap.put("companyType",mTvQiYeType.getText().toString());
                addMap.put("assureType",mTvDanBaoWay.getText().toString());
                if (mTvWuFaFangkuan.getText().toString().equals("无")){
                    creditLimit = "0";
                    creditWorkLimit = "";
                }else {
                    creditLimit = "1";
                    creditWorkLimit = mTvWuFaFangkuan.getText().toString();
                }
                addMap.put("creditLimit",creditLimit);
                addMap.put("creditWorkLimit",creditWorkLimit);

                addMap.put("pubAmount",maxDatas[0]+"");//  对公流水
                addMap.put("priAmount",maxDatas[1]+"");//   对私流水
                addMap.put("kbisLimit",maxDatas[2]*3+"");//  营业时长
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
            bundle.putString("titleName","企业类型");
        }else if (position==1){
            bundle.putString("titleName","担保方式");
        }else if (position==2){
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
                if (requestCode==2)
                    tagContain = "无";
            }
            switch (requestCode) {
                case 0:
                    mTvQiYeType.setText(tagContain);
                    break;
                case 1:
                    mTvDanBaoWay.setText(tagContain);
                    break;
                case 2:
                    mTvWuFaFangkuan.setText(tagContain);
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