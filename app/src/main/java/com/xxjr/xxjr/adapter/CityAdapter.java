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

public class CityAdapter extends BaseAdapter {

    private List<CityBean.AttrEntity.AreasEntity.CitysEntity> mCitysList ;

    private Context context;
    private LayoutInflater layoutInflater;

    public CityAdapter(Context context, List<CityBean.AttrEntity.AreasEntity.CitysEntity> mCitysList) {
        this.context = context;
        this.mCitysList = mCitysList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mCitysList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCitysList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.city_lv_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mCityName = (TextView) convertView.findViewById(R.id.city_tv_CityName1);

            convertView.setTag(viewHolder);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
//        Log.e("main",mCitysList.get(position).getNameCn());
        vh.mCityName.setText(mCitysList.get(position).getNameCn());
        return convertView;
    }


    protected class ViewHolder {
         TextView mCityName;
    }
}
