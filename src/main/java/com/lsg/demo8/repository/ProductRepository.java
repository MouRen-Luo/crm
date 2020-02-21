package com.lsg.demo8.repository;

import com.lsg.demo8.entity.OrdersLine;
import com.lsg.demo8.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    public Product getByProdId(Long id);

    public void deleteAllByProdId(Long id);
}
