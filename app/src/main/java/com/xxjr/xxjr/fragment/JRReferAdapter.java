package com.xxjr.xxjr.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xxjr.xxjr.activity.JRReferActivity;

/**
 * Created by Administrator on 2016/4/26.
 */
public class JRReferAdapter extends FragmentStatePagerAdapter {
    JRReferActivity activity;
    String[] zxTag;
    public JRReferAdapter(FragmentManager fm, JRReferActivity activity,String[] zxTag) {
        super(fm);
        this.zxTag = zxTag;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        JRReferFM fm = new JRReferFM();
        Bundle bundle = new Bundle();
        bundle.putString("zxTag",zxTag[position]);
        fm.setArguments(bundle);
        return fm;
    }

    @Override
    public int getCount() {
        return activity.titils.length;
    }

}
