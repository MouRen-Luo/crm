package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Lost;
import com.lsg.demo8.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LostRepository extends JpaRepository<Lost, Long>, JpaSpecificationExecutor<Lost> {
    public void deleteAllByLstCustNo(String no);
}
