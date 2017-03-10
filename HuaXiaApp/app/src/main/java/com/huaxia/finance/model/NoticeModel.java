package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * Created by  on 2016/1/18.
 * 通知 模型
 *
 */
public class NoticeModel implements Serializable {

    private String detail;
    private String url;
    private long beginDate;
    private String contents;
    private long endDate;
    private String noticeNo;
    private String noticeSubject;
    private String status;

    public NoticeModel() {
        super();
    }

    public NoticeModel(String url, String detail) {
        this.url = url;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getNoticeSubject() {
        return noticeSubject;
    }

    public void setNoticeSubject(String noticeSubject) {
        this.noticeSubject = noticeSubject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NoticeModel{" +
                "detail='" + detail + '\'' +
                ", url='" + url + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", contents='" + contents + '\'' +
                ", endDate='" + endDate + '\'' +
                ", noticeNo='" + noticeNo + '\'' +
                ", noticeSubject='" + noticeSubject + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
