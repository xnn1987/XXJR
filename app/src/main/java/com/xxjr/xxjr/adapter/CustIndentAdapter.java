package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;

import java.util.List;
import java.util.Map;


public class CustIndentAdapter extends BaseAdapter {
    private List<Map<String,Object>> mRowsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustIndentAdapter(Context context, List<Map<String, Object>> mRowsList) {
        this.context = context;
        this.mRowsList = mRowsList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mRowsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRowsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cust_order_itemt, null);
             viewHolder = new ViewHolder();
            viewHolder.mIvState = (ImageView) convertView.findViewById(R.id.Cust0rder_iv_state);
            viewHolder.myindentLlItem = (LinearLayout) convertView.findViewById(R.id.myindent_ll_item);
            viewHolder.mTvUsername = (TextView) convertView.findViewById(R.id.Cust0rder_tv_username);
            viewHolder.mTvOrdercount = (TextView) convertView.findViewById(R.id.Cust0rder_tv_ordercount);
            viewHolder.mTvUseID = (TextView) convertView.findViewById(R.id.Cust0rder_tv_useTel);
            viewHolder.mTvTime = (TextView) convertView.findViewById(R.id.Cust0rder_tv_time);
            viewHolder.mTvDealStatus  = (TextView) convertView.findViewById(R.id.CustOrder_tv_dealstatus);

            convertView.setTag(viewHolder);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.mTvUsername.setText(mRowsList.get(position).get("borrowerRealName").toString());
        vh.mTvTime.setText(mRowsList.get(position).get("createTime").toString());
        vh.mTvUseID.setText(mRowsList.get(position).get("telephone").toString());
        vh.mTvOrdercount.setText("(已经申请" + mRowsList.get(position).get("loanCount").toString() + "个订单)");

        if (mRowsList.get(position).get("noSeeCount").toString().equals("0")){
            vh.mIvState.setVisibility(View.GONE);
        }else {
            vh.mIvState.setVisibility(View.VISIBLE);
            vh.mIvState.setImageResource(R.mipmap.xinjinzhan);
        }

        switch (Integer.parseInt(mRowsList.get(position).get("status").toString())){
            case 0:
                vh.mTvDealStatus.setText(ConstantUtils.ORDER_STATUS_0);
                vh.mTvDealStatus.setTextColor(Color.parseColor("#ff69b7f9"));
                break;
            case 1:
                vh.mTvDealStatus.setText(ConstantUtils.ORDER_STATUS_1);
                vh.mTvDealStatus.setTextColor(Color.parseColor("#ff69b7f9"));

                break;
            case 3:
                vh.mTvDealStatus.setText(ConstantUtils.ORDER_STATUS_3);
                vh.mTvDealStatus.setTextColor(Color.parseColor("#ff69b7f9"));

                break;
            case 2:
                vh.mTvDealStatus.setText(ConstantUtils.ORDER_STATUS_2);
                vh.mTvDealStatus.setTextColor(Color.parseColor("#ffff7070"));
                break;
            case 4:
                vh.mTvDealStatus.setText(ConstantUtils.ORDER_STATUS_4);
                vh.mTvDealStatus.setTextColor(Color.parseColor("#ff5ec56f"));
                break;
            case 5:
                vh.mTvDealStatus.setText(ConstantUtils.ORDER_STATUS_5);
                vh.mTvDealStatus.setTextColor(Color.parseColor("#ffcecece"));
                break;
            case 6:
                vh.mTvDealStatus.setText(ConstantUtils.ORDER_STATUS_6);
                vh.mTvDealStatus.setTextColor(Color.parseColor("#ffcecece"));
                break;
        }

        return convertView;
    }
    protected class ViewHolder {
        private ImageView mIvState;
        private LinearLayout myindentLlItem;
        private TextView mTvUsername;
        private TextView mTvOrdercount;
        private TextView mTvUseID;
        private TextView mTvTime;
        private TextView mTvDealStatus;
    }
}
