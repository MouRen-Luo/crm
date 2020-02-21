package com.lsg.demo8.service;

import com.lsg.demo8.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    public Page<Product> getProductList(String prodName, String prodType, String prodBatch, Pageable pageable);
}
