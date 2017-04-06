package com.xxjr.xxjr.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class HomeCompanyBean {


    /**
     * forward : null
     * errorCode : null
     * message : null
     * page : {"everyPage":3,"totalPage":1,"currentPage":1,"beginIndex":0,"recordCount":0,"totalRecords":3}
     * success : true
     * attr : {}
     * rows : [{"orgType":1,"orgId":1,"orgImage":"/upfile/about/20160111171330287.png","rewardRate":3,"loanDesc":"10-80","applyCount":130,"orgName":"测试机构"},{"orgType":1,"orgId":2,"orgImage":"/upfile/about/20160111171321367.jpg","rewardRate":3,"loanDesc":"50-100","applyCount":40,"orgName":"恒昌"},{"orgType":1,"orgId":3,"orgImage":"/upfile/about/20160111171313255.png","rewardRate":3,"loanDesc":"1-50","applyCount":90,"orgName":"58信贷"}]
     */

    private Object forward;
    private Object errorCode;
    private Object message;
    /**
     * everyPage : 3
     * totalPage : 1
     * currentPage : 1
     * beginIndex : 0
     * recordCount : 0
     * totalRecords : 3
     */

    private PageEntity page;
    private boolean success;
    /**
     * orgType : 1
     * orgId : 1
     * orgImage : /upfile/about/20160111171330287.png
     * rewardRate : 3
     * loanDesc : 10-80
     * applyCount : 130
     * orgName : 测试机构
     */

    private List<RowsEntity> rows;

    public void setForward(Object forward) {
        this.forward = forward;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setRows(List<RowsEntity> rows) {
        this.rows = rows;
    }

    public Object getForward() {
        return forward;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public Object getMessage() {
        return message;
    }

    public PageEntity getPage() {
        return page;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<RowsEntity> getRows() {
        return rows;
    }

    public static class PageEntity {
        private int everyPage;
        private int totalPage;
        private int currentPage;
        private int beginIndex;
        private int recordCount;
        private int totalRecords;

        public void setEveryPage(int everyPage) {
            this.everyPage = everyPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setBeginIndex(int beginIndex) {
            this.beginIndex = beginIndex;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getEveryPage() {
            return everyPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getBeginIndex() {
            return beginIndex;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public int getTotalRecords() {
            return totalRecords;
        }
    }

    public static class RowsEntity {
        private int orgType;
        private int orgId;
        private String orgImage;
        private double rewardRate;
        private String loanDesc;
        private int applyCount;
        private String orgName;

        public void setOrgType(int orgType) {
            this.orgType = orgType;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public void setOrgImage(String orgImage) {
            this.orgImage = orgImage;
        }

        public void setRewardRate(double rewardRate) {
            this.rewardRate = rewardRate;
        }
        public void setLoanDesc(String loanDesc) {
            this.loanDesc = loanDesc;
        }
        public void setApplyCount(int applyCount) {
            this.applyCount = applyCount;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getOrgType() {
            return orgType;
        }

        public int getOrgId() {
            return orgId;
        }

        public String getOrgImage() {
            return orgImage;
        }

        public double getRewardRate() {
            return rewardRate;
        }

        public String getLoanDesc() {
            return loanDesc;
        }

        public int getApplyCount() {
            return applyCount;
        }

        public String getOrgName() {
            return orgName;
        }
    }
}
