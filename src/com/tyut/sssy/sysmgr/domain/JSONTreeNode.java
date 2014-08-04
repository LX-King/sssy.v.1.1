package com.tyut.sssy.sysmgr.domain;

/**
 * ClassName:JSONTreeNode
 * Function:JSON格式返回的树形菜单结点
 * Author: LiuXiang
 * Date: 2012-3-17
 * Time: 10:02:02
 * Mail:LXiang.tyut@gmail.com
 * Company:China.Citic.Bank
 */
public class JSONTreeNode {

    private String id;            //ID
    private String text;          //节点显示
    private String icon;           //图标
    private boolean leaf;         //是否叶子
    private String url ;          //URL
    private String href;          //链接
    private String hrefTarget;    //链接指向
    private boolean expandable;   //是否展开
    private String description;   //描述信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
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

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
