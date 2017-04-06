package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.SingleCityEvenbus;
import com.xxjr.xxjr.bean.UploadReturnDataEven;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.image.imageCircle.CircleImage;
import com.xxjr.xxjr.other.image.imageReduce.ImageCompressActivity;
import com.xxjr.xxjr.other.timePicker.NumberPicker;
import com.xxjr.xxjr.other.timePicker.OptionPicker;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.CommonUploadPicture;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

public class WDCardActivity extends SlidBackActivity  implements View.OnClickListener {
    private TextView mTvCity, mTvTime;
    private CircleImage mIvHead;
    private EditText mEtDianpuName, mEtCompanyName,mEtConstruct;
    private boolean isAdd;
    private String workLimt;
    private  CommonUploadPicture commonUploadPicture = new CommonUploadPicture();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.LOADING:
                    AppUtil.showProgress(WDCardActivity.this,ConstantUtils.DIALOG_SHOW);
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    break;
                case ConstantUtils.LOAD_SUCCESS_WEIXIN:
                    AppUtil.dismissProgress();

                    break;


            }
        }
    };
    private String imageUrl;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_wdcard);
        getIntenteDatas();
        SetTitleBar.setTitleText(this, "微店名片", "保存");
        initViews();
        setListener();
        if (isAdd){
            downloadDatas(Urls.WD_ALTER_NO_HAVE_WD);
        }else {
            downloadDatas(Urls.WD_ALTER_HAVE_WD);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //   回调
    public void onEventMainThread(UploadReturnDataEven even){
        imageUrl = even.getSingleImg();
        MyApplication.imageLoader.displayImage(Urls.RANQING+Urls.BUSSINESS_CUSTCARD_HEADICON+imageUrl
                ,mIvHead,MyApplication.optionsHead);
    }

    private void initViews() {
        mIvHead = (CircleImage) findViewById(R.id.wdcard_iv_head);
        mEtDianpuName = (EditText) findViewById(R.id.wdcard_et_dianpuname);
        mEtCompanyName = (EditText) findViewById(R.id.wdcard_et_companyname);
        mEtConstruct = (EditText) findViewById(R.id.wdcard_et_city);
        mTvCity = (TextView) findViewById(R.id.wdcard_tv_city);
        mTvTime = (TextView) findViewById(R.id.wdcard_tv_time);
    }

    private void setListener() {
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(this);//  标题的保存按钮
        findViewById(R.id.wdcard_rl_headicono).setOnClickListener(this);
        findViewById(R.id.wdcard_rl_city).setOnClickListener(this);
        findViewById(R.id.wdcard_rl_time).setOnClickListener(this);
    }


    private void downloadDatas(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
                            setData(attrMap);
                        } else {
                            Toast.makeText(WDCardActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }
    //  初始化控件数据
    private void setData(Map<String,Object> attrMap){
        MyApplication.imageLoader.displayImage(Urls.RANQING + Urls.BUSSINESS_CUSTCARD_HEADICON + attrMap.get("headImage"),
                mIvHead, MyApplication.optionsHead);
        if (attrMap.get("shopName")!=null)
            mEtDianpuName.setText(attrMap.get("shopName").toString());
        if (attrMap.get("orgName")!=null)
             mEtCompanyName.setText(attrMap.get("orgName").toString());
        if (attrMap.get("cityName")!=null)
            mTvCity.setText(attrMap.get("cityName").toString());
        if (attrMap.get("headImage")!=null)
            imageUrl = attrMap.get("headImage").toString();
        //  上面包含第一次初始化(没有创建微店的情况)   下面的创建微店多出来的
        if (!isAdd){
            if (attrMap.get("workLimit")!=null) {
                workLimt = attrMap.get("workLimit").toString();
                mTvTime.setText(workLimt + "年");
            }

            mEtConstruct.setText(attrMap.get("shopDesc").toString());
        }
    }

    private void alterWDCardData(String url){
        if (imageUrl == null) {
            Toast.makeText(this, "请上传头像", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(mEtDianpuName.getText().toString().trim())) {
            Toast.makeText(this, "请输入店铺名", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(mEtCompanyName.getText().toString().trim())) {
            Toast.makeText(this, "请输入机构名", Toast.LENGTH_SHORT).show();
            return;
        }else if (mTvCity.getText().toString().trim().equals("选择")) {
            Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
            return;
        }else if (mTvTime.getText().toString().trim().equals("选择")) {
            Toast.makeText(this, "请选择入行时间", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(mEtConstruct.getText().toString().trim())) {
            Toast.makeText(this, "请输入微店描述", Toast.LENGTH_SHORT).show();
            return;
        }
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("shopName",mEtDianpuName.getText().toString());
        params.put("orgName",mEtCompanyName.getText().toString());
        params.put("cityName",mTvCity.getText().toString());
        params.put("shopDesc", mEtConstruct.getText().toString());
        params.put("headImage", imageUrl);
        params.put("workLimit", workLimt);
        params.put("isAdd", isAdd + "");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Toast.makeText(WDCardActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            if (from.equals(ConstantUtils.WDCARD_FROM_ACTIVITY)) {
                                Intent intent = new Intent(getApplicationContext(), WDActivity02.class);
                                startActivity(intent);
                            }
                            finish();
                            MyApplication.haveWd=true;
                            MyApplication.isChangeWDCardActivity = true;
                        } else {
                            Toast.makeText(WDCardActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ThreeTitleBar_ll_click:
                alterWDCardData(Urls.WD_ALTER_CARD);
                break;
            case R.id.wdcard_rl_headicono:
                commonUploadPicture.setDialogPictur(this);
                break;
            case R.id.wdcard_rl_city:
                Intent intent  = new Intent(getApplicationContext(),CityActivity.class);
                intent.putExtra("fromFlag", "WDCardActivity");
                startActivity(intent);
                break;
            case R.id.wdcard_rl_time:
                NumberPicker picker = new NumberPicker(this);
                picker.setOffset(2);//偏移量
                picker.setRange(0, 20);//数字范围
                picker.setSelectedItem(3);
                picker.setLabel("年");
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {


                    @Override
                    public void onOptionPicked(String option) {
                        workLimt = option;
                        mTvTime.setText(option+"年");
                    }
                });
                picker.show();
                break;
        }
    }

    public void onEventMainThread(SingleCityEvenbus singleCityEvenbus){
        mTvCity.setText(singleCityEvenbus.getCityName());
    }

    //返回值上传相对应图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2://相機
                    String takePhtotImg = commonUploadPicture.getTakePhtotImg();
                    Intent intent = new Intent(getApplicationContext(), ImageCompressActivity.class);
                    intent.putExtra(ConstantUtils.UPLOAD_PHOTO,takePhtotImg);
                    intent.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_WD_HEAD);
                    startActivity(intent);
                    break;
                case 1://圖片
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columIndext = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columIndext);
                    cursor.close();

                    Intent intent1 = new Intent(getApplicationContext(), ImageCompressActivity.class);
                    intent1.putExtra(ConstantUtils.UPLOAD_PHOTO,picturePath);
                    intent1.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_WD_HEAD);
                    startActivity(intent1);
                    break;
            }
        }
    }

    private void getIntenteDatas() {
        Intent intent = getIntent();
        isAdd = intent.getBooleanExtra("isAdd",false);
        from = intent.getStringExtra(ConstantUtils.WDCARD_FROM);//  判断是从fm过来还是acitivity过来
    }
}
