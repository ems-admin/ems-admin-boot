package com.ems.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ems.common.constant.CommonConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.StringUtil;
import com.ems.system.entity.SysUser;
import com.ems.system.entity.dto.UserDto;
import com.ems.system.mapper.SysUserMapper;
import com.ems.system.service.SysRoleUserService;
import com.ems.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:44
 **/
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final PasswordEncoder passwordEncoder;

    private final SysRoleUserService roleUserService;

    /**
     * @param userName
     * @Description: 根据用户名查询用户
     * @Param: [userName]
     * @return: com.ems.system.entity.SysUser
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public SysUser findByName(String userName) {
        try {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("username", userName);
            return sysUserMapper.selectOne(wrapper);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userDto
     * @Description: 编辑用户
     * @Param: [userDto]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void editUser(UserDto userDto) {
        try {
            checkUser(userDto);
            SysUser user = new SysUser();
            user.setPassword(userDto.getPassword());
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setId(userDto.getId());
            user.setNickName(userDto.getNickName());
            if (userDto.getEnabled() != null){
                user.setEnabled(userDto.getEnabled());
            }
            if (user.getId() != null){
                sysUserMapper.updateById(user);
            } else {
                //  初始化用户密码.默认111111
                user.setPassword(passwordEncoder.encode(CommonConstants.DEFAULT_PASSWORD));
                sysUserMapper.insert(user);
            }

            //  如果带有角色,就修改角色
            if (!CollectionUtils.isEmpty(userDto.getRoles()) && user.getId() != null){
                roleUserService.edit(user.getId(), userDto.getRoles());
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param blurry
     * @Description: 查询用户列表
     * @Param: [blurry]
     * @return: java.util.List<com.ems.system.entity.SysUser>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<UserDto> queryUserTable(String blurry) {
        try {
            return sysUserMapper.queryUserTable(blurry);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param username
     * @Description: 根据用户名查询用户
     * @Param: [username]
     * @return: com.ems.system.entity.dto.UserDto
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public UserDto loadByName(String username) {
        try {
            return sysUserMapper.loadByName(username);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param id
     * @Description: 删除用户
     * @Param: [id]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    @Transactional
    public void delUser(String id) {
        try {
            //  先解除用户与角色的绑定
            roleUserService.deleteByUserId(id);
            //  再删除用户
            sysUserMapper.deleteById(id);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param sysUser
     * @Description: 修改用户状态
     * @Param: [sysUser]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void enabledUser(SysUser sysUser) {
        try {
            sysUserMapper.updateById(sysUser);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 校验用户名和昵称
    * @Param: [userDto]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    private void checkUser(UserDto userDto){
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        if (userDto.getId() != null){
            wrapper.ne(SysUser::getId, userDto.getId());
        }
        if (StringUtil.isNotBlank(userDto.getUsername()) && StringUtil.isNotBlank(userDto.getNickName())){
            wrapper.and(w -> w.eq(SysUser::getUsername, userDto.getUsername()).or().eq(SysUser::getNickName, userDto.getNickName()));
        }
        long count = sysUserMapper.selectCount(wrapper);
        if (count > 0){
            throw new BadRequestException("用户名或昵称已存在，请重新输入");
        }
    }
}
