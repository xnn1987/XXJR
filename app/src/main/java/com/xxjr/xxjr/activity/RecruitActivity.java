package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.Shared.SocialUmengShare;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class RecruitActivity extends SlidBackActivity implements View.OnClickListener {

    private RelativeLayout mRlMyRecruit,mRlRecruitMore,mRlWhyRecruit;
    private LinearLayout mRlMakeMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        initViews();
        setListener();
    }

    private void initViews() {
        mRlMyRecruit = (RelativeLayout) findViewById(R.id.Recruit_Rl_MyRecruit);
        mRlRecruitMore = (RelativeLayout) findViewById(R.id.Recruit_Rl_RecruitMore);
        mRlWhyRecruit = (RelativeLayout) findViewById(R.id.Recruit_Rl_WhyRecruit);
        mRlMakeMoney = (LinearLayout) findViewById(R.id.Recruit_Ll_makeMoney);
    }

    private void setListener() {
        mRlMyRecruit.setOnClickListener(this);
        mRlRecruitMore.setOnClickListener(this);
        mRlWhyRecruit.setOnClickListener(this);
        mRlMakeMoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.Recruit_Rl_MyRecruit://我的学徒
                intent.setClass(getApplicationContext(), MyRecriutActivity.class);
                startActivity(intent);
                break;
            case R.id.Recruit_Rl_RecruitMore://为什么要收徒
                intent.setClass(getApplicationContext(), WhyRecruitActivity.class);
                startActivity(intent);
                break;
            case R.id.Recruit_Rl_WhyRecruit://如何收取更多徒弟
                intent.setClass(getApplicationContext(), MakeMoreRecruitActivity.class);
                startActivity(intent);
                break;
            case R.id.Recruit_Ll_makeMoney://收徒赚佣金
                new SocialUmengShare(RecruitActivity.this,getResources().getString(R.string.app_name),"收徒赚佣金",Urls.SHARED_RECRUIT,null).showPop(findViewById(R.id.recruit_main));
                break;
        }
    }

}
