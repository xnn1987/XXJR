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

public class WD_QiYeAlterActivity extends CommomAcitivity implements View.OnClickListener {

    private TextView mTvCity, mTvQiYeType, mTvDanBaoWay, mTvWuFaFandKuan;
    private Map<String, String> addMap = new HashMap<>();
    private List<MaterialRangeSlider> mSbLists = new ArrayList<MaterialRangeSlider>();
    private List<TextView> mTvLists = new ArrayList<>();
    private MaterialRangeSlider mSbDuiGong, mSbDuiSi, mSbYingYeTime, mSbErDu, mSbTime, mSbYiCiXing, mSbLimit, mSbYueLiLv;
    private TextView mTvDuiGong, mTvDuiSi, mTvYingYeTime, mTvErDu, mTvTime, mTvYiCiXing, mTvLimit, mTvYueLiLv;
    private int clickPostion = 0;
    private int[] minSbDatas = {1, 1, 1, 1, 1, 0, 1, 0};
    private int[] maxSbDatas = {100, 100, 40, 200, 60, 500, 12, 400};
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
        setContentView(R.layout.activity_wd__qi_ye_alter);
        SetTitleBar.setTitleText(WD_QiYeAlterActivity.this, "编辑信贷产品-企业");
        initViews();
        setListener();
        getIntentDatas();
        setViewDatas(map);
    }

    private void initViews() {
        mSbDuiGong = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_duigong);
        mSbDuiSi = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_duisi);
        mSbYingYeTime = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_yingyeTime);
        mSbErDu = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_erdu);
        mSbTime = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_time);
        mSbYiCiXing = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_yicixing);
        mSbLimit = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_limt);
        mSbYueLiLv = (MaterialRangeSlider) findViewById(R.id.qiyeAlter_sb_yuelilv);
        mTvDuiGong = (TextView) findViewById(R.id.qiyeAlter_tv_duigong);
        mTvDuiSi = (TextView) findViewById(R.id.qiyeAlter_tv_duisi);
        mTvYingYeTime = (TextView) findViewById(R.id.qiyeAlter_tv_yingyeTime);
        mTvErDu = (TextView) findViewById(R.id.qiyeAlter_tv_erdu);
        mTvTime = (TextView) findViewById(R.id.qiyeAlter_tv_time);
        mTvYiCiXing = (TextView) findViewById(R.id.qiyeAlter_tv_yicixing);
        mTvLimit = (TextView) findViewById(R.id.qiyeAlter_tv_limt);
        mTvYueLiLv = (TextView) findViewById(R.id.qiyeAlter_tv_yuelilv);
        mSbLists.add(mSbDuiGong);
        mSbLists.add(mSbDuiSi);
        mSbLists.add(mSbYingYeTime);
        mSbLists.add(mSbErDu);
        mSbLists.add(mSbTime);
        mSbLists.add(mSbYiCiXing);
        mSbLists.add(mSbLimit);
        mSbLists.add(mSbYueLiLv);

        mTvLists.add(mTvDuiGong);
        mTvLists.add(mTvDuiSi);
        mTvLists.add(mTvYingYeTime);
        mTvLists.add(mTvErDu);
        mTvLists.add(mTvTime);
        mTvLists.add(mTvYiCiXing);
        mTvLists.add(mTvLimit);
        mTvLists.add(mTvYueLiLv);

        mTvCity = (TextView) findViewById(R.id.qiyeAlter_tv_city);
        mTvQiYeType = (TextView) findViewById(R.id.qiyeAlter_tv_qiyeType);
        mTvDanBaoWay = (TextView) findViewById(R.id.qiyeAlter_tv_danbaoWay);
        mTvWuFaFandKuan = (TextView) findViewById(R.id.qiyeAlter_tv_wufafangkuan);

        mTvDengErBenXi = (TextView) findViewById(R.id.qiyeAlter_tv_benxi);
        mTvDengErBenJin = (TextView) findViewById(R.id.qiyeAlter_tv_benjin);
        mTvXianXiHouBen = (TextView) findViewById(R.id.qiyeAlter_tv_xianxihouben);
        mIvDengErBenXi = (ImageView) findViewById(R.id.qiyeAlter_iv_benxi);
        mIvBenjin = (ImageView) findViewById(R.id.qiyeAlter_iv_benjin);
        mIvXianxihouben = (ImageView) findViewById(R.id.qiyeAlter_iv_xianxihouben);
        mIvList.add(mIvDengErBenXi);
        mIvList.add(mIvBenjin);
        mIvList.add(mIvXianxihouben);
        mTvList.add(mTvDengErBenXi);
        mTvList.add(mTvDengErBenJin);
        mTvList.add(mTvXianXiHouBen);

        mEtProName = (EditText) findViewById(R.id.qiyeAlter_tv_ProName);
        mEtDesc = (EditText) findViewById(R.id.qiyeAlter_et_desc);
        mIvVisible = (ImageView) findViewById(R.id.qiyeAlter_iv_visible);
    }

    private void initSeekBar() {
        setSeekBarInitDatas(mSbDuiGong, 1, 100, minSbDatas[0], maxSbDatas[0]);
        setSeekBarInitDatas(mSbDuiSi, 1, 100, minSbDatas[1], maxSbDatas[1]);
        setSeekBarInitDatas(mSbYingYeTime, 1, 40, minSbDatas[2], maxSbDatas[2]);
        setSeekBarInitDatas(mSbErDu, 1, 200, minSbDatas[3], maxSbDatas[3]);
        setSeekBarInitDatas(mSbTime, 1, 60, minSbDatas[4], maxSbDatas[4]);
        setSeekBarInitDatas(mSbYiCiXing, 0, 500, minSbDatas[5], maxSbDatas[5]);
        setSeekBarInitDatas(mSbLimit, 1, 12, minSbDatas[6], maxSbDatas[6]);
        setSeekBarInitDatas(mSbYueLiLv, 0, 400, minSbDatas[7], maxSbDatas[7]);
    }

    private void setListener() {
        findViewById(R.id.qiyeAlter_rl_ProName).setOnClickListener(this);
        findViewById(R.id.qiyeAlter_rl_city).setOnClickListener(this);
        findViewById(R.id.qiyeAlter_rl_daikuanfangshi).setOnClickListener(this);
        findViewById(R.id.qiyeAlter_rl_jiekunayongtu).setOnClickListener(this);
        findViewById(R.id.qiyeAlter_rl_wufafangkuan).setOnClickListener(this);
        findViewById(R.id.qiyeAlter_rl_benxi).setOnClickListener(this);
        findViewById(R.id.qiyeAlter_rl_benjin).setOnClickListener(this);
        findViewById(R.id.qiyeAlter_rl_xianxihouben).setOnClickListener(this);
        mIvVisible.setOnClickListener(this);
        findViewById(R.id.qiyeAlter_btn_commit).setOnClickListener(this);//提交

        for (int i = 0; i < mSbLists.size(); i++) {
            final int click = i;
            mSbLists.get(i).setRangeSliderListener(new MaterialRangeSlider.RangeSliderListener() {
                @Override
                public void onMinChanged(int newValue) {
                    minSbDatas[click] = newValue;
                    clickPostion = click;
                    if (clickPostion == 3) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "万-" + maxSbDatas[clickPostion] + "万");
                    } else if (clickPostion == 4) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "天-" + maxSbDatas[clickPostion] + "天");
                    } else if (clickPostion == 6) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "月-" + maxSbDatas[clickPostion] + "月");
                    } else if (clickPostion == 7) {
                        mTvLists.get(clickPostion).setText((float) minSbDatas[clickPostion] / 100 + "%-" + (float) maxSbDatas[clickPostion] / 100 + "%");
                    }
                }

                @Override
                public void onMaxChanged(int newValue) {
                    maxSbDatas[click] = newValue;
                    clickPostion = click;
                    if (clickPostion == 0) {
                        mTvLists.get(clickPostion).setText(maxSbDatas[clickPostion] + "万");
                    } else if (clickPostion == 1) {
                        mTvLists.get(clickPostion).setText(maxSbDatas[clickPostion] + "万");
                    } else if (clickPostion == 2) {
                        mTvLists.get(clickPostion).setText(maxSbDatas[clickPostion] + "万");
                    } else if (clickPostion == 3) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "万-" + maxSbDatas[clickPostion] + "万");
                    } else if (clickPostion == 4) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "天-" + maxSbDatas[clickPostion] + "天");
                    } else if (clickPostion == 5) {
                        mTvLists.get(clickPostion).setText((float) maxSbDatas[clickPostion] / 100 + "%");
                    } else if (clickPostion == 6) {
                        mTvLists.get(clickPostion).setText(minSbDatas[clickPostion] + "月-" + maxSbDatas[clickPostion] + "月");
                    } else if (clickPostion == 7) {
                        mTvLists.get(clickPostion).setText((float) minSbDatas[clickPostion] / 100 + "%-" + (float) maxSbDatas[clickPostion] / 100 + "%");
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
        mTvQiYeType.setText(TextUtil.getTextToString(proInfoMap.get("companyType")));
        mTvDanBaoWay.setText(TextUtil.getTextToString(proInfoMap.get("assureType")));
        mTvWuFaFandKuan.setText(TextUtil.getTextToString(proInfoMap.get("creditWorkLimit")));

        maxSbDatas[0] = Integer.parseInt(TextUtil.getTextToString(proInfoMap.get("pubAmount")));
        maxSbDatas[1] = Integer.parseInt(TextUtil.getTextToString(proInfoMap.get("priAmount")));
        maxSbDatas[2] = Integer.parseInt(TextUtil.getTextToString(proInfoMap.get("kbisLimit")));
        showFree = Integer.parseInt(TextUtil.getTextToString(proDtlInfoMap.get("showFree")));
        visibleImage();//  判断是否显示一次性收费

        minSbDatas[3] = Integer.parseInt(proDtlInfoMap.get("creditMin").toString());
        maxSbDatas[3] = Integer.parseInt(proDtlInfoMap.get("creditMax").toString());
        minSbDatas[4] = Integer.parseInt(proDtlInfoMap.get("creditDateMin").toString());
        maxSbDatas[4] = Integer.parseInt(proDtlInfoMap.get("creditDateMax").toString());
        maxSbDatas[5] = (int) (Float.valueOf(proDtlInfoMap.get("free").toString()) * 100);
        minSbDatas[6] = Integer.parseInt(proDtlInfoMap.get("loanDateMin").toString());//  贷款期限
        maxSbDatas[6] = Integer.parseInt(proDtlInfoMap.get("loanDateMax").toString());//  贷款期限
        minSbDatas[7] = (int) (Float.valueOf(proDtlInfoMap.get("rateMin").toString()) * 100);//  月利率
        maxSbDatas[7] = (int) (Float.valueOf(proDtlInfoMap.get("rateMax").toString()) * 100);
        initSeekBar();
        mEtDesc.setText(TextUtil.getTextToString(proInfoMap.get("descriptions").toString()));

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
            case R.id.qiyeAlter_rl_city://   城市
                Intent intent = new Intent(getApplicationContext(), WD_SeverCityActivity.class);
                String cityName = "";
                if (!mTvCity.getText().toString().equals("服务城市"))
                    cityName = mTvCity.getText().toString();
                intent.putExtra("cityName", cityName);
                startActivityForResult(intent, ConstantUtils.WD_CITY);
                break;

            case R.id.qiyeAlter_rl_daikuanfangshi:// 贷款方式
                choicePosition = 2;
                intentMultChoiceActivity(Wd_CommonProduce.daikuanfangshiTagData(), mTvQiYeType, choicePosition);
                break;
            case R.id.qiyeAlter_rl_jiekunayongtu:// 借款用途
                choicePosition = 3;
                intentMultChoiceActivity(Wd_CommonProduce.jiekuanYongtuTagData(), mTvDanBaoWay, choicePosition);
                break;
            case R.id.qiyeAlter_rl_wufafangkuan:// 无法放款行业
                choicePosition = 4;
                intentMultChoiceActivity(Wd_CommonProduce.wufafangkuanTagData(), mTvWuFaFandKuan, choicePosition);
                break;
            case R.id.qiyeAlter_rl_benxi://  还款方式  本息
                setHuanKuanWay(1);
                break;
            case R.id.qiyeAlter_rl_benjin://  还款方式  本金
                setHuanKuanWay(2);
                break;
            case R.id.qiyeAlter_rl_xianxihouben://  还款方式    先息后本
                setHuanKuanWay(3);

                break;

            case R.id.qiyeAlter_iv_visible:
                if (showFree == 0) {
                    showFree = 1;
                } else {
                    showFree = 0;
                }
                visibleImage();
                break;

            case R.id.qiyeAlter_btn_commit:
                if (TextUtils.isEmpty(mEtProName.getText().toString().trim())) {
                    Toast.makeText(this, "请输入产品名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvCity.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvCity.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择服务城市", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvQiYeType.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择企业类型", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mTvDanBaoWay.getText().toString().equals("请选择")) {
                    Toast.makeText(this, "请选择担保方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                String creditLimit;
                String creditWorkLimit;
                if (mTvWuFaFandKuan.getText().toString().equals("无")) {
                    creditLimit = "0";
                    creditWorkLimit = "";
                } else {
                    creditLimit = "1";
                    creditWorkLimit = mTvWuFaFandKuan.getText().toString();
                }
                addMap.put("productId", productId);//  产品id
                addMap.put("serviceType", 2 + "");//  对象   个人
                addMap.put("productName", mEtProName.getText().toString());
                addMap.put("includeCity", mTvCity.getText().toString());
                addMap.put("companyType", mTvQiYeType.getText().toString());
                addMap.put("assureType", mTvDanBaoWay.getText().toString());
                addMap.put("creditLimit", creditLimit);//  是否有限制行业
                addMap.put("creditWorkLimit", creditWorkLimit);//  限制的行业

                addMap.put("pubAmount", maxSbDatas[0] + "");//  对公流水
                addMap.put("priAmount", maxSbDatas[1] + "");//   对私流水
                addMap.put("kbisLimit", maxSbDatas[2] + "");//  营业时长

                addMap.put("creditMin", minSbDatas[3] + "");//放款额度
                addMap.put("creditMax", maxSbDatas[3] + "");
                addMap.put("creditDateMin", minSbDatas[4] + "");// 放款时间   天
                addMap.put("creditDateMax", maxSbDatas[4] + "");
                addMap.put("free", maxSbDatas[5] / 100 + "");//  一次性收费
                addMap.put("loanDateMin", minSbDatas[6] + "");//  贷款期限
                addMap.put("loanDateMax", maxSbDatas[6] + "");
                addMap.put("rateMin", minSbDatas[7] / 100 + "");// 月利率范围
                addMap.put("rateMax", maxSbDatas[7] / 100 + "");
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
                        Toast.makeText(WD_QiYeAlterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
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
                    break;
                case 1:
                    break;
                case 2:
                    mTvQiYeType.setText(tagContain);
                    break;
                case 3:
                    mTvDanBaoWay.setText(tagContain);
                    break;
                case 4:
                    mTvWuFaFandKuan.setText(tagContain);
                    break;
            }
        }
    }
}
