package com.tyut.sssy.task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tyut.sssy.task.domain.MicroTaskSurveyReport;
import com.tyut.sssy.utils.db.C3P0Util;

public class MicroTaskSurveyReportDao {

	/**
	 * 获取最大的微观任务代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxWgrwDm(String dateStr) {
		Connection conn = null;
		String sql = "select max(rwbg_dm) as rwbg_dm from wgrwbg where rwbg_dm like ?";
		
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
	public void add(MicroTaskSurveyReport microTaskSurveyReport) {
		Connection conn = null;
        String sql = "insert into wgrwbg values (?, ?, ?, ?, ?, ?, ?, " +
        									    "?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, microTaskSurveyReport.getRwbgDm());
            ps.setString(2, microTaskSurveyReport.getFxqNd());
            ps.setString(3, microTaskSurveyReport.getFxqSjq());
            ps.setString(4, microTaskSurveyReport.getFxqSjz());
            ps.setString(5, microTaskSurveyReport.getNsrsbh());
            ps.setString(6, microTaskSurveyReport.getFxzbDm());
            ps.setString(7, microTaskSurveyReport.getTzqj());
            ps.setString(8, microTaskSurveyReport.getJbqk());
            ps.setString(9, microTaskSurveyReport.getDcqk());
            ps.setString(10, microTaskSurveyReport.getCzwt());
            ps.setString(11, microTaskSurveyReport.getLscs());
            ps.setString(12, microTaskSurveyReport.getRwfbryDm());
            ps.setString(13, microTaskSurveyReport.getRwzxryDm());
            ps.setString(14, microTaskSurveyReport.getDcrqQ());
            ps.setString(15, microTaskSurveyReport.getDcrqZ());
            ps.setString(16, microTaskSurveyReport.getSjfkRq());

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
	public MicroTaskSurveyReport getSurveyReportById(String rwbgDm) {
		Connection conn = null;
        String sql = "select * from wgrwbg where rwbg_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        MicroTaskSurveyReport microTaskSurveyReport = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwbgDm);

            rs = ps.executeQuery();

            while (rs.next()) {
            	microTaskSurveyReport = new MicroTaskSurveyReport();
            	microTaskSurveyReport.setRwbgDm(rwbgDm);
            	microTaskSurveyReport.setFxqNd(rs.getString("fxq_nd"));
            	microTaskSurveyReport.setFxqSjq(rs.getString("fxq_sjq"));
            	microTaskSurveyReport.setFxqSjz(rs.getString("fxq_sjz"));
            	microTaskSurveyReport.setNsrsbh(rs.getString("nsrsbh"));
            	microTaskSurveyReport.setFxzbDm(rs.getString("fxzb_dm"));
            	microTaskSurveyReport.setTzqj(rs.getString("tzqj"));
            	microTaskSurveyReport.setJbqk(rs.getString("jbqk"));
            	microTaskSurveyReport.setDcqk(rs.getString("dcqk"));
            	microTaskSurveyReport.setCzwt(rs.getString("czwt"));
            	microTaskSurveyReport.setLscs(rs.getString("lscs"));
            	microTaskSurveyReport.setRwfbryDm(rs.getString("rwfbry_dm"));
            	microTaskSurveyReport.setRwzxryDm(rs.getString("rwzxry_dm"));
            	microTaskSurveyReport.setDcrqQ(rs.getString("dcrq_q"));
            	microTaskSurveyReport.setDcrqZ(rs.getString("dcrq_z"));
            	microTaskSurveyReport.setSjfkRq(rs.getString("sjfk_rq"));
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return microTaskSurveyReport;
	}

}
