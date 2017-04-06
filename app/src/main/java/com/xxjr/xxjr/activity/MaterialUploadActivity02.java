package com.xxjr.xxjr.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.OderDetailMateriaCountEvenbus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.CustGridview;
import com.xxjr.xxjr.photo.adapter.ImagePublishAdapter;
import com.xxjr.xxjr.photo.bean.PhotoEvenbus;
import com.xxjr.xxjr.photo.model.ImageItem;
import com.xxjr.xxjr.photo.util.CustomConstants;
import com.xxjr.xxjr.photo.util.DensityUtil;
import com.xxjr.xxjr.photo.util.IntentConstants;
import com.xxjr.xxjr.photo.view.ImageBucketChooseActivity;
import com.xxjr.xxjr.photo.view.ImageZoomActivity;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.FileUpload;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaterialUploadActivity02 extends SlidBackActivity {

    private int allPicCount = 0;
    private int displayCount = 0;
    public  int availableSize = 9;
    private final int NOTIFYADAPTER_ADD = 10;
    private LinearLayout mLlContain;
    private int applyId;
    private List<Map<String, Object>> mMaterialList;
    private List<List<String>> mAllList = new ArrayList<>();
    private List<ImagePublishAdapter> mGvList = new ArrayList<>();
    private String title = "";
    private ImagePublishAdapter mAdapter;
    private String materialType;
    private int clickPosition = 0;//点击所在的位置， 以及删除所在的位置
    private List<File> mFileList = new ArrayList<>();
    private List<Integer> mAvailableSizeList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.UPLOADING:
                    AppUtil.showProgress(MaterialUploadActivity02.this,ConstantUtils.DIALOG_UPLOADING,-1);
                    break;
                case ConstantUtils.UPLOAD_SUCCESS:
                    sentOrderDetail();
                    AppUtil.dismissProgress();
                    break;
                case ConstantUtils.UPLOAD_FAIL:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_SHOW);
                    break;

                case ConstantUtils.UPLOAD_RETURN_DATA:
                    Bundle bundle = msg.getData();
                    List<String> returnImaList =(List<String>) bundle.getSerializable("returnImaList");
                    addChildView(returnImaList);
                    break;

                case NOTIFYADAPTER_ADD:
                    //  更新所在位置的  是否要添加的图片隐藏不隐藏
                    getAcailableSize(clickPosition);//改变  是否要添加图片
                    mAvailableSizeList.add(clickPosition, allPicCount);//  替换gridview适配availablesize,
                    mGvList.get(clickPosition).notifyDataSetChanged();

                    break;
                case ConstantUtils.DEL_PIC_SUCCESS:
                    sentOrderDetail();
                    AppUtil.dismissProgress();
                    clickPosition = msg.arg1;
                    int gridviewChildItem = msg.arg2;
                    mAllList.get(clickPosition).remove(gridviewChildItem);
                    mGvList.get(clickPosition).notifyDataSetChanged();
                    break;
                case ConstantUtils.DEL_LOADING:
                    AppUtil.showProgress(MaterialUploadActivity02.this, "正在删除,请等待...");
                    break;
                case ConstantUtils.DEL_PIC_FAIL:
                    AppUtil.errodDoanload("网络不稳定");
                    break;
                
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_material_upload02);
        getIntentDatas();
        SetTitleBar.setTitleText(MaterialUploadActivity02.this, title);
        initViews();
        addGridViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO   清楚 list 容器
        mAllList.clear();
        mGvList.clear();
        EventBus.getDefault().unregister(this);
    }

    //改变了上传图片的状态  数量，  需要通知orderdetailActivity
    public void sentOrderDetail (){
        OderDetailMateriaCountEvenbus evenbus = new OderDetailMateriaCountEvenbus();
        evenbus.setRefresh(true);
        EventBus.getDefault().post(evenbus);
    }

    /**
     * 添加 gridview
     */
    private void addGridViews() {
        for (int i = 0; i < mMaterialList.size(); i++) {
            Map<String, Object> map = mMaterialList.get(i);
            ArrayList< String> picList = (ArrayList<String>) map.get("uploadedList");
            mAllList.add(picList);

            mLlContain.addView(addTextview(//字体
                    mMaterialList.get(i).get("materialName").toString(),
                    mMaterialList.get(i).get("maxCount").toString()));

            final CustGridview gridview = new CustGridview(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(DensityUtil.px2dip(getApplicationContext(), 22), 0,
                    DensityUtil.px2dip(getApplicationContext(), 22),
                    DensityUtil.px2dip(getApplicationContext(), 38));
            params.gravity = Gravity.CENTER;
            gridview.setLayoutParams(params);
            gridview.setNumColumns(3);
            //todo
            getAcailableSize(i);//先进行判断  availableSize  的值
            mAvailableSizeList.add(allPicCount);
            mAdapter = new ImagePublishAdapter(this, mAllList.get(i), mAvailableSizeList.get(i),i);//  availableSize  以及点击所在的位置
            mAdapter.setHandler(handler);

            gridview.setAdapter(mAdapter);
            final int finalI = i;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickPosition = finalI;
                    if (position == getDataSize(finalI)) {
                        new PopupWindows(MaterialUploadActivity02.this, gridview);
                    } else {
                        //TODO
                        Intent intent = new Intent(MaterialUploadActivity02.this, ImageZoomActivity.class);
                        intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mAllList.get(finalI));
                        intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, position);
                        startActivity(intent);
                    }
                }
            });
            mLlContain.addView(gridview);
            mGvList.add(mAdapter);
        }
    }


    private void initViews() {
        mLlContain = (LinearLayout) findViewById(R.id.main_ll_contain);
    }



    /**
     * 界面传值
     */
    private void getIntentDatas() {
        Intent intent = getIntent();
        applyId = intent.getIntExtra("applyId", -1);
        mMaterialList = (List<Map<String, Object>>) intent.getSerializableExtra("bufferList");
        title = intent.getStringExtra("title");
    }

    /**
     * 新建 textview
     *
     * @param content
     */
    private TextView addTextview(String content, String sum) {
        TextView mTvContent = new TextView(getApplicationContext());
        mTvContent.setText("请上传每月" + content + "账单 (最多" + sum + "张)");
        mTvContent.setTextColor(Color.parseColor("#9a9a9a"));
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(DensityUtil.px2dip(getApplicationContext(), 22),
                DensityUtil.px2dip(getApplicationContext(), 58), 0,
                DensityUtil.px2dip(getApplicationContext(), 32));
        mTvContent.setLayoutParams(params);
        return mTvContent;

    }

    private int getDataSize(int clickPosition) {
        return mAllList.get(clickPosition) == null ? 0 : mAllList.get(clickPosition).size();
    }


    private class PopupWindows extends PopupWindow {
        public PopupWindows(Context mContext, View parent) {
            View view = View.inflate(mContext, R.layout.photo_item_popupwindow, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.photo_fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.photo_push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setFocusable(true);
            setOutsideTouchable(false);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();
            Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });

            //todo 图库
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getAcailableSize(clickPosition);//获取 availableSize
                    Intent intent = new Intent(MaterialUploadActivity02.this,ImageBucketChooseActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, getAvailableSize(clickPosition));
                    intent.putExtra("availableSize",availableSize);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    /**
     * 获取最大上传数量
     * @param clickPosition
     */
    private void getAcailableSize(int clickPosition){
        allPicCount = (int) mMaterialList.get(clickPosition).get("maxCount");
        displayCount = mAllList.get(clickPosition).size();
        availableSize= allPicCount-displayCount>=9 ? 9 : allPicCount-displayCount;
    }

    /**
     * 从哪个点击哪个预览图
     *
     * @param clickPosition
     * @return
     */
    private int getAvailableSize(int clickPosition) {
        int availSize = CustomConstants.MAX_IMAGE_SIZE - mAllList.get(clickPosition).size();
        if (availSize >= 0) {
            return availSize;
        }
        return 0;
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    //照相
    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File vFile = new File(Environment.getExternalStorageDirectory()
                + "/myimage/", String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    // 相机的回传-->  上传
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (mAllList.get(clickPosition).size() < CustomConstants.MAX_IMAGE_SIZE
                            && resultCode == -1 && !TextUtils.isEmpty(path)) {
                        ImageItem item = new ImageItem();
                        item.sourcePath = path;
//                    mPhotoPic.clear();
//                    mPhotoPic.add(item);//添加到临时变量里面
                        File file = new File(item.sourcePath);
                        mFileList.clear();
                        mFileList.add(file);
                        materialType = mMaterialList.get(clickPosition).get("materialType").toString();//获取上传类型
                        new MyThread(mFileList, Urls.RANQING + "/uploadAction/uploadFile?materialType=" +
                                materialType + "&applyId=" + applyId).start();
                    }
                    break;
            }
        }
    }

    //TODO
    //  从图片那边回传-->  上传
    public void onEventMainThread(PhotoEvenbus photoEvenbus) {
//        mPhotoPic = photoEvenbus.getList();//添加到临时变量
        mFileList.clear();
        for (int i = 0; i < photoEvenbus.getList().size(); i++) {
            File file = new File(photoEvenbus.getList().get(i).sourcePath);
            mFileList.add(file);
        }//遍历图册中的图片
        materialType = mMaterialList.get(clickPosition).get("materialType").toString();//获取上传类型
        new MyThread(mFileList, Urls.RANQING + "/uploadAction/uploadFile?materialType=" +
                materialType + "&applyId=" + applyId).start();
    }


    /* public void onEventMainThread(PhoteZoomEvenbus evenbus){
         mAllList.get(clickPosition).remove(evenbus.getPosition());
         mGvList.get(clickPosition).notifyDataSetChanged();
     }*/
    //网络下载
    private class MyThread extends Thread {
        private List<File> fileList;
        String url;

        public MyThread(List<File> fileList, String url) {
            this.fileList = fileList;
            this.url = url;
        }
        @Override
        public void run() {
            FileUpload fileUpload = new FileUpload(fileList, url, handler);
            try {
                fileUpload.send();
            } catch (Exception e) {
                handler.sendEmptyMessage(ConstantUtils.UPLOAD_FAIL);
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加gridview中的子空间
     *
     */
    private void addChildView(List<String> mChildList) {
        mAllList.get(clickPosition).addAll(mChildList);
//        mGvList.get(clickPosition).notifyDataSetChanged();
        handler.sendEmptyMessage(NOTIFYADAPTER_ADD);
    }

}
