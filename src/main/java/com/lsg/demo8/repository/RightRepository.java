package com.lsg.demo8.repository;


import com.lsg.demo8.entity.Right;
import com.lsg.demo8.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RightRepository extends JpaRepository<Right,String> {
    public List<Right> findRightsByRoles(Role role);
}
