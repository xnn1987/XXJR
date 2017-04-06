package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.CityBean;

import java.util.List;


public class ProviceAdapter extends BaseAdapter {

//    private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    private List<CityBean.AttrEntity.AreasEntity> list ;

    private Context context;
    private LayoutInflater layoutInflater;

    public ProviceAdapter(Context context, List<CityBean.AttrEntity.AreasEntity> mCityList) {
        this.context = context;
        this.list = mCityList;
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
            convertView = layoutInflater.inflate(R.layout.provide_lv_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mTvCity = (TextView) convertView.findViewById(R.id.city_tv_ProviceName);

            convertView.setTag(viewHolder);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.mTvCity.setText(list.get(position).getProvice());

        return convertView;
    }


    protected class ViewHolder {
        TextView mTvCity;
    }
}
