package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.UnitEmpBusiness;
import com.tyut.sssy.sysmgr.domain.CompanyScale;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * ClassName:UnitEmpBusinessDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-14
 * Time: 14:07:34
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class UnitEmpBusinessDao {


    /**
     * 添加一个单位重点行业
     *
     * @param unitEmpBusiness
     * @return flag
     */
    public boolean add(UnitEmpBusiness unitEmpBusiness) {
        boolean flag = true;
        Connection conn = null;
        String sql = "insert into dm_hydl (hydl_dm , mc, cy_dm,zdhybz)values(?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, unitEmpBusiness.getEbCode());
            ps.setString(2, unitEmpBusiness.getEbCode());
            ps.setString(3, unitEmpBusiness.getIndustryCode());
            ps.setString(4, unitEmpBusiness.getFlag());
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
     * 修改 单位重点行业
     *
     * @param unitEmpBusiness
     * @return flag
     */
    public boolean modify(UnitEmpBusiness unitEmpBusiness) {
        boolean flag = true;
        Connection conn = null;
        String sql = "update dm_hydl set mc = ?, cy_dm = ?,zdhybz = ? where hydl_dm  = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, unitEmpBusiness.getEbName());
            ps.setString(2, unitEmpBusiness.getIndustryCode());
            ps.setString(3, unitEmpBusiness.getFlag());
            ps.setString(4, unitEmpBusiness.getEbCode());
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
     * 删除多个 单位重点行业
     *
     * @param unitEmpBusinessCodes
     */
    public boolean del(String unitEmpBusinessCodes[]) {
        boolean flag = true;
        Connection conn = null;
        String sql = "delete  from dm_hydl where hydl_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            for (String code : unitEmpBusinessCodes) {
                ps.setString(1, code);
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
     * @param code
     * @return companyScale
     */
    public UnitEmpBusiness getById(String code) {
        Connection conn = null;
        String sql = "select * from dm_hydl where hydl_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        UnitEmpBusiness unitEmpBusiness = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, code);
            rs = ps.executeQuery();
            while (rs.next()) {
                unitEmpBusiness = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }
        return unitEmpBusiness;
    }


    /**
     * 返回 最大记录数ID+1
     *
     * @return
     */
    public String getMaxId() {

        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String maxId = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            sql.append("select Max(id) from dm_hydl ");
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
     * 根据 名称查询
     *
     * @param ebName
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> queryByCon(String ebName, int pageSize, int pageNo) {

        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<UnitEmpBusiness> list = new ArrayList<UnitEmpBusiness>();
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            /*判断参数是否为空*/
            if (ebName == null || ebName.equals(""))
                ebName = "%";
            if (!ebName.equals("%"))
                //拼接查询总数的SQL
                sqlTotal.append("select count(*) from dm_hydl where mc like '%" + ebName + "%' ");
            else
                sqlTotal.append("select count(*) from dm_hydl ");

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
            sqlPaging.append(" * from dm_hydl where hydl_dm > (select ISNULL(MAX(hydl_dm),0)  from (  ");
            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from dm_hydl where mc like '%" + ebName + "%' ");

            sqlPaging.append(" order by hydl_dm ASC )A ");
            sqlPaging.append(") and mc like '%" + ebName + "%' ");
            sqlPaging.append("order by hydl_dm ASC ");

            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                UnitEmpBusiness temp = objectMapping(rs);
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


    /**
     * 实现ORM功能
     *
     * @param rs
     * @return CompanyScale
     */
    public UnitEmpBusiness objectMapping(ResultSet rs) {
        UnitEmpBusiness unitEmpBusiness = new UnitEmpBusiness();
        try {
            unitEmpBusiness.setEbCode(rs.getString("hydl_dm"));
            unitEmpBusiness.setEbName(rs.getString("mc"));
            unitEmpBusiness.setIndustryCode(rs.getString("cy_dm"));
            unitEmpBusiness.setFlag(rs.getString("zdhybz"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unitEmpBusiness;
    }


}
