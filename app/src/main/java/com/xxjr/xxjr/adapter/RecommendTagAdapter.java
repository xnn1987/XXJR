package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.TuiJianEvenBus;
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
public class RecommendTagAdapter extends BaseAdapter{
    private List<String> mList ;
    private Context context;
    private LayoutInflater inflate;
    private List<CheckBox> checkBoxList = new ArrayList<>();

    public RecommendTagAdapter(List<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return mList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflate.inflate(R.layout.tag_checkbox_item, null);
            viewHolder.tv_tag = (CheckBox) convertView.findViewById(R.id.mytag_checkbox_tag);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        checkBoxList.add(viewHolder.tv_tag);
        viewHolder.tv_tag.setText(mList.get(position));
        viewHolder.tv_tag.setChecked(false);
        viewHolder.tv_tag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i< getCount(); i++){
                    checkBoxList.get(i).setChecked(false);
                }
                TuiJianEvenBus bean = new TuiJianEvenBus();
                bean.setIsCheck(isChecked);
                bean.setTagText(buttonView.getText().toString());
                buttonView.setChecked(isChecked);
                EventBus.getDefault().post(bean);
            }
        });


        return convertView;
    }

    class ViewHolder {
        CheckBox tv_tag;
    }


}
