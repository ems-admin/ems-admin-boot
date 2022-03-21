package com.ems.config.filter;

import com.ems.common.constant.CommonConstants;
import com.ems.common.constant.SecurityConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.JwtUtil;
import com.ems.common.utils.RedisUtil;
import com.ems.common.utils.SecurityUtil;
import com.ems.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:15
 **/
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final SysMenuService menuService;

    private final RedisUtil redisUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SysMenuService menuService, RedisUtil redisUtil) {
        super(authenticationManager);
        this.menuService = menuService;
        this.redisUtil = redisUtil;
    }

    /**
    * @Description: 过滤用户请求
    * @Param: [request, response, filterChain]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            //  从request中获取token
            String token = this.getTokenFromHttpServletRequest(request);
            //  校验token是否有效
            if (JwtUtil.verifyToken(token)){
                //  获取认证信息
                Authentication authentication = JwtUtil.getAuthentication(token);
                //  将认证信息保存在spring安全上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //  校验访问权限（即使用token也需要对应的权限才能访问）
                if (!checkUri(request.getRequestURI())){
                    throw new BadRequestException("没有访问权限");
                }
                //  放行请求
                filterChain.doFilter(request, response);
            }
        } catch (BadRequestException e) {
            //  token问题,统一作401处理
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    /**
    * @Description: 从http中获取token
    * @Param: [request]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    private String getTokenFromHttpServletRequest(HttpServletRequest request){
        //  通过Authorization获取token
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
        }
        return null;
    }

    /**
    * @Description: 校验访问的url
    * @Param: [url]
    * @return: void
    * @Author: starao
    * @Date: 2022/1/19
    */
    private boolean checkUri(String url){
        try {
            boolean b = false;
            if (SecurityUtil.getCurrentRoles().contains(CommonConstants.ROLE_ADMIN)){
                return true;
            }
            List<String> menuList;
            String menuKey = "menu_" + SecurityUtil.getCurrentUserId();
            //  从redis中取出用户的所有菜单及按钮权限
            menuList = Collections.singletonList(redisUtil.getValue(menuKey));
            //  如果redis为空
            if (CollectionUtils.isEmpty(menuList)){
                //  重新获取当前用户所有授权菜单
                menuList = menuService.getUrlsByRoles(SecurityUtil.getCurrentRoles());
                //  如果不为空
                if (!CollectionUtils.isEmpty(menuList)){
                    //  保存进redis中
                    redisUtil.setValue(menuKey, menuList, 7200L, TimeUnit.SECONDS);
                }
            }

            if (menuList.contains(url)){
                b = true;
            }
            return b;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }
}
