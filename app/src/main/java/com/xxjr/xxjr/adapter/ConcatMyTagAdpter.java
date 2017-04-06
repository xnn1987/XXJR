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

/**
 * Created by hwf on 2016/1/29.
 */
public class ConcatMyTagAdpter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String,Object>> list;

    public ConcatMyTagAdpter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.contact_my_tag_lv_item, null);
            viewHolder.custConcatTagText = (TextView)convertView.findViewById(R.id.concat_my_cust_tatNames);
            viewHolder.custConcatCountSizeText = (TextView)convertView.findViewById(R.id.cust_concat_cust_size);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.custConcatTagText.setText(list.get(position).get("tagName").toString());
        viewHolder.custConcatCountSizeText.setText("("+list.get(position).get("countNum")+")");
        return convertView;
    }

    protected class ViewHolder {
        private TextView custConcatCountSizeText;
        private TextView custConcatTagText;
    }
}

