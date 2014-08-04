package com.tyut.sssy.sysmgr.domain;

import com.tyut.sssy.base.domain.TaxUnit;

public class User {

    private int id ;  //主键

    private String code ; //操作人员登录代码

    private String swjgdm;//虽无机关代码

    private TaxUnit taxUnit;
    
    private String name; //名称

    private String password ; //密码

    private String roleCode ; //角色代码

    private String isExpired ; //有效标志


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(String expired) {
        isExpired = expired;
    }

    public String getSwjgdm() {
        return swjgdm;
    }

    public void setSwjgdm(String swjgdm) {
        this.swjgdm = swjgdm;
    }

    public TaxUnit getTaxUnit() {
        return taxUnit;
    }

    public void setTaxUnit(TaxUnit taxUnit) {
        this.taxUnit = taxUnit;
    }
    
}
