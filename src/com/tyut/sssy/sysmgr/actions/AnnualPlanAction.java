package com.tyut.sssy.sysmgr.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.base.dao.BudgetLevelDao;
import com.tyut.sssy.base.domain.BudgetLevel;
import com.tyut.sssy.base.domain.TaxType;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.TaxTypeService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.sysmgr.domain.AnnualPlan;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.AnnualPlanService;
import com.tyut.sssy.utils.Constants;
import com.tyut.sssy.utils.SessionAttributeKey;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

public class AnnualPlanAction extends ActionSupport implements Preparable {

    private AnnualPlanService annualPlanService = null;
    private TaxUnitService taxunitDao = null;
    private BudgetLevelDao preCalLevelService = null;
    private TaxTypeService taxTypeService = null;


    /**
     * 预处理
     *
     * @throws Exception
     */
    public void prepare() throws Exception {
        annualPlanService = new AnnualPlanService();
        taxunitDao = new TaxUnitService();
        preCalLevelService = new BudgetLevelDao();
        taxTypeService = new TaxTypeService();
    }


    @Override
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String method = request.getParameter("method");
        method.trim();
        if (method.equals("showAdd")) {
            return showAdd(request);
        } else if (method.equals("showMgr")) {
            return showMgr(request);
        } else if (method.equals("cancelAdd")) {
            return cancelAdd(request);
        } else if (method.equals("add")) {
            add(request);
            return null;
        } else if (method.equals("save")) {
            save(request);
            return null;
        } else if (method.equals("addJudge")) {
            addJudge(request);
            return null;
        } else if (method.equals("query")) {
            return query(request);
        } else if (method.equals("showModify")) {
            return showModify(request);
        } else if (method.equals("modify")) {
            modify(request);
            return null;
        } else if (method.equals("del")) {
            del(request);
            return null;
        } else if (method.equals("getTaxUnit")) {
            getTaxUnit(request);
            return null;
        } else if (method.equals("getTaxUnits")) {
            getTaxUnits(request);
            return null;
        } else {
            return super.execute();
        }
    }


    /**
     * 显示管理界面
     *
     * @param request
     * @return String
     */
    private String showMgr(HttpServletRequest request) {
        String currYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        request.setAttribute("currYear",currYear);
        return "show_mgr";
    }

    /**
     * @param request
     * @return
     */
    private String query(HttpServletRequest request) {
        String nd = request.getParameter("nd");
        String msg = "";
        if (annualPlanService.queryByYear(nd).size() > 0) {
            User user = (User) request.getSession().getAttribute(SessionAttributeKey.LOGIN_USER);
            TaxUnit gljg = user.getTaxUnit();

            List<AnnualPlan> taxUnitAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> taxTypeAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> budgetAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> list = annualPlanService.queryByYear(nd);

            String total = list.get(0).getZjh().toString();
            for (AnnualPlan annualPlan : list) {
                if (annualPlan.getFl().equals(Constants.FL_TAX_UNIT))
                    taxUnitAnnualPlans.add(annualPlan);
                else if (annualPlan.getFl().equals(Constants.FL_TAX_TYPE))
                    taxTypeAnnualPlans.add(annualPlan);
                else
                    budgetAnnualPlans.add(annualPlan);
            }
            request.setAttribute("gljg", gljg);
            request.setAttribute("taxUnitAnnualPlans", taxUnitAnnualPlans);
            request.setAttribute("currYear", nd);
            request.setAttribute("total", total);
            request.setAttribute("taxTypeAnnualPlans", taxTypeAnnualPlans);
            request.setAttribute("budgetAnnualPlans", budgetAnnualPlans);
            request.setAttribute("annualPlanList", list);


            return "show_query_succ";
        } else {
            msg = nd + "年的计划为空!";
            request.setAttribute("msg", msg);
            return "show_query_faiure";
        }
    }


    /**
     * 显示添加
     *
     * @param request
     * @return String
     */
    private String showAdd(HttpServletRequest request) {
        String currYear = request.getParameter("nd");
        String msg = "";
        if (annualPlanService.queryByYear(currYear).size() == 0) {
            User user = (User) request.getSession().getAttribute(SessionAttributeKey.LOGIN_USER);
            TaxUnit gljg = user.getTaxUnit();
            List<TaxUnit> taxUnits = taxunitDao.getTaxUnitListByType("Y");
            List<BudgetLevel> preCalLevelList = this.preCalLevelService.queryAll();
            List<TaxType> taxTypeList = this.taxTypeService.queryAll();


            List<AnnualPlan> taxUnitAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> taxTypeAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> budgetAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> toBeAddedPlans = new ArrayList<AnnualPlan>();

            int maxId = annualPlanService.getMaxId() + 1;
            /*预先批量创建AnnualPlan到数据库创建*/
            AnnualPlan annualPlan = null;
            //建立管理机关的年度计划
            for (TaxUnit taxUnit : taxUnits) {
                annualPlan = new AnnualPlan();
                annualPlan.setId(maxId++);
                annualPlan.setFl(Constants.FL_TAX_UNIT);
                annualPlan.setFlMx(taxUnit.getMc());
                annualPlan.setNd(currYear);
                annualPlan.setNdjh(new BigDecimal(0));
                annualPlan.setSwjgDm(gljg.getSwjgDm());
                annualPlan.setTaxUnit(taxUnit);
                annualPlan.setZjh(new BigDecimal(0));
                taxUnitAnnualPlans.add(annualPlan);

                toBeAddedPlans.add(annualPlan);
            }

            //建立税种的年度计划
            for (TaxType taxType : taxTypeList) {
                annualPlan = new AnnualPlan();
                annualPlan.setId(maxId++);
                annualPlan.setFl(Constants.FL_TAX_TYPE);
                annualPlan.setFlMx(taxType.getMc());
                annualPlan.setNd(currYear);
                annualPlan.setNdjh(new BigDecimal(0));
                annualPlan.setZjh(new BigDecimal(0));
                annualPlan.setSwjgDm(gljg.getSwjgDm());

                taxTypeAnnualPlans.add(annualPlan);
                toBeAddedPlans.add(annualPlan);
            }

            //建立预算级次年度计划
            for (BudgetLevel budgetLevel : preCalLevelList) {
                annualPlan = new AnnualPlan();
                annualPlan.setId(maxId++);
                annualPlan.setFl(Constants.FL_BUDGET_LEVEL);
                annualPlan.setFlMx(budgetLevel.getMc());
                annualPlan.setNd(currYear);
                annualPlan.setNdjh(new BigDecimal(0));
                annualPlan.setZjh(new BigDecimal(0));
                annualPlan.setSwjgDm(gljg.getSwjgDm());

                budgetAnnualPlans.add(annualPlan);
                toBeAddedPlans.add(annualPlan);

            }

            boolean flag;
            /*flag = annualPlanService.batchInsert(taxUnitAnnualPlans);
          if (flag)
              flag = annualPlanService.batchInsert(taxTypeAnnualPlans);
          if (flag)
              flag = annualPlanService.batchInsert(budgetAnnualPlans);
          if (flag) {*/
            request.setAttribute("gljg", gljg);
            request.setAttribute("taxUnitAnnualPlans", taxUnitAnnualPlans);
            request.setAttribute("currYear", currYear);
            request.setAttribute("taxTypeAnnualPlans", taxTypeAnnualPlans);
            request.setAttribute("budgetAnnualPlans", budgetAnnualPlans);

            request.getSession().setAttribute("toBeAddedPlans", toBeAddedPlans);

            return "show_add_ui_succ";
        } else {
            msg = currYear + "年的年度已经添加过了！";
            request.setAttribute("msg", msg);
            return "show_add_ui_failure";
        }
    }

    private String cancelAdd(HttpServletRequest request) {
        String year = request.getParameter("year");
        if (year != null && !year.equals(""))
            annualPlanService.delByYear(year);
        return showMgr(request);

    }

    private void save(HttpServletRequest request) {
        String totalStr = request.getParameter("total");
        BigDecimal total = new BigDecimal(Float.parseFloat(totalStr));
        String annualPlansStr = request.getParameter("annualPlans");
        String annualPlans[] = annualPlansStr.trim().split("\\|");
        List<AnnualPlan> comparedList = new ArrayList<AnnualPlan>();

        for (String str : annualPlans) {
            AnnualPlan annualPlan = new AnnualPlan();
            int id = Integer.valueOf(str.substring(str.indexOf(":") + 1, str.indexOf(",")));
            String valueStr = str.substring(str.lastIndexOf(":") + 1);
            BigDecimal value = new BigDecimal(valueStr);
            annualPlan.setId(id);
            annualPlan.setNdjh(value);

            comparedList.add(annualPlan);
        }
        List<AnnualPlan> list = (List<AnnualPlan>) request.getSession().getAttribute("toBeAddedPlans");
        for (AnnualPlan annualPlan : list) {
            for (AnnualPlan annualPlan1 : comparedList) {
                if (annualPlan1.getId() == annualPlan.getId()) {
                    annualPlan.setNdjh(annualPlan1.getNdjh());
                }
                if (annualPlan.getZjh().compareTo(new BigDecimal(0)) == 0) {
                    annualPlan.setZjh(total);
                }
            }

        }

        String msg = "";
        boolean flag = annualPlanService.batchInsert(list);
        if (flag) {
            msg = "succ";
            request.getSession().removeAttribute("toBeAddedPlans");
        }
        printHtml(msg);
    }

    /**
     * 添加
     *
     * @param request
     * @return String
     */
    private void add(HttpServletRequest request) {
        String msg = null;
        HttpServletResponse response = ServletActionContext.getResponse();

        String nd = request.getParameter("nd");
        String fl = request.getParameter("fl");
        String swjgDm = request.getParameter("swjgDm");
        String flMx = request.getParameter("flMx");
        String ndjhStr = request.getParameter("ndjh");
        String zjhStr = request.getParameter("zjh");

        BigDecimal zjh = new BigDecimal(zjhStr);
        BigDecimal ndjh = new BigDecimal(ndjhStr);

        AnnualPlan annualPlan = new AnnualPlan();
        annualPlan.setFlMx(flMx);
        annualPlan.setFl(fl);
        annualPlan.setNd(nd);
        annualPlan.setNdjh(ndjh);
        annualPlan.setSwjgDm(swjgDm);
        annualPlan.setZjh(zjh);

        boolean flag = annualPlanService.insert(annualPlan);

        if (flag)
            msg = "添加成功!";
        else
            msg = "添加失败!";

        printHtml(msg);


    }

    /**
     * 删除
     *
     * @param request
     */
    private void del(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        AnnualPlan annualPlan = annualPlanService.queryById(id);
        boolean flag = annualPlanService.del(annualPlan);
        String msg = "";
        if (flag)
            msg = "删除成功!";
        else
            msg = "删除失败！";

        printHtml(msg);

    }

    /**
     * 显示修改
     *
     * @param request
     * @return String
     */
    private String showModify(HttpServletRequest request) {

        String nd = request.getParameter("nd");
        String msg = "";
        String forward = "show_modify_ui_succ";
        if (annualPlanService.queryByYear(nd).size() > 0) {

            User user = (User) request.getSession().getAttribute(SessionAttributeKey.LOGIN_USER);
            TaxUnit gljg = user.getTaxUnit();

            List<AnnualPlan> taxUnitAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> taxTypeAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> budgetAnnualPlans = new ArrayList<AnnualPlan>();
            List<AnnualPlan> list = annualPlanService.queryByYear(nd);

            String total = list.get(0).getZjh().toString();
            for (AnnualPlan annualPlan : list) {
                if (annualPlan.getFl().equals(Constants.FL_TAX_UNIT))
                    taxUnitAnnualPlans.add(annualPlan);
                else if (annualPlan.getFl().equals(Constants.FL_TAX_TYPE))
                    taxTypeAnnualPlans.add(annualPlan);
                else
                    budgetAnnualPlans.add(annualPlan);
            }
            request.setAttribute("gljg", gljg);
            request.setAttribute("taxUnitAnnualPlans", taxUnitAnnualPlans);
            request.setAttribute("currYear", nd);
            request.setAttribute("total", total);
            request.setAttribute("taxTypeAnnualPlans", taxTypeAnnualPlans);
            request.setAttribute("budgetAnnualPlans", budgetAnnualPlans);
            request.setAttribute("annualPlanList", list);
        } else {
            msg = nd + "年的计划还未添加,请添加后在修改！";
            request.setAttribute("msg", msg);
            forward = "show_modify_failure";
        }
        return forward;
    }

    /**
     * 修改
     *
     * @param request
     */
    private void modify(HttpServletRequest request) {
        String msg = null;
        String totalStr = request.getParameter("total");
        BigDecimal total = new BigDecimal(Float.parseFloat(totalStr));
        String annualPlansStr = request.getParameter("annualPlans");
        String annualPlans[] = annualPlansStr.trim().split("\\|");
        boolean flag = true;

        for (String str : annualPlans) {
            int id = Integer.valueOf(str.substring(str.indexOf(":") + 1, str.indexOf(",")));
            String valueStr = str.substring(str.lastIndexOf(":") + 1);
            BigDecimal value = new BigDecimal(valueStr);
            AnnualPlan annualPlan = annualPlanService.queryById(id);
            annualPlan.setNdjh(value);
            annualPlan.setZjh(total);
            flag = annualPlanService.modify(annualPlan);
        }


        if (flag)
            msg = "succ";
        else
            msg = "failure";

        printHtml(msg);
    }


    private void getTaxUnit(HttpServletRequest request) {
        String swjgDm = request.getParameter("swjgDm");
        TaxUnit taxUnit = taxunitDao.getTaxUnitById(swjgDm);
        String mc = taxUnit.getMc();

        printHtml(mc);
    }

    private void getTaxUnits(HttpServletRequest request) {
        StringBuffer res = new StringBuffer();
        List<TaxUnit> roleList = taxunitDao.getTaxUnitList();
        Iterator iter = roleList.iterator();
        TaxUnit taxUnit = null;
        res.append("[");
        while (iter.hasNext()) {
            taxUnit = (TaxUnit) iter.next();
            res.append("{");
            res.append("\"swjgDM\":\"" + taxUnit.getSwjgDm() + "\"");
            res.append(",\"mc\":\"" + taxUnit.getMc() + "\"");
            res.append("}");
            if (iter.hasNext()) {
                res.append(",");
            }
        }
        res.append("]");
        printJson(res.toString());
    }

    private void addJudge(HttpServletRequest request) {
        String swjgDm = request.getParameter("swjgDm");
        BigDecimal zjh = new BigDecimal(request.getParameter("zjh"));
        BigDecimal ndjh = new BigDecimal(request.getParameter("ndjh"));
        String msg = "";

        List<AnnualPlan> list = annualPlanService.queryByYear(swjgDm);
        if (list == null || list.size() == 0)
            msg = "allowed";
        else {
            AnnualPlan annualPlan = list.get(0);
            if (zjh.compareTo(annualPlan.getZjh()) != 0)
                msg = "different," + annualPlan.getZjh();
            else {
                BigDecimal total = new BigDecimal(0);
                for (AnnualPlan a : list) {
                    total = total.add(a.getNdjh());
                }
                total = total.add(ndjh);
                if (total.compareTo(zjh) > 0) {
                    msg = "beyond";
                }
            }
        }
        printHtml(msg);
    }

    private void printJson(String res) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    private void printHtml(String msg) {
        HttpServletResponse response = ServletActionContext.getResponse();
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
     * 返回JSON格式数据结果
     *
     * @param response
     * @param map
     */
    private void returnWithJson(HttpServletResponse response, Map<String, Object> map) {
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
}
