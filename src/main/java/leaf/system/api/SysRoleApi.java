package leaf.system.api;

import leaf.common.mysql.Where;
import leaf.system.common.Http;
import leaf.system.common.SysUser;
import leaf.common.DB;
import leaf.common.object.JSONList;
import leaf.common.object.JSONMap;
import leaf.common.util.Valid;
import leaf.system.annotate.LoginToken;
import leaf.system.interceptor.ApiGlobalInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色模块
 */
@RestController
public class SysRoleApi {
    /**
     * 获取角色列表
     */
    @GetMapping("/system/api/role/getRoleList")
    @LoginToken(validBackend = true,permissionKey = "PermissionKey-system_role_getRoleList")
    public JSONMap getRoleList() {
        String roleId = Http.param("role_id");
        String roleName = Http.param("role_name");
        String isAllow_login_backend = Http.param("is_allow_login_backend");
        String isDisable = Http.param("is_disable");
        String sortField = Http.param("SortField");
        String sortOrder = Http.param("SortOrder");
//        String relationship = SQLWhere.Like;
//
//        if("1".equals(Http.param("IsEqual","0"))) {
//            relationship = SQLWhere.Equal;
//        }
//
//        String sql = "" +
//                "select role_id,role_name,if(is_allow_login_backend = 1,1,0) 'is_allow_login_backend',if(is_disable = 1,1,0) 'is_disable' " +
//                "from sys_role ";
//        sql += new SQLWhere()
//                .addAnd("role_id", roleId, relationship)
//                .addOr("role_name", roleName, relationship)
//                .addAnd("ifnull(is_allow_login_backend,0)", isAllow_login_backend, "=")
//                .addAnd("ifnull(is_disable,0)", isDisable, "=").toString();

        Where.Operator relationship = Where.Operator.LIKE;

        if("1".equals(Http.param("IsEqual","0"))) {
            relationship = Where.Operator.EQ;
        }

        String sql = "" +
                "select role_id,role_name,if(is_allow_login_backend = 1,1,0) 'is_allow_login_backend',if(is_disable = 1,1,0) 'is_disable' " +
                "from sys_role ";
        sql += new Where(true)
                .and().add("role_id", roleId, Where.Operator.EQ)
                .or().add("role_name", roleName, relationship).group()
                .and().eq("ifnull(is_allow_login_backend,0)", isAllow_login_backend)
                .and().eq("ifnull(is_disable,0)", isDisable).prependWhere().toString();

        //排序字段
        switch(sortField) {
            case "role_id":
            case "role_name":
            case "is_allow_login_backend":
            case "is_disable":
                if(sortOrder.equals("asc")) sql += " order by " + sortField + " asc ";
                else if(sortOrder.equals("desc")) sql += " order by " + sortField+" desc ";
                else sql += " order by role_id desc ";
                break;
            default:
                sql += " order by role_id desc ";
        }
        return DB.sqlToJSONMap(sql,Http.param("PageNo"),Http.param("PageCount"),"100");
    }
    /**
     * 修改角色
     */
    @PostMapping("/system/api/role/updateRole")
    public JSONMap updateRole() {
        String updateType = Http.param("UpdateType");
        String roleId = Http.param("role_id");
        String roleName = Http.param("role_name");
        String isAllowLoginBackend = Http.param("is_allow_login_backend");
        String isDisable = Http.param("is_disable");

        if("1".equals(roleId)) {
            return JSONMap.error("没有权限操作该角色");
        }

        if("1".equals(isAllowLoginBackend)) {
            isAllowLoginBackend = "1";
        } else {
            isAllowLoginBackend = "null";
        }

        if("1".equals(isDisable)) {
            isDisable = "1";
        } else {
            isDisable = "null";
        }

        switch(updateType) {
            case "Edit":
                if(!ApiGlobalInterceptor.permission("PermissionKey-system_role_updateRoleEdit")) {
                    Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                if(roleId.isEmpty()) {
                    return JSONMap.error("角色代码不能为空");
                }

                if(roleName.isEmpty()) {
                    return JSONMap.error("角色名称不能为空");
                }


                String sql = "update sys_role set role_name = '"+DB.e(roleName)+"',is_allow_login_backend = "+isAllowLoginBackend+",is_disable = "+isDisable+" where role_id = '"+DB.e(roleId)+"'";

                if(DB.update(sql) > 0) {
                    return JSONMap.success();
                }
                break;
            case "Add":
                if(roleName.isEmpty()) {
                    return JSONMap.error("角色名称不能为空");
                }

                if(!ApiGlobalInterceptor.permission("PermissionKey-system_role_updateRoleAdd")) {
                    Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                sql = "" +
                        "insert into sys_role(role_name,is_allow_login_backend,is_disable)" +
                        "value('"+DB.e(roleName)+"',"+isAllowLoginBackend+","+isDisable+");";

                if(DB.update(sql) > 0) {
                    return JSONMap.success();
                }
                break;
            case "Delete":
                if(!ApiGlobalInterceptor.permission("PermissionKey-system_role_updateRoleDelete")) {
                    Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                if(roleId.isEmpty()) {
                    return JSONMap.error("角色代码不能为空");
                }

                if(DB.update("delete from sys_role where role_id = '"+DB.e(roleId)+"'") > 0) {
                    return JSONMap.success();
                }
                break;
            case "IsAllowLoginBackend":
                if(!ApiGlobalInterceptor.permission("PermissionKey-system_role_updateRoleIsAllowLoginBackend")) {
                    Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                if(roleId.isEmpty()) {
                    return JSONMap.error("角色代码不能为空");
                }

                if(DB.update("update sys_role set is_allow_login_backend = "+isAllowLoginBackend+" where role_id = '"+DB.e(roleId)+"'") > 0) {
                    return JSONMap.success();
                }
                break;
            case "IsDisable":
                if(!ApiGlobalInterceptor.permission("PermissionKey-system_role_updateRoleIsDisable")) {
                    Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                if(roleId.isEmpty()) {
                    return JSONMap.error("角色代码不能为空");
                }

                if(DB.update("update sys_role set is_disable = "+isDisable+" where role_id = '"+DB.e(roleId)+"'") > 0) {
                    return JSONMap.success();
                }
                break;
            default:
                return JSONMap.error("修改类型有误");
        }
        return JSONMap.error("操作失败");
    }
    /**
     * 获取角色菜单列表
     */
    @GetMapping("/system/api/role/getRoleMenuList")
    @LoginToken(validBackend = true,permissionKey = "PermissionKey-system_role_getRoleMenuList")
    public JSONMap getRoleMenuList() {
        String roleId = Http.param("role_id");
        String sql;

        if(Valid.isEmpty(roleId)) {
            return JSONMap.error("角色代码不能为空");
        }

        if("1".equals(roleId)) {
            sql = "select menu_id,menu_name,parent_menu_id,menu_icon,url,type,1 'has_permission' from sys_menu order by sort desc";
        } else {
            sql = "select a.menu_id,a.menu_name,a.parent_menu_id,a.menu_icon,a.url,a.type,if(b.role_id = '" + DB.e(roleId) + "','1','0') 'has_permission' " +
                    "from sys_menu a " +
                    "   left join sys_role_menu_rel b on a.menu_id = b.menu_id and b.role_id = '" + DB.e(roleId) + "' " +
                    " order by a.sort desc";
        }
        return DB.sqlToJSONMap(sql);
    }
    /**
     * 修改角色菜单
     */
    @PostMapping("/system/api/role/updateRoleMenu")
    @LoginToken(validBackend = true,permissionKey = "PermissionKey-system_role_updateRoleMenu")
    public JSONMap updateRoleMenu() {
        String roleId = Http.param("role_id");
        String menuIds = Http.param("menu_id_arr");
        String[] menuIdArr = menuIds.split(",");

        if(Valid.isEmpty(roleId)) {
            return JSONMap.error("角色代码不能为空");
        }

        if("1".equals(roleId)) {
            return JSONMap.error("没有权限操作该角色");
        }

        roleId = DB.e(roleId);
        String sql = "delete from sys_role_menu_rel where role_id = '" + DB.e(roleId) + "';";

        for(String menuId : menuIdArr) {
            if(!menuId.isEmpty()) sql += "insert into sys_role_menu_rel(role_id,menu_id) value('" + roleId + "','" + DB.e(menuId) + "');";
        }

        if(DB.updateTransaction(sql) != -1) {
            return JSONMap.success();
        }
        return JSONMap.error("操作失败");
    }
    /**
     * 获取角色菜单权限键列表
     */
    @GetMapping("/system/api/role/getRoleMenuPermissionKeyList")
    public JSONMap getRoleMenuPermissionKeyList() {
        String menuId = Http.param("menu_id");
        String parentMenuId = Http.param("parent_menu_id");
        String type = Http.param("type");
        String userRoleIdStr = SysUser.getUserRoleIdStr();

        if("1".equals(userRoleIdStr) || userRoleIdStr.startsWith("1,")) userRoleIdStr = "";
        else if(Valid.isEmpty(userRoleIdStr)) userRoleIdStr = "-1";

        String sql = "" +
                "select distinct a.menu_id,permission_key " +
                "from sys_menu a " +
                "   left join sys_role_menu_rel b on a.menu_id = b.menu_id ",
//        whereSql = new SQLWhere()
//                .addAnd("a.menu_id", menuId, "=")
//                .addAnd("a.parent_menu_id", parentMenuId, "=")
//                .addAnd("a.type", type, "=")
//                .addAnd("b.role_id",userRoleIdStr,SQLWhere.In).toString();

        whereSql = new Where(true)
                .and().eq("a.menu_id", menuId)
                .and().eq("a.parent_menu_id", parentMenuId)
                .and().eq("a.type", type)
                .and().in("b.role_id",userRoleIdStr).prependWhere().toString();

        if(Valid.isBlank(whereSql)) {
            sql += " where";
        } else {
            sql += whereSql + " and ";
        }

        sql += " !isnull(permission_key) and permission_key != ''";
        return DB.sqlToJSONMap(sql);
    }
    /**
     * 获取角色菜单tree(弃用)
     */
    @Deprecated
    @GetMapping("/system/api/role/getRoleMenuTree")
    @LoginToken(validBackend = true)
    public JSONMap getRoleMenuTree() {
        String roleId = Http.param("role_id");
        String sql;

        if(Valid.isEmpty(roleId)) {
            return JSONMap.error("角色代码不能为空");
        }

        if("1".equals(roleId)) {
            sql = "select menu_id,menu_name,parent_menu_id,menu_icon,url,type,1 'has_permission' from sys_menu";
        } else {
            sql = "select a.menu_id,a.menu_name,a.parent_menu_id,a.menu_icon,a.url,a.type,if(b.role_id = '" + DB.e(roleId) + "','1','0') 'has_permission' " +
                    "from sys_menu a " +
                    "   left join sys_role_menu_rel b on a.menu_id = b.menu_id and b.role_id = '" + DB.e(roleId) + "'";
        }

        JSONList menus = DB.query(sql);
        menus = menus.listToTree("menu_id","parent_menu_id","child_menu",
                false,true,false);
        return JSONMap.success(menus);
    }
}
