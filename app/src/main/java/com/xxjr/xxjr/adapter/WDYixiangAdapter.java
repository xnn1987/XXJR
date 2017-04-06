package com.xxjr.xxjr.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;

public class WDYixiangAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public WDYixiangAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        mList = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.wd_yixiang_kehu_item, null);
             vh = new ViewHolder();
            vh.mUserName = (TextView) convertView.findViewById(R.id.WD02_tv_name);
            vh.mTvOrgName = (TextView) convertView.findViewById(R.id.wd02_tv_companyName);
            vh.mTvContent = (TextView) convertView.findViewById(R.id.wd02_tv_content);
            vh.mTvTime = (TextView) convertView.findViewById(R.id.wd02_tv_time);
            vh.mLlTel = (LinearLayout) convertView.findViewById(R.id.WD02_ll_tel);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        vh.mUserName.setText(mList.get(position).get("applyName").toString());
        vh.mTvOrgName.setText(mList.get(position).get("productName").toString());
        vh.mTvTime.setText(mList.get(position).get("createTime").toString());
        vh.mTvContent.setText(mList.get(position).get("applyDtlDesc").toString());
        vh.mLlTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mList.get(position).get("telephone").toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);//内部类
            }
        });
        return convertView;
    }


    protected class ViewHolder {
        private LinearLayout mLlTel;
        private TextView mUserName;
        private TextView mTvOrgName;
        private TextView mTvContent;
        private TextView mTvTime;
    }
}
