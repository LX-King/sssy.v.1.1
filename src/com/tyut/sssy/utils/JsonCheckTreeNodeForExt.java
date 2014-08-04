package com.tyut.sssy.utils;

/**
 * ClassName:JsonCheckTreeNodeForExt
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-20
 * Time: 20:41:29
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class JsonCheckTreeNodeForExt {
    private String id;            //ID
    private String text;          //节点显示
    private String icon;           //图标
    private boolean leaf;         //是否叶子
    private String url;          //URL
    private String href;          //链接
    private boolean checked;  //是否选则
    private String hrefTarget;    //链接指向
    private boolean expandable;   //是否展开
    private String description;   //描述信息
    private int showOrder ;
    /*private int parentId ;*/

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }
}
