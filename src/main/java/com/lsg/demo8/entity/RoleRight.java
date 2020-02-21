package com.lsg.demo8.entity;

import javax.persistence.*;

@Entity
@Table(name = "sys_role_right")
public class RoleRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rf_id")
    private Long rfId;

    public Long getRfRoleId() {
        return rfRoleId;
    }

    public void setRfRoleId(Long rfRoleId) {
        this.rfRoleId = rfRoleId;
    }

    public String getRfRightCode() {
        return rfRightCode;
    }

    public void setRfRightCode(String rfRightCode) {
        this.rfRightCode = rfRightCode;
    }

    @Column(name = "rf_role_id")
    private Long rfRoleId;
    @Column(name = "rf_right_code")
    private String rfRightCode;

    public Long getRfId() {
        return rfId;
    }

    public void setRfId(Long rfId) {
        this.rfId = rfId;
    }

}
