package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Orders;
import com.lsg.demo8.entity.OrdersLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrdersLineRepository extends JpaRepository<OrdersLine, Long>, JpaSpecificationExecutor<OrdersLine> {
    public OrdersLine getByOddOrderId(Long id);

    public List<OrdersLine> findAllByOddOrderId(Long id);

    public void deleteAllByOddId(Long id);
}
