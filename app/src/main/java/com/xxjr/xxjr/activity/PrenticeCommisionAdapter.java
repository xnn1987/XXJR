package com.xxjr.xxjr.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrenticeCommisionAdapter extends BaseAdapter {

    private List<Map<String,String>> mlist = new ArrayList<Map<String,String>>();

    private Context context;
    private LayoutInflater layoutInflater;


    public PrenticeCommisionAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.prentice_commision_lv_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.prenticeCommisionTvAwark = (TextView) convertView.findViewById(R.id.prenticeCommision_tv_Awark);
            viewHolder.prenticeCommisionLlUserInfo = (LinearLayout) convertView.findViewById(R.id.PrenticeCommision_ll_userInfo);
            viewHolder.prenticeCommisionTvUserName = (TextView) convertView.findViewById(R.id.prenticeCommision_tv_UserName);

            convertView.setTag(viewHolder);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        return convertView;
    }


    protected class ViewHolder {
        private TextView prenticeCommisionTvAwark;
        private LinearLayout prenticeCommisionLlUserInfo;
        private TextView prenticeCommisionTvUserName;
    }
}
