package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.LoginActivity;
import com.xxjr.xxjr.activity.SalaryOrderActivity;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.ChoiceProduceBean;
import com.xxjr.xxjr.utils.TextUtil;
import com.ypy.eventbus.EventBus;

import java.util.List;
import java.util.Map;

public class CompanyProduceAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater layoutInflater;
    private String prgName;
    private List<Map<String,Object>> mList ;

    public CompanyProduceAdapter(Context context, List<Map<String,Object>> list, String prgName) {
        this.context = context;
        this.mList = list;
        this.layoutInflater = LayoutInflater.from(context);
        this.prgName = prgName;
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
            convertView = layoutInflater.inflate(R.layout.company_produce_lv_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.companyproduceTvRebate = (TextView) convertView.findViewById(R.id.companyproduce_tv__rebate);
            viewHolder.companyproduceBtnCommit = (Button) convertView.findViewById(R.id.companyproduce_btn_commit);
            viewHolder.orderDetailTvPoundage = (TextView) convertView.findViewById(R.id.OrderDetail_tv_poundage);
            viewHolder.companyProduceTvCooperation = (TextView) convertView.findViewById(R.id.CompanyProduce_tv_cooperation);
            viewHolder.companyProduceTvMonthCost = (TextView) convertView.findViewById(R.id.CompanyProduce_tv_MonthCost);
            viewHolder.orderDetailTvMoney = (TextView) convertView.findViewById(R.id.OrderDetail_tv_money);
            viewHolder.companyproduceTvName = (TextView) convertView.findViewById(R.id.CompanyProdece_tv_name);
            viewHolder.companyproduceTvAuditCount = (TextView) convertView.findViewById(R.id.companyProcuce_tv_loan);

            convertView.setTag(viewHolder);
        }

        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.companyproduceTvName.setText(TextUtil.getTextToString(mList.get(position).get("loanIntro")));
        vh.companyproduceTvRebate.setText(mList.get(position).get("rewardRate1")+"%"+"-"+mList.get(position).get("rewardRate2")+"%");
        vh.orderDetailTvPoundage.setText(TextUtil.getTextToString(mList.get(position).get("feeRate"))+"%");
        vh.companyProduceTvMonthCost.setText(mList.get(position).get("rateMin")+"-"+mList.get(position).get("rateMax")+"%");
        vh.companyProduceTvCooperation.setText(TextUtil.getTextToString(mList.get(position).get("applyCount")));
        vh.companyproduceTvAuditCount.setText(TextUtil.getTextToString(mList.get(position).get("auditCount")));

        vh.companyproduceBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.userInfo==null){
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else  {
                    Intent intent = new Intent(context, SalaryOrderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("loadId", (int)mList.get(position).get("loanId"));
                    intent.putExtra("loanType", mList.get(position).get("loanType").toString());
                    intent.putExtra("prgName", prgName + "-" + mList.get(position).get("loanIntro").toString());

                    ChoiceProduceBean choiceProduceBean = new ChoiceProduceBean();
                    choiceProduceBean.setLoadId((int) mList.get(position).get("loanId"));
                    choiceProduceBean.setLoanType(mList.get(position).get("loanType").toString());
                    choiceProduceBean.setUrl("-1");
                    choiceProduceBean.setTitleName(prgName + "-" + mList.get(position).get("loanIntro").toString());
                    EventBus.getDefault().post(choiceProduceBean);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    protected class ViewHolder {
        private TextView companyproduceTvName;
        private TextView companyproduceTvAuditCount;
        private TextView companyproduceTvRebate;
        private Button companyproduceBtnCommit;
        private TextView orderDetailTvPoundage;
        private TextView companyProduceTvCooperation;
        private TextView companyProduceTvMonthCost;
        private TextView orderDetailTvMoney;
    }
}
