package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.ChangeTagEvenbus;
import com.xxjr.xxjr.custview.CustViewGroup;
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/1.
 */
public class ConcatDelAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String, Object>> list;
    private List<Boolean> isCheckList = new ArrayList<>();
    private List<Integer> mPosList = new ArrayList<>();
    private String tagName;
    private int screenWidth;
    private float textWidth = 0;
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public ConcatDelAdapter(Context context, List<Map<String, Object>> list, String tagName) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.tagName = tagName;
        this.list = list;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        for (int i = 0; i < list.size(); i++) {
            isCheckList.add(false);
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh = new ViewHolder();
//        vh = new ViewHolder();
        convertView = layoutInflater.inflate(R.layout.choice_constance_item, null);
        vh.mTvRealName = (TextView) convertView.findViewById(R.id.choiceConstance_realname);
        vh.mLlConcatTag = (CustViewGroup) convertView.findViewById(R.id.choiceConstance_concat_tag);
        vh.mIvCheck = (ImageView) convertView.findViewById(R.id.choiceConstance_iv_image);
        vh.mLlItem = (LinearLayout) convertView.findViewById(R.id.ChoiceConstance_ll_item);
        Map<String, Object> map = list.get(position);
        if (map.get("realName").toString().length()>4) {
            String realName  = map.get("realName").toString().substring(0, 4)+" ...";
            vh.mTvRealName.setText(realName);
        }else {
            vh.mTvRealName.setText(map.get("realName").toString());
        }
        if (map.get("tagNames") != null) {
            vh.mLlConcatTag.removeAllViews();
            String[] tagNames = map.get("tagNames").toString().split(",");
            for (int i = 0; i < tagNames.length; i++) {

//                textWidth += mTextPaint.measureText(tagNames[i]);
//                if (textWidth > (screenWidth / 4)) {
//                    TextView spaceTextView = new TextView(context);//间隔
//                    spaceTextView.setText("...");
//                    vh.mLlConcatTag.addView(spaceTextView);
//                    break;
//                }

                TextView tagTextView = new TextView(context);
                tagTextView.setTextColor(Color.parseColor("#9a9a9a"));
                tagTextView.setText(tagNames[i]);
                vh.mLlConcatTag.addView(tagTextView);

            }
            textWidth = 0;
        }
        vh.mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheckList.get(position)) {
                    vh.mIvCheck.setVisibility(View.VISIBLE);
                    isCheckList.add(position, true);
                    mPosList.add(position);
                } else {
                    vh.mIvCheck.setVisibility(View.GONE);
                    isCheckList.add(position, false);
                    for (int i = 0; i < mPosList.size(); i++) {
                        if (position == mPosList.get(i)) {
                            mPosList.remove(i);
                        }
                    }
                }
                ChangeTagEvenbus changeTagEvenbus = new ChangeTagEvenbus();
                changeTagEvenbus.setList(mPosList);
                EventBus.getDefault().post(changeTagEvenbus);

            }
        });
        return convertView;
    }

    protected class ViewHolder {
        private TextView mTvRealName;
        private CustViewGroup mLlConcatTag;
        private ImageView mIvCheck;
        private LinearLayout mLlItem;
    }
}
