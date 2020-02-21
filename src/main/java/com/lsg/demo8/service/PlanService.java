package com.lsg.demo8.service;

import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanService {
    public void del(Long plaId);

    public Chance getChanceById(Long chcId);

    public List<Plan> getPlanList(Long chcId);

    public Plan save(Plan plan);

    public Plan getone(Long plaId);
}
