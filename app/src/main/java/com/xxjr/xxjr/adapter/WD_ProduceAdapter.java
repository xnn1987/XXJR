package com.xxjr.xxjr.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.WD_GeRenAlterActivity;
import com.xxjr.xxjr.activity.WD_QiYeAlterActivity;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.Shared.Shared;
import com.xxjr.xxjr.utils.TextUtil;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

public class WD_ProduceAdapter extends BaseAdapter {

    private List<Map<String,Object>> mList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private Activity activity ;
    private Handler handler;
    private RotateAnimation rotateAnimator;

    public WD_ProduceAdapter(Activity activity, List<Map<String,Object>> objects,Handler handler) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        mList = objects;
        this.layoutInflater = LayoutInflater.from(context);
        this.handler = handler;
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.wd_prodce_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        final ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.mTvCommpany.setText(mList.get(position).get("productName").toString());
        vh.mTvInitial.setText(vh.mTvCommpany.getText().toString().substring(0,1));
        vh.mTvTime.setText(mList.get(position).get("createTime").toString());
        String loadRequest = "贷款要求："+mList.get(position).get("proDesc").toString();
        vh.mTvLoadRequest.setText(TextUtil.changColor(loadRequest,Color.parseColor("#333333"),0,5));
        String ayylyRequest = "申请要求："+mList.get(position).get("proDtlDesc").toString();
        vh.mTvApplyRequest.setText(TextUtil.changColor(ayylyRequest,Color.parseColor("#333333"),0,5));

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        vh.contentLayout.measure(w,h);
        DebugLog.e("heigh  contentLayout  ",vh.contentLayout.getMeasuredHeight()+"");

        if (mList.get(position).get("brief")!=null ) {
            DebugLog.e("brief", mList.get(position).get("brief").toString());

            String otherDesc = "其他说明：" + mList.get(position).get("brief").toString();
            vh.mTvOtherDesc.setText(TextUtil.changColor(otherDesc, Color.parseColor("#333333"), 0, 5));
        }
        vh.mTvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                final Dialog dialog = builder.create();


                View view = layoutInflater.inflate(R.layout.dialog_wd_del,null);
                dialog.show();
                Window window = dialog.getWindow();
                 WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
               window.setAttributes(lp);

                window.setGravity(Gravity.CENTER);
                window.setContentView(view);
                TextView mTvContent = (TextView) view.findViewById(R.id.dialog_del_content);
                mTvContent.setText("确定删除改产品 ？");
                view.findViewById(R.id.dialog_del_sure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delProduce(Urls.WD_PRODUCE_DEL,position,dialog);
                    }
                });
                view.findViewById(R.id.dialog_del_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        vh.mTvAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadDatas(Urls.WD_PRODUCE_QUERY,position);
            }
        });
        vh.mLlSharedProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.sharedWeiDian(activity);
            }
        });

        /*vh.mLlClickShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vh.contentLayout.isShown()) {
                    vh.mTvShow.setText("展开详情");
                    collapse(vh.contentLayout,vh.mIvShow);
                } else {
                    vh.mTvShow.setText("收起");
                    expand(vh.contentLayout,vh.mIvShow);
                }
            }
        });*/

        return convertView;
    }


    protected class ViewHolder {
        private TextView mTvInitial;
        private TextView mTvCommpany;
        private TextView mTvTime;
        private TextView mTvLoadRequest;
        private TextView mTvApplyRequest;
        private TextView mTvOtherDesc;
        private LinearLayout mLlSharedProduce,contentLayout;
        private TextView mTvDel;
        private TextView mTvAlter;
        private ImageView mIvShow;
        private TextView mTvShow;

        public ViewHolder(View view) {
            mTvInitial = (TextView) view.findViewById(R.id.wdproduce_tv_initial);
            mTvCommpany = (TextView) view.findViewById(R.id.wdproduce_tv_company);
            mTvTime = (TextView) view.findViewById(R.id.wdproduce_tv_time);
            mTvLoadRequest = (TextView) view.findViewById(R.id.wdproduce_tv_loadRequest);
            mTvApplyRequest = (TextView) view.findViewById(R.id.wdproduce_tv_applyRequest);
            mTvOtherDesc = (TextView) view.findViewById(R.id.wdproduce_tv_other);
            mLlSharedProduce = (LinearLayout) view.findViewById(R.id.wdproduce_ll_shared);
            mTvDel = (TextView) view.findViewById(R.id.wdproduce_tv_del);
            mTvAlter = (TextView) view.findViewById(R.id.wdproduce_tv_alter);
//            mIvShow = (ImageView) view.findViewById(R.id.wdproduce_iv_show);
//            mTvShow = (TextView) view.findViewById(R.id.wdproduce_tv_show);
            contentLayout = (LinearLayout) view.findViewById(R.id.wdproduce_ll_show);
        }
    }

    private void downLoadDatas(String url, final int position){
        AppUtil.showProgress(activity, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("productId",mList.get(position).get("productId").toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        Intent intent  = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("map", (Serializable) map);
                        intent.putExtras(bundle);
                        if (mList.get(position).get("serviceType").toString().equals("1")){
                            intent.setClass(context,WD_GeRenAlterActivity.class);
                        }else if (mList.get(position).get("serviceType").toString().equals("2")){
                            intent.setClass(context,WD_QiYeAlterActivity.class);
                        }
                        activity.startActivityForResult(intent,2);
                        AppUtil.dismissProgress();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }

    private void delProduce(String url, final int position, final Dialog dialog){
        AppUtil.showProgress(activity, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("productId",mList.get(position).get("productId").toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        if ((boolean)map.get("success")){
                            dialog.dismiss();
                            Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
                        }
                        AppUtil.dismissProgress();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }

    /**
     * Expand animation to display the discoverable content.
     */
    public void expand(ViewGroup contentLayout,ImageView rightIcon) {
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        contentLayout.measure(w,h);
        int heigh  = contentLayout.getMeasuredHeight();


        //set Visible
        contentLayout.setVisibility(View.VISIBLE);
        int x = rightIcon.getMeasuredWidth() / 2;
        int y = rightIcon.getMeasuredHeight() / 2;

        rotateAnimator = new RotateAnimation(0f, 180, x, y);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(Animation.ABSOLUTE);
        rotateAnimator.setFillAfter(true);
        rotateAnimator.setDuration(ConstantUtils.DURATION);
        rightIcon.startAnimation(rotateAnimator);
        int measuredHeight = contentLayout.getMeasuredHeight();
        ValueAnimator animator = slideAnimator(0, measuredHeight,contentLayout);
        animator.start();
    }

    /**
     * Collapse animation to hide the discoverable content.
     */
    public void collapse(final ViewGroup contentLayout, ImageView rightIcon) {

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        contentLayout.measure(w, h);
        int height = contentLayout.getMeasuredHeight();
        int width = contentLayout.getMeasuredWidth();

        DebugLog.e("heigh  01  ",height+"");





        int finalHeight = contentLayout.getMeasuredHeight();

        DebugLog.e("heigh  02  ",finalHeight+"");

        int x = rightIcon.getMeasuredWidth() / 2;
        int y = rightIcon.getMeasuredHeight() / 2;

        rotateAnimator = new RotateAnimation(180, 0f, x, y);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(Animation.ABSOLUTE);
        rotateAnimator.setFillAfter(true);
        rotateAnimator.setDuration(ConstantUtils.DURATION);

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0,contentLayout);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                contentLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        rightIcon.startAnimation(rotateAnimator);
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final ViewGroup contentLayout) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(ConstantUtils.DURATION);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = contentLayout.getLayoutParams();
                layoutParams.height = value;


                contentLayout.setLayoutParams(layoutParams);
                contentLayout.invalidate();

                /*if (outsideContentLayoutList != null && !outsideContentLayoutList.isEmpty()) {

                    for (ViewGroup outsideParam : outsideContentLayoutList) {
                        ViewGroup.LayoutParams params = outsideParam.getLayoutParams();

                        if (outsideContentHeight == 0) {
                            outsideContentHeight = params.height;
                        }

                        params.height = outsideContentHeight + value;
                        outsideParam.setLayoutParams(params);
                        outsideParam.invalidate();
                    }
                }*/
            }
        });
        return animator;
    }


}
