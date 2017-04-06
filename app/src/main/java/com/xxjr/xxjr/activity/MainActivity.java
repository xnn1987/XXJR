package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.FragmentTabAdapter;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.DestroyMainActivityEvenbus;
import com.xxjr.xxjr.bean.SeeStatus;
import com.xxjr.xxjr.boradcast.ConnectionChangeReceiver;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.fragment.WeiDianFM;
import com.xxjr.xxjr.fragment.ContactFM;
import com.xxjr.xxjr.fragment.CustFM;
import com.xxjr.xxjr.fragment.HomeFm;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.network.UpdateUtil;
import com.xxjr.xxjr.utils.common.CommomAcitivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends CommomAcitivity {
    public RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentTabAdapter tabAdapter;
    private long firstTime = 0;
    private String versionDes = "更新之后，精彩更多";//版本更新描述
    public static int chatStatus;
    public static int orderStatus;
    public static int messageStatus;
    public static int allMsgCount = 0;
    private ConnectionChangeReceiver receiver;
    private SharedPrefUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.main);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new ConnectionChangeReceiver();
        registerReceiver(receiver, filter);
        sp = new SharedPrefUtil(getApplicationContext(), ConstantUtils.SP_USER_NAME);
        chatStatus = 0;
        orderStatus = 0;
        messageStatus = 0;
        allMsgCount = 0;
        addFragmen();

        //版本更新
        if (savedInstanceState == null) {
            if (sp.getBoolean(ConstantUtils.SP_UPDATA_APP, false)) {
                updateVrsion(Urls.QUANJU);
            } else{
                sp.putBoolean(ConstantUtils.SP_UPDATA_APP, true);
                sp.commit();
             }

        }else {
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.userInfo != null) {
            setMsgNotify(Urls.MAIN_MSG_NOTIFY);//获取消息
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(receiver);
        sp.remove(ConstantUtils.SP_UPDATA_APP).commit();
    }

    //获取消息通知
    private void setMsgNotify(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        if (rowsList.size() > 0) {
                            Map<String, Object> map1 = rowsList.get(0);
                            chatStatus = Integer.parseInt(map1.get("chatStatus").toString());
                            orderStatus = Integer.parseInt(map1.get("orderStatus").toString());
                            messageStatus = Integer.parseInt(map1.get("messageStatus").toString());
                            allMsgCount = chatStatus + orderStatus + messageStatus;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
    }

    /**
     * 版本更新-->>  全局信息
     * @param url
     */
    private void updateVrsion(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) responseMap.get("success");
                        if (success) {
                            Map<String, Object> userMap = (Map<String, Object>) responseMap.get("attr");
                            String version = userMap.get("androidVersion").toString();
                            UpdateUtil updateUtil = new UpdateUtil(MainActivity.this, Urls.BANBEN_UPDATE_download,
                                    version, versionDes);
                            updateUtil.updateThread();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    private void addFragmen() {
        fragments.clear();
        fragments.add(new HomeFm());
        fragments.add(new ContactFM());
        fragments.add(new WeiDianFM());
        fragments.add(new CustFM());
        rgs = (RadioGroup) findViewById(R.id.tabs_rg);
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);

    }

    /**
     * 登录成功销毁主界面
     *
     * @param destroyMainActivityEvenbus
     */
    public void onEventMainThread(DestroyMainActivityEvenbus destroyMainActivityEvenbus) {
        if (destroyMainActivityEvenbus.isExit()) {
            MainActivity.this.finish();
        }
    }

    /**
     * 获取see 状态
     *
     * @param seeStatus
     */
    public void onEventMainThread(SeeStatus seeStatus) {
        if (seeStatus.isSee()) {
            setMsgNotify(Urls.MAIN_MSG_NOTIFY);//获取消息see状态
        }
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(MainActivity.this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                System.exit(0);//否则退出程序
            }
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RadioButton childAt = (RadioButton) rgs.getChildAt(0);
                childAt.setChecked(true);
            }
        }, 500);
    }

}

