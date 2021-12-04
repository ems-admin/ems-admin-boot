package com.ems.system.service;

import com.ems.system.entity.SysUser;
import com.ems.system.entity.dto.UserDto;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:28
 **/
public interface SysUserService {

    /**
    * @Description: 根据用户名查询用户
    * @Param: [userName]
    * @return: com.ems.system.entity.SysUser
    * @Author: starao
    * @Date: 2021/11/27
    */
    SysUser findByName(String userName);

    /**
    * @Description: 编辑用户
    * @Param: [userDto]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void editUser(UserDto userDto);

    /**
    * @Description: 查询用户列表
    * @Param: [blurry]
    * @return: java.util.List<com.ems.system.entity.SysUser>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<UserDto> queryUserTable(String blurry);

    /**
    * @Description: 根据用户名查询用户
    * @Param: [username]
    * @return: com.ems.system.entity.dto.UserDto
    * @Author: starao
    * @Date: 2021/11/27
    */
    UserDto loadByName(String username);

    /**
    * @Description: 删除用户
    * @Param: [id]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void delUser(String id);

    /**
    * @Description: 修改用户状态
    * @Param: [sysUser]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void enabledUser(SysUser sysUser);
}
