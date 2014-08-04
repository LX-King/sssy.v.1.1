package com.tyut.sssy.sysmgr.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.tyut.sssy.sysmgr.domain.CompanyScale;
import com.tyut.sssy.sysmgr.domain.GCZHB;
import com.tyut.sssy.sysmgr.domain.MajorIndustry;
import com.tyut.sssy.sysmgr.service.CompanyScaleService;
import com.tyut.sssy.sysmgr.service.GCZHBService;
import com.tyut.sssy.utils.StringUtil;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.List;
import java.math.BigDecimal;
import java.net.URLDecoder;

/**
 * ClassName:MenuAction
 * Function:菜单类的Action
 * Author: LiuXiang
 * Date: 2012-3-28
 * Time: 21:15:29
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class CompanyAction extends ActionSupport {

    private CompanyScaleService companyScaleService = null;

    private CompanyScale companyScale;

    /**
     * 构造方法
     */
    public CompanyAction() {
        companyScaleService = new CompanyScaleService();
    }


    /**
     * 修改
     */
    public void modify() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        try {
            request.setCharacterEncoding("utf-8");
            Map params = request.getParameterMap();
            String lenStr = ((String[]) params.get("rows[total]"))[0];
            int len = Integer.parseInt(lenStr);
            CompanyScale companyScale = null;
            boolean fl = true;
            for (int i = 0; i < len; i++) {
                companyScale = new CompanyScale();
                String csCode = ((String[]) params.get("rows[data][" + i + "][csCode]"))[0];
                String csName = ((String[]) params.get("rows[data][" + i + "][csName]"))[0];
                csName = new String(csName.getBytes("iso-8859-1"), "utf-8");

                BigDecimal minLimit = new BigDecimal(Integer.parseInt(((String[]) params.get("rows[data][" + i + "][minLimit]"))[0]));
                BigDecimal maxLimit = new BigDecimal(Integer.parseInt(((String[]) params.get("rows[data][" + i + "][maxLimit]"))[0]));
                companyScale.setCsCode(csCode);
                companyScale.setCsName(csName);
                companyScale.setMinLimit(minLimit);
                companyScale.setMaxLimit(maxLimit);
                fl = companyScaleService.modify(companyScale);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示添加页面
     *
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

        try {
            request.setCharacterEncoding("utf-8");
            Map params = request.getParameterMap();
            String lenStr = ((String[]) params.get("rows[total]"))[0];
            int len = Integer.parseInt(lenStr);
            CompanyScale companyScale = null;
            boolean fl = true;
            for (int i = 0; i < len; i++) {
                companyScale = new CompanyScale();
                String csName = ((String[]) params.get("rows[data][" + i + "][csName]"))[0];

                csName = new String(csName.getBytes("iso-8859-1"), "utf-8");

                BigDecimal minLimit = new BigDecimal(Integer.parseInt(((String[]) params.get("rows[data][" + i + "][minLimit]"))[0]));
                BigDecimal maxLimit = new BigDecimal(Integer.parseInt(((String[]) params.get("rows[data][" + i + "][maxLimit]"))[0]));
                companyScale.setCsCode(companyScaleService.getMaxId());
                companyScale.setCsName(csName);
                companyScale.setMinLimit(minLimit);
                companyScale.setMaxLimit(maxLimit);
                companyScaleService.add(companyScale);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除
     */
    public void del() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String idArrs = request.getParameter("idArrs");
        String idArrsStr[] = idArrs.split(",");
        boolean flag = companyScaleService.del(idArrsStr);
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
     *
     * @return String
     */
    public String showMgr() {
        return "show_mgr_succ";
    }

    /**
     * 显示修改页面
     */
    public String showModify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String csCode = request.getParameter("csCode");
        companyScale = companyScaleService.getById(csCode);
        request.setAttribute("companyScale", companyScale);
        return "show_modify_ui_succ";
    }

    /**
     * 企业规模ZSGC页面查询
     */
    public void query() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String, Object> map;

        int pageSize = Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        String csName = request.getParameter("csName");
        map = companyScaleService.queryByCon(csName, pageSize, pageNo);
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
     * 企业规模重置
     */
    public void reset() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String msg = null;
        GCZHBService gczhbService = new GCZHBService();
        String year = request.getParameter("year");
        String season = request.getParameter("season");
        String seasonBegin  = season.substring(0,season.indexOf('-'));
        String seasonEnd = season.substring(season.indexOf('-')+1);
        
        List<GCZHB> gczhbList = gczhbService.getRecordsbyTime(year , seasonBegin , seasonEnd);
        List<CompanyScale> companyScaleList = companyScaleService.getAllRecords();
        boolean flag = true;
        for (GCZHB gczhb : gczhbList) {
            String qygm = compare2(gczhb.getSnsjse(), companyScaleList);
            gczhb.setQygm(qygm);
            flag = gczhbService.modify(gczhb);
        }

        if (flag) {
            msg = "succ";
        } else {
            msg = "fail";
        }

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    private String compare2(BigDecimal snsjse, List<CompanyScale> companyScaleList) {

        String res = null;
        String strTemp;

        for (CompanyScale companyScale : companyScaleList) {
            int compareMin = snsjse.compareTo(companyScale.getMinLimit());
            int compareMax = snsjse.compareTo(companyScale.getMaxLimit());
            if ((compareMin == 1 || compareMin == 0) && (compareMax == -1 || compareMax == 0)) {
                res = companyScale.getCsName();
                break;
            }
        }
        return res;
    }

    /**
     * 比较企业规模
     *
     * @param str
     * @param companyScaleList
     * @return String
     */
    private String compare(String str, List<CompanyScale> companyScaleList) {
        String res = null;
        String strTemp;
        if (str.contains("以上")) {
            strTemp = str.substring(0, str.indexOf("以上"));
            BigDecimal qygm = StringUtil.convertToBigDecimal(strTemp);
            for (CompanyScale companyScale : companyScaleList) {
                int compareMin = qygm.compareTo(companyScale.getMinLimit());
                int compareMax = qygm.compareTo(companyScale.getMaxLimit());
                if ((compareMin == -1 || compareMin == 0) && (compareMax == 1 || compareMax == 0)) {
                    res = companyScale.getCsName();
                    break;
                }
            }
        } else if (str.contains("以下")) {
            strTemp = str.substring(0, str.indexOf("以下"));
            BigDecimal qygm = StringUtil.convertToBigDecimal(strTemp);
            for (CompanyScale companyScale : companyScaleList) {
                int compareMin = qygm.compareTo(companyScale.getMinLimit());
                int compareMax = qygm.compareTo(companyScale.getMaxLimit());
                if ((compareMin == -1 || compareMin == 0) && (compareMax == 1 || compareMax == 0)) {
                    res = companyScale.getCsName();
                    break;
                }
            }
        } else {
            str = str.trim();
            int index = str.indexOf("-");
            String strMin = str.substring(0, index);
            String strMax = str.substring(index + 1);
            BigDecimal qygmMin = StringUtil.convertToBigDecimal(strMin);
            BigDecimal qygmMax = StringUtil.convertToBigDecimal(strMax);

            for (CompanyScale companyScale : companyScaleList) {
                int compareMin = qygmMin.compareTo(companyScale.getMinLimit());
                int compareMax = qygmMax.compareTo(companyScale.getMaxLimit());
                if ((compareMin == -1 || compareMin == 0) && (compareMax == 1 || compareMax == 0)) {
                    res = companyScale.getCsName();
                    break;
                }
            }
        }

        return res;

    }


    public CompanyScaleService getCompanyScaleService() {
        return companyScaleService;
    }

    public void setCompanyScaleService(CompanyScaleService companyScaleService) {
        this.companyScaleService = companyScaleService;
    }

    public CompanyScale getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(CompanyScale companyScale) {
        this.companyScale = companyScale;
    }
}