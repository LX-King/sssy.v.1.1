package com.tyut.sssy.task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tyut.sssy.utils.db.C3P0Util;

public class TaskThresholdDao {

	/**
	 * 根据任务类型获取任务阈值
	 * @param rwLx
	 * @return
	 */
	public int getRwyzByRwlx(String rwLx) {
		Connection conn = null;
        String sql = "select rw_yz from rwyz where rw_lx = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        int rwyz = 0;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwLx);

            rs = ps.executeQuery();

            if (rs.next()) {
            	rwyz = rs.getInt("rw_yz");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return rwyz;
	}

	/**
	 * 根据任务类型设置任务阈值
	 * @param rwLx
	 * @param rwyz
	 */
	public void updateRwyzByRwlx(String rwLx, int rwyz) {
		Connection conn = null;
        String sql = "update rwyz set rw_yz = ? where rw_lx = ?"; 
        PreparedStatement ps = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, rwyz);
            ps.setString(2, rwLx);
            
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
		
	}

}
