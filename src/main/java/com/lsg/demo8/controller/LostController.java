package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Lost;
import com.lsg.demo8.service.LostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class LostController {
    @Resource
    private LostService lostService;

    @RequestMapping("/lost/list")
    public String LostList(Model model, @RequestParam(required = false)String lstCustName,
                           @RequestParam(required = false)String lstCustManagerName,
                           @RequestParam(required = false)String lstStatus,
                           @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"lstId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Lost> list = lostService.getLostList(lstCustName,lstCustManagerName,lstStatus,pageable);
        model.addAttribute("lostPager",list);
        model.addAttribute("lstCustName",lstCustName);
        model.addAttribute("lstCustManagerName",lstCustManagerName);
        model.addAttribute("lstStatus",lstStatus);
        return "lost/list";
    }

    @RequestMapping("/customer/lost")
    public String LostList2(Model model, @RequestParam(required = false)String lstCustName,
                           @RequestParam(required = false)String lstCustManagerName,
                           @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"lstId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Lost> list = lostService.getLostList2(lstCustName,lstCustManagerName,pageable);
        model.addAttribute("lostPager",list);
        model.addAttribute("lstCustName",lstCustName);
        model.addAttribute("lstCustManagerName",lstCustManagerName);
        return "customer/lost";
    }

    @RequestMapping("/lost/defer")
    public String Defer(Long lstId,Model model){
        Lost lost = lostService.getone(lstId);
        model.addAttribute("lost",lost);
        return "lost/defer";
    }

    @RequestMapping("/lost/save")
    public String save(Lost lost,String dc,String lstDelay2){
        String num = lost.getLstDelay()+lstDelay2;
        System.out.println(num);
        if (dc.equals("defer")){
            System.out.println(123);
            lost.setLstDelay(num);
        }
        Lost lost1 = lostService.add(lost);
        if (lost1!=null){
            return "redirect:/lost/list";
        }
        return "lost/defer";
    }

    @RequestMapping("/lost/affirm")
    public String Affirm(Long lstId,Model model){
        Lost lost = lostService.getone(lstId);
        model.addAttribute("lost",lost);
        return "lost/affirm";
    }
}
