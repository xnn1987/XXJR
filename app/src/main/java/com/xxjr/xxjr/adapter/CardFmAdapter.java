package com.xxjr.xxjr.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.PictureSeriesActivity;
import com.xxjr.xxjr.activity.WDCardActivity02;
import com.xxjr.xxjr.activity.WDCardSetActivity;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.lrucacheUtil.ImageCache;
import com.xxjr.xxjr.utils.CommonMath;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.xxjr.xxjr.utils.network.MyOkHttpUtils;
import com.ypy.eventbus.EventBus;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardFmAdapter extends BaseAdapter {

    private List<Map<String, Object>> mList;
    private Context context;
    private LayoutInflater layoutInflater;
    private ImageCache manager;
    private String catgory;
    private Activity activity;

    public CardFmAdapter(Activity activity, List<Map<String, Object>> mList, String catgory) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.mList = mList;
        this.catgory = catgory;
        this.layoutInflater = LayoutInflater.from(context);
        manager = ImageCache.getInstanse(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_cardfm, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.mTvName.setText(mList.get(position).get("title").toString());
        holder.mTvCount.setText(mList.get(position).get("collectCnt").toString());
        setImageHeight(holder,position);//  图片的设置

        //  收藏图片的  处理
        String myCollectCntStr = TextUtil.getTextToString(mList.get(position).get("myCollectCnt"));
        final boolean isCollected;
        if (myCollectCntStr.equals("0")){
            isCollected = false;
        }else {
            isCollected = true;
        }

        holder.mIvPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cardData(position);
            }
        });
        return convertView;
    }

    /**
     * 对 图片的  适配   高度
     * @param holder
     * @param position
     */
    private void setImageHeight(ViewHolder holder,int position){
        ViewGroup.LayoutParams params = holder.mIvPic.getLayoutParams();
        WindowManager wm = (WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
        int screen_w = wm.getDefaultDisplay().getWidth();
        double map_w = Double.parseDouble(mList.get(position).get("map_w").toString());
        double map_h = Double.parseDouble(mList.get(position).get("map_h").toString());
        double measureWidth =( screen_w-24* MyApplication.density)/2;
        double measureHeight = map_h*measureWidth/map_w;
        params.width = (int) measureWidth;
        params.height = (int) measureHeight;
        MyApplication.imageLoader.displayImage(mList.get(position).get("map_url").toString(),
                holder.mIvPic, MyApplication.options);
        // 给ImageView设置一个Tag，保证异步加载图片时不会乱序
//        holder.mIvPic.setTag(mList.get(position).get("map_url").toString());
//        holder.mIvPic.setImageResource(R.mipmap.jiazai);
//        manager.loadImages(holder.mIvPic, mList.get(position).get("map_url").toString(), false);
    }

    private void cardData(final int position){
        MyOkHttpUtils.postRequest(Urls.WD_CARD_SETTING_QUERY,null)
                .execute(new MapCallback(activity) {
                    @Override
                    public void onResponse(Map rMap) {
                        super.onResponse(rMap);
                        if (rMap!=null){
                            Map<String,Object> attrMap = (Map<String, Object>) rMap.get("attr");
                            boolean hasWd = (boolean) attrMap.get("haveWdCard");
                            if (hasWd){
                                Intent intent = new Intent(activity, PictureSeriesActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("id", mList.get(position).get("funnyId").toString());
                                bundle.putString("title", mList.get(position).get("title").toString());
                                bundle.putString("catgory", catgory);
                                bundle.putSerializable("attrMap", (Serializable) attrMap);
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            }else {
                                Intent intent = new Intent(context,WDCardSetActivity.class);
                                intent.putExtra(ConstantUtils.WDCARD_ISFIRST_WD_CARD,true);
                                context.startActivity(intent);
                            }
                        }

                    }
                });
    }



    protected class ViewHolder {
        public TextView mTvName;
        private TextView mTvCount;
        private ImageView mIvPic;

        public ViewHolder(View view) {
            mTvName = (TextView) view.findViewById(R.id.cardfm_tv_ivName);
            mTvCount = (TextView) view.findViewById(R.id.cardfm_tv_count);
            mIvPic = (ImageView) view.findViewById(R.id.cardfm_iv_pic);
        }
    }
}
