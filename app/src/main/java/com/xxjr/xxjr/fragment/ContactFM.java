package com.xxjr.xxjr.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.ViewUtils;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.ConcatMyNewCustActivity;
import com.xxjr.xxjr.activity.CustomSearchActivity;
import com.xxjr.xxjr.activity.MyCustActivity;
import com.xxjr.xxjr.activity.TagMemberActivity;
import com.xxjr.xxjr.adapter.StarFriendAdpter;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.PassBackEvenbuss;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.CustListview;
import com.xxjr.xxjr.other.swipemenulistview.SwipeMenu;
import com.xxjr.xxjr.other.swipemenulistview.SwipeMenuCreator;
import com.xxjr.xxjr.other.swipemenulistview.SwipeMenuIsOpen;
import com.xxjr.xxjr.other.swipemenulistview.SwipeMenuItem;
import com.xxjr.xxjr.other.swipemenulistview.SwipeMenuListView;
import com.xxjr.xxjr.tongxun.ChineseToPinyinHelper;
import com.xxjr.xxjr.tongxun.MySortAdapter;
import com.xxjr.xxjr.tongxun.SidebarView;
import com.xxjr.xxjr.tongxun.UserModel;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.ViewMathUtils;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ContactFM extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final int REFRESH_LOADING = 1;
    private static final int REFRESH_STOP = 2;
    private static final int REFRESH_ERROR = 3;
    private List<Map<String, Object>> mStarFriendList = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> mCommentFriendList = new ArrayList<Map<String, Object>>();
    private StarFriendAdpter starFriendAdappter;
    private SwipeMenuListView mLvCommonFriend;
    private CustListview mLvStarFriend;
    private View view, mLlCooView;

    private TextView concatSizeText;
    private ImageView mIvSearch;
    private RelativeLayout mRlStartFriend, mRlCommonFriend;
    private TextView mTvDialog;
    private SidebarView mSvSidebar;
    private ArrayList<UserModel> totallList;
    private MySortAdapter adapter;
    private SwipeRefreshLayout mSrlRefresh;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_LOADING:
                    downloadInitDate(Urls.SEARCH_CUSTOM_INFO);
                    break;
                case REFRESH_STOP:
                    stopRefresh(500);
                    break;
                case REFRESH_ERROR:
                    stopRefresh(500);
                    break;

            }
        }

        ;
    };
    private ImageView mIvMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.custom_fm, container, false);
        initViews();
        ListViewAddHeadView();//嵌套头部
        lvSetAdapter();
        LvSetItemListener();
        setListener();
        lvAddheanListener();
        dismissView(mLvStarFriend, mLvCommonFriend);
        srlSetReresh();

        startRefresh();
        swipeListviewAddmenu();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewMathUtils.mIvMsgNotifySee(mIvMsg);
    }

    @Override
    public void onStop() {
        AppUtil.dismissProgress();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        DebugLog.e("FM","--savedInstanceState");
    }


    private void srlSetReresh() {
        mSrlRefresh.setOnRefreshListener(this);
        mSrlRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    // swipeRefreshLayout 开始刷新
    private void startRefresh() {
        mSrlRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSrlRefresh.setRefreshing(true);
                mHandler.sendEmptyMessage(REFRESH_LOADING);
            }
        });
    }

    private void stopRefresh(int time) {
        mSrlRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSrlRefresh.setRefreshing(false);
            }
        }, time);
    }

    private void initViews() {
        mIvMsg = (ImageView) view.findViewById(R.id.Custom_iv_message);
        mLvCommonFriend = (SwipeMenuListView) view.findViewById(R.id.Custom_lv_commonfriend);//普通客户
        mSrlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.Custom_srl_refresh);//刷新

        mIvSearch = (ImageView) view.findViewById(R.id.customfm_iv_search);
        mTvDialog = (TextView) view.findViewById(R.id.textView_dialog);
        mSvSidebar = (SidebarView) view.findViewById(R.id.sidebarView_main);
    }

    /*
     * 搜索监听
     */
    private void setListener() {
        mIvMsg.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }

    /*
     * 标星客户的适配
     */
    private void lvSetAdapter() {
        starFriendAdappter = new StarFriendAdpter(getContext(), mStarFriendList);//星标客户
        mLvStarFriend.setAdapter(starFriendAdappter);
    }

    private void initViewConnact() {
        ViewUtils.inject(getActivity());
        mSvSidebar.setTextView(mTvDialog);
        totallList = new ArrayList<UserModel>();
        totallList = getUserList();

        Collections.sort(totallList, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel lhs, UserModel rhs) {
                if (lhs.getFirstLetter().equals("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().equals("#")) {
                    return -1;
                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });
        adapter = new MySortAdapter(getActivity(), totallList);
        mLvCommonFriend.setAdapter(adapter);
        mSvSidebar.setOnLetterClickedListener(new SidebarView.OnLetterClickedListener() {
            @Override
            public void onLetterClicked(String str) {
                int position = adapter.getPositionForSection(str.charAt(0));
                mLvCommonFriend.setSelection(position);
            }
        });
    }

    private ArrayList<UserModel> getUserList() {
        ArrayList<UserModel> list = new ArrayList<UserModel>();//普通客户联系人
        for (int i = 0; i < mCommentFriendList.size(); i++) {
            UserModel userModel = new UserModel();
            String username = mCommentFriendList.get(i).get("realName").toString();
            if (mCommentFriendList.get(i).get("tagNames") != null) {
                userModel.setTagNames(mCommentFriendList.get(i).get("tagNames").toString());
            }
            userModel.setIsImportant(mCommentFriendList.get(i).get("isImportant").toString());
            String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(username);
            String firstLetter = pinyin.substring(0, 1).toUpperCase();
            if (firstLetter.matches("[A-Z]")) {
                userModel.setFirstLetter(firstLetter);
            } else {
                userModel.setFirstLetter("#");
            }
            userModel.setUesrname(username);
            list.add(userModel);
        }

        return list;
    }


    /*
     * 判断是否有数据,  然后决定是否显示
     *
     * @param startListview
     * @param commonListview
     */
    private void dismissView(ListView startListview, ListView commonListview) {
        if (startListview.getCount() == 0) {
            mRlStartFriend.setVisibility(View.GONE);
        } else {
            mRlStartFriend.setVisibility(View.VISIBLE);
        }
        if (commonListview.getCount() == 1) {
            mRlCommonFriend.setVisibility(View.GONE);
        } else {
            mRlCommonFriend.setVisibility(View.VISIBLE);
        }
    }

    private void lvAddheanListener() {
        //我的新客户
        mLlCooView.findViewById(R.id.concat_my_cust_head_new_cust).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ConcatMyNewCustActivity.class);
                startActivity(intent);
            }
        });
        //我的标签
        mLlCooView.findViewById(R.id.concat_my_tag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), TagMemberActivity.class);
                startActivity(intent);
            }
        });
        //让标星朋友覆盖lv点击事件
        mLlCooView.findViewById(R.id.ConcatMyCust_rl_starfriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void ListViewAddHeadView() {
        mLlCooView = LayoutInflater.from(getActivity()).inflate(R.layout.concat_my_cust_head, null);
        mLvStarFriend = (CustListview) mLlCooView.findViewById(R.id.my_all_cust_list);
        mRlStartFriend = (RelativeLayout) mLlCooView.findViewById(R.id.ConcatMyCust_rl_starfriend);
        mRlCommonFriend = (RelativeLayout) mLlCooView.findViewById(R.id.ConcatMyCust_rl_commonfriend);
        mLvCommonFriend.addHeaderView(mLlCooView);

    }

    private void downloadInitDate(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil(getActivity());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (boolean) map.get("success");
                        if (success) {
                            List<Map<String, Object>> importantListList = (List<Map<String, Object>>) ((Map<String, Object>) map.get("attr")).get("importantList");
                            mStarFriendList.clear();
                            mStarFriendList.addAll(importantListList);
                            starFriendAdappter.notifyDataSetChanged();
                            List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                            concatSizeText = (TextView) view.findViewById(R.id.my_concat_size);
                            concatSizeText.setText(rowsList.size() + "");
                            mCommentFriendList.clear();
                            mCommentFriendList.addAll(rowsList);
                            initViewConnact();//搜索的通讯录
                        }
                        dismissView(mLvStarFriend, mLvCommonFriend);
                        mHandler.sendEmptyMessage(REFRESH_STOP);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mHandler.sendEmptyMessage(REFRESH_ERROR);
                        Toast.makeText(getContext(), "网络不稳定", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    //lv 监听
    private void LvSetItemListener() {
        mLvStarFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MyCustActivity.class);
                intent.putExtra("realName", mStarFriendList.get(position).get("realName").toString());
                intent.putExtra("isImportant", mStarFriendList.get(position).get("isImportant").toString());
                startActivity(intent);
            }
        });
        mLvCommonFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1) {
                    Intent intent = new Intent(getContext(), MyCustActivity.class);
                    intent.putExtra("realName", totallList.get(position - 1).getUesrname());
                    intent.putExtra("isImportant", totallList.get(position - 1).getIsImportant());
                    startActivity(intent);
                }
            }
        });
    }
    //  监听  成功修改信息的，  并且刷新
    public void onEventMainThread(PassBackEvenbuss backPressEvenbus) {
        downloadInitDate(Urls.SEARCH_CUSTOM_INFO);
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessage(REFRESH_LOADING);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customfm_iv_search:
                Intent intent = new Intent(getContext(), CustomSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.Custom_iv_message:
                ViewMathUtils.intent2MsgActivity(getContext());
                break;
        }
    }

    //  对swipeListview 左滑的的功能实现
    private void swipeListviewAddmenu(){
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                // set item background
                deleteItem.setBackground(R.color.origin_qianse);
                // set item width
                deleteItem.setWidth((int) (MyApplication.density*60));
                deleteItem.setHeight((int) (MyApplication.density*44));
                // set item title
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(14);
                deleteItem.setTitleColor(Color.parseColor("#ffffff"));
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mLvCommonFriend.setMenuCreator(creator);
        // step 2. listener item click event
        mLvCommonFriend.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        delectConstom(Urls.DELECT_CONSTOM,position);
                        break;
                }
            }
        });

        mLvCommonFriend.isSwipeMenuIsOpen(new SwipeMenuIsOpen() {
            @Override
            public void isOpenningMenuItem(boolean open) {
                if (open){
                    mSrlRefresh.setEnabled(false);
                }else {
                    mSrlRefresh.setEnabled(true);
                }
            }
        });




    }




    /**
     * 删除用户
     *
     * @param url
     */
    private void delectConstom(String url,int position) {
        AppUtil.showProgress(getActivity(), ConstantUtils.DIALOG_DEL);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("contactId", mCommentFriendList.get(position).get("contactId").toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            downloadInitDate(Urls.SEARCH_CUSTOM_INFO);
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
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
}
