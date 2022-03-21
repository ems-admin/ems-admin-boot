package com.ems.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ems.common.constant.CommonConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.StringUtil;
import com.ems.system.entity.SysMenu;
import com.ems.system.entity.SysRoleMenu;
import com.ems.system.mapper.SysMenuMapper;
import com.ems.system.mapper.SysRoleMenuMapper;
import com.ems.system.service.SysMenuService;
import com.ems.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:33
 **/
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper menuMapper;

    private final SysRoleMenuMapper roleMenuMapper;

    /**
     * @param roles
     * @Description: 获取菜单树
     * @Param: [roles]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public JSONArray getMenuTree(List<String> roles) {
        try {
            List<SysMenu> menuListAll;
            //  如果角色中包含admin,则直接查询所有菜单
            if (roles.contains(CommonConstants.ROLE_ADMIN)){
                menuListAll = menuMapper.selectList(null);
            } else {
                menuListAll = menuMapper.getMenuTree(roles);
                if (!CollectionUtils.isEmpty(menuListAll)){
                    Set<SysMenu> menuSet = new HashSet<>();
                    for (SysMenu sysMenu : menuListAll) {
                        menuSet.addAll(getAllMenusByChildId(sysMenu.getId()));
                    }
                    menuListAll = new ArrayList<>(menuSet).stream().sorted(Comparator.comparing(SysMenu::getId)).collect(Collectors.toList());
                }
            }
            return getObjects(menuListAll, 0l, "title", null);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param type
     * @Description: 获取下拉框里面的树
     * @Param: [type]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public JSONArray getMenuSelectTree(String type) {
        try {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(type)){
                wrapper.eq("type", type);
            }
            List<SysMenu> menuList = menuMapper.selectList(wrapper);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "0");
            jsonObject.put("name", "顶级目录");
            jsonObject.put("open", true);
            jsonObject.put("checked", false);
            if (!getObjects(menuList, 0l,  "name", null).isEmpty()){
                jsonObject.put("children", getObjects(menuList, 0l,  "name", null));
            }
            jsonArray.add(0, jsonObject);
            return jsonArray;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param sysMenu
     * @Description: 编辑菜单
     * @Param: [sysMenu]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void editMenu(SysMenu sysMenu) {
        try {
            if (sysMenu.getId() != null){
                menuMapper.updateById(sysMenu);
            } else {
                menuMapper.insert(sysMenu);
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param id
     * @Description: 删除菜单
     * @Param: [id]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void delMenu(Long id) {
        try {
            //  校验菜单是否已绑定角色
            checkMenuRole(id);
            menuMapper.deleteById(id);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param roleId
     * @Description: 获取角色菜单树
     * @Param: [roleId]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public JSONArray getMenuTreeByRoleId(String roleId) {
        try {
            //  当前角色菜单
            List<String> menuList = menuMapper.getMenuTreeByRoleId(roleId);
            //  所有菜单
            List<SysMenu> allMenuList = menuMapper.selectList(null);
            return getObjects(allMenuList, 0l, "title", menuList);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param roles
     * @Description: 查询当前用户所有权限菜单
     * @Param: [roles]
     * @return: java.util.List<com.ems.system.entity.SysMenu>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<SysMenu> queryAllMenus(List<String> roles) {
        try {
            if (roles.contains(CommonConstants.ROLE_ADMIN)){
                return menuMapper.selectList(null);
            }
            return menuMapper.getMenuTree(roles);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param blurry
     * @Description: 获取菜单列表
     * @Param: [blurry]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2021/12/11
     */
    @Override
    public JSONArray getMenuTable(String blurry) {
        try {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            if (StringUtil.isNotBlank(blurry)){
                wrapper.like(SysMenu::getName, blurry);
                wrapper.or();
                wrapper.like(SysMenu::getPath, blurry);
            }
            wrapper.orderByAsc(SysMenu::getSort);
            List<SysMenu> list = menuMapper.selectList(wrapper);
            return getObjects(list, 0l, "title", null);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param roles
     * @Description: 通过角色获取所有授权菜单
     * @Param: [currentRoles]
     * @return: java.util.List<java.lang.String>
     * @Author: starao
     * @Date: 2022/1/19
     */
    @Override
    public List<String> getUrlsByRoles(List<String> roles) {
        try {
            return menuMapper.getMenuUrlByRole(roles);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param roles
     * @Description: 获取左侧菜单树
     * @Param: [roles]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2022/3/20
     */
    @Override
    public JSONArray getMenuTreeForLeft(List<String> roles) {
        //  如果是系统管理员，则直接获取所有菜单
        List<SysMenu> menuList = new ArrayList<>();
        if (roles.contains(CommonConstants.ROLE_ADMIN)){
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            //  菜单
            wrapper.eq(SysMenu::getType, "1");
            wrapper.or();
            //  页面
            wrapper.eq(SysMenu::getType, "2");
            wrapper.orderByAsc(SysMenu::getSort);
            menuList = menuMapper.selectList(wrapper);
        }
        JSONArray jsonArray = new JSONArray();
        if (!CollectionUtils.isEmpty(menuList)){
            List<SysMenu> topMenuList = menuList.stream().filter(sysMenu -> sysMenu.getParentId() == 0L).collect(Collectors.toList());
            for (SysMenu sysMenu : topMenuList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", sysMenu.getId());
                jsonObject.put("path", sysMenu.getPath());
                jsonObject.put("name", sysMenu.getName());
                jsonObject.put("type", sysMenu.getType());
                if (!CollectionUtils.isEmpty(getChildMenu(menuList, sysMenu.getId(), "left", null))){
                    jsonObject.put("children", getChildMenu(menuList, sysMenu.getId(), "left", null));
                }
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
    * @Description: 获取子菜单
    * @Param: [menuListAll, id, title, menuIds]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/11/27
    */
    public JSONArray getChildMenu(List<SysMenu> menuListAll, Long id, String title, List<String> menuIds){
        try {
            return getObjects(menuListAll, id, title, menuIds);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 组装树的公共方法
    * @Param: [menuListAll, id, title, menuIds]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/11/27
    */
    private JSONArray getObjects(List<SysMenu> menuListAll, Long id, String title, List<String> menuIds) {
        try {
            //  获取子菜单(不包含按钮)
            List<SysMenu> childList = menuListAll.stream().filter(menu ->
                    menu.getParentId().longValue() == id.longValue() && !"3".equals(menu.getType())).collect(Collectors.toList());
            //  组装树
            JSONArray jsonArray = new JSONArray();
            childList.forEach(menu -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", menu.getId());
                jsonObject.put(title, menu.getName());
                if ("title".equals(title)){
                    jsonObject.put("parentId", menu.getParentId());
                    jsonObject.put("path", menu.getPath());
                    jsonObject.put("name", menu.getName());
                    jsonObject.put("type", menu.getType());
                    jsonObject.put("sort", menu.getSort());
                    jsonObject.put("component", menu.getComponent());
                    jsonObject.put("permission", menu.getPermission());
                    if (!CollectionUtils.isEmpty(menuIds) && menuIds.contains(menu.getId().toString())){
                        jsonObject.put("checked", true);
                    }
                } else if ("name".equals(title)){
                    jsonObject.put("open", false);
                    jsonObject.put("checked", false);
                } else if ("left".equals(title)){
                    jsonObject.put("name", menu.getName());
                    jsonObject.put("path", menu.getPath());
                    jsonObject.put("type", menu.getType());
                }
                if (menuListAll.stream().anyMatch(menu1 -> menu1.getParentId().longValue() == id.longValue())) {
                    if (!getChildMenu(menuListAll, menu.getId(), title, menuIds).isEmpty()){
                        jsonObject.put("children", getChildMenu(menuListAll, menu.getId(), title, menuIds));
                    }
                }
                jsonArray.add(jsonObject);
            });
            return jsonArray;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 校验菜单是否已绑定角色
    * @Param: [menuId]
    * @return: void
    * @Author: starao
    * @Date: 2021/12/4
    */
    private void checkMenuRole(Long menuId){
        try {
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleMenu::getMenuId, menuId);
            long count = roleMenuMapper.selectCount(wrapper);
            if (count > 0){
                throw new BadRequestException("该菜单已绑定角色，无法删除");
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 通过菜单Id获取所有上级菜单
    * @Param: [menuId]
    * @return: java.util.List<com.ems.system.entity.SysMenu>
    * @Author: starao
    * @Date: 2021/12/17
    */
    private List<SysMenu> getAllMenusByChildId(Long menuId){
        try {
            return menuMapper.getAllMenusByChildId(menuId);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }
}
