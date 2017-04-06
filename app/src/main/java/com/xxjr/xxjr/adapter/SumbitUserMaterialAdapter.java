package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SumbitUserMaterialAdapter extends BaseAdapter {

    private List<Map<String,Object>> list;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> mPositionList = new ArrayList<>();

    public SumbitUserMaterialAdapter(Context context, List<Map<String, Object>> mMaterialGroupList) {
        this.context = context;
        this.list = mMaterialGroupList;
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
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.sumbit_user_material_lv_item, null);
             vh = new ViewHolder();
            vh.sumbitUserMateriaRlElseProve = (RelativeLayout) convertView.findViewById(R.id.SumbitUserMateria_rl_Prove);
            vh.sumbitUserMaterialIvElseProve = (ImageView) convertView.findViewById(R.id.SumbitUserMaterial_iv_Prove);
            vh.sumbitUserMaterialTvElseProve = (TextView) convertView.findViewById(R.id.SumbitUserMaterial_tv_ElseProve);
            vh.sumbitUserMaterialIvElseUploading = (ImageView) convertView.findViewById(R.id.SumbitUserMaterial_iv_Uploading);
            vh.sumbitUserMaterialTvElseUp = (TextView) convertView.findViewById(R.id.SumbitUserMaterial_tv_ElseUp);

            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        vh.sumbitUserMaterialTvElseProve.setText(list.get(position).get("groupName").toString());
        String materialFlag =  list.get(position).get("materialFlag").toString();
        switch (materialFlag){//状态 0待审核， 1已审核，2未通过，3未上传
            case "0":
                vh.sumbitUserMaterialTvElseUp.setText("待审核");
                vh.sumbitUserMaterialIvElseUploading.setImageResource(R.mipmap.back_arrow);
                mPositionList.add(materialFlag);
                break;
            case "1":
                //TODO   已经通过审核就不能再继续点击了
                vh.sumbitUserMaterialTvElseUp.setText("已审核");
                vh.sumbitUserMaterialIvElseUploading.setImageResource(R.mipmap.checked_mark);
                mPositionList.add(materialFlag);
                break;
            case "2":
                vh.sumbitUserMaterialTvElseUp.setText("未通过");
                vh.sumbitUserMaterialIvElseUploading.setImageResource(R.mipmap.wei);
                mPositionList.add(materialFlag);
                break;
            case "3":
                vh.sumbitUserMaterialTvElseUp.setText("未上传");
                vh.sumbitUserMaterialIvElseUploading.setImageResource(R.mipmap.back_arrow);
                mPositionList.add(materialFlag);
                break;
        }
        return convertView;
    }


    protected class ViewHolder {
        private RelativeLayout sumbitUserMateriaRlElseProve;
        private ImageView sumbitUserMaterialIvElseProve;
        private TextView sumbitUserMaterialTvElseProve;
        private ImageView sumbitUserMaterialIvElseUploading;
        private TextView sumbitUserMaterialTvElseUp;
    }

    public List<String> getSuccessPosition(){
        return mPositionList;
    }
}
