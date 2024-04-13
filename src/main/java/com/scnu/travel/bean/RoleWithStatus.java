package com.scnu.travel.bean;

import lombok.Data;

/**
 * 带有状态的角色
 */
@Data
public class RoleWithStatus {
    private Integer rid;
    private String roleName;
    private String roleDesc;
    private Boolean adminHas;//用户是否拥有该角色
}
