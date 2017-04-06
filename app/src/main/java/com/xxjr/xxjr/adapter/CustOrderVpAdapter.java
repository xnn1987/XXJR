package com.xxjr.xxjr.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xxjr.xxjr.fragment.CustOrderFM;

/**
 * Created by Administrator on 2016/2/29.
 */

public class CustOrderVpAdapter extends FragmentStatePagerAdapter {
    private String[] titils ={"全部","处理中","回退中","已完成","不成功"};
    private String[] inStatus = {"","0,1,3","2","4","5,6"};

    public CustOrderVpAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        CustOrderFM fm = new CustOrderFM();
        Bundle bundle = new Bundle();
        bundle.putString("inStatus",inStatus[position]);
        fm.setArguments(bundle);
        return fm;
    }

    @Override
    public int getCount() {
        return titils.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titils[position];
    }
}
