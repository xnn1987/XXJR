package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.OrderDetailLvItemAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class OrderDetailActivity extends SlidBackActivity implements View.OnClickListener{

    private static final int SIMPLE_ORDER = 1;
    private static final int FULL_ORDER = 2;
    private static final int MSG_ORDER = 3;
    private static final int WEIXIN_ORDER = 4;
    private ListView mLvIndentDetail;
    private View mVUserInfo , mVUserMeterial,mVUserAllIndent;
    private LinearLayout mLlUserInfo,mLlUserMaterial;
    private TextView mTvUserName,mTvUserId;
    private TextView mTvMaterial,mTvUpPicCount,mTvType,mTvAllOrderCount;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private Map<String,Object> mUserInfoMap = new HashMap<>();
    private Map<String,Object> mServiceMap = new HashMap<>();
    private OrderDetailLvItemAdapter adapter;
    private int loanId = -1;
    private int applyId = -1;
    private String type;
    private LinearLayout mLlPhone;
    private LinearLayout mLlMessage;
    private TextView mTvServive;
    private TextView mTvServiveTel;
    public  String telephone;
//    private String imName;
    public  String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        SetTitleBar.setTitleText(OrderDetailActivity.this, "交单详情");
        mVUserInfo = LayoutInflater.from(this).inflate(R.layout.order_detail_userinfo,null);//用户信息View
        mVUserMeterial = LayoutInflater.from(this).inflate(R.layout.order_detail_user_material,null);//用户材料View
        mVUserAllIndent = LayoutInflater.from(this).inflate(R.layout.order_detail_all_indent,null);//all
        initView(mVUserInfo, mVUserMeterial, mVUserAllIndent);
        applyId = getIntentDatas();
        setListener();
        lvAddView();
        downloading(Urls.ORDER_DETAIL);
    }
    private void initView(View mVUserInfo, View mVUserMeterial, View mVUserAllIndent) {
        mLvIndentDetail = (ListView) findViewById(R.id.IndentDetail_lv);
        mLlUserInfo = (LinearLayout) mVUserInfo.findViewById(R.id.OrderDetail_userInfo);
        mLlUserMaterial = (LinearLayout) mVUserMeterial.findViewById(R.id.OrderDetail_ll_UserMaterial);
        //用户个人信息
        mTvUserName = (TextView) mVUserInfo.findViewById(R.id.OrderDetail_tv_UserName1);
        mTvUserId = (TextView) mVUserInfo.findViewById(R.id.OrderDetail_tv_UserTel);
        mTvType  = (TextView) mVUserInfo.findViewById(R.id.IndentDetail_tv_type);
        //客户材料
        mTvMaterial  = (TextView) mVUserMeterial.findViewById(R.id.IndentDetail_Material);
        mTvUpPicCount  = (TextView) mVUserMeterial.findViewById(R.id.IndentDetail_tv_upPicCount);
        //联系客服
        mLlPhone = (LinearLayout) findViewById(R.id.OrderDetail_ll_phone);
        mLlMessage = (LinearLayout) findViewById(R.id.OrderDetail_ll_message);
        mTvServive = (TextView) findViewById(R.id.Ordertail_tv_Service);
        mTvServiveTel = (TextView) findViewById(R.id.Ordertail_tv_ServiceTel);

    }


    private void setListener() {
        mLlUserInfo.setOnClickListener(this);
        mLlUserMaterial.setOnClickListener(this);
        mLlPhone.setOnClickListener(this);
        mLlMessage.setOnClickListener(this);
    }

    private void lvAddView() {
        mLvIndentDetail.addHeaderView(mVUserInfo);
        mLvIndentDetail.addHeaderView(mVUserMeterial);
        mLvIndentDetail.addHeaderView(mVUserAllIndent);
    }

    private void lvSetAdapter() {
        adapter = new OrderDetailLvItemAdapter(OrderDetailActivity.this,mList,mTvUserName.getText().toString());
        mLvIndentDetail.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private int getIntentDatas(){
        Intent intent = getIntent();
        int applyId = intent.getIntExtra("applyId",-1);
        return applyId;
    }
    //交单详情
    private void downloading(String url){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("applyId",applyId+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
                        List<Map<String, Object>> orderDtlsList = (List<Map<String, Object>>) attrMap.get("orderDtls");
                        mList.addAll(orderDtlsList);
                        Map<String, Object> orderinfoMap = (Map<String, Object>) attrMap.get("orderInfo");
                        Map<String, Object> serviceInfoMap = (Map<String, Object>) attrMap.get("serviceInfo");
                        mUserInfoMap.putAll(orderinfoMap);
                        mServiceMap.putAll(serviceInfoMap);
                        setInitDatas();
                        adapter.notifyDataSetChanged();
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }

    /**
     * 设置控件的值
     * @param textView
     */
    private void setViewDatas(TextView textView,String str){
        textView.setText(str);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.OrderDetail_userInfo:
                intent.setClass(getApplicationContext(), ContactDetailActivity.class);
                intent.putExtra("name",mUserInfoMap.get("borrowerRealName").toString());
                String cardNo = "";
                if (mUserInfoMap.get("cardNo")!=null)
                    cardNo = mUserInfoMap.get("cardNo").toString();
                intent.putExtra("cardNo", cardNo);//todo  判断有没有
                intent.putExtra("Tel", mUserInfoMap.get("telephone").toString());
                intent.putExtra("ApplyAmount", mUserInfoMap.get("applyAmount").toString());
                startActivity(intent);
                break;
            case R.id.OrderDetail_ll_UserMaterial:
                intent.setClass(getApplicationContext(), SumbitUserMaterialActivity.class);
                intent.putExtra("applyId", applyId);
                intent.putExtra("loanId", loanId);
                intent.putExtra("commitType", ConstantUtils.COMMIT_RESULT_SIMPLE);
                intent.putExtra("produceType","-1");//这个标记是下一个   跳转按钮隐藏；
                intent.putExtra("titleName","");
                intent.putExtra("from","OrderDetailActivity");
                startActivity(intent);
                break;
            case R.id.OrderDetail_ll_phone:
                Intent intent2 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telephone));//拨打客服电话
                startActivity(intent2);
                break;
            case R.id.OrderDetail_ll_message://联系客服
                intent.setClass(getApplicationContext(),KefuChatOrderDetActivity.class);
                intent.putExtra("applyId",applyId+"");
                intent.putExtra("name",serviceName);
                startActivity(intent);
                break;
        }
    }
    //
    public void setInitDatas(){
        setViewDatas(mTvUpPicCount, "已上传" + mUserInfoMap.get("uploadedImg").toString());
        mTvUserName.setText(mUserInfoMap.get("borrowerRealName").toString());
        lvSetAdapter();
        mTvUserId.setText(mUserInfoMap.get("telephone").toString());//个人信息
        int status = (int) mUserInfoMap.get("status");
        //订单状态
        switch (status){
            case 0:
                mTvMaterial.setText(ConstantUtils.ORDER_STATUS_0);
                break;
            case 1:
                mTvMaterial.setText(ConstantUtils.ORDER_STATUS_1);
                break;
            case 2:
                mTvMaterial.setText(ConstantUtils.ORDER_STATUS_2);
                break;
            case 3:
                mTvMaterial.setText(ConstantUtils.ORDER_STATUS_3);
                break;
            case 4:
                mTvMaterial.setText(ConstantUtils.ORDER_STATUS_4);
                break;
            case 5:
                mTvMaterial.setText(ConstantUtils.ORDER_STATUS_5);
                break;
            case 6:
                mTvMaterial.setText(ConstantUtils.ORDER_STATUS_6);
                break;

        }
        if (Integer.parseInt(mUserInfoMap.get("fromType").toString())== SIMPLE_ORDER){//材料
            mTvType.setText("(简单交单)");
        }else if (Integer.parseInt(mUserInfoMap.get("fromType").toString()) == FULL_ORDER){
            mTvType.setText("(完整交单)");
        }else if (Integer.parseInt(mUserInfoMap.get("fromType").toString()) == MSG_ORDER){
            mTvType.setText("(短信交单)");
        }else if (Integer.parseInt(mUserInfoMap.get("fromType").toString()) == WEIXIN_ORDER){
            mTvType.setText("(微信交单)");
        }
        mTvAllOrderCount = (TextView) mVUserAllIndent.findViewById(R.id.OrderDetail_tv_count);
        mTvAllOrderCount.setText("("+mUserInfoMap.get("loanCount").toString()+")");
        applyId = (int) mUserInfoMap.get("applyId");


        //客服聊天相对应的名字
        telephone =mServiceMap.get("telephone").toString();
        serviceName = mServiceMap.get("userName").toString();
        mTvServive.setText(serviceName);
        mTvServiveTel.setText(telephone);

    }

    //TODO
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        telephone = null;
        serviceName = null;
    }
}
