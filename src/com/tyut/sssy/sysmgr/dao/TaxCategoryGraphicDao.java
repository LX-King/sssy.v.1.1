package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.TaxCategoryGraphic;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:TaxCategoryDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-25
 * Time: 上午10:24
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class TaxCategoryGraphicDao {

    /*映射的表名*/
    private static String DB_MAPPING = "dm_sz_tb";

    /*映射的表主键*/
    private static String DB_IDENTITY = "sz_dm";

    /**
     * 添加
     *
     * @param taxCategoryGraphic
     * @return boolean
     */
    public boolean add(TaxCategoryGraphic taxCategoryGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + "(sz_dm ,mc,yxbz)values(?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxCategoryGraphic.getSzDm());
            ps.setString(2, taxCategoryGraphic.getMc());
            ps.setString(3, taxCategoryGraphic.getYxbz());
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
    public List<TaxCategoryGraphic> queryAll() {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxCategoryGraphic> list = new ArrayList<TaxCategoryGraphic>();
        String sql = "select * from  " + DB_MAPPING;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TaxCategoryGraphic taxCategoryGraphic = objectMapping(rs);
                list.add(taxCategoryGraphic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return list;
    }

    public boolean modify(TaxCategoryGraphic taxCategoryGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING + " set sz_dm = ? , mc = ? , yxbz = ? where id = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxCategoryGraphic.getSzDm());
            ps.setString(2, taxCategoryGraphic.getMc());
            ps.setString(3, taxCategoryGraphic.getYxbz());
            ps.setInt(4, taxCategoryGraphic.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    public boolean del(TaxCategoryGraphic taxCategoryGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from  " + DB_MAPPING + " where id = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, taxCategoryGraphic.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    public TaxCategoryGraphic queryByCode(String code) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TaxCategoryGraphic obj = null;
        String sql = "select  * from  " + DB_MAPPING + " where sz_dm = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = objectMapping(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return obj;
    }


    public TaxCategoryGraphic queryById(int id) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TaxCategoryGraphic obj = null;
        String sql = "select  * from  " + DB_MAPPING + " where id = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = objectMapping(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return obj;
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


    public TaxCategoryGraphic objectMapping(ResultSet rs) {
        TaxCategoryGraphic taxCategoryGraphic = new TaxCategoryGraphic();
        try {
            taxCategoryGraphic.setId(rs.getInt("id"));
            taxCategoryGraphic.setSzDm(rs.getString("sz_dm"));
            taxCategoryGraphic.setMc(rs.getString("mc"));
            taxCategoryGraphic.setYxbz(rs.getString("yxbz"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxCategoryGraphic;
    }


}
