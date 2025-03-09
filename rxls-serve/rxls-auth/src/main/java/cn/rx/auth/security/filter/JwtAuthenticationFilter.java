//package cn.rx.auth.security.filter;
//
//import cn.rx.common.constant.JwtConstant;
//import cn.rx.common.enums.R;
//import cn.rx.common.util.JwtUtil;
//import cn.rx.core.exception.BusinessException;
//import cn.rx.db.entity.SysPermissions;
//import cn.rx.db.entity.SysRole;
//import cn.rx.db.mapper.SysPermissionsMapper;
//import cn.rx.db.mapper.SysRoleMapper;
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * 自定义Security认证过滤器
// */
//@Slf4j
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final SysPermissionsMapper permissionMapper;
//
//    private final SysRoleMapper roleMapper;
//
//    public JwtAuthenticationFilter(SysRoleMapper roleMapper, SysPermissionsMapper permissionMapper){
//        this.roleMapper = roleMapper;
//        this.permissionMapper = permissionMapper;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        String token = extractToken(request);
//        log.debug("Received token: {}", token);
//
//        if (token != null) {
//            try {
//                Claims claims = JwtUtil.parseToken(token);
//                if(Objects.isNull(claims) || Objects.isNull(claims.getSubject()) || "".equals(claims.getSubject()) ){
//                    throw new BusinessException(R.ERROR_LOGIN_NOT);
//                }
//                //查询用户角色信息
//                List<SysRole> roles = roleMapper.selectByUserId(Long.valueOf(claims.getSubject()));
//                List<Integer> roleId = roles.stream().map(s -> s.getRoleId()).collect(Collectors.toList());
//                Set<SysPermissions> permissions = permissionMapper.selectByRoleIdList(roleId);
//
//
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                permissions.forEach(s->{
//                    authorities.add(new SimpleGrantedAuthority(s.getUrl()));
//                });
//
//
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                        Integer.valueOf(claims.getSubject()),
//                    null,
//                    authorities
//                );
//
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            } catch (Exception e) {
//                logger.error("Token validation failed", e);
//                SecurityContextHolder.clearContext();
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//    private String extractToken(HttpServletRequest request) {
//        String token = request.getHeader(JwtConstant.TOKEN);
//        if (token != null && !"".equals(token) ) {
//            return token;
//        }
//        return null;
//    }
//}