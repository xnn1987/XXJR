package com.xxjr.xxjr.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.tongxun.UserModel;

import java.util.List;

public class ContractAdapter extends BaseAdapter implements SectionIndexer {
	private List<UserModel> list = null;
	private Context context = null;
	public Boolean[] statues;

	public ContractAdapter(Context context, List<UserModel> list) {
		this.context = context;
		this.list = list;
		statues = new Boolean[list.size()];
		for (int i=0;i<list.size();i++){
			statues[i] = false;
		}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
			ViewUtils.inject(mHolder, convertView);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		UserModel userModel = list.get(position);
		mHolder.mTvNmae.setText(userModel.getUesrname());
		mHolder.mTvNumber.setText(userModel.getTel());
		mHolder.mIvIcon.setImageResource(R.mipmap.lanyuan);
		final ViewHolder finalMHolder = mHolder;
		mHolder.mLlIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (statues[position] == false){
					finalMHolder.mIvIcon.setImageResource(R.mipmap.langou);
				}else {
					finalMHolder.mIvIcon.setImageResource(R.mipmap.lanyuan);
				}
				statues[position] = !statues[position];
			}
		});

		int section = getSectionForPosition(position);
		if (position == getPositionForSection(section)) {
			// 第一次出现该section
			mHolder.mTvalpha.setVisibility(View.VISIBLE);
			mHolder.mTvalpha.setText(userModel
					.getFirstLetter());
			mHolder.mTvalpha.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		} else {
			mHolder.mTvalpha.setVisibility(View.GONE);
		}


		return convertView;
	}

	class ViewHolder {
		@ViewInject(R.id.Contact_tv_name)
		private TextView mTvNmae;
		@ViewInject(R.id.Contact_tv_number)
		private TextView mTvNumber;
		@ViewInject(R.id.Contact_tvalpha)
		private TextView mTvalpha;
		@ViewInject(R.id.Contact_tv_icon)
		private ImageView mIvIcon;
		@ViewInject(R.id.Contact_ll_icon)
		private LinearLayout mLlIcon;
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
		for (int i = 0; i < getCount(); i++) {
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

	public Boolean[] getStatues() {
		return statues;
	}
}
