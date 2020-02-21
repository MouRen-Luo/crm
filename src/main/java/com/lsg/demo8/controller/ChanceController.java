package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.entity.Customer;
import com.lsg.demo8.entity.Dict;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.service.ChanceService;
import com.lsg.demo8.service.DictService;
import com.lsg.demo8.service.PlanService;
import org.apache.catalina.Session;
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
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChanceController {
    @Resource
    private ChanceService chanceService;
    @Resource
    private DictService dictService;
    @Resource
    private PlanService planService;

    @RequestMapping(value = "/chance/list")
    public String ChanceList(Model model, String chcCustName,
                             @RequestParam(required = false)String chcTitle,
                             @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"chcId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Chance> page = chanceService.getChanceList(chcCustName,null,chcTitle,pageable);
        model.addAttribute("chancePager",page);
        model.addAttribute("chcCustName",chcCustName);
        model.addAttribute("chcTitle",chcTitle);
        return "chance/list";
    }

    @RequestMapping(value = "/chance/add")
    public String add(Model model){
        List<Dict> dict1=dictService.getDictByType("客户等级");
        model.addAttribute("dicts",dict1);
        List<Dict> dict2 =dictService.getDictByType("地区");
        model.addAttribute("dictb",dict2);
        return "chance/add";
    }

    @RequestMapping(value = "/chance/save")
    public String save(Chance chance, String dictItem, String dictItem2, HttpSession session){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        chance.setChcCreateDate(df.format(new Date()));
        User chan = (User) session.getAttribute("user");
        if (chance.getChcCreateBy()==null && chance.getChcCreateBy()==""){
            chance.setChcCreateBy(chan.getUsrName());
        }
        chance.setChcCreateId(1L);
        Chance chance1=chanceService.add(chance);
        if(chance1!=null){
            return "redirect:/chance/list";
        }
        return "chance/add";
    }

    @RequestMapping("/chance/del")
    @ResponseBody
    public Map del(Long chcId,HttpSession session){
        Chance chance=chanceService.getChanceById(chcId);
        User chan = (User) session.getAttribute("user");
        Map map = new HashMap<>();
        if (!chance.getChcCreateBy().equals(chan.getUsrName())){
            map.put("delResult","false");
        }else {
            planService.del(chcId);
            chanceService.del(chcId);
            map.put("delResult", "true");
        }
        return map;
    }

    @RequestMapping("/chance/edit")
    public String edit(Long chcId,Model model){
        Chance chance=chanceService.getChanceById(chcId);
        model.addAttribute("chance",chance);
        List<Dict> dict1=dictService.getDictByType("客户等级");
        model.addAttribute("dicts",dict1);
        List<Dict> dict2 =dictService.getDictByType("地区");
        model.addAttribute("dictb",dict2);
        return "chance/edit";
    }

    @RequestMapping("/chance/assign")
    public String assign(Long chcId,Model model){
        Chance chance=chanceService.getChanceById(chcId);
        model.addAttribute("chance",chance);
        return "chance/assign";
    }

    @RequestMapping("/chance/assignsave")
    public String assginSave(Chance chance){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        chance.setChcCreateDate(df.format(new Date()));
        chance.setChcStatus("开发中");
        chance.setChcCreateId(1L);
        Chance chance1=chanceService.add(chance);
        if(chance1!=null){
            return "redirect:/chance/list";
        }
        return "chance/add";
    }
}
