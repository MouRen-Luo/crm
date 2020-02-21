package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Activity;
import com.lsg.demo8.entity.Chance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {
    public List<Activity> findAllByAtvCustNo(String custNo);

    public void deleteAllByAtvCustNo(String no);
}
