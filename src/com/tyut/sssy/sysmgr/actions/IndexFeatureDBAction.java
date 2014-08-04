package com.tyut.sssy.sysmgr.actions;

import com.tyut.sssy.sysmgr.service.IndexWeightAndValueService;
import com.tyut.sssy.sysmgr.service.IndexFeatureDBService;
import com.tyut.sssy.sysmgr.service.WrapperIndexFeatureDBService;
import com.tyut.sssy.sysmgr.service.IndexService;
import com.tyut.sssy.sysmgr.domain.IndexWeightAndValue;
import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;

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
public class IndexFeatureDBAction {

    private IndexFeatureDB indexFeatureDB = null;

    private WrapperIndexFeatureDBService wrapperService = null;

    private IndexFeatureDBService trueService = null;

    public IndexFeatureDBAction() {
        wrapperService = new WrapperIndexFeatureDBService();
        trueService = new IndexFeatureDBService();
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
     * @return 查询
     */
    public void query() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String indexName = request.getParameter("indexName");
        int pageSize = 5 * Integer.valueOf(request.getParameter("rows") == null ? "10" : request.getParameter("rows"));
        int pageNo = Integer.valueOf(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> map = null;
        if (indexName != null) {
            IndexService indexService = new IndexService();
            String indexCode = indexService.getByName(indexName).getIndexCode();
            map = wrapperService.queryByCon(indexCode, pageSize, pageNo);
        } else {
            map = wrapperService.queryByCon(null, pageSize, pageNo);
        }
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

    public String showModify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String fxzbDm = request.getParameter("zb");
        String lx = "ycqj1";
        IndexFeatureDBService service = new IndexFeatureDBService();
        IndexFeatureDB indexFeatureDB = service.getByCodeAndType(fxzbDm,lx);
        request.setAttribute("fxzbDm", fxzbDm);
        request.setAttribute("tz",indexFeatureDB.getFeature());
        request.setAttribute("yd",indexFeatureDB.getQuestion());
        request.setAttribute("jy",indexFeatureDB.getOption());
        return "show_modify";
    }

    public void queryBy() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String dm = request.getParameter("fxzbDm");
        String lx = request.getParameter("lx");
        IndexFeatureDBService service = new IndexFeatureDBService();
        IndexFeatureDB indexFeatureDB = service.getByCodeAndType(dm,lx);
        String msg = JSONArray.fromObject(indexFeatureDB).toString();
        printJson(msg);

    }

    public void modify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String dm = request.getParameter("dm");
        String lx = request.getParameter("lx");
        String tz = request.getParameter("tz");
        String yd = request.getParameter("yd");
        String jy = request.getParameter("jy");

        IndexFeatureDB indexFeatureDB = new IndexFeatureDB();
        indexFeatureDB.setIndexCode(dm);
        indexFeatureDB.setType(lx);
        indexFeatureDB.setFeature(tz);
        indexFeatureDB.setQuestion(yd);
        indexFeatureDB.setOption(jy);

        IndexFeatureDBService service = new IndexFeatureDBService();
        boolean flag = service.modify(indexFeatureDB);
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


    public IndexFeatureDB getIndexFeatureDB() {
        return indexFeatureDB;
    }

    public void setIndexFeatureDB(IndexFeatureDB indexFeatureDB) {
        this.indexFeatureDB = indexFeatureDB;
    }
}