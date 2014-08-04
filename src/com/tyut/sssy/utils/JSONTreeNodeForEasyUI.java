package com.tyut.sssy.utils;

/**
 * ClassName:JSONTreeNode
 * Function:JSON格式返回的树形菜单结点
 * Author: LiuXiang
 * Date: 2012-3-17
 * Time: 10:02:02
 * Mail:LXiang.tyut@gmail.com
 * Company:China.Citic.Bank
 */
public class JSONTreeNodeForEasyUI {

    private String id;            //ID
    private String text;          //节点显示
    private String iconCls;           //图标
    private String state;         //是否叶子
    private String children ;
    private boolean checked ;
    private boolean selected ; //是否选择
    private int showOrder ;

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

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }
}