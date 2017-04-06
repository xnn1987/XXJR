package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xxjr.xxjr.R;

import java.util.List;
import java.util.Map;


public class OrderFeedBackAdapter extends BaseAdapter {

    private List<Map<String,Object>>  list;

    private Context context;
    private LayoutInflater layoutInflater;

    public OrderFeedBackAdapter(Context context, List<Map<String,Object>> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.order_feedback_lv_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mTvTime = (TextView) convertView.findViewById(R.id.OrderFeedback_tv_time);
            viewHolder.mTvCheck = (TextView) convertView.findViewById(R.id.OrderFeedback_tv_check);

            convertView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTvTime.setText(list.get(position).get("logTime").toString());
        viewHolder.mTvCheck.setText(list.get(position).get("logDesc").toString());
        return convertView;
    }

    protected class ViewHolder {
        TextView mTvTime,mTvCheck;
    }
}
