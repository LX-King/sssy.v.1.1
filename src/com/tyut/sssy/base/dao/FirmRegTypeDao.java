package com.tyut.sssy.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.utils.db.C3P0Util;

public class FirmRegTypeDao {

	public List<FirmRegType> getFirmRegTypeList() {
		Connection conn = null;
        String sql = "select * from dm_qyzclx";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FirmRegType> list = new ArrayList<FirmRegType>();

        FirmRegType firmRegType = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	firmRegType = new FirmRegType();
            	firmRegType.setQyzclxDm(rs.getString("qyzclx_dm"));
            	firmRegType.setMc(rs.getString("mc"));
            	
                list.add(firmRegType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
	}

	public FirmRegType getFirmRegTypeById(String qyzclxDm) {
		Connection conn = null;
        String sql = "select * from dm_qyzclx where qyzclx_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        FirmRegType firmRegType = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, qyzclxDm);

            rs = ps.executeQuery();

            while (rs.next()) {
            	firmRegType = new FirmRegType();
            	firmRegType.setQyzclxDm(rs.getString("qyzclx_dm"));
            	firmRegType.setMc(rs.getString("mc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return firmRegType;
	}

}
