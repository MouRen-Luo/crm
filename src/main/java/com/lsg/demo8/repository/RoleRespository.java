package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleRespository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    public Role findRoleByUsers(User user);

    public List<Role> findRolesByRoleNameLike(String roleName);

    Page<Role> findAll(Specification<Role> specification, Pageable pageable);
}
