package leaf.system.api;

import leaf.common.DB;
import leaf.common.mysql.Where;
import leaf.common.object.JSONList;
import leaf.common.object.JSONMap;
import leaf.common.util.Num;
import leaf.common.util.Valid;
import leaf.system.annotate.LoginToken;
import leaf.system.common.Http;
import leaf.system.common.SysUser;
import leaf.system.interceptor.ApiGlobalInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 系统配置模块
 */
@RestController
public class SysConfigApi {
    /**
     * 获取系统配置
     */
    @GetMapping("/system/api/systemConfig/getSystemConfig")
    public JSONMap getSystemConfig() {
        String configKeys = DB.e(Http.param("ConfigKeys"));
        JSONMap result = new JSONMap();
        int i;

        if(Valid.isEmpty(configKeys)) {
            return JSONMap.success(result);
        }

        String[] configKeyArr = configKeys.split(",");
        String configKeyStr = "",userRoleIdStr = SysUser.getUserRoleIdStr();

        for(i = 0;i < configKeyArr.length;i++) {
            if(i == configKeyArr.length - 1) {
                configKeyStr += "'"+DB.e(configKeyArr[i])+"'";
            } else {
                configKeyStr += "'"+DB.e(configKeyArr[i])+"',";
            }
        }

        String sql = "" +
                "select config_key,config_value,b.role_id " +
                "from sys_config a " +
                "   left join sys_config_role_rel b on a.config_id = b.config_id " +
                "where isnull(b.role_id)",sqlCondition = "";

        if(!Valid.isEmpty(userRoleIdStr)) {
            sqlCondition += "b.role_id in (" + userRoleIdStr + ")";
        }

        if(!configKeyStr.isEmpty()) {
            if(!Valid.isBlank(sqlCondition)) {
                sqlCondition += " and ";
            }

            sqlCondition += "config_key in (" + configKeyStr + ")";
        }

        if(!Valid.isBlank(sqlCondition)) {
            sql += "or (" + sqlCondition + ")";
        }

        JSONList configs = DB.query(sql);

        if(configs == null) {
            return JSONMap.error("获取系统配置失败");
        }

        for(i = 0;i < configs.size();i++) {
            result.put(configs.getMap(i).getString("config_key"),configs.getMap(i).getString("config_value"));
        }

        return JSONMap.success(result);
    }
    /**
     * 获取系统配置列表
     */
    @GetMapping("/system/api/systemConfig/getSystemConfigList")
    @LoginToken(validBackend = true,permissionKey = "PermissionKey-system_systemConfig_getSystemConfigList")
    public JSONMap getSystemConfigList() {
        String configId = Http.param("config_id");
        String configKey = Http.param("config_key");
        String configValue = Http.param("config_value");
        String configDesc = Http.param("config_desc");
        String configType = Http.param("config_type");
        String sortField = Http.param("SortField");
        String sortOrder = Http.param("SortOrder");
        String pageNo = Http.param("PageNo");
        String pageCount = Http.param("PageCount");

        String sql = "" +
                "select a.config_id,config_key,config_value,config_desc,config_type,c.role_id,c.role_name " +
                "from (" +
                "       select config_id,config_key,config_value,config_desc,config_type " +
                "       from sys_config ";

        if(Valid.isInteger(pageNo) && Valid.isInteger(pageCount)) {
            sql += "limit "+ Num.integer(pageNo).subtract(Num.integer("1")).multiply(Num.integer(pageCount))+","+pageCount+" ";
        } else {
            sql += "limit 100 ";
        }

        sql += "   ) a " +
                "   left join sys_config_role_rel b on a.config_id = b.config_id " +
                "   left join sys_role c on b.role_id = c.role_id ";
//        String relationship = SQLWhere.Like;
//
//        if("1".equals(Http.param("IsEqual","0"))) {
//            relationship = SQLWhere.Equal;
//        }
//        sql += new SQLWhere()
//                .addAnd("a.config_id", configId, relationship)
//                .addOr("a.config_key", configKey, relationship)
//                .addOr("a.config_value", configValue, relationship)
//                .addOr("config_desc", configDesc, relationship).addBracket("and")
//                .addAnd("ifnull(config_type,1)", configType, "=").toString();

        Where.Operator relationship = Where.Operator.LIKE;

        if("1".equals(Http.param("IsEqual","0"))) {
            relationship =  Where.Operator.EQ;
        }
        sql += new Where(true)
                .and().add("a.config_id", configId, Where.Operator.EQ)
                .or().add("a.config_key", configKey, relationship)
                .or().add("a.config_value", configValue, relationship)
                .or().add("config_desc", configDesc, relationship).group()
                .and().eq("ifnull(config_type,1)", configType).prependWhere().toString();

        //排序字段
        switch(sortField) {
            case "config_id":
            case "config_key":
                if(sortOrder.equals("asc")) sql += " order by a." + sortField + " asc ";
                else if(sortOrder.equals("desc")) sql += " order by a." + sortField+" desc ";
                else sql += " order by a.config_id desc ";
                break;
            default:
                sql += " order by a.config_id desc ";
        }

        JSONList configList = DB.query(sql);
        JSONList configData = new JSONList();
        JSONMap configTemp = new JSONMap();
        JSONMap config;
        JSONMap role;

        for(int i = 0;i < configList.size();i++) {
            String configId2 = configList.getMap(i).getString("config_id");
            config = configTemp.getMap(configId2);

            if(config == null) {
                config = new JSONMap();
                config.put("config_id",configList.getMap(i).get("config_id"));
                config.put("config_key",configList.getMap(i).get("config_key"));
                config.put("config_value",configList.getMap(i).get("config_value"));
                config.put("config_desc",configList.getMap(i).get("config_desc"));
                config.put("config_type",configList.getMap(i).get("config_type"));
                config.put("role_list",new JSONList());
                configData.add(config);
                configTemp.put(configId2,config);
            }

            if(configList.getMap(i).getString("role_id") != null) {
                role = new JSONMap();
                role.put("role_id",configList.getMap(i).get("role_id"));
                role.put("role_name",configList.getMap(i).get("role_name"));
                config.getList("role_list").add(role);
            }
        }

        String count = DB.queryFirstField("select count(1) 'count' from sys_config");

        if(count == null) {
            return JSONMap.error("获取记录条数失败");
        }

        JSONMap result = JSONMap.success(configData);
        result.put("dataCount",count);
        return result;
    }
    /**
     * 获取系统配置列表
     */
    @PostMapping("/system/api/systemConfig/updateSystemConfig")
    @LoginToken(validBackend = true)
    public JSONMap updateSystemConfig() {
        String updateType = Http.param("UpdateType");
        String configId = Http.param("config_id");
        String configKey = Http.param("config_key");
        String configValue = Http.param("config_value");
        String configDesc = Http.param("config_desc");
        String configType = Http.param("config_type");
        String roleIds = Http.param("role_id_arr");
        String[] roleIdArr = roleIds.split(",");

        switch(updateType) {
            case "Edit":
                if (!ApiGlobalInterceptor.permission("PermissionKey-system_systemConfig_updateSystemConfigEdit")) {
                    Http.write(403, JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                if (configId.isEmpty()) {
                    return JSONMap.error("配置代码不能为空");
                }

                if (configKey.isEmpty()) {
                    return JSONMap.error("配置键不能为空");
                }

                switch (configType) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                        break;
                    default:
                        return JSONMap.error("配置类型有误");
                }

                configId = DB.e(configId);
                String sql = "" +
                        "update sys_config " +
                        "set config_key = '" + DB.e(configKey) + "',config_value = '" + DB.e(configValue) + "',config_desc = '" + DB.e(configDesc) + "',config_type = '" + configType + "' " +
                        "where config_id = '" + configId + "';" +
                        "delete from sys_config_role_rel where config_id = '" + configId + "';";

                for (String roleId : roleIdArr) {
                    if (!roleId.isEmpty()) sql += "insert into sys_config_role_rel(config_id,role_id) value('" + configId + "','" + DB.e(roleId) + "');";
                }

                if (DB.updateTransaction(sql) > 0) {
                    return JSONMap.success();
                }
                break;
            case "Add":
                if(!ApiGlobalInterceptor.permission("PermissionKey-system_systemConfig_updateSystemConfigAdd")) {
                    Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                if (configKey.isEmpty()) {
                    return JSONMap.error("配置键不能为空");
                }

                if (!configType.equals("1") && !configType.equals("2") && !configType.equals("3")) {
                    return JSONMap.error("配置类型有误");
                }

                sql = "" +
                        "insert into sys_config(config_key,config_value,config_desc,config_type)" +
                        "value('"+DB.e(configKey)+"','"+DB.e(configValue)+"','"+DB.e(configDesc)+"','"+configType+"');" +
                        "select @new_config_id:=last_insert_id();";

                for(String roleId : roleIdArr) {
                    if(!roleId.isEmpty()) sql += "insert into sys_config_role_rel(config_id,role_id) value(@new_config_id,'"+DB.e(roleId)+"');";
                }

                if(DB.updateTransaction(sql) > 0) {
                    return JSONMap.success();
                }
                break;
            case "Delete":
                if(!ApiGlobalInterceptor.permission("PermissionKey-system_systemConfig_updateSystemConfigDelete")) {
                    Http.write(403,JSONMap.error("接口执行失败，该用户没有权限"));
                    return null;
                }

                if (configKey.isEmpty()) {
                    return JSONMap.error("配置键不能为空");
                }

                if(DB.update("delete from sys_config where config_id = '"+DB.e(configId)+"'") > 0) {
                    return JSONMap.success();
                }
                break;
            default:
                return JSONMap.error("修改类型有误");
        }
        return JSONMap.error("操作失败");
    }
}
