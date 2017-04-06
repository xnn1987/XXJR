package com.xxjr.xxjr.utils.Shared;

import android.app.Activity;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.SharedPrefUtil;

/**
 * Created by Administrator on 2016/5/4.
 */
public class Shared {
    public  void spWeidianShared(Activity activity,String title,String content,String picUrl,String serveCity){
        SharedPrefUtil sp = new SharedPrefUtil(activity,ConstantUtils.SP_WEIDIAN);
        sp.putString(ConstantUtils.SP_WD_SHARED_TITLE,title+"的信贷微店");//  微店名称
        sp.putString(ConstantUtils.SP_WD_SHARED_CONTENT,content);// 微店描述
        sp.putString(ConstantUtils.SP_WD_SHARED_PICURL,picUrl);//  微店头像
        sp.putString(ConstantUtils.SP_WD_SERVECITY,serveCity);//微店城市
        sp.commit();
    }

    // 存储微店信息以及微店分享
    public static void sharedWeiDian(Activity activity){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(activity, ConstantUtils.SP_WEIDIAN);
        String title = sharedPrefUtil.getString(ConstantUtils.SP_WD_SHARED_TITLE,"");
        String contetn = sharedPrefUtil.getString(ConstantUtils.SP_WD_SHARED_CONTENT,"");
        String picUrl = sharedPrefUtil.getString(ConstantUtils.SP_WD_SHARED_PICURL,null);
        SocialUmengShare share = new SocialUmengShare(activity,title,contetn, Urls.WD_SHARED,picUrl);
        share.showPop(activity.findViewById(R.id.main));//所有都  住界面的底部

    }
}
