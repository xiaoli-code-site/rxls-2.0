//package cn.rx.auth.security;
//
//import cn.rx.auth.security.filter.JwtAuthenticationFilter;
//import cn.rx.db.mapper.SysPermissionsMapper;
//import cn.rx.db.mapper.SysRoleMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.annotation.Resource;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Resource
//    private SysPermissionsMapper permissionMapper;
//    @Resource
//    private SysRoleMapper roleMapper;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .antMatchers("/login/**").permitAll()
//                .antMatchers("/logout/**").permitAll()
//                .antMatchers("/upload/**").permitAll()
//                .antMatchers("/sys/subscribe").permitAll()
//                .antMatchers("/user/register").permitAll()
//                .antMatchers("/code/transit/**").permitAll()
//                .antMatchers("/encrypt/rsa").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .formLogin(AbstractHttpConfigurer::disable);
//
//
//    }
//
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter(roleMapper, permissionMapper);
//    }
//}