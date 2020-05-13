package com.sunrise.stoneage.config.shiro;

import com.sunrise.stoneage.mbg.model.PermissionDO;
import com.sunrise.stoneage.mbg.model.RoleDO;
import com.sunrise.stoneage.mbg.model.UserDO;
import com.sunrise.stoneage.service.IPermissionService;
import com.sunrise.stoneage.service.IRoleService;
import com.sunrise.stoneage.service.IUserService;
import com.sunrise.stoneage.utils.ShiroUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Shiro 从 Realm 获取安全数据（如用户、角色、权限），就是说 SecurityManager 要验证用户身份，那么它需要从 Realm 获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从 Realm 得到用户相应的角色/权限进行验证用户是否能进行操作；可以把 Realm 看成 DataSource。
 *
 * 一般继承 AuthorizingRealm （授权）即可，其继承了 AuthenticatingRealm（即身份验证），而 AuthenticatingRealm 继承了CachingRealm （带有缓存实现）。
 * doGetAuthorizationInfo()方法仅仅是实现从数据源里获取用户的角色和权限信息，至于如何进行权限验证则交由Shiro的AuthorizingRealm类来完成。
 * doGetAuthenticationInfo()方法仅仅是实现从数据源里获取用户的身份信息（通常是账号、密码），至于如何进行身份验证则交由Shiro的AuthenticatingRealm类来完成。
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;


    public ShiroRealm() {
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        LOGGER.debug("Token({}) 的类型是：{}" ,token.getPrincipal(), token.getClass().getName());
        return super.supports(token);
    }
    /**
     * 授权
     * 用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中。
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        UserDO user = (UserDO) principalCollection.getPrimaryPrincipal();
        //获取用户ID
        Long userId =user.getId();
        //这里可以进行授权和处理
        Set<String> rolesSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        //查询角色和权限(这里根据业务自行查询)
        List<RoleDO> roleList = roleService.getRolesByUserId(userId);
        for (RoleDO role : roleList) {
            rolesSet.add(role.getCode());
            List<PermissionDO> permissionList = permissionService.getPermissionsByRoleId(role.getId());
            for (PermissionDO permission : permissionList) {
                permsSet.add(permission.getCode());
            }
        }

        //将查到的权限和角色分别传入authorizationInfo中
        authorizationInfo.setStringPermissions(permsSet);
        authorizationInfo.setRoles(rolesSet);
        return authorizationInfo;
    }

    /**
     * 身份认证
     * 在shiro中，用户需要提供 principals（身份）和 credentials（证明/凭证）给shiro，以验证用户身份。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        // 通过username从数据库中查找 User对象，如果找到进行验证
        // 实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
        UserDO user = userService.getUserByUsername(username);
        // 判断账号是否存在
        if (user == null) {
            throw new UnknownAccountException();
        }
        // 判断账号是否被冻结
        if (user.getIsEnable() == null || !user.getIsEnable()){
            throw new DisabledAccountException();
        }
        // 交给 AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配，如果觉得人家的不好可以自定义实现。
        // CredentialsMatcher 会使用 token 的密码进行盐值加密，然后和数据库保存的密码进行匹配。
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,                                  //用户名
                user.getPassword(),                    //密码
                ByteSource.Util.bytes(user.getSalt()), //设置盐值
                getName()
        );
        // 验证成功开始踢人(清除缓存和Session)
        ShiroUtil.deleteCache(username,true);
        return authenticationInfo;
    }
}
