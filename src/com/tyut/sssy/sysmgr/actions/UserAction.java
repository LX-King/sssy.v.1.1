package com.tyut.sssy.sysmgr.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tyut.sssy.base.domain.LoginControl;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.LoginControlService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.sysmgr.domain.Category;
import com.tyut.sssy.sysmgr.domain.Role;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.RoleService;
import com.tyut.sssy.sysmgr.service.SchoolService;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.utils.JSONTreeNode;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * ClassName:UserAction
 * Function:登陆类的Action
 * Author: LiuXiang
 * Date: 2012-3-28
 * Time: 21:15:29
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */

public class UserAction extends ActionSupport {

    private User user;

    private RoleService roleService;

    public UserAction() {
        roleService = new RoleService();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private boolean success;

    private String msg;

    @JSON(name = "success")
    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JSON(name = "msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 显示用户添加界面
     *
     * @return String
     */
    public String showAddUI() {
        TaxUnitService taxUnitService = new TaxUnitService();
        List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
        ActionContext.getContext().put("taxUnitList", taxUnitList);
        return "show_add_ui_succ";
    }

    /**
     * 添加用户
     */
    public void add() {
        String msg = null;
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleCode = roleService.getMaxId();
        UserService userService = new UserService();
        boolean flag = userService.add(this.user);

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        if (flag)
            msg = "添加成功!";
        else
            msg = "添加失败!";
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
     * 显示用户列表界面
     *
     * @return void
     */
    public void showUserList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;
        UserService userService = new UserService();
        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));

        String name = request.getParameter("userName");
        String roleCode = request.getParameter("roleCode");
        map = userService.getPageListByCon(pageNo, pageSize, name, roleCode);
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
     *
     */
    public void getRoles() {
        StringBuffer res = new StringBuffer();
        RoleService roleService = new RoleService();
        List<Role> roleList = (List<Role>) roleService.getAllRoles().get("list");
        Iterator iter = roleList.iterator();
        Role role = null;
        res.append("[");
        while (iter.hasNext()) {
            role = (Role) iter.next();
            res.append("{");
            res.append("\"roleCode\":\"" + role.getRoleCode() + "\"");
            res.append(",\"roleName\":\"" + role.getRoleName() + "\"");
            res.append("}");
            if (iter.hasNext()) {
                res.append(",");
            }
        }
        res.append("]");

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    /**
     * 返回前台datagrid roleCode formatter 函数结果
     */
    public void getRoleName() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String roleCode = request.getParameter("roleCode");
        RoleService roleService = new RoleService();
        Role role = null;
        String res = null;
        if (roleCode != null) {
            role = roleService.getRoleById(roleCode);
            res = role.getRoleName();
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
     * 根据角色ID返回名称
     */
    public void getRoleNameById() {
        ServletRequest request = ServletActionContext.getRequest();
        ServletResponse response = ServletActionContext.getResponse();

        String roleCode = request.getParameter("roleCode");
        Role role = roleService.getRoleById(roleCode);

        PrintWriter out = null;

        out.println(role.getRoleName());

        out.flush();
        out.close();


    }

    /**
     * 显示用户修改界面
     *
     * @return
     */
    public String showModify() {

        SchoolService schoolService = new SchoolService();

        UserService userService = new UserService();
        HttpServletRequest request = ServletActionContext.getRequest();
        Integer userId = Integer.valueOf((request.getParameter("userId")));
        User user = userService.getById(userId);
        TaxUnitService taxUnitService = new TaxUnitService();
        List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
        List<Role> list = (List<Role>) ((Map<String, Object>) roleService.getAllRoles()).get("list");
        ActionContext.getContext().put("user", user);
        ActionContext.getContext().put("roleList", list);
        ActionContext.getContext().put("taxUnitList", taxUnitList);
        return "show_update_ui_succ";
    }

    /**
     * 修改用户
     */
    public void modify() {

        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        UserService userService = new UserService();
        User u = userService.getById(user.getId());
        u.setName(user.getName());
        u.setCode(user.getCode());
        u.setIsExpired(user.getIsExpired());
        u.setRoleCode(user.getRoleCode());
        u.setSwjgdm(user.getSwjgdm());

        boolean flag = userService.update(u);

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

    /**
     * 根据id删除用户
     *
     * @return void
     */
    public void del() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        UserService userService = new UserService();
        String idArrs = request.getParameter("idArrs");
        String idArrsStr[] = idArrs.split(",");
        int userId[] = new int[idArrsStr.length];
        for (int i = 0; i < idArrsStr.length; i++) {
            userId[i] = Integer.parseInt(idArrsStr[i]);
        }
        for (int id : userId) {
            userService.deleteById(id);
        }

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
     * 根据用户名密码登录
     *
     * @return
     */
    public String login() {

        HttpServletRequest request = ServletActionContext.getRequest();

        UserService userService = new UserService();
        String userCode = user.getCode();
        String password = user.getPassword();

        String dmQz = userCode.substring(0, 7);    // 代码前缀，代码前7位
        String ipAddr = request.getRemoteAddr();    // ip地址
        LoginControlService loginControlService = new LoginControlService();
        LoginControl loginControl = loginControlService.getBySwjgDm(dmQz);    // 根据代码前缀获取登录控制类

        boolean loginFlag = false;    // 登录标志

        if (null == loginControl) {
            // 按登录代码查不到记录
            setMsg("您无权使用该系统！");
        } else if (!("".equals(loginControl.getIpQ()) || loginControl.getIpQ() == null)) {
            // 如果ip地址不为空，需要进行校验
            if (!(compareIpAddr(ipAddr, loginControl.getIpQ()) >= 0 && compareIpAddr(ipAddr, loginControl.getIpZ()) <= 0)) {
                // ip地址不在规定范围内
                setMsg("请在规定IP地址范围内使用该系统！");
            } else {
                // 在规定ip地址范围内，登录
                loginFlag = true;
            }
        } else {
            // ip为空，不校验ip地址范围，直接登录
            loginFlag = true;
        }


        if (loginFlag == true) {
            request.setAttribute("ipAddr", ipAddr);
            User loginUser = userService.getByUserCodeAndPwd(userCode, password);
            boolean flag = true;
            if (null == loginUser) {
                setMsg("用户名或密码错误！");
                flag = false;
            } else {
                String roleId = loginUser.getRoleCode();
                RoleService roleService = new RoleService();
                Role role = roleService.getRoleById(roleId);
                ActionContext.getContext().getSession().put("categorys", role.getCategroyList());
                ActionContext.getContext().getSession().put("loginUser", loginUser);
                setSuccess(true);
            }

            if (!flag)
                return "login";
            else
                return "home";
        } else {
            return "login";
        }

    }

    public void getTaxUnitName() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String swjgdm = request.getParameter("swjgdm");

        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgdm);

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(taxUnit.getMc());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    /**
     * 退出系统
     *
     * @return
     */
    public String logout() {
        ActionContext.getContext().getSession().clear();
        return null;
    }

    /**
     * 重新登录
     *
     * @return
     */
    public String relogin() {
        ActionContext.getContext().getSession().clear();
        return "login";
    }

    /**
     * 返回列表
     *
     * @return
     */
    public String list() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        UserService userService = new UserService();

        int pageSize = Integer.valueOf(request.getParameter("limit") == null ? "10" : request.getParameter("limit"));
        int pageNo = Integer.valueOf(request.getParameter("start") == null ? "1" : request.getParameter("start")) / pageSize + 1;

        String dir = request.getParameter("dir");        // 排序类型

        if (dir == null) {
            dir = "ASC";
        }
        List<User> userList = userService.getPageList(pageSize, pageNo, dir);
        int totalProperty = userService.getList().size();
        String root = JSONArray.fromObject(userList).toString();
//		Ext.grid接收的json数据：Ext.data.JsonReader({totalProperty : "total", root : "root"}
        String jsonResult = "{totalProperty :" + totalProperty + ", root:" + root + "}";

        try {
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String downLoadIE() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String agentBrowserVersion = request.getParameter("agentBrowser");
        String iePath = "downloads/IE7-WindowsXP-x86-chs.exe";

        OutputStream out = null;
        InputStream in = null;
        File file;

        String absoluteFilePath = null;
        absoluteFilePath = request.getSession().getServletContext().getRealPath(iePath);


        /**获取处理好的doc的输入流**/
        file = new File(absoluteFilePath);

        try {
            in = new FileInputStream(file);
            int length = in.available();

            String outFileName = file.getName();

            //区分浏览器差异，构造不同文件名
            String agent = request.getHeader("USER-AGENT");
            if (agent != null && agent.indexOf("MSIE") != -1) {
                outFileName = URLEncoder.encode(outFileName, "gb2312");
                outFileName = URLDecoder.decode(outFileName, "ISO8859_1");
            } else if (agent != null && agent.indexOf("Mozilla") != -1) {
                outFileName = URLEncoder.encode(outFileName, "utf-8");
                outFileName = URLDecoder.decode(outFileName, "ISO8859_1");
            } else {
                outFileName = URLEncoder.encode(outFileName, "utf-8");
            }

            response.setContentType("application/x-msdownload;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + outFileName + "\"");


            out = response.getOutputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[512];

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert out != null;
        try {
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String downLoadFlashPlayer() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String agentBrowserVersion = request.getParameter("agentBrowser");
        String fpForIEPath = "downloads/install_flashplayer11x32ax_ie.exe";
        String fpForOtherPath = "downloads/install_flashplayer11x32_other.exe";
        OutputStream out = null;
        InputStream in = null;
        File file;

        String absoluteFilePath = null;
        if (agentBrowserVersion.equals("ie")) {
            absoluteFilePath = request.getSession().getServletContext().getRealPath(fpForIEPath);

        } else {
            absoluteFilePath = request.getSession().getServletContext().getRealPath(fpForOtherPath);
        }
        /**获取处理好的doc的输入流**/
        file = new File(absoluteFilePath);

        try {
            in = new FileInputStream(file);
            int length = in.available();

            String outFileName = file.getName();

            //区分浏览器差异，构造不同文件名
            String agent = request.getHeader("USER-AGENT");
            if (agent != null && agent.indexOf("MSIE") != -1) {
                outFileName = URLEncoder.encode(outFileName, "gb2312");
                outFileName = URLDecoder.decode(outFileName, "ISO8859_1");
            } else if (agent != null && agent.indexOf("Mozilla") != -1) {
                outFileName = URLEncoder.encode(outFileName, "utf-8");
                outFileName = URLDecoder.decode(outFileName, "ISO8859_1");
            } else {
                outFileName = URLEncoder.encode(outFileName, "utf-8");
            }

            response.setContentType("application/x-msdownload;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + outFileName + "\"");


            out = response.getOutputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[512];

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert out != null;
        try {
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 系统左边菜单AJAX访问获取子节点
     */
    public void getJSONTree() {
        List<Category> categoryList = (List<Category>) ActionContext.getContext().getSession().get("categorys");
        HttpServletRequest request = ServletActionContext.getRequest();
        int parentId = Integer.valueOf(request.getParameter("parentId"));
        List<JSONTreeNode> treeNodes = new ArrayList<JSONTreeNode>();
        StringBuffer parentIDBuffer = new StringBuffer();
        parentIDBuffer.append("|");

        if (parentId != 0) {
            for (Category category : categoryList) {
                if (category.getParentId() == parentId) {
                    JSONTreeNode treeNode = new JSONTreeNode();
                    String categoryId = String.valueOf(category.getId());
                    treeNode.setId(categoryId);
                    treeNode.setText(category.getText());
                    treeNode.setUrl(category.getUrl());
                    treeNode.setHref(category.getHref());
                    treeNode.setHrefTarget(category.getHrefTarget());
                    treeNode.setDescription(category.getDesc());

                    if (null != category.getIcon())
                        treeNode.setIcon(category.getIcon());

                    if (category.getLeaf().equals("n")) {
                        treeNode.setLeaf(false);
                        treeNode.setExpandable(false);
                    } else {
                        treeNode.setLeaf(true);
                        treeNode.setExpandable(false);
                    }
                    treeNodes.add(treeNode);
                }
            }
        } else {
            StringBuffer orderStr = new StringBuffer();

            for (Category category : categoryList) {
                if (category.getParentId()== 0 &&category.getOrder() != 0) {
                    orderStr.append(category.getOrder());
                    orderStr.append(",");
                }
            }
            String order = orderStr.toString();
            order = order.substring(0, order.lastIndexOf(","));
            String[] orderIDStr = order.split(",");
            int[] orderID = new int[orderIDStr.length];
            for (int i = 0; i < orderIDStr.length; i++)
                orderID[i] = Integer.valueOf(orderIDStr[i]);

            Arrays.sort(orderID);

            for (int orderId : orderID) {
                for (Category category : categoryList) {
                    if (category.getOrder() == orderId) {
                        if (category.getParentId() == parentId) {
                            JSONTreeNode treeNode = new JSONTreeNode();
                            String categoryId = String.valueOf(category.getId());
                            treeNode.setId(categoryId);
                            treeNode.setText(category.getText());
                            treeNode.setUrl(category.getUrl());
                            treeNode.setHref(category.getHref());
                            treeNode.setHrefTarget(category.getHrefTarget());
                            treeNode.setDescription(category.getDesc());

                            if (null != category.getIcon())
                                treeNode.setIcon(category.getIcon());

                            if (category.getLeaf().equals("n")) {
                                treeNode.setLeaf(false);
                                treeNode.setExpandable(false);
                            } else {
                                treeNode.setLeaf(true);
                                treeNode.setExpandable(false);
                            }
                            treeNodes.add(treeNode);
                            break;
                        }
                    }
                }


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


    // 修改密码

    public String changePwd() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String password = user.getPassword();

        String msg = "";
        // 原密码输入不正确
        if (!oldPassword.equals(password)) {
            msg = "原密码错误，密码修改失败！";
        } else {
            user.setPassword(newPassword);

            UserService userService = new UserService();
            userService.update(user);
            msg = "密码修改成功！";
        }

        request.setAttribute("msg", msg);
        return "change_pwd";
    }

    /**
     * 比较ip1和ip2，如果ip1 > ip2，返回1；如果ip1=ip2，返回0；如果ip1<ip2，返回-1
     *
     * @param ip1
     * @param ip2
     * @return
     */
    private int compareIpAddr(String ip1, String ip2) {
        String ip1Arr[] = ip1.split("\\.");
        String ip2Arr[] = ip2.split("\\.");

        int flag = 0;
        int i = 0;

        for (i = 0; i < ip1Arr.length; i++) {
            if (Integer.parseInt(ip1Arr[i]) > Integer.parseInt(ip2Arr[i])) {
                // ip1 > ip2
                flag = 1;
                break;
            } else if (Integer.parseInt(ip1Arr[i]) == Integer.parseInt(ip2Arr[i])) {
                continue;
            } else {
                // ip1 < ip2
                flag = -1;
                break;
            }
        }

        return flag;
    }
}