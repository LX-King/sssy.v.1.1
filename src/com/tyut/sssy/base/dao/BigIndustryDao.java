package com.tyut.sssy.base.dao;

import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BigIndustryDao {

	public List<BigIndustry> getBigIndustryList() {
		Connection conn = null;
        String sql = "select * from dm_hydl";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<BigIndustry> list = new ArrayList<BigIndustry>();

        BigIndustry bigIndustry = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	bigIndustry = new BigIndustry();
            	bigIndustry.setHydlDm(rs.getString("hydl_dm"));
            	bigIndustry.setMc(rs.getString("mc"));
            	bigIndustry.setCyDm(rs.getString("cy_dm"));
//                bigIndustry.setGmlxdm(rs.getString("gmlx_dm"));
            	bigIndustry.setZdhybz(rs.getString("zdhybz"));

                list.add(bigIndustry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
	}

	public BigIndustry getBigIndustryById(String hy) {
		Connection conn = null;
        String sql = "select * from dm_hydl where hydl_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        BigIndustry bigIndustry = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, hy);

            rs = ps.executeQuery();

            while (rs.next()) {
            	bigIndustry = new BigIndustry();
            	bigIndustry.setHydlDm(rs.getString("hydl_dm"));
            	bigIndustry.setMc(rs.getString("mc"));
            	bigIndustry.setCyDm(rs.getString("cy_dm"));
            	bigIndustry.setZdhybz(rs.getString("zdhybz"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return bigIndustry;
	}

	/**
	 * 获取主要行业
	 * @return
	 */
	public List<BigIndustry> getMainIndustryList() {
		Connection conn = null;
        String sql = "select * from dm_hydl where zdhybz = 'Y'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<BigIndustry> list = new ArrayList<BigIndustry>();

        BigIndustry bigIndustry = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	bigIndustry = new BigIndustry();
            	bigIndustry.setHydlDm(rs.getString("hydl_dm"));
            	bigIndustry.setMc(rs.getString("mc"));
            	bigIndustry.setCyDm(rs.getString("cy_dm"));
            	bigIndustry.setZdhybz(rs.getString("zdhybz"));

                list.add(bigIndustry);
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
