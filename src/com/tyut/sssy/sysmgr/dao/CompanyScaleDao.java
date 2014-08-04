package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.Category;
import com.tyut.sssy.sysmgr.domain.CompanyScale;
import com.tyut.sssy.utils.db.C3P0Util;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName:CompanyScaleDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-11
 * Time: 9:50:45
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class CompanyScaleDao {

    /*
     注意：CompanyScale - dm_qygm (db) 
     */

    /**
     * 添加一个企业规模记录
     *
     * @param companyScale
     * @return flag
     */
    public boolean add(CompanyScale companyScale) {
        boolean flag = true;
        Connection conn = null;
        String sql = "insert into dm_qygm (qygm_dm , qygm_mc , se_q,se_z)values(?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, companyScale.getCsCode());
            ps.setString(2, companyScale.getCsName());
            ps.setBigDecimal(3, companyScale.getMinLimit());
            ps.setBigDecimal(4, companyScale.getMaxLimit());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return flag;

    }


    /**
     * 修改一个企业规模
     *
     * @param companyScale
     * @return flag
     */
    public boolean modify(CompanyScale companyScale) {
        boolean flag = true;
        Connection conn = null;
        String sql = "update  dm_qygm  set qygm_mc = ? , se_q=? ,se_z=? where qygm_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, companyScale.getCsName());
            ps.setBigDecimal(2, companyScale.getMinLimit());
            ps.setBigDecimal(3, companyScale.getMaxLimit());
            ps.setString(4, companyScale.getCsCode());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return flag;
    }


    /**
     * 批量删除 CompanyScales
     *
     * @param companyScales
     * @return boolean
     */
    public boolean del(String companyScales[]) {
        boolean flag = true;
        Connection conn = null;
        String sql = "delete  from dm_qygm where qygm_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            for (String csCode : companyScales) {
                ps.setString(1, csCode);
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return flag;
    }


    /**
     * 根据主键查询
     *
     * @param csCode
     * @return companyScale
     */
    public CompanyScale getById(String csCode) {
        Connection conn = null;
        String sql = "select * from dm_qygm where qygm_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        CompanyScale companyScale = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, csCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                companyScale = objectMapping(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return companyScale;

    }

    /**
     * 返回所有的记录
     * @return list 
     */
    public List<CompanyScale> getAllRecords() {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CompanyScale> list = new ArrayList<CompanyScale>();
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();

            sqlTotal.append("select * from  dm_qygm ");

            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            CompanyScale companyScale = null;
            while (rs.next()) {
                companyScale = objectMapping(rs);
                list.add(companyScale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return list;
    }

    public Map<String, Object> queryByCon(String csName, int pageSize, int pageNo) {

        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<CompanyScale> list = new ArrayList<CompanyScale>();
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            /*判断参数是否为空*/
            if (csName == null || csName.equals(""))
                csName = "%";

            if (!csName.equals("%"))
                //拼接查询总数的SQL
                sqlTotal.append("select count(*) from dm_qygm where qygm_mc like '%" + csName + "%' ");
            else
                sqlTotal.append("select count(*) from dm_qygm ");

            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);
            i = 0;
            //拼接分页SQL查询
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from dm_qygm where qygm_dm > (select ISNULL(MAX(qygm_dm),0)  from (  ");
            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from dm_qygm where qygm_mc like '%" + csName + "%' ");

            sqlPaging.append(" order by se_q desc )A ");
            sqlPaging.append(") and qygm_dm like '%" + csName + "%' ");
            sqlPaging.append("order by se_q desc ");

            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                CompanyScale temp = objectMapping(rs);
                list.add(temp);
            }
            map.put("list", list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }

        return map;
    }

    public String getMaxId() {

        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String maxId = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            sql.append("select Max(qygm_dm) from dm_qygm ");
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();

            if (rs.next()) {
                maxId = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }

        String prefix = "1";
        String temp = prefix + maxId;
        Integer num = Integer.valueOf(temp);
        num += 1;

        return num.toString().substring(1);
    }

    /**
     * 实现ORM功能
     *
     * @param rs
     * @return CompanyScale
     */
    public CompanyScale objectMapping(ResultSet rs) {
        CompanyScale companyScale = new CompanyScale();
        try {
            companyScale.setCsCode(rs.getString("qygm_dm"));
            companyScale.setCsName(rs.getString("qygm_mc"));
            companyScale.setMinLimit(rs.getBigDecimal("se_q"));
            companyScale.setMaxLimit(rs.getBigDecimal("se_z"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyScale;
    }
}