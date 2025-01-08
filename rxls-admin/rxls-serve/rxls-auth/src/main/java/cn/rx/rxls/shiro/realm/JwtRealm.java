package cn.rx.rxls.shiro.realm;

import cn.rx.common.constant.JwtConstant;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.util.JwtUtil;
import cn.rx.common.util.StrUtil;
import cn.rx.common.vo.sysUser.SysUserVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.core.service.RedisService;
import cn.rx.core.service.constant.RedisConstant;
import cn.rx.db.entity.SysPermissions;
import cn.rx.db.entity.SysRole;
import cn.rx.db.mapper.SysPermissionsMapper;
import cn.rx.db.mapper.SysRoleMapper;
import cn.rx.db.mapper.SysUserMapper;
import cn.rx.rxls.shiro.JwtToken;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Resource
    private SysPermissionsMapper sysPermissionsMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private RedisService redisService;


    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //构造权限数据
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Claims claims = JwtUtil.parseToken(String.valueOf(principals));
        Optional.ofNullable(claims).orElseThrow(() -> new BusinessException(R.ERROR_LOGIN_NOT));
        String subject = claims.getSubject();
        SysUserVO userInfo = sysUserMapper.selectUserById(Long.valueOf(subject));
        Optional.ofNullable(userInfo).orElseThrow(() -> new BusinessException(R.ERROR_ATTESTATION_NO));
        if (!CommonStateEnum.OK.code.equals(userInfo.getState())) {
            throw new BusinessException(R.ERROR_INVALID_ACCOUNTS);
        }
        Optional.ofNullable(StrUtil.validateValue(subject)).orElseThrow(() -> new BusinessException(R.ERROR_LOGIN_NOT));


        //从缓存中查找
        Map<String, Object> loginCacheData = getLoginCacheData(subject);
        if (Objects.nonNull(loginCacheData)) {
            try {
                Object permission = loginCacheData.get(JwtConstant.PERMISSIONS);
                Object role = loginCacheData.get(JwtConstant.ROLE);
                if (role instanceof String) {
                    info.setRoles(Arrays.stream(((String) role).split(",")).collect(Collectors.toSet()));
                }
                if (permission instanceof List) {
                    List<String> list =  (List) permission;
                    info.setStringPermissions(new HashSet<>(list));
                }
                log.info("缓存获取");
                return info;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //查询过滤出可用的角色
        List<SysRole> sysRoles = sysRoleMapper.selectByUserId(Long.valueOf(subject));
        List<SysRole> sysRoleList = sysRoles.stream().filter(r -> CommonStateEnum.OK.code.equals(r.getState())).collect(Collectors.toList());
        List<String> roleNames = sysRoleList.stream().map(SysRole::getRoleName).collect(Collectors.toList());
        info.setRoles(new HashSet<>(roleNames));
        if (roleNames.size() > 0) {
            //查询权限
            Set<SysPermissions> permissions = sysPermissionsMapper.selectByRoleName(roleNames);
            info.setStringPermissions(permissions.stream().map(SysPermissions::getUrl).collect(Collectors.toSet()));
        }
        log.info("数据库查找");
        return info;
    }

    //认证
    //这个token就是从Jwt过滤器中传入的jwtToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwt = (String) token.getPrincipal();
        Optional.ofNullable(jwt).orElseThrow(() -> new BusinessException(R.ERROR_LOGIN_NOT));
        Optional.ofNullable(JwtUtil.parseToken(jwt)).orElseThrow(UnknownAccountException::new);
        Map<String, Object> stringObjectMap = JwtUtil.parseToken(jwt);
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
        return new SimpleAuthenticationInfo(jwt, jwt, "JwtRealm");

    }

    public Map<String, Object> getLoginCacheData(String subject) {
        String value = redisService.getValue(RedisConstant.LOGIN, subject);
        return JSON.parseObject(value, Map.class);
    }

}