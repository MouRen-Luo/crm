package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Lost;
import com.lsg.demo8.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRepository extends JpaRepository<Service, Long>, JpaSpecificationExecutor<Service> {
    public void deleteAllBySvrCustNo(String no);
}
