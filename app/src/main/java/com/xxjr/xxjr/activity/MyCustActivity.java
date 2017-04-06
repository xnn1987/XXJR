package com.xxjr.xxjr.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.PassBackEvenbuss;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.XianZhiLinearLayout;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class MyCustActivity extends SlidBackActivity implements View.OnClickListener {
    private TextView myCustRealName;
    private TextView myCustNickName;
    private TextView myCustTelephone;
    private XianZhiLinearLayout custTagLayou;
    private TextView concatDescText;
    private LinearLayout myCustTag;
    private static Map<String, Object> concatMap = null;
    private PopupWindow mPopupWindow;
    private String realName;
    private String isImportant = "-1";
    private TextView mTvStartfriend;
    private ImageView mIvStartFriend;
    private ImageView mIvStar;

    private void initView() {
        //标题
        myCustRealName = (TextView) this.findViewById(R.id.concat_my_cust_realNmae);
        myCustNickName = (TextView) this.findViewById(R.id.concat_my_cust_nickNmae);
        myCustTelephone = (TextView) this.findViewById(R.id.concat_my_cust_telephone);
        custTagLayou = (XianZhiLinearLayout) this.findViewById(R.id.my_cust_cust_tag);
        concatDescText = (TextView) this.findViewById(R.id.my_cust_contactDesc);
        myCustTag = (LinearLayout) this.findViewById(R.id.my_cust_tag);
        mIvStar = (ImageView) findViewById(R.id.MyCust_iv_star);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cust);
        getIntentDatas();
        initView();
        starFriend(isImportant);
        setListener();
        initDate();
        initPopupWindow();
        setTitleBack();
    }
    public void setTitleBack() {
        final LinearLayout mLlIcon = (LinearLayout) findViewById(R.id.ThreeTitle2Icon_ll_Icon);
        SetTitleBar.setThreeTitleHasIcon(MyCustActivity.this, "我的客户", R.mipmap.gengduo);
                mLlIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mPopupWindow.isShowing()) {
                            mPopupWindow.showAsDropDown(mLlIcon);
                        } else {
                            mPopupWindow.dismiss();
                        }
                    }
                });
    }
    private void setListener() {
        myCustTag.setOnClickListener(this);
    }

    private void initPopupWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mVPopupMenu = layoutInflater.inflate(R.layout.my_custom_popupwindow, null);
        mPopupWindow = new PopupWindow(mVPopupMenu, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        initPupuViews(mVPopupMenu);
    }

    /**
     * 对窗体的初始化和监听
     *
     * @param mVPopupMenu
     */
    private void initPupuViews(View mVPopupMenu) {
        LinearLayout mLlAlter = (LinearLayout) mVPopupMenu.findViewById(R.id.MyCustomPupop_ll_alter);
        LinearLayout mLLStartfriend = (LinearLayout) mVPopupMenu.findViewById(R.id.MyCustomPupop_ll_startfrient);
        mTvStartfriend = (TextView) mVPopupMenu.findViewById(R.id.MyCustomPupop_tv_startfrient);
        mIvStartFriend = (ImageView) mVPopupMenu.findViewById(R.id.MyCustomPupop_iv_startfrient);
        setStartFriend();
        LinearLayout mLlDelect = (LinearLayout) mVPopupMenu.findViewById(R.id.MyCustomPupop_ll_delect);
        mLlAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCustAlterActivity.class);
                intent.putExtra("realName", realName);
                startActivity(intent);
                mPopupWindow.dismiss();
            }
        });
        mLLStartfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImportant(Urls.SET_STARTFRIEND);
            }
        });
        mLlDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delectConstom(Urls.DELECT_CONSTOM);
                mPopupWindow.dismiss();
            }
        });

    }

    /**
     * 判断是否是已经注释好的星标朋友
     */
    private void setStartFriend() {
        if (isImportant.equals("0")) {
            mTvStartfriend.setText("设为星标朋友");
            mIvStartFriend.setImageResource(R.mipmap.baixing);
        } else if (isImportant.equals("1")) {
            mTvStartfriend.setText("取消星标朋友");
            mIvStartFriend.setImageResource(R.mipmap.hongxing);
        }
        starFriend(isImportant);//标星朋友
    }

    /**
     * 点击更改星标朋友选中的状态
     */
    private void changImportantFlag() {
        if (isImportant.equals("0")) {
            isImportant = "1";
        } else if (isImportant.equals("1")) {
            isImportant = "0";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_cust_tag:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MyTagActivity.class);
                intent.putExtra("contactId", concatMap.get("contactId").toString());
                startActivity(intent);
                break;
        }
    }


    /**
     * 初始化网络下载的数据  gfs
     */
    private void initDate() {
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("realName", realName);
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/contact/queryList",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("rows");
                        if (infoList != null && infoList.size() > 0) {
                            Map<String, Object> infoMap = infoList.get(0);
                            concatMap = infoMap;
                            isImportant = infoMap.get("isImportant").toString();
                            if (infoMap.get("realName") != null) {
                                myCustRealName.setText(infoMap.get("realName") + "");
                            }
                            if (infoMap.get("nickName") != null) {
                                myCustNickName.setText(infoMap.get("nickName") + "");
                            }
                            if (infoMap.get("telephone") != null) {
                                myCustTelephone.setText(infoMap.get("telephone") + "");
                            }
                            if (infoMap.get("tagNames") != null) {

                                String[] tagNames = infoMap.get("tagNames").toString().split(",");
                                for (int i = 0; i < tagNames.length; i++) {
                                    TextView tagTextView = new TextView(getApplicationContext());
                                    tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                    tagTextView.setTextColor(Color.parseColor("#9a9a9a"));
                                    tagTextView.setText(tagNames[i]);
                                    custTagLayou.addView(tagTextView);
                                }
                            }
                            concatDescText.setText(infoMap.get("contactDesc") + "");
                        }
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                }
        );
    }


    /**
     * 设置标星朋友
     *
     * @param url
     */
    private void setImportant(String url) {
        changImportantFlag();
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("contactId", concatMap.get("contactId").toString());
        httpRequestUtil.params.put("isImportant", isImportant);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            setStartFriend();
                            passBack();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        changImportantFlag();
                    }
                }
        );
    }

    /**
     * 删除用户
     *
     * @param url
     */
    private void delectConstom(String url) {
        AppUtil.showProgress(this, "正在加载数据，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("contactId", concatMap.get("contactId").toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            passBack();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload("网络不稳定");
                    }
                }
        );
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        custTagLayou.removeAllViews();
        initDate();
    }

    /**
     * 回传到客户界面
     */
    private void passBack() {
        PassBackEvenbuss backPressEvenbus = new PassBackEvenbuss();
        backPressEvenbus.setIsBackPress(true);
        EventBus.getDefault().post(backPressEvenbus);
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        realName = intent.getStringExtra("realName");
        isImportant = intent.getStringExtra("isImportant");
    }

    /**
     * 设置标星
     */
    private void starFriend(String isImportant){
        if (isImportant.equals("1")){
            mIvStar.setVisibility(View.VISIBLE);
        }else {
            mIvStar.setVisibility(View.GONE);
        }
    }
}
