package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xxjr.xxjr.Interface.WdServeCity;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.WdServeCityEven;
import com.xxjr.xxjr.tongxun.UserModel;
import com.ypy.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class WD_ChoiceFuWuCityAdapter extends RecyclerView.Adapter<WD_ChoiceFuWuCityAdapter.MyViewHolder> implements SectionIndexer {
    private List<UserModel> list = null;
    private Context context = null;
    public Boolean[] statues;

    public WD_ChoiceFuWuCityAdapter(List<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
        statues = new Boolean[list.size()];
        for (int i=0;i<list.size();i++){
            statues[i] = false;
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder mHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.wd_choicecity_item, parent, false));
        return mHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder mHolder, final int position) {
        UserModel userModel = list.get(position);
        mHolder.mTvCity.setText(userModel.getCity());

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            // 第一次出现该section
            mHolder.item_firstletter.setVisibility(View.VISIBLE);
            mHolder.item_firstletter.setText(userModel.getFirstLetter());
            mHolder.item_firstletter.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            mHolder.item_firstletter.setVisibility(View.GONE);
        }
        if (statues[position]){
            mHolder.mIvStatus.setImageResource(R.mipmap.langou);
        }else {
            mHolder.mIvStatus.setImageResource(R.mipmap.lanyuan);
        }

        mHolder.mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statues[position]){
                    statues[position] = true;
                    mHolder.mIvStatus.setImageResource(R.mipmap.langou);
                }else {
                    statues[position] = false;
                    mHolder.mIvStatus.setImageResource(R.mipmap.lanyuan);
                }

                WdServeCityEven bean = new WdServeCityEven();
                bean.setPositon(position);
                EventBus.getDefault().post(bean);

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvCity;
        private TextView item_firstletter;
        private ImageView mIvStatus;
        private RelativeLayout mRlItem;
        public MyViewHolder(View view)
        {
            super(view);
            mTvCity = (TextView) view.findViewById(R.id.choicecity_tv_city);
            item_firstletter = (TextView) view.findViewById(R.id.choicecity_item_firstletter);
            mIvStatus = (ImageView) view.findViewById(R.id.choicecity_iv_icon);
            mRlItem = (RelativeLayout) view.findViewById(R.id.choicecity_rl_item);


        }

    }

    @Override
    public Object[] getSections() {
        return null;
    }

    // 做字母索引的时候常常会用到SectionIndexer这个接口
    // 1. getSectionForPosition() 通过该项的位置，获得所在分类组的索引号
    // 2. getPositionForSection() 根据分类列的索引号获得该序列的首个位置

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String firstLetter = list.get(i).getFirstLetter();
            char firstChar = firstLetter.charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    // 根据position获取分类的首字母的Char ascii值
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstLetter().charAt(0);
    }

    public void updataImageSelected(boolean isSelected,int position){
        if (isSelected){

        }
    }
}
