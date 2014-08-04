/**
 * ClassName:TaxCollectionAndManagerAction
 * Function:
 * Author: LiuXiang
 * Date: 2014-2-3
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
package com.tyut.sssy.taxManager.action;

import com.opensymphony.xwork2.ActionSupport;
import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.TaxPayerService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.taxManager.dao.identityDao.*;
import com.tyut.sssy.taxManager.dao.reportQueryDao.*;
import com.tyut.sssy.taxManager.domain.identity.*;
import com.tyut.sssy.taxManager.domain.parameters.*;
import com.tyut.sssy.taxManager.domain.report.*;
import com.tyut.sssy.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TaxCollectionAndManagerAction extends ActionSupport {

    String fileName;

    InputStream excelStream;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    /**
     * 显示税务登记查询 条件录入界面
     *
     * @return String
     */
    public String showTaxRegisterUI() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();

            //核算形式列表
            AccountFormsDao accountFormsDao = new AccountFormsDao();
            List<AccountForms> accountFormsesList = accountFormsDao.getAccountFormsList();

            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);
            request.setAttribute("accountFormsList", accountFormsesList);

            return "show_tax_register_table_condition_ui";
        }
    }

    /**
     * 税务登记查询 结果显示界面
     *
     * @return String
     */
    public String showTaxRegisterTable() {
        HttpServletRequest request = ServletActionContext.getRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());


        String fxq = request.getParameter("fxq_nd") + request.getParameter("fxq_yf");
        String czq = request.getParameter("czq_nd") + request.getParameter("czq_yf");

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String gljgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String zdsybz = request.getParameter("zdsybz");//重点税源标志
        String hydlDm = request.getParameter("hydlDm");//行业大类代码
        String qyzclx = request.getParameter("qyzclx");//企业注册类型代码
        String hsxs = request.getParameter("hsxs");//核算形式


        TaxRegisterQueryParameters parameters = new TaxRegisterQueryParameters();
        parameters.setFxq(fxq);
        parameters.setCzq(czq);
        parameters.setHsjg(hsjgDm);
        parameters.setGljg(gljgDm);
        parameters.setZgy(zgyDm);
        parameters.setZdsy(zdsybz);
        parameters.setHylb(hydlDm);
        parameters.setHsxs(hsxs);
        parameters.setQyzclx(qyzclx);
        TaxRegisterQueryDao dao = new TaxRegisterQueryDao();

        List<TaxRegisterReturnReport> report = dao.taxRegisterQueryBy(parameters);

        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getGljg());

        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        TaxManager zgy = managerDao.getTaxManagerById(parameters.getZgy());

        //行业大类
        BigIndustry hydl = new BigIndustry();
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);

        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getQyzclx());
        }

        //核算形式
        AccountFormsDao accountFormsDao = new AccountFormsDao();
        AccountForms hsxx = null;
        if (hsxs == null || hsxs.trim().equals("")) {
            hsxx = new AccountForms();
            hsxx.setHsxsDm("");
            hsxx.setMc("全部");
        } else
            hsxx = accountFormsDao.getAccountFormsById(hsxs);


        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("hsxx", hsxx);
        request.setAttribute("zdsy", zdsybz.trim().equals("0") ? "否" : "是");
        request.setAttribute("czq", czq);
        request.setAttribute("zbq", zbq);
        request.setAttribute("report", report);
        request.getSession().setAttribute("registerParameters", parameters);
//		request.setAttribute("tableList", tableList);
//		request.setAttribute("taxResourceMonitorTableParameter", taxResourceMonitorTableParameter);

        return "show_tax_register_table";
    }


    /**
     * 查询税务机关和其征管员
     * --用于前台动态选择调用，返回JSON格式
     */
    public void ajaxQueryTaxUnitAndTaxManager() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();

        if (session == null) return;

        String hsjgDm = request.getParameter("hsjgDm");
        String swjgDm = null;
        TaxUnitService unitService = new TaxUnitService();
        TaxManagerDao managerDao = new TaxManagerDao();
        List<TaxUnit> swjgList = null;
        List<TaxManager> managerList = null;
        if (hsjgDm == null || hsjgDm.trim().equals("")) {
            swjgDm = request.getParameter("swjgDm");
            if (swjgDm != null && !swjgDm.trim().equals(""))
                managerList = managerDao.getTaxManagerListBySwjg(swjgDm);
        } else {
            swjgList = unitService.getTaxUnitListByLevel(hsjgDm);
            managerList = managerDao.getTaxManagerListBySwjg(swjgList.get(0).getSwjgDm());
        }

        StringBuffer jsonReturn = new StringBuffer();
        jsonReturn.append("{");
        jsonReturn.append("\"totalUnits\":\"" + (swjgList == null ? 0 : swjgList.size()) + "\",");
        jsonReturn.append("\"totalManagers\":\"" + (managerList == null ? 0 : managerList.size() + "\","));
        if (swjgList != null && swjgList.size() > 0) {
            jsonReturn.append("\"units\":[");
            for (TaxUnit unit : swjgList) {
                jsonReturn.append("{\"mc\":\"" + unit.getMc() + "\",\"dm\":\"" + unit.getSwjgDm() + "\"},");
            }
            jsonReturn.deleteCharAt(jsonReturn.lastIndexOf(","));
            jsonReturn.append("]");
        }
        if (managerList != null && managerList.size() > 0) {
            jsonReturn.append("\"managers\":[");
            for (TaxManager manager : managerList) {
                jsonReturn.append("{\"mc\":\"" + manager.getMc() + "\",\"zgyDm\":\"" + manager.getZgyDm() + "\"},");
            }
            jsonReturn.deleteCharAt(jsonReturn.lastIndexOf(","));
            jsonReturn.append("]");
        }
        jsonReturn.append("}");

        responseWithJson(jsonReturn, response);

    }

    /**
     * 按登记状态查询税务登记详情
     */
    public void ajaxQueryRegisterDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();
        if (session == null) return;

        TaxRegisterQueryParameters parameters = (TaxRegisterQueryParameters) session.getAttribute("registerParameters");


        String djzt = request.getParameter("djzt");
        String qj = request.getParameter("qj");
        String qjName = qj.trim().equals("czq") ? "基期" : "分析期";

        if (qj.trim().equals("")) return;
        if (djzt == null || djzt.trim().equals("")) return;

        TaxPayerRegisterStatusDao statusDao = new TaxPayerRegisterStatusDao();
        TaxPayerRegisterStatus status = statusDao.getTaxPayerRegisterStatusById(djzt);

        TaxPayerInfoDao taxPayerInfoDao = new TaxPayerInfoDao();
        List<TaxPayerInfo> payerInfoList = taxPayerInfoDao.queryByDjzt(djzt, qj, parameters);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String reportDate = sdf.format(new Date());
        StringBuffer registerDetailTable = new StringBuffer();

        registerDetailTable.append("{");
        registerDetailTable.append("\"title\":\"" + status.getMc() + "户" + qjName + "数据明细表\",");
        registerDetailTable.append("\"totalRecords\":\"" + payerInfoList.size() + "\",");
        registerDetailTable.append("\"reportDate\":\"" + reportDate + "\",");
        registerDetailTable.append("\"th\":[");
        registerDetailTable.append("{\"fieldName\":\"纳税人识别号\"},");
        registerDetailTable.append("{\"fieldName\":\"纳税人\"},");
        registerDetailTable.append("{\"fieldName\":\"税务机关代码\"},");
        registerDetailTable.append("{\"fieldName\":\"税务机关名称\"},");
        registerDetailTable.append("{\"fieldName\":\"征管员代码\"},");
        registerDetailTable.append("{\"fieldName\":\"登记状态代码\"},");
        registerDetailTable.append("{\"fieldName\":\"企业注册类型代码\"}],");


        if (payerInfoList.size() > 0) {
            registerDetailTable.append("\"records\":[");
            for (TaxPayerInfo taxPayerInfo : payerInfoList) {
                registerDetailTable.append("{\"nsrsbh\":\"" + taxPayerInfo.getNsrsbh() + "\"," +
                        "\"nsrmc\":\"" + taxPayerInfo.getNsrmc() + "\"," +
                        "\"swjgDm\":\"" + taxPayerInfo.getSwjgDm() + "\"," +
                        "\"swjgMc\":\"" + taxPayerInfo.getSwjgMc() + "\"," +
                        "\"zgy\":\"" + taxPayerInfo.getZgyDm() + "\"," +
                        "\"djztDm\":\"" + taxPayerInfo.getDjztDm() + "\"," +
                        "\"qyzclxDm\":\"" + taxPayerInfo.getQyzclxDm() + "\"},");
            }
            registerDetailTable.deleteCharAt(registerDetailTable.lastIndexOf(","));
            registerDetailTable.append("]");
        } else {
            registerDetailTable.deleteCharAt(registerDetailTable.lastIndexOf(","));
        }

        registerDetailTable.append("}");

        responseWithJson(registerDetailTable, response);

    }

    /**
     * 发票销售情况  条件选择界面
     *
     * @return
     */
    public String showTaxInvoiceSalesContionUI() throws IOException {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();

            //核算形式列表
            AccountFormsDao accountFormsDao = new AccountFormsDao();
            List<AccountForms> accountFormsesList = accountFormsDao.getAccountFormsList();

            //发票种类
            InvoiceCategoryDao invoiceKindDao = new InvoiceCategoryDao();
            List<InvoiceCategory> invoiceCategoryList = invoiceKindDao.getInvoiceCategoryList();


            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);
            request.setAttribute("accountFormsList", accountFormsesList);
            request.setAttribute("invoiceKindList", invoiceCategoryList);


            return "show_tax_invoice_sales_table_condition_ui";
        }


    }


    /**
     * 发票销售查询 结果显示界面
     *
     * @return String
     */

    public String showTaxInvoiceSalesTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型
        String fpzlDm = request.getParameter("fpzl");//发票种类代码
        String fpfsryDm = request.getParameter("fpfsry");//发票发售人员代码
        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String fpxsrqq = request.getParameter("nd") + request.getParameter("monthPeriod") + request.getParameter("dayPeriod");//发票销售日期
        String xsqhm = request.getParameter("xsqhm") == null ? "" : request.getParameter("xsqhm");
        String xszhm = request.getParameter("xszhm") == null ? "" : request.getParameter("xszhm");

        InvoiceSaleQueryParameters parameters = new InvoiceSaleQueryParameters();
        parameters.setHsjg(hsjgDm);
        parameters.setSwjg(swjgDm);
        parameters.setZgy(zgyDm);
        parameters.setHydl(hydlDm);
        parameters.setQyzclxDm(qyzclx);
        parameters.setFpzl(fpzlDm);
        parameters.setFpfsry(fpfsryDm);
        parameters.setNsrsbh(nsrsbh);
        parameters.setFpxsrqq(fpxsrqq);
        parameters.setXsqhm(xsqhm);
        parameters.setXszhm(xszhm);


        InvoiceSaleQueryDao dao = new InvoiceSaleQueryDao();
        List<InvoiceSaleReturnReport> report = dao.invoiceSaleQueryBy(parameters);


        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getSwjg());
        TaxManager zgy;
        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //发票发售人员
        InvoiceSaler fpfsry;
        if (fpfsryDm == null || fpfsryDm.trim().equals("")) {
            fpfsry = new InvoiceSaler();
            fpfsry.setMc("全部");
            fpfsry.setFpfsryDm("");
        } else {
            InvoiceSalerDao taxSalerDao = new InvoiceSalerDao();
            fpfsry = taxSalerDao.getById(fpfsryDm);
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //发票种类
        InvoiceCategory fpzl = null;
        if (fpzlDm == null || fpfsryDm.trim().equals("")) {
            fpzl = new InvoiceCategory();
            fpzl.setMc("全部");
            fpzl.setFpzlDm("");
        } else {
            InvoiceCategoryDao invoiceCategoryDao = new InvoiceCategoryDao();
            fpzl = invoiceCategoryDao.getInvoiceCategoryById(fpzlDm);
        }

        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getQyzclxDm());
        }


        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);
        request.setAttribute("fpfsry", fpfsry);
        request.setAttribute("fpzl", fpzl);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("xsqhm", xsqhm);
        request.setAttribute("xszhm", xszhm);
        request.setAttribute("parameters", parameters);


        return "show_tax_invoice_sales_table";
    }


    /**
     * 返回纳税人信息--用于前台动态获取
     */
    public void ajaxQueryTaxPayerDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String swjgDm = request.getParameter("swjgDm");
        StringBuffer jsonReponse = new StringBuffer();

        if (StringUtil.isNullOrEmp(swjgDm)) return;
        else {
            //纳税人信息
            TaxPayerService taxPayerService = new TaxPayerService();
            List<TaxPayer> taxPayerList = taxPayerService.getDjTaxPayerListByUnit(swjgDm);

            jsonReponse.append("{\"totalRecords\":\"" + taxPayerList.size() + "\"");
            if (taxPayerList.size() == 0) jsonReponse.append("}");
            else {
                jsonReponse.append(",");
                jsonReponse.append("\"records\":[");
                for (TaxPayer payer : taxPayerList) {
                    jsonReponse.append("{\"nsrsbh\":\"" + payer.getNsrsbh() + "\",\"nsrmc\":\"" + payer.getNsrmc() + "\"},");
                }
                jsonReponse.deleteCharAt(jsonReponse.lastIndexOf(","));
                jsonReponse.append("]}");
            }
            responseWithJson(jsonReponse, response);
        }

    }

    /**
     * 机打发票对比分析  条件选择界面
     *
     * @return String
     */
    public String showTaxMachineCompareTableConditionUI() throws IOException {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();

            //核算形式列表
            AccountFormsDao accountFormsDao = new AccountFormsDao();
            List<AccountForms> accountFormsesList = accountFormsDao.getAccountFormsList();

            //发票种类
            InvoiceCategoryDao invoiceKindDao = new InvoiceCategoryDao();
            List<InvoiceCategory> invoiceCategoryList = invoiceKindDao.getInvoiceCategoryList();


            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);
            request.setAttribute("accountFormsList", accountFormsesList);
            request.setAttribute("invoiceKindList", invoiceCategoryList);


            return "show_tax_machine_compare_table_condition_ui";
        }
    }

    /**
     * 机打发票对比分析 结果显示界面
     *
     * @return String
     */

    public String showTaxMachineCompareTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型

        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String sbrqq = request.getParameter("sbrqq");
        String sbrqz = request.getParameter("sbrqz");
        String cbrqq = request.getParameter("cbrqq");
        String cbrqz = request.getParameter("cbrqz");
        String fpzlDm = request.getParameter("invoiceType");

        TaxMachineCompareQueryParameters parameters = new TaxMachineCompareQueryParameters();
        parameters.setCbrqq(cbrqq);
        parameters.setCbrqz(cbrqz);
        parameters.setSbrqq(sbrqq);
        parameters.setSbrqz(sbrqz);
        parameters.setHsjg(hsjgDm);
        parameters.setSwjg(swjgDm);
        parameters.setHydl(hydlDm);
        parameters.setNsrsbh(nsrsbh);
        parameters.setZclx(qyzclx);
        parameters.setZgy(zgyDm);

        TaxMachineCompareQueryDao dao = new TaxMachineCompareQueryDao();
        List<TaxMachineCompareReturnReport> report = dao.taxMachineCompareQueryBy(parameters);


        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getSwjg());
        TaxManager zgy;
        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //发票种类
        InvoiceCategory fpzl = null;
        if (fpzlDm == null || fpzlDm.trim().equals("")) {
            fpzl = new InvoiceCategory();
            fpzl.setMc("全部");
            fpzl.setFpzlDm("");
        } else {
            InvoiceCategoryDao invoiceCategoryDao = new InvoiceCategoryDao();
            fpzl = invoiceCategoryDao.getInvoiceCategoryById(fpzlDm);
        }

        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getZclx());
        }


        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);

        request.setAttribute("fpzl", fpzl);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("parameters", parameters);


        return "show_tax_machine_compare_table";
    }

    /**
     * 钻取表 -- 机打发票对比查询
     */
    public void ajaxQueryTaxMachineCompareDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();
        if (session == null) return;

        /*TaxRegisterQueryParameters parameters = (TaxRegisterQueryParameters) session.getAttribute("registerParameters");
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       String reportDate = sdf.format(new Date());
       StringBuffer registerDetailTable = new StringBuffer();

       registerDetailTable.append("{");
       registerDetailTable.append("\"title\":\"" + status.getMc() + "户" + qjName + "数据明细表\",");
       registerDetailTable.append("\"totalRecords\":\"" + payerInfoList.size() + "\",");
       registerDetailTable.append("\"reportDate\":\"" + reportDate + "\",");
       registerDetailTable.append("\"th\":[");
       registerDetailTable.append("{\"filedName\":\"纳税人识别号\"},");
       registerDetailTable.append("{\"filedName\":\"纳税人\"},");
       registerDetailTable.append("{\"filedName\":\"税务机关代码\"},");
       registerDetailTable.append("{\"filedName\":\"税务机关名称\"},");
       registerDetailTable.append("{\"filedName\":\"征管员代码\"},");
       registerDetailTable.append("{\"filedName\":\"登记状态代码\"},");
       registerDetailTable.append("{\"filedName\":\"企业注册类型代码\"}],");


       if (payerInfoList.size() > 0) {
           registerDetailTable.append("\"records\":[");
           for (TaxPayerInfo taxPayerInfo : payerInfoList) {
               registerDetailTable.append("{\"nsrsbh\":\"" + taxPayerInfo.getNsrsbh() + "\"," +
                       "\"nsrmc\":\"" + taxPayerInfo.getNsrmc() + "\"," +
                       "\"swjgDm\":\"" + taxPayerInfo.getSwjgDm() + "\"," +
                       "\"swjgMc\":\"" + taxPayerInfo.getSwjgMc() + "\"," +
                       "\"zgy\":\"" + taxPayerInfo.getZgyDm() + "\"," +
                       "\"djztDm\":\"" + taxPayerInfo.getDjztDm() + "\"," +
                       "\"qyzclxDm\":\"" + taxPayerInfo.getQyzclxDm() + "\"},");
           }
           registerDetailTable.deleteCharAt(registerDetailTable.lastIndexOf(","));
           registerDetailTable.append("]");
       } else {
           registerDetailTable.deleteCharAt(registerDetailTable.lastIndexOf(","));
       }

       registerDetailTable.append("}");

       responseWithJson(registerDetailTable, response);*/

    }


    /**
     * 外埠纳税人  条件选择界面
     *
     * @return String
     */
    public String showTaxOtherCityTableConditionUI() throws IOException {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();


            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);


            return "show_tax_other_city_table_condition_ui";
        }
    }

    /**
     * 外埠纳税人 结果显示界面
     *
     * @return String
     */

    public String showTaxOtherCityTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型

        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String sbrqq = request.getParameter("sbrqq");
        String sbrqz = request.getParameter("sbrqz");


        TaxOtherCityQueryParameters parameters = new TaxOtherCityQueryParameters();
        parameters.setHsjg(hsjgDm);
        parameters.setGljg(swjgDm);
        parameters.setZgy(zgyDm);
        parameters.setHydl(hydlDm);
        parameters.setZclx(qyzclx);
        parameters.setNsrsbh(nsrsbh);

        TaxOtherCityQueryDao dao = new TaxOtherCityQueryDao();
        List<TaxOtherCityReturnReport> report = dao.taxOtherCityQueryBy(parameters);

        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getGljg());
        TaxManager zgy;
        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getZclx());
        }


        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);
        request.setAttribute("sbrqq", sbrqq);
        request.setAttribute("sbrqz", sbrqz);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("parameters", parameters);

        return "show_tax_other_city_table";
    }

    /**
     * 外埠纳税人  条件选择界面
     *
     * @return String
     */
    public String showTaxOtherCityRunTableConditionUI() throws IOException {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();

            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();


            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);
            return "show_tax_other_city_run_table_condition_ui";
        }
    }

    /**
     * 外埠纳税人 结果显示界面
     *
     * @return String
     */
    public String showTaxOtherCityRunTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型

        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String sbrqq = request.getParameter("sbrqq");
        String sbrqz = request.getParameter("sbrqz");

        TaxOtherCityRunQueryParameters parameters = new TaxOtherCityRunQueryParameters();
        parameters.setHsjg(hsjgDm);
        parameters.setGljg(swjgDm);
        parameters.setZgy(zgyDm);
        parameters.setHydl(hydlDm);
        parameters.setZclx(qyzclx);
        parameters.setNsrsbh(nsrsbh);
        parameters.setSbrqq(sbrqq);
        parameters.setSbrqz(sbrqz);

        TaxOtherCityRunQueryDao dao = new TaxOtherCityRunQueryDao();
        List<TaxOtherCityRunReturnReport> report = dao.taxOtherCityRunQueryBy(parameters);

        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getGljg());
        TaxManager zgy;
        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getZclx());
        }

        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);
        request.setAttribute("sbrqq", sbrqq);
        request.setAttribute("sbrqz", sbrqz);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("parameters", parameters);

        return "show_tax_other_city_run_table";
    }

    /**
     * 代开发票情况表
     *
     * @return
     * @throws IOException
     */
    public String showTaxInvoiceConsignmentTableConditionUI() throws IOException {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();

            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);

            return "show_tax_invoice_consignment_table_condition_ui";
        }
    }

    /**
     * 代开发票
     *
     * @return String
     */

    public String showTaxInvoiceConsignmentTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型

        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String kprqq = request.getParameter("kprqq");
        String kprqz = request.getParameter("kprqz");

        TaxInvoiceConsignQueryParameters parameters = new TaxInvoiceConsignQueryParameters();
        parameters.setHsjg(hsjgDm);
        parameters.setGljg(swjgDm);
        parameters.setZgy(zgyDm);
        parameters.setHydl(hydlDm);
        parameters.setZclx(qyzclx);
        parameters.setNsrsbh(nsrsbh);
        parameters.setKprqq(kprqq);
        parameters.setKprqq(kprqz);

        TaxInvoiceConsignQueryDao dao = new TaxInvoiceConsignQueryDao();
        List<TaxInvoiceConsignReturnReport> report = dao.taxInvoiceConsignQueryBy(parameters);

        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getGljg());
        TaxManager zgy;

        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getZclx());
        }

        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);
        request.setAttribute("kprqq", kprqq);
        request.setAttribute("kprqz", kprqz);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("parameters", parameters);

        return "show_tax_invoice_consignment_table";
    }

    /**
     * 申报情况
     *
     * @return
     * @throws IOException
     */
    public String showTaxDeclareTableConditionUI() throws IOException {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();

            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);


            return "show_tax_declare_table_condition_ui";
        }
    }

    /**
     * 代开发票
     *
     * @return String
     */

    public String showTaxDeclareTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型

        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String fxqq = request.getParameter("fxqq");
        String fxqz = request.getParameter("fxqz");
        String sbfs = request.getParameter("sbfs");

        TaxDeclareQueryParameters parameters = new TaxDeclareQueryParameters();
        parameters.setHsjg(hsjgDm);
        parameters.setGljg(swjgDm);
        parameters.setZgy(zgyDm);
        parameters.setHydl(hydlDm);
        parameters.setZclx(qyzclx);
        parameters.setNsrsbh(nsrsbh);
        parameters.setFxq(fxqq);
        parameters.setSbfs(sbfs);

        TaxDeclareQueryDao dao = new TaxDeclareQueryDao();
        List<TaxDeclareReturnReport> report = dao.taxDeclareQueryBy(parameters);

        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getGljg());
        TaxManager zgy;
        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getZclx());
        }

        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);
        request.setAttribute("fxqq", fxqq);
        request.setAttribute("fxqz", fxqz);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("sbfs", sbfs);
        request.setAttribute("parameters", parameters);

        return "show_tax_declare_table";
    }

    /**
     * 按开票状态 查询 TIPS 详情
     */
    public void ajaxQueryTaxDeclareDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();
        if (session == null) return;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String reportDate = sdf.format(new Date());
        StringBuffer detailTable = new StringBuffer();

        List<T> resultList = new ArrayList<T>();

        detailTable.append("{");
        detailTable.append("\"title\":\"应申报纳税户明细信息\",");
        detailTable.append("\"totalRecords\":\"" + 0 + "\",");
        detailTable.append("\"reportDate\":\"" + reportDate + "\",");
        detailTable.append("\"th\":[");
        detailTable.append("{\"fieldName\":\"纳税人识别号\"},");
        detailTable.append("{\"fieldName\":\"纳税人\"},");
        detailTable.append("{\"fieldName\":\"征收项目\"},");
        detailTable.append("{\"fieldName\":\"申报日期\"},");
        detailTable.append("{\"fieldName\":\"税款所属期起\"},");
        detailTable.append("{\"fieldName\":\"税款所属期止\"},");
        detailTable.append("{\"fieldName\":\"应征纳税费金额\"},");
        detailTable.append("{\"fieldName\":\"已缴税费金额\"},");
        detailTable.append("{\"fieldName\":\"管理机关\"},");
        detailTable.append("{\"fieldName\":\"管理员\"},");
        detailTable.append("{\"fieldName\":\"是否催报\"},");
        detailTable.append("{\"fieldName\":\"催报日期\"},");
        detailTable.append("{\"fieldName\":\"催报人\"},");
        detailTable.append("{\"fieldName\":\"是否调查\"},");
        detailTable.append("{\"fieldName\":\"调查日期\"},");
        detailTable.append("{\"fieldName\":\"调查人\"},");
        detailTable.append("{\"fieldName\":\"调查结论\"},");
        detailTable.append("{\"fieldName\":\"文书送达回证\"},");
        detailTable.append("{\"fieldName\":\"文书受理人\"}],");


        if (resultList.size() > 0) {
            detailTable.append("\"records\":[");

            /*    detailTable.append("{\"nsrsbh\":\"" + taxPayerInfo.getNsrsbh() + "\"," +*/
            /*            "\"nsrmc\":\"" + taxPayerInfo.getNsrmc() + "\"," +              */
            /*            "\"swjgDm\":\"" + taxPayerInfo.getSwjgDm() + "\"," +            */
            /*            "\"swjgMc\":\"" + taxPayerInfo.getSwjgMc() + "\"," +            */
            /*            "\"zgy\":\"" + taxPayerInfo.getZgyDm() + "\"," +                */
            /*            "\"djztDm\":\"" + taxPayerInfo.getDjztDm() + "\"," +            */
            /*            "\"qyzclxDm\":\"" + taxPayerInfo.getQyzclxDm() + "\"},");       */

            detailTable.deleteCharAt(detailTable.lastIndexOf(","));
            detailTable.append("]");
        } else {
            detailTable.deleteCharAt(detailTable.lastIndexOf(","));
        }
        detailTable.append("}");

        responseWithJson(detailTable, response);

    }

    /**
     * 代开发票情况表
     *
     * @return
     * @throws IOException
     */
    public String showTaxNetDeclareTableConditionUI() throws IOException {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();

            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);

            return "show_tax_net_declare_table_condition_ui";
        }
    }

    /**
     * 代开发票
     *
     * @return String
     */

    public String showTaxNetDeclareTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型

        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String fxqq = request.getParameter("fxqq");
        String fxqz = request.getParameter("fxqz");
        String ygz = request.getParameter("ygz");//营改增

        TaxNetDeclareQueryParameters parameters = new TaxNetDeclareQueryParameters();
        parameters.setHsjg(hsjgDm);
        parameters.setGljg(swjgDm);
        parameters.setZgy(zgyDm);
        parameters.setHydl(hydlDm);
        parameters.setZclx(qyzclx);
        parameters.setNsrsbh(nsrsbh);
        parameters.setFxq(fxqq);
        parameters.setYgz(ygz);


        TaxNetDeclareQueryDao dao = new TaxNetDeclareQueryDao();
        List<TaxNetDeclareReturnReport> report = dao.taxNetDeclareQueryBy(parameters);

        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getGljg());
        TaxManager zgy;
        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getZclx());
        }

        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);
        request.setAttribute("fxqq", fxqq);
        request.setAttribute("fxqz", fxqz);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("ygz", ygz);
        request.setAttribute("parameters", parameters);

        return "show_tax_net_declare_table";
    }


    /**
     * Tips分析条件选择 UI
     *
     * @return String
     */
    public String showTipsConditionUI() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");

        if (null == user) //如果Session过期，则提示重新登录
            return "relogin";
        else {
            TaxUnit defaultUnit = user.getTaxUnit();
            // 管理机关列表
            TaxUnitService taxUnitService = new TaxUnitService();
            List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();


            List<TaxUnit> hsjg = new ArrayList<TaxUnit>();
            List<TaxUnit> swjg = new ArrayList<TaxUnit>();

            if (defaultUnit.getGljgbz().trim().equals("H"))
                hsjg.add(defaultUnit);
            else
                swjg.add(defaultUnit);

            if (hsjg.size() > 0)
                for (TaxUnit unit : taxUnitList) {
                    if (!unit.getGljgbz().trim().equals("H")) {
                        swjg.add(unit);
                    }
                }
            else {
                TaxUnit sjhsjg = taxUnitService.getTaxUnitById(defaultUnit.getSjswjgDm());
                hsjg.add(sjhsjg);
            }

            //征管员的列表
            TaxManagerDao managerDao = new TaxManagerDao();
            List<TaxManager> taxManagerList = managerDao.getTaxManagerListBySwjg(swjg.get(0).getSwjgDm());

            // 行业大类
            BigIndustryService bigIndustryService = new BigIndustryService();
            List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

            //注册类型
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
            HttpServletRequest request = ServletActionContext.getRequest();

            request.setAttribute("hsjg", hsjg);
            request.setAttribute("swjg", swjg);
            request.setAttribute("defaultUnit", defaultUnit);
            request.setAttribute("taxManagerList", taxManagerList);
            request.setAttribute("bigIndustryList", bigIndustryList);
            request.setAttribute("firmRegTypeList", firmRegTypeList);

            return "show_tips_condition_ui";
        }
    }


    public String showTipsTable() {
        HttpServletRequest request = ServletActionContext.getRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String zbq = sdf.format(new Date());

        String hsjgDm = request.getParameter("hsjg");//核算机关代码
        String swjgDm = request.getParameter("swjg");//管理机关代码
        String zgyDm = request.getParameter("zgy");//征管员
        String hydlDm = request.getParameter("hydlDm");//行业大类
        String qyzclx = request.getParameter("qyzclx");//企业注册类型

        String nsrsbh = request.getParameter("nsrsbh");//纳税人识别号
        String fxqq = request.getParameter("fxqq");
        String fxqz = request.getParameter("fxqz");
        String jkfs = request.getParameter("jkfs");

        TipsQueryParameters parameters = new TipsQueryParameters();
        parameters.setHsjg(hsjgDm);
        parameters.setGljg(swjgDm);
        parameters.setZgy(zgyDm);
        parameters.setHydl(hydlDm);
        parameters.setZclx(qyzclx);
        parameters.setNsrsbh(nsrsbh);
        parameters.setFxq(fxqq);
        parameters.setJkfs(jkfs);


        TipsQueryDao dao = new TipsQueryDao();
        List<TipsReturnReport> report = dao.tipsQueryBy(parameters);

        // 管理机关
        TaxUnitService taxUnitService = new TaxUnitService();

        TaxUnit hsjg = taxUnitService.getTaxUnitById(parameters.getHsjg());
        TaxUnit gljg = taxUnitService.getTaxUnitById(parameters.getGljg());
        TaxManager zgy;
        //征管员
        TaxManagerDao managerDao = new TaxManagerDao();
        if (zgyDm == null || zgyDm.trim().equals("")) {
            zgy = new TaxManager();
            zgy.setMc("全部");
            zgy.setZgyDm("");
            zgy.setSwjgDm(gljg.getSwjgDm());
        } else {
            zgy = managerDao.getTaxManagerById(parameters.getZgy());
        }

        //行业大类
        BigIndustry hydl = null;
        BigIndustryService hyService = new BigIndustryService();
        if (hydlDm == null || hydlDm.trim().equals("")) {
            hydl = new BigIndustry();
            hydl.setHydlDm("");
            hydl.setMc("全部");
        } else
            hydl = hyService.getBigIndustryById(hydlDm);


        //注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        FirmRegType zclx = null;
        if (qyzclx == null || qyzclx.trim().equals("")) {
            zclx = new FirmRegType();
            zclx.setMc("全部");
            zclx.setQyzclxDm("");
        } else {
            zclx = firmRegTypeService.getFirmRegTypeById(parameters.getZclx());
        }

        //纳税人信息
        TaxPayer nsrxx = null;
        TaxPayerService taxPayerService = new TaxPayerService();
        if (nsrsbh == null || nsrsbh.trim().equals("")) {
            nsrxx = new TaxPayer();
            nsrxx.setNsrmc("全部");
            nsrxx.setNsrsbh("全部");
        } else {
            nsrxx = taxPayerService.getTaxPayerByCode(nsrsbh);
        }

        request.setAttribute("hsjg", hsjg);
        request.setAttribute("gljg", gljg);
        request.setAttribute("zgy", zgy);
        request.setAttribute("hydl", hydl);
        request.setAttribute("zclx", zclx);
        request.setAttribute("report", report);
        request.setAttribute("fxqq", fxqq);
        request.setAttribute("fxqz", fxqz);
        request.setAttribute("zbq", zbq);
        request.setAttribute("nsrxx", nsrxx);
        request.setAttribute("jkfs", jkfs);
        request.setAttribute("parameters", parameters);

        return "show_tips_table";
    }


    /**
     * 按开票状态 查询 TIPS 详情
     */
    public void ajaxQueryTipsDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();
        if (session == null) return;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String reportDate = sdf.format(new Date());
        StringBuffer detailTable = new StringBuffer();

        List<T> resultList = new ArrayList<T>();

        detailTable.append("{");
        detailTable.append("\"title\":\"已入库纳税户明细信息\",");
        detailTable.append("\"totalRecords\":\"" + 0 + "\",");
        detailTable.append("\"reportDate\":\"" + reportDate + "\",");
        detailTable.append("\"th\":[");
        detailTable.append("{\"fieldName\":\"纳税人识别号\"},");
        detailTable.append("{\"fieldName\":\"纳税人\"},");
        detailTable.append("{\"fieldName\":\"管理机关\"},");
        detailTable.append("{\"fieldName\":\"管理员\"},");
        detailTable.append("{\"fieldName\":\"注册类型\"},");
        detailTable.append("{\"fieldName\":\"行业类别\"},");
        detailTable.append("{\"fieldName\":\"开户银行名称\"},");
        detailTable.append("{\"fieldName\":\"开户行行号\"},");
        detailTable.append("{\"fieldName\":\"清算行名称\"},");
        detailTable.append("{\"fieldName\":\"清算行行号\"},");
        detailTable.append("{\"fieldName\":\"开户日期\"},");
        detailTable.append("{\"fieldName\":\"缴款方式\"},");
        detailTable.append("{\"fieldName\":\"征收项目\"},");
        detailTable.append("{\"fieldName\":\"协议书号\"}],");


        if (resultList.size() > 0) {
            detailTable.append("\"records\":[");

            /*    detailTable.append("{\"nsrsbh\":\"" + taxPayerInfo.getNsrsbh() + "\"," +*/
            /*            "\"nsrmc\":\"" + taxPayerInfo.getNsrmc() + "\"," +              */
            /*            "\"swjgDm\":\"" + taxPayerInfo.getSwjgDm() + "\"," +            */
            /*            "\"swjgMc\":\"" + taxPayerInfo.getSwjgMc() + "\"," +            */
            /*            "\"zgy\":\"" + taxPayerInfo.getZgyDm() + "\"," +                */
            /*            "\"djztDm\":\"" + taxPayerInfo.getDjztDm() + "\"," +            */
            /*            "\"qyzclxDm\":\"" + taxPayerInfo.getQyzclxDm() + "\"},");       */

            detailTable.deleteCharAt(detailTable.lastIndexOf(","));
            detailTable.append("]");
        } else {
            detailTable.deleteCharAt(detailTable.lastIndexOf(","));
        }
        detailTable.append("}");

        responseWithJson(detailTable, response);

    }


    /**
     * 将workbook写入InputStream
     *
     * @param workbook
     * @param fileName
     */
    private void workbook2InputStream(HSSFWorkbook workbook, String fileName) throws IOException {
        this.fileName = fileName;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        baos.flush();
        byte[] aa = baos.toByteArray();
        this.excelStream = new ByteArrayInputStream(aa, 0, aa.length);
        baos.close();
    }

    /**
     * 对客户端输出JSON
     *
     * @param res
     * @param response
     */
    private void responseWithJson(StringBuffer res, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert out != null;
        out.flush();
        out.close();
    }

}
