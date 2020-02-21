package com.lsg.demo8.service;

import com.lsg.demo8.entity.Customer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    //查询全部
    public Page<Customer> getUserList(String custName,String custNo,String custManagerName, String dictItem,String dictItem2, Pageable pageable);

    public Customer getone(Long custId);

    public Customer add(Customer customer);

    public Customer getByno(String no);

    public void del(String no);

    public void delLost(String no);

    public void delService(String no);

    public List<Customer> findAll();

    public XSSFWorkbook show();
}
