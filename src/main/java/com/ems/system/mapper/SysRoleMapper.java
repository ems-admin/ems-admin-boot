package com.ems.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ems.system.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:20
 **/
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过用户ID获取当前用户角色
     * @param userId
     * @return
     */
    List<SysRole> getRoleByUserId(@Param("userId") Long userId);
}
