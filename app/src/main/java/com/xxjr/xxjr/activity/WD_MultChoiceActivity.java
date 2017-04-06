package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.TagAdapter;
import com.xxjr.xxjr.other.tagcheckbox.TagCloudLayout;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import java.util.ArrayList;
import java.util.List;

public class WD_MultChoiceActivity extends SlidBackActivity implements View.OnClickListener {

    private TagCloudLayout mFtFlowTag;
    private EditText mEtOther;
    private LinearLayout mLlOther;
    private TagAdapter<String> mMobileTagAdapter;
    private List<String> tagList;
    private String titleName = "";
    private String tagName;
    private List<String> mTagIdList = new ArrayList<>();
    private String tags[];
    private  boolean otherHasDatas = false;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd__mult_choice);
        getIntentDatas();
        SetTitleBar.setTitleText(WD_MultChoiceActivity.this, titleName, "保存");
        initViews();
        setTagAdapter();
        setListener();
        otherHasDatas();

    }

    private void setListener() {
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(this);
    }

    private void initViews() {
        mLlOther = (LinearLayout) findViewById(R.id.mult_choice_ll_other);
        mEtOther = (EditText) findViewById(R.id.mult_et_other);
        mFtFlowTag = (TagCloudLayout) findViewById(R.id.mult_choice_ft_flowtag);

    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tagList = (List<String>) bundle.getSerializable("tag");
        titleName = bundle.getString("titleName");
        tagName = bundle.getString("tagName");
        tags = new String[tagList.size()];
    }
    //  显示和吟唱
    private void otherTagVisible(boolean isvisible) {
        if (isvisible) {
            otherHasDatas  = true;
            mLlOther.setVisibility(View.VISIBLE);
        } else {
            otherHasDatas = false;
            mLlOther.setVisibility(View.GONE);
        }
    }
    //  判断是否有   其他的数据
    private void otherHasDatas(){
        if (!TextUtils.isEmpty(tagName)) {
            String[] split = tagName.split("、");
            aa:for (int i = 0; i < split.length; i++) {
                bb:for (int j = 0; j < tagList.size(); j++) {
                    if (split[i].equals(tagList.get(j))) {
                        break bb;
                    }
                    if ((i == split.length - 1 && j == tagList.size() - 1) && !split.equals("")) {
                        otherTagVisible(true);
                        mEtOther.setText(split[i]);
                        adapter.updateProgressPartly(j);
                        break aa;
                    }
                }
            }
        }
    }

    private void setTagAdapter() {
        adapter = new MyAdapter();
        mFtFlowTag.setAdapter(adapter);

    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return tagList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return tagList.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.tag_checkbox_item, null);
                viewHolder.tv_tag = (CheckBox) convertView.findViewById(R.id.mytag_checkbox_tag);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final String[] tagNames = tagName.split("、");
            for (int i = 0; i < tagNames.length; i++) {
                if (tagNames[i].equals(tagList.get(position))) {
                    viewHolder.tv_tag.setChecked(true);
                    tags[position] = tagList.get(position);
                }
            }
            viewHolder.tv_tag.setText(tagList.get(position));
            viewHolder.tv_tag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (buttonView.getText().toString().equals("其他"))
                            otherTagVisible(true);
                        tags[position] = buttonView.getText().toString();
                    } else {
                        if (buttonView.getText().toString().equals("其他"))
                            otherTagVisible(false);
                        tags[position] = null;
                    }

                }
            });
            return convertView;
        }

        class ViewHolder {
            CheckBox tv_tag;
        }

        public void updateProgressPartly(int position){
                View view = mFtFlowTag.getChildAt(position);
                if(view.getTag() instanceof ViewHolder){
                    ViewHolder vh = (ViewHolder)view.getTag();
                    vh.tv_tag.setChecked(true);

                }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ThreeTitleBar_ll_click:
                Intent intent = new Intent();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tags.length; i++) {
                    if (tags[i] != null && !tags[i].equals("其他")) {
                        sb.append(tags[i] + "、");
                    }
                }
                if (otherHasDatas){
                    sb.append(mEtOther.getText().toString().trim());
                }
                if (sb.length()>0  &&  !otherHasDatas){
                    sb.deleteCharAt(sb.length() - 1);
                }
                intent.putExtra("tagContain", sb.toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
