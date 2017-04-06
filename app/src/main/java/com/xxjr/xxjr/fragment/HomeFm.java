package com.xxjr.xxjr.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.activity.BussinessConstruceActivity;
import com.xxjr.xxjr.activity.BussyCardActivity;
import com.xxjr.xxjr.activity.CalculatorActivity;
import com.xxjr.xxjr.activity.ChoiceProduceActivity;
import com.xxjr.xxjr.activity.CityActivity;
import com.xxjr.xxjr.activity.ComprehensiveSearchActivity;
import com.xxjr.xxjr.activity.JRReferActivity;
import com.xxjr.xxjr.activity.KongFuCheatsActivity;
import com.xxjr.xxjr.activity.LoginActivity;
import com.xxjr.xxjr.activity.QuTuActivity;
import com.xxjr.xxjr.activity.SalaryOrderActivity;
import com.xxjr.xxjr.activity.SignRobOrderActivity;
import com.xxjr.xxjr.activity.SimpleOrderActivity;
import com.xxjr.xxjr.activity.WDActivity01;
import com.xxjr.xxjr.activity.WDActivity02;
import com.xxjr.xxjr.activity.zx.ZxCustManageActivity;
import com.xxjr.xxjr.activity.zx.ZxWebviewActivity;
import com.xxjr.xxjr.adapter.JJReferAdapter;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.SingleCityEvenbus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.MyPagerGalleryView;
import com.xxjr.xxjr.custview.Pull2RefreshListView;
import com.xxjr.xxjr.other.dialog.EasyDialog;
import com.xxjr.xxjr.utils.map.BaiDuMapUtil;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.ViewMathUtils;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wqg
 *         首頁
 */
public class HomeFm extends Fragment implements View.OnClickListener, View.OnFocusChangeListener,
        Pull2RefreshListView.OnRefreshListener, Pull2RefreshListView.OnLoadMoreListener {
    private static final int ADD_HEADVIEW_COUNT = 4;
    private LinearLayout mLlCooAgency;
    private List<Map<String, Object>> mCompanyList = new ArrayList<>();
    private View view, mVpView, mLlCooView, mLlComprehensiveView;
    private JJReferAdapter adapter;
    private Pull2RefreshListView mWlvMain;
    private LinearLayout mLlCalculator, mLlSearch, mLlCheats, mLlRobOrder;
    private EditText mEtSearch;
    private TextView mTvLocationCity;
    private int currentPage = 0;
    private MyPagerGalleryView mPgTtitle;
    private ImageView mIvMsg;
    private boolean isFirst = true;
    List<String> mNetImageList;
    private LinearLayout mLlOvalDot;
    private LinearLayout mLlQuTu;
    private LinearLayout mLlWeiDian;
    private LinearLayout mLlMingPian;
    private LinearLayout mLlMore;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.LOADING:
                    if (isFirst) {
                        AppUtil.showProgress(getActivity(), ConstantUtils.DIALOG_SHOW);
                        isFirst = false;
                    }
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    currentPage--;
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.FOOT_REFRESH:
                    downLoadDatas(Urls.BUSSINESS_QUERYLIST);
                    break;
                case ConstantUtils.HEAD_REFRESH:
                    currentPage = 0;
                    mCompanyList.clear();
                    downLoadDatas(Urls.BUSSINESS_QUERYLIST);
                    downHomeDatas(Urls.HOME);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mWlvMain.stopAllRefresh();
                    break;
            }
        }
    };
    private BaiDuMapUtil baiDuMapUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        handler.sendEmptyMessage(ConstantUtils.LOADING);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViews(inflater, container);//初始化控件
        initViewComprehensive(mLlComprehensiveView);
        lvSetAdapter();
        lvAddView();//嵌套头部
        LvSetItemListener();
        setListener();
        downLoadDatas(Urls.BUSSINESS_QUERYLIST);
        downHomeDatas(Urls.HOME);
//        new GaoDeMap(mTvLocationCity, getActivity());
        baiDuMapUtil = new BaiDuMapUtil(getActivity(),mTvLocationCity);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        baiDuMapUtil.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewMathUtils.mIvMsgNotifySee(mIvMsg);
    }

    @Override
    public void onStop() {
        baiDuMapUtil.onDestroy();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompanyList.clear();
        EventBus.getDefault().unregister(this);
    }

    //  城市状态的改变
    public void onEventMainThread(SingleCityEvenbus evenbus) {
        MyApplication.city = evenbus.getCityName();
        MyApplication.cityCode = evenbus.getCityName();
        mTvLocationCity.setText(MyApplication.city);
    }

    private void setListener() {
        mTvLocationCity.setOnClickListener(this);
        mEtSearch.setOnFocusChangeListener(this);
        mLlQuTu.setOnClickListener(this);
        mLlWeiDian.setOnClickListener(this);
        mLlMingPian.setOnClickListener(this);
        mLlMore.setOnClickListener(this);
        mIvMsg.setOnClickListener(this);
        mLlCalculator.setOnClickListener(this);
        mLlSearch.setOnClickListener(this);
        mLlCheats.setOnClickListener(this);
        mLlRobOrder.setOnClickListener(this);
        mWlvMain.setOnRefreshListener(this);
        mWlvMain.setOnLoadListener(this);
        mLlCooAgency.setOnClickListener(this);
    }

    private void lvSetAdapter() {
        adapter = new JJReferAdapter(getContext(), mCompanyList);
        mWlvMain.setAdapter(adapter);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.home_fm, container, false);
        mVpView = LayoutInflater.from(getActivity()).inflate(R.layout.home_titlebar_vp, null);// banner滑动
        mLlComprehensiveView = LayoutInflater.from(getActivity()).inflate(R.layout.home_comprehensive_icon, null);//交单交单之类
        mLlCooView = LayoutInflater.from(getActivity()).inflate(R.layout.home_cooperative_agency, null);//   金融咨询
        mPgTtitle = (MyPagerGalleryView) mVpView.findViewById(R.id.homeFm_ll_title);//标题
        mWlvMain = (Pull2RefreshListView) view.findViewById(R.id.HomeFM_wlv_main);
        mEtSearch = (EditText) mVpView.findViewById(R.id.Home_et_search);//  搜索
        mIvMsg = (ImageView) mVpView.findViewById(R.id.Home_iv_message);
        mTvLocationCity = (TextView) mVpView.findViewById(R.id.HomeFm_tv_LocationCity);//定位城市
        mLlOvalDot = (LinearLayout) mVpView.findViewById(R.id.homeTitle_ll_ovalLayout1);//  标题点容器
//        mTvLocationCity.setMovementMethod(ScrollingMovementMethod.getInstance());
        mLlCooAgency = (LinearLayout) mLlCooView.findViewById(R.id.homeFM_ll_cooperative_agency);
    }

    /*
     * 初始化交单控件
     *@param mLlComprehensiveView
     **/
    private void initViewComprehensive(View mLlComprehensiveView) {
        mLlQuTu = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_qutu);
        mLlWeiDian = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_weidian);
        mLlMingPian = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_mingpian);
        mLlMore = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_more);

        mLlCalculator = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_calculator);
        mLlSearch = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_search);
        mLlCheats = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_cheats);
        mLlRobOrder = (LinearLayout) mLlComprehensiveView.findViewById(R.id.HomeComprehensive_ll_RobOrder);
    }

    private void lvAddView() {
        mWlvMain.addHeaderView(mVpView);//添加滑动头部
        mWlvMain.addHeaderView(mLlComprehensiveView);//添加滑动头部
        mWlvMain.addHeaderView(mLlCooView);//添加合作机构
    }

    private void LvSetItemListener() {
        mWlvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= ADD_HEADVIEW_COUNT) {
                    Intent intent = new Intent(getContext(), BussinessConstruceActivity.class);
                    intent.putExtra("zxTag", (mCompanyList.get(position - ADD_HEADVIEW_COUNT).get("zxTag")).toString());
                    intent.putExtra("novelId", (mCompanyList.get(position - ADD_HEADVIEW_COUNT).get("novelId")).toString());
                    intent.putExtra("title", (mCompanyList.get(position - ADD_HEADVIEW_COUNT).get("title").toString()));
                    intent.putExtra("smallImg", (mCompanyList.get(position - ADD_HEADVIEW_COUNT).get("smallImg").toString()));
                    startActivity(intent);
                }

            }
        });
    }

    //下载首页数据
    private void downHomeDatas(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
                        List<Map<String, Object>> bannersList = (List<Map<String, Object>>) attrMap.get("banners");
                        setHeadVpTitle(bannersList);//头部的刷新

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setHeadVpTitle(null);//头部的刷新
                    }
                });
    }

    private void setHeadVpTitle(List<Map<String, Object>> bannersList) {
        if (bannersList != null && bannersList.size() > 0) {
            mNetImageList = new ArrayList<>();
            for (int i = 0; i < bannersList.size(); i++) {
                mNetImageList.add(bannersList.get(i).get("homeImg").toString());
            }
        }
        // 第二和第三参数 2选1 ,参数2为 图片网络路径数组 ,参数3为图片id的数组,本地测试用 ,2个参数都有优先采用 参数2
        mPgTtitle.start(getContext(), mNetImageList, R.mipmap.banerjiazaizhong, 3000, mLlOvalDot,
                R.drawable.dot_focused, R.drawable.dot_normal, null, null);

    }

    //下载金融咨询
    private void downLoadDatas(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("zxTag", "");
        params.put("currentPage", (currentPage += 1) + "");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rows = (List<Map<String, Object>>) responseMap.get("rows");
                        if (rows.size() == 0) {
                            currentPage--;
                            Toast.makeText(getActivity(), ConstantUtils.NO_ANYTHING_DATA, Toast.LENGTH_SHORT).show();
                        } else {
                            mCompanyList.addAll(rows);
                            adapter.notifyDataSetChanged();
                        }
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.HomeFm_tv_LocationCity://定位
                intent.setClass(getContext(), CityActivity.class);
                intent.putExtra("fromFlag", "HomeFm");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
            case R.id.Home_iv_message:
                ViewMathUtils.intent2MsgActivity(getContext());
                break;
            case R.id.HomeComprehensive_ll_qutu:
                ViewMathUtils.intentIsLogin(getContext(), QuTuActivity.class);
                break;
            case R.id.HomeComprehensive_ll_mingpian:
                ViewMathUtils.intentIsLogin(getContext(), ZxWebviewActivity.class);
                break;
            case R.id.HomeComprehensive_ll_weidian:
                if (MyApplication.userInfo == null) {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (MyApplication.haveWd) {
                        ViewMathUtils.intentIsLogin(getContext(), WDActivity02.class);
                    } else {
                        ViewMathUtils.intentIsLogin(getContext(), WDActivity01.class);
                    }
                }
                break;
            case R.id.HomeComprehensive_ll_more:
                EasyDialog easyDialog = new EasyDialog(getActivity());
                easyDialog
                        .setLayoutResourceId(R.layout.homefm_gengduo)
                        .setBackgroundColor(getActivity().getResources().getColor(R.color.dialog_homegengduo))
                        .setLocationByAttachedView(mLlMore)
                        .setGravity(EasyDialog.GRAVITY_BOTTOM)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                        .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                        .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(true)
                        .setMarginLeftAndRight(24, 24)
                        .show();
                View gengduoView = easyDialog.getTipViewInstance();
                //   更多中的简单交单和完整交单的监听
                gengduoView.findViewById(R.id.homefmMore_tv_jiandanorder).setOnClickListener(this);
                gengduoView.findViewById(R.id.homefmMore_tv_wanzhengOrder).setOnClickListener(this);
                break;
            case R.id.homefmMore_tv_jiandanorder:
                ViewMathUtils.intentIsLogin(getContext(), SimpleOrderActivity.class);
                break;
            case R.id.homefmMore_tv_wanzhengOrder:
                ViewMathUtils.intentIsLogin(getContext(), SalaryOrderActivity.class);
                break;
            case R.id.HomeComprehensive_ll_calculator:
                intent.setClass(getContext(), CalculatorActivity.class);
                startActivity(intent);
                break;
            case R.id.HomeComprehensive_ll_search:
                intent.setClass(getContext(), ComprehensiveSearchActivity.class);
//                intent.setClass(getContext(), ZxCustManageActivity.class);
                startActivity(intent);
                break;
            case R.id.HomeComprehensive_ll_cheats:
                intent.setClass(getContext(), KongFuCheatsActivity.class);
                startActivity(intent);
                break;
            case R.id.HomeComprehensive_ll_RobOrder:
                ViewMathUtils.intentIsLogin(getContext(), SignRobOrderActivity.class);
                break;
            case R.id.homeFM_ll_cooperative_agency:
                jrRefer(Urls.JR_REFER);
                break;

        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.Home_et_search) {
            Intent intent = new Intent();
            intent.setClass(getContext(), ChoiceProduceActivity.class);
            intent.putExtra("from", "HomeFm");
            startActivity(intent);
            mEtSearch.clearFocus();
        }
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }


    //  金融咨询
    private void jrRefer(String url) {
        AppUtil.showProgress(getActivity(), ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil(getActivity());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
                            List<Map<String, Object>> zxTypesList = (List<Map<String, Object>>) attrMap.get("zxTypes");

                            Intent intent = new Intent(getContext(), JRReferActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("zxTypesList", (Serializable) zxTypesList);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }
}
