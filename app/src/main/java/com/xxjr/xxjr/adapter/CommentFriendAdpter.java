package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.custview.CustViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by hwf on 2016/1/29.
 */
public class CommentFriendAdpter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String, Object>> list;
    private int screenWidth;
    private float textWidth = 0;
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public CommentFriendAdpter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
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
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();
        convertView = layoutInflater.inflate(R.layout.contact_my_contact_lv_item, null);
        viewHolder.custConcatRealNameText = (TextView) convertView.findViewById(R.id.cust_concat_realname);
        viewHolder.custConcatTagText = (CustViewGroup) convertView.findViewById(R.id.concat_cust_concat_tag);
        Map<String, Object> map = list.get(position);
        if (map.get("realName").toString().length() > 4) {
            String realName = map.get("realName").toString().substring(0, 4) + " ...";
            viewHolder.custConcatRealNameText.setText(realName);
        } else {
            viewHolder.custConcatRealNameText.setText(map.get("realName").toString());
        }

        if (map.get("tagNames") != null) {
            viewHolder.custConcatTagText.removeAllViews();

            String[] tagNames = map.get("tagNames").toString().split(",");
            for (int i = 0; i < tagNames.length; i++) {
                textWidth += mTextPaint.measureText(tagNames[i]);

                TextView tagTextView = new TextView(context);
                tagTextView.setText(tagNames[i]);
                tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tagTextView.setTextColor(Color.parseColor("#9a9a9a"));
                viewHolder.custConcatTagText.addView(tagTextView);

                textWidth += mTextPaint.measureText("  ")+tagTextView.getPaddingLeft()+tagTextView.getPaddingRight();
            }
            textWidth = 0;
        }
        return convertView;
    }

    protected class ViewHolder {
        private TextView custConcatRealNameText;
        private CustViewGroup custConcatTagText;
    }
}

