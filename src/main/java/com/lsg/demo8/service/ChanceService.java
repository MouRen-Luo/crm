package com.lsg.demo8.service;

import com.lsg.demo8.entity.Chance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChanceService {
    public Page<Chance> getChanceList(String chcCustName,String chcLinkman, String chcTitle, Pageable pageable);

    public Chance add(Chance chance);

    public void del(Long chcId);

    public Chance getChanceById(Long chcId);
}
