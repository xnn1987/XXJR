package com.xxjr.xxjr.adapter;

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

import java.util.List;
import java.util.Map;

public class HomeCompanyAdapter extends BaseAdapter {

    private List<Map<String,Object>> list;

    private Context context;
    private LayoutInflater layoutInflater;
    private String TAG = "HomeCompanyAdapter-->>";
    public HomeCompanyAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.home_company_lv_item, null);
             viewHolder = new ViewHolder();
            viewHolder.homeCompanyTvReturnRate = (TextView) convertView.findViewById(R.id.homeCompany_tv_returnRate);
            viewHolder.homeCompanyIvCompanyIcon = (ImageView) convertView.findViewById(R.id.homeCompany_iv_companyIcon);
            viewHolder.homeCompanyTvCompanyName = (TextView) convertView.findViewById(R.id.homeCompany_tv_companyName);
            viewHolder.homeCompanyTvLoanLimits = (TextView) convertView.findViewById(R.id.homeCompany_tv_loanLimits);
            viewHolder.homeCompanyTvReturnCount = (TextView) convertView.findViewById(R.id.homeCompany_tv_returnCount);

            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.homeCompanyTvCompanyName.setText(list.get(position).get("orgName").toString());
        viewHolder.homeCompanyTvReturnRate.setText(Double.parseDouble(list.get(position).get("rewardRate").toString())+"");
        viewHolder.homeCompanyTvLoanLimits.setText(list.get(position).get("loanDesc").toString());
        viewHolder.homeCompanyTvReturnCount.setText(list.get(position).get("applyCount").toString());
        MyApplication.imageLoader.displayImage(Urls.RANQING+list.get(position).get("orgImage").toString(),
                viewHolder.homeCompanyIvCompanyIcon,MyApplication.options);
        return convertView;
    }
    protected class ViewHolder {
        private TextView homeCompanyTvReturnRate;
        private ImageView homeCompanyIvCompanyIcon;
        private TextView homeCompanyTvCompanyName;
        private TextView homeCompanyTvLoanLimits;
        private TextView homeCompanyTvReturnCount;
    }
}
