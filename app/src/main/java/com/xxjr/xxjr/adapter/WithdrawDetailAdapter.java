package com.xxjr.xxjr.adapter;

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

public class WithdrawDetailAdapter extends BaseAdapter {

    private List<Map<String,Object>> mlist = new ArrayList<> ();

    private Context context;
    private LayoutInflater layoutInflater;

    public WithdrawDetailAdapter(Context context, List<Map<String,Object>> mlist) {
        this.context = context;
        this.mlist = mlist;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.withdraw_detail_lv_item, null);
             viewHolder = new ViewHolder();
            viewHolder.withdrawDetailItemTvBank = (TextView) convertView.findViewById(R.id.WithdrawDetailItem_tv_bank);
            viewHolder.withdrawDetailItemTvTime = (TextView) convertView.findViewById(R.id.WithdrawDetailItem_tv_time);
            viewHolder.withdrawDetailItemTvMoney = (TextView) convertView.findViewById(R.id.WithdrawDetailItem_tv_money);
            viewHolder.withdrawDetailItemTvState = (TextView) convertView.findViewById(R.id.WithdrawDetailItem_tv_check);

            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.withdrawDetailItemTvBank.setText(mlist.get(position).get("recordDesc").toString());
        viewHolder.withdrawDetailItemTvTime.setText(mlist.get(position).get("createTime").toString());
        viewHolder.withdrawDetailItemTvMoney.setText(mlist.get(position).get("realAmount")+"万");

        //状态
        int status = (int) mlist.get(position).get("status");
        switch (status){
            case 0:
                viewHolder.withdrawDetailItemTvState.setText("审核中");
                break;
            case 1:
                viewHolder.withdrawDetailItemTvState.setText("审核中");
                break;
            case 2:
                viewHolder.withdrawDetailItemTvState.setText("提现成功");
                break;
            case 3:
                viewHolder.withdrawDetailItemTvState.setText("取消");
                break;
        }



        return convertView;
    }


    protected class ViewHolder {
        private TextView withdrawDetailItemTvBank;
        private TextView withdrawDetailItemTvTime;
        private TextView withdrawDetailItemTvMoney;
        private TextView withdrawDetailItemTvState;
    }
}
