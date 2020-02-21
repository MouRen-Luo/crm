package com.lsg.demo8.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.lsg.demo8.entity.Right;
import com.lsg.demo8.service.UserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private UserService userService;

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
   /* @Value("${spring.redis.password}")
    private String password;*/
    @Value("${spring.redis.timeout}")
    private int timeout;
    /**
     * 创建ShiroFilterFactoryBean
     */

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shiroFilter():配置权限控制规则");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //如果不设置默认会自动寻找Web工程目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/mian");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //添加shiro内置过滤器，实现权限相关的url拦截
        /**
         * 常见过滤器：
         * anon：无需认证（登录）可以访问
         * authc：必须认证才可以访问
         * user：如果使用Remember Me的功能，可以直接访问
         * perms：该资源必须得到资源权限才可以访问
         * role：该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterChainDeFinitionMap = new LinkedHashMap<String,String>();
        //配置不会被拦截的链接，顺序判断
        filterChainDeFinitionMap.put("/css/**","anon");
        filterChainDeFinitionMap.put("/fonts/**","anon");
        filterChainDeFinitionMap.put("/images/**","anon");
        filterChainDeFinitionMap.put("/js/**","anon");
        filterChainDeFinitionMap.put("/localcss/**","anon");
        filterChainDeFinitionMap.put("/dologin","anon");

        //配置退出 过滤器，其中的具体的退出代码shiro已经替我们实现了
        filterChainDeFinitionMap.put("/logout","logout");

        //配置测试权限（真实权限应该从数据库中获得）
        /*filterChainDeFinitionMap.put("/user/list","perms[用户管理]");
        filterChainDeFinitionMap.put("/user/add","perms[用户添加]");
        filterChainDeFinitionMap.put("/role/list","perms[角色管理]");*/

        //从数据库中动态获得所有权限控制（控制URL访问）
        List<Right> rights = userService.findAllRights();
        for (Right right:rights){
            if (!right.getRightType().equals("Folder") && !right.getRightType().equals("Button")){
                System.out.println(right.getRightText()+":"+right.getRightUrl());
                filterChainDeFinitionMap.put(right.getRightUrl(),"perms["+right.getRightText()+"]");
            }
        }
        /**
         * 过滤链定义，从上向下顺序执行，一般将/**放在最为下边  这是一个坑，一不小心代码就不好使了
         *
         * authc：所有url都必须认证通过才可以访问；anon：所有url都可以匿名访问
         */
        filterChainDeFinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDeFinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        myShiroRealm.setCachingEnabled(true);
        myShiroRealm.setAuthenticationCachingEnabled(true);
        myShiroRealm.setAuthenticationCacheName("authorizationCache");
        return myShiroRealm;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //自定义缓存实现，使用redis
        securityManager.setCacheManager(cacheManager());
        //自定义session管理，使用redis
        securityManager.setSessionManager(sessionManager());
        //设置realm
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     * @return
     */
    private SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
    /**
     * redisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     * @return
     */
    private SessionDAO redisSessionDAO() {
        RedisSessionDAO  redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * cacheManager缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    private CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //缓存名称
        redisCacheManager.setPrincipalIdFieldName("usrName");
        //缓存时间
        redisCacheManager.setExpire(1800);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        /*redisManager.setPassword(password);*/
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * 配置thymeleaf页面对shiro标签的支持
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


    /**
     * 加密
     */
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        System.out.println("加密hashedCredentialsMatcher----");
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //使用MD5算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数：意为加密几次
        hashedCredentialsMatcher.setHashIterations(1);
        return  hashedCredentialsMatcher;
    }
}
