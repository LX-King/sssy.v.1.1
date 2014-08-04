package com.tyut.sssy.sysmgr.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.tyut.sssy.sysmgr.domain.Category;
import com.tyut.sssy.sysmgr.domain.Role;
import com.tyut.sssy.sysmgr.service.CategoryService;
import com.tyut.sssy.sysmgr.service.RoleService;
import com.tyut.sssy.utils.JSONTreeNodeForEasyUI;
import com.tyut.sssy.utils.JsonCheckTreeNodeForExt;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:MenuAction
 * Function:菜单类的Action
 * Author: LiuXiang
 * Date: 2012-3-28
 * Time: 21:15:29
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class MenuAction extends ActionSupport {

    private CategoryService categoryService = null;

    private RoleService roleService = null;

    private Category category;

    private File uploadIconImage;

    private String uploadIconImageContentType;
    private String uploadIconImageFileName;


    /**
     * 构造方法
     */
    public MenuAction() {
        categoryService = new CategoryService();
        roleService = new RoleService();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    /**
     * 修改
     */
    public void modify() {
        String msg = null;
        boolean success = true;
        List<Role> roleList = new ArrayList<Role>();

        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        int categoryId = this.category.getId();
        Category c = new Category();
        c.setId(categoryId);
        if (this.category.getHref() != null && !this.category.getHref().equals(""))
            c.setHref(this.category.getHref());
        if (this.category.getDesc() != null && !this.category.getDesc().equals(""))
            c.setDesc(this.category.getDesc());
        if (this.category.getLeaf() != null && !this.category.getLeaf().equals(""))
            c.setLeaf(this.category.getLeaf());
        if (this.category.getParentId() != -1)
            c.setParentId(this.category.getParentId());
        if (this.category.getText() != null && !this.category.getText().equals(""))
            c.setText(this.category.getText());
        if (this.category.getUrl() != null && !this.category.getUrl().equals(""))
            c.setUrl(this.category.getUrl());
        if (this.category.getDesc() != null && !this.category.getDesc().equals(""))
            c.setDesc(this.category.getDesc());
        if (this.category.getHrefTarget() != null && !this.category.getHrefTarget().equals(""))
            c.setHrefTarget(this.category.getHrefTarget());

        boolean flag = categoryService.modify(c);
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

    public String showAdd() {
        return "show_add_ui_succ";
    }

    /**
     * 添加
     */
    public void add() {
        String msg = null;
        boolean success = true;
        List<Role> roleList = new ArrayList<Role>();

        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String iconImagePath = ServletActionContext.getServletContext().getRealPath("/images");
        try {
            if (uploadIconImage != null) {

                File file = new File(iconImagePath, "\\" + uploadIconImageFileName);
                InputStream in = new FileInputStream(uploadIconImage);
                OutputStream out = new FileOutputStream(file);
                byte[] buffer = new byte[512];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            }
            Category c = new Category();
            int categoryId = categoryService.getMaxId() + 1;
            String categoryIdStr = new Integer(categoryId).toString();
            String icon = null;
            if (uploadIconImage != null)
                icon = "images/" + uploadIconImageFileName;
            c.setId(categoryId);
            c.setIcon(icon);
            c.setText(this.category.getText());
            c.setHrefTarget(this.category.getHrefTarget());
            c.setHref(this.category.getHref());
            c.setUrl(this.category.getUrl());
            c.setLeaf(this.category.getLeaf());
            c.setParentId(this.category.getParentId());
            c.setDesc(this.category.getDesc());

            boolean flag = categoryService.add(c);
            /*if (roleCodes != null) {
                if (roleCodes.indexOf(",") == -1) {
                    String roleCode = null;
                    Role role = roleService.getRoleById(roleCode);
                    role.setCategorys(role.getCategorys() + "," + categoryIdStr);
                    roleService.modify(role);
                } else {
                    String[] roleCodesArr = roleCodes.split(",");
                    for (String roleCode : roleCodesArr) {
                        Role role = roleService.getRoleById(roleCode.trim());
                        role.setCategorys(role.getCategorys() + "," + categoryIdStr);
                        roleService.modify(role);
                    }
                }
            }*/

            if (flag) {
                msg = "添加成功!";
            } else {
                success = false;
                msg = "添加失败!";
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void del() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String idArrs = request.getParameter("idArrs");
        String idArrsStr[] = idArrs.split(",");
        int idArr[] = new int[idArrsStr.length];
        for (int i = 0; i < idArrsStr.length; i++) {
            idArr[i] = Integer.parseInt(idArrsStr[i]);
        }
        boolean flag = categoryService.del(idArr);
        String msg = null;
        if (flag) {
            msg = "删除成功!";
        } else {
            msg = "删除失败!";
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
    public void showMenuList() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;


        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        String menuName = request.getParameter("menuName");
        String type = request.getParameter("menuType");
        String parentIdStr = request.getParameter("menuParentId");
        Integer parentId;
        if (parentIdStr == null || parentIdStr.equals(""))
            parentId = null;
        else
            parentId = new Integer(request.getParameter("menuParentId"));

        map = categoryService.getCategoryListByCon(pageSize, pageNo, menuName, type, parentId);
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
     * 显示modify
     */
    public String showModify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        int id = Integer.parseInt(request.getParameter("id"));
        category = categoryService.getById(id);
        String parentName = null;
        if (category.getParentId() == 0) {
            parentName = "功能菜单";
        } else {
            parentName = categoryService.getById(category.getParentId()).getText();
        }

        request.setAttribute("parentMenuName", parentName);
        request.setAttribute("categroy", category);
        return "show_modify_ui_succ";


    }

    /**
     * 响应前端的AJAX访问，返回菜单分页结果
     */
    public void roleList() {

        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;

        RoleService roleService = new RoleService();
        map = roleService.getAllRoles();
        List<Role.RoleMapping> list = new ArrayList<Role.RoleMapping>();

        int totalProperty = (Integer) map.get("totalProperty");
        for (Role role : (List<Role>) map.get("list")) {
            Role.RoleMapping roleMapping = new Role().new RoleMapping();
            roleMapping.setName("roleCodes");
            roleMapping.setRoleCode(role.getRoleCode());
            roleMapping.setRoleName(role.getRoleName());
            list.add(roleMapping);
        }

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;


        String jsonResult = JSONArray.fromObject(list).toString();
        try {
            out = response.getWriter();
            out.write(jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }


        assert out != null;
        out.flush();
        out.close();
    }

    public void getParentMenu() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        int parentId = Integer.valueOf(request.getParameter("parentId"));
        Category category = null;
        String res;
        if (parentId != 0) {
            category = categoryService.getById(parentId);
            res = category.getText();
        } else {
            res = "功能菜单";
        }


        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(res);
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
    public void list() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HashMap<String, String> conditions = new HashMap<String, String>();
        Map<String, Object> map = null;


        int pageSize = Integer.valueOf(request.getParameter("limit") == null ? "10" : request.getParameter("limit"));
        int pageNo = Integer.valueOf(request.getParameter("start") == null ? "1" : request.getParameter("start")) / pageSize + 1;
        String dir = request.getParameter("dir") == null ? "ASC" : request.getParameter("dir");        // 排序类型

        map = categoryService.getPagingList(pageNo, pageSize, dir, conditions);
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
            treeNode.setChecked(true);
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
            assert out != null;
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

                    if (null != category.getIcon())
                        treeNode.setIconCls(category.getIcon());

                    /*if (parentIDString.indexOf("|" + categoryId + "|") > 0) {*/
                    if (category.getLeaf().equals("n")) {
                        treeNode.setState("closed");
                    } else {
                        treeNode.setState("open");
                    }

                    if(category.getOrder() != 0 ){
                        treeNode.setShowOrder(category.getOrder());
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
     *
     */
    public void getJSONTreeForExt() {
        HttpServletRequest request = ServletActionContext.getRequest();
        int parentId = Integer.valueOf(request.getParameter("parentId"));
        List<Category> allCategorys = (List<Category>) categoryService.getByParentId(parentId);
        List<JsonCheckTreeNodeForExt> treeNodes = new ArrayList<JsonCheckTreeNodeForExt>();
        String method = request.getParameter("method");

        if (method.equals("modify")) {
            if (parentId == -1) {
                JsonCheckTreeNodeForExt treeNode = new JsonCheckTreeNodeForExt();
                int modifyId = Integer.parseInt(request.getParameter("modifyId"));
                Category c = categoryService.getById(modifyId);
                treeNode.setId("0");
                treeNode.setText("功能菜单");
                treeNode.setLeaf(false);
                if (c.getParentId() ==  0) {
                    treeNode.setChecked(true);
                } else {
                    treeNode.setChecked(false);
                }
                treeNode.setExpandable(true);
                treeNodes.add(treeNode);
            } else {
                int modifyId = Integer.parseInt(request.getParameter("modifyId"));
                Category c = categoryService.getById(modifyId);
                for (Category category : allCategorys) {
                    JsonCheckTreeNodeForExt treeNode = new JsonCheckTreeNodeForExt();
                    String categoryId = String.valueOf(category.getId());
                    treeNode.setId(categoryId);
                    treeNode.setText(category.getText());

                    /*treeNode.setParentId(category.getParentId());*/
                    if (c.getParentId() == category.getId()) {
                        treeNode.setChecked(true);
                    } else {
                        treeNode.setChecked(false);
                    }
                    if (category.getLeaf().equals("n")) {
                        treeNode.setLeaf(false);
                        treeNode.setExpandable(true);
                    } else {
                        treeNode.setLeaf(true);
                    }

                    treeNode.setShowOrder(category.getOrder());
                    treeNodes.add(treeNode);
                }
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
}

