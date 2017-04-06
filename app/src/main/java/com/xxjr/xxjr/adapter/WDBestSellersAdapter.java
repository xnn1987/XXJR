package com.xxjr.xxjr.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Administrator on 2016/4/8.
 */
public class WDBestSellersAdapter extends FragmentStatePagerAdapter {
    private String[] titils ={"申请数排行","浏览量排行"};

    public WDBestSellersAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        WDBestSellerFm fm = new WDBestSellerFm();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
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
