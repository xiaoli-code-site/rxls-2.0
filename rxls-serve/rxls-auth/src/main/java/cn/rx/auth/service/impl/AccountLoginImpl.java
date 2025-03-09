package cn.rx.auth.service.impl;

import cn.rx.common.constant.JwtConstant;
import cn.rx.common.dto.admin.sysLogin.SysLoginAccountDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.util.*;

import cn.rx.common.vo.admin.sysMenu.SysMenuVO;
import cn.rx.common.vo.admin.sysRole.SysRoleVO;
import cn.rx.common.vo.admin.sysUser.SysUserVO;
import cn.rx.common.vo.login.SysLoginAdminVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.core.service.RedisService;
import cn.rx.core.service.constant.RedisConstant;
import cn.rx.db.entity.SysAdmin;
import cn.rx.db.entity.SysPermissions;
import cn.rx.db.mapper.*;
import cn.rx.auth.service.LoginService;
import cn.rx.system.service.SysOplogService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 账号密码登录服务实现类
 *
 * @author 不想说话而已
 */
@Service
public class AccountLoginImpl implements LoginService<SysLoginAccountDTO, SysLoginAdminVO> {


    private final SysAdminMapper sysAdminMapper;
    private final RedisService redisService;
    private final SysMenuMapper sysMenuMapper;
    private final SysPermissionsMapper sysPermissionsMapper;
    private final SysUserMapper sysUserMapper;
    private final SysOplogService sysOplogService;

    AccountLoginImpl(SysAdminMapper sysAdminMapper, RedisService redisService, SysOplogService sysOplogService, SysMenuMapper sysMenuMapper, SysUserMapper sysUserMapper, SysPermissionsMapper sysPermissionsMapper) {
        this.sysAdminMapper = sysAdminMapper;
        this.sysMenuMapper = sysMenuMapper;
        this.sysPermissionsMapper = sysPermissionsMapper;
        this.sysUserMapper = sysUserMapper;
        this.sysOplogService = sysOplogService;
        this.redisService = redisService;
    }

    /**
     * 管理系统登录
     *
     * @param dto 参数对象
     * @return 登录信息
     */
    @Override
    public SysLoginAdminVO handler(SysLoginAccountDTO dto) {

        //查询账号信息
        LambdaQueryWrapper<SysAdmin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysAdmin::getUsername, dto.getUsername());
        SysAdmin sysAdmin = sysAdminMapper.selectOne(wrapper);
        // 用户是否存在
        Optional.ofNullable(sysAdmin).orElseThrow(() -> new BusinessException(R.ERROR_USER));

        // 验证码校验
        Boolean verify = CaptchaUtil.verify(dto.getVerifyCodeKey(), dto.getVerifyCode(), CaptchaUtil.CACHE);
        if (!verify) {
            sysOplogService.login(sysAdmin.getUserId(),CommonStateEnum.ERROR,"登录",R.ERROR_CACHE.msg);
            throw new BusinessException(R.ERROR_CACHE);
        }

        //  密码校对
        Boolean bool = BcryptUtil.proofread(dto.getPassword(), sysAdmin.getPassword());
        if (!bool) {
            sysOplogService.login(sysAdmin.getUserId(),CommonStateEnum.ERROR,"登录",R.ERROR_PASSWORD.msg);
            throw new BusinessException(R.ERROR_PASSWORD);
        }

        //查询用户信息
        SysUserVO user = sysUserMapper.selectUserById(sysAdmin.getUserId());
        // 用户是否存在
        Optional.ofNullable(user).orElseThrow(() -> new BusinessException(R.ERROR_USER));
        if (!CommonStateEnum.OK.code.equals(user.getState())) {
            sysOplogService.login(sysAdmin.getUserId(),CommonStateEnum.ERROR,"登录",R.ERROR_INVALID_ACCOUNTS.msg);
            throw new BusinessException(R.ERROR_INVALID_ACCOUNTS);
        }


        //获取用户拥有的角色id
        List<Integer> roleIds = user.getRoles().stream().map(SysRoleVO::getRoleId).collect(Collectors.toList());
        //根据角色查询菜单
        List<SysMenuVO> menuList = sysMenuMapper.selectByRoleIds(roleIds).stream().sorted(Comparator.comparingInt(SysMenuVO::getSort)).collect(Collectors.toList());
        //根据角色查询权限
        Set<SysPermissions> permissions = sysPermissionsMapper.selectByRoleIdList(roleIds);
        // 令牌生成
        Map<String, Object> info = new HashMap<>();
        info.put(JwtConstant.ROLE, JSON.toJSONString(roleIds));
        info.put(JwtConstant.USERNAME, dto.getUsername());
        long duration = 1000 * 60 * 60 * 24 * 360L; //7天有效期
        String token = JwtUtil.createToken(JwtConstant.ADMIN_ISS, String.valueOf(sysAdmin.getAdminId()), info, 0L, duration);

        // 缓存用户信息到redis
        Map<String, Object> cacheInfo = new HashMap<>();
        cacheInfo.put(JwtConstant.ROLE, JSON.toJSONString(roleIds));
        List<String> collect = permissions.stream().map(SysPermissions::getUrl).collect(Collectors.toList());
        cacheInfo.put(JwtConstant.PERMISSIONS,collect);
        redisService.setValue(RedisConstant.LOGIN, user.getUserId(), cacheInfo, duration, TimeUnit.DAYS);
        sysOplogService.login(user.getUserId(),CommonStateEnum.OK,"登录",CommonStateEnum.OK.msg);
        return new SysLoginAdminVO(user, sysAdmin.getUsername(), token, menuList);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        Claims userLoginInfo = HttpUtil.getUserLoginInfo();
        Optional.ofNullable(userLoginInfo).orElseThrow(()->new BusinessException(R.ERROR_LOGIN_NOT));
        String subject = userLoginInfo.getSubject();
        redisService.delete(RedisConstant.LOGIN.concat(subject));
        sysOplogService.login(Long.valueOf(subject),CommonStateEnum.OK,"退出登录",CommonStateEnum.OK.msg);

    }



}


