package com.tyut.sssy.sysmgr.actions;

import com.tyut.sssy.sysmgr.service.IndexInitConfigService;
import com.tyut.sssy.sysmgr.domain.IndexInitConfig;
import com.tyut.sssy.sysmgr.domain.IndexWeightAndValue;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import net.sf.json.JSONArray;

/**
 * ClassName:IndexWeightAndValueAction
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 23:56:04
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexInitConfigAction {

    private IndexInitConfig indexInitConfig = null;

    private IndexInitConfigService service = null;

    public IndexInitConfigAction() {
        service = new IndexInitConfigService();
    }

    /**
     * 显示 管理页
     *
     * @return string
     */
    public String showMgr() {
        return "show_mgr_succ";

    }


/**
     * 显示修改界面
     * @return String
     */
    public String showModify() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String indexCode = request.getParameter("indexCode");
        IndexInitConfig indexInitConfig = service.getById(indexCode);

        request.setAttribute("indexInitConfig", indexInitConfig);
        return "show_modify_ui_succ";

    }

    /**
     * 修改
     */
    public void modify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();


        boolean flag = service.modify(indexInitConfig);
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
     * @return 查询
     */
    public void query() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String majorIndustryName = request.getParameter("name");
        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        Map<String,Object> params = new HashMap<String,Object>();
        if(majorIndustryName != null)
            params.put("majorIndustryName" , majorIndustryName);

        Map<String,Object> map = service.queryByCon(params, pageSize, pageNo);
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

    public IndexInitConfig getIndexInitConfig() {
        return indexInitConfig;
    }

    public void setIndexInitConfig(IndexInitConfig indexInitConfig) {
        this.indexInitConfig = indexInitConfig;
    }
}