package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.MajorTaxPayerGraphic;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorTaxPayerDao {

    /*映射的表名*/
    private static String DB_MAPPING = "jc_zdqy_tb";

    /*映射的表主键*/
    private static String DB_IDENTITY = "id";

    /**
     * 添加
     *
     * @param majorTaxPayerGraphic
     * @return boolean
     */
    public boolean add(MajorTaxPayerGraphic majorTaxPayerGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + "(nsrsbh ,nsrmc)values(?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, majorTaxPayerGraphic.getNsrsbh());
            ps.setString(2, majorTaxPayerGraphic.getNsrmc());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    /**
     * 查询所有
     *
     * @return list
     */
    public List<MajorTaxPayerGraphic> queryAll() {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MajorTaxPayerGraphic> list = new ArrayList<MajorTaxPayerGraphic>();
        String sql = "select * from  " + DB_MAPPING;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MajorTaxPayerGraphic majorTaxPayerGraphic = objectMapping(rs);
                list.add(majorTaxPayerGraphic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return list;
    }

    public MajorTaxPayerGraphic queryById(String nsrsbh) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MajorTaxPayerGraphic majorTaxPayerGraphic = new MajorTaxPayerGraphic();
        String sql = "select * from  " + DB_MAPPING + " where nsrsbh = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nsrsbh);
            rs = ps.executeQuery();
            while (rs.next()) {
                majorTaxPayerGraphic = objectMapping(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return majorTaxPayerGraphic;
    }

    public boolean modify(MajorTaxPayerGraphic majorTaxPayerGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING + " set nsrsbh = ? , nsrmc = ? where id = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, majorTaxPayerGraphic.getNsrsbh());
            ps.setString(2, majorTaxPayerGraphic.getNsrmc());
            ps.setInt(3, majorTaxPayerGraphic.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    public boolean del(MajorTaxPayerGraphic majorTaxPayerGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from  " + DB_MAPPING + " where id = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, majorTaxPayerGraphic.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }


    public int queryRecordCounter() {
        int counter = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select count(*)  from  " + DB_MAPPING;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next())
                counter = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return counter;
    }


    public MajorTaxPayerGraphic objectMapping(ResultSet rs) {
        MajorTaxPayerGraphic majorTaxPayerGraphic = new MajorTaxPayerGraphic();
        try {
            majorTaxPayerGraphic.setId(rs.getInt("id"));
            majorTaxPayerGraphic.setNsrsbh(rs.getString("nsrsbh"));
            majorTaxPayerGraphic.setNsrmc(rs.getString("nsrmc"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majorTaxPayerGraphic;
    }

}
