package com.tyut.sssy.base.dao;

import com.tyut.sssy.base.domain.PreCalLevel;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreCalLevelDao {

    public List<PreCalLevel> queryAll() {
        Connection conn = null;
        String sql = "select * from dm_ysjc ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PreCalLevel> list = new ArrayList<PreCalLevel>();
        PreCalLevel preCalLevel = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                preCalLevel = new PreCalLevel();
                preCalLevel.setYsjcDm(rs.getString("ysjc_dm"));
                preCalLevel.setMc(rs.getString("mc"));
                preCalLevel.setYxbz(rs.getString("yxbz"));
                list.add(preCalLevel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;


    }

    public PreCalLevel getPreCalLevelById(String ysjcDm) {
        Connection conn = null;
        String sql = "select * from dm_ysjc where ysjc_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        PreCalLevel preCalLevel = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, ysjcDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                preCalLevel = new PreCalLevel();
                preCalLevel.setYsjcDm(rs.getString("ysjc_dm"));
                preCalLevel.setMc(rs.getString("mc"));
                preCalLevel.setYxbz(rs.getString("yxbz"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return preCalLevel;
    }

}
