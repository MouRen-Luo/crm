package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.RoleRight;
import com.lsg.demo8.repository.RoleRightRepository;
import com.lsg.demo8.service.RoleRightService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleRightServiceImpl implements RoleRightService {
    @Resource
    private RoleRightRepository roleRightRepository;


    @Override
    public RoleRight addRoleRight(RoleRight roleRight) {
        return roleRightRepository.save(roleRight);
    }

    @Override
    public void del(Long rfRoleId) {
        roleRightRepository.deleteAllByRfRoleId(rfRoleId);
    }
}
