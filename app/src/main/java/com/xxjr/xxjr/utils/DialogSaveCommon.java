package com.xxjr.xxjr.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xxjr.xxjr.R;


/**
 * Created by Administrator on 2016/5/25.
 */
public class DialogSaveCommon {

    private  String content;
    private DialogListener listener;

    public DialogSaveCommon(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public  void AlterDialog(Activity activity) {
        //对话框
//        AlertDialog alertDialog  = new AlertDialog(this,R.style.theme_customer_progress_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Dialog dialog = builder.create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_common_sava, null);
        initDialogViews(view,dialog);

        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(false);
        window.setAttributes(params);
        window.setContentView(view);


    }

    private  void initDialogViews(View view, final Dialog dialog) {
        TextView mTvContent = (TextView) view.findViewById(R.id.dialogcommon_tv_content);
        mTvContent.setText(content);

        view.findViewById(R.id.dialogcommon_iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.dialogcommon_tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener!=null){
                    listener.addDialogListener(false);
                }
            }
        });
        view.findViewById(R.id.dialogcommon_tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener!=null){
                    listener.addDialogListener(true);
                }
            }
        });
    }

    public interface DialogListener{
        void addDialogListener(boolean isSure);
    }


    public void setListener(DialogListener listener) {
        this.listener = listener;
    }
}
