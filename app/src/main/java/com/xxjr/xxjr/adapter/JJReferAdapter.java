package com.xxjr.xxjr.adapter;

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
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;

public class JJReferAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList;

    private Context context;
    private LayoutInflater layoutInflater;

    public JJReferAdapter(Context context,List<Map<String,Object>> mList) {
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
            convertView = layoutInflater.inflate(R.layout.item_jrrefer_fm, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        MyApplication.imageLoader.displayImage(Urls.RANQING+mList.get(position).get("smallImg"),
                vh.mIvTitleIcon,MyApplication.options);
        vh.mTvTitle.setText(mList.get(position).get("title").toString());
        vh.mTvTime.setText(mList.get(position).get("publishTime").toString());
        vh.mTvMsgCount.setText(mList.get(position).get("viewCount").toString());


        return convertView;
    }


    protected class ViewHolder {
        private ImageView mIvTitleIcon;
        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvZuiXin;
        private TextView mTvMsgCount;

        public ViewHolder(View view) {
            mIvTitleIcon = (ImageView) view.findViewById(R.id.jrrefer_iv_icon);
            mTvTitle = (TextView) view.findViewById(R.id.jrrefer_tv_title);
            mTvTime = (TextView) view.findViewById(R.id.jrrefer_tv_time);
            mTvZuiXin = (TextView) view.findViewById(R.id.jrrefer_tv_zuixin);
            mTvMsgCount = (TextView) view.findViewById(R.id.jrrefer_tv_msgCount);
        }
    }
}
