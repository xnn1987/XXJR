package com.xxjr.xxjr.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**  用来当map  排序
 * Created by Administrator on 2016/6/2.
 */
public class MapSortUtil {

    public Map<String,Object> sortMap(Map<String,Object> map){
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }
}
