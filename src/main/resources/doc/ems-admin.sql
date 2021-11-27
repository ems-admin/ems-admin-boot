/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : ems-admin

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 27/11/2021 21:36:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` VALUES (23);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '日志类型（1正常 2错误）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '请求方式',
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '请求参数',
  `time` bigint DEFAULT NULL COMMENT '耗时（毫秒）',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT 'IP',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '请求用户名',
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_german2_ci COMMENT '错误信息详情',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (1, '1', 'com.ems.system.controller.LoginController.login()', '{com.ems.system.entity.dto.UserDto@5a482df3 FirewalledRequest[ org.apache.catalina.connector.RequestFacade@1aa04803]  }', 554, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 20:49:28', '用户登录');
INSERT INTO `sys_log` VALUES (2, '1', 'com.ems.system.controller.SysMenuController.getMenuTree()', '{ }', 12, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 20:49:28', '获取菜单树');
INSERT INTO `sys_log` VALUES (3, '1', 'com.ems.system.controller.SysUserController.queryUserTable()', '{null  }', 7, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 20:49:31', '查询用户列表');
INSERT INTO `sys_log` VALUES (4, '1', 'com.ems.system.controller.SysMenuController.getMenuTree()', '{ }', 9, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 20:53:11', '获取菜单树');
INSERT INTO `sys_log` VALUES (5, '1', 'com.ems.system.controller.SysUserController.queryUserTable()', '{null  }', 3, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 20:53:13', '查询用户列表');
INSERT INTO `sys_log` VALUES (6, '1', 'com.ems.system.controller.SysMenuController.getMenuTableTree()', '{null  }', 5, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 20:53:16', '获取菜单的table树');
INSERT INTO `sys_log` VALUES (7, '1', 'com.ems.system.controller.SysRoleController.getRoleList()', '{  }', 6, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 20:53:19', '获取角色列表');
INSERT INTO `sys_log` VALUES (8, '1', 'com.ems.system.controller.SysMenuController.getMenuTree()', '{ }', 236, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:12:37', '获取菜单树');
INSERT INTO `sys_log` VALUES (9, '1', 'com.ems.system.controller.SysUserController.queryUserTable()', '{null  }', 18, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:12:39', '查询用户列表');
INSERT INTO `sys_log` VALUES (10, '1', 'com.ems.system.controller.SysMenuController.getMenuTableTree()', '{null  }', 6, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:12:41', '获取菜单的table树');
INSERT INTO `sys_log` VALUES (11, '1', 'com.ems.system.controller.SysRoleController.getRoleList()', '{  }', 8, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:12:42', '获取角色列表');
INSERT INTO `sys_log` VALUES (12, '1', 'com.ems.system.controller.SysRoleController.getAllRoleForXm()', '{null  }', 19, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:12:51', '获取角色选择框列表');
INSERT INTO `sys_log` VALUES (13, '1', 'com.ems.system.controller.SysUserController.editUser()', '{com.ems.system.entity.dto.UserDto@62b92670  }', 93, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:13:05', '编辑用户');
INSERT INTO `sys_log` VALUES (14, '1', 'com.ems.system.controller.SysUserController.queryUserTable()', '{null  }', 2, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:13:05', '查询用户列表');
INSERT INTO `sys_log` VALUES (15, '1', 'com.ems.system.controller.SysRoleController.getAllRoleForXm()', '{null  }', 6, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:13:07', '获取角色选择框列表');
INSERT INTO `sys_log` VALUES (16, '1', 'com.ems.system.controller.SysUserController.editUser()', '{com.ems.system.entity.dto.UserDto@5dbe8b1c  }', 79, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:13:19', '编辑用户');
INSERT INTO `sys_log` VALUES (17, '1', 'com.ems.system.controller.SysUserController.queryUserTable()', '{null  }', 2, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:13:19', '查询用户列表');
INSERT INTO `sys_log` VALUES (18, '1', 'com.ems.system.controller.SysUserController.delUser()', '{3  }', 13, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:13:23', '删除用户');
INSERT INTO `sys_log` VALUES (19, '1', 'com.ems.system.controller.SysUserController.queryUserTable()', '{null  }', 2, '0:0:0:0:0:0:0:1', 'admin', NULL, '2021-11-27 21:13:23', '查询用户列表');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父ID',
  `path` varchar(100) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT 'url',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '类型（1菜单 2页面 3按钮）',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `component` varchar(100) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '组件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, NULL, '1', 1, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, '/user', '2', 1, NULL, '2021-11-22 14:28:45', 'user/index');
INSERT INTO `sys_menu` VALUES (3, '菜单管理', 1, '/menu', '2', 2, '2021-08-18 21:26:01', NULL, 'menu/index');
INSERT INTO `sys_menu` VALUES (11, '日志管理', 0, NULL, '1', 2, '2021-08-23 22:00:33', '2021-11-23 09:22:16', NULL);
INSERT INTO `sys_menu` VALUES (12, '角色管理', 1, '/role', '2', 3, '2021-08-24 20:46:39', '2021-11-22 23:01:32', 'role/index');
INSERT INTO `sys_menu` VALUES (13, '菜单编辑', 3, '/menu/edit', '2', 1, '2021-11-20 09:36:01', NULL, 'menu/edit');
INSERT INTO `sys_menu` VALUES (14, '用户编辑', 2, '/user/edit', '2', 1, '2021-11-22 14:56:30', NULL, 'user/edit');
INSERT INTO `sys_menu` VALUES (15, '角色编辑', 12, '/role/edit', '2', 1, '2021-11-22 22:49:32', NULL, 'role/edit');
INSERT INTO `sys_menu` VALUES (16, '角色授权', 12, '/role/authorize', '2', 2, '2021-11-22 23:08:35', NULL, 'role/authorize');
INSERT INTO `sys_menu` VALUES (17, '日志记录', 11, '/log', '2', 1, '2021-11-23 09:32:38', '2021-11-23 09:32:48', 'log/index');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '角色代码',
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '角色名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'ROLE_ADMIN', '超级管理员', '2021-08-25 22:55:45', '系统唯一超级管理员，可操作任意功能', NULL);
INSERT INTO `sys_role` VALUES (2, 'ROLE_USER', '普通用户', '2021-11-24 22:47:44', '普通用户', NULL);
INSERT INTO `sys_role` VALUES (3, 'ROLE_TEST', '测试用户', '2021-11-24 22:48:04', '测试用户', '2021-11-24 23:30:32');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_user` VALUES (1, 1, 1, '2021-11-17 23:00:35', NULL);
INSERT INTO `sys_role_user` VALUES (2, 2, 2, '2021-11-27 21:13:05', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '邮箱',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '密码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `enabled` bit(1) DEFAULT NULL COMMENT '状态（0停用 1启用）',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '昵称',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '123@qq.com', 'admin', '$2a$10$RaxC7eKvHy1Sj.UTLUN3Be/3xhdA5yd08rsVcNaWnBq8muCdbY7j.', '2021-11-27 20:50:19', b'1', '超级管理员', NULL);
INSERT INTO `sys_user` VALUES (2, NULL, 'user', '$2a$10$b5mQxndMAYD3cOnqVyENHOFCRu7lgOY0etmNT5JRtX9RGz.Bkh.jC', '2021-11-27 21:13:05', b'0', '普通用户', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
