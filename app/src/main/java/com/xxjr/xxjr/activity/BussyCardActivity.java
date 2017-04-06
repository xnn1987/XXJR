package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CardFmAdapter;
import com.xxjr.xxjr.bean.CollectEvent;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.ProgressWebView;
import com.xxjr.xxjr.other.multiColumn.XListView;
import com.xxjr.xxjr.utils.CommonMath;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.xxjr.xxjr.utils.network.MyOkHttpUtils;
import com.xxjr.xxjr.utils.popWin.MainPop;
import com.ypy.eventbus.EventBus;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;


public class BussyCardActivity extends SlidBackActivity implements XListView.IXListViewListener, View.OnClickListener {

    private int catgoryInfo;
    private String dayValue = "";
    private int currentPage = 0;
    private String title = "";//  支持模糊查询
    private List<Map<String, Object>> mList = new ArrayList<>();
    private XListView mLvMoreCard;
    private CardFmAdapter adapter;
    private EditText mEtTitle;
    private TextView mTvTime;
    private MainPop mainPop;
    private TextView mTvLoadType;
    private boolean cacheFlag = false;
    private List<TextView> mTvList = new ArrayList<>();
    private String[] is_cards = {"0", "1", "2", "4", "5"};
    private int titleIndex = -1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.LOADING:
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    adapter.notifyDataSetChanged();
                    mLvMoreCard.setPullLoadEnable(true, mList.size());
                    mLvMoreCard.stopAll();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    mLvMoreCard.stopAll();
                    break;
            }
        }
    };
    private MainPop.PopListener rightListen = new MainPop.PopListener() {
        @Override
        public void rightListener(String rightContent, String dayValue1) {
            dayValue = dayValue1;
            repeatDownLoad(true);
            mTvTime.setText(rightContent);
        }

        @Override
        public void leftListener(String leftContent, int catgoryInfo1) {
            mTvLoadType.setText(leftContent);
            catgoryInfo = catgoryInfo1;
        }
    };
    private View headView;
    private TextView mTvAll, mtvCard, mTvDate, mTvWeather, mTvHot;

    /*
     * 收藏列表的监听
     * @param event
     */
    public void onEventMainThread(CollectEvent event) {
        repeatDownLoad(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Map<String, Object> map = CommonMath.spGetMainCache(this, ConstantUtils.SP_MAIN_CARDFM);
        if (map != null) {
            List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
            mList.addAll(rowsList);
            cacheFlag = true;
        }
        setContentView(R.layout.activity_qu_tu);
        onClickChoiceTime();

        mainPop = new MainPop(this);
        mainPop.setRightListener(rightListen);

        initViews();
        addHeadInit();
        initAdapter();
        etTitleChangListener();

        setSelectedTitle(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void initViews() {
        mEtTitle = (EditText) findViewById(R.id.titlecard_et_title);
        mTvTime = (TextView) findViewById(R.id.cardfm_tv_choiceTime);
        mTvLoadType = (TextView) findViewById(R.id.cardfm_tv_loadType);
        mLvMoreCard = (XListView) findViewById(R.id.cardfm_lv_card);
    }

    private void initAdapter() {
        mLvMoreCard.setPullLoadEnable(true, mList.size());
        mLvMoreCard.setXListViewListener(this);
        adapter = new CardFmAdapter(this, mList, catgoryInfo + "");
        mLvMoreCard.setAdapter(adapter);
    }

    private void addHeadInit() {
        headView = LayoutInflater.from(this).inflate(R.layout.qutu_title, null);
        mLvMoreCard.addHeaderView(headView);
        mTvAll = (TextView) headView.findViewById(R.id.cardTitle_tv_all);
        mtvCard = (TextView) headView.findViewById(R.id.cardTitle_tv_card);
        mTvDate = (TextView) headView.findViewById(R.id.cardTitle_tv_date);
        mTvWeather = (TextView) headView.findViewById(R.id.cardTitle_tv_weather);
        mTvHot = (TextView) headView.findViewById(R.id.cardTitle_tv_hot);
        mTvAll.setOnClickListener(this);
        mtvCard.setOnClickListener(this);
        mTvDate.setOnClickListener(this);
        mTvWeather.setOnClickListener(this);
        mTvHot.setOnClickListener(this);
        mTvList.add(mTvAll);
        mTvList.add(mtvCard);
        mTvList.add(mTvDate);
        mTvList.add(mTvWeather);
        mTvList.add(mTvHot);

    }

    private void setSelectedTitle(int index) {
        if (index == titleIndex) {
            return;
        } else {
            currentPage = 0;
            mList.clear();
            titleIndex = index;
            for (int i = 0; i < mTvList.size(); i++) {
                if (i == index) {
                    mTvList.get(i).setSelected(true);
                } else {
                    mTvList.get(i).setSelected(false);
                }
            }
            downLoadCard(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardTitle_tv_all:
                setSelectedTitle(0);
                break;
            case R.id.cardTitle_tv_card:
                setSelectedTitle(1);
                break;
            case R.id.cardTitle_tv_date:
                setSelectedTitle(2);
                break;
            case R.id.cardTitle_tv_weather:
                setSelectedTitle(3);
                break;
            case R.id.cardTitle_tv_hot:
                setSelectedTitle(4);
                break;
        }
    }

    /*
     * 全部  按钮的   监听     popwind选择时间
     * @param view
     */
    public void onClickChoiceTime() {
        final LinearLayout mLlChoiceTime = (LinearLayout) findViewById(R.id.cardfm_ll_choiceTime);
        mLlChoiceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPop.show(mLlChoiceTime);
            }
        });
    }

    private void downLoadCard(final boolean isDialog) {
        Map<String, String> map = new HashMap<>();
        map.put("dayValue", dayValue);
        map.put("title", title);
        map.put("catgory", mTvLoadType.getText().toString());
        map.put("is_card", is_cards[titleIndex]);
        map.put("currentPage", (++currentPage) + "");
        map.put("everyPage", "20");
        RequestCall requestCall = MyOkHttpUtils.postRequest(Urls.QUTU_CARD, map);
        requestCall.execute(new MapCallback(this, isDialog) {
            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                if (!isDialog) {
                    DebugLog.Toast(BussyCardActivity.this, ConstantUtils.DIALOG_ERROR);
                }
                handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
            }

            @Override
            public void onResponse(Map responseMap) {
                super.onResponse(responseMap);
                if (responseMap != null) {
                    if (cacheFlag) {//  离线刷新
                        cacheFlag = false;
                        mList.clear();
                    }
                    if (currentPage == 1) {
                        mList.clear();
                    }
                    List<Map<String, Object>> rowsList = (List<Map<String, Object>>) responseMap.get("rows");
                    if (rowsList.size() != 0) {
                        mList.addAll(rowsList);
                    } else {
                        currentPage--;
                        DebugLog.Toast(BussyCardActivity.this, "没有新数据");
                    }
                    if (titleIndex == 0)
                        CommonMath.spPutMainCache(BussyCardActivity.this, responseMap, ConstantUtils.SP_MAIN_QUTUFM);
                }
                handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);

            }

            @Override
            public void onAfter() {
                super.onAfter();
            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }
        });
    }

    /*
     * 搜索文本框的监听
     */
    private void etTitleChangListener() {
        mEtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, int start, int count, int after) {
                if (s.length() == 1) {
                    title = "";
                    repeatDownLoad(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                title = s.toString();
                repeatDownLoad(false);
            }
        });
    }

    /*
     * 请求网络-->  重新请求网络
        */
    private void repeatDownLoad(boolean isDialog) {
        currentPage = 0;
        downLoadCard(isDialog);
    }

    @Override
    public void onRefresh() {
        currentPage = 0;
        downLoadCard(false);
    }

    @Override
    public void onLoadMore() {
        downLoadCard(false);
    }
}
