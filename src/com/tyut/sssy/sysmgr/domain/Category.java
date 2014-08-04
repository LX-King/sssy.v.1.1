package com.tyut.sssy.sysmgr.domain;

import java.io.Serializable;

/**
 * ClassName:Category
 * Function:菜单
 * Author: LiuXiang
 * Date: 2012-3-21
 * Time: 9:29:23
 * Mail:LXiang.tyut@gmail.com
 * Company:和信至诚科技有限公司
 */
public class Category implements Serializable{

    private int id ; //主键

    private String text ; //菜单名称

    private String icon ; //图标

    private String url  ;// 链接

    private String href ; //链接

    private String hrefTarget ; //链接指向

    private String isLeaf ; //是否是叶子

    private String desc  ; //描述

    private int parentId ; //父节点ID

    private int order; //显示次序

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getHrefTarget() {
        return hrefTarget;
    }

    public void setHrefTarget(String hrefTarget) {
        this.hrefTarget = hrefTarget;
    }

    public String getLeaf() {
        return isLeaf;
    }

    public void setLeaf(String leaf) {
        isLeaf = leaf;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public class MenuMapping{

        int id ;

        String text ;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }


    }
}
