package com.xxjr.xxjr.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.other.timePicker.Popup;


public class WDBerstSellerFmAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;
    private  int status;

    public WDBerstSellerFmAdapter(Context context,List<Map<String,Object>> mList,int status) {
        this.context = context;
        this.mList = mList;
        this.status  = status;
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
            convertView = layoutInflater.inflate(R.layout.wdberst_seller_item, null);
             vh = new ViewHolder();
            vh.mIvIcon  = (ImageView) convertView.findViewById(R.id.selleritemt_iv_icon);
            vh.mTvIcon  = (TextView) convertView.findViewById(R.id.selleritemt_tv_icon);
            vh.mTvName  = (TextView) convertView.findViewById(R.id.selleritemt_tv_name);
            vh.mTvCity  = (TextView) convertView.findViewById(R.id.selleritemt_tv_city);
            vh.mTvCount  = (TextView) convertView.findViewById(R.id.bestseller_tv_Count);
            convertView.setTag(vh);
        }
        vh  = (ViewHolder) convertView.getTag();
        if (position<3){
            vh.mIvIcon.setVisibility(View.VISIBLE);
            vh.mTvIcon.setVisibility(View.GONE);
            if (position==0){
                vh.mIvIcon.setImageResource(R.mipmap.yi_guangjun);
            }else if (position==1){
                vh.mIvIcon.setImageResource(R.mipmap.er);
            }else if (position==2){
                vh.mIvIcon.setImageResource(R.mipmap.san);
            }
        }else {
            vh.mIvIcon.setVisibility(View.GONE);
            vh.mTvIcon.setVisibility(View.VISIBLE);
        }
        if (position<9) {
            vh.mTvIcon.setText("0" + (position + 1));
        }else {
            vh.mTvIcon.setText(position+1+"");
        }
        vh.mTvName.setText(mList.get(position).get("shopName").toString());
        vh.mTvCity.setText(mList.get(position).get("cityName").toString());
        if (status==0) {
            vh.mTvCount.setText(mList.get(position).get("applyCount").toString());
        }else {
            vh.mTvCount.setText(mList.get(position).get("browseCount").toString());
        }
        return convertView;
    }



    protected class ViewHolder {
        ImageView mIvIcon;
        TextView mTvIcon,mTvName,mTvCity,mTvCount;

        public ViewHolder() {
            this.mIvIcon = mIvIcon;
        }
    }
}
