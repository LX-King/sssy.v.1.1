package com.tyut.sssy.sysmgr.actions;

import com.tyut.sssy.sysmgr.service.DIFilterConService;
import com.tyut.sssy.sysmgr.domain.DIFilterCon;
import com.tyut.sssy.sysmgr.domain.IndexInitConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import java.util.Map;
import java.io.PrintWriter;
import java.io.IOException;

import net.sf.json.JSONArray;

/**
 * ClassName:DIFilterConAction
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 20:10:55
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class DIFilterConAction {

    private DIFilterConService filterConService = null;

    private DIFilterCon diFilterCon = null;

    /**
     * 构造函数
     */
    public DIFilterConAction(){
        this.filterConService = new DIFilterConService();
    }

    /**
     * 显示管理页面
     * @return String
     */
    public String showMgr(){
        return "show_mgr_succ";
    }

    /**
     * 显示修改界面
     * @return String
     */
    public String showModify() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        int id = Integer.parseInt(request.getParameter("id"));
        DIFilterCon diFilterCon= filterConService.getById(id);

        request.setAttribute("diFilterCon", diFilterCon);
        return "show_modify_ui_succ";

    }

    /**
     * 修改
     */
    public void modify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();


        boolean flag = filterConService.modify(diFilterCon);
        String msg = "";
        if (flag) {
            msg = "修改成功!";
        } else {
            flag = false;
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
     *企业规模ZSGC页面查询
     */
    public void query() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;

        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        map = filterConService.queryByCon(pageSize, pageNo);
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

    public DIFilterCon getDiFilterCon() {
        return diFilterCon;
    }

    public void setDiFilterCon(DIFilterCon diFilterCon) {
        this.diFilterCon = diFilterCon;
    }
}
