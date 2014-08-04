package com.tyut.sssy.sysmgr.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.base.dao.TaxTypeDao;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxType;
import com.tyut.sssy.sysmgr.dao.RegisterTypeGraphicDao;
import com.tyut.sssy.sysmgr.dao.TaxCategoryGraphicDao;
import com.tyut.sssy.sysmgr.domain.RegisterTypeGraphic;
import com.tyut.sssy.sysmgr.domain.TaxCategoryGraphic;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class TaxCategoryGraphicAction extends ActionSupport implements Preparable {

    TaxCategoryGraphicDao dao = null;
    TaxTypeDao pDao = null;


    public void prepare() throws Exception {
        dao = new TaxCategoryGraphicDao();
        pDao = new TaxTypeDao();
    }

    @Override
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String method = request.getParameter("method");
        if (method.equals("showMgr")) {
            return "show_mgr";
        } else if (method.equals("query")) {
            query();
            return null;
        } else if (method.equals("showModify")) {
            return showModify(request);
        } else if (method.equals("modify")) {
            modify(request);
            return null;
        } else if (method.equals("showAdd")) {
            return showAdd(request);
        } else if (method.equals("add")) {
            add(request);
            return null;
        } else if (method.equals("del")) {
            del(request);
            return null;
        } else {
            return null;
        }
    }


    private void query() {
        List<TaxCategoryGraphic> list = this.dao.queryAll();
        int totalProperty = list.size();
        String root = JSONArray.fromObject(list).toString();
        String jsonResult = "{\"total\":" + totalProperty + ", \"rows\":" + root + "}";
        printJson(jsonResult);
    }

    private String showModify(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<TaxType> list = pDao.queryAll();
        request.setAttribute("list", list);
        TaxCategoryGraphic obj = this.dao.queryById(id);
        request.setAttribute("obj", obj);
        return "show_modify";

    }

    private void modify(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        TaxCategoryGraphic taxCategoryGraphic = new TaxCategoryGraphic();
        taxCategoryGraphic.setId(id);
        taxCategoryGraphic.setSzDm(code);
        taxCategoryGraphic.setMc(name);
        taxCategoryGraphic.setYxbz("y");
        boolean flag = this.dao.modify(taxCategoryGraphic);
        if (flag)
            printJson("{\"msg\":\"succ\"}");
        else
            printJson("{\"msg\":\"failure\"}");
    }

    private String showAdd(HttpServletRequest request) {
        List<TaxType> list = pDao.queryAll();
        request.setAttribute("list", list);
        return "show_add";
    }

    private void add(HttpServletRequest request) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");

        if (this.dao.queryByCode(code) == null) {
            TaxCategoryGraphic registerTypeGraphic = new TaxCategoryGraphic();

            registerTypeGraphic.setSzDm(code);
            registerTypeGraphic.setMc(name);
            registerTypeGraphic.setYxbz("Y");
            boolean flag = this.dao.add(registerTypeGraphic);
            if (flag)
                printJson("{\"msg\":\"succ\"}");
            else
                printJson("{\"msg\":\"failure\"}");
        } else {
            printJson("{\"msg\":\"repeat\"}");
        }
    }


    private void del(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        TaxCategoryGraphic firmRegType = this.dao.queryById(id);
        boolean flag = this.dao.del(firmRegType);
        if (flag)
            printJson("{\"msg\":\"succ\"}");
        else
            printJson("{\"msg\":\"failure\"}");

    }

    private void printJson(String json) {
        if (json == null || json.equals(""))
            json = "{\"chart\":{\"caption\":\"Business Results 2005 v 2006\",\"xaxisname\":\"Month\",\"yaxisname\":\"Revenue\",\"showvalues\":\"0\",\"numberprefix\":\"$\"},\"categories\":[{\"category\":[{\"label\":\"Jan\"},{\"label\":\"Feb\"},{\"label\":\"Mar\"},{\"label\":\"Apr\"},{\"label\":\"May\"},{\"label\":\"Jun\"},{\"vline\":\"true\",\"color\":\"FF5904\",\"thickness\":\"2\"},{\"label\":\"Jul\"},{\"label\":\"Aug\"},{\"label\":\"Sep\"},{\"label\":\"Oct\"},{\"label\":\"Nov\"},{\"label\":\"Dec\"}]}],\"dataset\":[{\"seriesname\":\"2006\",\"data\":[{\"value\":\"27400\"},{\"value\":\"29800\"},{\"value\":\"25800\"},{\"value\":\"26800\"},{\"value\":\"29600\"},{\"value\":\"32600\"},{\"value\":\"31800\"},{\"value\":\"36700\"},{\"value\":\"29700\"},{\"value\":\"31900\"},{\"value\":\"34800\"},{\"value\":\"24800\"}]},{\"seriesname\":\"2005\",\"data\":[{\"value\":\"10000\"},{\"value\":\"11500\"},{\"value\":\"12500\"},{\"value\":\"15000\"},{\"value\":\"11000\"},{\"value\":\"9800\"},{\"value\":\"11800\"},{\"value\":\"19700\"},{\"value\":\"21700\"},{\"value\":\"21900\"},{\"value\":\"22900\"},{\"value\":\"20800\"}]}],\"trendlines\":{\"line\":[{\"startvalue\":\"26000\",\"color\":\"91C728\",\"displayvalue\":\"Target\",\"showontop\":\"1\"}]},\"styles\": {\"definition\": [{\"name\": \"CanvasAnim\",\"type\": \"animation\",\"param\": \"_xScale\",\"start\": \"0\",\"duration\": \"1\"}],\"application\": [{\"toobject\": \"Canvas\",\"styles\": \"CanvasAnim\"}]}}";
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert out != null;
        out.flush();
        out.close();
    }

}
