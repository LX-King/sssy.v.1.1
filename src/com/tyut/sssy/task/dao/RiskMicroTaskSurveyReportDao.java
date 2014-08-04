package com.tyut.sssy.task.dao;

import com.tyut.sssy.task.domain.RiskMicroTaskSurveyReport;
import com.tyut.sssy.utils.db.BaseDaoJdbcImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RiskMicroTaskSurveyReportDao extends BaseDaoJdbcImpl {

    @Override
    public Object objectRelationMapping(ResultSet rs) {
        RiskMicroTaskSurveyReport report = new RiskMicroTaskSurveyReport();
        try{
            report.setRwbgDm(rs.getString("rwbg_dm"));
            report.setLscs(rs.getString("lscs"));
            report.setDcqk(rs.getString("dcqk"));
            report.setYtfx(rs.getString("ytfx"));
            report.setFxqNd(rs.getString("fxq_nd"));
            report.setJbqk(rs.getString("jbqk"));
            report.setXgfx(rs.getString("xgfx"));
            report.setFxqSjq(rs.getString("fxq_sjq"));
            report.setFxqSjz(rs.getString("fxq_sjz"));
            report.setNsrsbh(rs.getString("nsrsbh"));
            report.setFxz(rs.getBigDecimal("fxz"));
            report.setJbjb(rs.getString("jbjb"));
            report.setRwfbryDm(rs.getString("rwfbry_dm"));
            report.setRwzxryDm(rs.getString("rwzxry_dm"));
            report.setRwshryDm(rs.getString("rwshry_dm"));
            report.setRwshryMc(rs.getString("rwshry_mc"));
            report.setSjfkRq(rs.getString("sjfk_rq"));
            report.setDcrqQ(rs.getString("dcrq_q"));
            report.setDcrqZ(rs.getString("dcrq_z"));
        }catch(SQLException e){
            e.printStackTrace();
        }
        return report;

    }

    /**
	 * 获取最大的微观任务代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxWgrwDm(String dateStr) {

		String sql = "select max(rwbg_dm) as rwbg_dm from wgrwbg_new where rwbg_dm like ?";
		String rwbgDM = queryMaxId(sql);
		return rwbgDM;
	}

	/**
	 * 添加任务调查报告
	 * @param microTaskSurveyReport
	 */
	public void add(RiskMicroTaskSurveyReport microTaskSurveyReport) {
        StringBuffer sql = new StringBuffer();
        sql.append(" insert into wgrwbg_new(rwbg_dm,fxq_nd,fxq_sjq,fxq_sjz,nsrsbh,fxz,jbjb,jbqk,dcqk,ytfx,xgfx,lscs,rwfbry_dm,rwzxry_dm,dcrq_q,dcrq_z,sjfk_rq,rwshry_dm,rwshry_mc) ");
        sql.append("values(");
        sql.append("'"+microTaskSurveyReport.getRwbgDm()+"',");
        sql.append("'"+microTaskSurveyReport.getFxqNd()+"',");
        sql.append("'"+microTaskSurveyReport.getFxqSjq()+"',");
        sql.append("'"+microTaskSurveyReport.getFxqSjz()+"',");
        sql.append("'"+microTaskSurveyReport.getNsrsbh()+"',");
        sql.append(""+microTaskSurveyReport.getFxz()+",");
        sql.append("'"+microTaskSurveyReport.getJbjb()+"',");
        sql.append("'"+microTaskSurveyReport.getJbqk()+"',");
        sql.append("'"+microTaskSurveyReport.getDcqk()+"',");
        sql.append("'"+microTaskSurveyReport.getYtfx()+"',");
        sql.append("'"+microTaskSurveyReport.getXgfx()+"',");
        sql.append("'"+microTaskSurveyReport.getLscs()+"',");
        sql.append("'"+microTaskSurveyReport.getRwfbryDm()+"',");
        sql.append("'"+microTaskSurveyReport.getRwzxryDm()+"',");
        sql.append("'"+microTaskSurveyReport.getDcrqQ()+"',");
        sql.append("'"+microTaskSurveyReport.getDcrqZ()+"',");
        sql.append("'"+microTaskSurveyReport.getSjfkRq()+"',");
        sql.append("'"+microTaskSurveyReport.getRwshryDm()+"',");
        sql.append("'"+microTaskSurveyReport.getRwshryMc()+"')");
        boolean flag = add(sql.toString());
	}

	/**
	 * 根据id获取任务调查报告
	 * @param rwbgDm
	 * @return
	 */
	public RiskMicroTaskSurveyReport getSurveyReportById(String rwbgDm) {
        StringBuffer sql  = new StringBuffer();
        sql.append("select * from wgrwbg_new where rwbg_dm = '"+rwbgDm+"'");
		RiskMicroTaskSurveyReport report = (RiskMicroTaskSurveyReport)querySingleObject(sql.toString());
        return report;
	}

}
