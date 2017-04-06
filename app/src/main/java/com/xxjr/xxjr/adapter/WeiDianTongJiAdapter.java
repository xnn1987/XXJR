package com.xxjr.xxjr.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xxjr.xxjr.fragment.WDTongJiFm;
import com.xxjr.xxjr.utils.MapSortUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016/4/8.
 */
public class WeiDianTongJiAdapter extends FragmentStatePagerAdapter{
    private Map<String,Object> attrMap;
    private List<String> monthsList = new ArrayList<>();
    private final Map<String, Object> countDataMap;
    private List<Float> monthList;

    public WeiDianTongJiAdapter(FragmentManager fm,Map<String,Object> attrMap) {
        super(fm);
        this.attrMap = attrMap;
        countDataMap = new MapSortUtil().sortMap((Map<String, Object>) attrMap.get("countData"));

    }


    @Override
    public Fragment getItem(int position) {
        WDTongJiFm fm = new WDTongJiFm();
        Bundle bundle = new Bundle();
        int choicePosition = position+1;
        monthList = (List<Float>) countDataMap.get(""+choicePosition);
        bundle.putString("historyCount",attrMap.get("historyCount").toString());
        bundle.putString("dayCount",attrMap.get("dayCount").toString());
        bundle.putSerializable("monthList",(Serializable) monthList);
        fm.setArguments(bundle);
        return fm;
    }

    @Override
    public int getCount() {
        return countDataMap.size();
    }


}
