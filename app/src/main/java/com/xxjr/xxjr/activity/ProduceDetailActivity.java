package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.ChoiceProduceBean;
import com.xxjr.xxjr.bean.ProduceDetailBean;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

public class ProduceDetailActivity extends SlidBackActivity implements View.OnClickListener {

    private ProduceDetailBean produceDetailBean = new ProduceDetailBean();
    private TextView mTvHandRate, mTvMonthRate, mTvProduceInfo, mTvApplication, mTvNeedMaterail;
    private int loanId;
    private String companyName;
    private String produceType;
    private TextView mTvCompanyName;
    private LinearLayout mLlConnact, mLlCommit;
    private int type;
    private String loadType;
    private String prgName = "";
    private TextView mTvTel;
    private TextView mTvSeviceName;
    private String telephone;
    private String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_detail);
        SetTitleBar.setTitleText(ProduceDetailActivity.this, ConstantUtils.TITLE_BAR_PRODUCEDETAIL);
        getIntentDatas();
        initView();
        setDatas();
        setListener();
        judgeTypeCommit();
        downLoadingDatas(Urls.RANQING + Urls.PRODUCE_DETAIL + loanId);
    }

    private void initView() {
        mTvCompanyName = (TextView) findViewById(R.id.ProdiceDetail_tv_CompanyProduce);
        mTvHandRate = (TextView) findViewById(R.id.ProduceDetail_tv_commission);
        mTvMonthRate = (TextView) findViewById(R.id.ProduceDetail_tv_cost);
        mTvProduceInfo = (TextView) findViewById(R.id.ProduceDetail_tv_ProduceInfo);
        mTvApplication = (TextView) findViewById(R.id.ProduceDetail_tv_Apply);
        mTvNeedMaterail = (TextView) findViewById(R.id.ProduceDetail_tv_Material);
        //切换显示  立即交单和  联系客服
        mLlCommit = (LinearLayout) findViewById(R.id.produceDetail_ll_CommitOrder);
        mLlConnact = (LinearLayout) findViewById(R.id.produceDetail_ll_connact);
        mTvTel = (TextView) findViewById(R.id.ProduceDetail_tv_tel);
        mTvSeviceName = (TextView) findViewById(R.id.ProduceDetail_tv_service);
    }

    private void setDatas() {
        mTvCompanyName.setText(companyName + produceType);
        prgName = companyName + "-" + produceType;
    }

    private void setListener() {
        mLlCommit.setOnClickListener(this);
        findViewById(R.id.ProduceDetail_ll_tel).setOnClickListener(this);//电话
        findViewById(R.id.ProduceDetail_ll_leaveMsg).setOnClickListener(this);//留言
    }

    private void judgeTypeCommit() {
        if (type == ConstantUtils.COMPANY_PRODUCE_COMMIT_TYPE) {
            mLlCommit.setVisibility(View.VISIBLE);
            mLlCommit.setClickable(true);
            mLlConnact.setVisibility(View.INVISIBLE);
            mLlConnact.setClickable(false);
        }
    }


    public void setViewDatas(ProduceDetailBean produceDetailBean) {
        mTvHandRate.setText(produceDetailBean.getAttr().getLoanDetail().getFeeRate() + "%");
        mTvMonthRate.setText(produceDetailBean.getAttr().getLoanDetail().getRateMin() + "—" +
                produceDetailBean.getAttr().getLoanDetail().getRateMax() + "%");
        mTvProduceInfo.setText(produceDetailBean.getAttr().getLoanDetail().getLoanDesc());
        mTvApplication.setText(produceDetailBean.getAttr().getLoanDetail().getApplyDesc());
        mTvNeedMaterail.setText(produceDetailBean.getAttr().getLoanDetail().getNeedDesc());
        mTvTel.setText(telephone);
        mTvSeviceName.setText(serviceName);

    }
    public void downLoadingDatas(String url) {
        StringRequest mRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        produceDetailBean = gson.fromJson(response, ProduceDetailBean.class);
                        setViewDatas(produceDetailBean);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MyApplication.mQueue.add(mRequest);

    }

    //获取上页ID
    public void getIntentDatas() {
        Intent intent = getIntent();
        loanId = Integer.parseInt(intent.getStringExtra("loanId"));
        companyName = intent.getStringExtra("companyName");
        produceType = intent.getStringExtra("produceType");
        type = intent.getIntExtra("type", 0);
        loadType = intent.getStringExtra("loadType");
        telephone = intent.getStringExtra("telephone");
        serviceName = intent.getStringExtra("serviceName");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.produceDetail_ll_CommitOrder://立刻交单
                if (MyApplication.userInfo == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    Intent intent = new Intent(getApplicationContext(), SalaryOrderActivity.class);
                    intent.putExtra("loadId", loanId);
                    intent.putExtra("loanType", loadType);
                    intent.putExtra("prgName", prgName);
                    //通知简单交单
                    ChoiceProduceBean choiceProduceBean = new ChoiceProduceBean();
                    choiceProduceBean.setLoadId(loanId);
                    choiceProduceBean.setLoanType(loadType);
                    choiceProduceBean.setTitleName(prgName);
                    EventBus.getDefault().post(choiceProduceBean);
                    startActivity(intent);
                }
                break;
            case R.id.ProduceDetail_ll_tel:
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone));//拨打客服电话
                startActivity(intent2);
                break;
            case R.id.ProduceDetail_ll_leaveMsg:

                break;
        }
    }
}
