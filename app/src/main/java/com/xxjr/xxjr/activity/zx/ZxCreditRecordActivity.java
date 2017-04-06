package com.xxjr.xxjr.activity.zx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.lineChart.Line;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.xxjr.xxjr.utils.network.MyOkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZxCreditRecordActivity extends SlidBackActivity implements AdapterView.OnItemClickListener {
    private List<Map<String,Object>> mList = new ArrayList<>();
    private ListView mLvRecord;
    private String creditId;
    private CreditRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zx_credit_record);
        SetTitleBar.setTitleText(ZxCreditRecordActivity.this,"信用卡记录明细");
        creditId = getIntent().getStringExtra("creditId");
        mLvRecord = (ListView) findViewById(R.id.zxcredit_lv_record);
        adapter = new CreditRecordAdapter(getApplicationContext());
        mLvRecord.setAdapter(adapter);

        mLvRecord.setOnItemClickListener(this);

        downloadRecord();
    }
    private void downloadRecord(){
        Map map = new HashMap();
        map.put("creditId",creditId);
        RequestCall requestCall = MyOkHttpUtils.postRequest(Urls.ZX_CHAXUNSHUJU_RECORD, map);
        requestCall.execute(new MapCallback(ZxCreditRecordActivity.this) {
            @Override
            public void onResponse(Map responseMap) {
                super.onResponse(responseMap);
                if (responseMap!=null) {
                    Map<String, Object> attrMap = (Map<String, Object>) responseMap.get("attr");
                    List<Map<String, Object>> reportList = (List<Map<String, Object>>) attrMap.get("reportList");
                    mList.addAll(reportList);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(),ZxCreditRecordDetailActivity.class);
        intent.putExtra("creditId",creditId);
        intent.putExtra("reportId",mList.get(position).get("reportId").toString());
        startActivity(intent);
    }

    private class CreditRecordAdapter extends BaseAdapter{
        private Context context;
        public CreditRecordAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_credit_record,null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            }
            vh = (ViewHolder) convertView.getTag();
            vh.mTvTime.setText(mList.get(position).get("reportTime").toString());
            return convertView;

        }

        class ViewHolder{
            TextView mTvTime;
            public ViewHolder(View view) {
                mTvTime = (TextView) view.findViewById(R.id.credit_tv_time);
            }
        }

    }
}
