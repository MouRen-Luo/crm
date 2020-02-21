package com.lsg.demo8.repository;


import com.lsg.demo8.entity.Right;
import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.RoleRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRightRepository extends JpaRepository<RoleRight,Long> {
    public void deleteAllByRfRoleId(Long id);
}
