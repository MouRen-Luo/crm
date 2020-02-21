package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Activity;
import com.lsg.demo8.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.beans.Transient;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
    public List<Orders> findAllByOdrCustomerNo(String no);

    public Orders getByOdrId(Long id);

    public void deleteAllByOdrCustomerNo(String no);

    public Orders getByOdrCustomerNo(String no);

    @Transient
    @Query(value = "select `odr_customer_no`,max(odr_date) as odr_date,`odr_status`,`odr_addr`,`odr_id` from orders \n"+
                        " where timestampdiff(month,`odr_date`,NOW())>6 \n"+
                        " group by `odr_customer_no`",nativeQuery = true)
    public List<Orders> getList();

}
