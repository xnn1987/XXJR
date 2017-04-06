package com.xxjr.xxjr.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.network.FileUpload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/6.
 */
public class CommonUploadPicture {
    private TextView mTvTakePhoto;
    private TextView mTvPhotoBox;
    private TextView mTvCancel;
    private Activity activity;
    private int PHOTO_REQUEST_GALLERY1 = 1;
    private int PHOTO_REQUEST_CAREMA2 = 2;
    private File imgFile;
    private String takePhtotImg ;

    //弹出对话框----》》选择照片对话框
    public  void setDialogPictur(Activity activity) {
        this.activity = activity;
        //对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Dialog dialog = builder.create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_check_photo, null);
        initDialogViews(view);
        setDialogListener(dialog);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

        window.setContentView(view);

    }
    //对话框控件初始化
    private void initDialogViews(View view) {
        mTvTakePhoto = (TextView) view.findViewById(R.id.dialog_tv_photo);
        mTvPhotoBox = (TextView) view.findViewById(R.id.dialog_tv_photoBox);
        mTvCancel = (TextView) view.findViewById(R.id.dialog_tv_cancle);
    }


    private void setDialogListener(final Dialog dialog) {
        mTvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                camera();
            }
        });
        //从图库选取
        mTvPhotoBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                gallery();
            }
        });
        //取消
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    // 从相册获取
    private void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY1);
    }

    // 从相机获取
    private void camera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String saveDir = Environment.getExternalStorageDirectory() + "/camera";
            File imageDir = new File(saveDir);
            if (!imageDir.exists()) {
                imageDir.mkdir();
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = format.format(new Date(System.currentTimeMillis())) + ".JPEG";
            imgFile = new File(saveDir, time);
            if (!imgFile.exists()) {
                try {
                    imgFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            setTakePhtotImg(imgFile.getAbsolutePath());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
            activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA2);

        }
    }

    //异步上传
    public class MyThread extends Thread {
        private String url;
        private Handler handler;
        // 相机
        public MyThread( String url,Handler handler) {
            this.url = url;
            this.handler = handler;
        }
        // 图片
        public MyThread(File file ,String url,Handler handler){
            this.url = url;
            this.handler = handler;
            imgFile = file;
        }

        @Override
        public void run() {
            FileUpload fileUpload = new FileUpload(imgFile, url,handler);
            try {
                fileUpload.send();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 上传相机拍的图片
    public void uploadCamera(String url,Handler handler){
        new MyThread( url, handler).start();
    }
    //上传图册拍的图片
    public void uploadPicture(Intent data,Activity activity,String url,Handler handler){
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columIndext = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columIndext);
        cursor.close();
        File pictureFile = new File(picturePath);
        new MyThread( pictureFile,url, handler).start();
    }

    public void uploadFileImg(Bitmap imgBitmap, String url, Handler handler){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeName = format.format(new Date(System.currentTimeMillis())) + ".jpg";
        String bufferSave = Environment.getExternalStorageDirectory()+ "/bufferSaveFile";
        File imageDir = new File(bufferSave);
        if (!imageDir.exists()) {
            imageDir.mkdir();
        }
        File imgFile = new File(imageDir, timeName);
        if (!imgFile.exists()) {
            try {
                imgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imgFile));
            imgBitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        new MyThread( imgFile,url, handler).start();
    }

    public String getTakePhtotImg() {
        return takePhtotImg;
    }

    public void setTakePhtotImg(String takePhtotImg) {
        this.takePhtotImg = takePhtotImg;
    }
}
