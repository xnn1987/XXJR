package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ChoiceOrgAdapter extends BaseAdapter {

    private List<Map<String,String>> list = new ArrayList<Map<String, String>>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ChoiceOrgAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
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
            convertView = layoutInflater.inflate(R.layout.choice_org_lv_item, null);
            ViewHolder viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }
        return convertView;
    }


    protected class ViewHolder {
    }
}
