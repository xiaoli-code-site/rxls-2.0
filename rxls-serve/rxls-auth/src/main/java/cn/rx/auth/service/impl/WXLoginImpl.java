package cn.rx.auth.service.impl;

import cn.rx.common.constant.JwtConstant;
import cn.rx.common.dto.admin.sysLogin.LoginWXDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.enums.RoleEnum;
import cn.rx.common.util.HttpUtil;
import cn.rx.common.util.JwtUtil;
import cn.rx.common.vo.admin.sysUser.SysUserVO;
import cn.rx.common.vo.login.LoginUserVO;
import cn.rx.auth.pojo.WxChat;
import cn.rx.core.exception.BusinessException;
import cn.rx.core.service.RedisService;
import cn.rx.core.service.constant.RedisConstant;
import cn.rx.db.entity.SysRoleUser;
import cn.rx.db.entity.SysUser;
import cn.rx.db.mapper.SysRoleUserMapper;
import cn.rx.db.mapper.SysUserMapper;

import cn.rx.auth.service.LoginService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信登录服务实现类
 *
 * @author 不想说话而已
 */
@Service("wxLoginService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WXLoginImpl implements LoginService<LoginWXDTO, LoginUserVO> {

    @Value("${wechat.appid}")
    private  String APP_ID; //appid

    @Value("${wechat.secret}")
    private  String SECRET; //密钥

    //请求openid路径
    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    private final RedisService redisService;

    private final SysUserMapper sysUserMapper;
    private final SysRoleUserMapper sysRoleUserMapper;
    private final RestTemplate restTemplate;

    public WXLoginImpl(SysRoleUserMapper sysRoleUserMapper, SysUserMapper sysUserMapper, RedisService redisService, RestTemplate restTemplate) {
        this.sysUserMapper = sysUserMapper;
        this.restTemplate = restTemplate;
        this.redisService = redisService;
        this.sysRoleUserMapper = sysRoleUserMapper;
    }

    /**
     * 微信登录接口
     *
     * @param dto 参数对象
     * @return 登录信息
     */
    @Override
    public LoginUserVO handler(LoginWXDTO dto) {
        //获取openid
        String openid = getOpenid(dto.getCode());
        if (openid == null || "".equals(openid)) {
            throw new BusinessException(R.ERROR_AUTHORIZED);
        }

        //返回登录信息的VO
        LoginUserVO loginUserVO = new LoginUserVO();

        //查询用户信息
        SysUserVO userInfo = sysUserMapper.selectByOpenid(openid);

        //查询的用户信息不为null,则进行开始封装登录信息
        if (userInfo != null) {
            BeanUtils.copyProperties(userInfo, loginUserVO.getUser());
        } else {
            //注册，封装登录信息
            SysUserVO enroll = this.enroll(openid);
            BeanUtils.copyProperties(enroll, loginUserVO.getUser());
        }


        //生成token,并存入
        Map<String, Object> info = new HashMap<>();
        info.put(JwtConstant.ROLE, JSON.toJSONString(loginUserVO.getUser().getRoles()));
        long duration = 1000 * 60 * 60 * 24 * 7L;
        String token = JwtUtil.createToken(JwtConstant.WX_ISS, String.valueOf(loginUserVO.getUser().getUserId()), info, 0L, duration);
        loginUserVO.setToken(token);

        //返回登录信息
        return loginUserVO;
    }


    //远程请求微信，获取openid 用户唯一标识
    public String getOpenid(String code) {
        //请求路径
        String url = WX_LOGIN + "?appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        //远程请求微信 ，请求微信授权
        String forObject = restTemplate.getForObject(url, String.class);
        System.err.println(forObject);
        WxChat wxChat = JSON.parseObject(forObject, WxChat.class);
        return Objects.isNull(wxChat) ? null : wxChat.getOpenid();
    }

    /**
     * 注册
     *
     * @param openid 微信唯一标识
     * @return 用户信息
     */
    public SysUserVO enroll(String openid) {
        SysUser sysUser = new SysUser();
        SysUserVO userInfo = new SysUserVO();
        //默认用户名
        sysUser.setNickname("软星会员");
        sysUser.setAvatar("http://dummyimage.com/100x100");
        sysUser.setOpenid(openid);
        sysUser.setState(CommonStateEnum.OK.code);

        sysUserMapper.insert(sysUser);
        if (sysUser.getUserId() == null) {
            throw new BusinessException(R.ERROR_ENROLL);
        }

        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleId(RoleEnum.USER.value);
        sysRoleUser.setUserId(sysUser.getUserId());
        if (sysRoleUserMapper.insert(sysRoleUser) < 1) {
            throw new BusinessException(R.ERROR_ENROLL);
        }

        BeanUtils.copyProperties(sysUser, userInfo);
        return userInfo;
    }


    /**
     * 退出登录
     */
    @Override
    public void logout() {
        Claims userLoginInfo = HttpUtil.getUserLoginInfo();
        Optional.ofNullable(userLoginInfo).orElseThrow(() -> new BusinessException(R.ERROR_LOGIN_NOT));
        String subject = userLoginInfo.getSubject();
        redisService.delete(RedisConstant.LOGIN.concat(subject));
    }


    /**
     * wx登录token恢复用户信息
     *
     * @return 用户登录信息
     */
    public LoginUserVO userInfoByToken() {
        LoginUserVO loginVO = new LoginUserVO();

        //获取用户信息
        Claims userLoginInfo = HttpUtil.getUserLoginInfo();
        Optional.ofNullable(userLoginInfo).orElseThrow(()-> new BusinessException(R.ERROR_LOGIN_NOT));
        String uid = String.valueOf(userLoginInfo.getSubject());
        if (Objects.isNull(uid) || "".equals(uid)) {
            return null;
        }
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserId, Long.valueOf(uid));
        SysUser user = sysUserMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            loginVO.setUser(null);
            return loginVO;
        }

        BeanUtils.copyProperties(user, loginVO.getUser());
        return loginVO;
    }
}

