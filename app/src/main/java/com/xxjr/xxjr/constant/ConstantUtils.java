package com.xxjr.xxjr.constant;

import android.graphics.Color;

/**
 * Created by Administrator on 2015/12/28.
 */
public class ConstantUtils {
    public static final int TITLE_COLOR_ORANGE  = Color.parseColor("#fc6923");


    public static final int COMPANY_PRODUCE_COMMIT_TYPE = 1; //公司产品  立刻交单
    public static final int COMPANY_PRODUCE_CONNACT_TYPE = 0; //公司产品  联系客服
    public static final int COMMIT_RESULT_SIMPLE = 1; //交单结果   简单
    public static final int COMMIT_RESULT_COMPLETE = 2; //交单结果   完整
    public static final int COMMIT_RESULT_FAIL = 0; //交单结果   完整

    // 存储sp的名字
    public static final String SP_UPDATA_APP = "updataApp";

    public static final String SP_USER_NAME = "user";
    public static final String SP_USER_UID = "uid";
    public static final String SP_USER_INFO = "userInfo";
    public static final String SP_USER_HAVEWD = "haveWd";
    public static final String SP_USER_QUANJU = "userQuanJu";
    public static final String SP_USER_PSW = "psw";
    public static final String SP_MAIN_CACHE = "MAINCACHE"; // 首页缓存
    public static final String SP_MAIN_CARDFM = "CARDFM"; // 名片存储
    public static final String SP_MAIN_QUTUFM = "QUTUFM"; // 趣图
    //  微店存储
    public static final String SP_WEIDIAN = "WEIDIAN";//   微店存储
    public static final String SP_WD_SHARED_TITLE  = "title";//   微店分享   标题
    public static final String SP_WD_SHARED_CONTENT  = "content";//   微店分享   内容
    public static final String SP_WD_SHARED_PICURL  = "PICURL";//   微店分享   图片
    public static final String SP_WD_SERVECITY  = "SERVECITY";//   微店服务地区
    //   极光推送 存储
    public static final String SP_JPUSH_NAME = "JPUSH";
    public static final String SP_JPUSH_OPEN_STATUS = "OPEN";

    //标题
    public static final  String TITLE_BAR_FEEDBACK = "订单反馈";
    public static final  String TITLE_BAR_WITHDRAW = "提现";
    public static final  String TITLE_BAR_PRODUCEDETAIL = "产品详情";
    //字体大小
    public static final int TEXTSIZE_16 = 16;
    public static final int TEXTSIZE_22 = 22;


    public static final String DIALOG_CONTENT_01 = "没有保存信息，将有可能无法制作趣图";
    public static final String DIALOG_CONTENT_02 = "您将保存所有图片到手机qutu文件夹中";
    public static final String DIALOG_CONTENT_03 = "您将该图片到手机qutu文件夹中";
    public static final String DIALOG_CONTENT_04 = "确定要退出登录吗";

    //订单状态
    public static final String ORDER_STATUS_0 = "初始";
    public static final String ORDER_STATUS_1 = "客服处理中";
    public static final String ORDER_STATUS_2 = "回退处理";
    public static final String ORDER_STATUS_3= "机构处理中";
    public static final String ORDER_STATUS_4 = "处理完成";
    public static final String ORDER_STATUS_5 = "拒绝";
    public static final String ORDER_STATUS_6 = "取消";



    //产品状态
    public static final String PRODUCE_STATUS_0 = "待处理";
    public static final String PRODUCE_STATUS_1 = " 客服处理中";
    public static final String PRODUCE_STATUS_2 = "补充初始材料";
    public static final String PRODUCE_STATUS_3 = "机构处理中";
    public static final String PRODUCE_STATUS_4 = "机构补充资料";
    public static final String PRODUCE_STATUS_5 = "放款成功";
    public static final String PRODUCE_STATUS_6 = "拒绝";
    public static final String PRODUCE_STATUS_7 = "取消";


    // 上传文件
    public static final  int UPLOADING = 1;
    public static final int UPLOAD_SUCCESS = 2;
    public static final int UPLOAD_FAIL = 3;
    public static final int UPLOAD_RETURN_DATA = 14;
    public static final String UPLOAD_RETURN_DATAS = "returnDatas";
    //上传类型
    public static final String UPLOAD_HEAD_TPYE = "type";//  上传头像的类型  type
    public static final String UPLOAD_HEAD= "head";//  上传头像  type
    public static final String UPLOAD_WDCard_HEAD= "card";//  上传   微店卡片头像
    public static final String UPLOAD_WD_HEAD= "wd";//  上传   微店 店铺头像

    public static final String UPLOAD_FEEDBACK = "feedback";//  上传  意见反馈 type
    public static final String UPLOAD_PHOTO = "Photo";//  上传   图片

    //删除图片
    public static final int DEL_PIC_SUCCESS = 4;
    public static final int DEL_LOADING = 5;
    public static final int DEL_PIC_FAIL = 6;

    //普通网络加载
    public static final int LOADING = 7;
    public static final int LOAD_SUCCESS =8;
    public static final int LOAD_SUCCESS_WEIXIN = 9;
    public static final int LOAD_ERROR = 10;
    public static final int FOOT_REFRESH = 11;
    public static final int HEAD_REFRESH = 12;
    public static final int STOP_FRESH = 13;


    public static final String DIALOG_SHOW = "正在加载,请稍等...";
    public static final String DIALOG_UPLOADING = "正在上传,请等待...";
    public static final String DIALOG_ERROR = "网络不稳定";
    public static final String NO_ANYTHING_DATA = "没有新数据";
    public static final String DIALOG_DEL = "正在删除，请稍等...";

    public static final int EVERYPAGER_6 = 6;//  每页到6条才有刷新功能
    public static final int EVERYPAGER_10 = 10;//  每页到6条才有刷新功能

    //  微店
    public static final int WD_CITY = 10;
    public static final String WDCARD_FROM = "wdcar_from";
    public static final String WDCARD_FROM_ACTIVITY = "wdactivity";
    public static final String WDCARD_FROM_FRAGMENT = "wdfragment";
    public static final String WDCARD_ISFIRST_WD_CARD = "isFirst";

    //  聊天
    public static final  int CHAT_FILECHOOSER_RESULTCODE = 1;//  客服聊天上传图片时候intent回调的requestCode
    public static final int DURATION = 500;

}
