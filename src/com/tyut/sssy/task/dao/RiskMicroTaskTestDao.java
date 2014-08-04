package com.tyut.sssy.task.dao;

import com.tyut.sssy.task.domain.TaskTest;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiskMicroTaskTestDao {

	/**
	 * 根据反馈日期起止获得任务执行人员代码
	 * @param sjQ
	 * @param sjZ
	 * @return
	 */
	public List<String> getRwzxryDmListByRwfkRq(String sjQ, String sjZ) {
		Connection conn = null;
		String sql = "select distinct rwzxry_dm from wgrw_new where rwzt_dm = '05' and sjfk_rq >= ? and sjfk_rq <= ?";
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<String> list = new ArrayList<String>();
        String rwzxryDm = "";
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, sjQ);
            ps.setString(2, sjZ);
            
            rs = ps.executeQuery();

            while (rs.next()) {
            	rwzxryDm = rs.getString("rwzxry_dm");
            	
            	list.add(rwzxryDm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

		return list;
	}

	/**
	 * 获取最大的任务考核代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxRwkhDm(String dateStr) {
		Connection conn = null;
		String sql = "select max(rwkh_dm) as rwkh_dm from rwkh where rwkh_dm like ?";
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String  maxRwkhDm = "";
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dateStr + "%");
            
            rs = ps.executeQuery();

            if (rs.next()) {
            	maxRwkhDm = rs.getString("rwkh_dm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

		return maxRwkhDm;
	}

	public void add(TaskTest taskTest) {
		Connection conn = null;
        String sql = "insert into rwkh values (?, ?, ?, ?, ?, ?, " +
        									  "?, ?, ?, ?, ?, ?, ?)" ;
        PreparedStatement ps = null;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, taskTest.getRwkhDm());
            ps.setString(2, taskTest.getSjQ());
            ps.setString(3, taskTest.getSjZ());
            ps.setString(4, taskTest.getKhRq());
            ps.setString(5, taskTest.getKhSsq());
            
            ps.setString(6, taskTest.getBkhryDm());
            ps.setString(7, taskTest.getKhryDm());
            ps.setFloat(8, taskTest.getRwjsDf());
            ps.setFloat(9, taskTest.getRwfkDf());
            ps.setFloat(10, taskTest.getRwzxDf());
            
            ps.setString(11, taskTest.getZhkhPj());
            ps.setFloat(12, taskTest.getRwZdf());
            ps.setString(13, taskTest.getKhrwLx());
            

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
	 * 根据被考核人员和起止时间删除原有记录
	 * @param bkhryDm
	 * @param sjQ
	 * @param sjZ
	 */
	public void deleteByBkhryAndSjQAndSjZ(String bkhryDm, String sjQ, String sjZ) {
		Connection conn = null;
        String sql = "delete from rwkh where bkhry_dm = ? and sj_q = ? and sj_z = ?";
        PreparedStatement ps = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, bkhryDm);
            ps.setString(2, sjQ);
            ps.setString(3, sjZ);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
		
	}

	/**
	 * 根据任务考核期获得任务考核列表
	 * @param sjQ
	 * @param sjZ
	 * @return
	 */
	public List<TaskTest> getTaskTestListByKhRq(String sjQ, String sjZ, String khrwLx) {
		Connection conn = null;
		String sql = "select * from rwkh where kh_rq >= ? and kh_rq <= ? and khrw_lx = ? order by rw_zdf desc";
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<TaskTest> list = new ArrayList<TaskTest>();
        TaskTest taskTest = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, sjQ);
            ps.setString(2, sjZ);
            ps.setString(3, khrwLx);
            
            rs = ps.executeQuery();

            while (rs.next()) {
            	taskTest = new TaskTest();
            	taskTest.setRwkhDm(rs.getString("rwkh_dm"));
            	taskTest.setSjQ(rs.getString("sj_q"));
            	taskTest.setSjZ(rs.getString("sj_z"));
            	taskTest.setKhRq(rs.getString("kh_rq"));
            	taskTest.setKhSsq(rs.getString("kh_ssq"));
            	taskTest.setBkhryDm(rs.getString("bkhry_dm"));
            	taskTest.setKhryDm(rs.getString("khry_dm"));
            	taskTest.setRwjsDf(rs.getFloat("rwjs_df"));
            	taskTest.setRwfkDf(rs.getFloat("rwfk_df"));
            	taskTest.setRwzxDf(rs.getFloat("rwzx_df"));
            	taskTest.setZhkhPj(rs.getString("zhkh_pj"));
            	taskTest.setRwZdf(rs.getFloat("rw_zdf"));
            	taskTest.setKhrwLx(rs.getString("khrw_lx"));
            	
            	list.add(taskTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

		return list;
	}

}
