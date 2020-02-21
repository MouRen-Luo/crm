package com.lsg.demo8.service;

import com.lsg.demo8.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    public Page<Role> getRoleList(Long roleId, String roleName, Pageable pageable);

    public List<Role> getRole();

    void del(Long roleId);

    Role addRole(Role role);

    public Role getRoleBYId(Long roleId);
}
