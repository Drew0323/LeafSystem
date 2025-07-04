#  系统模块接口文档

[TOC]

### 1.系统模块

#### 1.1.上传 (POST)

接口地址:/system/api/upload

登录验证:无

**请求参数 Body**

| 字段      | 类型   | 必填 | 说明                     |
| --------- | ------ | ---- | ------------------------ |
| File      | 二进制 | 是   | 文件                     |
| IsOldName | number | 否   | 是否保留文件原名，默认 0 |

**成功响应 JSON**

```json
{
    "data": "文件路径",
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败响应 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 1.2.获取验证码 (GET)

接口地址:/system/api/getValidCode

登录验证:无

**请求参数 Body**

| 字段   | 类型   | 必填 | 说明         |
| ------ | ------ | ---- | ------------ |
| Length | number | 否   | 长度，默认 4 |

**成功返回值 JSON**

```json
{
    "data": {
        "img": "图片base64字符串",
        "valid_param": "验证参数"
    },
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 1.3.检查验证码 (GET)

接口地址:/system/api/checkValidCode

登录验证:无

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明     |
| ---------- | ------ | ---- | -------- |
| ValidParam | string | 是   | 验证参数 |
| Text       | string | 是   | 验证文本 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 1.4.获取拼图验证 (GET)

接口地址:/system/api/getValidPuzzle

登录验证:无

**请求参数**

无

**成功返回值 JSON**

```json
	{
    "daya": {
        "big_image": "大图base64字符串",
        "small_image": "小图base64字符串",
        "y": "小图纵坐标",
        "big_width": "大图宽度",
        "big_height": "大图高度",
        "small_width": "小图宽度",
        "small_height": "小图高度",
        "valid_param": "v3RdkpgE0KgRHqVwTvxEOh73gqptxWZAXytVOMSV84zSR44ERp0y3hKRzvqs/8nAA6aEnIGUeCEAUdEr01/rRA=="
    },
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 1.5.检查验证拼图 (GET)

接口地址:/system/api/checkValidPuzzle

登录验证:无

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明       |
| ---------- | ------ | ---- | ---------- |
| ValidParam | string | 是   | 验证参数   |
| X          | string | 是   | 验证横坐标 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

### 2.菜单模块

#### 2.1.获取菜单tree (GET)

接口地址:/system/api/menu/getMenuTree

登录验证:后台

**请求参数**

无

**成功返回值 JSON**

```json
{
    "data": [{
        "menu_id": "菜单代码",
        "menu_name": "菜单名称",
        "menu_icon": "菜单图标",
        "url": "url",
        "type": "类型",//1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件
        "child_menu": [{//子菜单
            "menu_name": "菜单名称",
            "menu_icon": "菜单图标",
            "url": "url",
            "type": "类型"//1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件
        }]
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 2.2.获取菜单列表 (GET)

接口地址:/system/api/menu/getMenuList

登录验证:后台

权限键:PermissionKey-system_menu_getMenuList

**请求参数 Body**

| 字段             | 类型   | 必填 | 说明                                                       |
| ---------------- | ------ | ---- | ---------------------------------------------------------- |
| menu_id          | number | 否   | 菜单代码                                                   |
| menu_name        | string | 否   | 菜单名称                                                   |
| parent_menu_id   | string | 否   | 父级菜单代码                                               |
| parent_menu_name | string | 否   | 父级菜单名称                                               |
| type_arr         | array  | 否   | 类型 1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件 |
| is_show          | number | 否   | 是否显示                                                   |
| IsEqual          | number | 否   | 是否等于，默认 0                                           |
| SortField        | string | 否   | 排序字段                                                   |
| SortOrder        | string | 否   | 排序顺序 asc:升序;desc:降序                                |
| PageNo           | number | 否   | 页号                                                       |
| PageCount        | number | 否   | 页最大数                                                   |

**成功返回值 JSON**

```json
{
    "data": [{
        "menu_id": "菜单代码",
        "menu_name": "菜单名称",
        "parent_menu_id": "父级菜单代码",
        "parent_menu_name": "父级菜单名称",
        "menu_icon": "菜单图标",
        "url": "url",
        "type": "类型",//1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件
        "permission_key": "权限键",
        "is_show": "是否显示",
        "sort": "排序"
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 2.3.修改菜单 (POST)

接口地址:/system/api/menu/updateMenu

登录验证:后台

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明                                                         |
| ---------- | ------ | ---- | ------------------------------------------------------------ |
| UpdateType | string | 是   | 修改类型 Sort:排序;IsShow:是否显示;Edit:编辑;Add:增加;Delete:删除 |

- UpdateType=Edit
  - 权限键:PermissionKey-system_menu_updateMenuEdit

| 字段           | 类型   | 必填 | 说明                                                       |
| -------------- | ------ | ---- | ---------------------------------------------------------- |
| menu_id        | number | 是   | 菜单代码                                                   |
| menu_name      | string | 是   | 菜单名称                                                   |
| parent_menu_id | number | 否   | 父级菜单代码                                               |
| menu_icon      | string | 否   | 菜单图标                                                   |
| url            | string | 否   | url                                                        |
| type           | number | 是   | 类型 1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件 |
| permission_key | string | 否   | 权限键                                                     |
| is_show        | number | 是   | 是否显示                                                   |

- UpdateType=Add
  - 权限键:PermissionKey-system_menu_updateMenuAdd

| 字段           | 类型   | 必填 | 说明                                                       |
| -------------- | ------ | ---- | ---------------------------------------------------------- |
| menu_name      | string | 是   | 菜单名称                                                   |
| parent_menu_id | number | 否   | 父级菜单代码                                               |
| menu_icon      | string | 否   | 菜单图标                                                   |
| url            | string | 否   | url                                                        |
| type           | number | 是   | 类型 1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件 |
| permission_key | string | 否   | 权限键                                                     |
| is_show        | number | 是   | 是否显示                                                   |

**成功返回值 JSON**

```json
{
  	"data": {
        "new_menu_id": "新菜单代码"
    }
}
```

- UpdateType=Delete
  - 权限键:PermissionKey-system_menu_updateMenuDelete

| 字段    | 类型   | 必填 | 说明     |
| ------- | ------ | ---- | -------- |
| menu_id | number | 是   | 菜单代码 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

- UpdateType=Sort
  - 权限键:PermissionKey-system_menu_updateMenuSort

| 字段     | 类型   | 必填 | 说明     |
| -------- | ------ | ---- | -------- |
| menu_id  | number | 是   | 菜单代码 |
| new_sort | number | 是   | 新排序   |

- UpdateType=IsShow
  - 权限键:PermissionKey-system_menu_updateMenuIsShow

| 字段    | 类型   | 必填 | 说明     |
| ------- | ------ | ---- | -------- |
| menu_id | number | 是   | 菜单代码 |
| is_show | number | 是   | 是否显示 |

### 3.角色模块

#### 3.1.获取角色列表 (GET)

接口地址:/system/api/role/getRoleList

登录验证:后台

权限键:PermissionKey-system_role_getRoleList

**请求参数 Body**

| 字段                   | 类型   | 必填 | 说明                        |
| ---------------------- | ------ | ---- | --------------------------- |
| role_id                | string | 否   | 角色代码                    |
| role_name              | string | 否   | 角色名称                    |
| is_allow_login_backend | number | 否   | 是否允许登录后台            |
| is_disable             | number | 否   | 是否禁用                    |
| IsEqual                | number | 否   | 是否等于，默认 0            |
| SortField              | string | 否   | 排序字段                    |
| SortOrder              | string | 否   | 排序顺序 asc:升序;desc:降序 |
| PageNo                 | number | 否   | 页号                        |
| PageCount              | number | 否   | 页最大数                    |

**成功返回值 JSON**

```json
{
    "data": [{
        "role_id": "角色代码",
		"role_name": "角色名称",
		"is_allow_login_backend": "是否允许登录后台",
        "is_disable": "是否禁用",
	}],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 3.2.修改角色 (POST)

接口地址:/system/api/role/updateRole

登录验证:后台

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明                                                         |
| ---------- | ------ | ---- | ------------------------------------------------------------ |
| UpdateType | string | 是   | 修改类型 IsAllowLoginBackend:是否允许登录后台;IsDisable:是否禁用;Edit:编辑;Add:增加;Delete:删除 |

- UpdateType=Edit
  - 权限键:PermissionKey-system_role_updateRoleEdit

| 字段                   | 类型   | 必填 | 说明             |
| ---------------------- | ------ | ---- | ---------------- |
| role_id                | number | 是   | 角色代码         |
| role_name              | string | 是   | 角色名称         |
| is_allow_login_backend | number | 否   | 是否允许登录后台 |
| is_disable             | number | 否   | 是否禁用         |

- UpdateType=Add
  - 权限键:PermissionKey-system_role_updateRoleAdd

| 字段                   | 类型   | 必填 | 说明             |
| ---------------------- | ------ | ---- | ---------------- |
| role_name              | string | 是   | 角色名称         |
| is_allow_login_backend | number | 否   | 是否允许登录后台 |
| is_disable             | number | 否   | 是否禁用         |

- UpdateType=Delete
  - 权限键:PermissionKey-system_role_updateRoleDelete

| 字段    | 类型   | 必填 | 说明     |
| ------- | ------ | ---- | -------- |
| role_id | number | 是   | 角色代码 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

- UpdateType=IsAllowLoginBackend
  - 权限键:PermissionKey-system_role_updateRoleIsAllowLoginBackend

| 字段                   | 类型   | 必填 | 说明             |
| ---------------------- | ------ | ---- | ---------------- |
| role_id                | number | 是   | 角色代码         |
| is_allow_login_backend | number | 否   | 是否运行登录后台 |

- UpdateType=IsDisable
  - 权限键:PermissionKey-system_role_updateRoleIsDisable

| 字段       | 类型   | 必填 | 说明              |
| ---------- | ------ | ---- | ----------------- |
| role_id    | number | 是   | 角色代码          |
| is_disable | number | 否   | 是否禁用（默认0） |

#### 3.3.获取角色菜单列表 (GET)

接口地址:/system/api/role/getRoleMenuList

登录验证:后台

权限键:PermissionKey-system_role_getRoleMenuList

**请求参数 Body**

| 字段    | 类型   | 必填 | 说明                    |
| ------- | ------ | ---- | ----------------------- |
| role_id | string | 是   | 角色代码（为1显示全部） |

**成功返回值 JSON**

```json
{
    "data": [{
        "menu_id": "菜单代码",
        "menu_name": "菜单名称",
        "parent_menu_id": "父级菜单代码",
        "menu_icon": "菜单图标",
        "url": "url",
        "type": "类型",//1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件
        “has_permission”: "是否有权限"
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 3.4.修改角色菜单 (POST)

接口地址:/system/api/role/updateRoleMenu

登录验证:后台

权限键:PermissionKey-system_role_updateRoleMenu

**请求参数 JSON**

| 字段        | 类型   | 必填 | 说明         |
| ----------- | ------ | ---- | ------------ |
| role_id     | string | 是   | 角色代码     |
| menu_id_arr | array  | 否   | 菜单代码数组 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 3.5.获取角色菜单权限键列表 (GET)

接口地址:/system/api/role/getRoleMenuPermissionKeyList

登录验证:无

**请求参数 Body**

| 字段           | 类型   | 必填 | 说明                                                       |
| -------------- | ------ | ---- | ---------------------------------------------------------- |
| menu_id        | number | 否   | 菜单代码                                                   |
| parent_menu_id | string | 否   | 父级菜单代码                                               |
| type           | number | 否   | 类型 1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件 |

**成功返回值 JSON**

```json
{
    "data": [{
        "menu_id": "菜单代码",
        "permission_key": "权限键"
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### (弃用)获取角色菜单tree (GET)

接口地址:/system/api/role/getRoleMenuTree

登录验证:后台

**请求参数 Body**

| 字段    | 类型   | 必填 | 说明                    |
| ------- | ------ | ---- | ----------------------- |
| role_id | string | 是   | 角色代码（为1显示全部） |

**成功返回值 JSON**

```json
{
    "data": [{
        "menu_id": "菜单代码",
        "menu_name": "菜单名称",
        "menu_icon": "菜单图标",
        "url": "url",
        "type": "类型",//1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件
        “has_permission”: "是否有权限",
        "child_menu": [{//子菜单
            "menu_name": "菜单名称",
            "menu_icon": "菜单图标",
            "url": "url",
            "type": "类型"//1:菜单目录;2:菜单项;3:iframe;4:此页面;5:新页面;6:控件
            “has_permission”: "是否有权限",
        }]
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

### 4.用户模块

#### 4.1.登录 (POST)

接口地址:/system/api/user/login

登录验证:无

**请求参数 Body**

| 字段              | 类型   | 必填         | 说明                     |
| ----------------- | ------ | ------------ | ------------------------ |
| account           | string | 是           | 账号                     |
| password          | string | 是           | 密码                     |
| ValidParam        | string | 后台用户必填 | 验证参数                 |
| Text              | string | 后台用户必填 | 验证文本                 |
| IsBackend         | string | 否           | 是否是后台用户，默认 0   |
| IsSaveLoginStatus | string | 否           | 是否保存登录状态，默认 0 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 4.2.退出登录 (GET)

接口地址:/system/api/user/logout

登录验证:无

**请求参数 Body**

| 字段      | 类型   | 必填 | 说明                   |
| --------- | ------ | ---- | ---------------------- |
| IsBackend | string | 否   | 是否是后台用户，默认 0 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

#### 4.3.是否登录 (GET)

接口地址:/system/api/user/isLogin

登录验证:无

**请求参数 Body**

| 字段      | 类型   | 必填 | 说明                   |
| --------- | ------ | ---- | ---------------------- |
| IsBackend | string | 否   | 是否是后台用户，默认 0 |

**成功返回值 JSON**

```json
{
    "data": {
		"name": "名称",
		"account": "账号",
		"personal_signature": "个性签名",
		"avatar": "头像",
		"phone": "手机号",
		"email": "电子邮箱",
		"sex": "性别"//1:男;2:女
	},
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"//未登录
}
```

#### 4.4.获取用户列表 (GET)

接口地址:/system/api/user/getUserList

登录验证:后台

权限键:lspk:system:user:list

**请求参数 Body**

| 字段        | 类型   | 必填 | 说明                        |
| ----------- | ------ | ---- | --------------------------- |
| user_id     | string | 否   | 用户代码                    |
| name        | string | 否   | 名称                        |
| account     | string | 否   | 账号                        |
| phone       | string | 否   | 手机号                      |
| email       | string | 否   | 电子邮箱                    |
| real_name   | string | 否   | 真实姓名                    |
| idcard      | string | 否   | 身份证号                    |
| sex         | array  | 否   | 性别 1:男;2:女;3:未知       |
| is_disable  | number | 否   | 是否禁用                    |
| login_ip    | string | 否   | 登录IP                      |
| role_id_arr | array  | 否   | 角色代码数组                |
| IsEqual     | number | 否   | 是否等于，默认 0            |
| SortField   | string | 否   | 排序字段                    |
| SortOrder   | string | 否   | 排序顺序 asc:升序;desc:降序 |
| PageNo      | number | 否   | 页号                        |
| PageCount   | number | 否   | 页最大数                    |

**成功返回值 JSON**

```json
{
    "data": [{
        "user_id": "用户代码",
		"name": "名称",
		"account": "账号",
        "password": "密码",
		"personal_signature": "个性签名",
		"avatar": "头像",
        "birthday": "生日",
		"phone": "手机号",
		"email": "电子邮箱",
        "real_name": "真实姓名",
    	"idcard": "身份证号",
		"sex": "性别",//1:男;2:女
        "is_disable": "是否禁用",
        "login_ip": "登录IP",
        "login_time": "登录时间",
        "role_list": [{//角色列表
            role_id: "角色代码",
            role_name: "角色名称"
        }]
	}],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 4.5.修改用户 (POST) 

接口地址:/system/api/user/updateUser

登录验证:后台

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明                                                       |
| ---------- | ------ | ---- | ---------------------------------------------------------- |
| UpdateType | string | 是   | 修改类型 IsDisable:是否禁用;Edit:编辑;Add:增加;Delete:删除 |

- UpdateType=Edit
  - 权限键:lspk:system:user:edit

| 字段               | 类型     | 必填 | 说明              |
| ------------------ | -------- | ---- | ----------------- |
| user_id            | number   | 是   | 用户代码          |
| name               | string   | 是   | 名称              |
| account            | string   | 是   | 账号              |
| password           | string   | 是   | 密码              |
| personal_signature | string   | 否   | 个性签名          |
| avatar             | string   | 否   | 头像              |
| birthday           | date     | 否   | 生日              |
| phone              | string   | 否   | 手机号            |
| email              | string   | 否   | 电子邮箱          |
| real_name          | string   | 否   | 真实姓名          |
| idcard             | string   | 否   | 身份证号          |
| sex                | number   | 否   | 性别              |
| is_disable         | number   | 否   | 是否禁用（默认0） |
| login_time         | datetime | 否   | 登录时间          |
| role_id_arr        | array    | 否   | 角色代码数组      |

- UpdateType=Add
  - 权限键:lspk:system:user:add

| 字段               | 类型   | 必填 | 说明         |
| ------------------ | ------ | ---- | ------------ |
| name               | string | 是   | 名称         |
| account            | string | 是   | 账号         |
| password           | string | 是   | 密码         |
| personal_signature | string | 否   | 个性签名     |
| avatar             | string | 否   | 头像         |
| birthday           | date   | 否   | 生日         |
| phone              | string | 否   | 手机号       |
| email              | string | 否   | 电子邮箱     |
| real_name          | string | 否   | 真实姓名     |
| idcard             | string | 否   | 身份证号     |
| sex                | number | 否   | 性别         |
| is_disable         | number | 否   | 是否禁用     |
| role_id_arr        | array  | 否   | 角色代码数组 |

- UpdateType=Delete
  - 权限键:lspk:system:user:delete

| 字段    | 类型   | 必填 | 说明     |
| ------- | ------ | ---- | -------- |
| user_id | number | 是   | 用户代码 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

- UpdateType=IsDisable
  - 权限键:lspk:system:user:edit:isDisable

| 字段       | 类型   | 必填 | 说明     |
| ---------- | ------ | ---- | -------- |
| user_id    | number | 是   | 用户代码 |
| is_disable | number | 否   | 是否禁用 |

#### 4.6.批量修改用户 (POST) 

接口地址:/system/api/user/batchUpdateUser

登录验证:后台

**请求参数 Body**

| 字段        | 类型   | 必填 | 说明                                             |
| ----------- | ------ | ---- | ------------------------------------------------ |
| UpdateType  | string | 是   | 修改类型 IsDisable:是否禁用;Sex:性别;Delete:删除 |
| user_id_arr | array  | 是   | 用户代码数组                                     |

- UpdateType=IsDisable
  - 权限键:lspk:system:user:edit:isDisable

| 字段       | 类型   | 必填 | 说明     |
| ---------- | ------ | ---- | -------- |
| is_disable | number | 否   | 是否禁用 |

### 5.系统配置模块

#### 5.1.获取系统配置 (GET)

接口地址:/system/api/systemConfig/getSystemConfig

登录验证:配置角色关系（如果没有则表示）

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明                       |
| ---------- | ------ | ---- | -------------------------- |
| ConfigKeys | string | 否   | 配置键（多个使用逗号分隔） |

**成功返回值 JSON**

```json
{
    "data": {
        "配置键": "配置值"
    },
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 5.2.获取系统配置列表 (GET)

接口地址:/system/api/systemConfig/getSystemConfigList

登录验证:后台

权限键:PermissionKey-system_systemConfig_getSystemConfigList

**请求参数 Body**

| 字段         | 类型   | 必填 | 说明                                                   |
| ------------ | ------ | ---- | ------------------------------------------------------ |
| config_id    | number | 否   | 配置代码                                               |
| config_key   | string | 否   | 配置键                                                 |
| config_value | string | 否   | 配置值                                                 |
| config_desc  | string | 否   | 配置描述                                               |
| config_type  | number | 否   | 配置类型 1:文本;2:图片;3:文件;4:开关;5:日期;6:日期时间 |
| IsEqual      | number | 否   | 是否等于，默认 0                                       |
| SortField    | string | 否   | 排序字段                                               |
| SortOrder    | string | 否   | 排序顺序 asc:升序;desc:降序                            |
| PageNo       | number | 否   | 页号                                                   |
| PageCount    | number | 否   | 页最大数                                               |

**成功返回值 JSON**

```json
{
    "data": [{
        "config_id": "配置代码",
        "config_key": "配置键",
        "config_value": "配置值",
        "config_desc": "配置描述",
        "config_type": "配置类型",
        "role_list": [{//角色列表
            role_id: "角色代码",
            role_name: "角色名称"
        }]
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 5.3.修改系统配置 (POST)

接口地址:/system/api/systemConfig/updateSystemConfig

登录验证:后台

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明                                    |
| ---------- | ------ | ---- | --------------------------------------- |
| UpdateType | string | 是   | 修改类型 Edit:编辑;Add:增加;Delete:删除 |

- UpdateType=Edit
  - 权限键:PermissionKey-system_systemConfig_updateSystemConfigEdit

| 字段         | 类型   | 必填 | 说明         |
| ------------ | ------ | ---- | ------------ |
| config_id    | number | 是   | 配置代码     |
| config_key   | string | 是   | 配置键       |
| config_value | string | 否   | 配置值       |
| config_desc  | string | 否   | 配置描述     |
| config_type  | number | 是   | 配置类型     |
| role_id_arr  | array  | 否   | 角色代码数组 |

- UpdateType=Add
  - 权限键:PermissionKey-system_systemConfig_updateSystemConfigAdd

| 字段         | 类型   | 必填 | 说明         |
| ------------ | ------ | ---- | ------------ |
| config_key   | string | 是   | 配置键       |
| config_value | string | 否   | 配置值       |
| config_desc  | string | 否   | 配置描述     |
| config_type  | number | 是   | 配置类型     |
| role_id_arr  | array  | 否   | 角色代码数组 |

- UpdateType=Delete
  - 权限键:PermissionKey-system_systemConfig_updateSystemConfigDelete

| 字段      | 类型   | 必填 | 说明     |
| --------- | ------ | ---- | -------- |
| config_id | number | 是   | 配置代码 |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

### 6.日志模块

#### 6.1.获取日志 (GET) 

接口地址:/system/api/log/getLog

登录验证:后台

权限键:lyfsysPermissionKey-system_log_getLog

**请求参数**

无

**成功返回值 JSON**

```json
{
    "data": [{
        "type": "dir",//类型为文件夹
        "name": "年",
        "children": [{
            "type": "dir",//类型为文件夹
        	"name": "月",
            "children": [{
                "type": "dir",//类型为文件夹
                "name": "日",
                "children": [{
                    "type": "file",//类型为文件
                    "name": "日志",
                    "log_url": "日志url"
                }]
            }]
        }]
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 6.2.修改日志 (GET)

接口地址:/system/api/log/updateLog

登录验证:后台

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明                               |
| ---------- | ------ | ---- | ---------------------------------- |
| UpdateType | string | 是   | 修改类型 Rename:重命名;Delete:删除 |
| log_url    | string | 是   | 日志路径                           |

- UpdateType=Rename
  - 权限键:lyfsysPermissionKey-system_log_updateLogRename

| 字段     | 类型   | 必填 | 说明   |
| -------- | ------ | ---- | ------ |
| new_name | string | 是   | 新名称 |

**成功返回值 JSON**

```json
{
    "log_new_url": "日志新路径",
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

- UpdateType=Delete
  - 权限键:lyfsysPermissionKey-system_log_updateLogDelete

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

### 7.服务器模块

#### 7.1.获取服务器信息 (GET)

接口地址:/system/api/server/getServerInfo

登录验证:后台

权限键:lyfsysPermissionKey-system_server_getServerInfo

**请求参数 Body**

| 字段            | 类型   | 必填 | 说明           |
| --------------- | ------ | ---- | -------------- |
| IsRepeatRequest | string | 否   | 是否是重复请求 |

**成功返回值 JSON**

```json
{
    "data": {
        //服务器信息(IsRepeatRequest=0)
        "server_info": {
            "server_name": "服务名称",
        	"server_ip": "服务IP",
            "operate_system": "操作系统",
            "system_architecture": "系统架构"
        },
        //Java虚拟机信息(IsRepeatRequest=0)
    	"java_vm_info": {
            "java_name": "Java名称",
        	"java_version": "Java版本",
            "java_install_path": "Java安装路径",
            "project_path": "项目路径"
        },
        //CPU信息
        "cpu_info": {
            "cpu_core_num": "cpu核心数",
            "cpu_load_rate": "cpu负载率"
        },
        //内存信息
        "memory_info": {
            "total_memory": "总内存",
            "used_memory": "已用内存",
            "free_memory": "内存",
            "use_rate": "使用率",
            //jvm内存信息
            "jvm_memory_info": {
                "max_memory": "最大内存",
                "total_memory": "总内存",
                "used_memory": "已用内存",
                "free_memory": "内存",
                "use_rate": "使用率"
            }
        },
    	//磁盘信息列表(IsRepeatRequest=0)
    	"disk_info_list": [{
            "disk_path": "盘符路径",
            "file_system": "文件系统",
            "total_size": "总大小",
            "used_size": "已用大小",
            "remain_size": "剩余大小",
            "used_percentage": "已用百分比",
        }],
	},
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 7.2.获取缓存信息 (GET)

接口地址:/system/api/server/getCacheInfo

登录验证:后台

权限键:lyfsysPermissionKey-system_server_getCacheInfo

**请求参数**

无

**成功返回值 JSON**

```json
{
    "data": [{
    	"缓存键": "缓存值"
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

#### 7.4.删除缓存 (GET)

接口地址:/system/api/server/deleteCache

登录验证:后台

权限键:lyfsysPermissionKey-system_server_deleteCache

**请求参数 Body**

| 字段        | 类型   | 必填 | 说明         |
| ----------- | ------ | ---- | ------------ |
| IsDeleteAll | string | 否   | 是否删除全部 |
| cache_key   | string | 否   | 缓存键       |

**成功返回值 JSON**

```json
{
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

### 8.代码生成模块（待修改）

#### 8.1.获取数据库表格信息 (GET)

接口地址:/system/api/codeGeneration/getDBTableInfo

验证:后台

权限键:lyfsysPermissionKey-system_codeGeneration_getDBTableInfo

**请求参数 Body**

| 字段          | 类型   | 必填 | 说明                        |
| ------------- | ------ | ---- | --------------------------- |
| table_name    | string | 否   | 表名称                      |
| table_comment | string | 否   | 表的注释、备注              |
| IsEqual       | number | 否   | 是否等于，默认 0            |
| SortField     | string | 否   | 排序字段                    |
| SortOrder     | string | 否   | 排序顺序 asc:升序;desc:降序 |
| PageNo        | number | 否   | 页号                        |
| PageCount     | number | 否   | 页最大数                    |

**成功返回值 JSON**

```json
{
    "data": [{
    	"table_name": "表名称",
        "table_comment": "表的注释、备注",
        "table_schema": "数据表所属的数据库名",
        "create_time": "表的创建时间",
        "update_time": "表的更新时间",
        "table_rows": "表里所存多少行数据",
        "auto_increment": "做自增主键的自动增量当前值"
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 8.2.获取数据库表格字段信息 (GET)

接口地址:/system/api/codeGeneration/getDBTableFieldInfo

验证:后台

权限键:lyfsysPermissionKey-system_codeGeneration_getDBTableFieldInfo

**请求参数 Body**

| 字段       | 类型   | 必填 | 说明   |
| ---------- | ------ | ---- | ------ |
| table_name | string | 否   | 表名称 |

**成功返回值 JSON**

```json
{
    "data": [{
    	"column_name": "字段名称",
        "column_comment": "字段的注释、备注",
        "column_type": "字段类型",
        "is_nullable": "能否为空",
        "column_default": "字段默认值",
        "column_key": "字段键",
        "ControlType": "控件类型"
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

#### 8.3.生成代码 (POST)

接口地址:/system/api/codeGeneration/generationCode

验证:后台

权限键:lyfsysPermissionKey-system_codeGeneration_generationCode

**请求参数 JSON**

```JSON
{
    "table_name": "表名",//不能为空
    "table_desc": "表描述",//不能为空
    "module_name": "模块名",//不能为空
    "api_root_address": "接口根地址",//不能为空
    "primary_key_column_name": "主键字段名",//不能为空
    "column_info_list": [{
        "column_name": "字段名称",//不能为空
        //前后端
        "search": "搜索",//默认0
        "sort": "排序",//默认0
        "not_null": "必填",//默认0
        "edit": "编辑",//默认1
        //后端
        "select": "select",//默认1
        //前端
        "title": "标题",//默认等于字段名称
        "visible": "显示",//默认1
        "align": "对齐"//默认center
    }]
}
```

**成功返回值 JSON**

```json
{
    "data": [{
    	"filename": "文件名称",
        "code_language": "代码语言",
        "code_content": "代码内容"
    }],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```

### 9.定时任务模块

#### 9.1.获取定时任务 (GET) 待开发

<span style="color:red">TODO 在common里面添加禁用功能</span>

接口地址:/system/api/timeJob/getTimeJob

登录验证:后台

权限键:

**请求参数 Body**

| 字段      | 类型   | 必填 | 说明     |
| --------- | ------ | ---- | -------- |
| taskGroup | string | 否   | 任务分组 |

**成功返回值 JSON**

```json
{
    "data": [
        {
            "taskName": "任务名称(uuid)",
            "taskGroup": "SystemTask",//任务分组
         	"cron": "cron表达式",
 			"taskDesc": "任务描述",
            "taskType": "任务类型",
            "methodPath": "方法路径",
            "url": "接口URL",
            "method": "请求方法",
            "contentType": "contentType",
            "param": "请求参数"
        }
    ],
  	"IsSuccess": "1"//是否调用成功 0:否;1:是
}
```

**失败返回值 JSON**

```json
{
  	"IsSuccess": "0",//是否调用成功 0:否;1:是
  	"Msg": "信息"
}
```
