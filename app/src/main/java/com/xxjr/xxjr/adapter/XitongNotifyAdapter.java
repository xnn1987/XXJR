package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.custview.Pull2RefreshListView;

import java.util.List;
import java.util.Map;

public class XitongNotifyAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList;

    private Context context;
    private LayoutInflater layoutInflater;
    private Pull2RefreshListView mListView;

    public XitongNotifyAdapter(Context context, List<Map<String,Object>> mList, Pull2RefreshListView mListView) {
        this.context = context;
        this.mList = mList;
        this.mListView = mListView;
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
            convertView = layoutInflater.inflate(R.layout.item_xitongnotify, null);
             vh = new ViewHolder();
            vh.mIvIcon = (ImageView) convertView.findViewById(R.id.xitongnotify_iv_icon);
            vh.mTvTishi = (TextView) convertView.findViewById(R.id.xitongnotify_tv_tishi);
            vh.mTvTime = (TextView) convertView.findViewById(R.id.xitongnotify_tv_time);
            vh.mTvConten = (TextView) convertView.findViewById(R.id.xitongnotify_tv_conten);
            convertView.setTag(vh);
        vh = (ViewHolder) convertView.getTag();
        if (mList.get(position).get("flag").toString().equals("1")){
            vh.mIvIcon.setImageResource(R.mipmap.xitongtongzhiyou);
        }else {
            vh.mIvIcon.setImageResource(R.mipmap.xitongtongzhihui);
        }
        vh.mTvTime.setText(mList.get(position).get("sendTime").toString());
        vh.mTvConten.setText(mList.get(position).get("content").toString());
        return convertView;
    }


    protected class ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvTishi;
        private TextView mTvTime;
        private TextView mTvConten;
    }

    public void updateView(int itemIndex) {
        //得到第一个可显示控件的位置，
        int visiblePosition = mListView.getFirstVisiblePosition();
        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
//        if (itemIndex - visiblePosition >= 0) {
            //得到要更新的item的view
            View view = mListView.getChildAt(itemIndex - visiblePosition);
            //从view中取得holder
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.mIvIcon.setImageResource(R.mipmap.xitongtongzhihui);
//        }
    }
}
