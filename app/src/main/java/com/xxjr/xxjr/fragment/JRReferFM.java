package com.xxjr.xxjr.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.BussinessConstruceActivity;
import com.xxjr.xxjr.adapter.JJReferAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.Pull2RefreshListView;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/26.
 */
public class JRReferFM extends Fragment implements Pull2RefreshListView.OnRefreshListener,Pull2RefreshListView.OnLoadMoreListener
,AdapterView.OnItemClickListener {
    private static final int ADDHEADN_COUNT = 1;
    private List<Map<String,Object>> mReferList = new ArrayList<>();
    private boolean isFirst = true;
    private int currentPage = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    if (isFirst) {
                        AppUtil.showProgress(getActivity(), ConstantUtils.DIALOG_SHOW);
                        isFirst = false;
                    }
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    currentPage--;
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.FOOT_REFRESH:
                    downLoadRefer(Urls.BUSSINESS_QUERYLIST);
                    break;
                case ConstantUtils.HEAD_REFRESH:
                    currentPage = 0;
                    mReferList.clear();
                    downLoadRefer(Urls.BUSSINESS_QUERYLIST);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mLvRefer.stopAllRefresh();
                    break;
            }
        }
    };
    private Pull2RefreshListView mLvRefer;
    private String zxTag;
    private JJReferAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        zxTag = bundle.getString("zxTag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_jr_refer, null);
        mLvRefer = (Pull2RefreshListView) view.findViewById(R.id.jrrefer_lv_refer);
        mLvRefer.setOnRefreshListener(this);
        mLvRefer.setOnLoadListener(this);
        mLvRefer.setOnItemClickListener(this);
        setLvAdapter();
        downLoadRefer(Urls.BUSSINESS_QUERYLIST);
        return view;
    }

    private void setLvAdapter() {
        adapter = new JJReferAdapter(getActivity(),mReferList);
        mLvRefer.setAdapter(adapter);
    }


    private void downLoadRefer(String url){
        currentPage+=1;
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("everyPage", 10+"");
        params.put("zxTag", zxTag);
        params.put("currentPage", currentPage + "");
        httpRequestUtil.jsonObjectRequestPostSuccess(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rows = (List<Map<String, Object>>) responseMap.get("rows");
                        if (rows.size() == 0) {
                            currentPage--;
                            mLvRefer.setCanLoadMore(false);
                            Toast.makeText(getActivity(), ConstantUtils.NO_ANYTHING_DATA, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mReferList.addAll(rows);
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



    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), BussinessConstruceActivity.class);
        intent.putExtra("zxTag", (mReferList.get(position - ADDHEADN_COUNT).get("zxTag")).toString());
        intent.putExtra("novelId", (mReferList.get(position - ADDHEADN_COUNT).get("novelId")).toString());
        intent.putExtra("title", (mReferList.get(position - ADDHEADN_COUNT).get("title").toString()));
        intent.putExtra("smallImg", (mReferList.get(position - ADDHEADN_COUNT).get("smallImg").toString()));
        startActivity(intent);
    }
}
