package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Customer;
import com.lsg.demo8.entity.Dict;
import com.lsg.demo8.entity.Service;
import com.lsg.demo8.service.CustomerService;
import com.lsg.demo8.service.DictService;
import com.lsg.demo8.service.ServiceService;
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
public class ServiceController {
    @Resource
    private CustomerService customerService;
    @Resource
    private DictService dictService;
    @Resource
    private ServiceService serviceService;

    @RequestMapping("/service/add")
    public String add(Model model){
        List<Customer> customers = customerService.findAll();
        List<Dict> dicts = dictService.getDictByType("服务类型");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("date",df.format(new Date()));
        model.addAttribute("cus",customers);
        model.addAttribute("di",dicts);
        return "service/add";
    }

    @RequestMapping("/service/save")
    public String save(Service service){
        Customer customer = customerService.getByno(service.getSvrCustNo());
        service.setSvrCustName(customer.getCustName());
        Service service1 = serviceService.add(service);
        if (service1!=null){
            return "redirect:/service/add";
        }else{
            return "service/add";
        }
    }

    @RequestMapping("/service/allotsave")
    public String allotsave(Service service){
        Service service1 = serviceService.add(service);
        if (service1!=null){
            return "redirect:/service/dealList";
        }else{
            return "service/allot";
        }
    }

    @RequestMapping("/service/dispatch")
    public String getList(Model model, @RequestParam(required = false)String svrCustName1,
                          @RequestParam(required = false)String svrTitle1,
                          @RequestParam(required = false)String svrType1,
                          @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Service> ser = serviceService.getList(svrCustName1,svrTitle1,svrType1,pageable);
        model.addAttribute("serPager",ser);
        model.addAttribute("svrCustName",svrCustName1);
        model.addAttribute("svrTitle",svrTitle1);
        model.addAttribute("svrType",svrType1);
        List<Dict> dicts = dictService.getDictByType("服务类型");
        model.addAttribute("di",dicts);
        return "service/dispatch";
    }

    @RequestMapping("/service/saveo")
    public String saveo(Long svrId,String chcDueTo){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Service service = serviceService.getOne(svrId);
        service.setSvrDueDate(dateFormat.format(new Date()));
        service.setSvrDueTo(chcDueTo);
        service.setSvrStatus("已分配");
        Service service1 = serviceService.add(service);
        return "redirect:/service/dispatch";
    }

    @RequestMapping("/service/del")
    @ResponseBody
    public Map del(Long svrId){
        Map<String,String> map = new HashMap<>();
        serviceService.del(svrId);
        map.put("delResult","true");
        return map;
    }

    @RequestMapping("/service/dealList")
    public String deal(Model model, @RequestParam(required = false)String svrCustName1,
                       @RequestParam(required = false)String svrTitle1,
                       @RequestParam(required = false)String svrType1,
                       @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Service> ser = serviceService.getList(svrCustName1,svrTitle1,svrType1,pageable);
        model.addAttribute("serPager",ser);
        model.addAttribute("svrCustName",svrCustName1);
        model.addAttribute("svrTitle",svrTitle1);
        model.addAttribute("svrType",svrType1);
        List<Dict> dicts = dictService.getDictByType("服务类型");
        model.addAttribute("di",dicts);
        return "service/dealList";
    }

    @RequestMapping("/service/allot")
    public String allot(Long svrId,Model model){
        Service service = serviceService.getOne(svrId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("date",dateFormat.format(new Date()));
        model.addAttribute("service",service);
        return "service/allot";
    }

    @RequestMapping("/service/feedbackList")
    public String feedback(Model model, @RequestParam(required = false)String svrCustName1,
                       @RequestParam(required = false)String svrTitle1,
                       @RequestParam(required = false)String svrType1,
                       @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Service> ser = serviceService.getList(svrCustName1,svrTitle1,svrType1,pageable);
        model.addAttribute("serPager",ser);
        model.addAttribute("svrCustName",svrCustName1);
        model.addAttribute("svrTitle",svrTitle1);
        model.addAttribute("svrType",svrType1);
        List<Dict> dicts = dictService.getDictByType("服务类型");
        model.addAttribute("di",dicts);
        return "service/feedback";
    }
    @RequestMapping("/service/tickling")
    public String tickling(Long svrId,Model model){
        Service service = serviceService.getOne(svrId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("date",dateFormat.format(new Date()));
        model.addAttribute("service",service);
        return "service/tickling";
    }

    @RequestMapping("/service/ticklingsave")
    public String ticklingsave(Service service){
        if (service.getSvrSatisfy()>=3){
            service.setSvrStatus("已归档");
        }else {
            service.setSvrStatus("已分配");
        }
        Service service1 = serviceService.add(service);
        if (service1!=null){
            return "redirect:/service/feedbackList";
        }else{
            return "service/tickling";
        }
    }

    @RequestMapping("/service/archList")
    public String archList(Model model, @RequestParam(required = false)String svrCustName1,
                           @RequestParam(required = false)String svrTitle1,
                           @RequestParam(required = false)String svrType1,
                           @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Service> ser = serviceService.getListBysvrStatus(svrCustName1,svrTitle1,svrType1,pageable);
        model.addAttribute("serPager",ser);
        model.addAttribute("svrCustName",svrCustName1);
        model.addAttribute("svrTitle",svrTitle1);
        model.addAttribute("svrType",svrType1);
        List<Dict> dicts = dictService.getDictByType("服务类型");
        model.addAttribute("di",dicts);
        return "service/archived";
    }

    @RequestMapping("/service/check")
    public String check(Long svrId,Model model){
        Service service = serviceService.getOne(svrId);
        model.addAttribute("service",service);
        return "service/check";
    }
}
