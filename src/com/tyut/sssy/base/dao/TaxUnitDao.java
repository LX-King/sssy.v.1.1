package com.tyut.sssy.base.dao;

import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class TaxUnitDao {

    /**
     * 返回所有上级税务机关为sjswjgDm的下属税务机关列表
     * @param sjswjgDM
     * @return 所属的税务机关列表
     */
    public List<TaxUnit> getTaxUnitListByLevel(String sjswjgDM) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from dm_swjg where sjswjg_dm = ?";
        List<TaxUnit> resultList = new ArrayList<TaxUnit>();
        TaxUnit taxUnit = null;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sjswjgDM.trim());
            rs = ps.executeQuery();

            while (rs.next()) {
                taxUnit = new TaxUnit();
                taxUnit.setSwjgDm(rs.getString("swjg_dm"));
                taxUnit.setSjswjgDm(rs.getString("sjswjg_dm"));
                taxUnit.setMc(rs.getString("mc"));
                taxUnit.setPx(rs.getInt("px"));
                taxUnit.setGljgbz(rs.getString("gljgbz"));

                resultList.add(taxUnit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return resultList;
    }

    /**
     * 获得管理机关列表
     *
     * @return
     */
    public List<TaxUnit> getTaxUnitList() {
        Connection conn = null;
        String sql = "select * from dm_swjg";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxUnit> list = new ArrayList<TaxUnit>();

        TaxUnit taxUnit = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                taxUnit = new TaxUnit();
                taxUnit.setSwjgDm(rs.getString("swjg_dm"));
                taxUnit.setSjswjgDm(rs.getString("sjswjg_dm"));
                taxUnit.setMc(rs.getString("mc"));
                taxUnit.setPx(rs.getInt("px"));
                taxUnit.setGljgbz(rs.getString("gljgbz"));

                list.add(taxUnit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

    public TaxUnit getTaxUnitById(String swjgDm) {
        Connection conn = null;
        String sql = "select * from dm_swjg where swjg_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        TaxUnit taxUnit = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, swjgDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                taxUnit = new TaxUnit();
                taxUnit.setSwjgDm(rs.getString("swjg_dm"));
                taxUnit.setSjswjgDm(rs.getString("sjswjg_dm"));
                taxUnit.setMc(rs.getString("mc"));
                taxUnit.setPx(rs.getInt("px"));
                taxUnit.setGljgbz(rs.getString("gljgbz"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxUnit;
    }

    /**
     * 根据单位类别查找单位
     *
     * @param unitType
     * @return
     */
    public List<TaxUnit> getTaxUnitListByType(String unitType) {
        Connection conn = null;
        String sql = "select * from dm_swjg where gljgbz = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxUnit> list = new ArrayList<TaxUnit>();

        TaxUnit taxUnit = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, unitType);

            rs = ps.executeQuery();

            while (rs.next()) {
                taxUnit = new TaxUnit();
                taxUnit.setSwjgDm(rs.getString("swjg_dm"));
                taxUnit.setSjswjgDm(rs.getString("sjswjg_dm"));
                taxUnit.setMc(rs.getString("mc"));
                taxUnit.setPx(rs.getInt("px"));
                taxUnit.setGljgbz(rs.getString("gljgbz"));

                list.add(taxUnit);
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
