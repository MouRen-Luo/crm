package com.lsg.demo8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "odr_id")
    private Long odrId;
    @Column(name = "odr_customer_no")
    private String odrCustomerNo;
    @Column(name = "odr_date")
    private String odrDate;
    @Column(name = "odr_addr")
    private String odrAddr;
    @Column(name = "odr_status")
    private String odrStatus;

    public Long getOdrId() {
        return odrId;
    }

    public void setOdrId(Long odrId) {
        this.odrId = odrId;
    }

    public String getOdrCustomerNo() {
        return odrCustomerNo;
    }

    public void setOdrCustomerNo(String odrCustomerNo) {
        this.odrCustomerNo = odrCustomerNo;
    }

    public String getOdrDate() {
        return odrDate;
    }

    public void setOdrDate(String odrDate) {
        this.odrDate = odrDate;
    }

    public String getOdrAddr() {
        return odrAddr;
    }

    public void setOdrAddr(String odrAddr) {
        this.odrAddr = odrAddr;
    }

    public String getOdrStatus() {
        return odrStatus;
    }

    public void setOdrStatus(String odrStatus) {
        this.odrStatus = odrStatus;
    }
}
