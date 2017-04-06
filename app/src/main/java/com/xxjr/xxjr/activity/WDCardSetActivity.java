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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.UploadReturnDataEven;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.image.imageReduce.ImageCompressActivity;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.CommonUploadPicture;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

public class WDCardSetActivity extends SlidBackActivity  implements View.OnClickListener {

    private EditText mEtCompany, mEtPositon,mEtWX;
    private ImageView mIvHead;
    private EditText mEtName, mEtTel, mEtDesc;
    private  CommonUploadPicture commonUploadPicture = new CommonUploadPicture();
    private String imageUrl;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_wdcard_set);
        SetTitleBar.setTitleText(this, "设置名片", "保存");
        initViews();
        setListener();
        boolean isFirstSetCard = getIntent().getBooleanExtra(ConstantUtils.WDCARD_ISFIRST_WD_CARD,false);
        if (isFirstSetCard){
            downloadDatas(Urls.WD_CARD_SETTING_QUERY_FIRSER);
        }else {
            downloadDatas(Urls.WD_CARD_SETTING_QUERY);
        }

    }

    //   回调
    public void onEventMainThread(UploadReturnDataEven even){
        imageUrl = even.getSingleImg();
        MyApplication.imageLoader.displayImage(
                Urls.RANQING+Urls.BUSSINESS_CUSTCARD_HEADICON+imageUrl,mIvHead,MyApplication.optionsHead);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initViews() {
        mIvHead = (ImageView) findViewById(R.id.wdcardset_iv_head);
        mEtName = (EditText) findViewById(R.id.wdcardset_et_name);
        mEtTel = (EditText) findViewById(R.id.wdcardset_et_tel);
        mEtCompany = (EditText) findViewById(R.id.wdcardset_et_city);
        mEtPositon = (EditText) findViewById(R.id.wdcardset_et_position);
        mEtWX = (EditText) findViewById(R.id.wdcardset_tv_wx);
        mEtDesc = (EditText) findViewById(R.id.wdcardset_et_desc);
    }

    private void setListener() {
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(this);//  标题的保存按钮
        mIvHead.setOnClickListener(this);
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
                            Toast.makeText(WDCardSetActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
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

        //todo  参数改变
        imageUrl = TextUtil.getTextToString(attrMap.get("headImgUrl"));
        MyApplication.imageLoader.displayImage(Urls.RANQING + Urls.BUSSINESS_CUSTCARD_HEADICON + imageUrl,
                mIvHead, MyApplication.optionsHead);
        if (attrMap.get("custName")!=null)
            mEtName.setText(attrMap.get("custName").toString());
        if (attrMap.get("telephone")!=null)
            mEtTel.setText(attrMap.get("telephone").toString());
        if (attrMap.get("company")!=null)
            mEtCompany.setText(attrMap.get("company").toString());
        if (attrMap.get("job")!=null)
            mEtPositon.setText(attrMap.get("job").toString());
        if (attrMap.get("wxNum")!=null)
            mEtWX.setText(attrMap.get("wxNum").toString());
        if (attrMap.get("custDesc")!=null)
            mEtDesc.setText(attrMap.get("custDesc").toString());
    }

    private void alterwdcardsetData(String url){
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        if (imageUrl == null) {
            Toast.makeText(this, "请上传头像", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(mEtName.getText().toString().trim())) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(mEtTel.getText().toString().trim())) {
            Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(mEtCompany.getText().toString().trim())) {
            Toast.makeText(this, "请输入公司名称", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(mEtPositon.getText().toString().trim())) {
            Toast.makeText(this, "请输入职位名称", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("custName", mEtName.getText().toString());
        params.put("telephone", mEtTel.getText().toString());
        params.put("company", mEtCompany.getText().toString());
        params.put("job", mEtPositon.getText().toString());
        params.put("weixin", mEtWX.getText().toString());
        params.put("custDesc", mEtDesc.getText().toString());
        params.put("headImgUrl", imageUrl);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }

    private void getJson(JSONObject response){
        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
        if ((Boolean) map.get("success")) {
            Toast.makeText(WDCardSetActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(WDCardSetActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
        }
        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ThreeTitleBar_ll_click:
                alterwdcardsetData(Urls.WD_CARD_SETTING);
                break;
            case R.id.wdcardset_iv_head:
                commonUploadPicture.setDialogPictur(this);
                break;
        }
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
                    intent.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_WDCard_HEAD);
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
                    intent1.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_WDCard_HEAD);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
