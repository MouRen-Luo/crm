package com.lsg.demo8;

import com.lsg.demo8.entity.Customer;
import com.lsg.demo8.entity.Orders;
import com.lsg.demo8.entity.OrdersLine;
import com.lsg.demo8.repository.CustomerRepository;
import com.lsg.demo8.repository.DictRespository;
import com.lsg.demo8.repository.OrdersRepository;
import com.lsg.demo8.service.CustomerService;
import com.lsg.demo8.service.DictService;
import com.lsg.demo8.service.OrdersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;

@SpringBootTest
public class Demo8ApplicationTests {
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private OrdersService ordersService;
    @Resource
    private CustomerService customerService;
    @Resource
    private DictService dictService;
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private DictRespository dictRespository;

    @Test
    public void testLoginAndLogout() {
        //通过shiro.ini配置文件创建Realm
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        //创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        //将securityManager设置当前的运行环境中
        //SecurityUtils是shiro的一个工具类
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //从SecurityUtils里边创建一个subject
        //通过SecurityUtils获取getsubject得到一个放回值
        Subject subject = SecurityUtils.getSubject();
        //在认证提交前准备token(令牌)
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "1");
        //执行认证提交
        try {
            //subject的返回值里有两个方法logout：登出 login:登入
            //这里我们使用登录方法，通过方法我们可以看到一个返回值：AutothenticationToken的类型
            //又因为AuthenticationToken是一个接口，所以我们使用他下面的UsernamePasswordToken的实现类来写
            subject.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("未知账户异常" + uae);
        } catch (IncorrectCredentialsException ice) {
            System.out.println("密码错误异常" + ice);
        } catch (LockedAccountException lae) {
            System.out.println("账户锁定异常" + lae);
        } catch (ExcessiveAttemptsException eae) {
            System.out.println("过多尝试次数异常" + eae);
        } catch (AuthenticationException ae) {
            System.out.println("其它异常");
        }
        //是否认证通过
        boolean isAuthenticted = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticted);
        //退出操作
        subject.logout();
        //是否认证通过
        isAuthenticted = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticted);
    }

    /**
     * 角色授权，资源授权测试
     */
    @Test
    public void testAthorizationCustomRealm() {
        //创建SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        //将SecurityManager设置到系统运行环境，和spring后将SecurityManager配置spring容器中，一般单例管理
        SecurityUtils.setSecurityManager(securityManager);
        //创建subject
        Subject subject = SecurityUtils.getSubject();
        //创建token令牌
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
        //执行认证
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println("认证状态：" + subject.isAuthenticated());
        /**
         * 认证通过后执行授权
         * 基于角色的授权
         * hasRole传入角色标识
         */
        boolean ishasRole = subject.hasRole("role1");
        System.out.println("单个角色判断：" + ishasRole);
        //hasAllRoles是否拥有多个角色
        boolean hasAllRoles = subject.hasAllRoles(Arrays.asList("role1", "role2"));
        System.out.println("多个角色判断:" + hasAllRoles);
        /**
         * 使用check方法进行授权，如果授权进行不通过会抛出异常
         * subject.checkRole("tole11")  有该角色不会抛出异常
         * subject.checkRole("role11")  没有该角色会抛出异常
         *
         * 基于资源的授权
         * isPermitted传入权限标识符
         */
        boolean isPermitted = subject.isPermitted("user:LO5");
        System.out.println("单个权限判断：" + isPermitted);

        boolean isPermittedAll = subject.isPermittedAll("user:LO5", "user:LO6");
        System.out.println("多个权限判断：" + isPermittedAll);
        /**
         * 使用check方法进行授权，如果授权进行不通过会抛出异常
         * subject.checkPermission("user:L05")  有该权限不会抛出异常
         * subject.checkPermission("user:L05")  没有该权限会抛出异常
         */
    }

    @Test
    public void getMd5HashToString() {
        /**
         *  soruce：加密字符串
         *  salt：盐
         *  hashIterations：加密次数
         */
        Md5Hash md5Hash = new Md5Hash("123", "salt", 1);
        System.out.println(md5Hash.toString());

    }

    @Test
    public void Test2() {
        List<Customer> list = customerService.findAll();
        List<Object> ok = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();
        List<Object> list1 = new ArrayList<>();
        for (Customer li :
                list) {
            list1.add(li.getCustName());
        }
        for (Customer li :
                list) {
            Map<Object, Object> ko = new HashMap<>();
            try {
                Orders orders = ordersService.getOrders(li.getCustNo());
                if (orders != null) {
                    OrdersLine ordersLine = ordersService.getOne(orders.getOdrId());
                    ko.put("name", li.getCustName());
                    ko.put("value", ordersLine.getOddPrice());
                } else {
                    ko.put("name", li.getCustName());
                    ko.put("value", 0);
                }
                ok.add(ko);
            } catch (Exception e) {

            }
        }
        for (Object li :
                ok) {
            map.put("value", ok);
            map.put("name", list1);
        }
        System.out.println(map);
    }



}
