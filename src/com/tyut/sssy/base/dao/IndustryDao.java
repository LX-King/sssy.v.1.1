package com.tyut.sssy.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.domain.Industry;
import com.tyut.sssy.utils.db.C3P0Util;

public class IndustryDao {

	public List<Industry> getIndustryList() {
		Connection conn = null;
        String sql = "select * from dm_cy";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Industry> list = new ArrayList<Industry>();

        Industry industry = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	industry = new Industry();
            	industry.setCyDm(rs.getString("cy_dm"));
            	industry.setMc(rs.getString("mc"));
            	
                list.add(industry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
	}

	public Industry getIndustryById(String cyDm) {
		Connection conn = null;
        String sql = "select * from dm_cy where cy_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        Industry industry = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, cyDm);

            rs = ps.executeQuery();

            while (rs.next()) {
            	industry = new Industry();
            	industry.setCyDm(rs.getString("cy_dm"));
            	industry.setMc(rs.getString("mc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return industry;
	}

}
