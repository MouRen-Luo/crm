package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.entity.Right;
import com.lsg.demo8.repository.RightRepository;
import com.lsg.demo8.repository.RoleRespository;
import com.lsg.demo8.repository.UserRepository;
import com.lsg.demo8.service.UserService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

@Service("userService")
public class IUserServiceImpl implements UserService{
    @Resource
    private UserRepository userBeanRepository;
    @Resource
    private RoleRespository roleRespository;
    @Resource
    private RightRepository rightRepository;

    //登录
    @Override
    @Cacheable(value = "getUser",keyGenerator = "keyGenerator")
    public User getUser(String usrName) {
        return userBeanRepository.findUserByUsrName(usrName);
    }

    //以Id查询
    @Override
    public User getById(Long usrId) {
        return userBeanRepository.getOne(usrId);
    }

    //查询全部
    @Override
    public Page<User> getUserList(String usrName, Long roleId, Pageable pageable) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (usrName!=null && !usrName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("usrName"),"%"+usrName+"%"));
                }
                if (roleId!=null && roleId.longValue() != 0l){
                    Role role = new Role();
                    role.setRoleId(roleId);
                    predicates.add(criteriaBuilder.equal(root.get("role"),role));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return userBeanRepository.findAll(specification,pageable);
    }

    //新增
    @Override
    public User addUser(User user) {
        return userBeanRepository.save(user);
    }

    //删除
    @Override
    public void del(Long usrId) {
        userBeanRepository.deleteById(usrId);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRespository.findAll();
    }

    /**
     * 根据User获得Role
     * @param user
     * @return
     */
    @Override
    public Role getRoleByUser(User user) {
        return roleRespository.findRoleByUsers(user);
    }

    @Override
    public void saveRole(Role role) {
        roleRespository.save(role);
    }

    @Override
    public List<Role> findRoles(String roleName) {
        List<Role> list = null;
        if (roleName!=null){
            list = roleRespository.findRolesByRoleNameLike("%"+roleName+"%");
        }else{
            list = roleRespository.findAll();
        }
        return list;
    }

    /**
     * 获得全部Right
     * @return
     */
    @Override
    public List<Right> findAllRights() {
        return rightRepository.findAll();
    }

    /**
     * 根据Role获得Roight
     * @param role
     * @return
     */
    @Override
    public List<Right> findRightsByRole(Role role) {
        return rightRepository.findRightsByRoles(role);
    }
}
