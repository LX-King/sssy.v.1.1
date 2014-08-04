package com.tyut.sssy.sysmgr.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import com.tyut.sssy.sysmgr.service.CategoryService;
import com.tyut.sssy.sysmgr.service.RoleService;
import com.tyut.sssy.sysmgr.domain.Role;
import com.tyut.sssy.sysmgr.domain.Category;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.*;

import net.sf.json.JSONArray;

/**
 * ClassName:MenuAction
 * Function:菜单类的Action
 * Author: LiuXiang
 * Date: 2012-3-28
 * Time: 21:15:29
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class RoleAction extends ActionSupport {

    private CategoryService categoryService = null;

    private RoleService roleService = null;

    private Role role;
    private File uploadIconImage;

    //为了Ext 文件上传组件 获取文件类型
    private String uploadIconImageContentType;
    //为了Ext 文件上传组件 获取文件名
    private String uploadIconImageFileName;


    /**
     * 构造方法
     */
    public RoleAction() {
        categoryService = new CategoryService();
        roleService = new RoleService();
    }


    /**
     * 显示用户添加界面
     *
     * @return String
     */
    public String showAddUI() {
        return "show_add_ui_succ";
    }

    /**
     * 添加
     */
    public void add() {
        String msg = null;
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleCode = roleService.getMaxId();
        Role r = new Role();
        String categorys = this.role.getCategorys();
        categorys = StringUtil.replaceBlank(categorys);
        r.setRoleCode(roleCode);
        r.setRoleName(this.role.getRoleName());
        r.setCategorys(categorys);

        boolean flag = roleService.add(r);
        if (flag)
            msg = "添加成功!";
        else
            msg = "添加失败!";

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }


        assert out != null;
        out.flush();
        out.close();
    }

    public void del() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String idArrs = request.getParameter("idArrs");
        String idArrsStr[] = idArrs.split(",");
        String msg = null;
        if (idArrsStr.length == 0)
            msg = "删除失败,参数不正确！";
        else {
            boolean flag = roleService.del(idArrsStr);

            if (flag) {
                msg = "删除成功!";
            } else {
                msg = "删除失败!";
            }
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }


        assert out != null;
        out.flush();
        out.close();

    }


    /**
     * 响应前端的AJAX访问，返回菜单分页结果
     */
    public void roleList() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;
        List<Role> list = new ArrayList<Role>();
        map = roleService.getAllRoles();
        int totalProperty = (Integer) map.get("totalProperty");

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;


        String root = JSONArray.fromObject(map.get("list")).toString();
        String jsonResult = "{totalProperty :" + totalProperty + ", root:" + root + "}";

        try {
            out = response.getWriter();
            out.write(jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    /**
     * 显示modify
     */
    public String showModify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String roleCode = request.getParameter("roleCode");
        Role role = roleService.getRoleById(roleCode);
        request.getSession().setAttribute(SessionAttributeKey.MODIFIED_ROLE, role);
        request.setAttribute("role", role);

        return "show_modify_ui_succ";

    }

    /**
     * 显示modify
     */
    public void modify() {
        String msg = null;
        boolean success = true;
        List<Role> roleList = new ArrayList<Role>();

        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String categoryId = this.role.getRoleCode();
        Role c = roleService.getRoleById(categoryId);
        if (this.role.getRoleName() != null && !this.role.getRoleName().equals(""))
            c.setRoleName(this.role.getRoleName());
        if (this.role.getCategorys() != null && !this.role.getCategorys().equals(""))
            c.setCategorys(StringUtil.replaceBlank(this.role.getCategorys()));


        boolean flag = roleService.modify(c);
        if (flag) {
            msg = "修改成功!";
        } else {
            success = false;
            msg = "修改失败!";
        }

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert out != null;
        out.flush();
        out.close();

    }


    public void showDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleCode = request.getParameter("roleCode");
        int parentId = Integer.parseInt(request.getParameter("parentId"));
        RoleService service = new RoleService();
        Role role = service.getRoleById(roleCode);
        List<Category> allowedCategorys = role.getCategroyList();
        List<JSONTreeNode> treeNodes = new ArrayList<JSONTreeNode>();
        for (Category category : allowedCategorys) {
            if (category.getParentId() == parentId) {
                JSONTreeNode treeNode = new JSONTreeNode();
                String categoryId = String.valueOf(category.getId());
                treeNode.setId(categoryId);
                treeNode.setText(category.getText());
                if (category.getLeaf().equals("n")) {
                    treeNode.setLeaf(false);
                } else {
                    treeNode.setLeaf(true);
                }
                treeNodes.add(treeNode);
            }
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;
        JSONArray JsonArray = JSONArray.fromObject(treeNodes); //得到JSON数组
        String jsonResult = JsonArray.toString();
        try {
            out = response.getWriter();

            out.print(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.flush();
        out.close();
    }

    /**
     * 响应前端的AJAX访问，返回菜单分页结果
     */
    public void showRoleList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;


        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        String roleName = request.getParameter("roleName");
        String menuCodesStr[] = request.getParameterValues("menuCodes[]");


        map = roleService.getRoleListByCon(pageSize, pageNo, roleName, menuCodesStr);
        int totalProperty = (Integer) map.get("totalProperty");

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;

        String root = JSONArray.fromObject(map.get("list")).toString();
        String jsonResult = "{\"total\":" + totalProperty + ", \"rows\":" + root + "}";

        try {
            out = response.getWriter();
            out.write(jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }


    /**
     * 系统左边菜单AJAX访问获取子节点
     */
    public void getJSONTree() {
        HttpServletRequest request = ServletActionContext.getRequest();
        int parentId = Integer.valueOf(request.getParameter("parentId"));

        if (parentId == -1) {
            JSONTreeNodeForEasyUI treeNode = new JSONTreeNodeForEasyUI();
            treeNode.setId("0");
            treeNode.setText("功能菜单");
            treeNode.setState("closed");
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out = null;
            JSONArray JsonArray = JSONArray.fromObject(treeNode); //得到JSON数组
            String jsonResult = JsonArray.toString();
            try {
                out = response.getWriter();

                out.print(jsonResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

            out.flush();
            out.close();

        } else {
            List<Category> categoryList = (List<Category>) categoryService.getByParentId(parentId);
            List<JSONTreeNodeForEasyUI> treeNodes = new ArrayList<JSONTreeNodeForEasyUI>();

            for (Category category : categoryList) {
                if (category.getParentId() == parentId) {
                    JSONTreeNodeForEasyUI treeNode = new JSONTreeNodeForEasyUI();
                    String categoryId = String.valueOf(category.getId());
                    treeNode.setId(categoryId);
                    treeNode.setText(category.getText());
                    treeNode.setSelected(true);
                    if (null != category.getIcon())
                        treeNode.setIconCls(category.getIcon());

                    /*if (parentIDString.indexOf("|" + categoryId + "|") > 0) {*/
                    if (category.getLeaf().equals("n")) {
                        treeNode.setState("closed");
                    } else {
                        treeNode.setState("open");
                    }
                    treeNodes.add(treeNode);
                }
            }


            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out = null;

            JSONArray JsonArray = JSONArray.fromObject(treeNodes); //得到JSON数组
            String jsonResult = JsonArray.toString();
            try {
                out = response.getWriter();

                out.print(jsonResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

            out.flush();
            out.close();
        }
    }

    /**
     * ext 获取菜单树
     */
    public void getJSONTreeForExt() {
        HttpServletRequest request = ServletActionContext.getRequest();
        int parentId = Integer.valueOf(request.getParameter("parentId"));
        List<Category> allCategorys = (List<Category>) categoryService.getByParentId(parentId);
        List<JsonCheckTreeNodeForExt> treeNodes = new ArrayList<JsonCheckTreeNodeForExt>();
        String method = request.getParameter("method");

        if (method.equals("modify")) {
            Role modifyRole = (Role) request.getSession().getAttribute(SessionAttributeKey.MODIFIED_ROLE);
            List<Category> allowedCategorys = modifyRole.getCategroyList();
            for (Category category : allCategorys) {
                JsonCheckTreeNodeForExt treeNode = new JsonCheckTreeNodeForExt();
                String categoryId = String.valueOf(category.getId());
                treeNode.setId(categoryId);
                treeNode.setText(category.getText());
                for (Category c : allowedCategorys) {
                    if (c.getId() == category.getId()) {
                        treeNode.setChecked(true);
                        break;
                    } else {
                        treeNode.setChecked(false);
                    }
                }
                if (category.getLeaf().equals("n")) {
                    treeNode.setLeaf(false);
                } else {
                    treeNode.setLeaf(true);
                }
                treeNodes.add(treeNode);
            }
        } else {
            for (Category category : allCategorys) {
                JsonCheckTreeNodeForExt treeNode = new JsonCheckTreeNodeForExt();
                String categoryId = String.valueOf(category.getId());
                treeNode.setId(categoryId);
                treeNode.setText(category.getText());
                treeNode.setChecked(false);
                if (category.getLeaf().equals("n")) {
                    treeNode.setLeaf(false);
                } else {
                    treeNode.setLeaf(true);
                }
                treeNodes.add(treeNode);
            }
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;

        JSONArray JsonArray = JSONArray.fromObject(treeNodes); //得到JSON数组
        String jsonResult = JsonArray.toString();
        try {
            out = response.getWriter();

            out.print(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.flush();
        out.close();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public File getUploadIconImage() {
        return uploadIconImage;
    }

    public void setUploadIconImage(File uploadIconImage) {
        this.uploadIconImage = uploadIconImage;
    }

    public String getUploadIconImageContentType() {
        return uploadIconImageContentType;
    }

    public void setUploadIconImageContentType(String uploadIconImageContentType) {
        this.uploadIconImageContentType = uploadIconImageContentType;
    }

    public String getUploadIconImageFileName() {
        return uploadIconImageFileName;
    }

    public void setUploadIconImageFileName(String uploadIconImageFileName) {
        this.uploadIconImageFileName = uploadIconImageFileName;
    }
}