package com.tyut.sssy.sysmgr.domain;

import java.util.List;

/**
 * ClassName:Role
 * Function:角色
 * Author: LiuXiang
 * Date: 2012-3-21
 * Time: 9:32:06
 * Mail:LXiang.tyut@gmail.com
 * Company:和信至诚科技有限公司
 */
public class Role {

    private String roleCode ; //角色代码

    private String roleName ; //角色名称

    private String categorys;

    private List<Category> categroyList ;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public List<Category> getCategroyList() {
        return categroyList;
    }

    public void setCategroyList(List<Category> categroyList) {
        this.categroyList = categroyList;
    }

    
    public class RoleMapping{
        private String name ;
        private String roleCode ;
        private String roleName ;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }
}
