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
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.Wd_CommonProduce;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WD_GeRenAlterActivity extends CommomAcitivity implements View.OnClickListener {

    private TextView mTvCity, mTvZhiye, mTvXinYong, mTvDaiKuanFangshi, mTvJieKuanYongtu, mTvWuFaFandKuan;
    private ImageView mIvXianjinfafang;
    private ImageView mIvDakafafang;
    private ImageView mIvWushebao;
    private ImageView mIvYoushebao;
    private ImageView mIvBujiaona;
    private ImageView mIvJiaona;
    private boolean isDakafafang = true, isXianJinFaFang = false;//    工资发放要求
    private boolean hasSheBao = true;//    有无社保
    private boolean hasGongJiJin = true;//    缴纳公积金

    private TextView mTvJiaona, mTvBujiaona;
    private TextView mTvYoushebao, mTvWushebao;
    private TextView mTvDakafafang, mTvXianjinfafang;
    private Map<String, String> addMap = new HashMap<>();
    private List<MaterialRangeSlider> mSbLists = new ArrayList<MaterialRangeSlider>();
    private List<TextView> mTvLists = new ArrayList<>();
    private MaterialRangeSlider mSbErDu, mSbTime, mSbYiCiXing, mSbLimit, mSbAge, mSbYueLiLv, mSbYueShouRu, mSbZaiZhiTime;
    private TextView mTvErDu, mTvTime, mTvYiCiXing, mTvLimit, mTvAge, mTvYueLiLv, mTvYueShouru, mTvZaiZhiTime;
    private int clickPostion = 0;
    private int[] minSbDatas = {1, 3, 0, 1, 18, 50, 1, 1};
    private int[] maxSbDatas = {10, 10, 70, 3, 60, 100, 100, 12};
    private boolean[] huanKuanWay = {true, false, false};
    private int choicePosition = 0;
    private EditText mEtProName, mEtDesc;
    private ImageView mIvVisible;
    private int showFree = 0;
    private Map<String, Object> map;
    private TextView mTvDengErBenXi, mTvDengErBenJin, mTvXianXiHouBen;
    private ImageView mIvDengErBenXi, mIvBenjin, mIvXianxihouben;
    private int repayType = 1;//  还款方式
    private List<ImageView> mIvList = new ArrayList<>();//  还款方式
    private List<TextView> mTvList = new ArrayList<>();//  还款方式
    private String productId;  //  产品ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd__ge_ren_alter);
        SetTitleBar.setTitleText(WD_GeRenAlterActivity.this, "编辑信贷产品-个人");
        initViews();
        setListener();
        getIntentDatas();
        setViewDatas(map);
    }

    private void initViews() {
        mSbErDu = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_erdu);
        mSbTime = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_time);
        mSbYiCiXing = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_yicixing);
        mSbLimit = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_limt);
        mSbAge = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_age);
        mSbYueLiLv = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_yuelilv);
        mSbYueShouRu = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_yueshouru);
        mSbZaiZhiTime = (MaterialRangeSlider) findViewById(R.id.gerenAlter_sb_zaizhishijian);
        mTvErDu = (TextView) findViewById(R.id.gerenAlter_tv_erdu);
        mTvTime = (TextView) findViewById(R.id.gerenAlter_tv_time);
        mTvYiCiXing = (TextView) findViewById(R.id.gerenAlter_tv_yicixing);
        mTvLimit = (TextView) findViewById(R.id.gerenAlter_tv_limt);
        mTvAge = (TextView) findViewById(R.id.gerenAlter_tv_age);
        mTvYueLiLv = (TextView) findViewById(R.id.gerenAlter_tv_yuelilv);
        mTvYueShouru = (TextView) findViewById(R.id.gerenAlter_tv_yueshouru);
        mTvZaiZhiTime = (TextView) findViewById(R.id.gerenAlter_tv_zaizhishijian);
        mSbLists.add(mSbErDu);
        mSbLists.add(mSbTime);
        mSbLists.add(mSbYiCiXing);
        mSbLists.add(mSbLimit);
        mSbLists.add(mSbAge);
        mSbLists.add(mSbYueLiLv);
        mSbLists.add(mSbYueShouRu);
        mSbLists.add(mSbZaiZhiTime);
        mTvLists.add(mTvErDu);
        mTvLists.add(mTvTime);
        mTvLists.add(mTvYiCiXing);
        mTvLists.add(mTvLimit);
        mTvLists.add(mTvAge);
        mTvLists.add(mTvYueLiLv);
        mTvLists.add(mTvYueShouru);
        mTvLists.add(mTvZaiZhiTime);

        mIvJiaona = (ImageView) findViewById(R.id.gerenAlter_iv_jiaona);
        mTvJiaona = (TextView) findViewById(R.id.gerenAlter_tv_jiaona);
        mIvBujiaona = (ImageView) findViewById(R.id.gerenAlter_iv_bujiaona);
        mTvBujiaona = (TextView) findViewById(R.id.gerenAlter_tv_bujiaona);
        mIvYoushebao = (ImageView) findViewById(R.id.gerenAlter_iv_youshebao);
        mTvYoushebao = (TextView) findViewById(R.id.gerenAlter_tv_youshebao);
        mIvWushebao = (ImageView) findViewById(R.id.gerenAlter_iv_wushebao);
        mTvWushebao = (TextView) findViewById(R.id.gerenAlter_tv_wushebao);
        mIvDakafafang = (ImageView) findViewById(R.id.gerenAlter_iv_dakafafang);
        mTvDakafafang = (TextView) findViewById(R.id.gerenAlter_tv_dakafafang);
        mIvXianjinfafang = (ImageView) findViewById(R.id.gerenAlter_iv_xianjinfafang);
        mTvXianjinfafang = (TextView) findViewById(R.id.gerenAlter_tv_xianjinfafang);

        mTvZhiye = (TextView) findViewById(R.id.gerenAlter_tv_zhiye);
        mTvCity = (TextView) findViewById(R.id.gerenAlter_tv_city);
        mTvXinYong = (TextView) findViewById(R.id.gerenAlter_tv_xinyong);
        mTvDaiKuanFangshi = (TextView) findViewById(R.id.gerenAlter_tv_daikuanfangshi);
        mTvJieKuanYongtu = (TextView) findViewById(R.id.gerenAlter_tv_jiekunayongtu);
        mTvWuFaFandKuan = (TextView) findViewById(R.id.gerenAlter_tv_wufafangkuan);

        mTvDengErBenXi = (TextView) findViewById(R.id.gerenAlter_tv_benxi);
        mTvDengErBenJin = (TextView) findViewById(R.id.gerenAlter_tv_benjin);
        mTvXianXiHouBen = (TextView) findViewById(R.id.gerenAlter_tv_xianxihouben);
        mIvDengErBenXi = (ImageView) findViewById(R.id.gerenAlter_iv_benxi);
        mIvBenjin = (ImageView) findViewById(R.id.gerenAlter_iv_benjin);
        mIvXianxihouben = (ImageView) findViewById(R.id.gerenAlter_iv_xianxihouben);
        mIvList.add(mIvDengErBenXi);
        mIvList.add(mIvBenjin);
        mIvList.add(mIvXianxihouben);
        mTvList.add(mTvDengErBenXi);
        mTvList.add(mTvDengErBenJin);
        mTvList.add(mTvXianXiHouBen);

        mEtProName = (EditText) findViewById(R.id.gerenAlter_tv_ProName);
        mEtDesc = (EditText) findViewById(R.id.gerenAlter_et_desc);
        mIvVisible = (ImageView) findViewById(R.id.gerenAlter_iv_visible);
    }

    private void initSeekBar() {
        setSeekBarInitDatas(mSbErDu, 1, 200, minSbDatas[0], maxSbDatas[0]);
        setSeekBarInitDatas(mSbTime, 1, 60, minSbDatas[1], maxSbDatas[1]);
        setSeekBarInitDatas(mSbYiCiXing, 0, 500, minSbDatas[2], maxSbDatas[2]);
        setSeekBarInitDatas(mSbLimit, 1, 36, minSbDatas[3], maxSbDatas[3]);
        setSeekBarInitDatas(mSbAge, 18, 70, minSbDatas[4], maxSbDatas[4]);
        setSeekBarInitDatas(mSbYueLiLv, 0, 400, minSbDatas[5], maxSbDatas[5]);
        setSeekBarInitDatas(mSbYueShouRu, 1, 100, 1, maxSbDatas[6]);
        setSeekBarInitDatas(mSbZaiZhiTime, 1, 12, minSbDatas[7], maxSbDatas[7]);
    }

    private void setListener() {
        findViewById(R.id.gerenAlter_rl_dakafafang).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_xianjinfafang).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_youshebao).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_wushebao).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_jiaona).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_bujiaona).setOnClickListener(this);

        findViewById(R.id.gerenAlter_rl_ProName).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_city).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_zhiye).setOnClickListener(this);

        findViewById(R.id.gerenAlter_rl_zhiye).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_xinyong).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_daikuanfangshi).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_jiekunayongtu).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_wufafangkuan).setOnClickListener(this);

        findViewById(R.id.gerenAlter_rl_benxi).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_benjin).setOnClickListener(this);
        findViewById(R.id.gerenAlter_rl_xianxihouben).setOnClickListener(this);

        mIvVisible.setOnClickListener(this);


        findViewById(R.id.gerenAlter_btn_commit).setOnClickListener(this);//提交

        for (int i = 0; i < mSbLists.size(); i++) {
            final int click = i;
            // todo
            mSbLists.get(i).setRangeSliderListener(new MaterialRangeSlider.RangeSliderListener() {
                @Override
                public void onMinChanged(int newValue) {
                    minSbDatas[click] = newValue;
                    clickPostion = click;
                    if (clickPostion == 0) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "万-" + maxSbDatas[clickPostion] + "万");
                    } else if (clickPostion == 1) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "天-" + maxSbDatas[clickPostion] + "天");
                    } else if (clickPostion == 3) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "月-" + maxSbDatas[clickPostion] + "月");
                    } else if (clickPostion == 4) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "岁-" + maxSbDatas[clickPostion] + "岁");
                    } else if (clickPostion == 5) {
                        mTvLists.get(clickPostion).setText((float) minSbDatas[clickPostion] / 100 + "%-" + (float) maxSbDatas[clickPostion] / 100 + "%");
                    }
                }

                @Override
                public void onMaxChanged(int newValue) {
                    maxSbDatas[click] = newValue;
                    clickPostion = click;
                    if (clickPostion == 0) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "万-" + maxSbDatas[clickPostion] + "万");
                    } else if (clickPostion == 1) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "天-" + maxSbDatas[clickPostion] + "天");
                    } else if (clickPostion == 2) {
                        mTvLists.get(clickPostion).setText((float) maxSbDatas[clickPostion] / 100 + "%");
                    } else if (clickPostion == 3) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "月-" + maxSbDatas[clickPostion] + "月");
                    } else if (clickPostion == 4) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "岁-" + maxSbDatas[clickPostion] + "岁");
                    } else if (clickPostion == 5) {
                        mTvLists.get(clickPostion).setText((float) minSbDatas[clickPostion] / 100 + "%-" + (float) maxSbDatas[clickPostion] / 100 + "%");
                    } else if (clickPostion == 6) {
                        mTvLists.get(clickPostion).setText(maxSbDatas[clickPostion] * 1000 + "元以上");
                    } else if (clickPostion == 7) {
                        mTvLists.get(clickPostion).setText(maxSbDatas[clickPostion] * 3 + "月以上");
                    }
                }

            });
        }
    }


    private void setViewDatas(Map<String, Object> map) {
        Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
        Map<String, Object> proDtlInfoMap = (Map<String, Object>) attrMap.get("proDtlInfo");
        Map<String, Object> proInfoMap = (Map<String, Object>) attrMap.get("proInfo");

        productId = proDtlInfoMap.get("productId").toString();
        mEtProName.setText(TextUtil.getTextToString(proDtlInfoMap.get("productName")));
        mTvCity.setText(TextUtil.getTextToString(proDtlInfoMap.get("includeCity")));
        mTvZhiye.setText(TextUtil.getTextToString(proInfoMap.get("workType")));
        mTvXinYong.setText(TextUtil.getTextToString(proInfoMap.get("trustType")));
        mTvDaiKuanFangshi.setText(TextUtil.getTextToString(proInfoMap.get("assureType")));
        mTvJieKuanYongtu.setText(TextUtil.getTextToString(proInfoMap.get("loanUseType")));
        mTvWuFaFandKuan.setText(TextUtil.getTextToString(proInfoMap.get("creditWorkLimit")));

        // todo
        minSbDatas[0] = Integer.parseInt(proDtlInfoMap.get("creditMin").toString());
        maxSbDatas[0] = Integer.parseInt(proDtlInfoMap.get("creditMax").toString());
        minSbDatas[1] = Integer.parseInt(proDtlInfoMap.get("creditDateMin").toString());
        maxSbDatas[1] = Integer.parseInt(proDtlInfoMap.get("creditDateMax").toString());
        maxSbDatas[2] = (int) (Float.valueOf(proDtlInfoMap.get("free").toString()) * 100);
        showFree = Integer.parseInt(proDtlInfoMap.get("showFree").toString());
        visibleImage();//  判断是否显示一次性收费

        minSbDatas[3] = Integer.parseInt(proDtlInfoMap.get("loanDateMin").toString());
        maxSbDatas[3] = Integer.parseInt(proDtlInfoMap.get("loanDateMax").toString());
        minSbDatas[4] = Integer.parseInt(proInfoMap.get("ageMin").toString());
        maxSbDatas[4] = Integer.parseInt(proInfoMap.get("ageMax").toString());
        minSbDatas[5] = (int) (Float.valueOf(proDtlInfoMap.get("rateMin").toString()) * 100);
        maxSbDatas[5] = (int) (Float.valueOf(proDtlInfoMap.get("rateMax").toString()) * 100);
        maxSbDatas[6] = Integer.parseInt(proInfoMap.get("income").toString()) / 1000;//  数组的时候
        maxSbDatas[7] = Integer.parseInt(proInfoMap.get("workLimit").toString()) / 3;  //  在职时间是3的倍数
        initSeekBar();
        mEtDesc.setText(TextUtil.getTextToString(proInfoMap.get("descriptions").toString()));

        String wagesType = proInfoMap.get("wagesType").toString();//    工资发放类型
        if (wagesType.contains("打卡发放")) {
            isDakafafang = true;
        } else {
            isDakafafang = false;
        }
        if (wagesType.contains("现金发放")) {
            isXianJinFaFang = true;
        } else {
            isXianJinFaFang = false;
        }
        selectedStatue(isDakafafang, mTvDakafafang, mIvDakafafang);
        selectedStatue(isXianJinFaFang, mTvXianjinfafang, mIvXianjinfafang);

        if (proInfoMap.get("socialType").toString().equals("1")) {//    社保要求
            hasSheBao = true;
        } else {
            hasSheBao = false;
        }
        selectedStatue(hasSheBao, mTvYoushebao, mIvYoushebao);
        selectedStatue(!hasSheBao, mTvWushebao, mIvWushebao);

        if (proInfoMap.get("fundType").toString().equals("1")) {
            hasGongJiJin = true;
        } else {
            hasGongJiJin = false;
        }
        selectedStatue(hasGongJiJin, mTvJiaona, mIvJiaona);
        selectedStatue(!hasGongJiJin, mTvBujiaona, mIvBujiaona);

        switch (proDtlInfoMap.get("repayType").toString()) {
            case "1":
                setHuanKuanWay(1);
                break;
            case "2":
                setHuanKuanWay(2);
                break;
            case "3":
                setHuanKuanWay(3);
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gerenAlter_rl_city://   城市
                Intent intent = new Intent(getApplicationContext(), WD_SeverCityActivity.class);
                String cityName = "";
                if (!mTvCity.getText().toString().equals("服务城市"))
                    cityName = mTvCity.getText().toString();
                intent.putExtra("cityName", cityName);
                startActivityForResult(intent, ConstantUtils.WD_CITY);
                break;

            case R.id.gerenAlter_rl_zhiye:// 职业要求
                choicePosition = 0;
                intentMultChoiceActivity(Wd_CommonProduce.zhiweiTagData(), mTvZhiye, choicePosition);
                break;
            case R.id.gerenAlter_rl_xinyong:// 信用记录
                choicePosition = 1;
                intentMultChoiceActivity(Wd_CommonProduce.xinyongTagData(), mTvXinYong, choicePosition);
                break;
            case R.id.gerenAlter_rl_daikuanfangshi:// 贷款方式
                choicePosition = 2;
                intentMultChoiceActivity(Wd_CommonProduce.daikuanfangshiTagData(), mTvDaiKuanFangshi, choicePosition);
                break;
            case R.id.gerenAlter_rl_jiekunayongtu:// 借款用途
                choicePosition = 3;
                intentMultChoiceActivity(Wd_CommonProduce.jiekuanYongtuTagData(), mTvJieKuanYongtu, choicePosition);
                break;
            case R.id.gerenAlter_rl_wufafangkuan:// 无法放款行业
                choicePosition = 4;
                intentMultChoiceActivity(Wd_CommonProduce.wufafangkuanTagData(), mTvWuFaFandKuan, choicePosition);
                break;

            case R.id.gerenAlter_rl_dakafafang:
                isDakafafang = !isDakafafang;
                selectedStatue(isDakafafang, mTvDakafafang, mIvDakafafang);
                break;
            case R.id.gerenAlter_rl_xianjinfafang:
                isXianJinFaFang = !isXianJinFaFang;
                selectedStatue(isXianJinFaFang, mTvXianjinfafang, mIvXianjinfafang);
                break;
            case R.id.gerenAlter_rl_youshebao:
                hasSheBao = true;
                selectedStatue(hasSheBao, mTvYoushebao, mIvYoushebao);
                selectedStatue(!hasSheBao, mTvWushebao, mIvWushebao);
                break;
            case R.id.gerenAlter_rl_wushebao:
                hasSheBao = false;
                selectedStatue(hasSheBao, mTvYoushebao, mIvYoushebao);
                selectedStatue(!hasSheBao, mTvWushebao, mIvWushebao);
                break;
            case R.id.gerenAlter_rl_jiaona:
                hasGongJiJin = true;
                selectedStatue(hasGongJiJin, mTvJiaona, mIvJiaona);
                selectedStatue(!hasGongJiJin, mTvBujiaona, mIvBujiaona);
                break;
            case R.id.gerenAlter_rl_bujiaona:
                hasGongJiJin = false;
                selectedStatue(hasGongJiJin, mTvJiaona, mIvJiaona);
                selectedStatue(!hasGongJiJin, mTvBujiaona, mIvBujiaona);
                break;

            case R.id.gerenAlter_rl_benxi://  还款方式  本息
                setHuanKuanWay(1);
                break;
            case R.id.gerenAlter_rl_benjin://  还款方式  本金
                setHuanKuanWay(2);
                break;
            case R.id.gerenAlter_rl_xianxihouben://  还款方式    先息后本
                setHuanKuanWay(3);

                break;

            case R.id.gerenAlter_iv_visible:
                if (showFree == 0) {
                    showFree = 1;
                } else {
                    showFree = 0;
                }
                visibleImage();
                break;

            case R.id.gerenAlter_btn_commit:
                if (TextUtils.isEmpty(mEtProName.getText().toString().trim())) {
                    Toast.makeText(this, "请输入产品名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvCity.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvZhiye.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择职业要求", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvXinYong.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择信用记录", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvDaiKuanFangshi.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择贷款方式", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvJieKuanYongtu.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择借款用途", Toast.LENGTH_SHORT).show();
                    return;
                }
                String creditLimit;
                String creditWorkLimit;
                String wagesType = "";//  工资发放
                if (mTvWuFaFandKuan.getText().toString().equals("无")) {
                    creditLimit = "0";
                    creditWorkLimit = "";
                } else {
                    creditLimit = "1";
                    creditWorkLimit = mTvWuFaFandKuan.getText().toString();
                }
                addMap.put("productId", productId);//  编辑
                addMap.put("serviceType", 1 + "");//  对象   个人
                addMap.put("productName", mEtProName.getText().toString());
                addMap.put("includeCity", mTvCity.getText().toString());
                addMap.put("workType", mTvZhiye.getText().toString());
                addMap.put("trustType", mTvXinYong.getText().toString());
                addMap.put("assureType", mTvDaiKuanFangshi.getText().toString());
                addMap.put("loanUseType", mTvJieKuanYongtu.getText().toString());
                addMap.put("creditLimit", creditLimit);//  是否有限制行业
                addMap.put("creditWorkLimit", creditWorkLimit);//  限制的行业

                addMap.put("creditMin", minSbDatas[0] + "");//放款额度
                addMap.put("creditMax", maxSbDatas[0] + "");
                addMap.put("creditDateMin", minSbDatas[1] + "");// 放款时间   天
                addMap.put("creditDateMax", maxSbDatas[1] + "");
                addMap.put("free", maxSbDatas[2] / 100 + "");//  一次性收费
                addMap.put("loanDateMin", minSbDatas[3] + "");//  贷款期限
                addMap.put("loanDateMax", maxSbDatas[3] + "");
                addMap.put("ageMin", minSbDatas[4] + "");//  年龄要求
                addMap.put("ageMax", maxSbDatas[4] + "");
                addMap.put("rateMin", minSbDatas[5] / 100 + "");// 月利率范围
                addMap.put("rateMax", maxSbDatas[5] / 100 + "");
                addMap.put("income", maxSbDatas[6] * 1000 + "");//  月收入
                addMap.put("workLimit", maxSbDatas[7] + "");//   在职时间
                if (isDakafafang) {//  打卡发放
                    wagesType += mTvDakafafang.getText().toString();
                }
                if (isXianJinFaFang) {
                    if (!TextUtils.isEmpty(wagesType))
                        wagesType += ",";
                    wagesType += mTvXianjinfafang.getText().toString();
                }
                addMap.put("wagesType", wagesType);

                if (hasSheBao) {//  是否有无社保
                    addMap.put("socialType", "1");
                } else {
                    addMap.put("socialType", "0");
                }
                if (hasGongJiJin) {
                    addMap.put("fundType", "1");
                } else {
                    addMap.put("fundType", "0");
                }
                addMap.put("repayType", repayType + "");
                addMap.put("descriptions", mEtDesc.getText().toString());
                uploadDatas(Urls.WD_ALTER_PRODUCE);
                break;

        }
    }

    //  还款方式
    private void setHuanKuanWay(int choicePosition) {
        for (int i = 1; i <= huanKuanWay.length; i++) {
            if (i == choicePosition) {
                huanKuanWay[i - 1] = true;
                repayType = choicePosition;
            } else {
                huanKuanWay[i - 1] = false;
            }
            if (huanKuanWay[i - 1]) {
                mTvList.get(i - 1).setSelected(true);
                mIvList.get(i - 1).setVisibility(View.VISIBLE);
            } else {
                mTvList.get(i - 1).setSelected(false);
                mIvList.get(i - 1).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示
     */
    private void visibleImage() {
        if (showFree == 1) {
            mIvVisible.setImageResource(R.mipmap.gouxuan1);
        } else {
            mIvVisible.setImageResource(R.mipmap.weigouxuan1);
        }
    }

    //  上传用户信息
    private void uploadDatas(String url) {
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.putAll(addMap);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtil.dismissProgress();
                        Toast.makeText(WD_GeRenAlterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
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
    private void selectedStatue(boolean isSelected, TextView textView, ImageView imageView) {
        if (isSelected) {
            textView.setTextColor(Color.parseColor("#f89169"));
            imageView.setVisibility(View.VISIBLE);
            textView.setBackgroundResource(R.drawable.layout_orange_line);
        } else {
            textView.setTextColor(getResources().getColor(R.color.word_gray_9a9a9a));
            textView.setBackgroundResource(R.drawable.layout_wd_gray_line);
            imageView.setVisibility(View.GONE);
        }
    }

    /**
     * 多选  标签
     *
     * @param tagList
     */
    private void intentMultChoiceActivity(List<String> tagList, TextView textView, int position) {
        Intent intent = new Intent(getApplicationContext(), WD_MultChoiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tag", (Serializable) tagList);
        if (!(textView.getText().toString().equals("请选择") || textView.getText().toString().equals("无"))) {
            bundle.putString("tagName", textView.getText().toString());
        } else {
            bundle.putString("tagName", "");
        }
        if (position == 0) {
            bundle.putString("titleName", "职业要求");
        } else if (position == 1) {
            bundle.putString("titleName", "信用记录");
        } else if (position == 2) {
            bundle.putString("titleName", "贷款方式");
        } else if (position == 3) {
            bundle.putString("titleName", "借款用途");
        } else if (position == 4) {
            bundle.putString("titleName", "无法放款行业");
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, position);
    }

    private void getIntentDatas() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        map = (Map<String, Object>) bundle.getSerializable("map");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            String tagContain = data.getStringExtra("tagContain");
            if (tagContain.equals("")) {
                tagContain = "请选择";
                if (requestCode == 4)
                    tagContain = "无";
            }
            switch (requestCode) {
                case ConstantUtils.WD_CITY:
                    mTvCity.setText(data.getStringExtra("cityname"));
                    break;
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
}
