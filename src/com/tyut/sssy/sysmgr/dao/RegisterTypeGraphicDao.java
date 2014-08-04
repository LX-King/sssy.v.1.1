package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.IndustryGraphic;
import com.tyut.sssy.sysmgr.domain.RegisterTypeGraphic;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:RegisterTypeGraphicDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-26
 * Time: 上午9:11
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class RegisterTypeGraphicDao {

    /*映射的表名*/
    private static String DB_MAPPING = "dm_qyzclx_tb";

    /*映射的表主键*/
    private static String DB_IDENTITY = "id";

    /**
     * 添加
     *
     * @param registerTypeGraphic
     * @return boolean
     */
    public boolean add(RegisterTypeGraphic registerTypeGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + "(qyzclx_dm ,mc)values(?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, registerTypeGraphic.getQyzclxDm());
            ps.setString(2, registerTypeGraphic.getMc());
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
    public List<RegisterTypeGraphic> queryAll() {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RegisterTypeGraphic> list = new ArrayList<RegisterTypeGraphic>();
        String sql = "select * from  " + DB_MAPPING;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                RegisterTypeGraphic registerTypeGraphic = objectMapping(rs);
                list.add(registerTypeGraphic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return list;
    }

    public boolean modify(RegisterTypeGraphic registerTypeGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING + " set qyzclx_dm = ? , mc = ? where id = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, registerTypeGraphic.getQyzclxDm());
            ps.setString(2, registerTypeGraphic.getMc());
            ps.setInt(3, registerTypeGraphic.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    public boolean del(RegisterTypeGraphic registerTypeGraphic) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from  " + DB_MAPPING + " where id = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, registerTypeGraphic.getId());
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

    public RegisterTypeGraphic queryByCode(String code) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RegisterTypeGraphic obj = null;
        String sql = "select  * from  " + DB_MAPPING + " where qyzclx_dm = ? ";
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

    public RegisterTypeGraphic queryById(int id) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RegisterTypeGraphic obj = null;
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

    public RegisterTypeGraphic objectMapping(ResultSet rs) {
        RegisterTypeGraphic registerTypeGraphic = new RegisterTypeGraphic();
        try {
            registerTypeGraphic.setId(rs.getInt("id"));
            registerTypeGraphic.setQyzclxDm(rs.getString("qyzclx_dm"));
            registerTypeGraphic.setMc(rs.getString("mc"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registerTypeGraphic;
    }
}
