package com.lsg.demo8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sal_chance")
public class Chance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "chc_id")
    private Long chcId;
    @Column(name = "chc_source")
    private String chcSource;
    @Column(name = "chc_cust_name")
    private String chcCustName;
    @Column(name = "chc_title")
    private String chcTitle;
    @Column(name = "chc_rate")
    private Integer chcRate;
    @Column(name = "chc_linkman")
    private String chcLinkman;
    @Column(name = "chc_tel")
    private String chcTel;
    @Column(name = "chc_desc")
    private String chcDesc;
    @Column(name = "chc_create_id")
    private Long chcCreateId;
    @Column(name = "chc_create_by")
    private String chcCreateBy;
    @Column(name = "chc_create_date")
    private String chcCreateDate;
    @Column(name = "chc_due_id")
    private Long chcDueId;
    @Column(name = "chc_due_to")
    private String chcDueTo;
    @Column(name = "chc_due_date")
    private String chcDueDate;

    public Long getChcId() {
        return chcId;
    }

    public void setChcId(Long chcId) {
        this.chcId = chcId;
    }

    public String getChcSource() {
        return chcSource;
    }

    public void setChcSource(String chcSource) {
        this.chcSource = chcSource;
    }

    public String getChcCustName() {
        return chcCustName;
    }

    public void setChcCustName(String chcCustName) {
        this.chcCustName = chcCustName;
    }

    public String getChcTitle() {
        return chcTitle;
    }

    public void setChcTitle(String chcTitle) {
        this.chcTitle = chcTitle;
    }

    public Integer getChcRate() {
        return chcRate;
    }

    public void setChcRate(Integer chcRate) {
        this.chcRate = chcRate;
    }

    public String getChcLinkman() {
        return chcLinkman;
    }

    public void setChcLinkman(String chcLinkman) {
        this.chcLinkman = chcLinkman;
    }

    public String getChcTel() {
        return chcTel;
    }

    public void setChcTel(String chcTel) {
        this.chcTel = chcTel;
    }

    public String getChcDesc() {
        return chcDesc;
    }

    public void setChcDesc(String chcDesc) {
        this.chcDesc = chcDesc;
    }

    public Long getChcCreateId() {
        return chcCreateId;
    }

    public void setChcCreateId(Long chcCreateId) {
        this.chcCreateId = chcCreateId;
    }

    public String getChcCreateBy() {
        return chcCreateBy;
    }

    public void setChcCreateBy(String chcCreateBy) {
        this.chcCreateBy = chcCreateBy;
    }

    public String getChcCreateDate() {
        return chcCreateDate;
    }

    public void setChcCreateDate(String chcCreateDate) {
        this.chcCreateDate = chcCreateDate;
    }

    public Long getChcDueId() {
        return chcDueId;
    }

    public void setChcDueId(Long chcDueId) {
        this.chcDueId = chcDueId;
    }

    public String getChcDueTo() {
        return chcDueTo;
    }

    public void setChcDueTo(String chcDueTo) {
        this.chcDueTo = chcDueTo;
    }

    public String getChcDueDate() {
        return chcDueDate;
    }

    public void setChcDueDate(String chcDueDate) {
        this.chcDueDate = chcDueDate;
    }

    public String getChcStatus() {
        return chcStatus;
    }

    public void setChcStatus(String chcStatus) {
        this.chcStatus = chcStatus;
    }

    @Column(name = "chc_status")
    private String chcStatus;
}
