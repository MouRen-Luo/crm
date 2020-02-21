package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Activity;
import com.lsg.demo8.entity.Chance;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @RequestMapping("/customer/activity")
    public String Activity(String custNo, Model model){
        List<Activity> list = activityService.getActivityList(custNo);
        model.addAttribute("lin",list);
        model.addAttribute("atvCustName",list.get(0).getAtvCustName());
        model.addAttribute("atvCustNo",list.get(0).getAtvCustNo());
        return "Activity/list";
    }

    @RequestMapping("/activity/edit")
    public String edit(Long atvId,Model model){
        Activity activity = activityService.getActivityById(atvId);
        model.addAttribute("ac",activity);
        return "Activity/edit";
    }

    @RequestMapping("/activity/save")
    public String save(Activity activity){
        Activity activity1 = activityService.add(activity);
        if (activity1!=null){
            return "redirect:Activity/list";
        }else {
            return "Activity/edit";
        }
    }

    @RequestMapping("/activity/add")
    public String add(String atvCustNo,String atvCustName,Model model){
        model.addAttribute("atvCustNo",atvCustNo);
        model.addAttribute("atvCustName",atvCustName);
        return "Activity/add";
    }

    @RequestMapping("/activity/del")
    @ResponseBody
    public Map del(Long atvId){
        activityService.del(atvId);
        Map map = new HashMap<>();
        map.put("delResult", "true");
        return map;
    }
}
