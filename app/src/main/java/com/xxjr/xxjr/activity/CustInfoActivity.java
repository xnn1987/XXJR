package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.UploadReturnDataEven;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.image.imageCircle.SelectableRoundedImageView;
import com.xxjr.xxjr.other.image.imageReduce.ImageCompressActivity;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.Shared.SocialUmeng;
import com.xxjr.xxjr.utils.CommonUploadPicture;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class CustInfoActivity extends SlidBackActivity implements View.OnClickListener {
    private RelativeLayout CustInfo_rl_eMail = null;
    private RelativeLayout CustInfo_rl_Tel = null;
    private RelativeLayout CustInfo_rl_sex = null;
    private boolean haveEmail = false;
    private boolean haveRealName = true;
    private RelativeLayout CustInfo_rl_nicknames = null;
    private RelativeLayout CustInfo_rl_PersonDetail = null;
    private RelativeLayout CustInfo_rl_Commony = null;
    private String email = null;
    private RelativeLayout mRlCity;
    private RelativeLayout mRlRealName, mRlHeadIcon,mRlWeiXin;
    private SelectableRoundedImageView mIvHeadImg;
    private ImageView mIvReadNameCome;
    private TextView mTvWeixin;
    private CommonUploadPicture commonUploadPicture = new CommonUploadPicture();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    break;
                case ConstantUtils.LOAD_SUCCESS_WEIXIN:
                    AppUtil.dismissProgress();
                    if (MyApplication.userInfo.get("wxNickName") != null) {
                        mTvWeixin.setText(MyApplication.userInfo.get("wxNickName").toString());
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().unregister(this);
        setContentView(R.layout.activity_cust_info);
        SetTitleBar.setTitleText(CustInfoActivity.this, "个人信息");
        initView();
        SetListener();
        downloading();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            downloading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //   回调
    public void onEventMainThread(UploadReturnDataEven even){
        downloading();
    }

    private void initView() {
        mRlRealName = (RelativeLayout) findViewById(R.id.CustInfo_rl_realname);//实名
        mRlHeadIcon = (RelativeLayout) findViewById(R.id.CustInfo_rl_headIcon);
        mIvHeadImg = (SelectableRoundedImageView) CustInfoActivity.this.findViewById(R.id.custInfo_cust_head_img);
        mIvReadNameCome = (ImageView) findViewById(R.id.CustInfo_img_to_nick_name);
        mRlWeiXin = (RelativeLayout) findViewById(R.id.CustInfo_rl_weixin);
        mTvWeixin = (TextView) findViewById(R.id.cust_nick_weixin);

        CustInfo_rl_eMail = (RelativeLayout) this.findViewById(R.id.CustInfo_rl_eMail);
        CustInfo_rl_Tel = (RelativeLayout) this.findViewById(R.id.CustInfo_rl_Tel);
        CustInfo_rl_sex = (RelativeLayout) this.findViewById(R.id.CustInfo_rl_sex);
        CustInfo_rl_nicknames = (RelativeLayout) this.findViewById(R.id.CustInfo_rl_nicknames);
        CustInfo_rl_PersonDetail = (RelativeLayout) this.findViewById(R.id.CustInfo_rl_PersonDetail);

        CustInfo_rl_Commony = (RelativeLayout) findViewById(R.id.CustInfo_rl_Commony);
        mRlCity = (RelativeLayout) findViewById(R.id.CustInfo_rl_city);//城市
    }

    private void SetListener() {
        mRlRealName.setOnClickListener(this);//改 wqg
        mRlWeiXin.setOnClickListener(this);

        CustInfo_rl_eMail.setOnClickListener(this);
        CustInfo_rl_Tel.setOnClickListener(this);
        CustInfo_rl_sex.setOnClickListener(this);
        CustInfo_rl_nicknames.setOnClickListener(this);
        CustInfo_rl_PersonDetail.setOnClickListener(this);
        CustInfo_rl_Commony.setOnClickListener(this);
        mRlCity.setOnClickListener(this);
        mRlHeadIcon.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.CustInfo_rl_headIcon:
                commonUploadPicture.setDialogPictur(this);
                break;
            case R.id.CustInfo_rl_eMail:
                if (haveEmail) {
                    intent.putExtra("email", email);
                    intent.setClass(getApplicationContext(), ChangeEmailActivity.class);
                } else {
                    intent.setClass(getApplicationContext(), InsertNewEmailActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.CustInfo_rl_Tel:
                intent.setClass(getApplicationContext(), ModifyTelPwdActivity.class);
                startActivity(intent);
                break;
           case R.id.CustInfo_rl_sex:
                intent.setClass(getApplicationContext(), CustSexActivity.class);
                TextView sex = (TextView) this.findViewById(R.id.cust_sex);
                if (sex.getText().toString().equals("女")) {
                    intent.putExtra("sex", "0");
                } else {
                    intent.putExtra("sex", "1");
                }
                startActivity(intent);
                break;
             case R.id.CustInfo_rl_realname:
                if (haveRealName) {
                    intent.setClass(getApplicationContext(), CustRealNameActivity.class);
                    startActivity(intent);
                }
                break;
           case R.id.CustInfo_rl_nicknames:
                intent.setClass(getApplicationContext(), CustNickNameActivity.class);
                startActivity(intent);
                break;
             case R.id.CustInfo_rl_weixin:
                 if (MyApplication.userInfo.get("wxNickName") != null) {
                     intent.setClass(getApplicationContext(), AlterWeixinActivity.class);
                     startActivity(intent);
                 }else {
                     SocialUmeng.threeLogin(this, handler);
                 }
                 break;
           case R.id.CustInfo_rl_Commony:
                intent.setClass(getApplicationContext(), CustOrgNameActivity.class);
                startActivity(intent);
                break;
             case R.id.CustInfo_rl_PersonDetail:
                intent.setClass(getApplicationContext(), CustDescActivity.class);
                startActivity(intent);
                break;
            case R.id.CustInfo_rl_city:
                intent.setClass(getApplicationContext(), CityActivity.class);
                intent.putExtra("fromFlag", "custInfo");
                startActivity(intent);
                break;
        }

    }


    private void downloading()  {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/info",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("rows");
                        if (infoList != null && infoList.size() > 0) {
                            Map<String, Object> infoMap = infoList.get(0);
                            MyApplication.userInfo = infoMap;
                            TextView telephoneText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_telephone);
                            telephoneText.setText(infoMap.get("telephone") + "");
                            TextView nickNameText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_nick_name);
                            if (infoMap.get("nickName") != null) {
                                nickNameText.setText(infoMap.get("nickName") + "");
                            }
                            if (!TextUtils.isEmpty((String) infoMap.get("realName"))) {
                                TextView realNameText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_realName);
                                realNameText.setText(infoMap.get("realName").toString());
                                haveRealName = false;
                                mIvReadNameCome.setVisibility(View.INVISIBLE);
                            }
                            TextView emailText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_email);
                            if (!TextUtils.isEmpty((String) infoMap.get("email"))) {
                                haveEmail = true;
                                email = infoMap.get("email").toString();
                                emailText.setText(infoMap.get("email").toString());

                            }
                            TextView orgText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_org);
                            if (!TextUtils.isEmpty((String) infoMap.get("orgName"))) {
                                orgText.setText(infoMap.get("orgName").toString());
                            }
                            TextView sexText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_sex);
                            String sex = (String) infoMap.get("sex");
                            if ("1".equals(sex)) {
                                sexText.setText("男");
                            } else if ("0".equals(sex)) {
                                sexText.setText("女");
                            } else {
                                sexText.setText("未填写");
                            }
                            TextView addressText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_address);
                            addressText.setText(infoMap.get("proName") + " " + infoMap.get("cityName"));
                            String userImageUrl = infoMap.get("userImage").toString();
                            if (!TextUtils.isEmpty(userImageUrl)) {
                                MyApplication.imageLoader.displayImage(Urls.RANQING + "/uploadAction/viewImage?imageName=" + userImageUrl,
                                        mIvHeadImg, MyApplication.options);
                            }
                            if (!TextUtils.isEmpty((String) infoMap.get("custDesc"))) {
                                TextView custDescText = (TextView) CustInfoActivity.this.findViewById(R.id.cust_custDesc);
                                custDescText.setText(infoMap.get("custDesc").toString());
                            }
                            if (!TextUtils.isEmpty((String) infoMap.get("wxNickName"))) {
                                mTvWeixin.setText(infoMap.get("wxNickName").toString());
                            }
                        }
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                }
        );
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
                    intent.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_HEAD);
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
                    intent1.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,ConstantUtils.UPLOAD_HEAD);
                    startActivity(intent1);
                    break;
            }
        }
    }


}
