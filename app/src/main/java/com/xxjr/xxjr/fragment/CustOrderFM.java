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
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.OrderDetailActivity;
import com.xxjr.xxjr.adapter.CustIndentAdapter;
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
 * Created by Administrator on 2016/2/29.
 */
public class CustOrderFM extends Fragment implements Pull2RefreshListView.OnRefreshListener,Pull2RefreshListView.OnLoadMoreListener {
    private CustIndentAdapter adapter;
    private int applyId = -1;
    private List<Map<String,Object>> mRowsList = new ArrayList<Map<String,Object>>();
    private int currentPage;
    private ImageView mIvCheckData;
    private String inStatus;
    private Pull2RefreshListView mLvOrder;
    private boolean isFirst = true;
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
                    downloading(Urls.CUST_ORDER);
                    break;
                case ConstantUtils.HEAD_REFRESH:
                    currentPage = 0;
                    mRowsList.clear();
                    downloading(Urls.CUST_ORDER);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mLvOrder.stopAllRefresh();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        inStatus = bundle.getString("inStatus");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cust_order_fm, null);
        initViews(view);
        lvListener();
        setLvAdapter();
        downloading(Urls.CUST_ORDER);
        return view;
    }


    private void initViews(View view) {
        mIvCheckData = (ImageView) view.findViewById(R.id.CustOrder_iv_checkIcon);
        mLvOrder = (Pull2RefreshListView) view.findViewById(R.id.MyIndentActivity_lv_indent);
    }

    private void lvListener() {
        mLvOrder.setOnLoadListener(this);
        mLvOrder.setOnRefreshListener(this);
        //lv  点击事件
        mLvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                applyId = Integer.parseInt(mRowsList.get(position - 1).get("applyId").toString());
                intent.putExtra("applyId", applyId);
                startActivity(intent);
            }
        });
    }
    //lv  添加适配内容
    private void setLvAdapter() {
        adapter = new CustIndentAdapter(getContext(),mRowsList);
        mLvOrder.setAdapter(adapter);
    }

    //我的交单
    private void downloading(String url){
        currentPage+=1;
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("everyPage", 10+"");
        params.put("inStatus", inStatus);
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
                            judgeCheckCount(mLvOrder, mIvCheckData);
                            cancleRefresh();
                        }else {
                            mRowsList.addAll(rows);
                            adapter.notifyDataSetChanged();
                        }
                        judgeCheckCount(mLvOrder, mIvCheckData);
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

    /**
     * 判断是否有数据，没有就显示图标
     * @param listview
     * @param imageView
     */
    private void judgeCheckCount(Pull2RefreshListView listview,ImageView imageView){
        if (adapter.getCount()!=0){
            listview.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }else {
            listview.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 取消刷新
     */
    private void cancleRefresh(){
        mLvOrder.setCanRefresh(false);
        mLvOrder.setCanRefresh(false);
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }
}
