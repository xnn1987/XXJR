package com.xxjr.xxjr.constant;

import com.xxjr.xxjr.application.MyApplication;

/**
 * Created by Administrator on 2016/1/8.
 */
public class Urls {
//    http://fang.ngrok.cc      ---   方丈
//    http://app.xxjr.com      //     正是环境-
//    http://192.168.31.67     //
    public final static String QUANJU="/cust/userInfo";//全局
//    public final static String RANQING =    "http://192.168.31.67";
    public final static String RANQING =    "http://app.xxjr.com";
    //首页
    public final static String BANBEN_UPDATE_download = "https://www.duoduozhifu.com/apps/android/XXJR.apk";//版本下载
    public final static String LOGGIN = "/cust/login";//登录
    public final static String REGISTER = "/cust/register";//注册
    public final static String REGISTER_DEAL = "/app/about/registerProtocol";//注册协议
    public final static String HOME = "/home/init";//首页
    public final static String HOME_COMPANY = "/org/queryList";//首页公司列表
    public final static String COMPANY_PRODUCE = "/org/loan/queryList";//公司产品
    public final static String COMPANY_PRODUCE_CHECK = "/org/queryList";//机构产品查询
    public final static String CALCULATOR = "/cpQuery/app/calc/calcTool";//计算器
    public final static String ZX_PERSON = "/cpQuery/credit/page/init";//  个人征信  webview
    public final static String COMPREHENCE_SEARCH = "/cpQuery/apptools";//信贷工具
    public final static String KONGFU_CHEATS = "/app/miji/queryList";//武功秘籍
    public final static String SIGN_ROBORDER= "/app/account/signOn/ticket";//签到抢单
    public final static String SIGN_INIT= "/account/sign/initPage";//签到抢单  初始化
    public final static String SIGN_SIGN= "/account/sign/signin";//签到抢单   签到
    public final static String SIGN_INTEGRAL= "/app/account/member/scoreDtl";//签到抢单   积分详情
    public final static String SIGN_CHOU_JIANG= "/account/sign/getLottery";//签到抢单  抽奖
    public final static String QUTU= "/app/image/funnyList?fromType=2";//趣图
    public final static String BUSSINESS_CARD= "/app/card/cardGroups?fromType=2";//展业名片
    public final static String JR_REFER= "/news/queryType";//金融咨询
    public final static String QUTU_CARD = "/app/funnyImg/groupList";//  趣图名片组
    public final static String QUTU_CUST_INFO = "/account/funnyImg/myCenter";//  趣图  我的那个模块
    public final static String SHOU_CANG_List = "/account/funnyImg/myCollectList";//  收藏列表
    public final static String SHOU_CANG_QUTU_CARD = "/account/funnyImg/collect";//  收藏 趣图  卡片
    public final static String CANCLE_SHOUC_QUTU_CARD = "/account/funnyImg/cancelCollect";//  取消 收藏
    public final static String CARD_SET_SAVE = "/account/cust/insertInfo";//  名片保存
    public final static String CARD_SET_QUERY = "/account/cust/queryPriefCustInfo";//  名片 信息  --》  个人信息
    public final static String QUTU_SERIES = "http://wx.xxjr.com/qutu/home/index/execute";//  趣图系列图片


    //客户
    public final static String SEARCH_CUSTOM_INFO = "/account/contact/queryList";//查询客户信息
    public final static String ADD_TAG = "/account/tag/add";//增加客户标签
    public final static String ADD_CUST = "/account/contact/add";//增加客户
    public final static String UPDATE_TAG = "/account/contact/updateTag";//修改客户标签
    public final static String UPDATE_USERINFO = "/account/contact/update";//修改客户信息
    public final static String SET_STARTFRIEND = "/account/contact/important";//设置星标朋友
    public final static String DELECT_CONSTOM = "/account/contact/delete";//删除朋友
    public final static String TAG_CONSTOM_QUERY = "/account/tag/queryContact";//查寻标签下的客户
    public final static String TAG_CHANG = "/account/tag/update";//修改标签

    //展业
    public final static String BUSSINESS_QUERYLIST = "/news/queryList";//展业列表内容
    public final static String BUSSINESS_BANNER = "/news/queryBanner";//展业banner
    public final static String BUSSINESS_CONSTRUCE = "/app/news/detail";//展业详情介绍ITEM
    public final static String BUSSINESS_CUSTCARD= "/account/card/queryDetail";//名片详情
    public final static String BUSSINESS_CUSTCARD_ALTER= "/account/card/updateDetail";//上传图片
    public final static String BUSSINESS_CUSTCARD_HEADICON= "/uploadAction/viewImage?&uid="+MyApplication.uid+"&imageName=";//添加头像需要前缀
    public final static String SHARED_CONSULT= "/app/news/share";//咨询分享
    public final static String SHARED_CUST_CARD= "/app/share/cardShare";//名片分享





    //个人中心
    public final static String CUST_ORDER = "/account/order/queryList";
    public final static String ORDER_DETAIL = "/account/order/queryDetail";
    public final static String CUST_INFO = "/account/cust/info";//用户详情
    public final static String CUST_INSER_INFO = "/account/cust/insertInfo";//上传用户信息
    public final static String PRODUCE_DETAIL = "/org/loan/queryDetail?loanId=";//产品详情
    public final static String CITY_LIEBIAO = "/org/loan/queryArea";//城市列表
    public final static String ABOUT_XXJR = "/app/about/xxjr";//关于小小金融
    public final static String MYMEMBER= "/app/account/member/custGrade";//我的会员
    public final static String MY_ROB_ORDER= "/app/account/signOn/grabList";//我的抢单
    public final static String ALTER_WEIXIN= "/account/cust/bindingWX";//绑定  修改微信
    public final static String SERVIC_CHAT= "/chat/chat/phoneChatWall";//客服聊天界面

    public final static String MY_RECTUIT= "/app/account/apprenticeList";//我的学徒
    public final static String WHY_RECTUIT= "/app/about/reason";//为什么要收徒
    public final static String MAKE_MORE_RECRUIT= "/app/about/strategy";//如何收徒更多徒弟
    public final static String SHARED_RECRUIT= "/app/cust/shoutuShare";//收徒分享
    public final static String WX_SENDCODE= "/smsAction/login/withdraw";//微信提现短信验证
    public final static String WX_UPLOA_USERINFO= "/account/cust/bindingWX";//微信用户上传给后台
    public final static String ORDER_DET_CHAT= "/chat/chatCust/phoneChat";//交单详情的客服聊天


    //我的业绩
    public final static String CUST_PERFORM_RETURN_COMMISSION = "/account/reward/queryMyList"; //已返佣金
    public final static String CUST_PERFORM_WITHDRAWdETAIL = "/account/fund/queryWithdraw"; //已返佣金
    public final static String CUST_PERFORM_WITHDRAW_BANK_PERSON = "/account/bank/cardList"; //提现银行卡个人信息
    public final static String CUST_PERFORM_WITHDRAW_BANK_LIST = "/account/bank/addBankInit"; //提现银行卡列表
    public final static String CUST_PERFORM_WITHDRAW_BANK_COMIT = "/account/bank/addCard"; //提现银行卡提交
    public final static String CUST_UPLOAD_PICTURE = "/uploadAction/uploadFile?fileType="; //图片上传


    public final static String SALARY_ORDER = "/account/order/simpleSubmit";//简单交单
    public final static String MSG_COMMIT_ORDER = "/account/order/simpleSubmit";//短信交单
    public final static String COMPLE_ORDER = "/account/order/materialList";//完整列表
    public final static String COMPLE_ORDER_UPLOAD = "/account/order/fullSubmit";//完整交单的提交
    public final static String COMPLE_REPITE_UPLOAD = "/account/order/resubmit";//完整交单重新提交
    public final static String DEL_MATERIAL_PIC = "/account/order/delImg";//删除上传图片

    public final static String ORDER_FEEDBACK = "/account/order/orderDtlLog";//订单反馈
    public final static String ORDER_CANCEL = "/account/order/cancelOrderDtl";//订单撤单

    //订单查询
    public final static String ADD_QUERY = "/account/query/add";//添加订单产品查询
    public final static String QUERY_RESULT = "/account/query/queryList";//查询订单结果
    public final static String MORENTUPIAN = "/_themes/images/share.jpg";//默认分享图片

    //消息提示
    public final static String MAIN_MSG_NOTIFY = "/account/message/queryMsgCount";//消息提示有没有
    public final static String MSG_KEFU_CHAT = "/account/message/queryGroupChat";//客服消息
    public final static String MSG_ORDER_NOTIFY = "/account/message/queryGroupOrder";//交单通知
    public final static String MSG_ORDER_NOTIFY_CHILD = "/account/message/queryOrderAll";//交单通知某个记录
    public final static String MSG_ORDER_NOTIFY_CHILD_SEE = "/account/message/viewLog";//交单通知 see  状态
    public final static String MSG_XITONG_NOTIFY = "/account/message/queryList";//系统通知
    public final static String MSG_XITONG_NOTIFY_CHILD_SEE = "/account/message/view";//系统通知see 状态

    //微店
    public final static String WD_HOME_INIT = "/account/wdShop/index";//微店首页
    public final static String WD_YIXIANG_KEHU_QUERY = "/account/wdShop/applyList";//意向客户的列表
    public final static String WD_ALTER_CARD = "/account/wdShop/editWdShop";//微店  修改的接口
    public final static String WD_CARD_SETTING = "/account/wdShop/editWdCard";//设置名片
    public final static String WD_CARD_SHARED = "/app/card/cardGroups";//微店名片分享
    public final static String WD_CARD_SETTING_QUERY = "/account/wdShop/queryWdCard";//设置名片_查询
    public final static String WD_CARD_SETTING_QUERY_FIRSER = "/account/wdShop/initWdCard";//设置名片   第一次没有创建的的时候
    public final static String WD_ALTER_HAVE_WD = "/account/wdShop/queryWdShop";//微店  修改  有微店的初始化 有微店
    public final static String WD_ALTER_NO_HAVE_WD = "/account/wdShop/wdInfoInit";//创建微店的初始化信息  没有微店
    public final static String WD_PAIHANG = "/account/wdShop/wdShopRanking";//微店排行
    public final static String WD_TONGJI = "/account/wdShop/wdApplyStatistic";//微店统计
    public final static String WD_SHARED = "/app/wdShop/shareShop?uid="+ MyApplication.uid+"&isApp=true";//微店分享
    public final static String WD_MODULE = "/account/wdShop/templateList";//微店模板
    public final static String WD_FUWU_CITY = "/area/allCity";//微店服务城市
    public final static String WD_ALTER_PRODUCE = "/account/wdShop/editWdProduct";//添加产品  和修改微店产品
    public final static String WD_PRODUCE_List = "/account/wdShop/productList";//产品列表
    public final static String WD_PRODUCE_QUERY = "/account/wdShop/queryWdProduct";//产品查询
    public final static String WD_PRODUCE_DEL = "/account/wdShop/delWdProduct";//产品删除

    //  征信
    public final static String ZX_CHAXUNSHUJU = "/cpQuery/credit/page/initData";
    public final static String ZX_CHAXUNSHUJU_LIST = "/cpQuery/credit/page/creditReportList";
    public final static String ZX_CHAXUNSHUJU_RECORD = "/cpQuery/credit/page/creditReportListData";
    public final static String ZX_CHAXUNSHUJU_RECORD_ITEM = "/cpQuery/credit/page/creditReportData";
}
