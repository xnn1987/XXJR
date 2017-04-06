package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.Md5Utils;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.Shared.SocialUmeng;


public class AlterWeixinActivity extends SlidBackActivity implements View.OnClickListener{
    private TextView mTvWX;
    private EditText mEtPsw;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    AppUtil.showProgress(AlterWeixinActivity.this,ConstantUtils.DIALOG_SHOW);
                    break;
                case ConstantUtils.LOAD_SUCCESS_WEIXIN:
                    AppUtil.dismissProgress();
                    finish();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    break;
            }
        }
    };
    private Button mBtnAlter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_weixin);
        SetTitleBar.setTitleText(AlterWeixinActivity.this, "修改微信");
        initView();
        initViewData();
        setListener();
    }

    private void initView() {
        mTvWX = (TextView) findViewById(R.id.AlterWX_tv_WX);
        mEtPsw = (EditText) findViewById(R.id.AlterWX_et_psw);
        mBtnAlter = (Button) findViewById(R.id.AlterWX_btn_ALTER);
    }

    private void setListener() {
        mBtnAlter.setOnClickListener(this);
    }

    private void initViewData() {
        if (MyApplication.userInfo.get("wxNickName") != null) {
            mTvWX.setText(MyApplication.userInfo.get("wxNickName").toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AlterWX_btn_ALTER:
                SharedPrefUtil sp = new SharedPrefUtil(getApplicationContext(),ConstantUtils.SP_USER_NAME);
                if (TextUtils.isEmpty(mEtPsw.getText().toString().trim())) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (!Md5Utils.string2MD5(mEtPsw.getText().toString().trim()).equals(sp.getString(ConstantUtils.SP_USER_PSW,null))){
                    DebugLog.Toast(this,"密码不正确");
                }else {
                    SocialUmeng.threeLogin(this, handler);
                }

                break;
        }
    }

}
