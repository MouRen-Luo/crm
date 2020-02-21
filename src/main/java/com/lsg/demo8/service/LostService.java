package com.lsg.demo8.service;

import com.lsg.demo8.entity.Lost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LostService {
    public Page<Lost> getLostList(String name, String manName,String lstStatus, Pageable pageable);

    public Page<Lost> getLostList2(String name, String manName, Pageable pageable);

    public Lost getone(Long id);

    public Lost add(Lost lost);
}
