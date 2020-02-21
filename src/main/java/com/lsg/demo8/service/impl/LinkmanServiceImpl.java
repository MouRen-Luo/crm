package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.Linkman;
import com.lsg.demo8.repository.LinkmanRepository;
import com.lsg.demo8.service.LinkmanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class LinkmanServiceImpl implements LinkmanService {
    @Resource
    private LinkmanRepository linkmanRepository;
    @Override
    public List<Linkman> getLinkmanList(String custNo) {
        return linkmanRepository.findAllByLkmCustNo(custNo);
    }

    @Override
    public Linkman getLinkman(Long lkmId) {
        return linkmanRepository.getOne(lkmId);
    }

    @Override
    public Linkman add(Linkman linkman) {
        return linkmanRepository.save(linkman);
    }

    @Override
    public void del(Long lkmId) {
        linkmanRepository.deleteById(lkmId);
    }

    @Override
    public void delLinkman(String no) {
        linkmanRepository.deleteAllByLkmCustNo(no);
    }
}
