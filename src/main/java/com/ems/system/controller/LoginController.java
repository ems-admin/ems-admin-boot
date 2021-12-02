package com.ems.system.controller;

import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.JwtUtil;
import com.ems.common.utils.ResultUtil;
import com.ems.config.security.JwtUser;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.SysRole;
import com.ems.system.entity.SysUser;
import com.ems.system.entity.dto.UserDto;
import com.ems.system.service.SysRoleService;
import com.ems.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:08
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController extends ResultUtil {

    private final SysUserService userService;

    private final SysRoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Log("用户登录")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto, HttpServletRequest request){
        try {
            //  根据用户名查询用户是否存在
            SysUser user = userService.findByName(userDto.getUsername());
            if (user == null){
                return fail(false, "用户名或密码错误");
            }
            //  判断密码是否正确
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            //  将认证信息设置到SpringSecurity上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //  获取当前用户角色对象
            List<SysRole> sysRoles = roleService.getRoleByUserId(user.getId());
            //  角色
            List<String> roles = new ArrayList<>();
            sysRoles.forEach( sysRole -> {
                roles.add(sysRole.getRoleCode());
            });
            //  生成token
            String token = JwtUtil.generateToken(user.getUsername(), roles, false);

            //  用户信息
            userDto.setEmail(user.getEmail());
            userDto.setNickName(user.getNickName());
            userDto.setRoles(roles);
            //  隐藏密码
            userDto.setPassword("******");

            return success(true, new JwtUser(token, userDto));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        } catch (Exception e){
            e.printStackTrace();
            return fail(false, e.getMessage());
        }
    }

    /**
    * @Description: 用户注册
    * @Param: [userDto]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("用户注册")
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto){
        try {
            userService.editUser(userDto);
            return success(true, "注册成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }
}
