package com.lsg.demo8.service.impl;


import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.repository.RoleRespository;
import com.lsg.demo8.service.RoleService;
import org.codehaus.groovy.runtime.StringGroovyMethods;
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
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleRespository roleRespository;

    //角色列表
    @Override
    public Page<Role> getRoleList(Long roleId, String roleName, Pageable pageable) {
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (roleName!=null && !roleName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("roleName"),"%"+roleName+"%"));
                }
                if (roleId!=null && roleId.longValue() != 0l){
                    predicates.add(criteriaBuilder.equal(root.get("roleId"),roleId));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return roleRespository.findAll(specification,pageable);
    }

    @Override
    public Role getRoleBYId(Long roleId) {
        return roleRespository.getOne(roleId);
    }

    @Override
    public void del(Long roleId) {
        roleRespository.deleteById(roleId);
    }
    //查询全部
    @Override
    public List<Role> getRole() {
        return roleRespository.findAll();
    }

    @Override
    public Role addRole(Role role) {
        return roleRespository.save(role);
    }
}
