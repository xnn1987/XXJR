package com.xxjr.xxjr.bean;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CollectEvent {
    private boolean isAddColect;
    private int addCollectIndex;
    private int delCollectIndex;

    public int getDelCollectIndex() {
        return delCollectIndex;
    }

    public void setDelCollectIndex(int delCollectIndex) {
        this.delCollectIndex = delCollectIndex;
    }

    public int getAddCollectIndex() {
        return addCollectIndex;
    }

    public void setAddCollectIndex(int addCollectIndex) {
        this.addCollectIndex = addCollectIndex;
    }

    public boolean isAddColect() {
        return isAddColect;
    }

    public void setAddColect(boolean addColect) {
        isAddColect = addColect;
    }
}
