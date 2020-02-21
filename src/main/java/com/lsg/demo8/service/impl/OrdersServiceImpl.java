package com.lsg.demo8.service.impl;

import com.lsg.demo8.entity.*;
import com.lsg.demo8.repository.ActivityRepository;
import com.lsg.demo8.repository.OrdersLineRepository;
import com.lsg.demo8.repository.OrdersRepository;
import com.lsg.demo8.repository.ProductRepository;
import com.lsg.demo8.service.ActivityService;
import com.lsg.demo8.service.OrdersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private OrdersLineRepository ordersLineRepository;
    @Resource
    private ProductRepository productRepository;

    @Override
    public List<Orders> getActivityList(String custNo) {
        return ordersRepository.findAllByOdrCustomerNo(custNo);
    }

    @Override
    public Orders getActivityById(Long atvId) {
        return ordersRepository.getOne(atvId);
    }

    @Override
    public Orders add(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public void del(Long atvId) {
        ordersRepository.deleteById(atvId);
    }

    @Override
    public Page<Orders> getlist(String custNo, Pageable pageable) {
        Specification<Orders> specification = new Specification<Orders>() {
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                    predicates.add(criteriaBuilder.like(root.get("odrCustomerNo"),custNo));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return ordersRepository.findAll(specification,pageable);
    }

    @Override
    public OrdersLine getOne(Long oddOrderId) {
        return ordersLineRepository.getByOddOrderId(oddOrderId);
    }

    @Override
    public Product getProductList(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public Orders get(Long id) {
        return ordersRepository.getByOdrId(id);
    }

    @Override
    public void delOdd(Long atvId) {
        ordersLineRepository.deleteAllByOddId(atvId);
    }

    @Override
    public void delPro(Long atvId) {
        productRepository.deleteAllByProdId(atvId);
    }

    @Override
    public void delord(String atvId) {
        ordersRepository.deleteAllByOdrCustomerNo(atvId);
    }

    @Override
    public List<OrdersLine> findAllLin(Long oddOrderId) {
        return ordersLineRepository.findAllByOddOrderId(oddOrderId);
    }

    @Override
    public Orders getOrders(String mo) {
        return ordersRepository.getByOdrCustomerNo(mo);
    }

//    @Override
//    public Page<Orders> Orderslist(Pageable pageable) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-6);
//        Specification<Orders> specification = new Specification<Orders>() {
//            @Override
//            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<>();
//                predicates.add(criteriaBuilder.lessThan(root.get("odrDate").as(String.class),dateFormat.format(calendar.getTime())));
//                criteriaQuery.groupBy(root.get("odrCustomerNo"));
//                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//        return ordersRepository.findAll(specification,pageable);
//    }
}
