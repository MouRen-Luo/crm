package com.lsg.demo8.service;

import com.lsg.demo8.entity.Right;
import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public User getUser(String usrName);

    //按usrId查询
    public User getById(Long usrId);
    //查询全部
    public Page<User> getUserList(String usrName, Long roleId, Pageable pageable);
    //新增
    public User addUser(User user);
    //删除
    public void del(Long usrId);

    //获取所有角色
    public List<Role> findAllRoles();

    //获取用户》角色
    public Role getRoleByUser(User user);

    //保存角色
    public void saveRole(Role role);

    //获取角色
    public List<Role> findRoles(String roleName);

    //获取所有权限
    public List<Right> findAllRights();

    //获取用户》角色》权限
    public List<Right> findRightsByRole(Role role);
}
