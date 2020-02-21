package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.Service;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.repository.ServiceRepository;
import com.lsg.demo8.service.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Resource
    private ServiceRepository serviceRepository;

    @Override
    public Service add(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Page<Service> getList(String svrCustName1, String svrTitle1, String svrType1, Pageable pageable) {
        Specification<Service> specification = new Specification<Service>() {
            @Override
            public Predicate toPredicate(Root<Service> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (svrCustName1!=null && !svrCustName1.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrCustName"),"%"+svrCustName1+"%"));
                }
                if (svrTitle1!=null && !svrTitle1.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrTitle"),"%"+svrTitle1+"%"));
                }
                if (svrType1!=null && !svrType1.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrType"),"%"+svrType1+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return serviceRepository.findAll(specification,pageable);
    }

    @Override
    public Service getOne(Long ko) {
        return serviceRepository.getOne(ko);
    }

    @Override
    public void del(Long svrId) {
        serviceRepository.deleteById(svrId);
    }

    @Override
    public Page<Service> getListBysvrStatus(String svrCustName1, String svrTitle1, String svrType1, Pageable pageable) {
        Specification<Service> specification = new Specification<Service>() {
            @Override
            public Predicate toPredicate(Root<Service> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (svrCustName1!=null && !svrCustName1.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrCustName"),"%"+svrCustName1+"%"));
                }
                if (svrTitle1!=null && !svrTitle1.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrTitle"),"%"+svrTitle1+"%"));
                }
                if (svrType1!=null && !svrType1.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrType"),"%"+svrType1+"%"));
                }
                predicates.add(criteriaBuilder.like(root.get("svrStatus"),"已归档"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return serviceRepository.findAll(specification,pageable);
    }
}
