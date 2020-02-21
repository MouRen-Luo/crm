package com.lsg.demo8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_user")
public class UserBean implements Serializable {
    @Column(name = "usr_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usrId;
    @Column(name = "usr_name")
    private String usrName;
    @Column(name = "usr_password")
    private String usrPassword;
    @ManyToOne(targetEntity = Role.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_role_id")
    private Role role;
    @Column(name = "usr_flag")
    @JsonIgnore
    private String usrFlag;

    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsrFlag() {
        return usrFlag;
    }

    public void setUsrFlag(String usrFlag) {
        this.usrFlag = usrFlag;
    }
}
