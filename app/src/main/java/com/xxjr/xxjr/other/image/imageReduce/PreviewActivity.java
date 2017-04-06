package com.xxjr.xxjr.other.image.imageReduce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.UploadReturnDataEven;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.CommonUploadPicture;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.ypy.eventbus.EventBus;

public class PreviewActivity extends SlidBackActivity {
	private ImageView preview;
	private Bitmap bitmap = null;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case ConstantUtils.UPLOADING:
					AppUtil.showProgress(PreviewActivity.this,"正在上传请等待");
					break;
				case ConstantUtils.UPLOAD_SUCCESS:
					Bundle bundle = msg.getData();
					String jsonReturnData = bundle.getString(ConstantUtils.UPLOAD_RETURN_DATAS);
					UploadReturnDataEven even = new UploadReturnDataEven();
					even.setSingleImg(jsonReturnData);
					EventBus.getDefault().post(even);
					setResult(RESULT_OK);
					finish();
					AppUtil.dismissProgress();
					break;
				case ConstantUtils.UPLOAD_FAIL:
					AppUtil.errodDoanload("网络不稳定");
					break;
			}
		}
	};
	private String uploadType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_preview);
		setTitleBars();
		getIntentDatas();

		preview = (ImageView) this.findViewById(R.id.iv_preview);
		if (bitmap != null) {
			preview.setImageBitmap(bitmap);
		}
		Log.e("bitmap size  = ", bitmap.getByteCount()+"");  /* 1366076   1340676*/   /*62600000    1340676*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bitmap = null;
	}

	private void setTitleBars() {
		SetTitleBar.setTitleText(this,"预览图片","保存");
		findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new CommonUploadPicture().uploadFileImg(bitmap,Urls.RANQING + Urls.CUST_UPLOAD_PICTURE + uploadType, handler);
			}
		});
	}

	public void getIntentDatas() {
		Intent in = getIntent();
		if (in != null) {
			byte[] bis = in.getByteArrayExtra("bitmap");
			bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
			uploadType = in.getStringExtra(ConstantUtils.UPLOAD_HEAD_TPYE);
		}
	}
}
