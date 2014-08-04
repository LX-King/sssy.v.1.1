package com.tyut.sssy.task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tyut.sssy.task.domain.MacroTaskSurveyReport;
import com.tyut.sssy.utils.db.C3P0Util;

public class MacroTaskSurveyReportDao {

	/**
	 * 获取最大的微观任务代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxWgrwDm(String dateStr) {
		Connection conn = null;
		String sql = "select max(rwbg_dm) as rwbg_dm from hgrwbg where rwbg_dm like ?";
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String  maxWgrwDm = "";
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dateStr + "%");
            
            rs = ps.executeQuery();

            if (rs.next()) {
            	maxWgrwDm = rs.getString("rwbg_dm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

		return maxWgrwDm;
	}

	/**
	 * 添加任务调查报告
	 * @param microTaskSurveyReport
	 */
	public void add(MacroTaskSurveyReport macroTaskSurveyReport) {
		Connection conn = null;
        String sql = "insert into hgrwbg values (?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        									    "?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, macroTaskSurveyReport.getRwbgDm());
            ps.setString(2, macroTaskSurveyReport.getFxqNd());
            ps.setString(3, macroTaskSurveyReport.getFxqSjq());
            ps.setString(4, macroTaskSurveyReport.getFxqSjz());
            ps.setString(5, macroTaskSurveyReport.getFxzblxMx());
            ps.setString(6, macroTaskSurveyReport.getFlMc());
            ps.setString(7, macroTaskSurveyReport.getFxzbDm());
            ps.setString(8, macroTaskSurveyReport.getTzqj());
            ps.setString(9, macroTaskSurveyReport.getJbqk());
            ps.setString(10, macroTaskSurveyReport.getDcqk());
            ps.setString(11, macroTaskSurveyReport.getCzwt());
            ps.setString(12, macroTaskSurveyReport.getLscs());
            ps.setString(13, macroTaskSurveyReport.getRwfbryDm());
            ps.setString(14, macroTaskSurveyReport.getRwzxryDm());
            ps.setString(15, macroTaskSurveyReport.getDcrqQ());
            ps.setString(16, macroTaskSurveyReport.getDcrqZ());
            ps.setString(17, macroTaskSurveyReport.getSjfkRq());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // JDBC关闭连接
//			JdbcUtil.close(ps, null, conn);

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
	}

	/**
	 * 根据id获取任务调查报告
	 * @param rwbgDm
	 * @return
	 */
	public MacroTaskSurveyReport getSurveyReportById(String rwbgDm) {
		Connection conn = null;
        String sql = "select * from hgrwbg where rwbg_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        MacroTaskSurveyReport macroTaskSurveyReport = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwbgDm);

            rs = ps.executeQuery();

            while (rs.next()) {
            	macroTaskSurveyReport = new MacroTaskSurveyReport();
            	macroTaskSurveyReport.setRwbgDm(rwbgDm);
            	macroTaskSurveyReport.setFxqNd(rs.getString("fxq_nd"));
            	macroTaskSurveyReport.setFxqSjq(rs.getString("fxq_sjq"));
            	macroTaskSurveyReport.setFxqSjz(rs.getString("fxq_sjz"));
            	macroTaskSurveyReport.setFxzblxMx(rs.getString("fxzblx_mx"));
            	macroTaskSurveyReport.setFlMc(rs.getString("fl_mc"));
            	macroTaskSurveyReport.setFxzbDm(rs.getString("fxzb_dm"));
            	macroTaskSurveyReport.setTzqj(rs.getString("tzqj"));
            	macroTaskSurveyReport.setJbqk(rs.getString("jbqk"));
            	macroTaskSurveyReport.setDcqk(rs.getString("dcqk"));
            	macroTaskSurveyReport.setCzwt(rs.getString("czwt"));
            	macroTaskSurveyReport.setLscs(rs.getString("lscs"));
            	macroTaskSurveyReport.setRwfbryDm(rs.getString("rwfbry_dm"));
            	macroTaskSurveyReport.setRwzxryDm(rs.getString("rwzxry_dm"));
            	macroTaskSurveyReport.setDcrqQ(rs.getString("dcrq_q"));
            	macroTaskSurveyReport.setDcrqZ(rs.getString("dcrq_z"));
            	macroTaskSurveyReport.setSjfkRq(rs.getString("sjfk_rq"));
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return macroTaskSurveyReport;
	}

}
