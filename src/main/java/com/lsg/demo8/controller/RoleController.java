package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Right;
import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.RoleRight;
import com.lsg.demo8.service.RightService;
import com.lsg.demo8.service.RoleRightService;
import com.lsg.demo8.service.RoleService;
import org.apache.catalina.connector.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private RightService rightService;
    @Resource
    private RoleRightService roleRightService;

    @RequestMapping("/role/list")
    public String RoleList(Model model, String roleName,
                           @RequestParam(required = false)Long roleId,
                           @RequestParam(required = false,defaultValue = "1")int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"roleId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Role> page = roleService.getRoleList(roleId,roleName,pageable);
        model.addAttribute("rolePager",page);
        model.addAttribute("roleId",roleId);
        model.addAttribute("roleName",roleName);
        return "role/list";
    }

    @RequestMapping("/role/add")
    public String roleadd(Model model){
        List<Right> right = rightService.findAll();
        model.addAttribute("rights",right);
        return "role/add";
    }

    @RequestMapping("/role/save")
    public String roleSave(Role role, HttpServletRequest request){
        String[] num = request.getParameterValues("rightCode");
        Role role1 = roleService.addRole(role);
        roleRightService.del(role.getRoleId());
        if (role1 == null){
            return "role/add";
        }
        for (String code:
             num) {
            RoleRight roleRight = new RoleRight();
            roleRight.setRfRoleId(role.getRoleId());
            roleRight.setRfRightCode(code);
            roleRightService.addRoleRight(roleRight);
        }
        return "redirect:/role/list";
    }

    @RequestMapping("/role/del")
    @ResponseBody
    public Map roledel(Long roleId){
        roleService.del(roleId);
        Map<String,String> map = new HashMap<String,String>();
        map.put("delResult","true");
        return map;
    }

    @RequestMapping("/role/edit")
    public String edit(Long roleId,Model model){
        Role role = roleService.getRoleBYId(roleId);
        List<Right> Rights = rightService.findAll();
        model.addAttribute("role",role);
        model.addAttribute("rights",Rights);
        return "role/edit";
    }
}
