package com.xxjr.xxjr.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.BussyCardActivity;
import com.xxjr.xxjr.activity.QuTuActivity;
import com.xxjr.xxjr.activity.WDBestSellersActivity;
import com.xxjr.xxjr.activity.WDCardActivity;
import com.xxjr.xxjr.activity.WDCardActivity02;
import com.xxjr.xxjr.activity.WDCardSetActivity;
import com.xxjr.xxjr.activity.WDProduceActivity01;
import com.xxjr.xxjr.activity.WDProduceActivity02;
import com.xxjr.xxjr.activity.WDTongJiActivity;
import com.xxjr.xxjr.activity.WDYiXiangActivity01;
import com.xxjr.xxjr.activity.WDYixiangActivity02;
import com.xxjr.xxjr.activity.WD_SharedActivity;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.Shared.Shared;
import com.xxjr.xxjr.utils.ViewMathUtils;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wqg
 */
public class WeiDianFM extends Fragment implements View.OnClickListener{
    private LinearLayout mLlNoApply;
    private LinearLayout mLlFoot,mLlApplyDes;
    private ImageView mIvHead;
    private int hasCustApply = 0;
    private String bufferTimpId ;
    private Map<String,Object> map = new HashMap<>();
    private TextView mTvShopName, mTvDes, mTvDianZanCount, mTvLiuLanCount, mTvFuWuDiQu, mTvChanPinCount, mTvLiLv, mTvShenQingCount;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    AppUtil.showProgress(getActivity(), ConstantUtils.DIALOG_SHOW);
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    break;
            }
        }
    };
    private LinearLayout mLlYinDao,mRlWdMain;
    private Button mBtnCredtWd;
    private ImageView mIvMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wei_dian_fm, container, false);
        initViews(view);
        setListener(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ViewMathUtils.mIvMsgNotifySee(mIvMsg);
        if (MyApplication.haveWd){
            mRlWdMain.setVisibility(View.VISIBLE);
            mLlYinDao.setVisibility(View.GONE);
            dowloadDatas(Urls.WD_HOME_INIT);
        }else {
            mRlWdMain.setVisibility(View.GONE);
            mLlYinDao.setVisibility(View.VISIBLE);
        }
        if (bufferTimpId!=MyApplication.tempId){
            dowloadDatas(Urls.WD_HOME_INIT);
        }
        if (MyApplication.isChangeWDCardActivity){
            dowloadDatas(Urls.WD_HOME_INIT);
            MyApplication.isChangeWDCardActivity = false;
        }
    }

    private void initViews(View view) {
        mLlYinDao = (LinearLayout) view.findViewById(R.id.wdfm_ll_yindao);
        mRlWdMain = (LinearLayout) view.findViewById(R.id.wdfm_ll_main);
        mBtnCredtWd = (Button) view.findViewById(R.id.wdfm_btn_towdcard);
        mIvMsg = (ImageView) view.findViewById(R.id.bussiness_iv_message);

        mLlNoApply = (LinearLayout) view.findViewById(R.id.wdfm_ll_noApply);
        mLlFoot = (LinearLayout) view.findViewById(R.id.wdfm_ll_foot);
        mLlApplyDes = (LinearLayout) view.findViewById(R.id.wdfm_ll_apply);
        mIvHead = (ImageView) view.findViewById(R.id.wdfm_iv_head);
        mTvShopName = (TextView) view.findViewById(R.id.wdfm_tv_shopname);
        mTvDes = (TextView) view.findViewById(R.id.wdfm_tv_des);
        mTvDianZanCount = (TextView) view.findViewById(R.id.wdfm_tv_dianzanCount);
        mTvLiuLanCount = (TextView) view.findViewById(R.id.wdfm_tv_liulanCount);
        mTvFuWuDiQu = (TextView) view.findViewById(R.id.wdfm_tv_fuwudiqu);
        mTvChanPinCount = (TextView) view.findViewById(R.id.wdfm_tv_chanpinCount);
        mTvLiLv = (TextView) view.findViewById(R.id.wdfm_tv_lilv);
        mTvShenQingCount = (TextView) view.findViewById(R.id.wdfm_tv_shenqingCount);

    }

    private void setListener(View view) {
        view.findViewById(R.id.wdfm_ll_custApply).setOnClickListener(this);
        view.findViewById(R.id.wdfm_ll_WDproduce).setOnClickListener(this);
        view.findViewById(R.id.wdfm_ll_WDShared).setOnClickListener(this);
        view.findViewById(R.id.wdfm_ll_WDOrder).setOnClickListener(this);
        view.findViewById(R.id.wdfm_ll_setCard).setOnClickListener(this);
        view.findViewById(R.id.wdfm_ll_BussyCard).setOnClickListener(this);
        view.findViewById(R.id.wdfm_ll_zhanyeQutu).setOnClickListener(this);
        view.findViewById(R.id.wdfm_ll_WDTontji).setOnClickListener(this);
        view.findViewById(R.id.wdfm_btn_more).setOnClickListener(this);//  底部的查看更多
        mIvHead.setOnClickListener(this);
        mBtnCredtWd.setOnClickListener(this);
        mIvMsg.setOnClickListener(this);
    }

    private void dowloadDatas(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map1 = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((Boolean) map1.get("success")) {
                            map.clear();
                            map.putAll(map1);
                            setData(map);
                            downloadQueryList(Urls.WD_YIXIANG_KEHU_QUERY);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
    private void setData(Map<String, Object> map) {
        Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
        Map<String, Object> wdInfoMap = (Map<String, Object>) attrMap.get("wdInfo");
        MyApplication.tempId = wdInfoMap.get("tempId").toString();//  微店模板
        bufferTimpId = MyApplication.tempId;
        MyApplication.imageLoader.displayImage(Urls.RANQING + Urls.BUSSINESS_CUSTCARD_HEADICON + wdInfoMap.get("headImage"),
                mIvHead, MyApplication.optionsHead);
        mTvShopName.setText(wdInfoMap.get("shopName").toString());
        mTvDes.setText(wdInfoMap.get("shopDesc").toString());
        mTvDianZanCount.setText(wdInfoMap.get("clickCount").toString());
        mTvLiuLanCount.setText(wdInfoMap.get("browseCount").toString());

        mTvShenQingCount.setText(wdInfoMap.get("applyCount").toString());
        mTvFuWuDiQu.setText("服务地区：" + attrMap.get("serviceArea").toString());
        mTvChanPinCount.setText(attrMap.get("proCount").toString());
        Map<String, Object> rateRangeMap = (Map<String, Object>) attrMap.get("rateRange");
        mTvLiLv.setText(rateRangeMap.get("rateMin").toString() + "%-" + rateRangeMap.get("rateMax").toString()+"%");
        //  存储微店分享信息
        new Shared().spWeidianShared(getActivity(),mTvShopName.getText().toString(),mTvDes.getText().toString(),
                Urls.BUSSINESS_CUSTCARD_HEADICON + wdInfoMap.get("headImage"),attrMap.get("serviceArea").toString());
    }
    //  首页列表
    private void downloadQueryList(String url){
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("everyPage",3+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        setQueryDatas(map);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
    //下载公司产品列表数据
    private void downLoadDatas(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        Intent intent = new Intent();
                        if (rowsList.size()==0){
                            intent.setClass(getContext(),WDProduceActivity01.class);
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("rowsList", (Serializable) rowsList);
                            intent.putExtras(bundle);
                            intent.setClass(getContext(),WDProduceActivity02.class);
                        }
                        startActivity(intent);
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
    //  最近申请  的记录
    private void setQueryDatas(Map<String, Object> map) {
        final List<Map<String,Object>>rowsList = (List<Map<String, Object>>) map.get("rows");
        hasCustApply = rowsList.size();
        if (rowsList==null || rowsList.size()==0){
            mLlNoApply.setVisibility(View.VISIBLE);
            mLlFoot.setVisibility(View.GONE);
        }else {
            mLlNoApply.setVisibility(View.GONE);
            mLlFoot.setVisibility(View.VISIBLE);
            for (int i = 0; i < rowsList.size(); i++) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.wd_yixiang_kehu_item,null);
                LinearLayout mLlItem = (LinearLayout) view.findViewById(R.id.yixiangkehu_ll_item);
                TextView mUserName = (TextView) view.findViewById(R.id.WD02_tv_name);
                TextView mOrgName = (TextView) view.findViewById(R.id.wd02_tv_companyName);
                LinearLayout mLlTel = (LinearLayout) view.findViewById(R.id.WD02_ll_tel);//  监听
                TextView mTvTime = (TextView) view.findViewById(R.id.yixiangkehu_tv_come);
                TextView mTvcontent = (TextView) view.findViewById(R.id.wd02_tv_content);

                mUserName.setText(rowsList.get(i).get("applyName").toString());
                mOrgName.setText(rowsList.get(i).get("productName").toString());
                mTvTime.setText(rowsList.get(i).get("createTime").toString());
                mTvcontent.setText(rowsList.get(i).get("applyDesc").toString());
                final int finalI = i;
                mLlTel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rowsList.get(finalI).get("telephone").toString()));
                        startActivity(intent);//内部类
                    }
                });
                mLlApplyDes.addView(view);

            }
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.bussiness_iv_message:
                ViewMathUtils.intent2MsgActivity(getContext());
                return;
            case R.id.wdfm_iv_head:
                intent.setClass(getContext(),WDCardActivity.class);
                intent.putExtra("isAdd", false);
                intent.putExtra(ConstantUtils.WDCARD_FROM,  ConstantUtils.WDCARD_FROM_FRAGMENT);
                break;
            case R.id.wdfm_ll_custApply:
                if (hasCustApply >0){
                    intent.setClass(getContext(),WDYixiangActivity02.class);
                }else {
                    intent.setClass(getContext(), WDYiXiangActivity01.class);
                }
                break;
            case R.id.wdfm_ll_WDproduce:
                downLoadDatas(Urls.WD_PRODUCE_List);
                return;
            case R.id.wdfm_ll_WDShared:
                Intent intent1 = new Intent(getContext(),WD_SharedActivity.class);
                startActivity(intent1);
                return;
            case R.id.wdfm_ll_WDOrder:
                intent.setClass(getContext(), WDBestSellersActivity.class);
                break;
            case R.id.wdfm_ll_setCard:
                // todo 这个不复用
//                intent.setClass(getContext(), WDCardActivity02.class);
                wdCardDownLoad(Urls.WD_CARD_SETTING_QUERY);
                return;
            case R.id.wdfm_ll_BussyCard:
                intent.setClass(getContext(),BussyCardActivity.class);
                break;
            case R.id.wdfm_ll_zhanyeQutu:
                intent.setClass(getContext(),QuTuActivity.class);
                break;
            case R.id.wdfm_ll_WDTontji:
                intent.setClass(getContext(),WDTongJiActivity.class);
                break;
            case R.id.wdfm_btn_more:
                intent.setClass(getContext(),WDYixiangActivity02.class);
                break;
            case R.id.wdfm_btn_towdcard:
                intent.setClass(getContext(),WDCardActivity.class);
                intent.putExtra("isAdd", true);
                intent.putExtra(ConstantUtils.WDCARD_FROM, ConstantUtils.WDCARD_FROM_FRAGMENT);
                startActivity(intent);
                break;
        }
        startActivity(intent);
    }
    //  判断  微店名片是否有数据
    private void wdCardDownLoad(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
                            if ((boolean) attrMap.get("haveWdCard")) {
                                Intent intent = new Intent(getContext(),WDCardActivity02.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("attrMap", (Serializable) attrMap);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(getContext(),WDCardSetActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getActivity(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
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
}
