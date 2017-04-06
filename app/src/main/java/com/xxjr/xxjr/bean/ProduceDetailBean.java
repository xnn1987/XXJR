package com.xxjr.xxjr.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class ProduceDetailBean {

    /**
     * forward : null
     * errorCode : null
     * message : null
     * page : {"everyPage":10,"totalPage":1,"currentPage":1,"beginIndex":0,"recordCount":0,"totalRecords":0}
     * success : true
     * attr : {"loanDetail":{"updateBy":"frq","applyDesc":"1、年龄要求：22-25周岁 2、收入要求：月收入不低于10000","rateType":2,"rewardRate2":0.3,"updateTime":"2016-01-12 14:25:06","rewardRate1":0.2,"status":0,"loanIntro":"好好贷","loanDesc":"额度范围：1-30万 期限范围：12-36月","feeRate":0.1,"needDesc":"1、二代身份证 2、社保满一年","orgId":1,"rateMax":1.2,"creditType":"1","auditTime":"2015-12-30 10:36:55","cityType":0,"auditBy":"frq","loanType":"1","rateMin":0.6,"loanId":1}}
     * rows : []
     */

    private Object forward;
    private Object errorCode;
    private Object message;
    /**
     * everyPage : 10
     * totalPage : 1
     * currentPage : 1
     * beginIndex : 0
     * recordCount : 0
     * totalRecords : 0
     */

    private PageEntity page;
    private boolean success;
    /**
     * loanDetail : {"updateBy":"frq","applyDesc":"1、年龄要求：22-25周岁 2、收入要求：月收入不低于10000","rateType":2,"rewardRate2":0.3,"updateTime":"2016-01-12 14:25:06","rewardRate1":0.2,"status":0,"loanIntro":"好好贷","loanDesc":"额度范围：1-30万 期限范围：12-36月","feeRate":0.1,"needDesc":"1、二代身份证 2、社保满一年","orgId":1,"rateMax":1.2,"creditType":"1","auditTime":"2015-12-30 10:36:55","cityType":0,"auditBy":"frq","loanType":"1","rateMin":0.6,"loanId":1}
     */

    private AttrEntity attr;
    private List<?> rows;

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

    public void setAttr(AttrEntity attr) {
        this.attr = attr;
    }

    public void setRows(List<?> rows) {
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

    public AttrEntity getAttr() {
        return attr;
    }

    public List<?> getRows() {
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

    public static class AttrEntity {
        /**
         * updateBy : frq
         * applyDesc : 1、年龄要求：22-25周岁 2、收入要求：月收入不低于10000
         * rateType : 2
         * rewardRate2 : 0.3
         * updateTime : 2016-01-12 14:25:06
         * rewardRate1 : 0.2
         * status : 0
         * loanIntro : 好好贷
         * loanDesc : 额度范围：1-30万 期限范围：12-36月
         * feeRate : 0.1
         * needDesc : 1、二代身份证 2、社保满一年
         * orgId : 1
         * rateMax : 1.2
         * creditType : 1
         * auditTime : 2015-12-30 10:36:55
         * cityType : 0
         * auditBy : frq
         * loanType : 1
         * rateMin : 0.6
         * loanId : 1
         */

        private LoanDetailEntity loanDetail;

        public void setLoanDetail(LoanDetailEntity loanDetail) {
            this.loanDetail = loanDetail;
        }

        public LoanDetailEntity getLoanDetail() {
            return loanDetail;
        }

        public static class LoanDetailEntity {
            private String updateBy;
            private String applyDesc;
            private int rateType;
            private double rewardRate2;
            private String updateTime;
            private double rewardRate1;
            private int status;
            private String loanIntro;
            private String loanDesc;
            private double feeRate;
            private String needDesc;
            private int orgId;
            private double rateMax;
            private String creditType;
            private String auditTime;
            private int cityType;
            private String auditBy;
            private String loanType;
            private double rateMin;
            private int loanId;

            public void setUpdateBy(String updateBy) {
                this.updateBy = updateBy;
            }

            public void setApplyDesc(String applyDesc) {
                this.applyDesc = applyDesc;
            }

            public void setRateType(int rateType) {
                this.rateType = rateType;
            }

            public void setRewardRate2(double rewardRate2) {
                this.rewardRate2 = rewardRate2;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public void setRewardRate1(double rewardRate1) {
                this.rewardRate1 = rewardRate1;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setLoanIntro(String loanIntro) {
                this.loanIntro = loanIntro;
            }

            public void setLoanDesc(String loanDesc) {
                this.loanDesc = loanDesc;
            }

            public void setFeeRate(double feeRate) {
                this.feeRate = feeRate;
            }

            public void setNeedDesc(String needDesc) {
                this.needDesc = needDesc;
            }

            public void setOrgId(int orgId) {
                this.orgId = orgId;
            }

            public void setRateMax(double rateMax) {
                this.rateMax = rateMax;
            }

            public void setCreditType(String creditType) {
                this.creditType = creditType;
            }

            public void setAuditTime(String auditTime) {
                this.auditTime = auditTime;
            }

            public void setCityType(int cityType) {
                this.cityType = cityType;
            }

            public void setAuditBy(String auditBy) {
                this.auditBy = auditBy;
            }

            public void setLoanType(String loanType) {
                this.loanType = loanType;
            }

            public void setRateMin(double rateMin) {
                this.rateMin = rateMin;
            }

            public void setLoanId(int loanId) {
                this.loanId = loanId;
            }

            public String getUpdateBy() {
                return updateBy;
            }

            public String getApplyDesc() {
                return applyDesc;
            }

            public int getRateType() {
                return rateType;
            }

            public double getRewardRate2() {
                return rewardRate2;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public double getRewardRate1() {
                return rewardRate1;
            }

            public int getStatus() {
                return status;
            }

            public String getLoanIntro() {
                return loanIntro;
            }

            public String getLoanDesc() {
                return loanDesc;
            }

            public double getFeeRate() {
                return feeRate;
            }

            public String getNeedDesc() {
                return needDesc;
            }

            public int getOrgId() {
                return orgId;
            }

            public double getRateMax() {
                return rateMax;
            }

            public String getCreditType() {
                return creditType;
            }

            public String getAuditTime() {
                return auditTime;
            }

            public int getCityType() {
                return cityType;
            }

            public String getAuditBy() {
                return auditBy;
            }

            public String getLoanType() {
                return loanType;
            }

            public double getRateMin() {
                return rateMin;
            }

            public int getLoanId() {
                return loanId;
            }
        }
    }
}
