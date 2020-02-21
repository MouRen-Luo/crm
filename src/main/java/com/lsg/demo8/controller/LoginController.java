package com.lsg.demo8.controller;

import com.lsg.demo8.entity.Right;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.service.RightService;
import com.lsg.demo8.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Resource
    private UserService iUserService;
    @Resource
    private RightService rightService;

    @RequestMapping("/dologin")
    public String dologin(String usrName, String usrPassword, HttpServletRequest request,
                          Map<String,Object> map, HttpSession session){
        User user = null;
        try {
            AuthenticationToken token = new UsernamePasswordToken(usrName,usrPassword);
            SecurityUtils.getSubject().login(token);
            user = (User) SecurityUtils.getSubject().getPrincipal();
            request.getSession().setAttribute("user",user);
            List<Right> list = rightService.findAll();
            request.getSession().setAttribute("ri",list);
        }catch (IncorrectCredentialsException i){
            i.printStackTrace();
            map.put("msg","密码错误"+i.getMessage());
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg",e.getMessage());
            return "login";
        }
        return "main";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        SecurityUtils.getSubject().logout();
        return "login";
    }


}
