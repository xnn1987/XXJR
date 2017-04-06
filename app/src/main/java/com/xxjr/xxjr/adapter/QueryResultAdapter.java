package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.custview.CustListview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryResultAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public QueryResultAdapter(Context context, List<Map<String, Object>> mList) {
        this.context = context;
        this.mList = mList;
        this.layoutInflater = LayoutInflater.from(context);
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
        ViewHolder vh ;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.query_result_item, null);
             vh = new ViewHolder();
            vh.mTvName = (TextView) convertView.findViewById(R.id.QueryResult_tv_Name);
            vh.mTvID = (TextView) convertView.findViewById(R.id.QueryResult_tv_ID);
            vh.mTvTime = (TextView) convertView.findViewById(R.id.QueryResult_tv_time);
            vh.mLvResult = (CustListview) convertView.findViewById(R.id.QueryResult_lv_result2);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        vh.mTvName.setText(mList.get(position).get("realName").toString());
        vh.mTvID.setText(mList.get(position).get("cardNo").toString());
        vh.mTvTime.setText(mList.get(position).get("createTime").toString());
        List<Map<String,Object>> orgDtlsList  = (List<Map<String, Object>>) mList.get(position).get("orgDtls");
        QueryResultAdapter2 adapter2 = new QueryResultAdapter2(context,orgDtlsList);
        vh.mLvResult.setAdapter(adapter2);

        return convertView;
    }


    protected class ViewHolder {
        private TextView mTvName,mTvID,mTvTime;
        private CustListview mLvResult;
    }
}
