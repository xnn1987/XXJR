package com.xxjr.xxjr.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.CustInfoActivity;
import com.xxjr.xxjr.activity.CustOrderActivity;
import com.xxjr.xxjr.activity.CustPerformanceActivity;
import com.xxjr.xxjr.activity.LoginActivity;
import com.xxjr.xxjr.activity.MyMemberActivity;
import com.xxjr.xxjr.activity.RecruitActivity;
import com.xxjr.xxjr.activity.RobOrderActivity;
import com.xxjr.xxjr.activity.SettingActivity;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.image.imageCircle.SelectableRoundedImageView;
import com.xxjr.xxjr.utils.ViewMathUtils;

/**
 * @author wqg
 */
public class CustFM extends Fragment implements View.OnClickListener{
    private LinearLayout mRlMyInfo,mRlIndent,mRlPerfomance/*,mRlDeposit*/,mRlRecruit, mRlRobOrder,mRlMyMember,mRlSetting;
    private TextView mTvMember;
    private TextView realNameView;
    private SelectableRoundedImageView headImg;
    private ImageView mIvMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.cust_fm, container, false);
        initView(view);
        setListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setInitDatas();
        ViewMathUtils.mIvMsgNotifySee(mIvMsg);
    }

    //监听 RL  布局
    private void setListener() {
        mRlMyInfo.setOnClickListener(this);
        mRlIndent.setOnClickListener(this);
        mRlPerfomance.setOnClickListener(this);
        mRlRecruit.setOnClickListener(this);
        mRlRobOrder.setOnClickListener(this);
        mRlMyMember.setOnClickListener(this);
        mRlSetting.setOnClickListener(this);
        mIvMsg.setOnClickListener(this);
    }
    //  初始化 RL  布局
    private void initView(View view) {
        mIvMsg = (ImageView) view.findViewById(R.id.Cust_iv_message);
        mRlMyInfo = (LinearLayout) view.findViewById(R.id.myfm_ll_personInfo);
        mRlIndent = (LinearLayout) view.findViewById(R.id.myfm_ll_indent);
        mRlPerfomance = (LinearLayout) view.findViewById(R.id.myfm_ll_performence);
        mRlRecruit = (LinearLayout) view.findViewById(R.id.myfm_ll_recruit);
        mRlRobOrder = (LinearLayout) view.findViewById(R.id.myfm_ll_RobOrder);
        mRlMyMember = (LinearLayout) view.findViewById(R.id.CustFm_Ll_MyMember);
        mRlSetting = (LinearLayout) view.findViewById(R.id.myfm_ll_setting);
        mTvMember = (TextView)view.findViewById(R.id.CustFm_tv_member);
        realNameView = (TextView)view.findViewById(R.id.CustFm_Tv_realName);
        headImg = (SelectableRoundedImageView) view.findViewById(R.id.myfm_iv_headIcon);


    }

    private void setInitDatas() {
        if (MyApplication.userInfo!=null) {
            if (MyApplication.userInfo.get("gradeName")!=null&&!TextUtils.isEmpty(MyApplication.userInfo.get("gradeName").toString())) {
                mTvMember.setText(MyApplication.userInfo.get("gradeName").toString());
            }
            if (MyApplication.userInfo.get("realName") != null) {
                realNameView.setText(MyApplication.userInfo.get("realName").toString());
            }else /*if (MyApplication.userInfo.get("realName") ==null)*/{
                realNameView.setText(MyApplication.userInfo.get("telephone").toString());
            }
            if (MyApplication.userInfo.get("userImage") != null) {
                if (!MyApplication.userInfo.get("userImage").equals("")) {
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.touxiang)// 加载等待 时显示的图片
                            .showImageForEmptyUri(R.mipmap.touxiang)// 加载数据为空时显示的图片
                            .showImageOnFail(R.mipmap.touxiang)// 加载失败时显示的图片
                            .build();
                    MyApplication.imageLoader.displayImage(Urls.RANQING + "/uploadAction/viewImage?imageName=" + MyApplication.userInfo.get("userImage").toString(),
                            headImg, options);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myfm_ll_personInfo:
                ViewMathUtils.intentIsLogin(getContext(),CustInfoActivity.class);
                break;
            case R.id.myfm_ll_indent:
                ViewMathUtils.intentIsLogin(getContext(),CustOrderActivity.class);
                break;
            case R.id.myfm_ll_performence:
                ViewMathUtils.intentIsLogin(getContext(),CustPerformanceActivity.class);
                break;
            case R.id.myfm_ll_recruit://收徒
                ViewMathUtils.intentIsLogin(getContext(),RecruitActivity.class);
                break;
            case R.id.myfm_ll_RobOrder://抢单
                ViewMathUtils.intentIsLogin(getContext(),RobOrderActivity.class);
                break;
            case R.id.CustFm_Ll_MyMember://会员
                ViewMathUtils.intentIsLogin(getContext(),MyMemberActivity.class);
                break;
            case R.id.myfm_ll_setting:
                ViewMathUtils.intentIsLogin(getContext(),SettingActivity.class);
                break;
            case R.id.Cust_iv_message:
                ViewMathUtils.intent2MsgActivity(getContext());
                return;
        }

    }


}
