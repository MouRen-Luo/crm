package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Lost;
import com.lsg.demo8.repository.LostRepository;
import com.lsg.demo8.service.LostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LostServiceImpl implements LostService {
    @Resource
    private LostRepository lostRepository;

    @Override
    public Page<Lost> getLostList(String name, String manName,String lstStatus, Pageable pageable) {
        Specification<Lost> specification = new Specification<Lost>() {
            @Override
            public Predicate toPredicate(Root<Lost> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name!=null && !name.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("lstCustName"),"%"+name+"%"));
                }
                if (manName!=null && !manName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("lstCustManagerName"),"%"+manName+"%"));
                }
                if (lstStatus!=null && !lstStatus.equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("lstStatus"),lstStatus));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return lostRepository.findAll(specification,pageable);
    }

    @Override
    public Page<Lost> getLostList2(String name, String manName, Pageable pageable) {
        Specification<Lost> specification = new Specification<Lost>() {
            @Override
            public Predicate toPredicate(Root<Lost> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name!=null && !name.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("lstCustName"),"%"+name+"%"));
                }
                if (manName!=null && !manName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("lstCustManagerName"),"%"+manName+"%"));
                }
                predicates.add(criteriaBuilder.like(root.get("lstStatus"),"确认流失"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return lostRepository.findAll(specification,pageable);
    }

    @Override
    public Lost getone(Long id) {
        return lostRepository.getOne(id);
    }

    @Override
    public Lost add(Lost lost) {
        return lostRepository.save(lost);
    }
}
