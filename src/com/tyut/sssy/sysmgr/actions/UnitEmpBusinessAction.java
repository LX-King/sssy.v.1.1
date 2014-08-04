package com.tyut.sssy.sysmgr.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.tyut.sssy.sysmgr.domain.CompanyScale;
import com.tyut.sssy.sysmgr.domain.UnitEmpBusiness;
import com.tyut.sssy.sysmgr.service.CompanyScaleService;
import com.tyut.sssy.sysmgr.service.UnitEmpBusinessService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UnitEmpBusinessAction extends ActionSupport {

    private UnitEmpBusinessService unitEmpBusinessService = null;

    private UnitEmpBusiness unitEmpBusiness;

    /**
     * 构造方法
     */
    public UnitEmpBusinessAction() {
        unitEmpBusinessService = new UnitEmpBusinessService();
    }


    /**
     * 修改
     */
    public void modify() {
        String msg = null;
        boolean success = true;

        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        if (this.unitEmpBusiness.getEbName() != null && !this.unitEmpBusiness.getEbName().equals("")) ;
        boolean flag = unitEmpBusinessService.modify(this.unitEmpBusiness);

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
     * 显示添加页面
     * @return String
    */
    public String showAdd() {
        return "show_add_ui_succ";
    }

    /**
     * 添加
     */
    public void add() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        this.unitEmpBusiness.setEbCode(this.unitEmpBusinessService.getMaxId());
        boolean flag = this.unitEmpBusinessService.add(this.unitEmpBusiness);
        String msg = null;
        if (flag) {
            msg = "添加成功!";
        } else {
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
    }

    /**
     * 删除
     */
    public void del() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String idArrs = request.getParameter("idArrs");
        String idArrsStr[] = idArrs.split(",");
        boolean flag = unitEmpBusinessService.del(idArrsStr);
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
     * 返回企业规模主页面
     * @return String
     */
    public String showMgr() {
        return "show_mgr_succ";
    }


    /**
     *企业规模ZSGC页面查询
     */
    public void query() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;

        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        String ebName = request.getParameter("ebName");
        map = unitEmpBusinessService.queryByCon(ebName, pageSize, pageNo);
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
     * 显示修改页面
     */
    public String showModify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String ebCode = request.getParameter("ebCode");
        unitEmpBusiness = unitEmpBusinessService.getById(ebCode);
        request.setAttribute("unitEmpBusiness", unitEmpBusiness);
        return "show_modify_ui_succ";


    }


    public UnitEmpBusiness getUnitEmpBusiness() {
        return unitEmpBusiness;
    }

    public void setUnitEmpBusiness(UnitEmpBusiness unitEmpBusiness) {
        this.unitEmpBusiness = unitEmpBusiness;
    }
}