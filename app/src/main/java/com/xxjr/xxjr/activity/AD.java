package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.view.View;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.common.SlidBackActivity;




public class AD extends SlidBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_text);

        findViewById(R.id.wd02_tv_shopname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rawCookies = "alskdjfla";
            }
        });

        /*final RequestCall requestCall = MyOkHttpUtils.postRequest(Urls.HOME, null);
        requestCall.execute(new MapCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("抽奖 --> ", e.getMessage());
            }

            @Override
            public void onResponse(Map response) {
                Log.e("抽奖 --> ",response.toString());
            }

            @Override
            public Map parseNetworkResponse(Response response) throws Exception {
                String rawCookies = response.headers().get("Set-Cookie");
                DebugLog.e("seeco =请求头-->",response.headers().toString());
                DebugLog.e("seeco = 新版本Set-Cookie-->",response.headers().get("Set-Cookie").substring(0, rawCookies.indexOf(";")));
                return super.parseNetworkResponse(response);
            }
        });
*/
    }




}
