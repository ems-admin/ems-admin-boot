package com.ems.config.security;

import com.ems.system.entity.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:23
 **/
@Getter
@Setter
public class JwtUser {

    private UserDto userDto;

    private String token;

    /**
    * @Description: 自定义登录后返回的用户对象
    * @Param: [token, userDto]
    * @return:
    * @Author: starao
    * @Date: 2021/11/27
    */
    public JwtUser(String token, UserDto userDto){
        this.token = token;
        this.userDto = userDto;
    }
}
