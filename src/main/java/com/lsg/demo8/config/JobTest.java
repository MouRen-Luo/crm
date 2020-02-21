package com.lsg.demo8.config;

import com.lsg.demo8.entity.Customer;
import com.lsg.demo8.entity.Lost;
import com.lsg.demo8.entity.Orders;
import com.lsg.demo8.repository.OrdersRepository;
import com.lsg.demo8.service.CustomerService;
import com.lsg.demo8.service.LostService;
import com.lsg.demo8.service.OrdersService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.List;

public class JobTest implements Job {
    @Resource
    private OrdersService ordersService;
    @Resource
    private LostService lostService;
    @Resource
    private CustomerService customerService;
    @Resource
    private OrdersRepository ordersRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("测试quartz  Job定时任务！！");
        List<Orders> list = ordersRepository.getList();
        for (Orders li:
                list) {
            System.out.println(li.getOdrCustomerNo()+"----"+li.getOdrDate());
            Customer customer = customerService.getByno(li.getOdrCustomerNo());
            Lost lost = new Lost();
            lost.setLstCustNo(customer.getCustNo());
            lost.setLstCustName(customer.getCustName());
            lost.setLstCustManagerId(customer.getCustManagerId());
            lost.setLstCustManagerName(customer.getCustManagerName());
            lost.setLstStatus("暂缓流失");
            lost.setLstLastOrderDate(li.getOdrDate());
            Lost lost1 = lostService.add(lost);
            if (lost1!=null){
                System.out.println("添加成功");
            }else{
                System.out.println("添加失败");
            }
        }
    }
}
