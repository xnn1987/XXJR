package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReturnCommissionAdapter extends BaseAdapter {

    private List<Map<String,Object>>  mlist = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ReturnCommissionAdapter(Context context, List<Map<String,Object>> mList) {
        this.context = context;
        this.mlist = mList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.return_commission_lv_item, null);
             viewHolder = new ViewHolder();
            viewHolder.orderDetailTvMoney = (TextView) convertView.findViewById(R.id.OrderDetail_tv_money);
            viewHolder.orderDetailTvRebate = (TextView) convertView.findViewById(R.id.OrderDetail_tv_rebate);
            viewHolder.orderDetailTvUserName = (TextView) convertView.findViewById(R.id.OrderDetail_tv_item_name);
            viewHolder.orderDetailTvTime = (TextView) convertView.findViewById(R.id.OrderDetail_tv_item_time);
            viewHolder.orderDetailTvOrgName = (TextView) convertView.findViewById(R.id.OrderDetail_tv_item_orgName);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.orderDetailTvMoney.setText(mlist.get(position).get("approveAmount")+"万");
        viewHolder.orderDetailTvRebate.setText(mlist.get(position).get("realAmount")+"万");
        viewHolder.orderDetailTvUserName.setText(mlist.get(position).get("loanName").toString());
        viewHolder.orderDetailTvTime.setText(mlist.get(position).get("auditTime").toString());
        viewHolder.orderDetailTvOrgName.setText(mlist.get(position).get("orgName").toString());
        return convertView;
    }


    protected class ViewHolder {
        private TextView orderDetailTvMoney;
        private TextView orderDetailTvUserName;
        private TextView orderDetailTvTime;
        private TextView orderDetailTvRebate;
        private TextView orderDetailTvOrgName;


    }
}
