package com.lsg.demo8.repository;

import com.lsg.demo8.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    public List<User> findByUsrNameAndUsrPassword(String usrName,String usrPassword);

    //根据用户名查询用户
    public User findUserByUsrName(String usrName);
}
