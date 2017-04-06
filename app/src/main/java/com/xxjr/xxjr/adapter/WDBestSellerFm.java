package com.xxjr.xxjr.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/8.
 */
public class WDBestSellerFm extends Fragment {
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
    private ImageView mIvBanner;
    private ImageView mIvPaiHang;
    private ListView mLvSeller;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private WDBerstSellerFmAdapter adapter;
    private int position;
    private TextView mTvCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("position");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wdbest_seller_fm, null);
        initViews(view);
        setLvAdapter();
        downloading(Urls.WD_PAIHANG);
        return view;
    }

    private void downloading(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        Map<String,Object> attrMap = (Map<String, Object>) responseMap.get("attr");
                        List<Map<String,Object>> applyRankList = (List<Map<String, Object>>) attrMap.get("applyRankList");
                        List<Map<String,Object>> browseRankList = (List<Map<String, Object>>) attrMap.get("browseRankList");
                        if (position==0){
                            mTvCount.setText("申请人数："+attrMap.get("myApplyCount").toString()+"人");
                            mList.addAll(applyRankList);
                        }else if (position==1){
                            mTvCount.setText("浏览人数："+attrMap.get("myBrowseCount").toString()+"人");
                            mList.addAll(browseRankList);
                        }
                        adapter.notifyDataSetChanged();
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

    private void setLvAdapter() {
        int status = position;
        adapter = new WDBerstSellerFmAdapter(getContext(),mList,status);
        mLvSeller.setAdapter(adapter);
    }

    private void initViews(View view) {
        mIvBanner = (ImageView) view.findViewById(R.id.bestseller_iv_sharedicon);
        mIvPaiHang = (ImageView) view.findViewById(R.id.bestseller_iv_paihang);
        mLvSeller = (ListView) view.findViewById(R.id.bestseller_lv_seller);
        mTvCount = (TextView) view.findViewById(R.id.bestseller_tv_shared);
        if (position==0){
            mIvBanner.setImageResource(R.mipmap.shenqpaihang);
        }else {
            mIvBanner.setImageResource(R.mipmap.fenxianbaner);
        }
    }

}
