package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckResultAdapter extends BaseAdapter {

    private List<Map<String,String>> objects = new ArrayList<Map<String, String>>();

    private Context context;
    private LayoutInflater layoutInflater;

    public CheckResultAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.check_result_lv_item_type01, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.checkResultTvUserName = (TextView) convertView.findViewById(R.id.CheckResult_tv_UserNameCheck);
            viewHolder.checkResultTvID = (TextView) convertView.findViewById(R.id.CheckResult_tv_ID);
            viewHolder.checkResultTvCompanyName = (TextView) convertView.findViewById(R.id.CheckResult_tv_companyName);
            viewHolder.checkResultTvChecking = (TextView) convertView.findViewById(R.id.CheckResult_tv_checking);
            viewHolder.checkResultTvUserName = (Button) convertView.findViewById(R.id.CheckResult_btn_commitOrRevoke);

            convertView.setTag(viewHolder);
        }
        return convertView;
    }


    protected class ViewHolder {
        private TextView checkResultTvUserName;
        private TextView checkResultTvID;
        private TextView checkResultTvCompanyName;
        private TextView checkResultTvChecking;
        private Button checkResultBtnUserName;
    }
}
