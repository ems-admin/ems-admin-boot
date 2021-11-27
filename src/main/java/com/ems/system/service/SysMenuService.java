package com.ems.system.service;

import com.alibaba.fastjson.JSONArray;
import com.ems.system.entity.SysMenu;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:24
 **/
public interface SysMenuService {

    /**
    * @Description: 获取菜单树
    * @Param: [roles]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/11/27
    */
    JSONArray getMenuTree(List<String> roles);

    /**
    * @Description: 获取菜单table树
    * @Param: [blurry]
    * @return: java.util.List<com.ems.system.entity.SysMenu>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<SysMenu> getMenuTableTree(String blurry);

    /**
    * @Description: 获取下拉框里面的树
    * @Param: [type]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/11/27
    */
    JSONArray getMenuSelectTree(String type);

    /**
    * @Description: 编辑菜单
    * @Param: [sysMenu]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void editMenu(SysMenu sysMenu);

    /**
    * @Description: 删除菜单
    * @Param: [id]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void delMenu(Long id);

    /**
    * @Description: 获取角色菜单树
    * @Param: [roleId]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/11/27
    */
    JSONArray getMenuTreeByRoleId(String roleId);

    /**
    * @Description: 查询当前用户所有权限菜单
    * @Param: [roles]
    * @return: java.util.List<com.ems.system.entity.SysMenu>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<SysMenu> queryAllMenus(List<String> roles);
}
