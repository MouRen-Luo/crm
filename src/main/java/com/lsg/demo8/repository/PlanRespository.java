package com.lsg.demo8.repository;


import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.Max;
import java.util.List;


public interface PlanRespository extends JpaRepository<Plan,Long>, JpaSpecificationExecutor<Plan> {
    void deleteByPlachcId(Long chcId);

    List<Plan> findAllByPlachcId(Long chcId);
}
