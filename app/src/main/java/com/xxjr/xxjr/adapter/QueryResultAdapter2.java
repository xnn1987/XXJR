package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryResultAdapter2 extends BaseAdapter {

    private List<Map<String,Object>> mList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public QueryResultAdapter2(Context context, List<Map<String, Object>> mList) {
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
            convertView = layoutInflater.inflate(R.layout.query_result2_item, null);
             vh = new ViewHolder();
            vh.mIvIcon = (ImageView) convertView.findViewById(R.id.QueryResult_iv_icon);
            vh.mTvOrgName = (TextView) convertView.findViewById(R.id.QueryResult_tv_name);
            vh.mTvStatus = (TextView) convertView.findViewById(R.id.QueryResult_tv_status);
            vh.mBtnStatus = (Button) convertView.findViewById(R.id.QueryResult_btn_stutas);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        MyApplication.imageLoader.displayImage(Urls.RANQING + mList.get(position).get("orgImage").toString(),
                vh.mIvIcon, MyApplication.options);
        vh.mTvOrgName.setText(mList.get(position).get("orgName").toString());
        switch (mList.get(position).get("status").toString()){
            case "0":
                vh.mTvStatus.setText("查询中");
                vh.mBtnStatus.setText("查询中");
                break;
            case "1":
                vh.mTvStatus.setText("可交单");
                vh.mBtnStatus.setText("可交单");
                break;
            case "2":
                vh.mTvStatus.setText("已交单");
                vh.mBtnStatus.setText("已交单");
                break;
            case "3":
                vh.mTvStatus.setText(mList.get(position).get("createTime").toString());
                vh.mBtnStatus.setText("已撤销");
                break;
            case "4":
                vh.mTvStatus.setText("已存在");
                vh.mBtnStatus.setText("已存在");
                break;
        }


        return convertView;
    }


    protected class ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvOrgName,mTvStatus;
        private Button mBtnStatus;
    }
}
