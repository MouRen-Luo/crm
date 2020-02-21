package com.lsg.demo8.entity;

import javax.persistence.*;

@Entity
@Table(name = "cst_service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "svr_id")
    private Long svrId;
    @Column(name = "svr_type")
    private String svrType;
    @Column(name = "svr_title")
    private String svrTitle;
    @Column(name = "svr_cust_no")
    private String svrCustNo;
    @Column(name = "svr_cust_name")
    private String svrCustName;
    @Column(name = "svr_status")
    private String svrStatus;
    @Column(name = "svr_request")
    private String svrRequest;
    @Column(name = "svr_create_id")
    private Long svrCreateId;
    @Column(name = "svr_create_by")
    private String svrCreateBy;
    @Column(name = "svr_create_date")
    private String svrCreateDate;
    @Column(name = "svr_due_id")
    private Long svrDueId;
    @Column(name = "svr_due_to")
    private String svrDueTo;
    @Column(name = "svr_due_date")
    private String svrDueDate;
    @Column(name = "svr_deal")
    private String svrDeal;
    @Column(name = "svr_deal_id")
    private Long svrDealId;
    @Column(name = "svr_deal_by")
    private String svrDealBy;
    @Column(name = "svr_deal_date")
    private String svrDealDate;
    @Column(name = "svr_result")
    private String svrResult;

    public Long getSvrId() {
        return svrId;
    }

    public void setSvrId(Long svrId) {
        this.svrId = svrId;
    }

    public String getSvrType() {
        return svrType;
    }

    public void setSvrType(String svrType) {
        this.svrType = svrType;
    }

    public String getSvrTitle() {
        return svrTitle;
    }

    public void setSvrTitle(String svrTitle) {
        this.svrTitle = svrTitle;
    }

    public String getSvrCustNo() {
        return svrCustNo;
    }

    public void setSvrCustNo(String svrCustNo) {
        this.svrCustNo = svrCustNo;
    }

    public String getSvrCustName() {
        return svrCustName;
    }

    public void setSvrCustName(String svrCustName) {
        this.svrCustName = svrCustName;
    }

    public String getSvrStatus() {
        return svrStatus;
    }

    public void setSvrStatus(String svrStatus) {
        this.svrStatus = svrStatus;
    }

    public String getSvrRequest() {
        return svrRequest;
    }

    public void setSvrRequest(String svrRequest) {
        this.svrRequest = svrRequest;
    }

    public Long getSvrCreateId() {
        return svrCreateId;
    }

    public void setSvrCreateId(Long svrCreateId) {
        this.svrCreateId = svrCreateId;
    }

    public String getSvrCreateBy() {
        return svrCreateBy;
    }

    public void setSvrCreateBy(String svrCreateBy) {
        this.svrCreateBy = svrCreateBy;
    }

    public String getSvrCreateDate() {
        return svrCreateDate;
    }

    public void setSvrCreateDate(String svrCreateDate) {
        this.svrCreateDate = svrCreateDate;
    }

    public Long getSvrDueId() {
        return svrDueId;
    }

    public void setSvrDueId(Long svrDueId) {
        this.svrDueId = svrDueId;
    }

    public String getSvrDueTo() {
        return svrDueTo;
    }

    public void setSvrDueTo(String svrDueTo) {
        this.svrDueTo = svrDueTo;
    }

    public String getSvrDueDate() {
        return svrDueDate;
    }

    public void setSvrDueDate(String svrDueDate) {
        this.svrDueDate = svrDueDate;
    }

    public String getSvrDeal() {
        return svrDeal;
    }

    public void setSvrDeal(String svrDeal) {
        this.svrDeal = svrDeal;
    }

    public Long getSvrDealId() {
        return svrDealId;
    }

    public void setSvrDealId(Long svrDealId) {
        this.svrDealId = svrDealId;
    }

    public String getSvrDealBy() {
        return svrDealBy;
    }

    public void setSvrDealBy(String svrDealBy) {
        this.svrDealBy = svrDealBy;
    }

    public String getSvrDealDate() {
        return svrDealDate;
    }

    public void setSvrDealDate(String svrDealDate) {
        this.svrDealDate = svrDealDate;
    }

    public String getSvrResult() {
        return svrResult;
    }

    public void setSvrResult(String svrResult) {
        this.svrResult = svrResult;
    }

    public Integer getSvrSatisfy() {
        return svrSatisfy;
    }

    public void setSvrSatisfy(Integer svrSatisfy) {
        this.svrSatisfy = svrSatisfy;
    }

    @Column(name = "svr_satisfy")
    private Integer svrSatisfy;
}
