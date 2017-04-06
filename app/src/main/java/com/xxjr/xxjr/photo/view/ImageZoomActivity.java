package com.xxjr.xxjr.photo.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.photo.util.IntentConstants;

import java.util.ArrayList;

public class ImageZoomActivity extends Activity
{

	private ViewPager pager;
	private MyPageAdapter adapter;
	private int currentPosition;
	private ArrayList<String> mDataList = new ArrayList<>();

//	private RelativeLayout photo_relativeLayout;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_zoom);

//		photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
//		photo_relativeLayout.setBackgroundColor(0x70000000);

		initData();
/*
//		Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
//		photo_bt_exit.setOnClickListener(new View.OnClickListener()
//		{
//			public void onClick(View v)
//			{
//				finish();
//			}
//		});
//		Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
		photo_bt_del.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if (mDataList.size() == 1)
				{
					removeImgs();
					finish();
				}
				else
				{
					removeImg(currentPosition);
					pager.removeAllViews();
					adapter.removeView(currentPosition);
					adapter.notifyDataSetChanged();
				}
//				PhoteZoomEvenbus evenbus = new PhoteZoomEvenbus();
//				evenbus.setPosition(currentPosition);
//				EventBus.getDefault().post(evenbus);
			}
		});*/

		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(pageChangeListener);

		adapter = new MyPageAdapter(mDataList);
		pager.setAdapter(adapter);
		pager.setCurrentItem(currentPosition);
	}

	private void initData()
	{
		currentPosition = getIntent().getIntExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, 0);
		mDataList = (ArrayList<String>) getIntent().getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);//TODO
	}

	private void removeImgs()
	{
		mDataList.clear();
	}

	private void removeImg(int location)
	{
		if (location + 1 <= mDataList.size())
		{
			mDataList.remove(location);
		}
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener()
	{

		public void onPageSelected(int arg0)
		{
			currentPosition = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		public void onPageScrollStateChanged(int arg0)
		{

		}
	};

	class MyPageAdapter extends PagerAdapter
	{
		private ArrayList<String> dataList = new ArrayList<>();
		private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

		public MyPageAdapter(ArrayList<String> dataList)
		{
			this.dataList = dataList;
			int size = dataList.size();
			for (int i = 0; i != size; i++)
			{
				ImageView iv = new ImageView(ImageZoomActivity.this);
//				ImageDisplayer.getInstance(ImageZoomActivity.this).displayBmp(
//						iv, null, dataList.get(i).sourcePath, false);
				MyApplication.imageLoader.displayImage(Urls.RANQING+ Urls.BUSSINESS_CUSTCARD_HEADICON+dataList.get(i)
						+"&UUID=" + MyApplication.device + "&uid=" + MyApplication.uid,iv, MyApplication.options);
				iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
				mViews.add(iv);
			}
		}

		public int getItemPosition(Object object)
		{
			return POSITION_NONE;
		}

		public Object instantiateItem(View arg0, int arg1)
		{
			ImageView iv = mViews.get(arg1);
			((ViewPager) arg0).addView(iv);
			return iv;
		}

		public void destroyItem(View arg0, int arg1, Object arg2)
		{
			if (mViews.size() >= arg1 + 1)
			{
				((ViewPager) arg0).removeView(mViews.get(arg1));
			}
		}

		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public int getCount()
		{
			return dataList.size();
		}

		public void removeView(int position)
		{
			if (position + 1 <= mViews.size())
			{
				mViews.remove(position);
			}
		}

	}
}