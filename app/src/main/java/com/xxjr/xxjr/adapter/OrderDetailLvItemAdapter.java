package com.xxjr.xxjr.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.OrderDetailActivity;
import com.xxjr.xxjr.activity.OrderFeedBackActivity;
import com.xxjr.xxjr.activity.OrderFlowActivity;
import com.xxjr.xxjr.activity.ProduceDetailActivity;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;

import java.util.List;
import java.util.Map;
public class OrderDetailLvItemAdapter extends BaseAdapter {

    private List<Map<String, Object>> list ;
    private Context context;
    private LayoutInflater layoutInflater;
    private int status;
    private String name;
    private OrderDetailActivity activity;
    private String[] produceStatus = {"提交申请材料","机构审核","机构批款","客户签约","客户领款","支付佣金"};


    public OrderDetailLvItemAdapter(OrderDetailActivity activity, List<Map<String, Object>> list, String name) {
        this.activity= activity;
        this.context = activity.getApplicationContext();
        this.list = list;
        this.name = name;
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
            convertView = layoutInflater.inflate(R.layout.order_detail_lv_item, null);
             vh = new ViewHolder();
            vh.mLlCompanyInfo = (LinearLayout) convertView.findViewById(R.id.IndentDetail_rl_companyInfo);
            //公司信息
            vh.mIvCompanyIcon = (ImageView) convertView.findViewById(R.id.IndentDetail_company_icon);
            vh.mtvCompanyName = (TextView) convertView.findViewById(R.id.OrderDetail_tv_company_name);
            vh.mTvCompanyMaterial = (TextView) convertView.findViewById(R.id.OrderDetail_tv_Material);
            //用户相关信息
            vh.mTvProduceName = (TextView) convertView.findViewById(R.id.OrderDetail_tv_producename);
            vh.mTvAppMoney = (TextView) convertView.findViewById(R.id.OrderDetail_tv_loadMoney);
            vh.mTvName = (TextView) convertView.findViewById(R.id.OrderDetail_tv_Name);
            vh.mTvRebate = (TextView) convertView.findViewById(R.id.OrderDetail_tv_Returnrebate);
            vh.mTvTime = (TextView) convertView.findViewById(R.id.OrderDetail_tv_time);


            vh.mBtnFeedBack = (Button) convertView.findViewById(R.id.IndentDetail_feedBack);
            vh.mBtnFlow = (Button) convertView.findViewById(R.id.OrderDetail_btn_flow);

            //监听
            setListener(vh.mLlCompanyInfo,vh.mBtnFeedBack,vh.mBtnFlow, position);

            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        //公司
        MyApplication.imageLoader.displayImage(Urls.RANQING + list.get(position).get("orgName").toString(),
                vh.mIvCompanyIcon, MyApplication.options);
        vh.mtvCompanyName.setText(list.get(position).get("orgName").toString());
        status = (int) list.get(position).get("status");
        switch (status){
            case 0:
                vh.mTvCompanyMaterial.setText(ConstantUtils.PRODUCE_STATUS_0);
                break;
            case 1:
                vh.mTvCompanyMaterial.setText(ConstantUtils.PRODUCE_STATUS_1);
                break;
            case 2:
                vh.mTvCompanyMaterial.setText(ConstantUtils.PRODUCE_STATUS_2);
                break;
            case 3:
//                vh.mTvCompanyMaterial.setText("机构处理中");
                vh.mTvCompanyMaterial.setText(produceStatus[((int) list.get(position).get("orgStatus"))]);//通过订单流程获取
                break;
            case 4:
                vh.mTvCompanyMaterial.setText(ConstantUtils.PRODUCE_STATUS_4);
                break;
            case 5:
                vh.mTvCompanyMaterial.setText(ConstantUtils.PRODUCE_STATUS_5);
                break;
            case 6:
                vh.mTvCompanyMaterial.setText(ConstantUtils.PRODUCE_STATUS_6);
                break;
            case 7:
                vh.mTvCompanyMaterial.setText(ConstantUtils.PRODUCE_STATUS_7);
                break;
        }

        //详情
        int rewardRate1 = (int) Double.parseDouble(list.get(position).get("rewardRate1").toString());
        int rewardRate2 = (int) Double.parseDouble(list.get(position).get("rewardRate2").toString());
        vh.mTvProduceName.setText(list.get(position).get("loanIntro").toString());
        vh.mTvAppMoney.setText(list.get(position).get("approveAmount").toString());
        vh.mTvRebate.setText(rewardRate1+"%-"+rewardRate2+"%");
        vh.mTvName.setText(name);
        vh.mTvTime.setText(list.get(position).get("createTime").toString());
        return convertView;
    }

    private void setListener(LinearLayout mLlCompanyInfo, Button mBtnFeedBack, Button mBtnFlow, final int position) {
        final Intent intent = new Intent();
        //  详情订单
        mLlCompanyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(context, ProduceDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("loanId", list.get(position).get("loanId").toString());
                intent.putExtra("type", ConstantUtils.COMPANY_PRODUCE_CONNACT_TYPE);
                intent.putExtra("companyName", list.get(position).get("loanName").toString());
                intent.putExtra("produceType","");
                intent.putExtra("telephone", activity.telephone);
                intent.putExtra("serviceName",activity.serviceName);
                context.startActivity(intent);
            }
        });
        //订单流程
        mBtnFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(context, OrderFlowActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orgStatus", list.get(position).get("orgStatus").toString());
                context.startActivity(intent);
            }
        });
        //订单反馈
        mBtnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(context, OrderFeedBackActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dtlId", list.get(position).get("dtlId").toString());
                intent.putExtra("status",status);
                intent.putExtra("orgName", list.get(position).get("orgName").toString());
                context.startActivity(intent);
            }
        });
    }


    protected class ViewHolder {
        private ImageView mIvCompanyIcon;
        private LinearLayout mLlCompanyInfo;
        private TextView mtvCompanyName,mTvCompanyNumber, mTvCompanyMaterial;//公司信息
        private TextView mTvProduceName,mTvAppMoney, mTvName,mTvRebate;//用户信息
        private TextView mTvTime;//时间
        private Button mBtnFeedBack,mBtnFlow;//流程   反馈
    }


}
