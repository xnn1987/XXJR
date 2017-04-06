package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderNotifyChildAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public OrderNotifyChildAdapter(Context context,List<Map<String,Object>> mList) {
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
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_ordernotify_chil, null);
             vh = new ViewHolder();
            vh.mTvTime = (TextView) convertView.findViewById(R.id.OrderNotifyChile_tv_time);
            vh.mTvCheck = (TextView) convertView.findViewById(R.id.OrderNotifyChile_tv_check);

            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        vh.mTvTime.setText(mList.get(position).get("logTime").toString());
        vh.mTvCheck.setText(mList.get(position).get("logDesc").toString());
        return convertView;
    }


    protected class ViewHolder {
        private TextView mTvTime;
        private TextView mTvCheck;
    }
}
