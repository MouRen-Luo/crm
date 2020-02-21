package com.lsg.demo8.config;

import com.lsg.demo8.entity.Right;
import com.lsg.demo8.entity.Role;
import com.lsg.demo8.entity.User;
import com.lsg.demo8.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService iUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //用户登录次数计数 rediskey
    private String SHIRO_LOGIN_COUNT="shiro_login_count";
    //用户登录是否被锁定  一小时 redisKey前缀
    private String SHIRO_IS_LOCK="shiro_is_lock";

    /*授权操作*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限认证-->MyShiroRealm.doGetAuthenticationInfo");
        //授权信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //设置权限，如果用户没有相应的权限
        //会访问ShiroConfig过滤器中ShiroFilter方法配置的未授权路径
        //将来这里应该是通过数据库动态授权
       /* authorizationInfo.addStringPermission("用户管理");
        authorizationInfo.addStringPermission("指派角色");*/
       User user = (User) principalCollection.getPrimaryPrincipal();
        for (Right right:
             user.getRole().getRights()) {
            System.out.println("用户授权的权限："+right.getRightText());
            authorizationInfo.addStringPermission(right.getRightText());
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("身份认证：MyShiroRealm.doGetAuthenticationInfo");
        //获取用户输入的账号
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String usrName = token.getUsername();
        String usrPassword = new String(token.getPassword());
        System.out.println("usrName:"+usrName);
        System.out.println("usrPassword:"+usrPassword);

        //访问一次，计数一次
        ValueOperations<String,String> opsForValue=stringRedisTemplate.opsForValue();
        opsForValue.increment(SHIRO_LOGIN_COUNT+usrName,1);
        //计数大于5时，设置用户被锁定1小时
        if (Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+usrName))>5){
            opsForValue.set(SHIRO_IS_LOCK+usrName,"LOCK");
            stringRedisTemplate.expire(SHIRO_IS_LOCK+usrName,1, TimeUnit.HOURS);
        }
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+usrName))){
            throw new DisabledAccountException("由于输入错误次数大于5次，账号1小时内已经禁止登录！");
        }
        //通过username从数据库中查找User对象。如果找到，没找到
        //实际项目中，这里可以根据实际情况做缓存，如果不做，shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = iUserService.getUser(usrName);
        System.out.println("--->>user"+user);
        if (user==null){
            throw new UnknownAccountException("账号不存在！");
        }/*else if (!user.getUsrPassword().equals(usrPassword)){
            throw new IncorrectCredentialsException("密码不正确！");
        }*/
        //认证成功，给用户添加权限
        Role role = iUserService.getRoleByUser(user);
        List<Right> rights = iUserService.findRightsByRole(role);
        role.getRights().addAll(rights);
        user.setRole(role);

        //认证信息
        SimpleAuthenticationInfo authenticationInfo =new SimpleAuthenticationInfo(
                user,//用户
                user.getUsrPassword(),//密码
                new DefaultByteSource("salt"),
                getName()//realm name
        );
        opsForValue.set(SHIRO_LOGIN_COUNT+usrName,"0");
        return authenticationInfo;
    }
}
