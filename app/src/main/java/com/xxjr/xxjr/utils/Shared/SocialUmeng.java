package com.xxjr.xxjr.utils.Shared;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/1.
 */
public class SocialUmeng {
    static String appID = "wx5d5d9fa306ba254a";
    static String appSecret = "c8e53826e7f8b5526397763bf8d67b7e";

    static String QQ_appID = "1105283887";
    static String QQ_appKEY = "KEYSx82HWzvGX9pwZpZ";
    /**
     * 设置  微信  分享相关的内容
     * @param activity
     * @param content
     * @param url
     * @param imgUrl
     */
    private static void setWerXinContent(Activity activity, String title,String content, String url, String imgUrl,UMSocialService mController){
        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置分享文字
        weixinContent.setShareContent(content);
        //设置title
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(Urls.RANQING+url);
        //设置分享图片
        if (imgUrl == null)
            imgUrl = Urls.MORENTUPIAN;
        weixinContent.setShareImage(new UMImage(activity.getApplicationContext(), Urls.RANQING + imgUrl));
        mController.setShareMedia(weixinContent);
    }
    /**
     * 设置   朋友圈 分享相关的内容
     * @param activity
     * @param content
     * @param url
     * @param imgUrl
     */
    private static void setWerXinCircleContent(Activity activity, String title,String content, String url, String imgUrl,UMSocialService mController){
        //设置微信好友分享内容
        CircleShareContent circleMedia  = new CircleShareContent ();
        //设置分享文字
        circleMedia.setShareContent(content);
        //设置title
        circleMedia.setTitle(title);
        circleMedia.setTargetUrl(Urls.RANQING+url);
        //设置分享图片
        if (imgUrl == null)
            imgUrl = Urls.MORENTUPIAN;
        circleMedia.setShareImage(new UMImage(activity.getApplicationContext(), Urls.RANQING + imgUrl));
        mController.setShareMedia(circleMedia);
    }

    /**
     * 微信分享
     * @param activity
     * @param content  不可为空，不然会报错的
     * @param url
     * @param imgUrl
     */
    public static void SharedWeiXin(final Activity activity,String title,String content, String url, String imgUrl){
        UMSocialService mController =  UMServiceFactory.getUMSocialService("com.umeng.share");
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity,appID,appSecret);
        wxHandler.addToSocialSDK();
        setWerXinContent(activity, title, content, url, imgUrl,mController);//  设置内容
        choiceSharedTyte(activity, SHARE_MEDIA.WEIXIN,mController);
    }

    /**
     * 朋友圈的分享
     * @param activity
     */
    public static void SharedWeiXinCircle(final Activity activity, String title,String content, String url, String imgUrl){
        UMSocialService mController =  UMServiceFactory.getUMSocialService("com.umeng.share");
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity,appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        setWerXinCircleContent(activity,title, content, url, imgUrl,mController);//  设置内容
        choiceSharedTyte(activity, SHARE_MEDIA.WEIXIN_CIRCLE,mController);

    }

    public static void QQShared(final Activity activity,String title,String content,String url,String imgUrl){
        UMSocialService mController =  UMServiceFactory.getUMSocialService("com.umeng.share");
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, QQ_appID,QQ_appKEY);
        qqSsoHandler.addToSocialSDK();
        setQQContent(activity, title,content, url, imgUrl, mController);
        choiceSharedTyte(activity, SHARE_MEDIA.QQ,mController);

    }

    public static void QzoneShared(final Activity activity,String title,String content,String url,String imgUrl){
        UMSocialService mController =  UMServiceFactory.getUMSocialService("com.umeng.share");
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, QQ_appID,QQ_appKEY);
        qZoneSsoHandler.addToSocialSDK();
        setQzoneContent(activity,title, content, url, imgUrl,mController);
        choiceSharedTyte(activity, SHARE_MEDIA.QZONE,mController);
    }

    // QQ分享的内容
    private static void setQQContent(Activity activity,String title, String content, String url, String imgUrl, UMSocialService mController){
        QQShareContent qqShareContent = new QQShareContent();
        //设置分享文字
        qqShareContent.setShareContent(content);
        //设置分享title
        qqShareContent.setTitle(title);
        //设置分享图片
        if (imgUrl == null)
            imgUrl = Urls.MORENTUPIAN;
        qqShareContent.setShareImage(new UMImage(activity.getApplicationContext(), Urls.RANQING + imgUrl));
        //设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(Urls.RANQING + url);
        mController.setShareMedia(qqShareContent);

    }
    // QQ空间 内容
    private static void setQzoneContent(Activity activity, String title,String content, String url, String imgUrl,UMSocialService mController){
        QZoneShareContent  qzone  = new QZoneShareContent();
        //设置分享文字
        qzone .setShareContent(content);
        //设置分享title
        qzone .setTitle(title);
        //设置分享图片
        if (imgUrl == null)
            imgUrl = Urls.MORENTUPIAN;
        qzone .setShareImage(new UMImage(activity.getApplicationContext(), Urls.RANQING + imgUrl));
        //设置点击分享内容的跳转链接
        qzone.setTargetUrl(Urls.RANQING + url);
        mController.setShareMedia(qzone);

    }

    /**
     * 分享的回调
     * @param activity
     * @param share_type
     */
    private static void  choiceSharedTyte(final Activity activity,SHARE_MEDIA share_type,UMSocialService mController){
        mController.postShare(activity, share_type,
                new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(activity.getApplicationContext(), "开始分享.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                        if (eCode == 200) {
                            Toast.makeText(activity.getApplicationContext(), "分享成功.", Toast.LENGTH_SHORT).show();
                        } else {
                            String eMsg = "";
                            if (eCode == -101) {
                                eMsg = "没有授权";
                            }
                            Toast.makeText(activity.getApplicationContext(), "分享失败[" + eCode + "] " +
                                    eMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    //     第三方登录
    public static void threeLogin(final Activity activity, final Handler handler){
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        UMWXHandler wxHandler = new UMWXHandler(activity,appID,appSecret);
        wxHandler.addToSocialSDK();
        final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        mController.doOauthVerify(activity, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Toast.makeText(activity, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                Toast.makeText(activity, "授权错误", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(activity, "授权取消", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
            }

            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                Toast.makeText(activity, "授权完成", Toast.LENGTH_SHORT).show();
                mController.getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(activity, "开始获取用户信息", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for (String key : keys) {
                                sb.append(key + "=" + info.get(key).toString() + "\r\n");
                            }
                            uploadWX2Sevice(activity,Urls.ALTER_WEIXIN ,info,handler);
                        } else {
                            DebugLog.e("第三方登录", "发生错误：" + status);
                            DebugLog.Toast(activity, "发生错误：" + status);
                            handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                        }

                    }
                });
            }
        });
    }

    /**
     * 上传微信
     * @param url
     */
    private static void uploadWX2Sevice(final Context context, String url, Map<String, Object> hashMap, final Handler handler) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("sex", hashMap.get("sex").toString());
        params.put("nickname", hashMap.get("nickname").toString());
        params.put("unionid", hashMap.get("unionid").toString());
        params.put("province", hashMap.get("province").toString());
        params.put("openid", hashMap.get("openid").toString());
        params.put("language", hashMap.get("language").toString());
        params.put("headimgurl", hashMap.get("headimgurl").toString());
        params.put("country", hashMap.get("country").toString());
        params.put("city", hashMap.get("city").toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            updateUserInfo(Urls.CUST_INFO,handler);
                        } else {
                            Toast.makeText(context, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                        }
                        DebugLog.Toast((Activity) context, "上传微信是否成功：" + map.get("success").toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }

    /**
     * 重新更新用户信息
     * @param url
     */
    private static void updateUserInfo(String url, final Handler handler) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("rows");
                        if (infoList != null && infoList.size() > 0) {
                            Map<String, Object> infoMap = infoList.get(0);
                            MyApplication.userInfo = infoMap;
                            handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS_WEIXIN);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                }
        );
    }
}
