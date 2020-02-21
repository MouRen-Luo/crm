package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Linkman;
import com.lsg.demo8.service.LinkmanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LinkmanController {
    @Resource
    private LinkmanService linkmanService;

    @RequestMapping("/customer/tel")
    public String tel(String custNo, Model model){
        List<Linkman> list = linkmanService.getLinkmanList(custNo);
        model.addAttribute("lin",list);
        return "linkman/tel";
    }

    @RequestMapping("/linkman/edit")
    public String edit(Long lkmId,Model model){
        Linkman lik = linkmanService.getLinkman(lkmId);
        model.addAttribute("lin",lik);
        return "linkman/edit";
    }

    @RequestMapping("/linkman/save")
    public String save(Linkman linkman){
        Linkman linkman1 = linkmanService.add(linkman);
        if (linkman1!=null){
            return "redirect:/linkman/tel";
        }else{
            return "linkman/edit";
        }
    }

    @RequestMapping("/linkman/del")
    @ResponseBody
    public Map del(Long lkmId){
        linkmanService.del(lkmId);
        Map<String,String> map = new HashMap<>();
        map.put("delResult","true");
        return map;
    }
}
