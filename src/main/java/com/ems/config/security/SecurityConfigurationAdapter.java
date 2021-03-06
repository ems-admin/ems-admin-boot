package com.ems.config.security;

import com.ems.common.constant.SecurityConstants;
import com.ems.common.utils.RedisUtil;
import com.ems.config.filter.JwtAuthorizationFilter;
import com.ems.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:40
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    
    private final SecurityProblemSupport securityProblemSupport;

    private final SysMenuService menuService;

    private final RedisUtil redisUtil;

    /**
    * @Description: ??????SpringSecurity?????????????????????
    * @Param: []
    * @return: org.springframework.security.crypto.password.PasswordEncoder
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
    * @Description: ??????HttpSecurity???????????????
    * @Param: [httpSecurity]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */

    /**
    * @Description: ???????????????SpringSecurity?????????,????????????SpringSecurity?????????????????????JwtAuthorizationFilter????????????
    * @Param: [webSecurity]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring()
                .antMatchers(HttpMethod.POST,"/auth/login");
    }
    
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                // ???????????????????????????????????? 401 ??????
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                // ????????????????????????????????????????????? 403 ??????
                .accessDeniedHandler(securityProblemSupport)
                .and()
                // ?????? CSRF
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // ????????????????????????????????????
                // ?????????????????????
                .anyRequest().authenticated()
                .and()
                // ????????? session?????????????????????
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(securityConfigurationAdapter());
    }

    /**
    * @Description: ???HttpSecurity????????????????????????JWT
    * @Param: []
    * @return: com.ems.config.security.JwtConfigurerAdapter
    * @Author: starao
    * @Date: 2021/11/27
    */
    private JwtConfigurerAdapter securityConfigurationAdapter() throws Exception {
        return new JwtConfigurerAdapter(new JwtAuthorizationFilter(authenticationManager(), menuService, redisUtil));
    }

}
