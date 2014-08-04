package com.tyut.sssy.sysmgr.actions;

import com.tyut.sssy.sysmgr.service.MajorIndustryService;
import com.tyut.sssy.sysmgr.service.RoleService;
import com.tyut.sssy.sysmgr.service.IndustryService;
import com.tyut.sssy.sysmgr.domain.MajorIndustry;
import com.tyut.sssy.sysmgr.domain.Role;
import com.tyut.sssy.sysmgr.domain.Industry;
import com.sun.xml.internal.fastinfoset.Decoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.net.URLDecoder;

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
public class MajorIndustryAction {

    private MajorIndustry majorIndustry = null;

    private MajorIndustryService service = null;

    public MajorIndustryAction() {
        service = new MajorIndustryService();
    }

    /**
     * 显示 管理页
     *
     * @return string
     */
    public String showMgr() {
        return "show_mgr_succ";

    }

    public void modify() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        
        Map params = request.getParameterMap();
        String lenStr = ((String[]) params.get("rows[total]"))[0];
        int len = Integer.parseInt(lenStr);
        MajorIndustry majorIndustry = null;
        boolean fl = true;
        for (int i = 0; i < len; i++) {
            majorIndustry = new MajorIndustry();
            String majorIndustryCode = ((String[]) params.get("rows[data][" + i + "][majorIndustryCode]"))[0];
            String industryCode = ((String[]) params.get("rows[data][" + i + "][industryCode]"))[0];
            String flag = ((String[]) params.get("rows[data][" + i + "][flag]"))[0];
            majorIndustry.setIndustryCode(industryCode);
            majorIndustry.setMajorIndustryCode(majorIndustryCode);
            majorIndustry.setFlag(flag);
            fl = service.modify(majorIndustry);
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        String result = "succ";
        if (!fl) {
            result = "fail";
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();

            out = response.getWriter();
            out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();

    }

    /**
     * 获取产业代码
     */
    public void getIndustrys() {
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;

        IndustryService industryService = new IndustryService();
        List<Industry> list = industryService.getAllIndustry();
        StringBuffer res = new StringBuffer();

        Iterator iter = list.iterator();
        Industry industry = null;
        res.append("[");
        while (iter.hasNext()) {
            industry = (Industry) iter.next();
            res.append("{");
            res.append("\"industryCode\":\"" + industry.getIndustryCode() + "\"");
            res.append(",\"industryName\":\"" + industry.getIndustryName() + "\"");
            res.append("}");
            if (iter.hasNext()) {
                res.append(",");
            }
        }
        res.append("]");

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
     * @return 查询
     */
    public void query() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String majorIndustryName = request.getParameter("name");
        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        Map<String, Object> params = new HashMap<String, Object>();
        if (majorIndustryName != null)
            params.put("majorIndustryName", majorIndustryName);

        Map<String, Object> map = service.queryByCon(params, pageSize, pageNo);
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

    public MajorIndustry getMajorIndustry() {
        return majorIndustry;
    }

    public void setMajorIndustry(MajorIndustry majorIndustry) {
        this.majorIndustry = majorIndustry;
    }
}