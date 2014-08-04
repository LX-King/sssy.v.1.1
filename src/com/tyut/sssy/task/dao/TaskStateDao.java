package com.tyut.sssy.task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tyut.sssy.task.domain.TaskState;
import com.tyut.sssy.utils.db.C3P0Util;

public class TaskStateDao {

	public TaskState getTaskStateById(String rwztDm) {
		Connection conn = null;
        String sql = "select * from dm_rwzt where rwzt_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        TaskState taskState = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwztDm);

            rs = ps.executeQuery();

            if (rs.next()) {
            	taskState = new TaskState();
            	taskState.setRwztDm(rs.getString("rwzt_dm"));
            	taskState.setRwztMc(rs.getString("rwzt_mc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taskState;
	}

}
