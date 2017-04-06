package com.xxjr.xxjr.bean;

import org.ddq.common.util.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 */
public class UploadReturnDataEven {
    private String singleImg;


    /**
     * 返回的json 解析
     * @param json
     */
    public void setSingleImg(String json) {
        Map<String,Object> map = JsonUtil.getInstance().json2Object(json,Map.class);
        List<String> fileIdList = (List<String>) map.get("fileId");
        singleImg = fileIdList.get(0);
    }

    public String getSingleImg() {
        return singleImg;
    }

}
