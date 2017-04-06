package com.xxjr.xxjr.utils;

import android.widget.ImageView;

import com.xxjr.xxjr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class Wd_CommonProduce {
    //职位数据
    public static List<String> zhiweiTagData(){
        List<String> list = new ArrayList<>();
        list.add("公务员");
        list.add("事业单位");
        list.add("国企员工");
        list.add("私企员工");
        list.add("自由创业者");
        list.add("其他");
        return list;
    }
    //信用
    public static List<String> xinyongTagData(){
        List<String> list = new ArrayList<>();
        list.add("信用良好");
        list.add("轻微不良");
        list.add("有严重不良");
        list.add("无信用记录");
        return list;
    }
    //贷款方式
    public static List<String> daikuanfangshiTagData(){
        List<String> list = new ArrayList<>();
        list.add("信用贷款");
        list.add("车抵押贷款");
        list.add("房抵押贷款");
        list.add("其他");
        return list;
    }
    //借用途
    public static List<String> jiekuanYongtuTagData(){
        List<String> list = new ArrayList<>();
        list.add("买车");
        list.add("买房");
        list.add("装修");
        list.add("投资");
        list.add("其他");
        return list;
    }
    //无法放款行业
    public static List<String> wufafangkuanTagData(){
        List<String> list = new ArrayList<>();
        list.add("现役军人");
        list.add("运输司机");
        list.add("公检人员");
        list.add("娱乐业人员");
        list.add("担保小贷人员");
        list.add("能源矿业人员");
        list.add("其他");
        return list;
    }

                            /*企业*/
    //企业类型
    public static List<String> qiyeTypeData(){
        List<String> list = new ArrayList<>();
        list.add("政府");
        list.add("事业单位");
        list.add("国企");
        list.add("私企");
        list.add("上市公司");
        list.add("外企");
        list.add("个体户");
        list.add("其他");
        return list;
    }
    //担保方式
    public static  List<String> danbaofangshiData(){
        List<String> list = new ArrayList<>();
        list.add("信用贷款 ");
        list.add("抵押贷款");
        list.add("质押贷款");
        list.add("担保贷款");
        return list;
    }
    //无法放款行业
    public static  List<String> wufafangkuanDatas(){
        List<String> list = new ArrayList<>();
        list.add("房地产");
        list.add("物流运输");
        list.add("能源");
        list.add("钢贸");
        list.add("煤矿");
        list.add("美容美发");
        list.add("服装");
        list.add("担保公司");
        list.add("其他");
        return list;
    }


}
