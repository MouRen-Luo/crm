package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.service.RoleService;
import com.lsg.demo8.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
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
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Resource
    private UserService iUserService;

    @Resource
    private RoleService roleService;


    //查询全部
    @RequestMapping(value = "/user/list")
    public String getUserList(Model model, String usrName,
                              @RequestParam(required = false,defaultValue = "0")Long roleId,
                              @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"usrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<User> page = iUserService.getUserList(usrName,roleId,pageable);
        model.addAttribute("userPager",page);
        model.addAttribute("roleId",roleId);
        List<Role> rlist = roleService.getRole();
        model.addAttribute("usrName",usrName);
        model.addAttribute("roles",rlist);
        return "user/list";
    }
    //新增跳转
    @RequestMapping("/user/add")
    public String addlogin(Model model){
        List<Role> list = roleService.getRole();
        model.addAttribute("roles",list);
        return "user/add";
    }

    //新增+修改之后添加进去
    @RequestMapping("/user/save")
    public String addUser(User user){
        Md5Hash num = new Md5Hash(user.getUsrPassword(),"salt",1);
        System.out.println(num);
        user.setUsrPassword(num.toString());
        User users=iUserService.addUser(user);
        if (users!=null){
            return "redirect:/user/list";
        }else{
            return "user/add";
        }
    }
    //删除
    @RequestMapping("/user/del")
    @ResponseBody
    public Map del(Long usrId){
        iUserService.del(usrId);
        Map map = new HashMap();
        map.put("delResult","true");
        return  map;
    }
    //修改跳转
    @RequestMapping("/user/edit")
    public String edit(Long usrId,Model model){
        User user = iUserService.getById(usrId);
        List<Role> list = roleService.getRole();
        model.addAttribute("user",user);
        model.addAttribute("roles",list);
        return "user/edit";
    }

}
