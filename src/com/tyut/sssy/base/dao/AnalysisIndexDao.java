package com.tyut.sssy.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.domain.AnalysisIndex;
import com.tyut.sssy.utils.db.C3P0Util;

public class AnalysisIndexDao {

	public List<AnalysisIndex> getAnalysisIndexList() {
		
		Connection conn = null;
        String sql = "select * from dm_fxzb";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AnalysisIndex> list = new ArrayList<AnalysisIndex>();

        AnalysisIndex analysisIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	analysisIndex = new AnalysisIndex();
            	analysisIndex.setFxzbDm(rs.getString("fxzb_dm"));
            	analysisIndex.setMc(rs.getString("mc"));
            	analysisIndex.setYxbz(rs.getString("yxbz"));
            	
                list.add(analysisIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
		
	}

	public AnalysisIndex getAnalysisIndexById(String fxzbDm) {
		Connection conn = null;
        String sql = "select * from dm_fxzb where fxzb_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        AnalysisIndex analysisIndex = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, fxzbDm);

            rs = ps.executeQuery();

            while (rs.next()) {
            	analysisIndex = new AnalysisIndex();
            	analysisIndex.setFxzbDm(rs.getString("fxzb_dm"));
            	analysisIndex.setMc(rs.getString("mc"));
            	analysisIndex.setYxbz(rs.getString("yxbz"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return analysisIndex;
	}
	
}
