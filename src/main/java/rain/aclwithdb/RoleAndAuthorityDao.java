package rain.aclwithdb;

import java.util.List;

/**
 * 角色、菜单、请求表以及相互关系的dao
 *
 * CREATE TABLE `REQUEST`
 * (
 *   `ID`          BIGINT(20) UNSIGNED NOT NULL COMMENT '请求ID',
 *   `URL`         VARCHAR(50)         NOT NULL COMMENT '请求',
 *   `DESC`        VARCHAR(50)         NOT NULL COMMENT '描述',
 *   `CREATE_TIME` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   PRIMARY KEY (`ID`)
 * ) ENGINE = INNODB
 *   DEFAULT CHARSET = UTF8MB4 COMMENT = '请求表';
 *
 * CREATE TABLE `MENU_REQUEST`
 * (
 *   `ID`          BIGINT(20) UNSIGNED NOT NULL COMMENT '主键ID',
 *   `MENU_ID`     BIGINT(20)          NOT NULL COMMENT '菜单',
 *   `REQUEST_ID`  BIGINT(20)          NOT NULL COMMENT '请求ID',
 *   `CREATE_TIME` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   PRIMARY KEY (`ID`)
 * ) ENGINE = INNODB
 *   DEFAULT CHARSET = UTF8MB4 COMMENT = '菜单下的请求关系表';
 *
 * CREATE TABLE `menu` (
 *   `ID` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
 *   `MENU_NAME` varchar(50) NOT NULL COMMENT '菜单名称',
 *   `MENU_ROUTE` varchar(50) NOT NULL COMMENT '前端菜单路由',
 *   `PARENT_MENU_ID` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
 *   `ANONYMOUS_CAN_ACCESS` char(1) NOT NULL DEFAULT '0' COMMENT '该菜单匿名访问,0不准1准',
 *   `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   PRIMARY KEY (`ID`)
 * ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '菜单表';
 *
 * CREATE TABLE `ROLE_MENU`
 * (
 *   `ID`          BIGINT(20) UNSIGNED NOT NULL COMMENT '主键ID',
 *   `ROLE_ID`     BIGINT(20)          NOT NULL COMMENT '角色ID',
 *   `MENU_ID`     BIGINT(20)          NOT NULL COMMENT '菜单ID',
 *   `CREATE_TIME` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   PRIMARY KEY (`ID`)
 * ) ENGINE = INNODB
 *   DEFAULT CHARSET = UTF8MB4 COMMENT = '角色下的菜单关系表';
 *
 * CREATE TABLE `ROLE`
 * (
 *   `ID`          BIGINT(20) UNSIGNED NOT NULL COMMENT '角色ID',
 *   `ROLE_NAME`   VARCHAR(50)         NOT NULL COMMENT '角色名称',
 *   `CREATE_TIME` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   PRIMARY KEY (`ID`)
 * ) ENGINE = INNODB
 *   DEFAULT CHARSET = UTF8MB4 COMMENT = '角色表';
 *
 * CREATE TABLE `USER_ROLE`
 * (
 *   `ID`          BIGINT(20) UNSIGNED NOT NULL COMMENT '主键ID',
 *   `USER_ID`     BIGINT(20)          NOT NULL COMMENT '用户id',
 *   `ROLE_ID`     BIGINT(20)          NOT NULL COMMENT '角色ID',
 *   `CREATE_TIME` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   PRIMARY KEY (`ID`)
 * ) ENGINE = INNODB
 *   DEFAULT CHARSET = UTF8MB4 COMMENT = '用户下的角色关系表';
 *
 * CREATE TABLE `USER`
 * (
 *   `USER_ID`     BIGINT(20) UNSIGNED NOT NULL COMMENT '用户ID',
 *   `NAME_NAME`   VARCHAR(50)         NOT NULL COMMENT '用户名称',
 *   `PASSWORD`    VARCHAR(50)         NOT NULL COMMENT '登录密码',
 *   `CREATE_TIME` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   `UPDATE_TIME` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 *   PRIMARY KEY (`USER_ID`)
 * ) ENGINE = INNODB
 *   DEFAULT CHARSET = UTF8MB4 COMMENT = '用户表';
 */
//@IEqler
public interface RoleAndAuthorityDao {

//    @Sql("  SELECT DISTINCT " +
//            "      R.URL " +
//            " FROM REQUEST  R  " +
//            "     ,MENU_REQUEST MR  " +
//            "     ,MENU M       " +
//            "WHERE R.ID = MR.ID  " +
//            "  AND M.ID = MR.MENU_ID  " +
//            "  AND M.ANONYMOUS_CAN_ACCESS = '1'")
//    List<String> queryAnonymousCanAccessMenus();
//
//    @Sql("  SELECT DISTINCT" +
//            "      R.URL" +
//            "     ,RM.ROLE_ID " +
//            " FROM REQUEST  R " +
//            "     ,MENU_REQUEST MR " +
//            "     ,MENU M " +
//            "     ,ROLE_MENU RM " +
//            "WHERE R.ID = MR.ID " +
//            "  AND M.ID = RM.MENU_ID " +
//            "  AND M.ANONYMOUS_CAN_ACCESS = '0'" +
//            "  AND RM.MENU_ID = MR.MENU_ID")
//    List<RoleRequest> queryRequests();
//
//    @Sql("  SELECT M.ID" +
//            "     ,M.MENU_NAME" +
//            "     ,M.MENU_ROUTE " +
//            "     ,M.PARENT_MENU_ID " +
//            " FROM MENU M " +
//            "     ,ROLE_MENU RM " +
//            "WHERE M.ID = RM.MENU_ID " +
//            "  AND M.ANONYMOUS_CAN_ACCESS = '0'" +
//            "  AND RM.ROLE_ID = #1#")
//    List<Menu> queryMenusByRoleId(String roleId);
//
//    @Sql("  SELECT M.ID" +
//            "     ,M.MENU_NAME" +
//            "     ,M.MENU_ROUTE " +
//            "     ,M.PARENT_MENU_ID " +
//            " FROM MENU M " +
//            "     ,ROLE_MENU RM " +
//            "WHERE M.ID = RM.MENU_ID " +
//            "  AND M.ANONYMOUS_CAN_ACCESS = '0'" +
//            "  AND RM.ROLE_ID in (/* in _1 */)")
//    List<Menu> queryMenusByRoleIds(List<String> roleIds);
//
//    @Sql("delete from role_menu where ROLE_ID = #1#")
//    int deleteRoleMenus(String roleId);
//
//    @Sql(" insert into role_menu (ID              , MENU_ID             , ROLE_ID          ) " +
//            "              values" +
//            " /* for item=roleMenu index=index collection=_1 separator=, */" +
//            "                    ('#roleMenu.id#' , '#roleMenu.menuId#' , '#roleMenu.roleId#')" +
//            " /* end */")
//    int addRoleMenus(List<RoleMenu> roleMenus);
//
//    @Sql("select `ID`, ROLE_NAME, NOTIFY_LEVEL, CREATE_TIME from `role` " +
//            "/* isNotEmpty _1 */" +
//            " where NOTIFY_LEVEL = #_1# " +
//            "/* end */")
//    List<Role> queryRoles(String notifyLevel);
//
//    @Sql("update role set ROLE_NAME = #2# where id = #1#")
//    int updateRoleName(String id, String name);
}

