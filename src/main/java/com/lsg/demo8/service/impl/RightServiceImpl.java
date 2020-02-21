package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Right;
import com.lsg.demo8.repository.RightRepository;
import com.lsg.demo8.service.RightService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RightServiceImpl implements RightService {
    @Resource
    private RightRepository rightRepository;

    @Override
    public List<Right> findAll() {
        return rightRepository.findAll();
    }
}
