package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;

import java.util.List;
import java.util.Map;

public class ChoiceOrg2Adapter extends BaseAdapter {

    private List<Map<String ,Object>> mList ;

    private Context context;
    private LayoutInflater layoutInflater;

    public ChoiceOrg2Adapter(Context context, List<Map<String, Object>> mList) {
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
            convertView = layoutInflater.inflate(R.layout.choice_org2_item, null);
             vh = new ViewHolder();
            vh.mIvOrgHead = (ImageView) convertView.findViewById(R.id.ChoiceOrg2_iv_org);
            vh.mTvOrgName = (TextView) convertView.findViewById(R.id.ChoiceOrg2_tv_orgname);
            vh.mIvStatus = (ImageView) convertView.findViewById(R.id.ChoiceOrg2_iv_choiceStutas);

            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        MyApplication.imageLoader.displayImage(Urls.RANQING+mList.get(position).get("orgImage").toString(),
                vh.mIvOrgHead,MyApplication.options);
        vh.mTvOrgName.setText(mList.get(position).get("orgName").toString());
        return convertView;
    }


    protected class ViewHolder {
        private ImageView mIvOrgHead,mIvStatus;
        private TextView mTvOrgName;
    }

    public void updateView(int itemIndex,ListView mListView) {
        //得到第一个可显示控件的位置，
        int visiblePosition = mListView.getFirstVisiblePosition();
        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        if (itemIndex - visiblePosition >= 0) {
            //得到要更新的item的view
            View view = mListView.getChildAt(itemIndex - visiblePosition);
            //从view中取得holder
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.mIvStatus.setVisibility(View.VISIBLE);
        }
    }
}
