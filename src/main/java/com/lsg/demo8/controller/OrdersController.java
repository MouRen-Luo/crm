package com.lsg.demo8.controller;

import com.lsg.demo8.entity.*;
import com.lsg.demo8.service.ActivityService;
import com.lsg.demo8.service.CustomerService;
import com.lsg.demo8.service.OrdersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrdersController {
    @Resource
    private OrdersService ordersService;
    @Resource
    private CustomerService customerService;


    @RequestMapping("/customer/orders")
    public String Activity(String custNo, Model model,
                                            @RequestParam(required = false, defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"odrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Orders> list = ordersService.getlist(custNo,pageable);
        Customer customer = customerService.getByno(custNo);
        model.addAttribute("chancePager",list);
        model.addAttribute("atvCustName",customer.getCustName());
        model.addAttribute("atvCustNo",customer.getCustNo());
        return "orders/list";
    }

    @RequestMapping("/orders/enact")
    public String enact(Long odrId,Model model){
        Orders orders = ordersService.get(odrId);
        model.addAttribute("or",orders);
        OrdersLine ordersLine = ordersService.getOne(orders.getOdrId());
        model.addAttribute("orl",ordersLine);
        Product product = ordersService.getProductList(ordersLine.getOddProdId());
        model.addAttribute("pro",product);
        return "orders/check";
    }
}
