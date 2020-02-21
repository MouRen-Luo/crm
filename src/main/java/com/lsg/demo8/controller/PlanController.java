package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.entity.Plan;
import com.lsg.demo8.service.ChanceService;
import com.lsg.demo8.service.PlanService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PlanController {
    @Resource
    private PlanService planService;
    @Resource
    private ChanceService chanceService;

    @RequestMapping(value = "/plan/list")
    public String ChanceList(Model model, String chcCustName,
                             @RequestParam(required = false)String chcTitle,
                             @RequestParam(required = false)String chcLinkman,
                             @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"chcId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Chance> page = chanceService.getChanceList(chcCustName,chcLinkman,chcTitle,pageable);
        model.addAttribute("planPager",page);
        model.addAttribute("chcCustName",chcCustName);
        model.addAttribute("chcLinkman",chcLinkman);
        model.addAttribute("chcTitle",chcTitle);
        return "plan/list";
    }

    @RequestMapping("/plan/check")
    public String edit(Long chcId,Model model){
        Chance chance=chanceService.getChanceById(chcId);
        model.addAttribute("plan",chance);
        return "plan/check";
    }

    @RequestMapping("/plan/enact")
    public String enact(Long chcId,Model model){
        Chance chance=chanceService.getChanceById(chcId);
        List<Plan> plans = planService.getPlanList(chcId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        model.addAttribute("plaDate",df.format(new Date()));
        model.addAttribute("plans",plans);
        model.addAttribute("plachcId",chcId);
        model.addAttribute("plan",chance);
        return "plan/enact";
    }

    @RequestMapping("/plan/save")
    @ResponseBody
    public Map save(Plan plan){
        Plan plan1 = planService.save(plan);
        Map<String,String> map = new HashMap<>();
        if (plan!=null){
            map.put("delResult","true");
        }else{
            map.put("delResult","false");
        }
        return map;
    }

    @RequestMapping("/plan/del")
    @ResponseBody
    public Map del(Long plaId){
        planService.del(plaId);
        Map<String,String> map = new HashMap<>();
        map.put("delResult","true");
        return map;
    }

    @RequestMapping("/plan/assign")
    public String zx(Long chcId,Model model){
        Chance chance=chanceService.getChanceById(chcId);
        List<Plan> plans = planService.getPlanList(chcId);
        model.addAttribute("plans",plans);
        model.addAttribute("chcId",chcId);
        model.addAttribute("plan",chance);
        return "plan/perform";
    }

    @RequestMapping("/plan/kf")
    @ResponseBody
    public Map kf(Long chcId){
        Chance chance=chanceService.getChanceById(chcId);
        chance.setChcStatus("已归档");
        Chance chance1 = chanceService.add(chance);
        Map<String,String> map = new HashMap<>();
        if (chance1!=null){
            map.put("delResult","true");
        }
        return map;
    }

    @RequestMapping("/plan/saveo")
    @ResponseBody
    public Map saveo(Long plaId,String plaResult){
        Plan plan = planService.getone(plaId);
        plan.setPlaResult(plaResult);
        Plan plan1 = planService.save(plan);
        Map<String,String> map = new HashMap<>();
        if (plan!=null){
            map.put("delResult","true");
        }else{
            map.put("delResult","false");
        }
        return map;
    }
}
