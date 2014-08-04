package com.tyut.sssy.base.dao;

import com.tyut.sssy.base.domain.TaxType;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaxTypeDao {



    public TaxType getTaxTypeById(String szDm) {
        Connection conn = null;
        String sql = "select * from dm_sz where sz_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        TaxType taxType = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, szDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                taxType = new TaxType();
                taxType.setSzDm(rs.getString("sz_dm"));
                taxType.setMc(rs.getString("mc"));
                taxType.setYxbz(rs.getString("yxbz"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxType;
    }


    public List<TaxType> queryAll() {
        Connection conn = null;
        String sql = "select * from dm_sz ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxType> list = new ArrayList<TaxType>();

        TaxType taxType = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                taxType = new TaxType();
                taxType.setSzDm(rs.getString("sz_dm"));
                taxType.setMc(rs.getString("mc"));
                taxType.setYxbz(rs.getString("yxbz"));
                list.add(taxType);
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
