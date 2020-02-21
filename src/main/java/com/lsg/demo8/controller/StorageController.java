package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Storage;
import com.lsg.demo8.service.StorageService;
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
public class StorageController {
    @Resource
    private StorageService storageService;

    @RequestMapping("/storage/list")
    public String StorageList(Model model, @RequestParam(required = false)String product,
                              @RequestParam(required = false)String stkWarehouse,
                              @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"stkId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Storage> page = storageService.getStorageList(product,stkWarehouse,pageable);
        model.addAttribute("product",product);
        model.addAttribute("stkWarehouse",stkWarehouse);
        model.addAttribute("strPager",page);
        return "storage/list";
    }

}
