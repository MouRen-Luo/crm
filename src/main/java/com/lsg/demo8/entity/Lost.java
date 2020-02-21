package com.lsg.demo8.entity;

import javax.persistence.*;

@Entity
@Table(name = "cst_lost")
public class Lost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lst_id")
    private Long lstId;
    @Column(name = "lst_cust_no")
    private String lstCustNo;
    @Column(name = "lst_cust_name")
    private String lstCustName;
    @Column(name = "lst_cust_manager_id")
    private Long lstCustManagerId;
    @Column(name = "lst_cust_manager_name")
    private String lstCustManagerName;
    @Column(name = "lst_last_order_date")
    private String lstLastOrderDate;
    @Column(name = "lst_lost_date")
    private String lstLostDate;
    @Column(name = "lst_delay")
    private String lstDelay;
    @Column(name = "lst_reason")
    private String lstReason;

    public Long getLstId() {
        return lstId;
    }

    public void setLstId(Long lstId) {
        this.lstId = lstId;
    }

    public String getLstCustNo() {
        return lstCustNo;
    }

    public void setLstCustNo(String lstCustNo) {
        this.lstCustNo = lstCustNo;
    }

    public String getLstCustName() {
        return lstCustName;
    }

    public void setLstCustName(String lstCustName) {
        this.lstCustName = lstCustName;
    }

    public Long getLstCustManagerId() {
        return lstCustManagerId;
    }

    public void setLstCustManagerId(Long lstCustManagerId) {
        this.lstCustManagerId = lstCustManagerId;
    }

    public String getLstCustManagerName() {
        return lstCustManagerName;
    }

    public void setLstCustManagerName(String lstCustManagerName) {
        this.lstCustManagerName = lstCustManagerName;
    }

    public String getLstLastOrderDate() {
        return lstLastOrderDate;
    }

    public void setLstLastOrderDate(String lstLastOrderDate) {
        this.lstLastOrderDate = lstLastOrderDate;
    }

    public String getLstLostDate() {
        return lstLostDate;
    }

    public void setLstLostDate(String lstLostDate) {
        this.lstLostDate = lstLostDate;
    }

    public String getLstDelay() {
        return lstDelay;
    }

    public void setLstDelay(String lstDelay) {
        this.lstDelay = lstDelay;
    }

    public String getLstReason() {
        return lstReason;
    }

    public void setLstReason(String lstReason) {
        this.lstReason = lstReason;
    }

    public String getLstStatus() {
        return lstStatus;
    }

    public void setLstStatus(String lstStatus) {
        this.lstStatus = lstStatus;
    }

    @Column(name = "lst_status")
    private String lstStatus;
}
