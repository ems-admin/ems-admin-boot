package com.ems.system.controller;

import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.ResultUtil;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.SysUser;
import com.ems.system.entity.dto.UserDto;
import com.ems.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 17:06
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysUserController extends ResultUtil {

    private final SysUserService userService;

    /**
    * @Description: 查询用户列表
    * @Param: [blurry]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("查询用户列表")
    @GetMapping("/user/table")
    public ResponseEntity<Object> queryUserTable(String blurry){
        try {
            return success(true, userService.queryUserTable(blurry));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 编辑用户
    * @Param: [userDto]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("编辑用户")
    @PostMapping("/user/edit")
    public ResponseEntity<Object> editUser(@RequestBody UserDto userDto){
        try {
            userService.editUser(userDto);
            return success(true, "编辑成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 删除用户
    * @Param: [id]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("删除用户")
    @DeleteMapping("/user/del")
    public ResponseEntity<Object> delUser(String id){
        try {
            userService.delUser(id);
            return success(true, "删除成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, "删除失败");
        }
    }

    /**
    * @Description: 修改用户状态
    * @Param: [sysUser]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("改变用户状态")
    @PutMapping("/user/enabled")
    public ResponseEntity<Object> enabledUser(@RequestBody SysUser sysUser){
        String str = sysUser.isEnabled() ? "启用" : "停用";
        try {
            userService.enabledUser(sysUser);
            return success(true, str + "成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }
}
