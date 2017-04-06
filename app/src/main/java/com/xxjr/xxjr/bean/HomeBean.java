package com.xxjr.xxjr.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class HomeBean {


    /**
     * message : null
     * errorCode : null
     * attr : {"navMenus":[{"imgUrl":"/app/images/reg/reg.png","id":2,"isUse":1,"orderIndex":1,"name":"工薪交单","type":0,"url":"1","imgDesc":""},{"imgUrl":"/app/images/user/more-new.png","id":3,"isUse":1,"orderIndex":2,"name":"有房交单","type":0,"url":"2","imgDesc":""},{"imgUrl":"/app/images/reg/xsfl.png","id":4,"isUse":1,"orderIndex":3,"name":"老板交单","type":0,"url":"3","imgDesc":""},{"imgUrl":"/app/images/home/home10.png","id":5,"isUse":1,"orderIndex":4,"name":"其他交单","type":0,"url":"0","imgDesc":""},{"imgUrl":"/app/images/user/more-new.png","id":6,"type":0,"url":"","isUse":1,"orderIndex":5,"name":"计算器"},{"imgUrl":"/app/images/user/more-develop.png","id":7,"type":0,"url":"","isUse":1,"orderIndex":6,"name":"综合查询"},{"imgUrl":"/app/images/home/home10.png","id":8,"type":0,"url":"","isUse":1,"orderIndex":7,"name":"武功秘籍"},{"imgUrl":"/app/images/user/more-opinion.png","id":9,"type":0,"url":"","isUse":1,"orderIndex":8,"name":"签到抢单"}],"myBorrow":{"cardNo":"500***770520****","customerName":"王","applyId":190,"createTime":"01-28 13:59","customerId":22,"status":1,"loanCount":0,"loanType":"0","borrowerRealName":"哈哈","applyAmount":25,"telephone":"131****8685","createBy":"15298967709","fromType":1},"banners":[{"createTime":"2015-01-17","title":"test","useType":0,"imageType":1,"orderIndex":2,"homeImg":"/_themes/banner/banner2.png","enable":1,"contentType":0,"homeUrl":"http://www.qq.com","homeId":2,"homeType":1},{"createTime":"2015-08-12","title":"test","useType":0,"imageType":1,"orderIndex":3,"homeImg":"/_themes/banner/banner3.png","enable":1,"contentType":0,"homeUrl":"http://www.qq.com","homeId":5,"homeType":1},{"createTime":"2015-08-12","title":"test2","useType":1,"imageType":1,"orderIndex":4,"homeImg":"/_themes/banner/banner4.png","enable":1,"contentType":0,"homeUrl":"http://www.baidu.com","homeId":6,"homeType":1}]}
     * success : true
     * page : {"recordCount":0,"totalRecords":0,"currentPage":1,"totalPage":1,"everyPage":10,"beginIndex":0}
     * forward : null
     * rows : []
     */

    private Object message;
    private Object errorCode;
    /**
     * navMenus : [{"imgUrl":"/app/images/reg/reg.png","id":2,"isUse":1,"orderIndex":1,"name":"工薪交单","type":0,"url":"1","imgDesc":""},{"imgUrl":"/app/images/user/more-new.png","id":3,"isUse":1,"orderIndex":2,"name":"有房交单","type":0,"url":"2","imgDesc":""},{"imgUrl":"/app/images/reg/xsfl.png","id":4,"isUse":1,"orderIndex":3,"name":"老板交单","type":0,"url":"3","imgDesc":""},{"imgUrl":"/app/images/home/home10.png","id":5,"isUse":1,"orderIndex":4,"name":"其他交单","type":0,"url":"0","imgDesc":""},{"imgUrl":"/app/images/user/more-new.png","id":6,"type":0,"url":"","isUse":1,"orderIndex":5,"name":"计算器"},{"imgUrl":"/app/images/user/more-develop.png","id":7,"type":0,"url":"","isUse":1,"orderIndex":6,"name":"综合查询"},{"imgUrl":"/app/images/home/home10.png","id":8,"type":0,"url":"","isUse":1,"orderIndex":7,"name":"武功秘籍"},{"imgUrl":"/app/images/user/more-opinion.png","id":9,"type":0,"url":"","isUse":1,"orderIndex":8,"name":"签到抢单"}]
     * myBorrow : {"cardNo":"500***770520****","customerName":"王","applyId":190,"createTime":"01-28 13:59","customerId":22,"status":1,"loanCount":0,"loanType":"0","borrowerRealName":"哈哈","applyAmount":25,"telephone":"131****8685","createBy":"15298967709","fromType":1}
     * banners : [{"createTime":"2015-01-17","title":"test","useType":0,"imageType":1,"orderIndex":2,"homeImg":"/_themes/banner/banner2.png","enable":1,"contentType":0,"homeUrl":"http://www.qq.com","homeId":2,"homeType":1},{"createTime":"2015-08-12","title":"test","useType":0,"imageType":1,"orderIndex":3,"homeImg":"/_themes/banner/banner3.png","enable":1,"contentType":0,"homeUrl":"http://www.qq.com","homeId":5,"homeType":1},{"createTime":"2015-08-12","title":"test2","useType":1,"imageType":1,"orderIndex":4,"homeImg":"/_themes/banner/banner4.png","enable":1,"contentType":0,"homeUrl":"http://www.baidu.com","homeId":6,"homeType":1}]
     */

    private AttrEntity attr;
    private boolean success;
    /**
     * recordCount : 0
     * totalRecords : 0
     * currentPage : 1
     * totalPage : 1
     * everyPage : 10
     * beginIndex : 0
     */

    private PageEntity page;
    private Object forward;
    private List<?> rows;

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public void setAttr(AttrEntity attr) {
        this.attr = attr;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public void setForward(Object forward) {
        this.forward = forward;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Object getMessage() {
        return message;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public AttrEntity getAttr() {
        return attr;
    }

    public boolean isSuccess() {
        return success;
    }

    public PageEntity getPage() {
        return page;
    }

    public Object getForward() {
        return forward;
    }

    public List<?> getRows() {
        return rows;
    }

    public static class AttrEntity {
        /**
         * cardNo : 500***770520****
         * customerName : 王
         * applyId : 190
         * createTime : 01-28 13:59
         * customerId : 22
         * status : 1
         * loanCount : 0
         * loanType : 0
         * borrowerRealName : 哈哈
         * applyAmount : 25
         * telephone : 131****8685
         * createBy : 15298967709
         * fromType : 1
         */

        private MyBorrowEntity myBorrow;
        /**
         * imgUrl : /app/images/reg/reg.png
         * id : 2
         * isUse : 1
         * orderIndex : 1
         * name : 工薪交单
         * type : 0
         * url : 1
         * imgDesc :
         */

        private List<NavMenusEntity> navMenus;
        /**
         * createTime : 2015-01-17
         * title : test
         * useType : 0
         * imageType : 1
         * orderIndex : 2
         * homeImg : /_themes/banner/banner2.png
         * enable : 1
         * contentType : 0
         * homeUrl : http://www.qq.com
         * homeId : 2
         * homeType : 1
         */

        private List<BannersEntity> banners;

        public void setMyBorrow(MyBorrowEntity myBorrow) {
            this.myBorrow = myBorrow;
        }

        public void setNavMenus(List<NavMenusEntity> navMenus) {
            this.navMenus = navMenus;
        }

        public void setBanners(List<BannersEntity> banners) {
            this.banners = banners;
        }

        public MyBorrowEntity getMyBorrow() {
            return myBorrow;
        }

        public List<NavMenusEntity> getNavMenus() {
            return navMenus;
        }

        public List<BannersEntity> getBanners() {
            return banners;
        }

        public static class MyBorrowEntity {
            private String cardNo;
            private String customerName;
            private int applyId;
            private String createTime;
            private int customerId;
            private int status;
            private double loanCount;
            private String loanType;
            private String borrowerRealName;
            private int applyAmount;
            private String telephone;
            private String createBy;
            private int fromType;

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public void setApplyId(int applyId) {
                this.applyId = applyId;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setLoanCount(double loanCount) {
                this.loanCount = loanCount;
            }

            public void setLoanType(String loanType) {
                this.loanType = loanType;
            }

            public void setBorrowerRealName(String borrowerRealName) {
                this.borrowerRealName = borrowerRealName;
            }

            public void setApplyAmount(int applyAmount) {
                this.applyAmount = applyAmount;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public void setCreateBy(String createBy) {
                this.createBy = createBy;
            }

            public void setFromType(int fromType) {
                this.fromType = fromType;
            }

            public String getCardNo() {
                return cardNo;
            }

            public String getCustomerName() {
                return customerName;
            }

            public int getApplyId() {
                return applyId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public int getCustomerId() {
                return customerId;
            }

            public int getStatus() {
                return status;
            }

            public double getLoanCount() {
                return loanCount;
            }

            public String getLoanType() {
                return loanType;
            }

            public String getBorrowerRealName() {
                return borrowerRealName;
            }

            public int getApplyAmount() {
                return applyAmount;
            }

            public String getTelephone() {
                return telephone;
            }

            public String getCreateBy() {
                return createBy;
            }

            public int getFromType() {
                return fromType;
            }
        }

        public static class NavMenusEntity {
            private String imgUrl;
            private int id;
            private int isUse;
            private int orderIndex;
            private String name;
            private int type;
            private String url;
            private String imgDesc;

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setIsUse(int isUse) {
                this.isUse = isUse;
            }

            public void setOrderIndex(int orderIndex) {
                this.orderIndex = orderIndex;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setImgDesc(String imgDesc) {
                this.imgDesc = imgDesc;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public int getId() {
                return id;
            }

            public int getIsUse() {
                return isUse;
            }

            public int getOrderIndex() {
                return orderIndex;
            }

            public String getName() {
                return name;
            }

            public int getType() {
                return type;
            }

            public String getUrl() {
                return url;
            }

            public String getImgDesc() {
                return imgDesc;
            }
        }

        public static class BannersEntity {
            private String createTime;
            private String title;
            private int useType;
            private int imageType;
            private int orderIndex;
            private String homeImg;
            private int enable;
            private int contentType;
            private String homeUrl;
            private int homeId;
            private int homeType;

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUseType(int useType) {
                this.useType = useType;
            }

            public void setImageType(int imageType) {
                this.imageType = imageType;
            }

            public void setOrderIndex(int orderIndex) {
                this.orderIndex = orderIndex;
            }

            public void setHomeImg(String homeImg) {
                this.homeImg = homeImg;
            }

            public void setEnable(int enable) {
                this.enable = enable;
            }

            public void setContentType(int contentType) {
                this.contentType = contentType;
            }

            public void setHomeUrl(String homeUrl) {
                this.homeUrl = homeUrl;
            }

            public void setHomeId(int homeId) {
                this.homeId = homeId;
            }

            public void setHomeType(int homeType) {
                this.homeType = homeType;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getTitle() {
                return title;
            }

            public int getUseType() {
                return useType;
            }

            public int getImageType() {
                return imageType;
            }

            public int getOrderIndex() {
                return orderIndex;
            }

            public String getHomeImg() {
                return homeImg;
            }

            public int getEnable() {
                return enable;
            }

            public int getContentType() {
                return contentType;
            }

            public String getHomeUrl() {
                return homeUrl;
            }

            public int getHomeId() {
                return homeId;
            }

            public int getHomeType() {
                return homeType;
            }
        }
    }

    public static class PageEntity {
        private int recordCount;
        private int totalRecords;
        private int currentPage;
        private int totalPage;
        private int everyPage;
        private int beginIndex;

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setEveryPage(int everyPage) {
            this.everyPage = everyPage;
        }

        public void setBeginIndex(int beginIndex) {
            this.beginIndex = beginIndex;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getEveryPage() {
            return everyPage;
        }

        public int getBeginIndex() {
            return beginIndex;
        }
    }
}
