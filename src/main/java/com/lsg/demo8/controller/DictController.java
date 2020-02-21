package com.lsg.demo8.controller;



import com.lsg.demo8.entity.Dict;
import com.lsg.demo8.service.DictService;
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
import java.util.HashMap;
import java.util.Map;

@Controller
public class DictController {
    @Resource
    private DictService dictService;

    /**
     * 查询字典全部  分页
     * @param model
     * @param dictId
     * @param dictItem
     * @param dictType
     * @param pageIndex
     * @return
     */
    @RequestMapping("/dict/list")
    public String DictList(Model model,@RequestParam(required = false)Long dictId,
                           @RequestParam(required = false)String dictItem,
                           @RequestParam(required = false)String dictType,
                           @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"dictId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Dict> li = dictService.getDictList(dictId,dictItem,dictType,pageable);
        model.addAttribute("dictId",dictId);
        model.addAttribute("dictItem",dictItem);
        model.addAttribute("dictType",dictType);
        model.addAttribute("cusPager",li);
        return "dict/list";
    }

    /***
     * 删除字典中某一个
     * @param dictId
     * @return
     */
    @RequestMapping("/dict/del")
    @ResponseBody
    public Map del(Long dictId){
        dictService.del(dictId);
        Map<String,String> map = new HashMap<>();
        map.put("delResult","true");
        return map;
    }

    /**
     * 新增字典
     * @param dict
     * @return
     */
    @RequestMapping("/dict/save")
    public String save(Dict dict){
            if (dict.getDictType().equals("客户等级")){
                int num = dictService.count(dict.getDictType());
                dict.setDictValue(String.valueOf(num+1));
            }else {
                dict.setDictValue(dict.getDictItem());
            }
            Dict dict1 = dictService.add(dict);
            if (dict1==null){
                return "dict/add";
            }
        return "redirect:/dict/list";
    }

    /**
     * 编辑跳转处理
     * @param dictId
     * @param model
     * @return
     */
    @RequestMapping("/dict/edit")
    public String edit(Long dictId,Model model){
        Dict dict = dictService.getById(dictId);
        model.addAttribute("dict",dict);
        return "dict/edit";
    }

    @RequestMapping("/dict/saveedit")
    public String saveEdit(Dict dict){
        Dict dict1 = dictService.add(dict);
        if (dict1==null){
            return "dict/edit";
        }
        return "redirect:/dict/list";
    }

    /**
     * 新增跳转处理
     * @param dictType
     * @param dictIsEditable
     * @return
     */
    @RequestMapping("/dict/add")
    public String add(String dictType,Long dictIsEditable,Model model){
        model.addAttribute("dictType",dictType);
        model.addAttribute("dictIsEditable",dictIsEditable);
        return "dict/add";
    }
}
