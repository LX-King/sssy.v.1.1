package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.IndustryGraphic;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:IndustryGraphicDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-25
 * Time: 上午9:45
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class IndustryGraphicDao {

    /*映射的表名*/
    private static String DB_MAPPING = "dm_hy_tb";

    /*映射的表主键*/
    private static String DB_IDENTITY = "hy_dm";

    /**
     * 添加
     *
     * @param industryGraphic
     * @return boolean
     */
    public boolean add(IndustryGraphic industryGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + "(hy_dm ,hy_mc)values(?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, industryGraphic.getIndustryCode());
            ps.setString(2, industryGraphic.getIndustryName());
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
    public List<IndustryGraphic> queryAll() {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IndustryGraphic> list = new ArrayList<IndustryGraphic>();
        String sql = "select * from  " + DB_MAPPING;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                IndustryGraphic industryGraphic = objectMapping(rs);
                list.add(industryGraphic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return list;
    }

    public boolean modify(IndustryGraphic industryGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING + " set hy_dm = ? , hy_mc = ? where id = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, industryGraphic.getIndustryCode());
            ps.setString(2, industryGraphic.getIndustryName());
            ps.setInt(3, industryGraphic.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    public boolean del(IndustryGraphic industryGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from  " + DB_MAPPING + " where id = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, industryGraphic.getId());
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

    public IndustryGraphic queryByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        IndustryGraphic obj = null;
        String sql = "select  * from  " + DB_MAPPING + " where hy_dm = ? ";
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
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return obj;
    }

    public IndustryGraphic queryById(int id) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        IndustryGraphic obj = null;
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

    public IndustryGraphic objectMapping(ResultSet rs) {
        IndustryGraphic industryGraphic = new IndustryGraphic();
        try {
            industryGraphic.setId(rs.getInt("id"));
            industryGraphic.setIndustryCode(rs.getString("hy_dm"));
            industryGraphic.setIndustryName(rs.getString("hy_mc"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return industryGraphic;
    }


}
