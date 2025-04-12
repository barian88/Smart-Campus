package com.ikun.backend.utils.pageUtil.children;

import com.ikun.backend.utils.pageUtil.PageRequest;

public class UserPageRequest extends PageRequest {
//    角色id
    int roleId;
//  真实姓名
    String realName;
//    学校工号
    String schoolJobId;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSchoolJobId() {
        return schoolJobId;
    }

    public void setSchoolJobId(String schoolJobId) {
        this.schoolJobId = schoolJobId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
