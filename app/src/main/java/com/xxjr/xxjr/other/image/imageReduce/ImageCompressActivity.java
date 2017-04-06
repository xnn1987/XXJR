package com.xxjr.xxjr.other.image.imageReduce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.other.image.imageReduce.view.ClipImageLayout;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 *
 * @author wqg
 */
public class ImageCompressActivity extends SlidBackActivity {
    private ClipImageLayout mClipImageLayout;

    private File mCaptureFile = null;
    private static final int REQUEST_CAPTURE_IMAGE = 0;
    private View contview;
    private AlertDialog dialog;
    private ImageView imageView;
    private String uploadType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_compress);
        setTitle();

        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        contview = findViewById(R.id.contive);
        getIntentDatas();


        initImageDialog();
    }

    private void initImageDialog() {
        dialog = new AlertDialog.Builder(this).create();
        imageView = new ImageView(this);
        dialog.setTitle("裁剪图片结果");
        dialog.setView(imageView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK
                && requestCode == REQUEST_CAPTURE_IMAGE) {
            try {
                final Cursor cr = getContentResolver().query(data.getData(),
                        new String[]{MediaStore.Images.Media.DATA}, null,
                        null, null);
                if (cr.moveToFirst()) {
                    String localPath = cr.getString(cr
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    mClipImageLayout.getZoomImageView()
                            .setImageBitmap(BitmapFactory.decodeFile(localPath));
                }
                cr.close();
            } catch (Exception e) {
                if (mCaptureFile != null && mCaptureFile.exists()) {
                    mClipImageLayout.getZoomImageView()
                            .setImageBitmap(BitmapFactory.decodeFile(mCaptureFile.getAbsolutePath()));
                }
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void setTitle() {
        SetTitleBar.setTitleText(ImageCompressActivity.this,"图片","保存");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = mClipImageLayout.clip();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] bitmapByte = baos.toByteArray();


                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PreviewActivity.class);
                intent.putExtra("bitmap", bitmapByte);
                intent.putExtra(ConstantUtils.UPLOAD_HEAD_TPYE,uploadType);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);

    }


    public void getIntentDatas() {
        Intent intent = getIntent();
        String localImgPath = intent.getStringExtra(ConstantUtils.UPLOAD_PHOTO);
        uploadType = getIntent().getStringExtra(ConstantUtils.UPLOAD_HEAD_TPYE);

        mClipImageLayout.getZoomImageView()
                .setImageBitmap(BitmapFactory.decodeFile(localImgPath));

    }
}
