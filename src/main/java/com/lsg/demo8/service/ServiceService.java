package com.lsg.demo8.service;

import com.lsg.demo8.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceService {

    public Service add(Service service);

    public Page<Service> getList(String svrCustName1, String svrTitle1, String svrType1, Pageable pageable);

    public Service getOne(Long ko);

    public void del(Long svrId);

    public Page<Service> getListBysvrStatus(String svrCustName1, String svrTitle1, String svrType1, Pageable pageable);
}
