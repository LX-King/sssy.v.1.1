package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.GCZHB;
import com.tyut.sssy.utils.db.C3P0Util;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName:GCZHBDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-31
 * Time: 16:17:09
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class GCZHBDao {

    /*映射的表名*/
    private static String DB_MAPPING = "gc_zhb";

    /*映射的表主键*/
    private static String DB_IDENTITY = "id";


    /**
     * 返回所有的GCZHB
     *
     * @return list
     */
    public List<GCZHB> getAllRecords() {
        Connection conn = null;
        String sql = "select id , nd , sssq_q , sssq_z , qygm ,snsjse  from " + DB_MAPPING;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<GCZHB> list = new ArrayList<GCZHB>();
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            GCZHB o = null;
            while (rs.next()) {
                o = objectMapping(rs);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return list;
    }

    /**
     * 返回所有的GCZHB 根据年度
     *
     * @return list
     */
    public List<GCZHB> getRecordsbyTime(String year , String seasonBegin , String seasonEnd) {
        Connection conn = null;
        String sql = "select id , nd , sssq_q , sssq_z , qygm  , snsjse from " + DB_MAPPING + " where nd like '%" + year + "%' and sssq_q like '%"+seasonBegin+"%' and sssq_z like '%"+seasonEnd+"%'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<GCZHB> list = new ArrayList<GCZHB>();
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            GCZHB o = null;
            while (rs.next()) {
                o = objectMapping(rs);
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return list;
    }


    /**
     * 修改
     *
     * @param o
     * @return Boolean
     */
    public boolean modify(GCZHB o) {
        Connection conn = null;
        String sql = "update " + DB_MAPPING + " set qygm = ? where id = ?";
        PreparedStatement ps = null;
        boolean flag = true;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getQygm());
            ps.setInt(2, o.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }


    /**
     * ORM 映射
     *
     * @param rs
     * @return gczhb
     */
    public GCZHB objectMapping(ResultSet rs) {
        GCZHB o = new GCZHB();
        try {
            o.setId(rs.getInt("id"));
            o.setNd(rs.getString("nd"));
            o.setSssq_q(rs.getString("sssq_q"));
            o.setSssq_z(rs.getString("sssq_z"));
            o.setQygm(rs.getString("qygm"));
            o.setSnsjse(rs.getBigDecimal("snsjse"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
