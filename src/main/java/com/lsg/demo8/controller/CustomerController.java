package com.lsg.demo8.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.lsg.demo8.entity.*;
import com.lsg.demo8.repository.CustomerRepository;
import com.lsg.demo8.repository.DictRespository;
import com.lsg.demo8.service.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {
    @Resource
    private CustomerService customerService;
    @Resource
    private DictService dictService;
    @Resource
    private LinkmanService linkmanService;
    @Resource
    private ActivityService activityService;
    @Resource
    private OrdersService ordersService;
    @Resource
    private DictRespository dictRespository;
    @Resource
    private CustomerRepository customerRepository;

    @RequestMapping("/customer/list")
    public String customer(Model model,@RequestParam(required = false)String custName,
                           @RequestParam(required = false)String custNo,
                           @RequestParam(required = false)String custManagerName,
                           @RequestParam(required = false)String dictItem,
                           @RequestParam(required = false)String dictItem2,
                           @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"custId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Customer> page = customerService.getUserList(custName,custNo,custManagerName,dictItem,dictItem2,pageable);
        model.addAttribute("cusPager",page);
        model.addAttribute("custName",custName);
        model.addAttribute("custNo",custNo);
        model.addAttribute("custManagerName",custManagerName);
        model.addAttribute("dictItem",dictItem);
        model.addAttribute("dictItem2",dictItem2);
        List<Dict> dict1=dictService.getDictByType("客户等级");
        model.addAttribute("dicts",dict1);
        List<Dict> dict2 =dictService.getDictByType("地区");
        model.addAttribute("dictb",dict2);
        return "customer/list";
    }

    @RequestMapping("/customer/edit")
    public String edit(Long custId,Model model){
        Customer customer = customerService.getone(custId);
        model.addAttribute("cus",customer);
        List<Dict> dict1=dictService.getDictByType("客户等级");
        model.addAttribute("dicts",dict1);
        List<Dict> dict2 =dictService.getDictByType("地区");
        model.addAttribute("dictb",dict2);
        return "customer/edit";
    }

    @RequestMapping("/customer/save")
    public String addUser(Customer customer){
        Customer customer1 = customerService.add(customer);
        if (customer1!=null){
            return "redirect:/customer/list";
        }else{
            return "customer/edit";
        }
    }

    @RequestMapping("/tel/add")
    public String add(String lkmCustNo,String lkmCustName ,Model model){
        model.addAttribute("lkmCustNo",lkmCustNo);
        model.addAttribute("lkmCustName",lkmCustName);
        return "linkman/add";
    }

    @RequestMapping("/customer/del")
    @ResponseBody
    @Transactional
    public Map del(Long custId){
        Customer customer = customerService.getone(custId);
        Orders orders = ordersService.getOrders(customer.getCustNo());
        OrdersLine ordersLine = ordersService.getOne(orders.getOdrId());
        Product product = ordersService.getProductList(ordersLine.getOddProdId());
        ordersService.delOdd(ordersLine.getOddId());
        ordersService.delPro(product.getProdId());
        ordersService.delord(customer.getCustNo());
        activityService.delActivity(customer.getCustNo());
        linkmanService.delLinkman(customer.getCustNo());
        customerService.delLost(customer.getCustNo());
        customerService.delService(customer.getCustNo());
        customerService.del(customer.getCustNo());
        Map<String,String> map = new HashMap<>();
        map.put("delResult","true");
        return  map;
    }

    @RequestMapping("/customer/statistics")
    public String li(Model model,@RequestParam(required = false)String custName,
                     @RequestParam(required = false)String custNo,
                     @RequestParam(required = false)String custManagerName,
                     @RequestParam(required = false)String dictItem,
                     @RequestParam(required = false)String dictItem2,
                     @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"custId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Customer> page = customerService.getUserList(custName,custNo,custManagerName,dictItem,dictItem2,pageable);
        model.addAttribute("cusPager",page);
        model.addAttribute("custName",custName);
        return "customer/statistics";
    }

    @RequestMapping("/echart")
    @ResponseBody
    public Map echart(){
        List<Customer> list = customerService.findAll();
        List<Object> ok = new ArrayList<>();
        Map<Object,Object> map = new HashMap<>();
        List<Object> list1=new ArrayList<>();
        for (Customer li:
                list) {
            list1.add(li.getCustName());
        }
        for (Customer li:
                list) {
                Map<Object,Object> ko=new HashMap<>();
            try {
                ko.put("name",li.getCustName());
                Orders orders = ordersService.getOrders(li.getCustNo());
                if (orders!=null){
                    int num = 0;
                    List<OrdersLine> ordersLine=ordersService.findAllLin(orders.getOdrId());
                    for (OrdersLine kl:
                         ordersLine) {
                        num+=kl.getOddPrice();
                    }
                    ko.put("value",num);
                }else {
                    ko.put("value",0);
                }
            }catch (Exception e){
            }
            ok.add(ko);
        }
            map.put("value",ok);
            map.put("name",list1);
        System.out.println(map);
        return map;
    }

    /**
     * 生成Excel表格
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/downExcel")
    public String downExcel(HttpServletRequest request, HttpServletResponse response,int index) throws Exception {
        XSSFWorkbook wb = null;
        String fileName = null;
        if (index == 1) {
             wb = customerService.show();
             fileName = "客户报表.xlsx";
        }else if (index == 2){
            wb = dictService.show();
            fileName = "客户等级数量报表.xlsx";
        }else if (index==3){
            wb = dictService.show2();
            fileName = "服务类型数量报表.xlsx";
        }
        OutputStream outputStream = null;
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成pdf
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/helloPdf")
    public String Pdf(HttpServletRequest request, HttpServletResponse response,int index) throws Exception {
        XSSFWorkbook wb = null;
        String fileName = null;
        List<Customer> list = null;
        List<Dict> di =null;
        if (index == 1) {
            list = customerRepository.findAll();
            System.out.println();
            fileName = "客户报表.pdf";
        }else if (index == 2){
            di = dictRespository.kl();
            fileName = "客户等级数量报表.pdf";
        }else if (index==3){
            di = dictRespository.ol();
            fileName = "服务类型数量报表.pdf";
        }
        try {
            //1 创建Document
            Document document = new Document();
            //2 获取PdfWriter
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Administrator\\Desktop\\"+fileName));
            //3 打开
            document.open();
            //4 添加内容
            BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont);
            if (di!=null){
                for (Dict d : di){
                    document.add(new Paragraph("编号："+d.getDictId()+"----类型："+d.getDictItem()+"----数量："+d.getDictIsEditable(),font));
                }
            }else if (list!=null){
                for (Customer cst : list){
                    document.add(new Paragraph("客户编号"+cst.getCustNo()+"----客户名称："+cst.getCustName()+"----客户等级："+cst.getCustLevelLabel()+"----客户地址:"+cst.getCustAddr(),font));
                }
            }
            //5 关闭
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *客户等级
     * @param model
     * @param pageIndex
     * @return
     */
    @RequestMapping("/customer/constitute")
    public String constitute(Model model,@RequestParam(required = false,defaultValue = "客户等级")String dictType,
                             @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"dictId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Dict> list = dictService.list(dictType,pageable);
        model.addAttribute("conPager",list);
        return "customer/constitute";
    }

    /**
     * 客户等级数量统计
     * @return
     */
    @RequestMapping("/consti")
    @ResponseBody
    public Map consti(){
        List<Dict> list = dictRespository.kl();
        List<Object> name = new ArrayList<>();
        List<Object> value = new ArrayList<>();
        for (Dict li:
             list) {
            name.add(li.getDictItem());
            if (li.getDictIsEditable()==null){
                value.add(0);
            }else{
                value.add(li.getDictIsEditable());
            }
        }
        Map<Object,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("value",value);
        return map;
    }


    /**
     * 跳转客户服务
     * @param model
     * @param pageIndex
     * @return
     */
    @RequestMapping("/customer/service")
    public String service(Model model,@RequestParam(required = false,defaultValue = "服务类型")String dictType,
                     @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"dictId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Dict> list = dictService.list(dictType,pageable);
        model.addAttribute("cusPager",list);
        return "customer/service";
    }


    /**
     * 客户服务统计图
     * @return
     */
    @RequestMapping("/service")
    @ResponseBody
    public Map service(){
        List<Dict> list = dictRespository.ol();
        List<Object> name = new ArrayList<>();
        List<Object> value = new ArrayList<>();
        for (Dict li:
                list) {
            name.add(li.getDictItem());
            if (li.getDictIsEditable()==null){
                value.add(0);
            }else{
                value.add(li.getDictIsEditable());
            }
        }
        Map<Object,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("value",value);
        return map;
    }

}
