package com.xxjr.xxjr.photo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImagePublishAdapter extends BaseAdapter {
    private List<String> mDataList = new ArrayList<>();
    private Context mContext;
    private int allPicCount;
    private int childPosition;//  子空间所在位置
    private Handler handler;

    public ImagePublishAdapter(Context context, List<String> dataList, Integer allPicCount, int childPosition) {
        this.mContext = context;
        this.mDataList = dataList;
        this.allPicCount = allPicCount;
        this.childPosition = childPosition;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getCount() {
        // 多返回一个用于展示添加图标
        if (mDataList == null) {
            return 1;
        } else if (mDataList.size() == allPicCount) {
            return allPicCount;
        } else {
            return mDataList.size() + 1;
        }
    }

    public Object getItem(int position) {
        if (mDataList != null && mDataList.size() == allPicCount) {
            return mDataList.get(position);
        } else if (mDataList == null || position - 1 < 0
                || position > mDataList.size()) {
            return null;
        } else {
            return mDataList.get(position - 1);
        }
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    public View getView(final int position, View convertView, ViewGroup parent) {
        //所有Item展示不满一页，就不进行ViewHolder重用了，避免了一个拍照以后添加图片按钮被覆盖的奇怪问题
        convertView = View.inflate(mContext, R.layout.photo_item_publish, null);
        ImageView imageIv = (ImageView) convertView.findViewById(R.id.item_grid_image);
        ImageView mIvDel = (ImageView) convertView.findViewById(R.id.item_grid_del);
        if (isShowAddItem(position)) {
            imageIv.setImageResource(R.drawable.photo_btn_add_pic);
            imageIv.setBackgroundResource(R.drawable.image_shape_coners_line_gray);
            mIvDel.setVisibility(View.GONE);
        } else {
            MyApplication.imageLoader.displayImage(Urls.RANQING + Urls.BUSSINESS_CUSTCARD_HEADICON +
                            mDataList.get(position) + "&UUID=" + MyApplication.device + "&uid=" + MyApplication.uid,
                    imageIv, MyApplication.options);
            mIvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadDelPic(Urls.DEL_MATERIAL_PIC, position);
                }
            });

        }

        return convertView;
    }

    private boolean isShowAddItem(int position) {
        int size = mDataList == null ? 0 : mDataList.size();
        return position == size;
    }

    /**
     * 删除链接
     *
     * @param url
     */
    private void uploadDelPic(String url, final int position) {
        handler.sendEmptyMessage(ConstantUtils.DEL_LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("materialImg", mDataList.get(position));
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Message message = handler.obtainMessage();
                        message.what = ConstantUtils.DEL_PIC_SUCCESS;
                        message.arg1 = childPosition;//  item 中所在的位置
                        message.arg2 = position;  //   gridview 子控件所在的位置
                        handler.sendMessage(message);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.DEL_PIC_FAIL);
                    }
                });
    }

}
