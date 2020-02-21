package com.lsg.demo8.service;

import com.lsg.demo8.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersService {
    public List<Orders> getActivityList(String custNo);

    public Orders getActivityById(Long atvId);

    public Orders add(Orders Orders);

    public void del(Long atvId);

    public Page<Orders> getlist(String custNo, Pageable pageable);

    public OrdersLine getOne(Long oddOrderId);

    public Product getProductList(Long id);

    public Orders get(Long id);

    public void delOdd(Long atvId);

    public void delPro(Long atvId);

    public void delord(String atvId);

    public List<OrdersLine> findAllLin(Long oddOrderId);

    public Orders getOrders(String mo);

//    public Page<Orders> Orderslist(Pageable pageable);
}
