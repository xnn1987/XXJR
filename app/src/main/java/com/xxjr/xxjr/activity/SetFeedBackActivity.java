package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.UploadReturnDataEven;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.image.imageReduce.ImageCompressActivity;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.CommonUploadPicture;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

public class SetFeedBackActivity extends SlidBackActivity implements View.OnClickListener {

    private CommonUploadPicture commonUploadPicture = new CommonUploadPicture();
    private Button mBtnCommit;
    private LinearLayout mLlUploadPic;
    private ImageView mIvUploadPic;
    private String pictureUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_set_feedback);
        SetTitleBar.setTitleText(SetFeedBackActivity.this, "设置");
        initViews();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //   回调
    public void onEventMainThread(UploadReturnDataEven even){
        pictureUrl = even.getSingleImg();
        MyApplication.imageLoader.displayImage(Urls.RANQING+Urls.BUSSINESS_CUSTCARD_HEADICON+pictureUrl+"&uid="+MyApplication.uid+"&UUID="+MyApplication.device,
                mIvUploadPic,MyApplication.options);
    }

    private void setListener() {
        mBtnCommit.setOnClickListener(this);
        mLlUploadPic.setOnClickListener(this);

    }

    private void initViews() {
        mBtnCommit = (Button) findViewById(R.id.SettingFeedBack_btn_Commit);

        mLlUploadPic = (LinearLayout) findViewById(R.id.Feedback_ll_uploadPicture);
        mIvUploadPic = (ImageView) findViewById(R.id.Feedback_iv_uploading);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SettingFeedBack_btn_Commit:
                EditText backContentText = (EditText) this.findViewById(R.id.cust_feedback_content);
                String feedbackContent = backContentText.getText().toString();
                if (TextUtils.isEmpty(feedbackContent)) {
                    Toast.makeText(getApplication(), "请输入反馈内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                AppUtil.showProgress(this, ConstantUtils.DIALOG_UPLOADING);
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("content", feedbackContent);
                httpRequestUtil.params.put("url", pictureUrl);
                httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/feedback", new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean) map.get("success");
                                if (success) {
                                    DebugLog.Toast(SetFeedBackActivity.this,"感谢您的意见反馈");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    },500);
                                } else {
                                    String message = map.get("message").toString();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                                AppUtil.dismissProgress();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AppUtil.errodDoanload("网络不稳定");
                            }
                        });

                break;
            case R.id.Feedback_ll_uploadPicture:
                commonUploadPicture.setDialogPictur(this);
                break;


        }
    }



    //返回值上传相对应图片
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
                    intent.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_FEEDBACK);
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
                    intent1.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_FEEDBACK);
                    startActivity(intent1);
                    break;
            }
        }
    }

}
