package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.OrderNotifyChildActivity;
import com.xxjr.xxjr.custview.Pull2RefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderNotifyAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");;
    private Date data;
    private Pull2RefreshListView mListView;

    public OrderNotifyAdapter(Context context, List<Map<String, Object>> mList,Pull2RefreshListView listview) {
        this.context = context;
        this.mList = mList;
        this.layoutInflater = LayoutInflater.from(context);
        this.mListView = listview;
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
            convertView = layoutInflater.inflate(R.layout.item_order_notify, null);
             vh = new ViewHolder();
            vh.mTvTime = (TextView) convertView.findViewById(R.id.ordernotify_tv_time);
            vh.mTvBorrowName = (TextView) convertView.findViewById(R.id.OrderNotify_tv_borrowName);
            vh.mTvBorrowMsg = (TextView) convertView.findViewById(R.id.orderNotify_tv_borrowMsg);
            vh.mBtnChat = (Button) convertView.findViewById(R.id.ordernotify_btn_chat);
            vh.mTvState = (TextView) convertView.findViewById(R.id.ordernotify_tv_state);
            convertView.setTag(vh);
        vh = (ViewHolder) convertView.getTag();
        if (mList.get(position).get("isSee").toString().equals("0")){
            vh.mTvState.setVisibility(View.VISIBLE);
        }else {
            vh.mTvState.setVisibility(View.GONE);
        }
        data = new Date(Long.parseLong(mList.get(position).get("logTime").toString().trim()));
        vh.mTvTime.setText(sdf.format(data));
        vh.mTvBorrowName.setText(mList.get(position).get("title").toString());
        vh.mTvBorrowMsg.setText(mList.get(position).get("logDesc").toString());
        final ViewHolder finalVh = vh;
        vh.mBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderNotifyChildActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("applyId", mList.get(position).get("applyId").toString());
                context.startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finalVh.mTvState.setVisibility(View.GONE);
                    }
                },1000);
            }
        });
        return convertView;
    }


    protected class ViewHolder {
        private TextView mTvTime;
        private TextView mTvBorrowName;
        private TextView mTvBorrowMsg;
        private TextView mTvState;
        private Button mBtnChat;
    }
}
