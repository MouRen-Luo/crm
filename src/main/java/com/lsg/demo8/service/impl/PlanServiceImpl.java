package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.entity.Plan;
import com.lsg.demo8.repository.ChanceRepository;
import com.lsg.demo8.repository.PlanRespository;
import com.lsg.demo8.service.PlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Resource
    private PlanRespository planRespository;
    @Resource
    private ChanceRepository chanceRepository;

    @Override
    @Transactional
    public void del(Long plaId) {
        planRespository.deleteById(plaId);
    }

    @Override
    public Chance getChanceById(Long chcId) {
        return chanceRepository.getOne(chcId);
    }

    @Override
    public List<Plan> getPlanList(Long chcId) {
        return planRespository.findAllByPlachcId(chcId);
    }

    @Override
    public Plan save(Plan plan) {
        return planRespository.save(plan);
    }

    @Override
    public Plan getone(Long plaId) {
        return planRespository.getOne(plaId);
    }
}
