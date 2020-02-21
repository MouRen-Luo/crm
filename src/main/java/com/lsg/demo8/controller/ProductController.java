package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Product;
import com.lsg.demo8.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class ProductController {
    @Resource
    private ProductService productService;

    /**
     * 查询产品信息 分页
     * @param model
     * @param prodName
     * @param prodType
     * @param prodBatch
     * @param pageIndex
     * @return
     */
    @RequestMapping("/product/list")
    public String ProductList(Model model, @RequestParam(required = false)String prodName,
                              @RequestParam(required = false) String prodType,
                              @RequestParam(required = false) String prodBatch,
                              @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"prodId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Product> list = productService.getProductList(prodName,prodType,prodBatch,pageable);
        model.addAttribute("proPager",list);
        model.addAttribute("prodName",prodName);
        model.addAttribute("prodType",prodType);
        model.addAttribute("prodBatch",prodBatch);
        return "product/list";
    }
}
