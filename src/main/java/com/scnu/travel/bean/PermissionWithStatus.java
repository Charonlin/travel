package com.scnu.travel.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 带有权限状态的角色
 * */
@Data
public class PermissionWithStatus {
    private Integer pid;
    private String permissionName; // 权限名
    private String permissionDesc;//权限详情
    private boolean rolesHas;//表示是否拥有改权限
}
