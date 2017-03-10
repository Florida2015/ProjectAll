package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/2.
 * 会员中心 积分
 */

public class IntegralBean<T> implements Serializable {//extends BaseEntity.ListBean

//    @Override
//    public Observable getPageAt(int page) {
//        return Api.getClient().getQueryMemberRecordList(Api.uid,Api.token,param.get("type"),page, C.PAGE_COUNT).compose(RxApiThread.convert());
//    }

    private int currentPage;
    private int numPerPage;
    private int totalCount;
    private int pageCount;
    private int beginPageIndex;
    private T recordList;

    public IntegralBean() {
        super();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getBeginPageIndex() {
        return beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public T getRecordList() {
        return recordList;
    }

    public void setRecordList(T recordList) {
        this.recordList = recordList;
    }

    @Override
    public String toString() {
        return "IntegralBean{" +
                "currentPage=" + currentPage +
                ", numPerPage=" + numPerPage +
                ", totalCount=" + totalCount +
                ", pageCount=" + pageCount +
                ", beginPageIndex=" + beginPageIndex +
                ", recordList=" + recordList +
                '}';
    }


}
