package com.tyut.sssy.task.dao;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.task.domain.RiskMicroTask;
import com.tyut.sssy.task.domain.RiskMicroTaskExtractParameter;
import com.tyut.sssy.utils.db.BaseDaoJdbcImpl;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * ClassName:RiskMicroTaskDao
 * Function:微观任务Dao
 * Author: LiuXiang
 * Date: 13-7-9
 * Mail:LXiang.tyut@gmail.com
 * Company:和信至诚
 */
public class RiskMicroTaskDao extends BaseDaoJdbcImpl {


    @Override
    public Object objectRelationMapping(ResultSet rs) {
        RiskMicroTask riskMicroTask = new RiskMicroTask();
        try {
            riskMicroTask.setId(rs.getInt("id"));
            riskMicroTask.setFxqNd(rs.getString("fxq_nd"));
            riskMicroTask.setFxqSjq(rs.getString("fxq_sjq"));
            riskMicroTask.setFxqSjz(rs.getString("fxq_sjz"));
            riskMicroTask.setCyDm(rs.getString("cy_dm"));
            riskMicroTask.setNsrsbh(rs.getString("nsrsbh"));
            riskMicroTask.setNsrmc(rs.getString("nsrmc"));
            riskMicroTask.setZgSwjgDm(rs.getString("zgswjg_dm"));
            riskMicroTask.setZgSwjgMc(rs.getString("zgswjg_mc"));
            riskMicroTask.setQyzclxDm(rs.getString("qyzclx_dm"));
            riskMicroTask.setHydlDm(rs.getString("hydl_dm"));
            riskMicroTask.setGygm(rs.getString("gygm"));
            riskMicroTask.setSe(rs.getBigDecimal("se"));
            riskMicroTask.setFxz(rs.getBigDecimal("fxz"));
            riskMicroTask.setHydlMc(rs.getString("hydl_mc"));
            riskMicroTask.setFz1(rs.getBigDecimal("fz1"));
            riskMicroTask.setJsz1(rs.getBigDecimal("jsz1"));
            riskMicroTask.setFz2(rs.getBigDecimal("fz2"));
            riskMicroTask.setJsz2(rs.getBigDecimal("jsz2"));
            riskMicroTask.setFz3(rs.getBigDecimal("fz3"));
            riskMicroTask.setJsz3(rs.getBigDecimal("jsz3"));
            riskMicroTask.setFz4(rs.getBigDecimal("fz4"));
            riskMicroTask.setJsz4(rs.getBigDecimal("jsz4"));
            riskMicroTask.setFz5(rs.getBigDecimal("fz5"));
            riskMicroTask.setJsz5(rs.getBigDecimal("jsz5"));
            riskMicroTask.setFz6(rs.getBigDecimal("fz6"));
            riskMicroTask.setJsz6(rs.getBigDecimal("jsz6"));
            riskMicroTask.setFz7(rs.getBigDecimal("fz7"));
            riskMicroTask.setJsz7(rs.getBigDecimal("jsz7"));
            riskMicroTask.setFz8(rs.getBigDecimal("fz8"));
            riskMicroTask.setJsz8(rs.getBigDecimal("jsz8"));
            riskMicroTask.setFz9(rs.getBigDecimal("fz9"));
            riskMicroTask.setJsz9(rs.getBigDecimal("jsz9"));
            riskMicroTask.setFz10(rs.getBigDecimal("fz10"));
            riskMicroTask.setJsz10(rs.getBigDecimal("jsz10"));
            riskMicroTask.setFz11(rs.getBigDecimal("fz11"));
            riskMicroTask.setJsz11(rs.getBigDecimal("jsz11"));
            riskMicroTask.setFz12(rs.getBigDecimal("fz12"));
            riskMicroTask.setJsz12(rs.getBigDecimal("jsz12"));
            riskMicroTask.setFz13(rs.getBigDecimal("fz13"));
            riskMicroTask.setJsz13(rs.getBigDecimal("jsz13"));
            riskMicroTask.setFz14(rs.getBigDecimal("fz14"));
            riskMicroTask.setJsz14(rs.getBigDecimal("jsz14"));
            riskMicroTask.setFz15(rs.getBigDecimal("fz15"));
            riskMicroTask.setJsz15(rs.getBigDecimal("jsz15"));
            riskMicroTask.setFz16(rs.getBigDecimal("fz16"));
            riskMicroTask.setJsz16(rs.getBigDecimal("jsz16"));
            riskMicroTask.setFz17(rs.getBigDecimal("fz17"));
            riskMicroTask.setJsz17(rs.getBigDecimal("jsz17"));
            riskMicroTask.setFz18(rs.getBigDecimal("fz18"));
            riskMicroTask.setJsz18(rs.getBigDecimal("jsz18"));
            riskMicroTask.setFz19(rs.getBigDecimal("fz19"));
            riskMicroTask.setJsz19(rs.getBigDecimal("jsz19"));
            riskMicroTask.setFz20(rs.getBigDecimal("fz20"));
            riskMicroTask.setJsz20(rs.getBigDecimal("jsz20"));
            riskMicroTask.setFz21(rs.getBigDecimal("fz21"));
            riskMicroTask.setJsz21(rs.getBigDecimal("jsz21"));
            riskMicroTask.setFz22(rs.getBigDecimal("fz22"));
            riskMicroTask.setJsz22(rs.getBigDecimal("jsz22"));
            riskMicroTask.setFz23(rs.getBigDecimal("fz23"));
            riskMicroTask.setJsz23(rs.getBigDecimal("jsz23"));
            riskMicroTask.setFz24(rs.getBigDecimal("fz24"));
            riskMicroTask.setJsz24(rs.getBigDecimal("jsz24"));

            riskMicroTask.setZsjgDm(rs.getString("zsjg_dm"));
            riskMicroTask.setZsjgMc(rs.getString("zsjg_mc"));
            riskMicroTask.setJbjb(rs.getString("jbjb"));
            riskMicroTask.setSffb(rs.getString("sffb"));
            riskMicroTask.setClsx(rs.getString("clsx"));
            riskMicroTask.setBqYysrLrl(rs.getBigDecimal("bq_yysr_lrl"));
            riskMicroTask.setTqYysrLrl(rs.getBigDecimal("tq_yysr_lrl"));
            riskMicroTask.setBqYycbLrl(rs.getBigDecimal("bq_yycb_lrl"));
            riskMicroTask.setTqYycbLrl(rs.getBigDecimal("tq_yycb_lrl"));
            riskMicroTask.setBqQjfyLrl(rs.getBigDecimal("bq_qjfy_lrl"));
            riskMicroTask.setTqQjfyLrl(rs.getBigDecimal("tq_qjfy_lrl"));
            riskMicroTask.setQyfl(rs.getString("qyfl"));

            riskMicroTask.setRwztDm(rs.getString("rwzt_dm"));
            riskMicroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
            riskMicroTask.setRwtqRq(rs.getString("rwtq_rq"));
            riskMicroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
            riskMicroTask.setRwfbRq(rs.getString("rwfb_rq"));
            riskMicroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
            riskMicroTask.setBzjsRq(rs.getString("bzjs_rq"));
            riskMicroTask.setSjjsRq(rs.getString("sjjs_rq"));
            riskMicroTask.setBzfkRq(rs.getString("bzfk_rq"));
            riskMicroTask.setSjfkRq(rs.getString("sjfk_rq"));
            riskMicroTask.setRwbgDm(rs.getString("rwbg_dm"));
            riskMicroTask.setGlpj(rs.getString("glfj"));
            riskMicroTask.setZfpj(rs.getString("zfpj"));
            riskMicroTask.setZhpj(rs.getString("zhpj"));
            riskMicroTask.setPjryDm(rs.getString("pjry_dm"));
            riskMicroTask.setRwpjRq(rs.getString("rwpj_rq"));
            riskMicroTask.setRwjsDf(rs.getInt("rwjs_df"));
            riskMicroTask.setRwfkDf(rs.getInt("rwfk_df"));
            riskMicroTask.setRwzxDf(rs.getInt("rwzx_df"));


        } catch (SQLException e) {

        }
        return riskMicroTask;
    }

    /**
     * 获取提取任务列表
     *
     * @param riskMicroTaskExtractParameter
     * @return List
     */
    public List<RiskMicroTask> getExtractRiskMicroTaskList(
            RiskMicroTaskExtractParameter riskMicroTaskExtractParameter) {

        StringBuffer sql = new StringBuffer();
        sql.append("select * from wgrw_new where");
        sql.append(" fxq_nd = '" + riskMicroTaskExtractParameter.getFxqNd().trim() + "'" + " and ");
        sql.append(" fxq_sjq = '" + riskMicroTaskExtractParameter.getFxqSjq().trim() + "'" + " and ");
        sql.append(" fxq_sjz = '" + riskMicroTaskExtractParameter.getFxqSjz().trim() + "'" + " and ");
        sql.append(" zgswjg_dm like '%" + riskMicroTaskExtractParameter.getSwjgDm().trim() + "%'" + " and ");
        sql.append(" nsrsbh like '%" + riskMicroTaskExtractParameter.getNsrsbh().trim() + "%'" + " and ");
        sql.append(" hydl_dm like '%" + riskMicroTaskExtractParameter.getHydlDm().trim() + "%'" + " and ");
        sql.append(" qyzclx_dm like '%" + riskMicroTaskExtractParameter.getQyzclxDm().trim() + "%'" + " and ");
        sql.append(" cy_dm like '%" + riskMicroTaskExtractParameter.getCyDm().trim() + "%'"+" and ");
        sql.append(" rwzt_dm like '%"+riskMicroTaskExtractParameter.getRwztDm()+"%' ");
        if (!riskMicroTaskExtractParameter.getType().equals("none")) {
            if (riskMicroTaskExtractParameter.getOrder() != null && !riskMicroTaskExtractParameter.getOrder().equals(""))
                if (riskMicroTaskExtractParameter.getOrder().equals("asc"))
                    sql.append(" order by " + riskMicroTaskExtractParameter.getType() + " asc");
                else
                    sql.append(" order by " + riskMicroTaskExtractParameter.getType() + " desc ");

        }
        List<RiskMicroTask> list = queryAll(sql.toString());

        return list;
    }

    public void add(RiskMicroTask microTask) {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" insert into wgrw_new(");
        sql.append("");
        add(sql.toString());
    }

    /**
     * 按分析期删除记录
     *
     * @param dataCalcParameter
     */
    public void deleteByFxq(DataCalcParameter dataCalcParameter) {

        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        sql.append("delete from wgrw_new where fxq_nd = '" + dataCalcParameter.getFxqNd() + "'");
        sql.append(" and fxq_sjq = '" + dataCalcParameter.getFxqSssqQ() + "'");
        sql.append(" and fxq_sjz = '" + dataCalcParameter.getFxqSssqZ() + "'");

        del(sql.toString());
    }

    /**
     * 根据id获取任务对象
     *
     * @param id
     * @return
     */
    public RiskMicroTask getRiskMicroTaskById(int id) {
        Connection conn = null;
        String sql = "select * from wgrw_new where id = '" + id + "'";
        RiskMicroTask riskMicroTask = (RiskMicroTask) querySingleObject(sql);
        return riskMicroTask;
    }


    public void deleteByFxqAndZt(DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "delete from wgrw_new where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and rwzt_dm = '00'";
        PreparedStatement ps = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
    }

    /**
     * 更新任务对象
     *
     * @param riskMicroTask
     */
    public void update(RiskMicroTask riskMicroTask) {
        StringBuffer sql = new StringBuffer();
        sql.append("update wgrw_new ");
        sql.append("set ");
        sql.append(" fxq_nd = '" + riskMicroTask.getFxqNd() + "',");
        sql.append(" fxq_sjq = '" + riskMicroTask.getFxqSjq() + "',");
        sql.append(" fxq_sjz = '" + riskMicroTask.getFxqSjz() + "',");
        sql.append(" cy_dm = '" + riskMicroTask.getCyDm().trim() + "',");
        sql.append(" nsrsbh = '" + riskMicroTask.getNsrsbh() + "',");
        sql.append(" nsrmc = '" + riskMicroTask.getNsrmc() + "',");
        sql.append(" zgswjg_dm = '" + riskMicroTask.getZsjgDm() + "',");
        sql.append(" zgswjg_mc = '" + riskMicroTask.getZsjgMc() + "',");
        sql.append(" qyzclx_dm = '" + riskMicroTask.getQyzclxDm() + "',");
        sql.append(" hydl_dm = '" + riskMicroTask.getHydlDm() + "',");
        sql.append(" gygm = '" + riskMicroTask.getGygm() + "',");
        sql.append(" se  = " + riskMicroTask.getSe() + ",");
        sql.append(" fxz = " + riskMicroTask.getFxz() + ",");
        sql.append(" zsjg_dm = '" + riskMicroTask.getZsjgDm() + "',");
        sql.append(" zsjg_mc = '" + riskMicroTask.getZsjgMc() + "',");
        sql.append(" hydl_mc = '" + riskMicroTask.getHydlMc() + "',");
        sql.append(" jsz1 = " + riskMicroTask.getJsz1() + ",");
        sql.append(" fz1 = " + riskMicroTask.getFz1() + ",");
        sql.append(" jsz2 = " + riskMicroTask.getJsz2() + ",");
        sql.append(" fz2 = " + riskMicroTask.getFz2() + ",");
        sql.append(" jsz3 = " + riskMicroTask.getJsz3() + ",");
        sql.append(" fz3 = " + riskMicroTask.getFz3() + ",");
        sql.append(" jsz4 = " + riskMicroTask.getJsz4() + ",");
        sql.append(" fz4 = " + riskMicroTask.getFz4() + ",");
        sql.append(" jsz5 = " + riskMicroTask.getJsz5() + ",");
        sql.append(" fz5 = " + riskMicroTask.getFz1() + ",");
        sql.append(" jsz6 = " + riskMicroTask.getJsz6() + ",");
        sql.append(" fz6 = " + riskMicroTask.getFz6() + ",");
        sql.append(" jsz7 = " + riskMicroTask.getJsz7() + ",");
        sql.append(" fz7 = " + riskMicroTask.getFz7() + ",");
        sql.append(" jsz8 = " + riskMicroTask.getJsz8() + ",");
        sql.append(" fz8 = " + riskMicroTask.getFz8() + ",");
        sql.append(" jsz9 = " + riskMicroTask.getJsz9() + ",");
        sql.append(" fz9 = " + riskMicroTask.getFz9() + ",");
        sql.append(" jsz10 = " + riskMicroTask.getJsz10() + ",");
        sql.append(" fz10 = " + riskMicroTask.getFz10() + ",");
        sql.append(" jsz11 = " + riskMicroTask.getJsz11() + ",");
        sql.append(" fz11 = " + riskMicroTask.getFz11() + ",");
        sql.append(" jsz12 = " + riskMicroTask.getJsz12() + ",");
        sql.append(" fz12 = " + riskMicroTask.getFz12() + ",");
        sql.append(" jsz13 = " + riskMicroTask.getJsz13() + ",");
        sql.append(" fz13 = " + riskMicroTask.getFz13() + ",");
        sql.append(" jsz14 = " + riskMicroTask.getJsz14() + ",");
        sql.append(" fz14 = " + riskMicroTask.getFz14() + ",");
        sql.append(" jsz15 = " + riskMicroTask.getJsz15() + ",");
        sql.append(" fz15 = " + riskMicroTask.getFz15() + ",");
        sql.append(" jsz16 = " + riskMicroTask.getJsz16() + ",");
        sql.append(" fz16 = " + riskMicroTask.getFz16() + ",");
        sql.append(" jsz17 = " + riskMicroTask.getJsz17() + ",");
        sql.append(" fz17 = " + riskMicroTask.getFz17() + ",");
        sql.append(" jsz18 = " + riskMicroTask.getJsz18() + ",");
        sql.append(" fz18 = " + riskMicroTask.getFz18() + ",");
        sql.append(" jsz19 = " + riskMicroTask.getJsz19() + ",");
        sql.append(" fz19 = " + riskMicroTask.getFz19() + ",");
        sql.append(" jsz20 = " + riskMicroTask.getJsz20() + ",");
        sql.append(" fz20 = " + riskMicroTask.getFz20() + ",");
        sql.append(" jsz21 = " + riskMicroTask.getJsz21() + ",");
        sql.append(" fz21 = " + riskMicroTask.getFz21() + ",");
        sql.append(" jsz22 = " + riskMicroTask.getJsz22() + ",");
        sql.append(" fz22 = " + riskMicroTask.getFz22() + ",");
        sql.append(" jsz23 = " + riskMicroTask.getJsz23() + ",");
        sql.append(" fz23 = " + riskMicroTask.getFz23() + ",");
        sql.append(" jsz24 = " + riskMicroTask.getJsz24() + ",");
        sql.append(" fz24 = " + riskMicroTask.getFz24() + ",");


        sql.append(" jbjb  = '" + riskMicroTask.getJbjb().trim() + "',");
        sql.append(" sffb = '" + riskMicroTask.getSffb() + "',");
        sql.append(" qyfl  = '" + riskMicroTask.getQyfl() + "',");
        sql.append(" rwzt_dm = '" + riskMicroTask.getRwztDm().trim() + "',");
        sql.append(" rwtqry_dm = '" + riskMicroTask.getRwtqryDm().trim() + "',");
        sql.append(" rwtq_rq = '" + riskMicroTask.getRwtqRq().trim() + "',");
        sql.append(" rwfbry_dm = '" + riskMicroTask.getRwfbryDm().trim() + "',");
        sql.append(" rwfb_rq = '" + riskMicroTask.getRwfbRq().trim() + "',");
        sql.append(" rwzxry_dm = '" + riskMicroTask.getRwzxryDm().trim() + "',");
        sql.append(" bzjs_rq = '" + riskMicroTask.getBzjsRq().trim() + "',");
        sql.append(" sjjs_rq = '" + riskMicroTask.getSjjsRq().trim() + "',");
        sql.append(" bzfk_rq = '" + riskMicroTask.getBzfkRq().trim() + "',");
        sql.append(" sjfk_rq = '" + riskMicroTask.getSjfkRq().trim() + "',");
        sql.append(" rwbg_dm = '" + riskMicroTask.getRwbgDm().trim() + "',");
        sql.append(" glfj = '" + riskMicroTask.getGlpj().trim() + "',");
        sql.append(" zfpj = '" + riskMicroTask.getZfpj().trim() + "',");
        sql.append(" zhpj = '" + riskMicroTask.getZhpj().trim() + "',");
        sql.append(" pjry_dm = '" + riskMicroTask.getPjryDm().trim() + "',");
        sql.append(" rwpj_rq = '" + riskMicroTask.getRwpjRq().trim() + "',");
        sql.append(" rwjs_df = " + riskMicroTask.getRwjsDf() + ",");
        sql.append(" rwfk_df = " + riskMicroTask.getRwfkDf() + ",");
        sql.append(" rwzx_df = " + riskMicroTask.getRwzxDf() + "");

        sql.append(" from wgrw_new ");
        sql.append(" where id = " + riskMicroTask.getId());

        modify(sql.toString());
    }


    /**
     * 根据执行人员代码 和 任务状态代码 查询任务列表
     *
     * @param rwzxryDm
     * @param rwztDm
     * @return List
     */
    public List<RiskMicroTask> getRiskMicroTaskListByZxryAndRwzt(String rwzxryDm,
                                                                 String rwztDm) {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        sql.append("select * from wgrw_new where rwzxry_dm like  '%" + rwzxryDm + "%' and rwzt_dm = '" + rwztDm + "'");
        List<RiskMicroTask> list = queryAll(sql.toString());
        return list;
    }

    public boolean isExist(RiskMicroTask riskMicroTask) {

        boolean flag = true;
        StringBuffer sql = new StringBuffer();
        sql.append("select * from wgrw_new where ");
        sql.append("");
        queryCount(sql.toString());
        return flag;

    }

    public RiskMicroTask getById(int id) {
        StringBuffer sql = new StringBuffer();
        if (id != 0) {
            sql.append("select * from wgrw_new where id = " + id);
            RiskMicroTask riskMicroTask = (RiskMicroTask) querySingleObject(sql.toString());
            return riskMicroTask;
        } else
            return null;

    }

    /**
     * 根据任务状态获取任务列表
     *
     * @param rwztDm
     * @return List
     */
    public List<RiskMicroTask> getRiskMicroTaskListByRwzt(String rwztDm) {
        Connection conn = null;
        String sql = "select * from wgrw_new where rwzt_dm = '" + rwztDm + "'";
        List<RiskMicroTask> list = queryAll(sql);

        return list;
    }

    /**
     * 根据发布人员 和 任务状态获得任务列表
     *
     * @param rwfbryDm
     * @param rwztDm
     * @return
     */
    public List<RiskMicroTask> getRiskMicroTaskListByFbryAndRwzt(String rwfbryDm,
                                                                 String rwztDm) {
        Connection conn = null;
        String sql = "select * from wgrw_new where rwfbry_dm = '" + rwfbryDm + "' and rwzt_dm = '" + rwztDm + "'";
        List<RiskMicroTask> list = queryAll(sql);
        return list;

    }

    /**
     * 根据反馈时间起止 任务状态 任务执行人员 获得任务列表
     *
     * @param sjQ
     * @param sjZ
     * @param rwztDm
     * @param rwzxryDm
     * @return
     */
    public List<RiskMicroTask> getListByRwfkRqAndRwztAndRwzxryDm(String sjQ,
                                                                 String sjZ, String rwztDm, String rwzxryDm) {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from wgrw_new where ");
        sql.append(" sjfk_rq between '" + sjQ + "' and '" + sjZ + "' and ");
        sql.append(" rwzt_dm = '" + rwztDm + "' and rwzxry_dm = '" + rwzxryDm + "'");

        List<RiskMicroTask> list = queryAll(sql.toString());

        return list;

    }

    /**
     * 根据管理机关和任务状态获取任务列表
     *
     * @param swjgDm
     * @param rwztDm
     * @return
     */
    public List<RiskMicroTask> getListBySwjgAndRwzt(String swjgDm, String rwztDm) {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from wgrw_new where ");
        sql.append(" zgswjg_dm like '%" + swjgDm + "%' and ");
        sql.append(" rwzt_dm like '%" + rwztDm + "%'");

        List<RiskMicroTask> list = queryAll(sql.toString());
        return list;

    }

}
