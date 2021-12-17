package com.ems.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ems.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:18
 **/
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
    * @Description: 获取菜单树
    * @Param: [roles]
    * @return: java.util.List<com.ems.system.entity.SysMenu>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<SysMenu> getMenuTree(@Param("roles") List<String> roles);

    /**
    * @Description: 通过角色获取url
    * @Param: [roles]
    * @return: java.util.List<java.lang.String>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<String> getMenuUrlByRole(@Param("roles") List<String> roles);

    /**
    * @Description: 获取角色菜单树
    * @Param: [roleId]
    * @return: java.util.List<java.lang.String>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<String> getMenuTreeByRoleId(@Param("roleId") String roleId);

    /**
    * @Description: 通过菜单Id获取所有上级菜单
    * @Param: [menuId]
    * @return: java.util.List<com.ems.system.entity.SysMenu>
    * @Author: starao
    * @Date: 2021/12/17
    */
    List<SysMenu> getAllMenusByChildId(@Param("menuId") Long menuId);
}
