package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.fragment.JRReferAdapter;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.SystemBarTintManager;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import java.util.List;
import java.util.Map;

public class JRReferActivity extends CommomAcitivity {
    public String[] titils ;
    public String[] zxTag ;
    private TabLayout mTlRefer;
    private ViewPager mVpRefer;
    private Object intentDatas;
    private List<Map<String, Object>> zxTypesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jrrefer);
        SetTitleBar.setTitleText(JRReferActivity.this, "金融咨询");
        getIntentDatas();
        initViews();
        setAdapter();
    }
    private void setAdapter() {
        JRReferAdapter mVpAdapter = new JRReferAdapter(getSupportFragmentManager(),this,zxTag);
        mVpRefer.setAdapter(mVpAdapter);
//        mVpRefer.setOffscreenPageLimit(5);
        mTlRefer.setTabMode(TabLayout.MODE_FIXED);
        mTlRefer.setupWithViewPager(mVpRefer);
        setTab();
    }

    private  void  setTab(){
        for (int i= 0 ;i< mTlRefer.getTabCount(); i++ ){
            TabLayout.Tab tab = mTlRefer.getTabAt(i);
            if (tab!=null){
                tab.setCustomView(getTabView(i));
            }
        }
    }
    public TextView getTabView(int position) {
        View v = LayoutInflater.from(this).inflate(R.layout.tablayout_tabchild, null);
        final TextView tv = (TextView) v.findViewById(R.id.tablayout_tabChild);
        tv.setText(titils[position]);
        return tv;
    }


    private void initViews() {
        mTlRefer = (TabLayout) findViewById(R.id.jrrefer_TabLayout);
        mVpRefer = (ViewPager) findViewById(R.id.jrrefer_ViewPager);
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        zxTypesList = (List<Map<String, Object>>) bundle.getSerializable("zxTypesList");
        titils = new String[zxTypesList.size()];
        zxTag = new String[zxTypesList.size()];
        for (int i = 0; i< zxTypesList.size(); i++){
            titils[i] = zxTypesList.get(i).get("label").toString();
            zxTag[i] =  zxTypesList.get(i).get("value").toString();
        }

    }
}
