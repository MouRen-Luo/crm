package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.repository.ChanceRepository;
import com.lsg.demo8.service.ChanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Service
public class ChanceServiceImpl implements ChanceService {
    @Resource
    private ChanceRepository chanceRepository;

    @Override
    public Page<Chance> getChanceList(String chcCustName,String chcLinkman, String chcTitle, Pageable pageable) {
        Specification<Chance> specification = new Specification<Chance>() {
            @Override
            public Predicate toPredicate(Root<Chance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (chcCustName!=null && !chcCustName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("chcCustName"),"%"+chcCustName+"%"));
                }
                if (chcTitle!=null && chcTitle.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("chcTitle"),"%"+chcTitle+"%"));
                }
                if (chcLinkman!=null && chcLinkman.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("chcLinkman"),"%"+chcLinkman+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return chanceRepository.findAll(specification,pageable);
    }

    @Override
    public Chance add(Chance chance) {
        return chanceRepository.save(chance);
    }

    @Override
    public void del(Long chcId) {
        chanceRepository.deleteById(chcId);
    }

    @Override
    public Chance getChanceById(Long chcId) {
        return chanceRepository.getOne(chcId);
    }
}
