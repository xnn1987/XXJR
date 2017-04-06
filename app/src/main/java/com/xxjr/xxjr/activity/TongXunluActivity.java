package com.xxjr.xxjr.activity;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.ViewUtils;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ContractAdapter;
import com.xxjr.xxjr.bean.ContactBean;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.tongxun.ChineseToPinyinHelper;
import com.xxjr.xxjr.tongxun.SidebarView;
import com.xxjr.xxjr.tongxun.UserModel;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TongXunluActivity extends SlidBackActivity {
    private ContractAdapter adapter;
    private ListView mLvConcat;

    private SidebarView mSidebar;
    private TextView textView_dialog;
    private List<UserModel> totallList = new ArrayList<UserModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_xunlu);
        setTitleDatas();
        initViews();
        readContrac();
    }

    private void setTitleDatas() {
        SetTitleBar.setTitleText(this,"通讯录","保存");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TongXunluActivity.this, "暂未实现该功能", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViews() {
        mLvConcat = (ListView) findViewById(R.id.TongXunLu_lv_tel);
        mSidebar = (SidebarView) findViewById(R.id.TongXunlu_sidebarView);
        textView_dialog = (TextView) findViewById(R.id.Tongxunlu_tv_dialog);
    }

    public void readContrac() {
        ViewUtils.inject(this);
        mSidebar.setTextView(textView_dialog);

        totallList = getUserList();
        Collections.sort(totallList, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel lhs, UserModel rhs) {
                if (lhs == null || rhs == null)
                    return 0;
//                if (lhs.getFirstLetter().equals("#")) {
//                    return 1;
//                } else if (rhs.getFirstLetter().equals("#")) {
//                    return -1;
//                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
//                }
            }
        });

        adapter = new ContractAdapter(this, totallList);
        mLvConcat.setAdapter(adapter);

        mLvConcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MyCustAlterActivity.class);
                intent.putExtra("name", totallList.get(position).getUesrname());
                intent.putExtra("telephone", totallList.get(position).getTel());
                startActivity(intent);
            }
        });

        mSidebar.setOnLetterClickedListener(new SidebarView.OnLetterClickedListener() {
            @Override
            public void onLetterClicked(String str) {
                int position = adapter.getPositionForSection(str
                        .charAt(0));
                mLvConcat.setSelection(position);
            }
        });
    }

    private List<UserModel> getUserList() {
        List<UserModel> list = new ArrayList<UserModel>();
        Cursor cursor = this.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        String phoneNumber;
        int index = 0;
        String phoneName;
        while (cursor.moveToNext()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//
            phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));//
            UserModel userModel = new UserModel();
            String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(phoneName);
            String firstLetter = pinyin.substring(0, 1).toUpperCase();
            if (firstLetter.matches("[A-Z]")) {
                userModel.setFirstLetter(firstLetter);
            } else {
                userModel.setFirstLetter("#");
            }
            userModel.setUesrname(phoneName);
            userModel.setTel(phoneNumber);

            if (!userModel.getFirstLetter().equals("#")) {
                list.add(userModel);
            }
        }
        return list;
    }

    /**
     * 获取通讯录
     */
    /*private void readContract(){
        Cursor cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        String phoneNumber;
        String phoneName;
        while (cursor.moveToNext()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//鐢佃瘽鍙风爜
            phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));//濮撳悕
            Log.e("main", phoneName + phoneNumber);
        }
    }*/


}
