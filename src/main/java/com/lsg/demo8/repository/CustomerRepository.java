package com.lsg.demo8.repository;

import com.lsg.demo8.entity.Customer;
import org.hibernate.annotations.Subselect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.beans.Transient;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    public Customer findAllByCustNo(String custNo);

    public void deleteAllByCustNo(String no);

    @Transient
    @Query(value = "select *,count(cust_level_label) as counts  from cst_customer where 1=1 group by cust_level_label",nativeQuery = true)
    public List<Customer> kl();
}
