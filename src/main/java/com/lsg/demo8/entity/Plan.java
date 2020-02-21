package com.lsg.demo8.entity;

import javax.persistence.*;

@Entity
@Table(name = "sal_plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pla_id")
    private Long plaId;
    @Column(name = "pla_chc_id")
    private Long plachcId;
    @Column(name = "pla_date")
    private String plaDate;
    @Column(name = "pla_todo")
    private String plaTodo;
    @Column(name = "pla_result")
    private String plaResult;

    public Long getPlaId() {
        return plaId;
    }

    public void setPlaId(Long plaId) {
        this.plaId = plaId;
    }

    public Long getPlachcId() {
        return plachcId;
    }

    public void setPlachcId(Long plachcId) {
        this.plachcId = plachcId;
    }

    public String getPlaDate() {
        return plaDate;
    }

    public void setPlaDate(String plaDate) {
        this.plaDate = plaDate;
    }

    public String getPlaTodo() {
        return plaTodo;
    }

    public void setPlaTodo(String plaTodo) {
        this.plaTodo = plaTodo;
    }

    public String getPlaResult() {
        return plaResult;
    }

    public void setPlaResult(String plaResult) {
        this.plaResult = plaResult;
    }
}
